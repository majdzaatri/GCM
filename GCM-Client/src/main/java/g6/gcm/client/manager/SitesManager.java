package g6.gcm.client.manager;

import com.sothawo.mapjfx.Coordinate;
import g6.gcm.client.entity.MapBindableManager;
import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.MapDTO;
import g6.gcm.core.entity.SiteClassificationDTO;
import g6.gcm.core.entity.SiteDTO;
import g6.gcm.core.entity.TourDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.interfaces.AbstractDTO;
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

public class SitesManager extends BeanManager implements MapBindableManager {

  private static SitesManager onlyInstance = new SitesManager();
  private final SiteDTO DEFAULT_SITE = new SiteDTO();

  private ListProperty<Renderable> sitesOfSelectedMap;
  private ListProperty<Renderable> sitesOfSelectedTour;

  private SitesManager() {

    // Initialize the default city view
    resetDefaultSite();

    // Instantiate & initialize an empty bean as an object property
    bean = new SimpleObjectProperty<>(DEFAULT_SITE);

    // Initialize bean's bean adaptor
    superBean = new BeanPathAdapter<>(DEFAULT_SITE);

    // Initialize maps list
    beansList = new SimpleListProperty<>(FXCollections.observableArrayList());

    sitesOfSelectedMap = new SimpleListProperty<>(FXCollections.emptyObservableList());
    sitesOfSelectedTour = new SimpleListProperty<>(FXCollections.emptyObservableList());

    // Initialize Manager's bindings
    initializeInternalBindings();
  }

  public static SitesManager getInstance() {
    return onlyInstance;
  }

  private void resetDefaultSite() {
    DEFAULT_SITE.setSiteName("New Site");
    DEFAULT_SITE.setDescription("");
    DEFAULT_SITE.setRecommendedVisitDuration("");
    DEFAULT_SITE.setAccessible(false);
    DEFAULT_SITE.setCoordinates(CitiesManager.getInstance().getSelectedBeanCoordinates());
    DEFAULT_SITE.setCityID(((CityDTO) CitiesManager.getInstance().getBean()).getCityID());
    DEFAULT_SITE.setMapID(((MapDTO) MapsManager.getInstance().getBean()).getMapID());
    DEFAULT_SITE.setSiteClassification(
        ((SiteClassificationDTO) SitesClassificationsManager.getInstance().getBean()));
  }

  protected void initializeInternalBindings() {

    // Bind bean to superBean
    bean.addListener((observable, oldValue, newValue) -> {
      if (oldValue != newValue) {
        if (newValue == null) {
          resetDefaultSite();
          setBean(DEFAULT_SITE);
        }
        superBean.setBean(bean.get());
        setSelectedBeanCoordinates(((SiteDTO) bean.get()).getCoordinates());
        SitesClassificationsManager.getInstance()
            .setBean(((SiteDTO) bean.get()).getSiteClassification());
        logBeanUpdate();

      }
    });

    MapsManager.getInstance().beanProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != oldValue) {
        if (newValue != null) {
          List<SiteDTO> sitesList = new ArrayList<>();
          List<String> siteNamesList = ((MapDTO) MapsManager.getInstance().getBean()).getSiteList();

          for (Renderable site : getBeansList()) {
            if (siteNamesList.contains(((SiteDTO) site).getSiteName())) {
              sitesList.add((SiteDTO) site);
            }
          }
          if (sitesList.isEmpty()) {
            setSitesOfSelectedMap(FXCollections.emptyObservableList());
          } else {
            setSitesOfSelectedMap(FXCollections.observableArrayList(sitesList));
          }
        }
      }
    });

    ToursManager.getInstance().beanProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != oldValue) {
        if (newValue != null) {
          List<SiteDTO> sitesList = new ArrayList<>();
          List<String> siteNamesList = ((TourDTO) ToursManager.getInstance().getBean()).getSites();

          for (Renderable site : getBeansList()) {
            if (siteNamesList.contains(((SiteDTO) site).getSiteName())) {
              sitesList.add((SiteDTO) site);
            }
          }
          if (sitesList.isEmpty()) {
            setSitesOfSelectedTour(FXCollections.emptyObservableList());
          } else {
            setSitesOfSelectedTour(FXCollections.observableArrayList(sitesList));
          }
        }
      }
    });

    // MapsManager.getInstance().beanProperty().addListener((observable, oldValue, newValue) -> {
    //   if (newValue != oldValue) {
    //     if (newValue != null) {
    //       setSitesOfSelectedMap(FXCollections.observableArrayList(beansList.stream()
    //           .filter(site -> ((MapDTO) MapsManager.getInstance().getBean()).getSiteList().stream()
    //               .anyMatch(siteName ->
    //                   ((SiteDTO) site).getSiteName().equals(siteName)))
    //           .collect(Collectors.toList())));
    //     } else {
    //       setSitesOfSelectedMap(FXCollections.emptyObservableList());
    //     }
    //   }
    // });
    //

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

  public List<? extends Renderable> getSitesCategoriesList() {
    return getBeansList().stream().map(site -> ((SiteDTO) site).getSiteClassification()).distinct()
        .collect(Collectors.toList());
  }

  public Iterable<? extends Renderable> getSitesPerCategory(String category) {
    return getBeansList().stream()
        .filter(site -> ((SiteDTO) site).getSiteClassification().equals(category))
        .collect(Collectors.toList());
  }

  public ObservableList<? extends Renderable> getSitesOfSelectedTour() {
    return sitesOfSelectedTour.get();
  }

  public void setSitesOfSelectedTour(ObservableList<? super Renderable> sitesOfSelectedTour) {
    this.sitesOfSelectedTour.set((ObservableList<Renderable>) sitesOfSelectedTour);
  }

  public ListProperty<Renderable> sitesOfSelectedTourProperty() {
    return sitesOfSelectedTour;
  }

  public ObservableList<? extends Renderable> getSitesOfSelectedMap() {
    return sitesOfSelectedMap.get();
  }

  public void setSitesOfSelectedMap(ObservableList<? super Renderable> sitesOfSelectedMap) {
    this.sitesOfSelectedMap.set((ObservableList<Renderable>) sitesOfSelectedMap);
  }

  public ListProperty<Renderable> sitesOfSelectedMapProperty() {
    return sitesOfSelectedMap;
  }

  /**************************************************************************************************/
/**       ___  _  _               _          ___                       _        _                **/
/**     / ___|| |(_)  ___  _ __  | |_  ___  |_ _| _ __    __ _  _   _ (_) _ __ (_)  ___  ___     **/
/**    | |    | || | / _ \| '_ \ | __|/ __|  | | | '_ \  / _` || | | || || '__|| | / _ \/ __|    **/
/**    | |___ | || ||  __/| | | || |_ \__ \  | | | | | || (_| || |_| || || |   | ||  __/\__ \    **/
/**     \____||_||_| \___||_| |_| \__||___/ |___||_| |_| \__, | \__,_||_||_|   |_| \___||___/    **/
/**                                                         |_|                                  **/

  /************************************************************************************************/


  public void requestAllSites() {
    SiteDTO sitesRequest = new SiteDTO();
    sitesRequest.setRequest(ClientsInquiries.ALL_SITES);
    GCMClient.send(sitesRequest);
  }

  // public void requestSitesOfMap() {
  //   SiteDTO sitesRequest = new SiteDTO();
  //   sitesRequest.setRequest(ClientsInquiries.ALL_SITES_OF_MAP);
  //   sitesRequest.setMapID(((MapDTO) MapsManager.getInstance().getBean()).getMapID());
  //   GCMClient.send(sitesRequest);
  // }

  public void requestSitesOfCity() {
    SiteDTO sitesRequest = new SiteDTO();
    sitesRequest.setRequest(ClientsInquiries.ALL_SITES_OF_CITY);
    sitesRequest.setCityID(((CityDTO) CitiesManager.getInstance().getBean()).getCityID());
    GCMClient.send(sitesRequest);
  }

  public List<Coordinate> getSitesCoordinates() {
    return beansList.stream().map(site -> ((SiteDTO) site).getCoordinates()).collect(
        Collectors.toList());
  }

  public void createNewSite() {
    SiteDTO newSite = new SiteDTO();
    newSite.setCoordinates(getSelectedBeanCoordinates());
    newSite.setCityID(((CityDTO) CitiesManager.getInstance().getBean()).getCityID());
    newSite.setMapID(((MapDTO) MapsManager.getInstance().getBean()).getMapID());

    newSite.setSiteName("");
    newSite.setDescription("");
    newSite.setRecommendedVisitDuration("");
    newSite.setSiteClassification(new SiteClassificationDTO());
    newSite.setAccessible(false);
    setBean(newSite);
  }

  public List<Coordinate> getSitesOfTour(TourDTO renderable) {
    return beansList.stream().filter(site -> renderable.getSites().stream()
        .allMatch(siteName -> siteName.equals(((SiteDTO) site).getSiteName())))
        .map(site -> ((SiteDTO) site).getCoordinates()).collect(Collectors.toList());
  }

  public void requestSiteCreation() {
    ((SiteDTO) getBean()).setMapID(((CityDTO) CitiesManager.getInstance().getBean()).getCityID());
    getBean().setRequest(ClientsInquiries.CREATE_SITE);
    GCMClient.send((AbstractDTO) getBean());
  }

  public void requestSiteDeletion() {
    ((SiteDTO) getBean()).setMapID(((CityDTO) CitiesManager.getInstance().getBean()).getCityID());
    getBean().setRequest(ClientsInquiries.DELETE_SITE);
    GCMClient.send((AbstractDTO) getBean());
  }

  /*********************************************************************************************/
  /*********************************************************************************************/
  /*********************************************************************************************/
  /*********************************************************************************************/


}