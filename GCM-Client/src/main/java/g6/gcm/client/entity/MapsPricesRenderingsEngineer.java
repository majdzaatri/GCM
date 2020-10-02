package g6.gcm.client.entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.NotificationsManager;
import g6.gcm.client.manager.PriceRequestManager;
import g6.gcm.client.manager.UsersManager;
import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.NotificationDTO;
import g6.gcm.core.entity.PriceRequestDTO;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.interfaces.Renderable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * @class MapsPricesRenderingsEngineer responsible for refreshing the vbox by the relevant and updated data
 */

public class MapsPricesRenderingsEngineer {

    private static DropShadow dropShadow = new DropShadow();

    /**
     * Initialize DropShadow effect
     */
    static {
        dropShadow.setRadius(10);
        dropShadow.setWidth(21);
        dropShadow.setHeight(21);
        dropShadow.setOffsetX(-2);
        dropShadow.setOffsetY(4);
        dropShadow.setColor(Color.BLACK);
    }

    /**
     * Returns a HashMap updated by the current Cities and PriceRequest BeansList and we call this method when the Cities or PricesRequest list's change
     * and every time this method invoke it build all the VBoxes from the start.
     * The method build the VBoxes by calling the MapsPricesRenddering and from this method it update the VBox by the relevant data about the specific city,
     * so if the city was in PENDING status it will send request for PriceRequest to get all the relevant data from there unless it was RELEASED it get the data from @param renderableCity
     *
     * @param citiesTP to load the VBoxes into it
     * @return cityPreview and its HashMap Type
     */
    public static HashMap<Renderable, MapsPricesRenddering> refreshCatalogsCitiesView(TilePane citiesTP) {

        List<CityDTO> renderablecities = (List<CityDTO>) CitiesManager.getInstance().getBeansList();
        List<PriceRequestDTO> renderablePricesRequest = (List<PriceRequestDTO>) PriceRequestManager.getInstance().getBeansList();
        HashMap<Renderable, MapsPricesRenddering> cityPreview = new HashMap<>();

        if (!renderablecities.isEmpty()) {
            citiesTP.getChildren().clear();

            VBoxRendering cityRendering;

            for (CityDTO renderableCity : renderablecities) {

                cityRendering = new MapsPricesRenddering(renderableCity);

                cityRendering.setEffect(dropShadow);

                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");

                JFXButton requestReleaseApprovalBTN = new JFXButton();
                cityRendering.designJFXButton(requestReleaseApprovalBTN);
                ((MapsPricesRenddering) cityRendering).getButtonHB().getChildren().add(requestReleaseApprovalBTN);

                Label requestStatus = new Label();
                Label requestDate = new Label();
                JFXTextField subscriptionPriceTF = new JFXTextField();
                subscriptionPriceTF.setPrefWidth(50);
                subscriptionPriceTF.setPrefHeight(20);
                JFXTextField oneShotDealPriceTF = new JFXTextField();
                oneShotDealPriceTF.setPrefWidth(50);
                oneShotDealPriceTF.setPrefHeight(20);


                if (!renderablePricesRequest.isEmpty()) {
                    for (Renderable priceRequest : renderablePricesRequest) {
                        if (((PriceRequestDTO) priceRequest).getCityName().equals(renderableCity.getCityName())) {
                            requestStatus.setStyle("\t-fx-font-family: \"Eras Demi ITC\";\n" +
                                    "\t-fx-font-size: 20px;\n" +
                                    "\t-fx-text-fill:red;");
                            requestStatus.setText("PENDING");
                            ((MapsPricesRenddering) cityRendering).setStatusfilter("PENDING");
                            requestDate.setStyle("");
                            requestDate.setText(DATE_FORMAT.format(((PriceRequestDTO) priceRequest).getRequestDate()));
                            ((MapsPricesRenddering) cityRendering).getSubscriptionPriceTF().setText(String.valueOf(((PriceRequestDTO) priceRequest).getSubscribtionPrice()));
                            ((MapsPricesRenddering) cityRendering).getSubscriptionPriceTF().setStyle("-fx-text-fill:red;");
                            ((MapsPricesRenddering) cityRendering).getSubscriptionPriceTF().setDisable(true);
                            ((MapsPricesRenddering) cityRendering).getOneShotDealTF().setText(String.valueOf(((PriceRequestDTO) priceRequest).getOneShotDealPrice()));
                            ((MapsPricesRenddering) cityRendering).getOneShotDealTF().setStyle("-fx-text-fill:red;");
                            ((MapsPricesRenddering) cityRendering).getOneShotDealTF().setDisable(true);
                            requestReleaseApprovalBTN.setText("Cancel Request");
                            break;
                        } else {
                            requestStatus.setStyle("-fx-font-family: \"Eras Demi ITC\";\n" +
                                    "\t-fx-font-size: 20px;\n" +
                                    "\t-fx-text-fill:white;");
                            requestStatus.setText("RELEASED");
                            ((MapsPricesRenddering) cityRendering).setStatusfilter("RELEASED");
                            //TODO: Get request date for the specific cities (Build a statement)
//                            requestDate.setText(String.valueOf(((PriceRequestDTO) priceRequest).getRequestDate()));
                            if (String.valueOf(renderableCity.getRequestDate()).equals("null")) {
                                requestDate.setWrapText(true);
                                requestDate.setText("DD-MM-YYYY");
                                requestDate.setStyle("-fx-font-size:15px;");
                            } else {
                                requestDate.setText(DATE_FORMAT.format(renderableCity.getRequestDate()));
                                requestDate.setStyle("");
                            }

                            ((MapsPricesRenddering) cityRendering).getSubscriptionPriceTF().setText(String.valueOf(renderableCity.getSubscriptionPrice()));
                            ((MapsPricesRenddering) cityRendering).getSubscriptionPriceTF().setStyle("-fx-text-fill:white;");
                            ((MapsPricesRenddering) cityRendering).getOneShotDealTF().setText(String.valueOf(renderableCity.getOneShotDealPrice()));
                            ((MapsPricesRenddering) cityRendering).getOneShotDealTF().setStyle("-fx-text-fill:white;");

                            requestReleaseApprovalBTN.setText("Request Release Approval");
                        }
                    }
                } else {
                    requestStatus.setStyle("-fx-font-family: \"Eras Demi ITC\";\n" +
                            "\t-fx-font-size: 20px;\n" +
                            "\t-fx-text-fill:white;");
                    requestStatus.setText("RELEASED");
                    ((MapsPricesRenddering) cityRendering).setStatusfilter("RELEASED");
                    //TODO: Get request date for the specific cities (Build a statement)
                    requestDate.setStyle("");
                    requestDate.setText(DATE_FORMAT.format(renderableCity.getRequestDate()));
                    ((MapsPricesRenddering) cityRendering).getSubscriptionPriceTF().setText(String.valueOf(renderableCity.getSubscriptionPrice()));
                    ((MapsPricesRenddering) cityRendering).getSubscriptionPriceTF().setStyle("-fx-text-fill:white;");
                    ((MapsPricesRenddering) cityRendering).getOneShotDealTF().setText(String.valueOf(renderableCity.getOneShotDealPrice()));
                    ((MapsPricesRenddering) cityRendering).getOneShotDealTF().setStyle("-fx-text-fill:white;");

                    requestReleaseApprovalBTN.setText("Request Release Approval");
                }
                ((MapsPricesRenddering) cityRendering).getRequestStatusHB().getChildren().addAll(requestStatus);
                ((MapsPricesRenddering) cityRendering).getRequestDateHB().getChildren().addAll(requestDate);
                ((MapsPricesRenddering) cityRendering).getSubscriptionPriceHB().getChildren().addAll(subscriptionPriceTF);
                ((MapsPricesRenddering) cityRendering).getOneShotDealHB().getChildren().addAll(oneShotDealPriceTF);

                citiesTP.getChildren().add(cityRendering);
                cityPreview.put(renderableCity, (MapsPricesRenddering) cityRendering);
            }
        }
        return cityPreview;
    }

    /**
     * This method gets String of the button that has been clicked and if it was "Cancel Request" it will send request to delete priceRequest and update the list of priceRequest
     * to refresh the tilePane's VBoxes, otherwise if it was "Request release Approval" it will send request to add a new request to priceRequest and then it will update the list
     * of priceRequests too.
     *
     * @param buttonsText to detect the button that has been clicked and to go for the relevant case
     */

    public static void catalogMouseClickHandler(String buttonsText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch (buttonsText) {
            case "Cancel Request": {
                alert.setContentText("Are you sure you want to cancel this request?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    PriceRequestManager.getInstance().deletePriceRequest();
                    PriceRequestManager.getInstance().getAllPricesRequest();
                    NotificationDTO notification = new NotificationDTO();
                    notification.setUpdate(((CityDTO) (CitiesManager.getInstance().getBean())).getCityID());
                    NotificationsManager.setNotificationprop(notification);
                    NotificationsManager.setNotificationprop(notification);
                    NotificationsManager.deleteNotification();
                    System.out.println("Price Request Canceled Clicked");
                }
                break;
            }
            case "Request Release Approval": {
                alert.setContentText("Are you sure you want to send request?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    NotificationDTO notification = new NotificationDTO();
                    PriceRequestManager.getInstance().addMapsPriceRequest();
                    PriceRequestManager.getInstance().getAllPricesRequest();
                    notification.setFromUsername(
                        ((UserDTO) UsersManager.getInstance().getBean()).getEmail());
                    notification.setToUsername("companymanager@gmail.com");
                    notification.setDateOfNotifying(Date.valueOf(LocalDate.now()));
                    notification.setNotificationType("CHANGE_PRICE");
                    notification.setNotification("You have received a new price request");
                    notification.setUpdate(((CityDTO) (CitiesManager.getInstance().getBean())).getCityID());
                    NotificationsManager.setNotificationprop(notification);
                    NotificationsManager.addPriceRequestNotification();
                    System.out.println("Request Release Approval Clicked");
                }
                break;
            }
        }
    }
}
