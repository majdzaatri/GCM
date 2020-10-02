package g6.gcm.client.boundary.startup;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import g6.gcm.client.manager.StageManager;
import g6.gcm.client.manager.UsersManager;
import g6.gcm.core.entity.UserDTO;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * sign in controller
 *
 * @author lowis kayal group 6
 */

public class SignInUIController {


    RegexValidator emailFieldValidator = new RegexValidator();
    RegexValidator passwordFieldValidator = new RegexValidator();
    RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

  @FXML
  private VBox signInVB;

  @FXML
  private HBox emailHB;

  @FXML
  private JFXTextField emailTF;

  @FXML
  private HBox passwordHB;

  @FXML
  private JFXPasswordField passwordTF;

  @FXML
  private JFXButton forgotPasswordBTN;

  @FXML
  private JFXButton signInBTN;


  private StageManager stageManager;

  @FXML
  private void initialize() {


    initializeBindings();
    initializeActions();
    initializeValidators();
    initializeListeners();

  }

  /**
   * initializes each single button functionality
   */
  private void initializeActions() {
    signInBTN.setOnMouseClicked(e ->
            signInClicked()
    );

    forgotPasswordBTN.setOnMouseClicked(e -> {
      ((UserDTO) UsersManager.getInstance().getBean()).setEmail("Julian@Broude.co.il");
      ((UserDTO) UsersManager.getInstance().getBean()).setPassword("74107410");
      UsersManager.authenticateLoginCredentials();
    });
  }

  /**
   * initializes each local validator and assigns it to it proper FXML variable
   */
  private void initializeValidators() {


    // set Patterns
    emailFieldValidator.setRegexPattern(
            "^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?" +
                    "(GCM|gcm|gmail|yahoo|hotmail|live|Broude|s.braude|broude|" +
                    "braude|G6|g6)\\.(com|co.il|ac.il)$");

    passwordFieldValidator.setRegexPattern(".{8,}");

    // set appropriate messages
    emailFieldValidator.setMessage("Illegal email address");
    passwordFieldValidator.setMessage("8 characters or more");
    requiredFieldValidator.setMessage("Required field");

    // add Validators to control
    emailTF.getValidators().addAll(requiredFieldValidator, emailFieldValidator);
    passwordTF.getValidators().addAll(requiredFieldValidator, passwordFieldValidator);
  }

  /**
   * initialize Listeners for both email and password text fields
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
  }

  /**
   * initialize Bindings with UsersManager's bean
   * {@see UsersManager}
   */
  private void initializeBindings() {

    UsersManager.getInstance().getSuperBean().
            bindBidirectional("email", emailTF.textProperty());
    UsersManager.getInstance().getSuperBean().
            bindBidirectional("password", passwordTF.textProperty());
  }

  /**
   * applies shake effect on node when shakeIT is true
   * that means when the node isn't valid due it's validation process
   * @param shakeIT
   * @param node
   */
  private void shakeNode(Boolean shakeIT, Node node) {
    if (shakeIT) {
      Shake shake = new Shake(node);
      shake.play();
    }
  }






  private void signInClicked(){
    if (emailTF.validate() && passwordTF.validate()) {
      UsersManager.authenticateLoginCredentials();
    }
  }
}
