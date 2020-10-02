package g6.gcm.client.manager;

import g6.gcm.client.entity.MapBindableManager;
import g6.gcm.core.entity.PriceRequestDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.interfaces.Renderable;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import jfxtras.labs.scene.control.BeanPathAdapter;

import java.sql.Date;
import java.time.LocalDate;

public class PriceRequestManager extends BeanManager implements MapBindableManager {

    private static PriceRequestManager onlyInstance = new PriceRequestManager();

    private final PriceRequestDTO DEFAULT_PRICE = new PriceRequestDTO();

    private PriceRequestManager() {

        // Initialize maps list
        bean = new SimpleObjectProperty<>(DEFAULT_PRICE);

        // Initialize bean's bean adaptor
        superBean = new BeanPathAdapter<>(DEFAULT_PRICE);

        // Initialize maps list
        beansList = new SimpleListProperty<>(FXCollections.emptyObservableList());

        // Initialize Manager's bindings
        initializeInternalBindings();
    }

    public static PriceRequestManager getInstance() {
        return onlyInstance;
    }

    private void initializeInternalBindings() {

        bean.addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {

                superBean.setBean(bean.get());
            }
        });

        beansList.addListener(((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                for (Renderable priceRequest : getBeansList()) {
                    System.out.println(((PriceRequestDTO) priceRequest).getCityName());
                }
            }
        }));

    }

/**************************************************************************************************/
/**    _____        _    _                               _____        _    _                     **/
/**   / ____|      | |  | |                     ___     / ____|      | |  | |                    **/
/**  | (___    ___ | |_ | |_  ___  _ __  ___   ( _ )   | |  __   ___ | |_ | |_  ___  _ __  ___   **/
/**   \___ \  / _ \| __|| __|/ _ \| '__|/ __|  / _ \/\ | | |_ | / _ \| __|| __|/ _ \| '__|/ __|  **/
/**   ____) ||  __/| |_ | |_|  __/| |   \__ \ | (_>  < | |__| ||  __/| |_ | |_|  __/| |   \__ \  **/
/**  |_____/  \___| \__| \__|\___||_|   |___/  \___/\/  \_____| \___| \__| \__|\___||_|   |___/  **/
/**                                                                                              **/
    /**************************************************************************************************/



/**************************************************************************************************/
/**       ___  _  _               _          ___                       _        _                **/
/**     / ___|| |(_)  ___  _ __  | |_  ___  |_ _| _ __    __ _  _   _ (_) _ __ (_)  ___  ___     **/
/**    | |    | || | / _ \| '_ \ | __|/ __|  | | | '_ \  / _` || | | || || '__|| | / _ \/ __|    **/
/**    | |___ | || ||  __/| | | || |_ \__ \  | | | | | || (_| || |_| || || |   | ||  __/\__ \    **/
/**     \____||_||_| \___||_| |_| \__||___/ |___||_| |_| \__, | \__,_||_||_|   |_| \___||___/    **/
/**                                                         |_|                                  **/
    /**************************************************************************************************/


    public void getCityPrices() {
        PriceRequestDTO priceRequest = new PriceRequestDTO();
        priceRequest.setCityName(CitiesManager.getInstance().getBean().render());
        priceRequest.setRequest(ClientsInquiries.CITY_PRICE);
        GCMClient.send(priceRequest);
    }

    public void getAllPricesRequest() {
        PriceRequestDTO priceRequest = new PriceRequestDTO();
        priceRequest.setRequest(ClientsInquiries.GET_ALL_PRICES_REQUEST);
        GCMClient.send(priceRequest);
    }

    public void deletePriceRequest() {
        getBean().setRequest(ClientsInquiries.CANCEL_REQUEST);
        GCMClient.send((PriceRequestDTO) getBean());
    }

    public void addMapsPriceRequest() {
        ((PriceRequestDTO) getBean()).setRequestStatus("PENDING");
        ((PriceRequestDTO) getBean()).setRequestDate(Date.valueOf(LocalDate.now()));
        getBean().setRequest(ClientsInquiries.ADD_MAPS_PRICE_REQUEST);
        GCMClient.send((PriceRequestDTO) getBean());
    }

}
