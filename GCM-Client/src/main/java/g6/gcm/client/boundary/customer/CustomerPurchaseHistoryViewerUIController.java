package g6.gcm.client.boundary.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import g6.gcm.client.entity.PurchaseHistoryRenderingsEngineer;
import g6.gcm.client.entity.PurchasesHistoryRendring;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.PurchasesManager;
import g6.gcm.client.manager.StageManager;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.Renderable;
import g6.gcm.core.interfaces.Request;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.util.HashMap;
import java.util.Map;

public class CustomerPurchaseHistoryViewerUIController {

    StageManager stageManager;
    @FXML
    private AnchorPane mainViewAP;

    @FXML
    private JFXTextField searchTF;

    @FXML
    private JFXComboBox<String> searchCB;

    @FXML
    private TilePane PurchasesHistoryTP;

    private HashMap<Renderable, PurchasesHistoryRendring> cityPreview;

    private ObjectProperty<Request> serverMessage = new SimpleObjectProperty<>();


    @FXML
    private void initialize() {
        stageManager = StageManager.getManager();
//        CitiesManager.getInstance().requestOneCity();
        initializeListeners();

        searchCB.getItems().addAll("One Shot Deal", "Subscription", " ");
    }


    private void initializeListeners() {


        //initializing listner for searchTF and searchTypeCB to filter the VBoxes
        searchTF.textProperty().addListener(((observable, oldValue, newValue) -> {
            for (Map.Entry<Renderable, PurchasesHistoryRendring> entry : cityPreview.entrySet()) {
                if (searchCB.getSelectionModel().getSelectedItem() == null || searchCB.getSelectionModel().getSelectedItem().equals(" ")) {
                    if ((entry.getKey().render().toLowerCase().startsWith((newValue.toLowerCase())))) {
                        entry.getValue().setVisible(true);
                        entry.getValue().setManaged(true);
                    } else {
                        entry.getValue().setVisible(false);
                        entry.getValue().setManaged(false);
                    }
                }
            }
        }));


        searchCB.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.equals(" ")) {
                for (Map.Entry<Renderable, PurchasesHistoryRendring> entry : cityPreview.entrySet()) {
                    if (searchTF.getText().isEmpty()) {
                        if ((entry.getValue().getPurchaseTypeString().equals(newValue))) {
                            entry.getValue().setVisible(true);
                            entry.getValue().setManaged(true);
                        } else {
                            entry.getValue().setVisible(false);
                            entry.getValue().setManaged(false);
                        }
                    }
                }
            } else {
                for (Map.Entry<Renderable, PurchasesHistoryRendring> entry : cityPreview.entrySet()) {
                    entry.getValue().setVisible(true);
                    entry.getValue().setManaged(true);
                }
            }

        }));

        //listeners for cities and purchases lists to change the VBoxes according to the changes that happens to the relevant list
        CitiesManager.getInstance().beansListProperty()
                .addListener((ListChangeListener<? super Renderable>) c ->
                        cityPreview = PurchaseHistoryRenderingsEngineer.refreshCatalogsCitiesView(PurchasesHistoryTP));

        PurchasesManager.getInstance().beansListProperty()
                .addListener((ListChangeListener<? super Renderable>) c ->
                        cityPreview = PurchaseHistoryRenderingsEngineer.refreshCatalogsCitiesView(PurchasesHistoryTP));


        //listeners for server messages that return from the server and in each request(command) it shows the relevant alert
        PurchasesManager.getInstance().beanProperty()
                .addListener((observable, oldValue, newValue) -> {

                    switch ((ServersCommands) newValue.getRequest()) {

                        case SUCCESSFUL_ONESHOTDEAL_CREATION: {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText(
                                    "your deal is set to city:" + CitiesManager.getInstance().getBean().render());
                            alert.show();
                            break;
                        }
                        case SUCCESSFUL_SUBSCRIPTION_CREATION: {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText(
                                    "subscribed to the city :" + CitiesManager.getInstance().getBean().render());
                            alert.show();
                            break;
                        }
                        case SUBSCRIPTION_SUCCESSFULLY_EXTENDED: {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText(
                                    " renewed subscription to the city :" + CitiesManager.getInstance().getBean()
                                            .render());
                            alert.show();
                            break;
                        }
                        case FAILED_SUBSCRIPTION_CREATION: {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Faild to subscribe to:" + CitiesManager.getInstance().getBean()
                                    .render());
                            alert.show();
                            break;
                        }
                        case SUBSCRIPTION_NOT_EXTENDED: {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Faild to extend the subscription for:" + CitiesManager.getInstance().getBean()
                                    .render());
                            alert.show();
                            break;
                        }
                        case FAILED_ONESHOTDEAL_CREATION: {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Faild to get one shot deal for:" + CitiesManager.getInstance().getBean()
                                    .render());
                            alert.show();
                            break;
                        }
                    }
                });
    }


}