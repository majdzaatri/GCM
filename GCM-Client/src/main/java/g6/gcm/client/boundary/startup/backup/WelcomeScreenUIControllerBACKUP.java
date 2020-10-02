package g6.gcm.client.boundary.startup.backup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.manager.StageManager;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeScreenUIControllerBACKUP {


    @FXML
    private AnchorPane root;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXButton signInBTN;

    @FXML
    private JFXButton signUpBTN;

    @FXML
    private JFXButton justBrowseBTN;

    @FXML
    private ImageView exitButton;

    @FXML
    private ImageView minimizeButton;

    private StageManager stageManager;


    @FXML
    private SignUpScreenController signUpScreenController;


    private JFXButton NextSignUpBT;

    @FXML
    private void initialize() {

        stageManager = StageManager.getManager();

        VBox signInVB = new VBox();
        VBox signUpVB = new VBox();

        VBox backGround = new VBox();
//		backGround.setStyle("WelcomeScreen");

        stageManager.switchScene(FXMLFactory.SIGN_IN_SCREEN, signInVB);
        drawer.setSidePane(signInVB);
        stageManager.switchScene(FXMLFactory.SIGN_UP_SCREEN_1, signUpVB);

        drawer.setOverLayVisible(false);
        drawer.setAlignment(Pos.CENTER);

        PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
        PauseTransition delay2 = new PauseTransition(Duration.seconds(0.5));
        PauseTransition delay3 = new PauseTransition(Duration.seconds(0.2));
        delay.setOnFinished(event -> drawer.open());
        signInVB.setVisible(false);
        signUpVB.setVisible(false);

        signInBTN.setOnMouseClicked(e -> {
            signInVB.setVisible(true);
            if (signUpVB.isVisible()) {
                drawer.close();
                delay3.setOnFinished(event -> drawer.setSidePane(backGround));
                delay2.setOnFinished(event -> drawer.setSidePane(signInVB));
                delay3.play();
                delay2.play();
                delay.play();
                signUpVB.setVisible(false);
            } else {
                if (drawer.isOpened()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            }
        });

        signUpBTN.setOnMouseClicked(e -> {
            signUpVB.setVisible(true);
            if (signUpVB.isVisible()) {
                drawer.close();
                delay3.setOnFinished(event -> drawer.setSidePane(backGround));
                delay2.setOnFinished(event -> drawer.setSidePane(signUpVB));
                delay3.play();
                delay2.play();
                delay.play();
                signInVB.setVisible(false);
            } else {
                if (drawer.isOpened()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            }
        });

        exitButton.setOnMouseClicked(event -> exitApp(stageManager));
        minimizeButton.setOnMouseClicked(event -> minimizeApp(stageManager));
    }

    private void exitApp(StageManager stage) {
        // stage.close();
        System.exit(1);
    }

    private void minimizeApp(StageManager stage) {
        Stage primaryStage = stage.primaryStage;
        primaryStage.setIconified(true);
    }
}
