package g6.gcm.client.boundary.startup;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.entity.CityPreviewRendering;
import g6.gcm.client.entity.RenderingsEngineer;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.PurchasesManager;
import g6.gcm.client.manager.StageManager;
import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.interfaces.Renderable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import org.controlsfx.control.textfield.CustomTextField;


public class CatalogBrowserUIController {


  @FXML
  private TilePane citiesTP;

  @FXML
  private JFXComboBox<String> searchoptionsCB;

  @FXML
  private CustomTextField searchTF;

  @FXML
  private JFXComboBox<?> sortingCB;

  @FXML
  private Label searchActivitatedLBL;

  @FXML
  private JFXDrawer catalogDRAWER;

  @FXML
  private JFXButton closeBTN;

  @FXML
  private Label topPaneLBL;


  private StageManager stageManager;


  // a Hash map to hold all city renderables at any given time to perform a filter on them
  private ConcurrentHashMap<Renderable, CityPreviewRendering> cityPreview;

  @FXML
  private void initialize() {

    stageManager = StageManager.getManager();
    AnchorPane customerCityExplorer = new AnchorPane();
    searchoptionsCB.getItems().addAll("City", "Description", "Sites", " ");
    //initialize listeners for an appropriate reaction for the controller
    initializeListeners();

    stageManager.switchScene(FXMLFactory.CUSTOMER_CITY_EXPLORER, customerCityExplorer);
    catalogDRAWER.setSidePane(customerCityExplorer);

    catalogDRAWER.setOnDrawerOpened(e -> {

      closeBTN.setVisible(true);
      catalogDRAWER.setDisable(false);
    });

    catalogDRAWER.setOnDrawerClosed(e -> {
      catalogDRAWER.setDisable(true);
    });

    closeBTN.setOnMouseClicked(e -> {
      closeBTN.setVisible(false);
      catalogDRAWER.close();
    });
  }


  private void initializeListeners() {

//      searchActivitatedLBL.textProperty().bind(Bindings.createStringBinding(()->
//              searchoptionsCB.getValue().isEmpty()? " ":"Search by "+ searchoptionsCB.getValue()+" activated!",searchoptionsCB.valueProperty()
//      ));

    searchTF.disableProperty().bind(searchoptionsCB.valueProperty().isNull());

    topPaneLBL.textProperty().bind(Bindings.createStringBinding(
        () -> catalogDRAWER.isClosed() ? "GCM Catalog"
            : CitiesManager.getInstance().getBean().render() + " View",
        catalogDRAWER.disabledProperty()));

    // Listen to cities list changes
    CitiesManager.getInstance().beansListProperty()
        .addListener((ListChangeListener<? super Renderable>) c -> {
          Platform.runLater(() -> {
            cityPreview = RenderingsEngineer.CatalogEngineer
                .refreshCatalogsCitiesView(citiesTP, catalogDRAWER);
          });
        });

    // Listen to purchases list changes
    PurchasesManager.getInstance().beansListProperty()
        .addListener((ListChangeListener<? super Renderable>) c -> {
          Platform.runLater(() -> {
            cityPreview = RenderingsEngineer.CatalogEngineer
                .refreshCatalogsCitiesView(citiesTP, catalogDRAWER);
          });
        });

    //filer by selected value in combobox
    searchTF.textProperty().addListener(((observable, oldValue, newValue) -> {
      Platform.runLater(() -> {
        if (!searchoptionsCB.valueProperty().get().isEmpty()) {
          switch (searchoptionsCB.getValue()) {
            case "City": {
              for (Map.Entry<Renderable, CityPreviewRendering> entry : cityPreview.entrySet()) {

                if ((entry.getKey().render().toLowerCase()
                    .startsWith(newValue.toLowerCase()))) {
                  entry.getValue().setVisible(true);
                  entry.getValue().setManaged(true);

                } else {
                  entry.getValue().setVisible(false);
                  entry.getValue().setManaged(false);

                }
              }
              break;
            }
            case "Description": {
              for (Map.Entry<Renderable, CityPreviewRendering> entry : cityPreview.entrySet()) {
                if ((entry.getValue()).getDescription().toLowerCase()
                    .contains(newValue.toLowerCase())) {
                  entry.getValue().setVisible(true);
                  entry.getValue().setManaged(true);

                } else {
                  entry.getValue().setVisible(false);
                  entry.getValue().setManaged(false);
                }
              }
              break;
            }
            case "Sites": {

              //render back all cities
              if (newValue.isEmpty()) {
                for (Map.Entry<Renderable, CityPreviewRendering> entry : cityPreview
                    .entrySet()) {
                  entry.getValue().setVisible(true);
                  entry.getValue().setManaged(true);
                }
              } else {
                for (Map.Entry<Renderable, CityPreviewRendering> entry : cityPreview
                    .entrySet()) {

                  //search matches in sites list for each city
                  if (((CityDTO) entry.getKey()).getSitesList().stream()
                      .anyMatch(site -> site.toLowerCase().contains(newValue.toLowerCase()))) {
                    entry.getValue().setVisible(true);
                    entry.getValue().setManaged(true);
                  } else {
                    entry.getValue().setVisible(false);
                    entry.getValue().setManaged(false);

                  }
                }
              }
//
              break;
            }
          }
        }
      });
    }));
  }

  private void shakeNode(Boolean shakeIT, Node node) {
    if (shakeIT) {
      Shake shake = new Shake(node);
      shake.play();
    }
  }

}
