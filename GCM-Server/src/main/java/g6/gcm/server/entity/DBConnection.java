package g6.gcm.server.entity;

import g6.gcm.server.manager.GCMServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    // TODO: This isn't the best implementation possible, better: connection pool.

    private final String database, dbUsername, dbPassword;
    private GCMServer gcmServer;

    /**
     * This constructor saves the database details for establishing connections.
     *
     * @param database   is the name of the database to connect to.
     * @param dbUsername is the name of the user to connect with.
     * @param dbPassword is the user's password.
     */
    public DBConnection(String database, String dbUsername, String dbPassword) {
        gcmServer = GCMServer.getServer();
        loadDriver();
        this.database = database;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    /**
     * Loads driver - 1 time thing.
     */
    private void loadDriver() {
        try {
            gcmServer.say("Loading driver class...");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            gcmServer.oops(e);
        }
        gcmServer.say("Driver class successfully loaded.");
    }

    /**
     * Establishes a new connection.
     * <p>
     * Connection must be closed after querying it.
     */
    public Connection getConnection() {
        Connection connection = null;
        gcmServer.say("Establishing connection...");
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + database + "?serverTimezone=AST", dbUsername,
                    dbPassword);
            gcmServer.say("Connection successfully established.");
        } catch (SQLException e) {

        }
        return connection;
    }


    /**
     * Explicitly closes passed connection.
     *
     * @return True if connection was successfully closed.
     */
    public boolean closeConnection(Connection connection) {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            gcmServer.oops(e);
            return false;
        }
    }

}
