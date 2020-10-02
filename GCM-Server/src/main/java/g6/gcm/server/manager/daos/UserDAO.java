package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.EmployeeDTO;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends AbstractDAO {


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
            UserDTO userRequest = (UserDTO) theDTO;

            /* To get the answer in */
            UserDTO userAnswer;

            /* Direct to the right executor method with a ready statement */
            switch (theRequest) {

                case AUTHENTICATE_LOGIN: {
                    statement.setString(1, userRequest.getEmail());
                    statement.setString(2, userRequest.getPassword());
                    userAnswer = (UserDTO) getOne(statement);

                    if (userAnswer != null) {
                        if (userAnswer.isOnline()) {
                            userAnswer = new UserDTO();
                            userAnswer.setRequest(ServersCommands.ALREADY_LOGGED_IN);
                            answer = userAnswer;
                        } else {
                            userAnswer.setRequest(ClientsInquiries.UPDATE_ONLINE_STATUS);
                            userAnswer = (UserDTO) DAOsFactory.getUserDAO().processQuery(userAnswer);

                            if (userAnswer.getIsEmployee() == 1) {
                                userAnswer.setRequest(ClientsInquiries.EMPLOYEE);

                                EmployeeDTO employee = (EmployeeDTO) DAOsFactory.getEmployeesDAO().processQuery(userAnswer);

                                employee.setEmail(userAnswer.getEmail());
                                employee.setFirstName(userAnswer.getFirstName());
                                employee.setPhoneNumber(userAnswer.getPhoneNumber());
                                employee.setPassword(userAnswer.getPassword());
                                employee.setUsername(userAnswer.getUsername());
                                employee.setOnline(userAnswer.isOnline());

                                answer = employee;
                            } else {
                                userAnswer.setRequest(ServersCommands.SIGN_IN_COSTUMER);
                                answer = userAnswer;
                            }
                        }
                    } else {
                        /* Authentication failed */
                        userAnswer = new UserDTO();
                        userAnswer.setRequest(ServersCommands.RETRY_LOGIN);
                        answer = userAnswer;
                    }
                    break;
                }

                case SIGN_UP_USER: {
                    statement.setString(1, userRequest.getEmail());
                    statement.setString(2, userRequest.getPassword());
                    statement.setString(3, userRequest.getFirstName());
                    statement.setString(4, userRequest.getLastName());
                    statement.setString(5, userRequest.getUsername());
                    statement.setString(6, userRequest.getPhoneNumber());

                    if (insert(statement)) {
                        userRequest.setRequest(ServersCommands.SUCCESSFUL_SIGN_UP);
                        answer = userRequest;
                    } else {
                        userAnswer = new UserDTO();
                        userAnswer.setRequest(ServersCommands.FAILED_SIGN_UP);
                        answer = userAnswer;
                    }
                    break;
                }
                case UPDATE_USER: {
                    statement.setString(1, userRequest.getPassword());
                    statement.setString(2, userRequest.getFirstName());
                    statement.setString(3, userRequest.getLastName());
                    statement.setString(4, userRequest.getUsername());
                    statement.setString(5, userRequest.getPhoneNumber());
                    statement.setString(6, userRequest.getEmail());

                    if (update(statement) > 0) {
                        userRequest.setRequest(ServersCommands.SCCESSFUL_UPDATE_PROFILE);
                        answer = userRequest;
                    }

                    break;

                }
                case UPDATE_ONLINE_STATUS: {
                    statement.setBoolean(1, true);
                    statement.setString(2, userRequest.getEmail());
                    // update(statement);
                    userRequest.setOnline(true);
                    answer = userRequest;
                    break;
                }
                case ALL_USERS: {
                    answer = getAll(statement);
                    break;
                }
                case SIGN_OUT_USER: {
                    statement.setBoolean(1, false);
                    statement.setString(2, userRequest.getEmail());
                    update(statement);
                    userRequest.setRequest(ServersCommands.EXIT_FROM_SYSTEM);
                    answer = userRequest;
                    break;
                }
                case UPDATE_USER_PASSWORD: {

                    statement.setString(1, userRequest.getPassword());
                    statement.setString(2, userRequest.getEmail());

                    if (update(statement) > 0) {
                        userRequest.setRequest(ServersCommands.SUCCESSFUL_UPDATE_PASSWORD);
                        answer = userRequest;
                    }
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

    /*
     * This method extracts all the data needed to initialize a user. More concrete
     * data (Employees) will be retrieved from their own DAO.
     *
     * @see
     * core.java.entity.dao.interfaces.AbstractDAO#extractFromResultSet(java.sql.
     * ResultSet)
     */
    @Override
    protected UserDTO extractFromResultSet(ResultSet resultSet) {
        UserDTO user = new UserDTO();
        try {
            user.setEmail(resultSet.getString("Email"));
            user.setPassword(resultSet.getString("Password"));
            user.setFirstName(resultSet.getString("FirstName"));
            user.setLastName(resultSet.getString("LastName"));
            user.setPhoneNumber(resultSet.getString("PhoneNumber"));
            user.setUsername(resultSet.getString("Username"));
            user.setOnline(resultSet.getBoolean("Online"));
            user.setIsEmployee(resultSet.getInt("isEmployee"));
        } catch (SQLException e) {
            e.printStackTrace();
            user = null;
        }
        return user;
    }

}
