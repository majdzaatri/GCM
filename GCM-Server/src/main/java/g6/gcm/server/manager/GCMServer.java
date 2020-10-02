package g6.gcm.server.manager;

import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.ocsf.server.ConnectionToClient;
import g6.gcm.core.ocsf.server.ObservableOriginatorServer;
import g6.gcm.server.entity.DBConnection;

import java.time.LocalTime;
import java.util.Objects;

/**
 * @Singleton
 */
public class GCMServer extends ObservableOriginatorServer {

    private static GCMServer server = null;
    public DBConnection dbConnection = null;

    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    private GCMServer(int port) {
        super(port);
    }


    /**
     * Constructs a new server and instantiates a data base connection.
     *
     * @param port     is the port number on which to listen.
     * @param schema   is the schema to which connect.
     * @param username is the username to connect with.
     * @param password is the username's password.
     */
    public static GCMServer initializeServer(int port, String schema, String username,
                                             String password) {
        if (server == null) {
            server = new GCMServer(port);
        }
        server.initializeDBConection(schema, username, password);
        return server;
    }

    /**
     * The only proper way to get the server after initializing.
     *
     * @return instanceOf GCMServer.
     */
    public static GCMServer getServer() {
        try {
            Objects.requireNonNull(server);
        } catch (NullPointerException e) {
            server.say("Oops, got an exception: " + e + "Server must be initialized first.");
        }
        return server;
    }

    @Override
    protected synchronized void clientConnected(ConnectionToClient client) {
        say("New connection from client: " + (client));
    }

    /**
     * Initializes the data base.
     *
     * @param schema   is the schema to which connect.
     * @param username is the username to connect with.
     * @param password is the username's password.
     */
    private void initializeDBConection(String schema, String username, String password) {
        dbConnection = new DBConnection(schema, username, password);
    }

    public void say(String string) {
        LocalTime currTime = LocalTime.now();
        System.out.println(ConsoleTextColorsFactory.ANSI_BLUE + "[" + currTime + "] >>> "
                + ConsoleTextColorsFactory.ANSI_RESET + string);
    }

    public void oops(Exception e) {
        LocalTime currTime = LocalTime.now();
        System.out.println(
                "\n[" + currTime + "] >>> Server:\n[" + currTime + "] >>> Oops, got an exception: " + e);
    }


}
