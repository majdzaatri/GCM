package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.CoordinatesDTO;
import g6.gcm.core.entity.SiteClassificationDTO;
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

public class SitesDAO extends AbstractDAO {

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
      SiteDTO siteRequest = (SiteDTO) theDTO;

      /* To get the answer in */
      SiteDTO siteAnswer;

      /* Direct to the right executor method with a ready statement */
      switch (theRequest) {

        case ALL_SITES: {
          answer = getAll(statement);
          break;
        }

        case ALL_SITES_OF_CITY: {
          statement.setInt(1, siteRequest.getCityID());
          answer = getAll(statement);
          break;
        }

        case ALL_SITES_OF_MAP: {
          statement.setInt(1, siteRequest.getMapID());
          answer = getAll(statement);
          break;
        }

        case ALL_SITES_NAMES_OF_TOUR: {
          statement.setInt(1, siteRequest.getTourID());
          answer = getAll(statement);
          break;
        }
        case ALL_SITES_NAMES_OF_CITY: {
          statement.setInt(1, siteRequest.getCityID());
          answer = getAll(statement);
          break;
        }
        case ALL_SITES_NAMES_OF_MAP: {
          statement.setInt(1, siteRequest.getMapID());
          answer = getAll(statement);
          break;
        }

        case CITY_NUMBER_OF_SITES: {
          statement.setInt(1, siteRequest.getCityID());
          answer = getCount(statement);
          break;
        }

        case CREATE_SITE: {
          //(`SiteName`, `CityID`, `Description`, `RecommendedVisitDuration`, "
          //           + "`Category`, `Accessible`)
          statement.setString(1, siteRequest.getSiteName());
          statement.setInt(2, siteRequest.getCityID());
          statement.setString(3, siteRequest.getDescription());
          statement.setString(4, siteRequest.getRecommendedVisitDuration());
          statement.setString(5, siteRequest.getSiteClassification().render());
          statement.setBoolean(6, siteRequest.getAccessible());
          if (insert(statement)) {

            siteAnswer = new SiteDTO();
            siteAnswer.setRequest(ServersCommands.SUCCESSFULL_SITE_CREATION);
            answer = siteRequest;
          }
          ;
          break;
        }
        case DELETE_SITE: {
          statement.setInt(1, siteRequest.getSiteID());
          if (delete(statement) != -1) {
            siteAnswer = new SiteDTO();
            siteAnswer.setRequest(ServersCommands.SUCCESSFULL_SITE_DELETION);
            answer = siteRequest;
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

    SiteDTO site = new SiteDTO();
    try {
      switch (resultSet.getMetaData().getColumnCount()) {
        case 8: {
          site.setSiteID(resultSet.getInt("SiteID"));
          site.setCityID(resultSet.getInt("CityID"));
          site.setDescription(resultSet.getString("Description"));
          site.setRecommendedVisitDuration(resultSet.getString("RecommendedVisitDuration"));

          SiteClassificationDTO siteClassification = new SiteClassificationDTO();
          siteClassification.setCategory(resultSet.getString("Category"));
          site.setSiteClassification(siteClassification);
          site.setAccessible(resultSet.getBoolean("Accessible"));
          site.setCoordinatesDTO((CoordinatesDTO) DAOsFactory.getCoordinatesDAO()
              .getCoordinatesByID(resultSet.getInt("CoordinatesID")));
        }
        case 1: {
          site.setSiteName(resultSet.getString("SiteName"));
          break;
        }
        default: {
          GCMServer.getServer().say("This is " + this.getClass().toString()
              + ". My extractFromResultSet switch missed the cases!");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // TODO: check what is the right practice..
      return site;
    }

  }

}
