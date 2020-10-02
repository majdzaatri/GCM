package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.CoordinatesDTO;
import g6.gcm.core.entity.ExtentTransfer;
import g6.gcm.core.entity.MapDTO;
import g6.gcm.core.entity.SiteDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CitiesDAO extends AbstractDAO {

  @Override
  public Object processQuery(AbstractDTO theDTO) {

    /* Prepare answer */
    Object answer = null;

    /* Cast for easier usage */
    ClientsInquiries theRequest = (ClientsInquiries) theDTO.getRequest();

    /* Closing leaks: if any of the following lines fails, the instances will be automatically close */
    try (
        /* Get a connection ready */
        Connection connection = GCMServer.getServer().dbConnection.getConnection();

        /* Prepare Statement */
        PreparedStatement statement = connection.prepareStatement(theRequest.getQuery())
    ) {

      /* Cast for easier usage */
      CityDTO cityRequest = (CityDTO) theDTO;

      /* To get the answer in */
      CityDTO cityAnswer;

      /* Direct to the right executor method with a ready statement */
      switch (theRequest) {

        case ONE_CITY: {
          statement.setInt(1, cityRequest.getCityID());
          answer = getOne(statement);
          break;
        }
        case ALL_CITIES: {
          answer = getAll(statement);
          SiteDTO siteRequest = new SiteDTO();
          siteRequest.setRequest(ClientsInquiries.ALL_SITES_NAMES_OF_CITY);
          for (CityDTO city : (List<CityDTO>) answer) {
            siteRequest.setCityID(city.getCityID());
            city.setSitesList(
                ((List<SiteDTO>) DAOsFactory.getSitesDAO().processQuery(siteRequest)).stream()
                    .map(site -> site.getSiteName()).collect(
                    Collectors.toList()));
          }

          break;
        }

        case CITY_PRICE_BY_REQUEST: {
          System.out.println("city price by request entered");
          statement.setString(1, cityRequest.getCityName());
          answer = getOne(statement);
          break;
        }

        case CREATE_CITY: {
          // "INSERT INTO `gcm-db`.`cities` (`CityName`, `Description`) VALUES ('?', '?');";
          statement.setString(1, cityRequest.getCityName());
          statement.setString(2, cityRequest.getCityDescription());
          if (insert(statement)) {
            MapsDAO mapDAO = DAOsFactory.getMapsDAO();
            MapDTO mapRequest = new MapDTO();
            mapRequest.setCityID(cityRequest.getCityID());
            mapRequest.setRequest(ClientsInquiries.UPDATE_MAP);
            for (Integer mapID : cityRequest.getMapsList()) {
              mapRequest.setMapID(mapID);
              mapDAO.processQuery(mapRequest);
            }
            cityAnswer = new CityDTO();
            cityAnswer.setRequest(ServersCommands.SUCCESSFUL_CITY_CREATE);
            answer = cityAnswer;
          }
          break;
        }
        case UPDATE_CITY: {
          // "UPDATE cities SET CityName = ?,  Description = ? WHERE CityID = ?";
          statement.setString(1, cityRequest.getCityName());
          statement.setString(2, cityRequest.getCityDescription());
          statement.setInt(3, cityRequest.getCityID());

          if (update(statement) == 1) {
            cityRequest.getCoordinatesDTO().setRequest(ClientsInquiries.UPDATE_COORDINATES);
            DAOsFactory.getCoordinatesDAO().processQuery(cityRequest.getCoordinatesDTO());
            MapsDAO mapDAO = DAOsFactory.getMapsDAO();
            MapDTO mapRequest = new MapDTO();
            mapRequest.setCityID(cityRequest.getCityID());
            mapRequest.setRequest(ClientsInquiries.UPDATE_MAP);
            for (Integer mapID : cityRequest.getMapsList()) {
              mapRequest.setMapID(mapID);
              mapDAO.processQuery(mapRequest);
            }
            cityAnswer = new CityDTO();
            cityAnswer.setRequest(ServersCommands.SUCCESSFUL_CITY_UPDATE);
            answer = cityAnswer;
          }
          break;
        }

        case EXTENT: {
          ExtentTransfer ex = (ExtentTransfer) theDTO;
          statement.setInt(1, ex.getCityID());
          statement.setDouble(2, ex.getMinLatitude());
          statement.setDouble(3, ex.getMinLongitude());
          statement.setDouble(4, ex.getMaxLatitude());
          statement.setDouble(5, ex.getMaxLongitude());
          if (insert(statement)) {
            System.out.println(ConsoleTextColorsFactory.ANSI_GREEN + "city " + ex.getCityID()
                + "'s extent was updated bro" + ConsoleTextColorsFactory.ANSI_RESET);
          }
          break;
        }

        default: {
          GCMServer.getServer().say(
              "Request " + theRequest + " was" + ConsoleTextColorsFactory.ANSI_RED + " NOT "
                  + ConsoleTextColorsFactory.ANSI_RESET + "processed!");
        }
      }
    } catch (SQLException e) {
      GCMServer.getServer().say(
          ConsoleTextColorsFactory.ANSI_RED + "EXCEPTION" + ConsoleTextColorsFactory.ANSI_RESET
              + " IN " + this);
      GCMServer.getServer().oops(e);
      e.printStackTrace();
    }
    return answer;
  }

  @Override
  protected CityDTO extractFromResultSet(ResultSet resultSet) {

    CityDTO city = new CityDTO();

    try {
      city.setCityID(resultSet.getInt("CityID"));
      city.setCityName(resultSet.getString("CityName"));
      city.setCollectionsMapsNumber(resultSet.getInt("CollectionsMapsNumber"));
      city.setCollectionVersion(resultSet.getDouble("CollectionVersion"));
      city.setCollectionsDownloadsNumber(resultSet.getInt("CollectionsDownloadsNumber"));
      city.setCollectionsPriceID(resultSet.getInt("CollectionsPriceID"));
      city.setCityDescription(resultSet.getString("Description"));
      city.setSubscriptionPrice(resultSet.getDouble("subscriptionPrice"));
      city.setOneShotDealPrice(resultSet.getDouble("oneShotDealPrice"));
      city.setRequestDate(resultSet.getDate("requestDate"));

      SiteDTO citysNumberOfSites = new SiteDTO();
      citysNumberOfSites.setCityID(city.getCityID());
      citysNumberOfSites.setRequest(ClientsInquiries.CITY_NUMBER_OF_SITES);
      city.setCollectionsNumberOfSites(
          (Integer) DAOsFactory.getSitesDAO().processQuery(citysNumberOfSites));

      city.setCoordinatesDTO(
          (CoordinatesDTO) DAOsFactory.getCoordinatesDAO()
              .getCoordinatesByID(resultSet.getInt("CoordinatesID")));
      city.setZoom(resultSet.getDouble("MapViewZoom"));

    } catch (SQLException e) {
      e.printStackTrace();
      city = null;
    } finally {
      return city;
    }
  }

}
