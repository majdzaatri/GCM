package g6.gcm.client.manager;

import com.sothawo.mapjfx.MapView;
import g6.gcm.client.entity.MapBindableManager;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javafx.beans.value.ChangeListener;


public class MapBindingsManager {

  private static MapBindingsManager ourInstance = new MapBindingsManager();

  private static MapView currentMapView;
  private ConcurrentHashMap<MapBindableManager, ChangeListener> managers;
  private ConcurrentHashMap<MapBindableManager, MapView> boundManagers;

  private MapBindingsManager() {
    managers = new ConcurrentHashMap<>();
    boundManagers = new ConcurrentHashMap<>();
  }

  public static MapView getCurrentMapView() {
    return currentMapView;
  }

  public static void setCurrentMapView(MapView currentMapView) {
    MapBindingsManager.currentMapView = currentMapView;
  }

  public static MapBindingsManager getInstance() {
    return ourInstance;
  }

  public static void closeMap() {
    if (currentMapView != null) {
      currentMapView.close();
    }
  }

  public static void initializeMap() {
    if (!currentMapView.getInitialized()) {
      currentMapView.initialize();
    }
  }

  /**
   * Binds MapView's zoom and center properties to the passed manager's selected bean info. This
   * method iterates over other already bound managers and unbinds them before creating the
   * requested binding.
   *
   * @param mapView is the current MapView to be bound.
   * @param toThisManager is the manager to be bound.
   */
  public void bindThis(MapView mapView, MapBindableManager toThisManager) {

    // TODO add a check if it is already bound?

    // Remove all listeners if there are any.
    for (Entry<MapBindableManager, MapView> entry : boundManagers.entrySet()) {
      MapBindableManager thatManager = entry.getKey();
      if (entry.getValue().equals(mapView)) {
        (((BeanManager) toThisManager).beanProperty())
            .removeListener(managers.get(entry.getKey()));
        // entry.getKey().zoomProperty().unbindBidirectional(mapView.zoomProperty());
        // mapView.zoomProperty().unbindBidirectional(entry.getKey());
        if (!(thatManager instanceof SitesManager)) {
          ((BeanManager) entry.getKey()).getSuperBean()
              .unBindBidirectional("zoom", mapView.zoomProperty());
        }
      }
      // if (thatManager != toThisManager) {
      //   boundManagers.remove(thatManager);
      // }
    }

    // Activate manager & create listener if it hasn't been activated yet.
    activateManager(toThisManager);
    if (!boundManagers.containsKey(toThisManager)) {
      boundManagers.put(toThisManager, mapView);
      ((BeanManager) toThisManager).beanProperty().addListener(managers.get(toThisManager));
    }
    if (!(toThisManager instanceof SitesManager)) {
      ((BeanManager) toThisManager).getSuperBean()
          .bindBidirectional("zoom", mapView.zoomProperty());
    }

    // if (!boundManagers.containsKey(toThisManager)) {
    //   // Add a listener.
    //   // toThisManager.zoomProperty().bindBidirectional(mapView.zoomProperty());
    //   // mapView.zoomProperty().bindBidirectional(toThisManager.);
    //
    //   // mapView.zoomProperty().bindBidirectional(toThisManager.zoomProperty());
    // }

  }

  /**
   * Adds passed {@see MapBindableManager} to the active BindableManagers list along with it's new
   * ChangeListener to be added and removed when needed.
   *
   * @param mapBindableManager the manager to be added.
   */
  public void activateManager(MapBindableManager mapBindableManager) {
    if (!managers.containsKey(mapBindableManager)) {
      managers.put(mapBindableManager, (observable, oldValue, newValue) -> {
        boundManagers.get(mapBindableManager)
            .setCenter(mapBindableManager.getSelectedBeanCoordinates());
      });

    }
  }

}
