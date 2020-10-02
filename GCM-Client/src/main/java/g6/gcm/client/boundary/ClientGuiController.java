package g6.gcm.client.boundary;

import g6.gcm.client.manager.ClientLauncher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class ClientGuiController {


        @FXML
        private TextField hostTXT;

        @FXML
        private TextField portTXT;

        @FXML
        void connectClicked(ActionEvent event) {

                try
                {
                        /*ClientLauncher.initClient(hostTXT.getText(), Integer.parseInt(portTXT.getText()));
                        ClientLauncher.setSuccess(1);*/
                        ((Node) event.getSource()).getScene().getWindow().hide();

                } catch (Exception e) {
                        hostTXT.clear();
                        portTXT.clear();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Failed to connect!");
                        alert.show();
                }



        }



}
