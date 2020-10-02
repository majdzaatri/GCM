package g6.gcm.client.manager;

import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.core.ocsf.client.ObservableClient;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;
import javafx.application.Platform;


public class GCMClient extends ObservableClient {

    public static UserDTO user;
    private static GCMClient client = null;


    /**
     * Constructs the client.
     *
     * @param host the server's host name.
     * @param port the port number.
     */
    private GCMClient(String host, int port) {
        super(host, port);
        user = new UserDTO();
    }

    public static GCMClient initializeClient(String host, int port) {
        if (client == null) {
            client = new GCMClient(host, port);
        }
        return client;
    }

    public static GCMClient getClient() {
        try {
            Objects.requireNonNull(client);
        } catch (NullPointerException e) {
            System.out.println("[" + LocalTime.now() + "] >>> Client:\n[" + LocalTime.now()
                    + "] >>> Oops, got an exception: " + e + "\n[" + LocalTime.now()
                    + "] >>> Client must be initialized first.");
        }
        return client;
    }

    public static void send(AbstractDTO request) {

        // TODO: Ask malki if the runnable helps
        Platform.runLater(() -> {
            try {
                client.sendToServer(request);
            } catch (IOException e) {
                client.oops(e);
            }
        });
    }

    @Override
    protected void connectionEstablished() {
        say("Connection to server has been successfully established :)");
    }

    @Override
    protected void connectionException(Exception exception) {
        oops(exception);
    }


    public void say(String string) {
        LocalTime currTime = LocalTime.now();
        System.out.println(ConsoleTextColorsFactory.ANSI_PURPLE + "[" + currTime + "] >>> "
                + ConsoleTextColorsFactory.ANSI_RESET + string);
    }

    public void oops(Exception e) {
        LocalTime currTime = LocalTime.now();

        System.out.println(
                "\n[" + currTime + "] >>> Client:\n[" + currTime + "] >>> Oops, got an exception: " + e);
        e.printStackTrace();
    }


}
