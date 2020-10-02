package g6.gcm.client.boundary.startup.backup;

import g6.gcm.client.boundary.startup.SignUp1Controller;
import g6.gcm.client.manager.StageManager;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class WelcomeScreenFormManagerController {

    @FXML
    private AnchorPane formManagerAP;

    @FXML
    private SignUp1Controller signUp1Controller;

    private StageManager stageManager;

    private boolean signInFormIsVisible;

    @FXML
    private void initialize() {
        stageManager = StageManager.getManager();
        signInFormIsVisible = true;
    }

}
