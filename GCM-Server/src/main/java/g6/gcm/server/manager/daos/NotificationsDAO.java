package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.entity.NotificationDTO;
import g6.gcm.server.manager.GCMServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationsDAO extends AbstractDAO {

    @Override
    public Object processQuery(AbstractDTO request) {

        Connection connection = GCMServer.getServer().dbConnection.getConnection();

        PreparedStatement preparedStatement;

        ClientsInquiries theRequest = (ClientsInquiries) request.getRequest();

        Object answer;

        try {
            switch (theRequest) {

                case ADD_NOTIFICATION: {
                    preparedStatement = connection
                            .prepareStatement(theRequest.getQuery());
                    preparedStatement.setString(1, ((NotificationDTO) request).getFromUsername());
                    preparedStatement.setString(2, ((NotificationDTO) request).getToUsername());
                    preparedStatement.setDate(3, ((NotificationDTO) request).getDateOfNotifying());
                    preparedStatement.setString(4, ((NotificationDTO) request).getNotificationType());
                    preparedStatement.setString(5, ((NotificationDTO) request).getNotification());

                    InsertInto(preparedStatement);
                    answer = (NotificationDTO) request;
                    break;
                }
                case GET_NOTIFICATIONS: {
                    preparedStatement = connection.prepareStatement(theRequest.getQuery());
                    preparedStatement.setString(1, ((NotificationDTO) request).getToUsername());
                    answer = getAll(preparedStatement);
                    break;
                }
                case UPDATE_NOTIFICATION_READ: {
                    preparedStatement = connection.prepareStatement(theRequest.getQuery());
                    preparedStatement.setInt(1, ((NotificationDTO) request).getNotificationID());
                    update(preparedStatement);
                    answer = request;
                    break;
                }

                default: {
                    answer = null;
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
        NotificationDTO notification = new NotificationDTO();

        try {
            notification.setNotificationID(resultSet.getInt("NotificationID"));
            notification.setFromUsername(resultSet.getString("Sender"));
            notification.setToUsername(resultSet.getString("Receiver"));
            notification.setDateOfNotifying(resultSet.getDate("NotificationDate"));
            notification.setNotification(resultSet.getString("Notification"));
            notification.setNotificationType(resultSet.getString("NotificationType"));
            notification.setRead(resultSet.getInt("Read"));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notification;
    }

}
