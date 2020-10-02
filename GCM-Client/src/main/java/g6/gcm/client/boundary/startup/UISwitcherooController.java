package g6.gcm.client.boundary.startup;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import g6.gcm.client.manager.StageManager;

public class UISwitcherooController {

    @FXML
    private AnchorPane switcherooAP;

    private StageManager stageManager;

    @FXML
    private void initialize() {
        stageManager = StageManager.getManager();
        stageManager.setMainViewAP(switcherooAP);
    }

}
