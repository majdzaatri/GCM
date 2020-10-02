package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.OneShotDealDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OneShotDealDAO extends AbstractDAO {


    @Override
    public Object processQuery(AbstractDTO request) {
        /* Get a connection ready */
        Connection connection = GCMServer.getServer().dbConnection.getConnection();

        /* Prepare Statement */
        PreparedStatement statement;

        /* Prepare Object */
        Object answer = null;

        ClientsInquiries theRequest = (ClientsInquiries) request.getRequest();

        OneShotDealDTO oneShotDealRequest = (OneShotDealDTO) request;

        try {
            /* Direct to the right executer method with a ready statement */
            switch (theRequest) {

                case ONE_SHOT_DEAL_PURCHASE: {
                    OneShotDealDTO oneshotdealDTO = new OneShotDealDTO();
                    statement = connection.prepareStatement(theRequest.getQuery());
                    statement.setString(3, oneShotDealRequest.getEmail());
                    if (insert(statement)) {

                        request.setRequest(ServersCommands.SUCCESSFUL_ONESHOTDEAL_CREATION);
                        answer = request;

                    } else {

                        oneshotdealDTO.setRequest(ServersCommands.FAILED_ONESHOTDEAL_CREATION);
                        answer = oneshotdealDTO;
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
        return null;
    }
}
