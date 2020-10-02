package g6.gcm.client.boundary.employee;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class EmployeeWorkspaceUIController {

    @FXML
    private AnchorPane mainViewAP;

    @FXML
    private JFXRadioButton cityRB;

    @FXML
    private JFXComboBox<String> cityCB;

    @FXML
    private JFXRadioButton poiRB;

    @FXML
    private JFXComboBox<String> poiCB;

    @FXML
    private JFXRadioButton sortByCityRB;

    @FXML
    private JFXRadioButton sortByExpirationDateRB;

    @FXML
    private JFXRadioButton sortByNumberOfTourRB;

    @FXML
    private JFXRadioButton sortByNumberOfPOIsRB;

}
