package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.ReportDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportDAO extends AbstractDAO {


    public Object processQuery(AbstractDTO theDTO) {

        PreparedStatement NumberOfMapsStatement;

        /* Prepare answer */
        Object answer = null;

        /* Cast for easier usage */
        ClientsInquiries theRequest = (ClientsInquiries) theDTO.getRequest();

        /* Closing leaks: if any of the following lines fails, the instances will be automatically close */
        try (
                /* Get a connection ready */
                Connection connection = GCMServer.getServer().dbConnection.getConnection();

                /* Prepare Statement */
                PreparedStatement statement = connection.prepareStatement(theRequest.getQuery())
        ) {

            /* Cast for easier usage */
            ReportDTO reportRequest = (ReportDTO) theDTO;

            /* To get the answer in */
            ReportDTO reportAnswer;

            /* Direct to the right executor method with a ready statement */
      switch (theRequest) {
        case REPORT_2: {
          ResultSet resultSet;
          ReportDTO report = new ReportDTO();
          try {
              report.setCityName(reportRequest.getCityName());
              report.setReportType(reportRequest.getReportType());

              statement.setString(1, reportRequest.getFromDate().toString());
              statement.setString(2, reportRequest.getToDate().toString());
            //statement.setString(3, "DOWNLOADED");
              statement.setInt(4, reportRequest.getDoneTo());

            System.out.println(report.getReportType());
            for (String type : report.getReportType()) {
              System.out.println("Switchh");
              System.out.println(type);
              switch (type) {

                case "Number of maps":
                  type = "";
                    theDTO.setRequest(ClientsInquiries.REPORT_MAPS);
                    theRequest = (ClientsInquiries) theDTO.getRequest();
                  NumberOfMapsStatement = (connection.prepareStatement(theRequest.getQuery()));
                    NumberOfMapsStatement.setString(1, report.getCityName());
                  resultSet = ExecuteQuery(NumberOfMapsStatement);

                  if (resultSet.next()) {
                    report.setNumberOfMaps(resultSet.getInt(1));
                  } else {
                    System.out.println("ResultSet is empty!");
                  }
                  break;
                case "Number of One Shot Deals":
                  type = "ONE_SHOT_DEAL";
                  statement.setString(3, type);
                  resultSet = ExecuteQuery(statement);

                  if (resultSet.next()) {
                    report.setOneShotDeals(resultSet.getInt(1));
                  } else {
                    System.out.println("ResultSet is empty!");
                  }

                  break;
                case "Number of subscribers":
                    type = "SUBSCRIPTION";
                  statement.setString(3, type);
                  resultSet = ExecuteQuery(statement);
                  if (resultSet.next()) {
                    report.setNumberOfSubscribers(resultSet.getInt(1));
                  } else {
                    System.out.println("ResultSet is empty!");
                  }

                  break;
                case "Number of subscription renewals":
                  type = "RENEWED_SUBSRIPTION";
                  statement.setString(3, type);
                  resultSet = ExecuteQuery(statement);
                  if (resultSet.next()) {
                    report.setNumberOfRenewals(resultSet.getInt(1));
                  } else {
                    System.out.println("ResultSet is empty!");
                  }
                  break;
                case "Number of views":
                  type = "CITY_VIEW";
                  statement.setString(3, type);
                  resultSet = ExecuteQuery(statement);
                  if (resultSet.next()) {
                    report.setNumberOfViews(resultSet.getInt(1));
                  } else {
                    System.out.println("ResultSet is empty!");
                  }
                  break;
                case "Number of downloads":
                  type = "DOWNLOADED";
                  statement.setString(3, type);
                  resultSet = ExecuteQuery(statement);
                  if (resultSet.next()) {
                    report.setNumberOfDownloads(resultSet.getInt(1));
                  } else {
                    System.out.println("ResultSet is empty!");
                  }
                  break;
              }
            }

            //ADD NUMBER OF MAPS!!!!!!!!!!!!!!!!!

            answer = report;

          } catch (SQLException e1) {
            e1.printStackTrace();
          }
          break;
        }
          case REPORT_BY_USER: {
              try {
                  statement.setString(1, reportRequest.getFromDate().toString());
                  statement.setString(2, reportRequest.getToDate().toString());
                  statement.setString(3, reportRequest.getEmail());

                  try {
                      answer = getAll(statement);
                  } catch (Exception e) {
                      JOptionPane.showMessageDialog(null, "OooCmooon man, type an existing Email");
                      answer = null;
                  }
              } catch (SQLException e1) {
                  e1.printStackTrace();
              }
              break;
          }


        case REPORT_ONE: {
          ReportDTO report = new ReportDTO();
          try {
              statement.setString(1, reportRequest.getFromDate().toString());
              statement.setString(2, reportRequest.getToDate().toString());
            answer = getOne(statement);
          } catch (SQLException e1) {
            e1.printStackTrace();
          }
          break;
        }

        case REPORT: {
          try {
              statement.setString(1, reportRequest.getFromDate().toString());
              statement.setString(2, reportRequest.getToDate().toString());
            answer = getAll(statement);
          } catch (SQLException e1) {
            e1.printStackTrace();
          }
          break;
        }
        default: {
            GCMServer.getServer().say(
                    "Request " + theRequest + " was" + ConsoleTextColorsFactory.ANSI_RED + " NOT "
                            + ConsoleTextColorsFactory.ANSI_RESET + "processed!");
        }
      }
    } catch (SQLException e) {
            GCMServer.getServer().say(
                    ConsoleTextColorsFactory.ANSI_RED + "EXCEPTION" + ConsoleTextColorsFactory.ANSI_RESET
                            + " IN " + this);
            GCMServer.getServer().oops(e);
      e.printStackTrace();
    }
    return answer;
  }

  @Override
  protected AbstractDTO extractFromResultSet(ResultSet resultSet) {
      ReportDTO report = new ReportDTO();
      try {
          report.setActivityType(resultSet.getString("ActivityType"));
          report.setActivityDate(resultSet.getDate("ActivityDate"));
          report.setDoneTo(resultSet.getInt("DoneTo"));
          report.setEmail(resultSet.getString("Email"));
      } catch (SQLException e) {
          report = null;
          e.printStackTrace();
      }

      return report;
  }


  public ResultSet ExecuteQuery(PreparedStatement statement) {

    AbstractDTO abstractDTO = null;

    ResultSet resultSet = null;
    try {
      resultSet = statement.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return resultSet;
  }

}
