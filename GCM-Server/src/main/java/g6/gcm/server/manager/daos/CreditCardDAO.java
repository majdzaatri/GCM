package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.CreditCardDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCardDAO extends AbstractDAO {
    /*
     * (non-Javadoc)
     *
     * @see
     * core.java.entity.dao.interfaces.AbstractDAO#processQuery(core.java.entity.
     * AbstractDTO)
     */
    @Override
    public Object processQuery(AbstractDTO request) {

        /* Get a connection ready */
        Connection connection = GCMServer.getServer().dbConnection.getConnection();

        /* Prepare Statement */
        PreparedStatement statement;

        /* Prepare Object */
        Object answer = null;

        ClientsInquiries theRequest = (ClientsInquiries) request.getRequest();
        try {
            /* Direct to the right executer method with a ready statement */
            switch (theRequest) {

                case INSERT_CREDIT_CARD: {
                    CreditCardDTO cc = new CreditCardDTO();
                    statement = connection.prepareStatement(theRequest.getQuery());
                    statement.setString(1, ((CreditCardDTO) request).getAccountEmail());
                    statement.setString(2, ((CreditCardDTO) request).getCardholdersName());
                    statement.setString(3, ((CreditCardDTO) request).getCardholdersID());
                    statement.setString(4, ((CreditCardDTO) request).getCreditCardNumber());
                    statement.setDate(5, ((CreditCardDTO) request).getCreditCardExpirationDate());
                    statement.setString(6, ((CreditCardDTO) request).getCreditCardCSV());

                    if (insert(statement)) {

                        request.setRequest(ServersCommands.SUCCESSFUL_CREDIT_CARD_INSERT);
                        answer = (CreditCardDTO) request;
                    } else {

                        cc.setRequest(ServersCommands.FAILED_CREDIT_CARD_INSERT);
                        answer = cc;
                    }
                    break;
                }


                case ONE_CREDIT_CARD: {

                    statement = connection.prepareStatement(theRequest.getQuery());
                    statement.setString(1, ((CreditCardDTO) request).getAccountEmail());
                    request.setRequest(ServersCommands.CREDIT_INFO_TRANSFERED);
                    answer = getOne(statement);
                    break;
                }

                case DELETE_CREDIT_CARD: {
                    statement = connection.prepareStatement((theRequest.getQuery()));
                    statement.setString(1, ((CreditCardDTO) request).getCreditCardNumber());
                    if (delete(statement) == 0) {
                        request.setRequest(ServersCommands.CREDIT_CARD_DELETED);
                        answer = request;
                    }
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            answer = null;
        }
        return answer;
    }

    @Override
    protected AbstractDTO extractFromResultSet(ResultSet resultSet) {
        CreditCardDTO creditCard = new CreditCardDTO();
        try {
            creditCard.setAccountEmail(resultSet.getString("email"));
            creditCard.setCardholdersID(resultSet.getString("cardholdersID"));
            creditCard.setCardholdersName(resultSet.getString("cardholdersName"));
            creditCard.setCreditCardNumber(resultSet.getString("creditCardNumber"));
            creditCard.setCreditCardExpirationDate(resultSet.getDate("creditCardExpirationDate"));
            creditCard.setCreditCardCSV(resultSet.getString("creditCardCSV"));
        } catch (SQLException e) {
            e.printStackTrace();
            creditCard = null;
        }
        return creditCard;
    }


}
