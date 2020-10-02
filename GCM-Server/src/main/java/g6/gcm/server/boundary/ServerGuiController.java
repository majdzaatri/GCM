package g6.gcm.server.boundary;


import g6.gcm.server.entity.DBConnection;
import g6.gcm.server.manager.GCMServer;
import g6.gcm.server.manager.ServerLauncher;
import g6.gcm.server.manager.SubscriptionNotifier;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;


public class ServerGuiController {
    private boolean successfulConnection = true;

    @FXML
    private TextField portTXT;

    @FXML
    private TextField usernameTXT;

    @FXML
    private TextField passwordTXT;

    @FXML
    void connectClicked(ActionEvent event) {

        try
        {
            successfulConnection = true;
            ServerLauncher.initServer(usernameTXT.getText(), passwordTXT.getText(), Integer.valueOf(portTXT.getText()));
            Thread.sleep(2000);

        } catch (Exception e) {
            usernameTXT.clear();
            portTXT.clear();
            passwordTXT.clear();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to connect! Your port or password is incorrect!!");
            alert.show();
        }


          if(GCMServer.getServer().dbConnection.getConnection() == null)
        {

            usernameTXT.clear();
            portTXT.clear();
            passwordTXT.clear();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to connect! Your port or password is incorrect!!");
            alert.show();
            successfulConnection = false;
        }
        if (successfulConnection) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful Connection");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Successfully Connected to Server!");

            alert.showAndWait();

            Platform.exit();
            SubscriptionNotifier notifier = new SubscriptionNotifier();
            notifier.launchNotifier();
        }
    }



}
