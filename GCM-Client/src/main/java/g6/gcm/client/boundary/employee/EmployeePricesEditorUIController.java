package g6.gcm.client.boundary.employee;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import g6.gcm.client.entity.MapsPricesRenddering;
import g6.gcm.client.entity.MapsPricesRenderingsEngineer;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.PriceRequestManager;
import g6.gcm.client.manager.StageManager;
import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.PriceRequestDTO;
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
import java.util.List;
import java.util.Map;

public class EmployeePricesEditorUIController {

    @FXML
    private AnchorPane mainViewAP;

    StageManager stageManager;

    @FXML
    private JFXComboBox<String> searchTypeCB;

    @FXML
    private JFXTextField searchTF;

    @FXML
    private TilePane MapsPricesTP;

    private List<CityDTO> myCitiesList;

    private List<PriceRequestDTO> pricesRequest;

    private HashMap<Renderable, MapsPricesRenddering> cityPreview;

    private ObjectProperty<Request> serverMessage = new SimpleObjectProperty<>();

    /**
     * initializing ComboBox by relevant items and call initializeListeners method
     */
    @FXML
    private void initialize() {

        stageManager = StageManager.getManager();
        stageManager.setMainViewAP(mainViewAP);

        searchTypeCB.getItems().addAll("RELEASED", "PENDING", " ");

        initializeListeners();

    }

    /**
     * initialize listeners for server commands when changes
     * initialize listeners for Cities and pricesRequests List and every time the lists change shows the relevant pop up
     * initialize listeners for search TextField and search ComboBox and every time the value change it filter the VBoxes by the relevant value that has been entered
     */
    public void initializeListeners() {
        PriceRequestManager.getInstance().getSuperBean().bindBidirectional("request", serverMessage, Request.class);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        serverMessage.addListener(((observable, oldValue, newValue) -> {

            if (newValue == ServersCommands.PRICE_REQUEST_DELETED) {
                alert.setContentText("Price request deleted successfully!");
                alert.show();
            } else if (newValue == ServersCommands.PRICE_REQUEST_HAS_BEEN_ADDED) {
                alert.setContentText("Price request has been sent to the company manager");
                alert.show();
            }
        }));

        CitiesManager.getInstance().beansListProperty()
                .addListener((ListChangeListener<? super Renderable>) c -> {
                    cityPreview = MapsPricesRenderingsEngineer.refreshCatalogsCitiesView(MapsPricesTP);
                });

        PriceRequestManager.getInstance().beansListProperty()
                .addListener((ListChangeListener<? super Renderable>) c ->
                        cityPreview = MapsPricesRenderingsEngineer.refreshCatalogsCitiesView(MapsPricesTP));

        searchTF.textProperty().addListener(((observable, oldValue, newValue) -> {
            for (Map.Entry<Renderable, MapsPricesRenddering> entry : cityPreview.entrySet()) {
                if (searchTypeCB.getSelectionModel().getSelectedItem() == null || searchTypeCB.getSelectionModel().getSelectedItem().equals(" ")) {
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
        searchTypeCB.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.equals(" ")) {
                for (Map.Entry<Renderable, MapsPricesRenddering> entry : cityPreview.entrySet()) {
                    if (searchTF.getText().isEmpty()) {
                        if ((entry.getValue().getStatusfilter().equals(newValue))) {
                            entry.getValue().setVisible(true);
                            entry.getValue().setManaged(true);
                        } else {
                            entry.getValue().setVisible(false);
                            entry.getValue().setManaged(false);
                        }
                    }
                }
            } else {
                for (Map.Entry<Renderable, MapsPricesRenddering> entry : cityPreview.entrySet()) {
                    entry.getValue().setVisible(true);
                    entry.getValue().setManaged(true);
                }
            }

        }));

    }

}