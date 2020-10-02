package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.CreditCardDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;

import java.sql.*;

public class CreditCardsDAO extends AbstractDAO {

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
            CreditCardDTO creditCardRequest = (CreditCardDTO) theDTO;

            /* To get the answer in */
            CreditCardDTO creditCardAnswer;

            /* Direct to the right executor method with a ready statement */
            switch (theRequest) {

                case INSERT_CREDIT_CARD: {
                    statement.setString(1, creditCardRequest.getAccountEmail());
                    statement.setString(2, creditCardRequest.getCardholdersName());
                    statement.setString(3, creditCardRequest.getCardholdersID());
                    statement.setString(4, creditCardRequest.getCreditCardNumber());
                    statement.setDate(5, creditCardRequest.getCreditCardExpirationDate());
                    statement.setString(6, creditCardRequest.getCreditCardCSV());

                    if (insert(statement)) {
                        creditCardRequest.setRequest(ServersCommands.SUCCESSFUL_CREDIT_CARD_INSERT);
                        answer = creditCardRequest;
                    } else {
                        creditCardAnswer = new CreditCardDTO();
                        creditCardAnswer.setRequest(ServersCommands.FAILED_CREDIT_CARD_INSERT);
                        answer = creditCardAnswer;
                    }
                    break;
                }
                case ONE_CREDIT_CARD: {
                    statement.setString(1, creditCardRequest.getAccountEmail());
                    answer = getOne(statement);
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
        CreditCardDTO creditCard = new CreditCardDTO();
        try {
            creditCard.setAccountEmail(resultSet.getString("AccountsEmail"));
            creditCard.setCardholdersName(resultSet.getString("CardholdersName"));
            creditCard.setCardholdersID(resultSet.getString("CardholdersID"));
            creditCard.setCreditCardNumber(resultSet.getString("CreditCardNumber"));
            creditCard.setCreditCardExpirationDate(resultSet.getDate("CreditCardExpirationDate"));
            creditCard.setCreditCardCSV(resultSet.getString("CreditCardCSV"));
        } catch (SQLException e) {
            e.printStackTrace();
            creditCard = null;
        }
        return creditCard;
    }


}
