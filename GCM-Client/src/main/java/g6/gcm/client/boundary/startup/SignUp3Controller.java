package g6.gcm.client.boundary.startup;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.manager.CreditCardsManager;
import g6.gcm.client.manager.StageManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class SignUp3Controller {

    StageManager stageManager;
    RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
    RegexValidator ccFullnameValidator = new RegexValidator();
    RegexValidator cardHoldersIDValidator = new RegexValidator();
    RegexValidator creditCardNumberValidator = new RegexValidator();
    RegexValidator cvvValidator = new RegexValidator();
    // Date sqlDate;
    @FXML
    private VBox signUp3VB;

    @FXML
    private HBox cardholdersNameHB;

    @FXML
    private JFXTextField cardholdersNameTF;

    @FXML
    private HBox cardholdersIDHB;

    @FXML
    private JFXTextField cardholdersIDTF;

    @FXML
    private HBox ccNumberHB;

    @FXML
    private JFXTextField ccNumberTF;

    @FXML
    private HBox expirationDateHB;

    @FXML
    private JFXDatePicker expirationDateDP;

    @FXML
    private HBox cvvHB;

    @FXML
    private JFXTextField cvvTF;

    @FXML
    private JFXButton backToStep2BTN;

    @FXML
    private JFXButton signUpBTN;


    @FXML
    private void initialize() {
        stageManager = StageManager.getManager();
        expirationDateDP.setValue(LocalDate.now());

        initializeActions();
        initializeListeners();
        initializeValidators();
        initializeBindings();
    }

    private void initializeBindings() {


        CreditCardsManager.getInstance().getSuperBean().
                bindBidirectional("cardholdersName", cardholdersNameTF.textProperty());
        CreditCardsManager.getInstance().getSuperBean().
                bindBidirectional("cardholdersID", cardholdersIDTF.textProperty());
        CreditCardsManager.getInstance().getSuperBean().
                bindBidirectional("creditCardNumber", ccNumberTF.textProperty());
        CreditCardsManager.getInstance().getSuperBean().
                bindBidirectional("creditCardCSV", cvvTF.textProperty());
        CreditCardsManager.getInstance().getSuperBean().
                bindBidirectional("creditCardExpirationDate", expirationDateDP.valueProperty(), LocalDate.class);
    }


    private void initializeValidators() {

        //set Patterns

        ccFullnameValidator.setRegexPattern("^[A-Za-z]+\\s[A-Za-z]+(\\s)*$");
        cardHoldersIDValidator.setRegexPattern("[0-9]{9}");
        cvvValidator.setRegexPattern("[0-9]{3}");
        creditCardNumberValidator.setRegexPattern("[0-9]{16}");

        // set appropriate messages
        requiredFieldValidator.setMessage("Required field");
        ccFullnameValidator.setMessage("Wrong Full Name pattern");
        cardHoldersIDValidator.setMessage("Wrong ID format");
        creditCardNumberValidator.setMessage("Wrong number format");
        cvvValidator.setMessage("3 digits only");

        //add Validators to control
        cardholdersNameTF.getValidators().addAll(requiredFieldValidator, ccFullnameValidator);
        cardholdersIDTF.getValidators().addAll(requiredFieldValidator, cardHoldersIDValidator);
        ccNumberTF.getValidators().addAll(requiredFieldValidator, creditCardNumberValidator);
        cvvTF.getValidators().addAll(requiredFieldValidator, cvvValidator);
    }


    private void initializeListeners() {

        cardholdersNameTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                cardholdersNameHB.getStyleClass().add("hbox-sign-in-form-focused");
            } else {
                cardholdersNameHB.getStyleClass().remove("hbox-sign-in-form-focused");
                if (!cardholdersNameTF.validate()) {
                    shakeNode(true, cardholdersNameTF);
                }
            }
        });


        cardholdersIDTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                cardholdersIDHB.getStyleClass().add("hbox-sign-in-form-focused");
            } else {
                cardholdersIDHB.getStyleClass().remove("hbox-sign-in-form-focused");
                if (!cardholdersIDTF.validate()) {
                    shakeNode(true, cardholdersIDTF);
                }
            }
        });


        ccNumberTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ccNumberHB.getStyleClass().add("hbox-sign-in-form-focused");
            } else {
                ccNumberHB.getStyleClass().remove("hbox-sign-in-form-focused");
                if (!ccNumberTF.validate()) {
                    shakeNode(true, ccNumberTF);
                }
            }
        });


        cvvTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                cvvHB.getStyleClass().add("hbox-sign-in-form-focused");
            } else {
                cvvHB.getStyleClass().remove("hbox-sign-in-form-focused");
                if (!cvvTF.validate()) {
                    shakeNode(true, cvvTF);
                }
            }
        });

        expirationDateDP.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.isBefore(LocalDate.now())) {
                Shake shake = new Shake(expirationDateDP);
                shake.play();
                expirationDateDP.setValue(oldValue);
            }
        }));

    }

    private void initializeActions() {
        signUpBTN.setOnMouseClicked(e -> {
            if (!cardholdersNameTF.validate() || !ccNumberTF.validate() ||
                    !cardholdersIDTF.validate() || !cvvTF.validate() ||
                    expirationDateDP.valueProperty().getValue().isBefore(LocalDate.now())) {
                shakeNode(cardholdersNameTF.validate(), cardholdersNameTF);
                shakeNode(ccNumberTF.validate(), ccNumberTF);
                shakeNode(cardholdersIDTF.validate(), cardholdersIDTF);
                shakeNode(cvvTF.validate(), cvvTF);
                shakeNode(expirationDateDP.valueProperty().getValue().isBefore(LocalDate.now()), expirationDateDP);
            } else {
                //everything is valid send request
                CreditCardsManager.getInstance().createCC();

            }
        });
        backToStep2BTN.setOnMouseClicked(e -> {
            stageManager.switchScene(FXMLFactory.SIGN_UP_SCREEN_2, signUp3VB);
            signUp3VB.setAlignment(Pos.CENTER);
        });

    }

    private void shakeNode(Boolean shakeIT, Node node) {
        if (shakeIT) {
            Shake shake = new Shake(node);
            shake.play();
        }
    }

}
