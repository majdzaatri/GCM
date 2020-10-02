package g6.gcm.client.boundary.customer;

import animatefx.animation.Shake;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import g6.gcm.client.manager.CreditCardsManager;
import g6.gcm.client.manager.StageManager;
import g6.gcm.client.manager.UsersManager;
import g6.gcm.core.entity.CreditCardDTO;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ServersCommands;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class CustomerProfileEditorUI2Controller {


    StageManager stageManager;
    @FXML
    private AnchorPane mainViewAP;
    @FXML
    private JFXButton EditPersonalInfoBTN;
    @FXML
    private JFXButton ChangePasswordBTN;
    @FXML
    private JFXButton EditPaymentMethodsBTN;
    @FXML
    private VBox EditPersonalInformationVB;
    @FXML
    private JFXTextField FirstNameTF;
    @FXML
    private JFXTextField LastNameTF;
    @FXML
    private JFXTextField UserNameTF;
    @FXML
    private JFXComboBox<String> PhoneNumberCB;
    @FXML
    private JFXTextField PhoneNumberTF;
    @FXML
    private JFXButton SavePersonalInfoBTN;
    @FXML
    private VBox ChangePasswordVB;
    @FXML
    private JFXPasswordField OldPasswordTF;
    @FXML
    private JFXPasswordField NewPasswordTF;
    @FXML
    private JFXPasswordField ConfirmPasswordTF;
    @FXML
    private JFXButton ConfirmPasswordBTN;
    @FXML
    private VBox EditPaymentMethodsVB;
    @FXML
    private Label CardholderNameLabel;
    @FXML
    private Label CreditCardNumberLabel;
    @FXML
    private Label ExpirationDateLabel;
    @FXML
    private Button DeletePaymentMethodBTN;
    @FXML
    private JFXButton AddPaymentMethodBTN;
    @FXML
    private VBox AddPaymentMethodVB;
    @FXML
    private JFXTextField CardholderFullNameTF;
    @FXML
    private JFXTextField CardholderIdTF;
    @FXML
    private JFXTextField CreditCardNumberTF;
    @FXML
    private JFXTextField CvvTF;
    @FXML
    private JFXDatePicker ExpirationDateDP;
    @FXML
    private JFXButton SavePaymentMethodBTN;
    @FXML
    private Label InformationUpdatedLBL;
    @FXML
    private Label PasswordUpdatedLBL;

    private ObjectProperty<ServersCommands> updated = new SimpleObjectProperty<>();

    RegexValidator phoneNumberValidator = new RegexValidator();
    RegexValidator passwordFieldValidator = new RegexValidator();
    RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
    RegexValidator ccFullnameValidator = new RegexValidator();
    RegexValidator cardHoldersIDValidator = new RegexValidator();
    RegexValidator creditCardNumberValidator = new RegexValidator();
    RegexValidator cvvValidator = new RegexValidator();
    RequiredFieldValidator dontMatchValidator = new RequiredFieldValidator();
    RequiredFieldValidator oldPasswordDontMatch = new RequiredFieldValidator();

    @FXML
    private void initialize() {

        stageManager = StageManager.getManager();
        stageManager.setMainViewAP(mainViewAP);
        initializeValidators();

      updated.bind(Bindings.createObjectBinding(
          () -> (ServersCommands) UsersManager.getInstance().getBean().getRequest(),
          UsersManager.getInstance().beanProperty()));

        PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
        updated.addListener(((observable, oldValue, newValue) -> {
            switch (newValue) {

                case SCCESSFUL_UPDATE_PROFILE:
                    InformationUpdatedLBL.setVisible(true);
                    visiblePause.setOnFinished(event -> InformationUpdatedLBL.setVisible(false));
                    visiblePause.play();
                    break;

                case CREDIT_CARD_DELETED:
                    System.out.println("Credit Card deleted successfully");

                case SUCCESSFUL_UPDATE_PASSWORD:
                    PasswordUpdatedLBL.setVisible(true);
                    visiblePause.setOnFinished(event -> PasswordUpdatedLBL.setVisible(false));
                    visiblePause.play();
                    break;
            }
        }));

        PhoneNumberCB.getItems().addAll("050", "052", "054", "058");

      FirstNameTF.setText(((UserDTO) (UsersManager.getInstance().getBean())).getFirstName());

      LastNameTF.setText(((UserDTO) (UsersManager.getInstance().getBean())).getLastName());

      UserNameTF.setText((((UserDTO) (UsersManager.getInstance().getBean()))).getUsername());

      PhoneNumberTF.setText(
          ((((UserDTO) (UsersManager.getInstance().getBean())).getPhoneNumber().substring(3))));

      PhoneNumberCB.getSelectionModel().select(
          ((((UserDTO) (UsersManager.getInstance().getBean())).getPhoneNumber().substring(0, 3))));

        //TODO:Fix Credit Card loading info
        CreditCardsManager.getCreditCardByEmail();
        CreditCardsManager.getInstance().beanProperty().addListener(((observable, oldValue, newValue) -> {
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
            if (oldValue != newValue) {
                CardholderNameLabel.setText(((CreditCardDTO) CreditCardsManager.getInstance().getBean()).getCardholdersName());
                CreditCardNumberLabel.setText("************" + ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).getCreditCardNumber().substring(11));
                ExpirationDateLabel.setText(DATE_FORMAT.format(((CreditCardDTO) CreditCardsManager.getInstance().getBean()).getCreditCardExpirationDate()));
            }
        }));


        SavePersonalInfoBTN.setOnMouseClicked(event -> {
            if (FirstNameTF.validate() && LastNameTF.validate() && UserNameTF.validate() && PhoneNumberCB.validate() && PhoneNumberTF.validate()) {
              if (FirstNameTF.getText() != ((UserDTO) UsersManager.getInstance().getBean())
                  .getFirstName() || LastNameTF.getText() != ((UserDTO) UsersManager.getInstance()
                  .getBean()).getLastName() ||
                  PhoneNumberCB.getSelectionModel().getSelectedItem() != ((UserDTO) UsersManager
                      .getInstance().getBean()).getPhoneNumber().substring(0, 3) ||
                  PhoneNumberTF.getText() != ((UserDTO) UsersManager.getInstance().getBean())
                      .getPhoneNumber().substring(3) ||
                  UserNameTF.getText() != ((UserDTO) UsersManager.getInstance().getBean())
                      .getUsername()) {

                ((UserDTO) UsersManager.getInstance().getBean())
                    .setFirstName(FirstNameTF.getText());

                ((UserDTO) UsersManager.getInstance().getBean()).setLastName(LastNameTF.getText());

                if (PhoneNumberCB.getSelectionModel().isEmpty()) {
                  ((UserDTO) UsersManager.getInstance().getBean()).setPhoneNumber(
                      ((UserDTO) UsersManager.getInstance().getBean()).getPhoneNumber()
                          .substring(0, 3) + PhoneNumberTF.getText());
                } else {
                  ((UserDTO) UsersManager.getInstance().getBean()).setPhoneNumber(
                      PhoneNumberCB.getSelectionModel().getSelectedItem() + PhoneNumberTF
                          .getText());
                }
                ((UserDTO) UsersManager.getInstance().getBean()).setUsername(UserNameTF.getText());
                UsersManager.UpdateUser();
                }
            } else {
                shakeNode(!FirstNameTF.validate(), FirstNameTF);
                shakeNode(!LastNameTF.validate(), LastNameTF);
                shakeNode(!UserNameTF.validate(), UserNameTF);
                shakeNode(!PhoneNumberCB.validate(), PhoneNumberCB);
                shakeNode(!PhoneNumberTF.validate(), PhoneNumberTF);
            }
        });

        ConfirmPasswordBTN.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if (OldPasswordTF.validate() && NewPasswordTF.validate() && ConfirmPasswordTF.validate()) {
              if (OldPasswordTF.getText()
                  .equals(((UserDTO) (UsersManager.getInstance().getBean())).getPassword())) {
                    if (NewPasswordTF.getText().equals(ConfirmPasswordTF.getText())) {
                        if (NewPasswordTF.getText().equals(ConfirmPasswordTF.getText())) {
                          if (OldPasswordTF.getText().equals(
                              ((UserDTO) (UsersManager.getInstance().getBean())).getPassword())) {
                            UsersManager.UpdateUserPassword();
                            }
                        }
                    } else {
                        alert.setContentText("New password and confirm password don't match");
                        alert.show();
                    }
                } else {
                    alert.setContentText("Old password don't match with your current");
                    alert.show();
                }
            } else {
                shakeNode(!OldPasswordTF.validate(), OldPasswordTF);
                shakeNode(!NewPasswordTF.validate(), NewPasswordTF);
                shakeNode(!ConfirmPasswordTF.validate(), ConfirmPasswordTF);
            }
        });

        SavePaymentMethodBTN.setOnMouseClicked(event -> {
            if (CardholderFullNameTF.validate() && CardholderIdTF.validate() && CreditCardNumberTF.validate() && ExpirationDateDP.validate() && CvvTF.validate()) {
                if (!(CardholderFullNameTF.getText().isEmpty()) && (!CardholderIdTF.getText().isEmpty())
                        && (!CreditCardNumberTF.getText().isEmpty()) && (!ExpirationDateDP.getValue().equals(null)) && (!CvvTF.getText().isEmpty())) {
                  ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).setAccountEmail(
                      ((UserDTO) (UsersManager.getInstance().getBean())).getEmail());
                    ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).setCardholdersID(CardholderIdTF.getText());
                    ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).setCardholdersName(CardholderFullNameTF.getText());
                    ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).setCreditCardNumber(CreditCardNumberTF.getText());
//                    ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).setCreditCardExpirationDate(Date.valueOf(ExpirationDateDP.getValue()));
                    ((CreditCardDTO) CreditCardsManager.getInstance().getBean()).setCreditCardCSV(CvvTF.getText());
                    CreditCardsManager.createCC();
                }
            } else {
                shakeNode(!CardholderFullNameTF.validate(), CardholderFullNameTF);
                shakeNode(!CardholderIdTF.validate(), CardholderIdTF);
                shakeNode(!CreditCardNumberTF.validate(), CreditCardNumberTF);
                shakeNode(!ExpirationDateDP.validate(), ExpirationDateDP);
                shakeNode(!CvvTF.validate(), CvvTF);
            }
        });

        DeletePaymentMethodBTN.setOnMouseClicked(event -> {
            CreditCardsManager.deleteCreditCard();
        });

        EditPersonalInfoBTN.getStyleClass().add("purple-sided-button-on-focused");
        EditPersonalInformationVB.setVisible(true);
        EditPersonalInfoBTN.setOnMouseClicked(event -> {
            ManageScreen(EditPersonalInfoBTN, EditPersonalInformationVB);
        });

        ChangePasswordBTN.setOnMouseClicked(event -> {
            ManageScreen(ChangePasswordBTN, ChangePasswordVB);
        });

        EditPaymentMethodsBTN.setOnMouseClicked(event -> {
            ManageScreen(EditPaymentMethodsBTN, EditPaymentMethodsVB);
        });

        AddPaymentMethodBTN.setOnMouseClicked(event -> {
            if (AddPaymentMethodVB.isVisible())
                AddPaymentMethodVB.setVisible(false);
            else
                AddPaymentMethodVB.setVisible(true);
        });
    }


    private void ManageScreen(JFXButton button, VBox vbox) {
        if (!(vbox.isVisible())) {
            if (vbox.equals(EditPersonalInformationVB)) {
                ChangePasswordVB.setVisible(false);
                EditPaymentMethodsVB.setVisible(false);
                EditPersonalInformationVB.setVisible(true);
                EditPaymentMethodsBTN.getStyleClass().remove("purple-sided-button-on-focused");
                ChangePasswordBTN.getStyleClass().remove("purple-sided-button-on-focused");
                EditPersonalInfoBTN.getStyleClass().add("purple-sided-button-on-focused");
            }
            if (vbox.equals(ChangePasswordVB)) {
                EditPersonalInformationVB.setVisible(false);
                EditPaymentMethodsVB.setVisible(false);
                ChangePasswordVB.setVisible(true);
                EditPersonalInfoBTN.getStyleClass().remove("purple-sided-button-on-focused");
                EditPaymentMethodsBTN.getStyleClass().remove("purple-sided-button-on-focused");
                ChangePasswordBTN.getStyleClass().add("purple-sided-button-on-focused");
            }
            if (vbox.equals(EditPaymentMethodsVB)) {
                EditPersonalInformationVB.setVisible(false);
                ChangePasswordVB.setVisible(false);
                EditPaymentMethodsVB.setVisible(true);
                EditPersonalInfoBTN.getStyleClass().remove("purple-sided-button-on-focused");
                ChangePasswordBTN.getStyleClass().remove("purple-sided-button-on-focused");
                EditPaymentMethodsBTN.getStyleClass().add("purple-sided-button-on-focused");
            }

        }
    }

    private void shakeNode(Boolean shakeIT, Node node) {
        if (shakeIT) {
            Shake shake = new Shake(node);
            shake.play();
        }
    }

    private void initializeValidators() {
        passwordFieldValidator.setRegexPattern(".{8,}");
        requiredFieldValidator.setMessage("Required field");
        passwordFieldValidator.setMessage("8 characters or more");
        phoneNumberValidator.setRegexPattern("[0-9]{7}");
        phoneNumberValidator.setMessage("Must be 7 Digits");
        dontMatchValidator.setMessage("Passwords don't match");
        oldPasswordDontMatch.setMessage("Password don't match");

        requiredFieldValidator.setMessage("Required field");
        ccFullnameValidator.setMessage("Wrong Full Name pattern");
        cardHoldersIDValidator.setMessage("Wrong ID format");
        creditCardNumberValidator.setMessage("Wrong number format");
        cvvValidator.setMessage("3 digits only");

        ccFullnameValidator.setRegexPattern("^[A-Za-z]+\\s[A-Za-z]+$");
        cardHoldersIDValidator.setRegexPattern("[0-9]{9}");
        cvvValidator.setRegexPattern("[0-9]{3}");
        creditCardNumberValidator.setRegexPattern("[0-9]{16}");

        OldPasswordTF.getValidators().addAll(requiredFieldValidator, passwordFieldValidator, oldPasswordDontMatch);
        NewPasswordTF.getValidators().addAll(requiredFieldValidator, passwordFieldValidator);
        ConfirmPasswordTF.getValidators().addAll(requiredFieldValidator, passwordFieldValidator, dontMatchValidator);
        FirstNameTF.getValidators().add(requiredFieldValidator);
        LastNameTF.getValidators().add(requiredFieldValidator);
        UserNameTF.getValidators().add(requiredFieldValidator);
        PhoneNumberCB.getValidators().add(requiredFieldValidator);
        PhoneNumberTF.getValidators().addAll(requiredFieldValidator, phoneNumberValidator);

        CardholderFullNameTF.getValidators().addAll(requiredFieldValidator, ccFullnameValidator);
        CardholderIdTF.getValidators().addAll(requiredFieldValidator, cardHoldersIDValidator);
        CreditCardNumberTF.getValidators().addAll(requiredFieldValidator, creditCardNumberValidator);
        CvvTF.getValidators().addAll(requiredFieldValidator, cvvValidator);
        ExpirationDateDP.getValidators().add(requiredFieldValidator);
    }

}
