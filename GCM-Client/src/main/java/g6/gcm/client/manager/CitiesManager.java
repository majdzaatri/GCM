package g6.gcm.client.manager;

import com.sothawo.mapjfx.Coordinate;
import g6.gcm.client.entity.MapBindableManager;
import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.core.interfaces.Renderable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import jfxtras.labs.scene.control.BeanPathAdapter;

public class CitiesManager extends BeanManager implements MapBindableManager {

  private static CitiesManager onlyInstance = new CitiesManager();

  private final CityDTO DEFAULT_CITY_VIEW = new CityDTO();
  private final Coordinate ISRAEL_COORDINATES = new Coordinate(31.494261815532752,
      35.1947021484375);
  private final int DEFAILT_ZOOM = 8;

  private CitiesManager() {

    // Initialize the default city view
    resetDefaultCity();

    // Instantiate & initialize an empty bean as an object property
    bean = new SimpleObjectProperty<>(DEFAULT_CITY_VIEW);

    // Initialize bean's bean adaptor
    superBean = new BeanPathAdapter<>(DEFAULT_CITY_VIEW);

    // Initialize maps list
    beansList = new SimpleListProperty<>(FXCollections.emptyObservableList());

    // Initialize Manager's bindings
    initializeInternalBindings();
  }

  public static CitiesManager getInstance() {
    return onlyInstance;
  }

  private void resetDefaultCity() {
    DEFAULT_CITY_VIEW.setCityName("");
    DEFAULT_CITY_VIEW.setCityID(0);
    DEFAULT_CITY_VIEW.setCityDescription("");
    DEFAULT_CITY_VIEW.setCoordinates(ISRAEL_COORDINATES);
    DEFAULT_CITY_VIEW.setZoom(DEFAILT_ZOOM);
    DEFAULT_CITY_VIEW.setSitesList(new ArrayList<>());
    DEFAULT_CITY_VIEW.setSubscriptionPrice(0);
    DEFAULT_CITY_VIEW.setOneShotDealPrice(0);
    DEFAULT_CITY_VIEW.setMapsList(new ArrayList<>());
    DEFAULT_CITY_VIEW.setRequestDate(Date.valueOf(LocalDate.now()));
    DEFAULT_CITY_VIEW.setRequest(ClientsInquiries.ONE_CITY);
  }

  private void initializeInternalBindings() {

    bean.addListener((observable, oldValue, newValue) -> {
      if (oldValue != newValue) {
        if (newValue == null) {
          resetDefaultCity();
          setBean(DEFAULT_CITY_VIEW);
        } else if (((CityDTO) newValue).getCityName() == null) {
          ((CityDTO) newValue).setCityName("");
        }
        MapsManager.getInstance().requestMapsOfCity();
        SitesManager.getInstance().requestSitesOfCity();
        superBean.setBean(bean.get());
        try {
          setSelectedBeanCoordinates(((CityDTO) bean.get()).getCoordinates());
        } catch (NullPointerException e) {

        }
        logBeanUpdate();
      }
    });

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


  public void requestAllCities() {
    CityDTO citiesRequest = new CityDTO();
    citiesRequest.setRequest(ClientsInquiries.ALL_CITIES);
    GCMClient.send(citiesRequest);
  }

  public void requestOneCity() {
    CityDTO cityRequest = new CityDTO();
    cityRequest.setCityID(10);
    cityRequest.setRequest(ClientsInquiries.ONE_CITY);
    GCMClient.send(cityRequest);
  }


  public void createNewCity() {
    CityDTO newCity = new CityDTO();
    newCity.setCityName("");
    newCity.setCityID(0);
    newCity.setCityDescription("");
    newCity.setCoordinates(ISRAEL_COORDINATES);
    newCity.setZoom(DEFAILT_ZOOM);
    newCity.setSitesList(new ArrayList<>());
    newCity.setSubscriptionPrice(0);
    newCity.setOneShotDealPrice(0);
    newCity.setMapsList(new ArrayList<>());
    newCity.setRequestDate(Date.valueOf(LocalDate.now()));
    getBean().setRequest(ClientsInquiries.CREATE_CITY);
    setBean(newCity);
  }

  @SuppressWarnings("Duplicates")
  public void requestCityCreation() {

    if (((CityDTO) getBean()).getCityID() != 0) {
      getBean().setRequest(ClientsInquiries.UPDATE_CITY);
    } else {
      getBean().setRequest(ClientsInquiries.CREATE_CITY);
    }
    ((CityDTO) getBean())
        .setMapsList(MapsManager.getInstance().getBeansList().stream().map(
            Renderable::render).map(id -> Integer.valueOf(id)).collect(Collectors.toList()));
    // Update this city and its maps
    GCMClient.send((AbstractDTO) getBean());

    //Update the list of maps that got unattached
    resetDefaultCity();
    setBean(DEFAULT_CITY_VIEW);
    getBean().setRequest(ClientsInquiries.UPDATE_CITY);
    ((CityDTO) getBean())
        .setMapsList(MapsManager.getInstance().getUnattachedMaps().stream().map(
            Renderable::render).map(id -> Integer.valueOf(id)).collect(Collectors.toList()));
    GCMClient.send((AbstractDTO) getBean());

  }

}