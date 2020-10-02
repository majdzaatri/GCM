package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.SiteDTO;
import g6.gcm.core.entity.TourDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TourDAO extends AbstractDAO {

  @Override
  public Object processQuery(AbstractDTO theDTO) {

    /* Prepare answer */
    Object answer = null;

    /*Cast for easier usage*/
    ClientsInquiries theRequest = (ClientsInquiries) theDTO.getRequest();

    /* Closing leaks: if any of the following lines fails, the instances will be automatically close */
    try (
        /* Get a connection ready */
        Connection connection = GCMServer.getServer().dbConnection.getConnection();

        /* Prepare Statement */
        PreparedStatement statement = connection.prepareStatement(theRequest.getQuery())
    ) {

      /* Cast for easier usage */
      TourDTO tourRequest = (TourDTO) theDTO;

      /* To get the answer in */
      TourDTO tourAnswer;

      /* Direct to the right executor method with a ready statement */
      switch (theRequest) {

        case ALL_TOURS_OF_MAP: {
          statement.setInt(1, tourRequest.getMapID());
          List<TourDTO> toursList = (List<TourDTO>) getAll(statement);
          SiteDTO siteRequest = new SiteDTO();
          siteRequest.setRequest(ClientsInquiries.ALL_SITES_NAMES_OF_TOUR);
          for (TourDTO tour : toursList) {
            siteRequest.setTourID(tour.getTourID());
            tour.setSites(
                ((List<SiteDTO>) DAOsFactory.getSitesDAO().processQuery(siteRequest)).stream()
                    .map(SiteDTO::getSiteName).collect(Collectors.toList()));
          }
          answer = toursList;
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
    TourDTO tour = new TourDTO();
    try {
      tour.setTourID(resultSet.getInt("TourID"));
      tour.setMapID(resultSet.getInt("MapID"));
    } catch (SQLException e) {
      e.printStackTrace();
      tour = null;
    }
    return tour;

  }


}
