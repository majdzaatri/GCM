package g6.gcm.client.boundary.employee;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import g6.gcm.client.manager.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class EmployeeSwitcherooController {

    @FXML
    private ImageView arrowImage;

    @FXML
    private Pane notificationPane;

    @FXML
    private Label label1InNotification;

    @FXML
    private AnchorPane switcherooAP;

    @FXML
    private FontAwesomeIconView bellNotificationImage;


    private StageManager stageManager;


    @FXML
    private void initialize() {
        stageManager = StageManager.getManager();
        stageManager.setMainViewAP(switcherooAP);
    }

}
