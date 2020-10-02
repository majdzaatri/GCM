package g6.gcm.client.manager;

import g6.gcm.core.entity.MapDTO;
import g6.gcm.core.entity.TourDTO;
import g6.gcm.core.factories.ClientsInquiries;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import jfxtras.labs.scene.control.BeanPathAdapter;

public class ToursManager extends BeanManager {

  private static ToursManager onlyInstance = new ToursManager();

  private final TourDTO DEFAULT_TOUR = new TourDTO();

  private ToursManager() {

    // Initialize default tour
    updateDefaultTour();

    // Instantiate & initialize an empty bean as an object property
    bean = new SimpleObjectProperty<>(DEFAULT_TOUR);

    // Initialize bean's bean adaptor
    superBean = new BeanPathAdapter<>(DEFAULT_TOUR);

    // Initialize maps list
    beansList = new SimpleListProperty<>(FXCollections.observableArrayList());

    // Initialize Manager's bindings
    initializeInternalBindings();
  }

  public static ToursManager getInstance() {
    return onlyInstance;
  }

  private void updateDefaultTour() {
    DEFAULT_TOUR.setSites(new ArrayList<>());
    DEFAULT_TOUR.setDescription("");
  }

  protected void initializeInternalBindings() {

    // Bind bean to superBean
    bean.addListener((observable, oldValue, newValue) -> {
      if (oldValue != newValue) {
        if (newValue == null) {
          updateDefaultTour();
          setBean(DEFAULT_TOUR);
        }
        superBean.setBean(bean.get());
        logBeanUpdate();

        // setSelectedBeanCoordinates(((SiteDTO) bean.get()).getCoordinates());
        // SitesClassificationsManager.getInstance().setBean(((SiteDTO) bean.get()).getSiteClassification());
      }
    });

    // superBean.bindBidirectional("siteClassification",
    //     SitesClassificationsManager.getInstance().beanProperty(), Renderable.class);

  }

/**************************************************************************************************/
/**    _____        _    _                               _____        _    _                     **/
/**   / ____|      | |  | |                     ___     / ____|      | |  | |                    **/
/**  | (___    ___ | |_ | |_  ___  _ __  ___   ( _ )   | |  __   ___ | |_ | |_  ___  _ __  ___   **/
/**   \___ \  / _ \| __|| __|/ _ \| '__|/ __|  / _ \/\ | | |_ | / _ \| __|| __|/ _ \| '__|/ __|  **/
/**   ____) ||  __/| |_ | |_|  __/| |   \__ \ | (_>  < | |__| ||  __/| |_ | |_|  __/| |   \__ \  **/
/**  |_____/  \___| \__| \__|\___||_|   |___/  \___/\/  \_____| \___| \__| \__|\___||_|   |___/  **/
/**                                                                                              **/
  /************************************************************************************************/

/**************************************************************************************************/
/**       ___  _  _               _          ___                       _        _                **/
/**     / ___|| |(_)  ___  _ __  | |_  ___  |_ _| _ __    __ _  _   _ (_) _ __ (_)  ___  ___     **/
/**    | |    | || | / _ \| '_ \ | __|/ __|  | | | '_ \  / _` || | | || || '__|| | / _ \/ __|    **/
/**    | |___ | || ||  __/| | | || |_ \__ \  | | | | | || (_| || |_| || || |   | ||  __/\__ \    **/
/**     \____||_||_| \___||_| |_| \__||___/ |___||_| |_| \__, | \__,_||_||_|   |_| \___||___/    **/
/**                                                         |_|                                  **/
  /************************************************************************************************/

  public void getAllTours() {
    TourDTO toursRequest = new TourDTO();
    toursRequest.setRequest(ClientsInquiries.ALL_TOURS);
    GCMClient.send(toursRequest);
  }

  public void requestToursOfMap() {
    TourDTO toursRequest = new TourDTO();
    toursRequest.setRequest(ClientsInquiries.ALL_TOURS_OF_MAP);
    toursRequest.setMapID(((MapDTO) MapsManager.getInstance().getBean()).getMapID());
    GCMClient.send(toursRequest);
  }


}
