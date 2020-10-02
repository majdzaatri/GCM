package g6.gcm.client.boundary.startup.backup;

import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.manager.StageManager;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SignUpUIManagerController {

    @FXML
    private AnchorPane signUpFormAP;

    private StageManager stageManager;


    @FXML
    private void initialize() {
        stageManager = StageManager.getManager();
        stageManager.switchScene(FXMLFactory.SIGN_UP_SCREEN_1, signUpFormAP);

    }

}
