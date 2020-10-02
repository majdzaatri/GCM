package g6.gcm.client.manager;

import com.sothawo.mapjfx.MapView;
import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.MapDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.interfaces.Renderable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.labs.scene.control.BeanPathAdapter;

public class MapsManager extends BeanManager {

  private static MapsManager onlyInstance = new MapsManager();

  private final MapDTO DEFAULT_MAP_VIEW = new MapDTO();

  private ListProperty<Renderable> unattachedMaps;

  private MapsManager() {

    resetDefaultMap();

    // Instantiate & initialize an empty bean as an object property
    bean = new SimpleObjectProperty<>(DEFAULT_MAP_VIEW);

    // Initialize bean's bean adaptor
    superBean = new BeanPathAdapter<>(DEFAULT_MAP_VIEW);

    // Initialize maps list
    beansList = new SimpleListProperty<>(FXCollections.emptyObservableList());
    unattachedMaps = new SimpleListProperty<>(FXCollections.emptyObservableList());

    // Initialize Manager's bindings
    initializeInternalBindings();
  }

  public static MapsManager getInstance() {
    return onlyInstance;
  }

  private void resetDefaultMap() {
    DEFAULT_MAP_VIEW.setSiteList(new ArrayList<>());
    DEFAULT_MAP_VIEW.setToursList(new ArrayList<>());
  }

  private void initializeInternalBindings() {

    bean.addListener((observable, oldValue, newValue) -> {
      if (oldValue != newValue) {
        if (newValue == null) {
          resetDefaultMap();
          setBean(DEFAULT_MAP_VIEW);
        }
        ToursManager.getInstance().requestToursOfMap();
        SitesClassificationsManager.getInstance().requestAllSitesClassifications();
        superBean.setBean(bean.get());
        logBeanUpdate();
        // setSelectedBeanCoordinates(((MapDTO) bean.get()).getCoordinates());
      }
    });

    // superBean.bindBidirectional("zoom", this.zoomProperty());

    //
    // bean.addListener((observable, oldValue, newValue) -> {
    //   if (newValue != null && oldValue != newValue) {
    //     superBean.setBean(bean.get());
    //     SitesManager.getInstance().requestSitesOfMap();
    //     ToursManager.getInstance().requestToursOfMap();
    //   }
    // });
  }

/**************************************************************************************************/
/**         _____      _   _                            _____      _   _                         **/
/**        / ____|    | | | |                  ___     / ____|    | | | |                        **/
/**       | |  __  ___| |_| |_ ___ _ __ ___   ( _ )   | (___   ___| |_| |_ ___ _ __ ___          **/
/**       | | |_ |/ _ \ __| __/ _ \ '__/ __|  / _ \/\  \___ \ / _ \ __| __/ _ \ '__/ __|         **/
/**       | |__| |  __/ |_| ||  __/ |  \__ \ | (_>  <  ____) |  __/ |_| ||  __/ |  \__ \         **/
/**        \_____|\___|\__|\__\___|_|  |___/  \___/\/ |_____/ \___|\__|\__\___|_|  |___/         **/
/**                                                                                              **/
  /************************************************************************************************/


  public ObservableList<? extends Renderable> getUnattachedMaps() {
    return unattachedMaps.get();
  }

  public void setUnattachedMaps(ObservableList<? super Renderable> unattachedMaps) {
    this.unattachedMaps.set((ObservableList<Renderable>) unattachedMaps);
  }

  public ListProperty<Renderable> unattachedMapsProperty() {
    return unattachedMaps;
  }

  @Override
  public void setBeansList(ObservableList<? super Renderable> beansList) {

    List list =
        beansList.stream().filter(map -> ((MapDTO) map).getCityID() != 0)
            .collect(Collectors.toList());
    super.setBeansList(FXCollections.observableArrayList(list));
    list =
        (List<Renderable>) beansList.stream().filter(map -> ((MapDTO) map).getCityID() == 0)
            .collect(Collectors.toList());

    setUnattachedMaps(FXCollections.observableArrayList(list));
  }

  /**************************************************************************************************/
/**       ___  _  _               _          ___                       _        _                **/
/**     / ___|| |(_)  ___  _ __  | |_  ___  |_ _| _ __    __ _  _   _ (_) _ __ (_)  ___  ___     **/
/**    | |    | || | / _ \| '_ \ | __|/ __|  | | | '_ \  / _` || | | || || '__|| | / _ \/ __|    **/
/**    | |___ | || ||  __/| | | || |_ \__ \  | | | | | || (_| || |_| || || |   | ||  __/\__ \    **/
/**     \____||_||_| \___||_| |_| \__||___/ |___||_| |_| \__, | \__,_||_||_|   |_| \___||___/    **/
/**                                                         |_|                                  **/
  /************************************************************************************************/

  public void requestAllMaps() {
    MapDTO mapsRequest = new MapDTO();
    mapsRequest.setRequest(ClientsInquiries.ALL_MAPS);
    GCMClient.send(mapsRequest);
  }

  public void requestMapsOfCity() {
    MapDTO mapsRequest = new MapDTO();
    mapsRequest.setRequest(ClientsInquiries.ALL_MAPS_OF_CITY);
    mapsRequest.setCityID(((CityDTO) CitiesManager.getInstance().getBean()).getCityID());
    GCMClient.send(mapsRequest);
  }

  /*********************************************************************************************/
  /*********************************************************************************************/
  /*********************************************************************************************/
  /*********************************************************************************************/


  public void bindMap(MapView mapView) {

  }

  public void requestMapSave() {
    ((MapDTO) getBean()).setCityID(((CityDTO) CitiesManager.getInstance().getBean()).getCityID());
    getBean().setRequest(ClientsInquiries.SAVE_MAP);
  }
}
