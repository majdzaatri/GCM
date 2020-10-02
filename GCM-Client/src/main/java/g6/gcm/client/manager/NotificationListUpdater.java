package g6.gcm.client.manager;

import g6.gcm.core.entity.NotificationDTO;
import g6.gcm.core.entity.SubscriptionDTO;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ClientsInquiries;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationListUpdater {


    private static ObjectProperty<UserDTO> userProperty = new SimpleObjectProperty<UserDTO>();
    Timer myTimer = new Timer();
    TimerTask myTask = new TimerTask() {

        @Override
        public void run() {

            // ******   Waits for the user to sign in to update his notification list *****


            userPropertyProperty().addListener(((observable, oldValue, newValue) ->
            {
                NotificationsManager.getUserNotificationsString(getUserProperty().getEmail());
            }));


            try {
                NotificationsManager.getUserNotificationsString(getUserProperty().getEmail());
            } catch (NullPointerException e) {
                System.out.println("Notification Updater: Not signed in yet..");
            }

        }
    };
    private long period = 120000;    //notifications updates each 2 minutes

    public static UserDTO getUserProperty() {
        return userProperty.get();
    }

    public static void setUserProperty(UserDTO userProperty) {
        NotificationListUpdater.userProperty.set(userProperty);
    }

    public static ObjectProperty<UserDTO> userPropertyProperty() {
        return userProperty;
    }

    // TODO: check for better alternatives
    public void launchTask() {

        myTimer.scheduleAtFixedRate(myTask, 1000, period);

    }
}
