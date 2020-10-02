package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.PriceRequestDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;

import java.sql.*;

public class PriceRequestDAO extends AbstractDAO {
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

            PriceRequestDTO priceRequest = new PriceRequestDTO();

            PriceRequestDTO priceRequestAnswer;

            switch (theRequest) {
                case CITY_PRICE: {
                    statement.setString(1, ((PriceRequestDTO) theDTO).getCityName());
                    answer = getOne(statement);
                    break;
                }
                case CANCEL_REQUEST: {
                    statement.setString(1, ((PriceRequestDTO) theDTO).getCityName());
                    int deleteReurnValue = delete(statement);
                    if (deleteReurnValue == 1) {
                        System.out.println("deleted");
                        theDTO.setRequest(ServersCommands.PRICE_REQUEST_DELETED);
                        answer = ((PriceRequestDTO) theDTO);
                    }
                    break;
                }
                case ADD_MAPS_PRICE_REQUEST: {
                    statement.setString(1, ((PriceRequestDTO) theDTO).getCityName());
                    statement.setDouble(2, ((PriceRequestDTO) theDTO).getSubscribtionPrice());
                    statement.setString(3, ((PriceRequestDTO) theDTO).getRequestStatus());
                    statement.setDouble(4, ((PriceRequestDTO) theDTO).getOneShotDealPrice());
                    statement.setDate(5, ((PriceRequestDTO) theDTO).getRequestDate());
                    if (insert(statement)) {
                        theDTO.setRequest(ServersCommands.PRICE_REQUEST_HAS_BEEN_ADDED);
                        answer = ((PriceRequestDTO) theDTO);
                    }
                    break;
                }
                case GET_ALL_PRICES_REQUEST: {
                    answer = getAll(statement);
                    break;
                }
                case DELETE_PRICE_REQUEST: {
                    statement.setInt(1,((PriceRequestDTO) theDTO).getRequestID());
                    update(statement);
                    break;
                }
            }
        } catch (SQLException e) {
            GCMServer.getServer().say("EXCEPTION IN " + this);
            GCMServer.getServer().oops(e);
            e.printStackTrace();
        }
        return answer;
    }


    @Override
    protected AbstractDTO extractFromResultSet(ResultSet resultSet) {
        PriceRequestDTO priceRequest = new PriceRequestDTO();

        try {
            priceRequest.setCityName(resultSet.getString("cityName"));
            priceRequest.setRequestID(resultSet.getInt("requestID"));
            priceRequest.setSubscribtionPrice(resultSet.getDouble("subscriptionPrice"));
            priceRequest.setOneShotDealPrice(resultSet.getDouble("oneShotDealPrice"));
            priceRequest.setRequestStatus(resultSet.getString("requestStatus"));
            priceRequest.setRequestDate(resultSet.getDate("requestDate"));
        } catch (SQLException e) {
            e.printStackTrace();
            priceRequest = null;
        }
        return priceRequest;
    }
}
