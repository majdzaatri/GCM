package g6.gcm.client.boundary.customer;

import com.jfoenix.controls.*;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.entity.CityPreviewRendering;
import g6.gcm.client.entity.RenderingsEngineer;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.PurchasesManager;
import g6.gcm.client.manager.StageManager;
import g6.gcm.core.interfaces.Renderable;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CustomerActiveSubscriptionsViewerUIController {

    /*Hellooo*/

    @FXML
    private AnchorPane mainViewAP;

    @FXML
    private JFXRadioButton cityNameRB;

    @FXML
    private JFXRadioButton numberOfMapsRB;

    @FXML
    private JFXRadioButton numberOfSitesRB;

    @FXML
    private JFXTextField searchTF;

    @FXML
    private TilePane activeCitiesTP;

    @FXML
    private JFXDrawer activeSubscriptionDRAWER;

    @FXML
    private JFXButton closeBTN;


    private StageManager stageManager;

    // a Hash map to hold all city renderables at any given time to perform a filter on them
    private ConcurrentHashMap<Renderable, CityPreviewRendering> activeSubsriptions;
    private RadioButton finalRB =new RadioButton("");

    @FXML
    private void initialize() {
        stageManager = StageManager.getManager();

        AnchorPane customerCityExplorer = new AnchorPane();

        //initialize listeners for an appropriate reaction for the controller
        initializeListeners();


        stageManager.switchScene(FXMLFactory.CUSTOMER_CITY_EXPLORER, customerCityExplorer);
        activeSubscriptionDRAWER.setSidePane(customerCityExplorer);


        activeSubscriptionDRAWER.setOnDrawerOpened(e -> {

            closeBTN.setVisible(true);

            activeSubscriptionDRAWER.setDisable(false);
        });


        activeSubscriptionDRAWER.setOnDrawerClosed(e -> {
            activeSubscriptionDRAWER.setDisable(true);

        });


         closeBTN.setOnMouseClicked(e -> {
            closeBTN.setVisible(false);
            activeSubscriptionDRAWER.close();
        });


    }

    private void initializeListeners() {

        cityNameRB.selectedProperty().addListener(((observable, oldValue, newValue) -> {

            if (newValue) {
                finalRB.setText(cityNameRB.getText());
                numberOfMapsRB.setSelected(false);
                numberOfSitesRB.setSelected(false);

            }
        }));


        numberOfMapsRB.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                finalRB.setText(numberOfMapsRB.getText());
                cityNameRB.setSelected(false);
                numberOfSitesRB.setSelected(false);

            }
        }));


        numberOfSitesRB.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                finalRB.setText(numberOfSitesRB.getText());
                numberOfMapsRB.setSelected(false);
                cityNameRB.setSelected(false);
            }
        }));



        // Listen to cities list changes
        CitiesManager.getInstance().beansListProperty()
                .addListener((ListChangeListener<? super Renderable>) c ->
                        activeSubsriptions = RenderingsEngineer.CatalogEngineer.refreshCatalogsCitiesView(activeCitiesTP, activeSubscriptionDRAWER));

        // Listen to purchases list changes
        PurchasesManager.getInstance().beansListProperty()
                .addListener((ListChangeListener<? super Renderable>) c ->
                        activeSubsriptions = RenderingsEngineer.CatalogEngineer.refreshCatalogsCitiesView(activeCitiesTP, activeSubscriptionDRAWER));


        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {

            switch (finalRB.getText()) {
                case "City Name": {

                    if(newValue.isEmpty()){
                        for(Map.Entry<Renderable,CityPreviewRendering> entry: activeSubsriptions.entrySet()) {
                            entry.getValue().setVisible(true);
                            entry.getValue().setManaged(true);
                        }
                    }
                    else {

                        for (Map.Entry<Renderable, CityPreviewRendering> entry : activeSubsriptions.entrySet()) {

                            if ((entry.getKey().render().toLowerCase().startsWith(newValue.toLowerCase()))) {

                                entry.getValue().setVisible(true);
                                entry.getValue().setManaged(true);

                            } else {

                                entry.getValue().setVisible(false);
                                entry.getValue().setManaged(false);

                            }
                        }
                    }
                    break;
                }
                case "Number Of Maps": {

                    if(newValue.isEmpty()){
                        for(Map.Entry<Renderable,CityPreviewRendering> entry: activeSubsriptions.entrySet()) {
                            entry.getValue().setVisible(true);
                            entry.getValue().setManaged(true);
                        }
                    }

                   else {
                        for (Map.Entry<Renderable, CityPreviewRendering> entry : activeSubsriptions.entrySet()) {

                            if ((entry.getValue().getNumberOfMaps().equals(newValue))) {
                                entry.getValue().setVisible(true);
                                entry.getValue().setManaged(true);

                            } else {
                                entry.getValue().setVisible(false);
                                entry.getValue().setManaged(false);

                            }
                        }
                    }
                    break;


                }
                case "Number Of Sites": {

                    if(newValue.isEmpty()){
                        for(Map.Entry<Renderable,CityPreviewRendering> entry: activeSubsriptions.entrySet()) {
                            entry.getValue().setVisible(true);
                            entry.getValue().setManaged(true);
                        }
                    }

                   else {
                        for (Map.Entry<Renderable, CityPreviewRendering> entry : activeSubsriptions.entrySet()) {

                            if ((entry.getValue().getNumberOfSites().equals(newValue))) {
                                entry.getValue().setVisible(true);
                                entry.getValue().setManaged(true);

                            } else {
                                entry.getValue().setVisible(false);
                                entry.getValue().setManaged(false);

                            }
                        }
                    }
                    break;


                }
            }


        });



    }


}



