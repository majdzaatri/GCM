package g6.gcm.client.manager;

import g6.gcm.core.entity.SiteClassificationDTO;
import g6.gcm.core.entity.SiteDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.interfaces.AbstractDTO;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import jfxtras.labs.scene.control.BeanPathAdapter;

public class SitesClassificationsManager extends BeanManager {

  private static SitesClassificationsManager onlyInstance = new SitesClassificationsManager();


  public SitesClassificationsManager() {

    // Instantiate & initialize an empty bean as an object property
    bean = new SimpleObjectProperty<>(new SiteClassificationDTO());

    // Initialize bean's bean adaptor
    superBean = new BeanPathAdapter<>(bean.get());

    // Initialize maps list
    beansList = new SimpleListProperty<>(FXCollections.emptyObservableList());

    // Initialize Manager's bindings
    initializeInternalBindings();
  }

  public static SitesClassificationsManager getInstance() {
    return onlyInstance;
  }

  private void initializeInternalBindings() {

    bean.addListener((observable, oldValue, newValue) -> {
      if (oldValue != newValue) {
        if (newValue == null) {
          SiteClassificationDTO newCategory = new SiteClassificationDTO();
          newCategory.setCategory("");
          setBean(newCategory);
        }
        superBean.setBean(bean.get());
        ((SiteDTO) SitesManager.getInstance().getBean()).setSiteClassification(
            (SiteClassificationDTO) getBean());
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

  public void requestAllSitesClassifications() {
    bean.get().setRequest(ClientsInquiries.ALL_SITES_CLASSIFICATIONS);
    GCMClient.send((AbstractDTO) bean.get());
  }

}
