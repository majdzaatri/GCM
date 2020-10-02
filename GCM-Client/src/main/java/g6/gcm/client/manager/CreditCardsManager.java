package g6.gcm.client.manager;

import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.CreditCardDTO;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ClientsInquiries;
import java.sql.Date;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import jfxtras.labs.scene.control.BeanPathAdapter;

import java.time.LocalDate;

/**
 * Creates credit cards and manages user credit card information
 */

public class CreditCardsManager extends BeanManager {

    private static CreditCardsManager onlyInstance = new CreditCardsManager();

    private final CreditCardDTO DEFAULT_CREDIT_CARD = new CreditCardDTO();

    private CreditCardsManager() {
        super();

        //Initialize default credit card

        DEFAULT_CREDIT_CARD.setAccountEmail("");
        DEFAULT_CREDIT_CARD.setCardholdersName("");
        DEFAULT_CREDIT_CARD.setCreditCardNumber("");
        DEFAULT_CREDIT_CARD.setCreditCardCSV("");
        DEFAULT_CREDIT_CARD.setCreditCardExpirationDate(Date.valueOf(LocalDate.now()));

        // Instantiate & initialize an empty bean as an object property
        bean = new SimpleObjectProperty<>(new CreditCardDTO());

        // Initialize bean's bean adaptor
        superBean = new BeanPathAdapter<>(new CreditCardDTO());

        // Initialize maps list
        beansList = new SimpleListProperty<>(FXCollections.observableArrayList());

        // Initialize Manager's bindings
        initializeInternalBindings();
    }

    public static CreditCardsManager getInstance() {
        return onlyInstance;
    }

    public static void createCC() {

        CreditCardDTO creditCardRequest = new CreditCardDTO();
        creditCardRequest.setAccountEmail(
            ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).getAccountEmail());
        creditCardRequest.setCreditCardNumber(
            ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).getCreditCardNumber());
        creditCardRequest.setCardholdersID(
            ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).getCardholdersID());
        creditCardRequest.setCreditCardCSV(
            ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).getCreditCardCSV());
        creditCardRequest.setCardholdersName(
            ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).getCardholdersName());
        creditCardRequest.setCreditCardExpirationDate(
            ((CreditCardDTO) CreditCardsManager.getInstance().getBean())
                .getCreditCardExpirationDate());
        creditCardRequest.setRequest(ClientsInquiries.INSERT_CREDIT_CARD);
        GCMClient.send(creditCardRequest);
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

    public static void getCreditCardByEmail() {
        CreditCardDTO creditCard = new CreditCardDTO();
      creditCard.setAccountEmail(((UserDTO) UsersManager.getInstance().getBean()).getEmail());
        creditCard.setRequest(ClientsInquiries.ONE_CREDIT_CARD);
        GCMClient.send(creditCard);
    }

    public static void deleteCreditCard() {
        CreditCardsManager.getInstance().getBean()
            .setRequest((ClientsInquiries.DELETE_CREDIT_CARD));
        GCMClient.send((CreditCardDTO) CreditCardsManager.getInstance().getBean());
    }

    private void initializeInternalBindings() {

        // Bind bean to superBean
        bean.addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (newValue == null) {
                    setBean(DEFAULT_CREDIT_CARD);
                }
            } else {
                getSuperBean().setBean(getBean());
            }
        });
    }

}
