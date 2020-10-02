package g6.gcm.client;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.ListActionView;

import java.util.ArrayList;
import java.util.List;

public class JustStuffController {

    @FXML
    private AnchorPane mainAP;

    @FXML
    private JFXListView<String> jfxListView;

    @FXML
    private ListActionView<String> listActionView;

    @FXML
    private CheckListView<String> checkListView;


    @FXML
    private void initialize() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }

        jfxListView.getItems().setAll(list);
        listActionView.getItems().setAll(list);
        checkListView.getItems().setAll(list);

    }

}
