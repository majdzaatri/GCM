package g6.gcm.client.boundary.startup;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.manager.CreditCardsManager;
import g6.gcm.client.manager.StageManager;

import g6.gcm.client.manager.UsersManager;
import g6.gcm.core.entity.CreditCardDTO;
import g6.gcm.core.entity.UserDTO;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * first sign up screen controller (step 1)
 *
 * @author lowis kayal group 6
 */
public class SignUp1Controller {


    //local Validators to control
    StageManager stageManager;
    RegexValidator emailFieldValidator = new RegexValidator();
    RegexValidator passwordFieldValidator = new RegexValidator();
    RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

    @FXML
    private VBox signUp1VB;
    @FXML
    private HBox emailHB;
    @FXML
    private JFXTextField emailTF;
    @FXML
    private HBox passwordHB;
    @FXML
    private JFXPasswordField passwordTF;
    @FXML
    private HBox passwordConfirmationHB;
    @FXML
    private JFXPasswordField passwordConfirmationTF;
    @FXML
    private JFXButton openStep2BTN;

    @FXML
    private void initialize() {

        stageManager = StageManager.getManager();

        initializeValidators();

        initializeBindings();
        initializeActions();
        initializeListeners();

    }

    /**
     *
     * initializes each local validator and assigns it to it proper FXML variable
     */
    private void initializeValidators() {
        // Set patterns
        emailFieldValidator.setRegexPattern(
               "^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?" +

                       "(GCM|gcm|gmail|yahoo|hotmail|live|Broude|s.braude|"+

                       "broude|braude|G6|g6)\\.(com|co.il|ac.il)$");

        passwordFieldValidator.setRegexPattern(".{8,}");

        // Set appropriate messages
        requiredFieldValidator.setMessage("Required field");
        emailFieldValidator.setMessage("Illegal email address");
        passwordFieldValidator.setMessage("Your password must be at least 8 characters long.");

        // Add validators to controls
        emailTF.getValidators().addAll(requiredFieldValidator, emailFieldValidator);
        passwordTF.getValidators().addAll(requiredFieldValidator, passwordFieldValidator);
        passwordConfirmationTF.getValidators().addAll(requiredFieldValidator, passwordFieldValidator);
    }



    /**
     * initializes each single button functionality
     */

    private void initializeActions() {
        openStep2BTN.setOnMouseClicked(e -> openStep2Clicked());
    }


    /**
     *
     * initialize Listeners for all input fields
     *
     * to perform validation on each one.
     *
     */
    private void initializeListeners() {

      emailTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                emailHB.getStyleClass().add("hbox-sign-in-form-focused");
            } else {
                emailHB.getStyleClass().remove("hbox-sign-in-form-focused");
                if (!emailTF.validate()) {
                    shakeNode(true, emailTF);
                }
            }
        });


        passwordTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordHB.getStyleClass().add("hbox-sign-in-form-focused");
            } else {
                passwordHB.getStyleClass().remove("hbox-sign-in-form-focused");
                if (!passwordTF.validate()) {
                    shakeNode(true, passwordTF);
                }
            }
        });


        passwordConfirmationTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordConfirmationHB.getStyleClass().add("hbox-sign-in-form-focused");
            } else {
                passwordConfirmationHB.getStyleClass().remove("hbox-sign-in-form-focused");
                if (!passwordConfirmationTF.validate() || !(passwordConfirmationTF.textProperty().getValue().equals(passwordTF.textProperty().getValue()))) {
                    shakeNode(true, passwordConfirmationTF);
                }
            }
        });


    }
    private void initializeBindings(){

        UsersManager.getInstance().getSuperBean().
                bindBidirectional("email",emailTF.textProperty());
        UsersManager.getInstance().getSuperBean().
                bindBidirectional("password",passwordTF.textProperty());
        CreditCardsManager.getInstance().getSuperBean().
                bindBidirectional("accountEmail",emailTF.textProperty());
    }



    private void openStep2Clicked() {
        if (!emailTF.validate() || !passwordTF.validate() || !passwordConfirmationTF.validate()) {
            shakeNode(true, emailTF);
            shakeNode(true, passwordConfirmationTF);
            shakeNode(true, passwordTF);
        } else {
            stageManager.switchScene(FXMLFactory.SIGN_UP_SCREEN_2, signUp1VB);
            signUp1VB.setAlignment(Pos.CENTER);
        }
    }

    private void shakeNode(Boolean shakeIT, Node node) {
        if (shakeIT) {
            Shake shake = new Shake(node);
            shake.play();
        }
    }

}
