package g6.gcm.client.boundary.employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import g6.gcm.client.entity.RenderingsEngineer.EngineeredCellFactories;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.MapsManager;
import g6.gcm.client.manager.StageManager;
import g6.gcm.core.interfaces.Renderable;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;

public class EmployeeMapsAttacherUIController {

  @FXML
  private AnchorPane mainViewAP;

  @FXML
  private JFXComboBox<Renderable> citiesCB;

  @FXML
  private JFXButton createCityBTN;

  @FXML
  private JFXTextField cityNameTF;

  @FXML
  private JFXTextArea cityDescriptionTA;

  @FXML
  private JFXListView<Renderable> leftLV;

  @FXML
  private JFXButton moveLeftBTN;

  @FXML
  private JFXButton moveRightBTN;

  @FXML
  private JFXListView<Renderable> rightLV;
  @FXML
  private JFXButton saveBTN;

  private StageManager stageManager;

  @FXML
  private void initialize() {

    CitiesManager.getInstance().requestAllCities();

    initializeCitiesComboBox();

    initializeSelectedCityFields();

    initializeMapsLSV();

    createCityBTN.setOnMouseClicked(click -> CitiesManager.getInstance().createNewCity());

    saveBTN.setOnMouseClicked(click -> {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setHeaderText(null);

      if (rightLV.getItems().isEmpty()) {
        alert.setTitle("City without maps?");

        // Header Text: null
        alert.setContentText("A city must have at least 1 map.");

        alert.showAndWait();
      } else if (cityNameTF.getText().isEmpty() || cityNameTF.getText() == "") {
        alert.setTitle("City without a name?");
        alert.setContentText("Please provide a name...");
        alert.showAndWait();
      } else if (cityDescriptionTA.getText().isEmpty() || cityDescriptionTA.getText() == "") {
        alert.setTitle("You lazy ass!");
        alert.setContentText("How about the description?");
        alert.showAndWait();
      } else {

        CitiesManager.getInstance().requestCityCreation();
      }
    });

  }


  private void initializeCitiesComboBox() {
    // Bind the combobox's list to the always up to date CitiesManager list of cities
    citiesCB.itemsProperty().bind(CitiesManager.getInstance().beansListProperty());

    // Bind selected city's info to the CitiesManager
    citiesCB.valueProperty().bindBidirectional(CitiesManager.getInstance().beanProperty());

    // Set the renderings' cell factory
    citiesCB.setCellFactory(EngineeredCellFactories::callLV);
    citiesCB.setButtonCell(EngineeredCellFactories.callLV(null));
  }


  private void initializeSelectedCityFields() {
    CitiesManager.getInstance().getSuperBean()
        .bindBidirectional("cityName", cityNameTF.textProperty());
    CitiesManager.getInstance().getSuperBean()
        .bindBidirectional("cityDescription", cityDescriptionTA.textProperty());
  }

  @SuppressWarnings("Duplicates")
  private void initializeMapsLSV() {
    leftLV.setCellFactory(EngineeredCellFactories::callLV);
    rightLV.setCellFactory(EngineeredCellFactories::callLV);
    leftLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    rightLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    moveLeftBTN.setOnMouseClicked(click -> {
      if (!rightLV.getItems().isEmpty()) {
        ObservableList selectedItems = rightLV.getSelectionModel().getSelectedItems();
        leftLV.getItems().addAll(selectedItems);
        rightLV.getItems().removeAll(selectedItems);
      }
    });

    moveRightBTN.setOnMouseClicked(click -> {
      if (!leftLV.getItems().isEmpty()) {
        ObservableList selectedItems = leftLV.getSelectionModel().getSelectedItems();
        rightLV.getItems().addAll(selectedItems);
        leftLV.getItems().removeAll(selectedItems);
      }
    });

    moveRightBTN.disableProperty().bind(
        Bindings.createBooleanBinding(
            () -> leftLV.getSelectionModel().getSelectedItems().isEmpty() ? true : false,
            leftLV.getSelectionModel().getSelectedItems()));
    moveLeftBTN.disableProperty().bind(
        Bindings.createBooleanBinding(
            () -> rightLV.getSelectionModel().getSelectedItems().isEmpty() ? true : false,
            rightLV.getSelectionModel().getSelectedItems()));

    leftLV.itemsProperty().bindBidirectional(MapsManager.getInstance().unattachedMapsProperty());
    rightLV.itemsProperty().bindBidirectional(MapsManager.getInstance().beansListProperty());

  }


}
