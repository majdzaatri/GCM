package g6.gcm.client.boundary;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import g6.gcm.client.manager.GCMClient;
import g6.gcm.client.manager.NotificationsManager;
import g6.gcm.client.manager.PurchasesManager;
import g6.gcm.client.manager.StageManager;
import g6.gcm.core.entity.NotificationDTO;
import g6.gcm.core.entity.PurchaseDTO;
import g6.gcm.core.factories.ClientsInquiries;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class NotificationsManagerUIController {


  @FXML
  private FontAwesomeIconView notificationsBTN;

  @FXML
  private VBox notificationsVB;

  @FXML
  private VBox notificationsListVB;

  @FXML
  private HBox exampleHB;

  @FXML
  private FontAwesomeIconView exampleICON;

  @FXML
  private Label exampleLBL;

  @FXML
  private Separator exampleSEPARATOR;

  @FXML
  private Label newNotificationsLBL;

  @FXML
  private void initialize() {
/*    notificationsVB.setMouseTransparent(true);
    notificationsVB.mouseTransparentProperty().bind(notificationsVB.visibleProperty());*/

    notificationsBTN.setOnMouseClicked(e ->
    {
      notificationsVB.setVisible(!notificationsVB.isVisible());
      notificationsBTN.getStyleClass().removeAll("bell-icon-armed");
      for (NotificationDTO notification : NotificationsManager.getListProperty().get()) {
        notification.setRead(1);
        notification.setRequest(ClientsInquiries.UPDATE_NOTIFICATION_READ);
        GCMClient.send(notification);
      }

    });
    NotificationsManager.getListProperty().addListener((observable, oldValue, newValue) ->
    {
      constructNotifications(newValue);

      for (NotificationDTO notification : NotificationsManager.getListProperty().get()) {
        if (notification.getRead() == 0) {
          notificationsBTN.getStyleClass().add("bell-icon-armed");

        }
      }

    });

  }

  public void addNotification(String notification) {
    HBox newNotification = new HBox();
    newNotification.setPadding(new Insets(10, 20, 10, 10));
    Label newLabel = new Label();
    newLabel.setText(notification);
    Separator newSeperator = new Separator();
    newNotification.getChildren().add(newSeperator);
    notificationsListVB.getChildren().add(newNotification);

  }

  private void activateSwitcheroo(FXMLFactory fxmlView) {
    if (FXMLFactory.UI_SWITCHEROO != fxmlView) {
      StageManager.getManager().switchScene(fxmlView, StageManager.getManager().getMainViewAP());
    }
  }


  //When the list of notifications changes, build a new GUI list.
  public void constructNotifications(List<NotificationDTO> list) {
    notificationsListVB.getChildren().clear();
    for (NotificationDTO newNotification : list) {
      HBox newNotificationHB = new HBox();
      newNotificationHB.getStyleClass().add("notification-hbox");
      newNotificationHB.setAlignment(Pos.CENTER_LEFT);
      newNotificationHB.setPrefHeight(Region.USE_COMPUTED_SIZE);
      newNotificationHB.setPrefWidth(Region.USE_COMPUTED_SIZE);
      newNotificationHB.setMinHeight(46);

      FontAwesomeIconView newIcon = new FontAwesomeIconView();
      newIcon.setGlyphName("RENREN");
      newIcon.setGlyphSize(15);
      newIcon.setFill(Color.web("#f80606"));

      Region newReg = new Region();
      newReg.setMinWidth(10);
      newReg.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

      newNotificationHB.setPadding(new Insets(10, 20, 10, 10));

      newNotificationHB.getChildren().add(newIcon);
      newNotificationHB.getChildren().add(newReg);
      Label newLabel = new Label();
      newLabel.setText(newNotification.getNotification());
      newNotificationHB.getChildren().add(newLabel);
      Separator newSeperator = new Separator();

      notificationsListVB.getChildren().add(newNotificationHB);
      notificationsListVB.getChildren().add(newSeperator);

      switch (newNotification.getNotificationType()) {
        case "RENEWAL":
          newNotificationHB.setOnMouseClicked((e) -> {

            notificationsVB.setVisible(!notificationsVB.isVisible());
              PurchaseDTO purchaseDto = new PurchaseDTO();
              purchaseDto.setCityID(newNotification.getUpdatee());
              purchaseDto.setEmail(newNotification.getToUsername());
              purchaseDto.setRequest(ClientsInquiries.EXTEND_SUBSCRIPTION_BY_USER);
              GCMClient.send(purchaseDto);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Subscription Renewal");
            alert.setHeaderText(null);
            alert.setContentText(
                "You have extended your subscription and received 10% discount.");

            alert.showAndWait();
          });
          break;


        case "MAP_REQUEST":
          newLabel.setOnMouseClicked((e) -> {
            notificationsVB.setVisible(!notificationsVB.isVisible());
            activateSwitcheroo(FXMLFactory.EMPLOYEE_REQUESTS_VIEWER);
          });
          break;
      }

    }
  }


}
