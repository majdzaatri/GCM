package g6.gcm.client.boundary.startup.backup;

import animatefx.animation.Shake;
import com.jfoenix.controls.*;
import g6.gcm.client.manager.StageManager;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;


public class SignUpScreenController {


    BooleanExpression invalidEmail;
    BooleanExpression invalidPassword;
    BooleanExpression invalidConfirmPassword;
    BooleanExpression invalidUserName;
    BooleanExpression invalidFullName;
    BooleanExpression invalidPhoneNumber;
    BooleanExpression invalidAreaCode;
    BooleanExpression invalidCardHolderName;
    BooleanExpression invalidCardHoldersID;
    BooleanExpression invalidCreditCard;
    BooleanExpression invalidCVV;
    EmailValidator emailValidator = EmailValidator.getInstance();
    @FXML
    private VBox signUpForm;
    @FXML
    private JFXTextField FullNameTF;
    @FXML
    private JFXTextField UsernameTF;
    @FXML
    private JFXTextField EmailTF;
    @FXML
    private JFXPasswordField PasswordTF;
    @FXML
    private JFXPasswordField ConfirmPasswordTF;
    @FXML
    private JFXButton NextSignUpBT;
    @FXML
    private VBox signupForm2;
    @FXML
    private JFXComboBox<String> PrePhoneNumberCM;
    @FXML
    private JFXTextField PhoneNumberTF;
    @FXML
    private JFXTextField CardHolderNameTF;
    @FXML
    private JFXTextField CardHolderIDTF;
    @FXML
    private JFXTextField CreditCardTF;
    @FXML
    private JFXDatePicker ExpirationDatePicker;
    @FXML
    private JFXTextField cvvTF;
    @FXML
    private JFXButton SignUpBT;
    private StageManager stageManager;
    @FXML
    private JFXButton backBTN;

    @FXML
    private void initialize() {
        stageManager = StageManager.getManager();


        initializeActions();
        initializeListeners();
        initializeVariables();
        initializeExpressions();
    }


    private void initializeVariables() {
        PrePhoneNumberCM.getItems().addAll("050", "052", "054", "058");
        ExpirationDatePicker.setValue(LocalDate.now());
    }

    private void initializeActions() {
        NextSignUpBT.setOnMouseClicked(e -> signUpNextClicked());
        SignUpBT.setOnAction(e -> SignUpClicked());
        backBTN.setOnAction(e -> BackButtonClicked());
    }

    private void initializeListeners() {

        ExpirationDatePicker.valueProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println(observable.getValue());
            if (newValue.isBefore(LocalDate.now())) {
                Shake shake = new Shake(ExpirationDatePicker);
                shake.play();
                ExpirationDatePicker.setValue(oldValue);
            } else {
//                java.sql.Date sqlDate = java.sql.Date.valueOf(observable.getValue());
//                CreditCardsManager.getCreditCard().setCreditCardExpirationDate(sqlDate);

            }
        }));

        PrePhoneNumberCM.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (PrePhoneNumberCM.getValue() == null) {
                    Shake shake = new Shake(PrePhoneNumberCM);
                    shake.play();
                }
            }
        });

        PhoneNumberTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (invalidPhoneNumber.get()) {
                    Shake shake = new Shake(PhoneNumberTF);
                    shake.play();
                }

            }
        });

        ConfirmPasswordTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (invalidConfirmPassword.get()) {
                    Shake shake = new Shake(ConfirmPasswordTF);
                    shake.play();
                }
            }
        });
//
//        EmailTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (invalidEmail.get()) {
//                    Shake shake = new Shake(EmailTF);
//                    shake.play();
//                } else {
//                    UsersManager.getThisUser().setEmail(EmailTF.getText());
//                }
//            }
//        });
//
//        FullNameTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (invalidFullName.get()) {
//                    Shake shake = new Shake(FullNameTF);
//                    shake.play();
//                } else {
//
//                    String[] splitarray = FullNameTF.getText().split("[\\s]");
//                    UsersManager.getThisUser().setFirstName(splitarray[0]);
//                    UsersManager.getThisUser().setLastName(splitarray[1]);
//                }
//            }
//        });
//
//        PasswordTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (invalidPassword.get()) {
//                    Shake shake = new Shake(PasswordTF);
//                    shake.play();
//                } else {
//                    UsersManager.getThisUser().setPassword(PasswordTF.getText());
//                }
//            }
//        });
//
//        UsernameTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (invalidUserName.get()) {
//                    Shake shake = new Shake(UsernameTF);
//                    shake.play();
//                } else {
//                    UsersManager.getThisUser().setUsername(UsernameTF.getText());
//                }
//            }
//        });

//        CardHolderNameTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (invalidCardHolderName.get()) {
//                    Shake shake = new Shake(CardHolderNameTF);
//                    shake.play();
//                } else {
//                    CreditCardsManager.getCreditCard().setCardholdersName(CardHolderNameTF.getText());
//                }
//
//            }
//        });
//
//        CardHolderIDTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (invalidCardHoldersID.get()) {
//                    Shake shake = new Shake(CardHolderIDTF);
//                    shake.play();
//                } else {
//                    CreditCardsManager.getCreditCard().setCardholdersID(CardHolderIDTF.getText());
//
//                }
//
//            }
//        });
//
//        CreditCardTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (invalidCreditCard.get()) {
//                    Shake shake = new Shake(CreditCardTF);
//                    shake.play();
//                } else {
//                    CreditCardsManager.getCreditCard().setCreditCardNumber(CreditCardTF.getText());
//
//                }
//
//            }
//        });
//
//        cvvTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                if (invalidCVV.get()) {
//                    Shake shake = new Shake(cvvTF);
//                    shake.play();
//                } else {
//                    CreditCardsManager.getCreditCard().setCreditCardCSV(cvvTF.getText());
//
//                }
//
//            }
//        });


    }

    private void initializeExpressions() {

        StringBinding validEmailExpression = Bindings.createStringBinding(
                () -> emailValidator.isValid(EmailTF.getText()) ? EmailTF.getText() : "",
                EmailTF.textProperty());

        StringBinding validFullNameExpression = Bindings.createStringBinding(
                () -> isValidFullName(FullNameTF.getText()) ? FullNameTF.getText() : "",
                FullNameTF.textProperty());

        StringBinding validCardHoldersNameExpression = Bindings.createStringBinding(
                () -> isValidFullName(CardHolderNameTF.getText()) ? CardHolderNameTF.getText() : "",
                CardHolderNameTF.textProperty());

        StringBinding validCardHoldersIDExpression = Bindings.createStringBinding(
                () -> isValidNumber(CardHolderIDTF.getText()) ? CardHolderIDTF.getText() : "",
                CardHolderIDTF.textProperty());


        StringBinding validPhoneNumber = Bindings.createStringBinding(
                () -> isValidNumber(PhoneNumberTF.getText()) ? PhoneNumberTF.getText() : "",
                PhoneNumberTF.textProperty());

        StringBinding validCreditCard = Bindings.createStringBinding(
                () -> isValidNumber(CreditCardTF.getText()) ? CreditCardTF.getText() : "",
                CreditCardTF.textProperty());

        StringBinding validCVV = Bindings.createStringBinding(
                () -> isValidNumber(cvvTF.getText()) ? cvvTF.getText() : "",
                cvvTF.textProperty());


//        invalidAreaCode = PrePhoneNumberCM.getSelectionModel().selectedIndexProperty().isEqualTo(-1);

        invalidPhoneNumber = PhoneNumberTF.textProperty().length().greaterThan(7).or(PhoneNumberTF.textProperty().length().lessThan(7))
                .or(PhoneNumberTF.textProperty().isNotEqualTo(validPhoneNumber));

        invalidEmail = EmailTF.textProperty().isEmpty()
                .or(EmailTF.textProperty().isNotEqualTo(validEmailExpression));

        invalidPassword = PasswordTF.textProperty().length().lessThan(8).or(PasswordTF.textProperty().isEmpty());

        invalidConfirmPassword = ConfirmPasswordTF.textProperty().isNotEqualTo(PasswordTF.textProperty()).or(ConfirmPasswordTF.textProperty().isEmpty()).or(ConfirmPasswordTF.textProperty().length().lessThan(8));

        invalidUserName = UsernameTF.textProperty().isEmpty();

        invalidFullName = FullNameTF.textProperty().isNotEqualTo(validFullNameExpression).or(FullNameTF.textProperty().isEmpty());

        invalidCardHolderName = CardHolderNameTF.textProperty().isNotEqualTo(validCardHoldersNameExpression).or(CardHolderNameTF.textProperty().isEmpty());

        invalidCardHoldersID = CardHolderIDTF.textProperty().isNotEqualTo(validCardHoldersIDExpression).or(CardHolderIDTF.textProperty().isEmpty()).or(CardHolderIDTF.textProperty().length().lessThan(9));

        invalidCreditCard = CreditCardTF.textProperty().isNotEqualTo(validCreditCard).or(CreditCardTF.textProperty().isEmpty()).or(CreditCardTF.textProperty().length().lessThan(16)).or
                (CreditCardTF.textProperty().length().greaterThan(16));

        invalidCVV = cvvTF.textProperty().isNotEqualTo(validCVV).or(cvvTF.textProperty().length().lessThan(3)).or(cvvTF.textProperty().length().greaterThan(3));


    }

    private void shakeNode(Boolean animate, Node node) {
        if (animate) {
            Shake shake = new Shake(node);
            shake.play();
        }
    }

    public Boolean isValidFullName(String name) {
        return (name.matches("^[A-Za-z]+\\s[A-Za-z]+$"));
    }

    public Boolean isValidNumber(String number) {
        return (number.matches("^[0-9]+$"));
    }

    private void signUpNextClicked() {
        if (invalidUserName.get() || invalidConfirmPassword.get() || invalidEmail.get() || invalidFullName.get() || invalidPassword.get()) {
            shakeNode(invalidConfirmPassword.get(), ConfirmPasswordTF);
            shakeNode(invalidUserName.get(), UsernameTF);
            shakeNode(invalidPassword.get(), PasswordTF);
            shakeNode(invalidFullName.get(), FullNameTF);
            shakeNode(invalidEmail.get(), EmailTF);
        } else {
            signUpForm.setVisible(false);
            signupForm2.setVisible(true);

        }

    }

    private void SignUpClicked() {

//        if (invalidCVV.get() || invalidCreditCard.get() || invalidCardHoldersID.get() || invalidCardHolderName.get() || invalidAreaCode.get() || invalidPhoneNumber.get()) {
//            shakeNode(invalidPhoneNumber.get(), PhoneNumberTF);
//            shakeNode(invalidCVV.get(), cvvTF);
//            shakeNode(invalidCreditCard.get(), CreditCardTF);
//            shakeNode(invalidCardHoldersID.get(), CardHolderIDTF);
//            shakeNode(invalidCardHolderName.get(), CardHolderNameTF);
//            shakeNode(invalidAreaCode.get(), PrePhoneNumberCM);
//            shakeNode(ExpirationDatePicker.getValue().isBefore(LocalDate.now()), ExpirationDatePicker);
//        } else {
//            UsersManager.getThisUser().setPhoneNumber(PrePhoneNumberCM.valueProperty().getValue() + PhoneNumberTF.getText());
//        }
    }

    private void BackButtonClicked() {
        signupForm2.setVisible(false);
        signUpForm.setVisible(true);
    }
}



