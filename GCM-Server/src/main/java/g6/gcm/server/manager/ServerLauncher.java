package g6.gcm.server.manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerLauncher extends Application {

    private static Stage PStage;
    private static GCMServer gcmServer;
    private static ServerMessageHandler serverMessageHandler;

    public static Stage getPStage() {
        return PStage;
    }

    public static void setPStage(Stage PStage) {
        ServerLauncher.PStage = PStage;
    }

    public static void main(String[] args) {

        launch(args);


    }

    public static void initServer(String username, String password, int port)
            throws ClassNotFoundException {

        //SubscriptionNotifier notifier = new SubscriptionNotifier();
        /**
         * Initialize server. Server initializes data base {@See GCMServer}
         */
/*        try
        {


        } catch (Exception e) {

        }*/
        gcmServer = GCMServer.initializeServer(port, "gcm-db", username, password);
        /**
         * Initializes Server's Message Handler.
         */
        serverMessageHandler = new ServerMessageHandler();

  /*      Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("successful connection");
        alert.setHeaderText(null);
        alert.setContentText(
                "Connected successfully");

        alert.showAndWait();
        Platform.exit();*/

        try {
            gcmServer.listen();
        } catch (IOException e) {
            gcmServer.oops(e);
        }

        // SubscriptionNotifier.launchNotifier();
        /*  notifier.launchNotifier();*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        boolean testin = true;
        if (!testin) {
            Parent root = FXMLLoader.load(getClass().getResource("/ServerGui.fxml"));
            setPStage(primaryStage);
            Scene scene = new Scene(root, 940, 500);
            primaryStage.setTitle("Connection");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            gcmServer = GCMServer.initializeServer(4332, "gcm-db", "root", "password");
            serverMessageHandler = new ServerMessageHandler();

            try {
                gcmServer.listen();
            } catch (IOException e) {
                gcmServer.oops(e);
            }

        }


    }
}
