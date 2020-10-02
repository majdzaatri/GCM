package g6.gcm.client.boundary.employee;

import com.jfoenix.controls.JFXButton;

import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.ClientLauncher;
import g6.gcm.client.manager.StageManager;
import g6.gcm.client.manager.UsersManager;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.DTOsFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EmployeeSidePaneUIController {

    @FXML
    private VBox sideMenuVB;

    @FXML
    private JFXButton attachMapsBTN;

    @FXML
    private JFXButton editMapsBTN;

    @FXML
    private JFXButton requestsBTN;

    @FXML
    private JFXButton mapsPricesBTN;

    @FXML
    private JFXButton reportsBTN;
    @FXML
    private JFXButton logoutBTN;



    private StageManager stageManager;

    private AnchorPane switcherooPane;

    private FXMLFactory currentlyON;

    @FXML
    private void initialize() {
        stageManager = StageManager.getManager();
        switcherooPane = stageManager.getMainViewAP();

        attachMapsBTN.setOnMouseClicked(e->activateSwitcheroo(FXMLFactory.EMPLOYEE_MAPS_ATTACHER));
        requestsBTN.setOnMouseClicked(e -> activateSwitcheroo(FXMLFactory.EMPLOYEE_REQUESTS_VIEWER));
        mapsPricesBTN.setOnMouseClicked(e -> activateSwitcheroo(FXMLFactory.EMPLOYEE_PRICES_EDITOR));
        reportsBTN.setOnMouseClicked(e -> activateSwitcheroo(FXMLFactory.EMPLOYEE_REPORTS_GENERATOR));
        editMapsBTN.setOnMouseClicked(e->activateSwitcheroo(FXMLFactory.EMPLOYEE_MAPS_EDITOR));

        if (((UserDTO)UsersManager.getInstance().getBean()).getType().equals(DTOsFactory.CONTENT_EDITOR)) {
            reportsBTN.setDisable(true);
            mapsPricesBTN.setDisable(true);
        }

    }

    private void activateSwitcheroo(FXMLFactory fxmlView) {
        if (currentlyON != fxmlView) {
            stageManager.switchScene(fxmlView, switcherooPane);
            currentlyON = fxmlView;
        }
    }

    @FXML
    void logoutClicked(ActionEvent event) {

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