package g6.gcm.client.manager;

import g6.gcm.core.entity.ActivityDTO;
import g6.gcm.core.factories.ClientsInquiries;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActivityManager {
    private static ListProperty<ActivityDTO> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList());

    public static ObservableList<ActivityDTO> getListProperty() {
        return listProperty.get();
    }

    public static void setListProperty(ObservableList<ActivityDTO> listProperty) {
        ActivityManager.listProperty.set(listProperty);
    }

    public static ListProperty<ActivityDTO> listPropertyProperty() {
        return listProperty;
    }

    public static void getActivities() {
        ActivityDTO activity = new ActivityDTO();

        activity.setRequest(ClientsInquiries.ACTIVITIES_MAPREQUEST);
        GCMClient.send(activity);
    }
}
