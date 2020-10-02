package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.MapDTO;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.entity.CompanyManagerDTO;
import g6.gcm.core.entity.ContentEditorDTO;
import g6.gcm.core.entity.ContentManagerDTO;
import g6.gcm.core.entity.EmployeeDTO;
import g6.gcm.core.entity.EmployeeDTO.Roles;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.server.manager.GCMServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeesDAO extends AbstractDAO {
    @Override
    public Object processQuery(AbstractDTO theDTO) {

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
            UserDTO employeeRequest = (UserDTO) theDTO;

            /* To get the answer in */
            EmployeeDTO employeeAnswer;

            /* Direct to the right executor method with a ready statement */
            switch (theRequest) {

                case EMPLOYEE: {
                    statement.setString(1, employeeRequest.getEmail());
                    answer = getOne(statement);
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
    protected EmployeeDTO extractFromResultSet(ResultSet resultSet) {
        EmployeeDTO employee = null;
        try {

            switch (Roles.valueOf(resultSet.getString("EmployeesRole"))) {
                case CONTENT_EDITOR: {
                    employee = new ContentEditorDTO();
                    break;
                }
                case CONTENT_MANAGER: {
                    employee = new ContentManagerDTO();
                    break;
                }
                case COMPANY_MANAGER: {
                    employee = new CompanyManagerDTO();
                    break;
                }
            }
            employee.setEmployeeID(resultSet.getInt("EmployeeID"));
            employee.setRole(Roles.valueOf(resultSet.getString("EmployeesRole")));
            employee.setRequest(ServersCommands.SIGN_IN_EMPLOYEE);
        } catch (SQLException e) {
            resultSet.close();// TODO: is this method needed?
            e.printStackTrace();
        } finally {
            return employee;
        }
    }

}
