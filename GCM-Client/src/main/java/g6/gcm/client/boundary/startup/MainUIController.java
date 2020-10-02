package g6.gcm.client.boundary.startup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.manager.GCMClient;
import g6.gcm.client.manager.NotificationsManager;
import g6.gcm.client.manager.StageManager;
import g6.gcm.client.manager.UsersManager;
import g6.gcm.core.entity.NotificationDTO;
import g6.gcm.core.entity.PurchaseDTO;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.factories.ServersCommands;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class MainUIController {


  @FXML
  private JFXDrawer sidePaneDRAWER;

  @FXML
  private AnchorPane mainAP;

  @FXML
  private FontAwesomeIconView notificationsBTN;

  @FXML
  private JFXButton minimizeBTN;

  @FXML
  private JFXButton exitBTN;

  @FXML
  private JFXHamburger menuToggleBTN;

  @FXML
  private VBox notificationsVB;

  @FXML
  private VBox notificationsListVB;

  @FXML
  private Label newNotificationsLBL;


  private StageManager stageManager;


  @FXML
  private void initialize() {
    stageManager = StageManager.getManager();

    VBox sidePane = new VBox();

    stageManager.switchScene(FXMLFactory.UI_SWITCHEROO, mainAP);

    stageManager.switchScene(FXMLFactory.UI_SWITCHEROO, mainAP);
    if (!((UserDTO) UsersManager.getInstance().getBean()).isOnline()) {
      stageManager.switchScene(FXMLFactory.JUST_BROWSE_SIDE_PANE, sidePane);
      stageManager.switchScene(FXMLFactory.CATALOG_BROWSER, mainAP);

    } else if (((UserDTO) UsersManager.getInstance().getBean()).getType() == DTOsFactory.USER) {
      stageManager.switchScene(FXMLFactory.CUSTOMER_SIDE_PANE, sidePane);
    } else {
      stageManager.switchScene(FXMLFactory.EMPLOYEE_SIDE_PANE, sidePane);
    }
    sidePaneDRAWER.setSidePane(sidePane);
    sidePaneDRAWER.setOverLayVisible(false);
    sidePaneDRAWER.setAlignment(Pos.CENTER);

    HamburgerNextArrowBasicTransition sidePaneAnimation = new HamburgerNextArrowBasicTransition(
        menuToggleBTN);
    sidePaneAnimation.setRate(-1);

    menuToggleBTN.setOnMouseClicked(e -> {
      sidePaneAnimation.setRate(sidePaneAnimation.getRate() * -1);
      sidePaneAnimation.play();

      if (sidePaneDRAWER.isOpened()) {
        sidePaneDRAWER.close();
      } else {
        sidePaneDRAWER.open();
      }
    });
    minimizeBTN.setOnMouseClicked(e -> stageManager.primaryStage.setIconified(true));
    exitBTN.setOnMouseClicked(e ->
        UsersManager.getInstance().signOutUser()
    );

    notificationsBTN.setOnMouseClicked(e ->
    {
      System.out.println("hi");
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

    UsersManager.getInstance().beanProperty().addListener((observable, oldValue, newValue) -> {
      if (oldValue != newValue && newValue != null) {
        if (newValue.getRequest() == ServersCommands.EXIT_FROM_SYSTEM) {
          System.exit(1);
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