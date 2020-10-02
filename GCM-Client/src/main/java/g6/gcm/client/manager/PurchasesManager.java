package g6.gcm.client.manager;

import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.OneShotDealDTO;
import g6.gcm.core.entity.PurchaseDTO;
import g6.gcm.core.entity.SubscriptionDTO;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.core.interfaces.Renderable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import jfxtras.labs.scene.control.BeanPathAdapter;

/**
 *
 * Keeps track over all kinds of purchases of user .
 */
public class PurchasesManager  {

  private static PurchasesManager onlyInstance = new PurchasesManager();

  private ObjectProperty<Renderable> bean;
  private BeanPathAdapter<Renderable> superBean;
  private ListProperty<Renderable> beansList;

  private PurchasesManager() {
    super();

    // Instantiate & initialize an empty bean as an object property
    bean = new SimpleObjectProperty<>(new SubscriptionDTO());

    // Initialize bean's bean adaptor
    superBean = new BeanPathAdapter<>(new SubscriptionDTO());

    // Initialize subscriptionList list
    beansList = new SimpleListProperty<>(FXCollections.observableArrayList());

    // Initialize Manager's bindings
    initializeInternalBindings();
  }


  public static PurchasesManager getInstance() {
    return onlyInstance;
  }


  private void initializeInternalBindings() {

    bean.addListener((observable, oldValue, newValue) -> {
      if (oldValue != newValue) {
        superBean.setBean(bean.get());
        switch ((ServersCommands) newValue.getRequest()) {
          case SUCCESSFUL_ONESHOTDEAL_CREATION: {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(
                    "your deal is set to city:" + CitiesManager.getInstance().getBean().render());
            alert.show();
            break;
          }
          case SUCCESSFUL_SUBSCRIPTION_CREATION: {
            requestAllSubscriptionsOfUser();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(
                    "subscribed to the city :" + CitiesManager.getInstance().getBean().render());
            alert.show();
            break;
          }
          case SUBSCRIPTION_SUCCESSFULLY_EXTENDED: {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(
                    " renewed subscription to the city :" + CitiesManager.getInstance().getBean()
                            .render());
            alert.show();
            break;
          }
        }
      }
    });
    CitiesManager.getInstance().beanProperty().addListener((observable, oldValue, newValue) -> {
      for (Renderable purchase : getBeansList()) {
        if (((PurchaseDTO) purchase).getCityID() == ((CityDTO) CitiesManager.getInstance()
            .getBean()).getCityID()) {
          setBean(purchase);
          break;
        } else {
          setBean(new PurchaseDTO());
        }
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


  public Renderable getBean() {
    return bean.get();
  }

  public /*TODO: check*/ synchronized void setBean(Renderable bean) {
    this.bean.set(bean);
  }

  public ObjectProperty<Renderable> beanProperty() {
    return bean;
  }

  public BeanPathAdapter<Renderable> getSuperBean() {
    return superBean;
  }

  public ObservableList<? extends Renderable> getBeansList() {
    return beansList.get();
  }

  public void setBeansList(ObservableList<? super Renderable> beansList) {
    this.beansList.set((ObservableList<Renderable>) beansList);
  }

  public ListProperty<Renderable> beansListProperty() {
    return beansList;
  }

/**************************************************************************************************/
/**       ___  _  _               _          ___                       _        _                **/
/**     / ___|| |(_)  ___  _ __  | |_  ___  |_ _| _ __    __ _  _   _ (_) _ __ (_)  ___  ___     **/
/**    | |    | || | / _ \| '_ \ | __|/ __|  | | | '_ \  / _` || | | || || '__|| | / _ \/ __|    **/
/**    | |___ | || ||  __/| | | || |_ \__ \  | | | | | || (_| || |_| || || |   | ||  __/\__ \    **/
/**     \____||_||_| \___||_| |_| \__||___/ |___||_| |_| \__, | \__,_||_||_|   |_| \___||___/    **/
/**                                                         |_|                                  **/
  /************************************************************************************************/


  private synchronized void createPurchase(PurchaseDTO purchaseRequest) {
    purchaseRequest.setCityID(((CityDTO) CitiesManager.getInstance().getBean()).getCityID());
    purchaseRequest.setEmail(((UserDTO) UsersManager.getInstance().getBean()).getEmail());
    GCMClient.send(purchaseRequest);
  }

  public synchronized void subscribeToCity() {

    PurchaseDTO subscriptionRequest = (PurchaseDTO) getBean();
    createPurchase(subscriptionRequest);
    subscriptionRequest.setRequest(ClientsInquiries.SUBSCRIBE_TO_CITY);
    GCMClient.send(subscriptionRequest);

  }

  public void createOneShotDeal() {

    OneShotDealDTO oneShotDealRequest = new OneShotDealDTO();
    createPurchase(oneShotDealRequest);
    oneShotDealRequest.setRequest(ClientsInquiries.ONE_SHOT_DEAL_PURCHASE);
    GCMClient.send(oneShotDealRequest);

  }

  public void extendSubscription() {

    // bean to be renewed is in beanProperty.
    // The user is in UsersManager
    // Date is today
    getBean().setRequest(ClientsInquiries.EXTEND_SUBSCRIPTION);
    GCMClient.send((AbstractDTO) getBean());
  }

  public void requestAllSubscriptionsOfUser() {

    SubscriptionDTO subscriptionsRequest = new SubscriptionDTO();
    subscriptionsRequest.setRequest(ClientsInquiries.ALL_SUBSCRIPTIONS_OF_USER);
    subscriptionsRequest.setEmail(((UserDTO) UsersManager.getInstance().getBean()).getEmail());
    GCMClient.send(subscriptionsRequest);

  }


  public void requestAllCitiesUnderSubscriptionOfUser() {

    PurchaseDTO purchase = new PurchaseDTO();
    purchase.setEmail(((UserDTO) UsersManager.getInstance().getBean()).getEmail());
    purchase.setRequest(ClientsInquiries.ALL_CITIES_SUBSCRIPTIONS_OF_USER);
    GCMClient.send(purchase);
  }
}
