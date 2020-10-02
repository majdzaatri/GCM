package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.ActivityDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivitiesDAO extends AbstractDAO {


    @Override
    public Object processQuery(AbstractDTO request) {
        /* Get a connection ready */
        Connection connection = GCMServer.getServer().dbConnection.getConnection();

        /* Prepare Statement */
        PreparedStatement statement;

        /* Prepare Object */
        Object answer = null;

        ClientsInquiries theRequest = (ClientsInquiries) request.getRequest();

        ActivityDTO activity = (ActivityDTO) request;

        try {
            /* Direct to the right executer method with a ready statement */
            switch (theRequest) {

                case ONE_SHOT_DEAL_PURCHASE:
                case SUBSCRIBE_TO_CITY:
                case EXTEND_SUBSCRIPTION: {
                    statement = connection.prepareStatement((ClientsInquiries.LOG_INTO_ACTIVITY).getQuery());
                    statement.setString(1, ((ActivityDTO) request).getEmail());
                    statement.setString(2, ((ActivityDTO) request).getActivity().name());
                    statement.setDate(3, ((ActivityDTO) request).getDate());
                    statement.setInt(4, ((ActivityDTO) request).getDoneTo());
                    if (insert(statement)) {

                        answer = request;
                    }
                    break;
                }
                case ACTIVITIES_MAPREQUEST: {
                    statement = connection.prepareStatement((ClientsInquiries.ACTIVITIES_MAPREQUEST).getQuery());
                    answer = getAll(statement);
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
        ActivityDTO activity = new ActivityDTO();

        try {
            System.out.println("HI " + activity.getType());
            activity.setId(resultSet.getInt("ActivityID"));
            activity.setEmail(resultSet.getString("Email"));
            activity.setActivity(ActivityDTO.Activity.valueOf(resultSet.getString("ActivityType")));
            activity.setDate(resultSet.getDate("ActivityDate"));
            activity.setDoneTo(resultSet.getInt("DoneTo"));

        } catch (SQLException e) {
            activity = null;
        }
        return activity;
    }

}
