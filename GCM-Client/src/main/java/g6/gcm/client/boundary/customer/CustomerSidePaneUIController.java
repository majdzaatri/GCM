package g6.gcm.client.boundary.customer;

import com.jfoenix.controls.JFXButton;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.PurchasesManager;
import g6.gcm.client.manager.StageManager;
import g6.gcm.client.manager.UsersManager;
import javafx.beans.binding.BooleanExpression;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CustomerSidePaneUIController {

  @FXML
  private VBox costumerSidePaneVB;

  @FXML
  private JFXButton profileBTN;

  @FXML
  private JFXButton browseCatalogBTN;

  @FXML
  private JFXButton myCollectionsBTN;

  @FXML
  private HBox activeSubscriptionsHB;

  @FXML
  private JFXButton activeSubscriptionsBTN;

  @FXML
  private HBox purchaseHistoryHB;

  @FXML
  private JFXButton purchaseHistoryBTN;

  @FXML
  private JFXButton helpBTN;

  @FXML
  private JFXButton contactUsBTN;

  @FXML
  private JFXButton donateBTN;

  private StageManager stageManager;

  private AnchorPane switcherooAP;

  private FXMLFactory currentlyON;
  @FXML
  private JFXButton LogoutBTN;


  @FXML
  private void initialize() {
    stageManager = StageManager.getManager();
    switcherooAP = stageManager.getMainViewAP();
    currentlyON = FXMLFactory.UI_SWITCHEROO;
    activeSubscriptionsHB.setVisible(false);
    purchaseHistoryHB.setVisible(false);
    BooleanExpression shouldBeVisible = myCollectionsBTN.focusedProperty()
        .or(activeSubscriptionsBTN.focusedProperty().or(purchaseHistoryBTN.focusedProperty()));

    activeSubscriptionsHB.visibleProperty().bind(shouldBeVisible);
    activeSubscriptionsHB.managedProperty().bind(shouldBeVisible);
    purchaseHistoryHB.visibleProperty().bind(shouldBeVisible);
    purchaseHistoryHB.managedProperty().bind(shouldBeVisible);

    profileBTN.setOnMouseClicked(e -> {
      activateSwitcheroo(FXMLFactory.CUSTOMER_PROFILE_EDITOR_2);
    });
    browseCatalogBTN.setOnMouseClicked(e -> {
      CitiesManager.getInstance().requestAllCities();
      activateSwitcheroo(FXMLFactory.CATALOG_BROWSER);
    });
    activeSubscriptionsBTN.setOnMouseClicked(

            e ->{
              PurchasesManager.getInstance().requestAllCitiesUnderSubscriptionOfUser();
              activateSwitcheroo(FXMLFactory.CUSTOMER_ACTIVE_SUBSCRIPTIONS_VIEWER);});
    purchaseHistoryBTN
            .setOnMouseClicked(e -> {
                CitiesManager.getInstance().requestAllCities();
                PurchasesManager.getInstance().requestAllSubscriptionsOfUser();
                activateSwitcheroo(FXMLFactory.CUSTOMER_PURCHASE_HISTORY_VIEWER);
            });
    helpBTN.setOnMouseClicked(e -> activateSwitcheroo(FXMLFactory.CUSTOMER_HELP_SCREEN));
  }


  private void activateSwitcheroo(FXMLFactory fxmlView) {
    if (currentlyON != fxmlView) {
      stageManager.switchScene(fxmlView, switcherooAP);
      currentlyON = fxmlView;
    }
  }

  @FXML
  void logoutClicked(ActionEvent event)
  {

    UsersManager.getInstance().logoutUser();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Successful logout");
    alert.setHeaderText(null);
    alert.setContentText(
            "You have successfully logged-out.");

    alert.showAndWait();
    stageManager.stageScene(FXMLFactory.WELCOME_SCREEN, Color.TRANSPARENT);
  }

}
