package g6.gcm.client.boundary.startup;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.CreditCardsManager;
import g6.gcm.client.manager.StageManager;
import g6.gcm.client.manager.UsersManager;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.Request;
import g6.gcm.core.interfaces.Requestable;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class WelcomeScreenUIController {

    @FXML
    private AnchorPane welcomeAP;

    @FXML
    private VBox menuVB;

    @FXML
    private JFXDrawer switcherooDRAWER;

    @FXML
    private JFXButton signInBTN;

    @FXML
    private JFXButton signUpBTN;

    @FXML
    private JFXButton justBrowseBTN;

    @FXML
    private JFXButton minimizeBTN;

    @FXML
    private JFXButton exitBTN;

    private StageManager stageManager;

    private boolean signInFormIsVisible;

    private VBox signInVB, signUpVB;

   // bound to the request type in each Manager
    private ObjectProperty<Request> serversCommands = new SimpleObjectProperty<>();
    private ObjectProperty<Request> creditCardRequest = new SimpleObjectProperty<>();


    @FXML
    private void initialize() {

        serversCommands.set(ServersCommands.INITIAL_STATE);
        stageManager = StageManager.getManager();
        signInFormIsVisible = true;
        signInVB = new VBox();
        signUpVB = new VBox();
        stageManager.switchScene(FXMLFactory.SIGN_IN_SCREEN, signInVB);
        stageManager.switchScene(FXMLFactory.SIGN_UP_SCREEN_MANAGER, signUpVB);



        initializeListeners();
        initializeActions();
        initializeBindings();


        switcherooDRAWER.setSidePane(signInVB);
        switcherooDRAWER.open();




;



        minimizeBTN.setOnMouseClicked(e -> StageManager.getManager().primaryStage.setIconified(true));
        exitBTN.setOnMouseClicked(e -> System.exit(1));
    }

    private void initializeBindings(){

        // bind to returned request
        serversCommands.bind(Bindings.createObjectBinding(() ->UsersManager.getInstance().getBean().getRequest(),
               UsersManager.getInstance().beanProperty()));

//        UsersManager.getInstance().getSuperBean().
//                bindBidirectional("request",serversCommands, Request.class);

        //bind to returned request
        creditCardRequest.bind(Bindings.createObjectBinding(() ->CreditCardsManager.getInstance().getBean().getRequest(),
                CreditCardsManager.getInstance().beanProperty()));
//        CreditCardsManager.getInstance().getSuperBean().
//                bindBidirectional("request",creditCardRequest,Request.class);

    }

    private void initializeActions() {
        signInBTN.setOnMouseClicked(e -> {
            if (signInFormIsVisible) {
                Shake shake = new Shake(switcherooDRAWER);
                shake.play();
            } else {
                signInFormIsVisible = true;
                doTheSwitcheroo(signInVB);
            }
        });
        signUpBTN.setOnMouseClicked(e -> {
            if (!signInFormIsVisible) {
                Shake shake = new Shake(switcherooDRAWER);
                shake.play();
            } else {
                signInFormIsVisible = false;
                doTheSwitcheroo(signUpVB);
            }
        });

        justBrowseBTN.setOnMouseClicked(e -> {
            CitiesManager.getInstance().requestAllCities();
            stageManager.stageScene(FXMLFactory.MAIN_UI);});
    }

    private void initializeListeners() {

        serversCommands.addListener((observable, oldValue, newValue) -> {
            if(newValue!=oldValue) {
                switch ((ServersCommands) newValue) {
                    case RETRY_LOGIN: {
                        System.out.println("retry");
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("wrong Credentials try again!!");
                        alert.show();
                        stageManager.switchScene(FXMLFactory.SIGN_IN_SCREEN, signInVB);
                        break;
                    }
                    case SIGN_IN_EMPLOYEE:
                    case SIGN_IN_COSTUMER: {

                        stageManager.stageScene(FXMLFactory.MAIN_UI);
                        break;
                    }
                    case SUCCESSFUL_SIGN_UP: {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText(
                                "Welcome to your new home" + " " + UsersManager.getInstance().getBean().render() + " "
                                        + "Please sign in");
                        alert.show();
                        doTheSwitcheroo(signInVB);

                        break;
                    }
                    case FAILED_SIGN_UP: {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Failed to sign up user try again");
                        alert.show();
                        stageManager.switchScene(FXMLFactory.SIGN_UP_SCREEN_1, signUpVB);
                        break;

                    }
                  case ALREADY_LOGGED_IN: {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Already Signed In ");
                    alert.show();
                    stageManager.switchScene(FXMLFactory.SIGN_UP_SCREEN_1, signUpVB);
                    break;

                  }
                  case EXIT_FROM_SYSTEM: {

                    break;
                  }
                }
            }
        });

        creditCardRequest.addListener(((observable, oldValue, newValue) -> {
            switch ((ServersCommands) newValue) {
                case SUCCESSFUL_CREDIT_CARD_INSERT: {
                    UsersManager.getInstance().createUser();
                    break;
                }
                case FAILED_CREDIT_CARD_INSERT: {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Credit card is invalid try again");
                    alert.show();
                    stageManager.switchScene(FXMLFactory.SIGN_UP_SCREEN_3, signUpVB);
                    break;

                }
            }
        }));
    }

    //alternate between sign in and sign up
    private void doTheSwitcheroo(VBox formVB) {
        switcherooDRAWER.close();
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(e -> {
            switcherooDRAWER.setSidePane(formVB);
            switcherooDRAWER.open();
        });
        pause.play();
    }

}
