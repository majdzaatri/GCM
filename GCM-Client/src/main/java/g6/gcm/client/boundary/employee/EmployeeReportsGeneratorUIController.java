package g6.gcm.client.boundary.employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import g6.gcm.client.manager.*;
import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.MapDTO;
import g6.gcm.core.entity.ReportDTO;

import java.time.LocalDate;
import java.util.ArrayList;

import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.interfaces.Renderable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckListView;

import javax.swing.*;

/*Jeris*/

public class EmployeeReportsGeneratorUIController {

    private String fromDatee;

    private String toDatee;

    @FXML
    private JFXButton genButton;
    @FXML
    private JFXDatePicker fromDate;

    @FXML
    private JFXDatePicker toDate;

    @FXML
    private CheckListView<String> beansList;

    @FXML
    private AnchorPane mainViewAP;

    @FXML
    private CheckListView<String> itemsCLV;

    private StageManager stageManager;
    @FXML
    private JFXTextField emailTextField;

    @FXML
    private JFXButton ButtonByUser;
    @FXML
    private CheckListView<String> UsersListView;

    @FXML
    private void initialize() {


        //emailTextField.textProperty().bind(UsersListView.getSelectionModel().selectedItemProperty());
        CityDTO cityDto = new CityDTO();
        cityDto.setRequest(ClientsInquiries.ALL_CITIES);
        GCMClient.send(cityDto);


        UserDTO userDto = new UserDTO();
        userDto.setRequest(ClientsInquiries.ALL_USERS);
        GCMClient.send(userDto);


        stageManager = StageManager.getManager();

        ReportDTO report = new ReportDTO();
        itemsCLV.getItems().addAll("Number of maps", "Number of One Shot Deals",
                "Number of subscribers", "Number of subscription renewals", "Number of views",
                "Number of downloads");


        UsersManager.getInstance().beansListProperty().addListener(((observable, oldValue, newValue) ->
        {
            for (Renderable user : UsersManager.getInstance().getBeansList()) {
                UsersListView.getItems().add(((UserDTO) user).getEmail());
            }
        }));


        CitiesManager.getInstance().beansListProperty().addListener((observable, oldValue, newValue) ->
        {
            for (Renderable city : CitiesManager.getInstance().getBeansList()) {
                beansList.getItems().add(((CityDTO) city).getCityName());
            }

        });

        //beansList.getItems().addAll(MapsManager.getInstance().getBeansList());
        //GCMClient.send(report);
    }

    @FXML
    void genButtonClicked(ActionEvent event) {

        ArrayList<String> types = new ArrayList<String>();
        types.addAll(itemsCLV.getCheckModel().getCheckedItems());
        for (String s : beansList.getCheckModel().getCheckedItems()) {
            if (fromDate.getValue() == null || toDate.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Dates");
                alert.setHeaderText(null);
                alert.setContentText(
                        "You haven't specified dates, So we will take all dates.");

                alert.showAndWait();

                fromDate.setValue(LocalDate.parse("2018-01-01"));
                toDate.setValue(LocalDate.parse("2022-01-01"));
            }

            ReportsManager.generateReport(fromDate.getValue(), toDate.getValue(), s, types);
        }
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "The Report has been downloaded to your Desktop");
    }

    @FXML
    void ButtonByUserClicked(ActionEvent event) {

        for (String UserEmail : UsersListView.getCheckModel().getCheckedItems()) {
            if (fromDate.getValue() == null || toDate.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Dates ");
                alert.setHeaderText(null);
                alert.setContentText(
                        "You haven't specified dates, So we will take all dates.");

                alert.showAndWait();

                fromDate.setValue(LocalDate.parse("2018-01-01"));
                toDate.setValue(LocalDate.parse("2022-01-01"));
            }
            ReportsManager.generateReportByUser(fromDate.getValue(), toDate.getValue(), UserEmail);
        }

        ReportsManager.generateReportByUser(fromDate.getValue(), toDate.getValue(), emailTextField.getText());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "The Report has been downloaded to your Desktop");
    }

}
