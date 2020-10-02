package g6.gcm.client.boundary.fix;

import org.controlsfx.control.textfield.CustomTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class CatalogUIController {

    @FXML
    private AnchorPane catalogMainViewAP;

    @FXML
    private JFXButton viewCityBTN;

    @FXML
    private JFXRadioButton cityRB;

    @FXML
    private CustomTextField cityTF;

    @FXML
    private JFXRadioButton poiRB;

    @FXML
    private CustomTextField poiTF;

    @FXML
    private JFXRadioButton descriptionRB;

    @FXML
    private CustomTextField descriptionTF;

    @FXML
    private JFXComboBox<?> sortingCB;

}
