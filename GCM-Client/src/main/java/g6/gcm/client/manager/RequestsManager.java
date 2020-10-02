package g6.gcm.client.manager;

import g6.gcm.core.entity.MapDTO;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RequestsManager {

    private static ObjectProperty<MapDTO> mapRequestProperty = new SimpleObjectProperty<MapDTO>();

    private static ListProperty<MapDTO> mapRequestsList = new SimpleListProperty<>(FXCollections.observableArrayList());

    private static ObjectProperty<MapDTO> priceRequestProperty = new SimpleObjectProperty<MapDTO>();

    private static ListProperty<MapDTO> priceRequestsList = new SimpleListProperty<>(FXCollections.observableArrayList());


    /**************************************************************************************************/
/**    _____        _    _                               _____        _    _                     **/
/**   / ____|      | |  | |                     ___     / ____|      | |  | |                    **/
/**  | (___    ___ | |_ | |_  ___  _ __  ___   ( _ )   | |  __   ___ | |_ | |_  ___  _ __  ___   **/
/**   \___ \  / _ \| __|| __|/ _ \| '__|/ __|  / _ \/\ | | |_ | / _ \| __|| __|/ _ \| '__|/ __|  **/
/**   ____) ||  __/| |_ | |_|  __/| |   \__ \ | (_>  < | |__| ||  __/| |_ | |_|  __/| |   \__ \  **/
/**  |_____/  \___| \__| \__|\___||_|   |___/  \___/\/  \_____| \___| \__| \__|\___||_|   |___/  **/
/**                                                                                              **/
    /************************************************************************************************/


    public static MapDTO getMapRequestProperty() {
        return mapRequestProperty.get();
    }

    public static void setMapRequestProperty(MapDTO mapRequestProperty) {
        RequestsManager.mapRequestProperty.set(mapRequestProperty);
    }

    public static ObjectProperty<MapDTO> mapRequestPropertyProperty() {
        return mapRequestProperty;
    }

    public static ObservableList<MapDTO> getMapRequestsList() {
        return mapRequestsList.get();
    }

    public static void setMapRequestsList(ObservableList<MapDTO> mapRequestsList) {
        RequestsManager.mapRequestsList.set(mapRequestsList);
    }

    public static ListProperty<MapDTO> mapRequestsListProperty() {
        return mapRequestsList;
    }

    public static MapDTO getPriceRequestProperty() {
        return priceRequestProperty.get();
    }

    public static void setPriceRequestProperty(MapDTO priceRequestProperty) {
        RequestsManager.priceRequestProperty.set(priceRequestProperty);
    }

    public static ObjectProperty<MapDTO> priceRequestPropertyProperty() {
        return priceRequestProperty;
    }

    public static ObservableList<MapDTO> getPriceRequestsList() {
        return priceRequestsList.get();
    }

    public static void setPriceRequestsList(ObservableList<MapDTO> priceRequestsList) {
        RequestsManager.priceRequestsList.set(priceRequestsList);
    }

    public static ListProperty<MapDTO> priceRequestsListProperty() {
        return priceRequestsList;
    }

}
