package g6.gcm.client.boundary.startup;

import com.jfoenix.controls.JFXButton;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.manager.StageManager;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class JustBrowseSidePaneUIController {

    @FXML
    private VBox costumerSidePaneVB;

    @FXML
    private HBox signInHB;

    @FXML
    private JFXButton signInBTN;

    @FXML
    private HBox signUpHB;

    @FXML
    private JFXButton signUpBTN;

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
    private void initialize() {

        stageManager = StageManager.getManager();

//    switcherooAP=stageManager.getMainViewAP();
//   currentlyON=FXMLFactory.UI_SWITCHEROO;


        signInBTN.setOnMouseClicked(e -> stageManager.stageScene(FXMLFactory.WELCOME_SCREEN));
        signUpBTN.setOnMouseClicked(e -> {
            stageManager.stageScene(FXMLFactory.WELCOME_SCREEN);

        });


    }

    private void activateSwitcheroo(FXMLFactory fxmlView) {
        if (currentlyON != fxmlView) {
            stageManager.switchScene(fxmlView, switcherooAP);
            currentlyON = fxmlView;
        }
    }

}
