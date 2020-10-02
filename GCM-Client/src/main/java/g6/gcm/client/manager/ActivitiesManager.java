package g6.gcm.client.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import g6.gcm.core.entity.ActivityDTO;
import g6.gcm.core.entity.OneShotDealDTO;
import g6.gcm.core.entity.SiteDTO;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.interfaces.Renderable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.labs.scene.control.BeanPathAdapter;

public class ActivitiesManager {

    private static ActivitiesManager onlyInstance = new ActivitiesManager();

    private ObjectProperty<Renderable> activity;
    private BeanPathAdapter<Renderable> activityBean;
    private ListProperty<Renderable> activitiesList;

    private ActivitiesManager() {

        // Instantiate & initialize an empty activity as an object property
        activity = new SimpleObjectProperty<>(new SiteDTO());

        // Initialize activity's bean adaptor
        activityBean = new BeanPathAdapter<>(new SiteDTO());

        // Initialize activities list
        activitiesList = new SimpleListProperty<>(FXCollections.observableArrayList());

        // Initialize Manager's bindings
        initializeInternalBindings();

    }

    public static ActivitiesManager getInstance() {
        return onlyInstance;
    }

    /************************************************************************************************/





    public static void sendAndRecordActivity(ActivityDTO activity) {
        GCMClient.send(activity);
    }

    /**************************************************************************************************/
/**    _____        _    _                               _____        _    _                     **/
/**   / ____|      | |  | |                     ___     / ____|      | |  | |                    **/
/**  | (___    ___ | |_ | |_  ___  _ __  ___   ( _ )   | |  __   ___ | |_ | |_  ___  _ __  ___   **/
/**   \___ \  / _ \| __|| __|/ _ \| '__|/ __|  / _ \/\ | | |_ | / _ \| __|| __|/ _ \| '__|/ __|  **/
/**   ____) ||  __/| |_ | |_|  __/| |   \__ \ | (_>  < | |__| ||  __/| |_ | |_|  __/| |   \__ \  **/
/**  |_____/  \___| \__| \__|\___||_|   |___/  \___/\/  \_____| \___| \__| \__|\___||_|   |___/  **/
    /**
     *
     **/

    private void initializeInternalBindings() {

        activity.addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                activityBean.setBean(activity.get());
            }
        });

    }

    /************************************************************************************************/


    public Renderable getActivity() {
        return activity.get();
    }

    public void setActivity(Renderable activity) {
        this.activity.set(activity);
    }

    public ObjectProperty<Renderable> activityProperty() {
        return activity;
    }

    public BeanPathAdapter<Renderable> getActivityBean() {
        return activityBean;
    }

    public ObservableList<? extends Renderable> getActivitiesList() {
        return activitiesList.get();
    }

    public synchronized void setActivityList(ObservableList<? super ActivityDTO> activitiesList) {
        this.activitiesList.set((ObservableList<Renderable>) activitiesList);
    }

    /**************************************************************************************************/
/**       ___  _  _               _          ___                       _        _                **/
/**     / ___|| |(_)  ___  _ __  | |_  ___  |_ _| _ __    __ _  _   _ (_) _ __ (_)  ___  ___     **/
/**    | |    | || | / _ \| '_ \ | __|/ __|  | | | '_ \  / _` || | | || || '__|| | / _ \/ __|    **/
/**    | |___ | || ||  __/| | | || |_ \__ \  | | | | | || (_| || |_| || || |   | ||  __/\__ \    **/
/**     \____||_||_| \___||_| |_| \__||___/ |___||_| |_| \__, | \__,_||_||_|   |_| \___||___/    **/
    /**
     * |_|
     **/

    public ListProperty<Renderable> activitiesListProperty() {
        return activitiesList;
    }


    public static void getReport(String cityName, ArrayList<Boolean> checkedBoxes) {

    }


}
