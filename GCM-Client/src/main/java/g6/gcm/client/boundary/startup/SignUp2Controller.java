package g6.gcm.client.boundary.startup;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.manager.StageManager;
import g6.gcm.client.manager.UsersManager;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * sign up controller step 2
 *
 * @author lowis kayal group 6
 */
public class SignUp2Controller {

  // local variables for internal usage
  StageManager stageManager;
  RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
  RequiredFieldValidator requiredCMValidator = new RequiredFieldValidator();
  RegexValidator phoneNumberValidator = new RegexValidator();
  RegexValidator nameValidator = new RegexValidator();

  // string property to bind with phone number
  StringProperty phoneNumberTextProperty = new SimpleStringProperty("");

  @FXML
  private VBox signUp2VB;
  @FXML
  private HBox firstNameHB;
  @FXML
  private JFXTextField firstNameTF;
  @FXML
  private HBox lastNameHB;
  @FXML
  private JFXTextField lastNameTF;
  @FXML
  private HBox usernameHB;
  @FXML
  private JFXTextField usernameTF;
  @FXML
  private JFXComboBox<String> phoneNumberPrefixCB;
  @FXML
  private JFXTextField phoneNumberTF;
  @FXML
  private JFXButton backToStep1BTN;
  @FXML
  private JFXButton openStep3BTN;

  @FXML
  private void initialize() {
    stageManager = StageManager.getManager();

    initializeActions();
    initializeListeners();
    initializeValidators();
    initializeVariables();
    initializeBindings();

  }
  /**
   * initialize Bindings with UsersManager's bean
   * {@see UsersManager}
   */

  private void initializeBindings() {

    phoneNumberTextProperty.bind(Bindings.createStringBinding(
        () -> phoneNumberPrefixCB.valueProperty().isNotNull().get()
            && phoneNumberTF.textProperty().isNotNull().get() ?
            phoneNumberPrefixCB.valueProperty().get() + phoneNumberTF.textProperty().get()
            : "lowis", phoneNumberPrefixCB.valueProperty(), phoneNumberTF.textProperty()));

    UsersManager.getInstance().getSuperBean()
        .bindBidirectional("firstName", firstNameTF.textProperty());
    UsersManager.getInstance().getSuperBean()
        .bindBidirectional("lastName", lastNameTF.textProperty());
    UsersManager.getInstance().getSuperBean()
        .bindBidirectional("username", usernameTF.textProperty());
    UsersManager.getInstance().getSuperBean().
        bindBidirectional("phoneNumber", phoneNumberTextProperty);
  }

  private void initializeActions() {
    openStep3BTN.setOnMouseClicked(e -> {

      if (!firstNameTF.validate() || !lastNameTF.validate() || !usernameTF.validate()
          || !phoneNumberPrefixCB.validate() || !phoneNumberTF.validate()) {
        shakeNode(firstNameTF.validate(), firstNameTF);
        shakeNode(lastNameTF.validate(), lastNameTF);
        shakeNode(usernameTF.validate(), usernameTF);
        shakeNode(phoneNumberPrefixCB.validate(), phoneNumberPrefixCB);
        shakeNode(phoneNumberTF.validate(), phoneNumberTF);

      } else {

        stageManager.switchScene(FXMLFactory.SIGN_UP_SCREEN_3, signUp2VB);
        signUp2VB.setAlignment(Pos.CENTER);
      }
    });

    backToStep1BTN.setOnMouseClicked(e -> {
      stageManager.switchScene(FXMLFactory.SIGN_UP_SCREEN_1, signUp2VB);
      signUp2VB.setAlignment(Pos.CENTER);
    });

  }

  private void initializeVariables() {
    phoneNumberPrefixCB.getItems().addAll("050", "052", "054", "058");

  }
  private void initializeValidators() {


    // set Patterns
    nameValidator.setRegexPattern("^[a-zA-Z]+$");
    phoneNumberValidator.setRegexPattern("[0-9]{7}");


    // set appropriate messages
    requiredCMValidator.setMessage("Required");
    phoneNumberValidator.setMessage("Must be 7 Digits");
    requiredFieldValidator.setMessage("Required field");
    nameValidator.setMessage("Letters only");

    // add Validators to control
    firstNameTF.getValidators().addAll(requiredFieldValidator,nameValidator);
    lastNameTF.getValidators().addAll(requiredFieldValidator,nameValidator);
    usernameTF.getValidators().add(requiredFieldValidator);
    phoneNumberPrefixCB.getValidators().add(requiredCMValidator);
    phoneNumberTF.getValidators().addAll(requiredFieldValidator, phoneNumberValidator);




  }

  private void initializeListeners() {


    phoneNumberPrefixCB.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == false) {
        if (!phoneNumberPrefixCB.validate()) {
          shakeNode(true, phoneNumberPrefixCB);
        }
      }
      else{
        phoneNumberPrefixCB.show();
      }
    });

    firstNameTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        firstNameHB.getStyleClass().add("hbox-sign-in-form-focused");
      } else {
        firstNameHB.getStyleClass().remove("hbox-sign-in-form-focused");
        if (!firstNameTF.validate()) {
          shakeNode(true, firstNameTF);
        }
      }

    });


    lastNameTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        lastNameHB.getStyleClass().add("hbox-sign-in-form-focused");
      } else {
        lastNameHB.getStyleClass().remove("hbox-sign-in-form-focused");
        if (!lastNameTF.validate()) {
          shakeNode(true, lastNameTF);
        }
      }
    });


    usernameTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        usernameHB.getStyleClass().add("hbox-sign-in-form-focused");
      } else {
        usernameHB.getStyleClass().remove("hbox-sign-in-form-focused");
        if (!usernameTF.validate()) {
          shakeNode(true, usernameTF);
        }
      }
    });


    phoneNumberTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue==false) {
        if (!phoneNumberTF.validate()) {
          shakeNode(true, phoneNumberTF);

        }

      }
    });
  }

  private void shakeNode(Boolean shakeIT, Node node) {
    if (shakeIT) {
      Shake shake = new Shake(node);
      shake.play();
    }
  }

}

