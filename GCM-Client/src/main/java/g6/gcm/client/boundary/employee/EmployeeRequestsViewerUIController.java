package g6.gcm.client.boundary.employee;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import g6.gcm.client.entity.RenderingsEngineer;
import g6.gcm.client.manager.ActivityManager;
import g6.gcm.client.manager.GCMClient;
import g6.gcm.client.manager.MapsManager;
import g6.gcm.client.manager.PriceRequestManager;
import g6.gcm.core.entity.MapDTO;
import g6.gcm.core.entity.PriceRequestDTO;
import g6.gcm.core.factories.ClientsInquiries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

//import java.awt.*;

public class EmployeeRequestsViewerUIController {


    @FXML
    private ComboBox<String> requestTypeCB;


    @FXML
    private TilePane myTile;

    @FXML
    private ScrollPane myScrollPane;

    @FXML
    private void initialize() {
        requestTypeCB.getItems().add("Price Requests");
        requestTypeCB.getItems().add("Maps Requests");

        ActivityManager.getActivities();


        MapDTO map = new MapDTO();
        map.setRequest(ClientsInquiries.ALL_MAPS_REQUEST);
        GCMClient.send(map);

        PriceRequestDTO priceRequest = new PriceRequestDTO();
        priceRequest.setRequest(ClientsInquiries.GET_ALL_PRICES_REQUEST);
        GCMClient.send(priceRequest);

/*
        PriceRequestManager.getInstance().beansListProperty().addListener(((observable, oldValue, newValue) -> {
            RenderingsEngineer.PricesRequestsBuilder(myTile);
        }));
*/

       /* try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RenderingsEngineer.RequestsBuilder(myTile);*/


  /*      MapsManager.getInstance().beansListProperty().addListener((observable, oldValue, newValue) ->
        {
            RenderingsEngineer.MapsRequestsBuilder(myTile);
        });*/

    }

    @FXML
    void CBSelected(ActionEvent event) {

        switch (requestTypeCB.getValue())
        {
            case "Maps Requests":
                System.out.println();
            RenderingsEngineer.MapsRequestsBuilder(myTile);

                        break;
            case "Price Requests":
                    RenderingsEngineer.PricesRequestsBuilder(myTile);
                break;
        }
    }

}
