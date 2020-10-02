package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.OneShotDealDTO;
import g6.gcm.core.entity.PurchaseDTO;
import g6.gcm.core.entity.SubscriptionDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchasesDAO extends AbstractDAO {

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
            PurchaseDTO purchaseRequest = (PurchaseDTO) theDTO;

            /* To get the answer in */
            PurchaseDTO purchaseAnswer;

            /* Direct to the right executor method with a ready statement */
            switch (theRequest) {

                case ONE_SUBSCRIPTION: {
                    statement.setInt(1, purchaseRequest.getPurchaseID());
                    answer = getOne(statement);
                    break;
                }

                case ALL_SUBSCRIPTIONS: {
                    answer = getAll(statement);
                    break;
                }
                case SUBSCRIBE_TO_CITY: {
                    statement.setString(1, purchaseRequest.getEmail());
                    statement.setInt(2, purchaseRequest.getCityID());
                    if (super.insert(statement)) {
                        purchaseAnswer = purchaseRequest;
                        purchaseAnswer.setRequest(ServersCommands.SUCCESSFUL_SUBSCRIPTION_CREATION);
                        answer = purchaseAnswer;
                    } else {
                        purchaseAnswer = new SubscriptionDTO();
                        purchaseAnswer.setRequest(ServersCommands.FAILED_SUBSCRIPTION_CREATION);
                        answer = purchaseAnswer;
                    }
                    break;
                }
                case EXTEND_SUBSCRIPTION: {

                    statement.setInt(1, purchaseRequest.getPurchaseID());

                    if (update(statement) > 0) {

                        // Subscription was successfully extended
                        purchaseAnswer = purchaseRequest;
                        purchaseAnswer.setRequest(ServersCommands.SUBSCRIPTION_SUCCESSFULLY_EXTENDED);
                        answer = purchaseAnswer;

                    } else {
                        // Was not
                        purchaseAnswer = new SubscriptionDTO();
                        purchaseAnswer.setRequest(ServersCommands.SUBSCRIPTION_NOT_EXTENDED);
                        answer = purchaseAnswer;
                    }
                    break;
                }
                case ALL_CITIES_SUBSCRIPTIONS_OF_USER: {
                    statement.setString(1, purchaseRequest.getEmail());
                    // get all cities ID's
                    List<PurchaseDTO> citiesID = (List<PurchaseDTO>) getAll(statement);
                    //
                    List<CityDTO> citiesList = new ArrayList();

                    CityDTO city = new CityDTO();

                    city.setRequest(ClientsInquiries.ONE_CITY);
                    for (PurchaseDTO p : citiesID) {
                        city.setCityID(p.getCityID());
                        citiesList.add((CityDTO) DAOsFactory.getCitiesDAO().processQuery(city));

                    }
                    answer = citiesList;

                    break;

                }

                case ALL_SUBSCRIPTIONS_OF_USER: {
                    statement.setString(1, purchaseRequest.getEmail());
                    answer = getAll(statement);

                    break;
                }


                case ONE_SHOT_DEAL_PURCHASE: {

                    statement.setString(1, purchaseRequest.getEmail());
                    statement.setInt(2, purchaseRequest.getCityID());
                    if (insert(statement)) {
                        purchaseAnswer = purchaseRequest;
                        purchaseAnswer.setRequest(ServersCommands.SUCCESSFUL_ONESHOTDEAL_CREATION);
                        answer = purchaseAnswer;

                    } else {
                        purchaseAnswer = new OneShotDealDTO();
                        purchaseAnswer.setRequest(ServersCommands.FAILED_ONESHOTDEAL_CREATION);
                        answer = purchaseAnswer;
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

    /*
     * This method extracts all the data needed to initialize a user. More concrete
     * data (Customer, Employee, ...) will be retrieved from it's own DAO.
     *
     * @see
     * core.java.entity.dao.interfaces.AbstractDAO#extractFromResultSet(java.sql.
     * ResultSet)
     */
    @Override
    protected PurchaseDTO extractFromResultSet(ResultSet resultSet) {
        PurchaseDTO purchaseDTO = null;

        // For the time being, we are not extracting the "WasExtended" column

        try {
            switch (resultSet.getMetaData().getColumnCount()) {
                case 6: {
                    if (resultSet.getDate("ExpirationDate") != null) {
                        purchaseDTO = new SubscriptionDTO();
                        ((SubscriptionDTO) purchaseDTO)
                                .setSubscriptionExpirationDate(resultSet.getDate("ExpirationDate"));
                    } else {
                        purchaseDTO = new OneShotDealDTO();
                    }
                    purchaseDTO.setWasExtended(resultSet.getInt("WasExtended"));
                    purchaseDTO.setPurchaseDate(resultSet.getDate("PurchaseDate"));
                    purchaseDTO.setPurchaseID(resultSet.getInt("PurchaseID"));
                    purchaseDTO.setEmail(resultSet.getString("CustomerEmail"));
                    purchaseDTO.setCityID(resultSet.getInt("CityID"));

                    break;
                }
                case 1: {
                    purchaseDTO = new PurchaseDTO();
                    purchaseDTO.setCityID(resultSet.getInt("CityID"));
                    break;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // TODO: check what is the right practice..
            return purchaseDTO;
        }
    }
}
