package g6.gcm.client.entity;

import com.jfoenix.controls.JFXButton;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.PurchasesManager;
import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.PurchaseDTO;
import g6.gcm.core.entity.SubscriptionDTO;
import g6.gcm.core.interfaces.Renderable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class PurchaseHistoryRenderingsEngineer {

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

    public static HashMap<Renderable, PurchasesHistoryRendring> refreshCatalogsCitiesView(TilePane PurchasesHistoryTP) {

        List<CityDTO> renderablecities = (List<CityDTO>) CitiesManager.getInstance().getBeansList();
        List<PurchaseDTO> renderablepurchases = (List<PurchaseDTO>) PurchasesManager.getInstance().getBeansList();
        HashMap<Renderable, PurchasesHistoryRendring> purchaseHistoryPreview = new HashMap<>();

        if (!renderablecities.isEmpty()) {
            PurchasesHistoryTP.getChildren().clear();

            VBoxRendering cityRendering;

            for (CityDTO renderableCity : renderablecities) {

                cityRendering = new PurchasesHistoryRendring(renderableCity);

                cityRendering.setEffect(dropShadow);

                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");

                JFXButton subscriptionBTN = new JFXButton();
                cityRendering.designJFXButton(subscriptionBTN);

                Label or = new Label("-OR-");

                JFXButton oneShotDealBTN = new JFXButton();
                cityRendering.designJFXButton(oneShotDealBTN);

                ((PurchasesHistoryRendring) cityRendering).getButtonsVbox().getChildren().addAll(subscriptionBTN, or, oneShotDealBTN);

                Label purchaseTypeLBLValue = new Label();
                ((PurchasesHistoryRendring) cityRendering).getPurchaseTypeHB().getChildren().add((purchaseTypeLBLValue));
                Label purchasedOnLBL = new Label();
                ((PurchasesHistoryRendring) cityRendering).getPurchasedOnHB().getChildren().add((purchasedOnLBL));
                Label purchaseExpiredOnLBL = new Label();
                ((PurchasesHistoryRendring) cityRendering).getPurchaseExpiredOnHB().getChildren().add((purchaseExpiredOnLBL));

                PurchaseHistoryRenderingsEngineer.styleLabels(or, purchasedOnLBL, purchaseExpiredOnLBL, purchaseTypeLBLValue);

                if (!renderablepurchases.isEmpty()) {
                    for (Renderable purchases : renderablepurchases) {
                        if (((PurchaseDTO) purchases).getCityID() == renderableCity.getCityID()) {
                            if (((SubscriptionDTO) purchases).getSubscriptionExpirationDate().equals(null)) {

                                purchaseTypeLBLValue.setText("One Shot Deal");
                                ((PurchasesHistoryRendring) cityRendering).setPurchaseTypeString("One Shot Deal");
                                purchasedOnLBL.setText(DATE_FORMAT.format(((PurchaseDTO) purchases).getPurchaseDate()));
                                purchaseExpiredOnLBL.setText("MM/DD/YYYY");
                                subscriptionBTN.setText("Subscribe");
                                oneShotDealBTN.setText("Get Another Deal");
                            } else {
                                purchaseTypeLBLValue.setText("Subscription");
                                ((PurchasesHistoryRendring) cityRendering).setPurchaseTypeString("Subscription");
                                purchasedOnLBL.setText(DATE_FORMAT.format(((PurchaseDTO) purchases).getPurchaseDate()));
                                purchaseExpiredOnLBL.setText(DATE_FORMAT.format(((SubscriptionDTO) purchases).getSubscriptionExpirationDate()));
                                subscriptionBTN.setText("Renew Subscription");
                                oneShotDealBTN.setText("One Shot Deal");
                            }
                            PurchasesHistoryTP.getChildren().add(cityRendering);
                            purchaseHistoryPreview.put(renderableCity, (PurchasesHistoryRendring) cityRendering);

                        }
                    }
                }
            }
        }
        return purchaseHistoryPreview;
    }

    private static void styleLabels(Label... labels) {
        for (Label label : labels) {
            label.getStyleClass().add("colored-label-white");
            label.getStyleClass().add("label-20");
        }
    }

    public static void catalogMouseClickHandler(String buttonsText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch (buttonsText) {
            case "Subscribe": {
                alert.setContentText("Are you sure you want to buy a subscription?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    PurchasesManager.getInstance().subscribeToCity();
                }
                break;
            }
            case "One Shot Deal": {
                alert.setContentText("Are you sure you want to buy a One Shot Deal?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    PurchasesManager.getInstance().createOneShotDeal();
                }
                break;
            }
            case "Renew Subscription": {
                alert.setContentText("Are you sure you want to renew subscription?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    PurchasesManager.getInstance().extendSubscription();
                }
                break;
            }
            case "Get Another Deal": {
                alert.setContentText("Are you sure you want to get another deal?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    PurchasesManager.getInstance().createOneShotDeal();
                }
                break;
            }
        }
    }
}