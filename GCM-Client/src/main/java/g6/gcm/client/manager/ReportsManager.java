package g6.gcm.client.manager;

import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.entity.ReportDTO;
import g6.gcm.core.interfaces.Renderable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class ReportsManager {

    private static ListProperty<ReportDTO> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList());

    public static ObservableList<ReportDTO> getListProperty() {
        return listProperty.get();
    }

    public static void setListProperty(ObservableList<ReportDTO> listProperty) {
        ReportsManager.listProperty.set(listProperty);
    }

    public static ListProperty<ReportDTO> listPropertyProperty() {
        return listProperty;
    }

    public static void generateReport(LocalDate fromDate, LocalDate toDate, String city,
                                      ArrayList<String> type) {
        ReportDTO report = new ReportDTO();
        report.setToDate(toDate);
        report.setFromDate(fromDate);
        report.setReportType(type);
        for (Renderable cityName : CitiesManager.getInstance().getBeansList()) {
            if (((CityDTO) cityName).getCityName().equals(city))
                report.setDoneTo(((CityDTO) cityName).getCityID());
        }
        report.setCityName(city);
        report.setRequest(ClientsInquiries.REPORT_2);
        GCMClient.send(report);


    }

    public static void generateReportByUser(LocalDate fromDate, LocalDate toDate, String user) {
        ReportDTO report = new ReportDTO();
        report.setEmail(user);
        report.setToDate(toDate);
        report.setFromDate(fromDate);
        report.setRequest(ClientsInquiries.REPORT_BY_USER);
        GCMClient.send(report);

        listPropertyProperty().addListener(((observable, oldValue, newValue) ->
        {
            DownloadReportByUser();
        }
        ));
    }

    public static void DownloadReport(ReportDTO report) {

        try {
            // File file = new File("C:\\Users\\Jeries\\Desktop\\report.txt");

            FileWriter file = new FileWriter("C:\\Users\\Jeries\\Desktop\\report.txt", true);
/*            if (!file.exists()) {
                file.createNewFile();
            }*/
            PrintWriter pw = new PrintWriter(file);
            pw.println(
                    "                  **********City-Report For " + report.getCityName() + "**********");

            for (String type : report.getReportType()) {
                switch (type) {

                    case "Number of maps":
                        pw.println(type + ": " + report.getNumberOfMaps());
                        break;

                    case "Number of One Shot Deals":
                        pw.println(type + ": " + report.getOneShotDeals());
                        break;

                    case "Number of subscribers":
                        pw.println(type + ": " + report.getNumberOfSubscribers());

                        break;

                    case "Number of subscription renewals":
                        pw.println(type + ": " + report.getNumberOfRenewals());

                        break;

                    case "Number of views":
                        pw.println(type + ": " + report.getNumberOfViews());

                        break;

                    case "Number of downloads":
                        pw.println(type + ": " + report.getNumberOfDownloads());

                        break;
                }
            }


            pw.close();
        } catch (IOException e) {

        }
    }


    public static void DownloadReportByUser() {

        try {
            for (ReportDTO report : getListProperty()) {
                // File file = new File("C:\\Users\\Jeries\\Desktop\\report.txt");

                FileWriter file = new FileWriter("C:\\Users\\Jeries\\Desktop\\reportByUser.txt", true);

                PrintWriter pw = new PrintWriter(file);
                pw.println(
                        "                  **********Activity-Report For " + report.getEmail() + "**********");

                pw.println("Activity Type: " + report.getActivityType());
                pw.println("Activity Date: " + report.getActivityDate());
                pw.println("Done to ID: : " + report.getDoneTo());


                pw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
