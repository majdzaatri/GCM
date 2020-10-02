package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.CoordinatesDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoordinatesDAO extends AbstractDAO {


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
          CoordinatesDTO coordinatesRequest = (CoordinatesDTO) theDTO;

          /* To get the answer in */
          CoordinatesDTO coordinatesAnswer;

          /* Direct to the right executor method with a ready statement */
      switch (theRequest) {
        case INSERT: {
            statement.setDouble(1, coordinatesRequest.getxCoordinate());
            statement.setDouble(2, coordinatesRequest.getyCoordinate());
          answer = insert(statement);
          break;
        }

          case ONE_COORDINATES: {
              statement.setInt(1, coordinatesRequest.getID());
          answer = getOne(statement);
          break;
        }

          case UPDATE_COORDINATES: {
              statement.setDouble(1, coordinatesRequest.getxCoordinate());
              statement.setDouble(2, coordinatesRequest.getyCoordinate());
              statement.setInt(3, coordinatesRequest.getID());
              if (update(statement) == 1) {
                  answer = coordinatesAnswer = new CoordinatesDTO();
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
  protected CoordinatesDTO extractFromResultSet(ResultSet resultSet) {
    CoordinatesDTO coordinates = new CoordinatesDTO();
    try {
        coordinates.setID(resultSet.getInt("CoordinatesID"));
        coordinates.setxCoordinate(resultSet.getDouble("Latitude"));
        coordinates.setyCoordinate(resultSet.getDouble("Longitude"));
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // TODO: check what is the right practice..
      return coordinates;
    }
  }

    public Object getCoordinatesByID(int coordinateID) {
        CoordinatesDTO coordinates = new CoordinatesDTO();
        coordinates.setRequest(ClientsInquiries.ONE_COORDINATES);
        coordinates.setID(coordinateID);
        return processQuery(coordinates);
    }
}
