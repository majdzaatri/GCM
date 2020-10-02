package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.CoordinatesDTO;
import g6.gcm.core.entity.CoordinatizedDTO;
import g6.gcm.core.entity.MapDTO;
import g6.gcm.core.entity.SiteDTO;
import g6.gcm.core.factories.AccessabilityState;
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


public class MapsDAO extends AbstractDAO {

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
      MapDTO mapRequest = (MapDTO) theDTO;

      /* To get the answer in */
      MapDTO mapAnswer;

      /* Direct to the right executor method with a ready statement */
      switch (theRequest) {
        // `ID`, `Status`, `Version`, `BelongsTo`

        case ONE_MAP: {
          statement.setInt(1, mapRequest.getMapID());
          answer = getOne(statement);
          break;
        }
        case INSERT: {
          statement.setInt(1, mapRequest.getMapID());
          statement.setString(2, mapRequest.getStatus().name());
          statement.setDouble(3, mapRequest.getVersion());
          statement.setInt(4, mapRequest.getCityID());
          answer = insert(statement);
          try (PreparedStatement secondStatement = connection
              .prepareStatement(theRequest.getQuery())) {
            // answer = getAll(secondStatement);
          } catch (SQLException e) {
            GCMServer.getServer().say("EXCEPTION IN " + this);
            GCMServer.getServer().oops(e);
            e.printStackTrace();
          }
          break;
        }

        case ALL_MAPS: {
          answer = getAll(statement);
          break;
        }
          case ALL_MAPS_REQUEST: {
              answer = getAll(statement);
              break;
          }

          case UPDATE_MAP_STATUS: {
              statement.setString(1, "RELEASED");
              statement.setInt(2, mapRequest.getMapID());
              update(statement);
          }

        case ALL_MAPS_OF_CITY: {
          statement.setInt(1, mapRequest.getCityID());
          answer = getAll(statement);
          break;
        }

        case SAVE_MAP: {
          statement.setInt(1, mapRequest.getMapID());
          if (update(statement) > 0) {
            answer = new MapDTO();
            ((MapDTO) answer).setRequest(ServersCommands.MAP_SAVED);
          }
          break;
        }

        case UPDATE_MAP: {
          statement.setInt(1, mapRequest.getCityID());
          statement.setInt(2, mapRequest.getMapID());
          if (update(statement) > 0) {
            answer = new MapDTO();
            ((MapDTO) answer).setRequest(ServersCommands.MAP_SAVED);
          }
          break;
        }
        case CREATE_MAP: {
          // return "INSERT INTO `gcm-db`.`maps` (`MapID`, `CityID`) VALUES (?, ?);";
          statement.setInt(1, mapRequest.getMapID());
          statement.setInt(2, mapRequest.getCityID());
          if (insert(statement)) {
            answer = new MapDTO();
            ((MapDTO) answer).setRequest(ServersCommands.MAP_CREATED);
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
  protected AbstractDTO extractFromResultSet(ResultSet resultSet) {
    MapDTO map = new MapDTO();
    try {
      map.setMapID(resultSet.getInt("MapID"));
      map.setCityID(resultSet.getInt("CityID"));
      map.setVersion(resultSet.getDouble("MapsVersion"));
      map.setStatus(AccessabilityState.valueOf(resultSet.getString("MapsState")));

      SiteDTO sitesListRequest = new SiteDTO();
      sitesListRequest.setRequest(ClientsInquiries.ALL_SITES_NAMES_OF_MAP);
      sitesListRequest.setMapID(map.getMapID());
      map.setSiteList(
          ((List<SiteDTO>) DAOsFactory.getSitesDAO().processQuery(sitesListRequest)).stream()
              .map(SiteDTO::getSiteName).collect(Collectors.toList()));
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // TODO: check what is the right practice..
      return map;
    }
  }
}
