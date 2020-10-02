package g6.gcm.client.manager;

import com.sothawo.mapjfx.Coordinate;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.core.entity.CreditCardDTO;
import g6.gcm.core.entity.UserDTO;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;

public class ClientLauncher extends Application {

    protected static StageManager stageManager;
    private static GCMClient gcmClient;
    NotificationListUpdater updater = new NotificationListUpdater();
    private final Coordinate ISRAEL_COORDINATES = new Coordinate(31.494261815532752,
            35.1947021484375);

    // private final Injector gInjector = Guice.createInjector(new
    // GCMGuiceModule());
    private final int DEFAILT_ZOOM = 9;
    private IncomingMessageManager incomingMessageManager;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        gcmClient = gcmClient.initializeClient("localhost", 4332);

        // UsersManager.setThisUser();
        // MapDTO initMap = new MapDTO();
        // initMap.setMapID(10);
        // initMap.setCoordinates(ISRAEL);
        // initMap.setCityID(17);
        // initMap.setStatus(AccessabilityState.RELEASED);
        // initMap.setVersion(1.2D);
        // initMap.setZoom(DEFAILT_ZOOM);
        // initMap.setRequest(ClientsInquiries.ONE_MAP);
        // MapsManager.getInstance().setBean(initMap);
        // CityDTO initCity = new CityDTO();
        // initCity.setCityName("ISRAEL_VIEW");
        // initCity.setCityID(0);
        // initCity.setCoordinates(ISRAEL_COORDINATES);
        // initCity.setZoom(DEFAILT_ZOOM);
        // initCity.setRequest(ClientsInquiries.ONE_CITY);
        // CitiesManager.getInstance().setBean(initCity);
        //    MapDTO initMap = new MapDTO();
        //    initMap.setMapID(0);
        //    initMap.setCityID(0);
        //    initMap.setCoordinates(ISRAEL_COORDINATES);
        //    initMap.setZoom(DEFAILT_ZOOM);
        //    initMap.setRequest(ClientsInquiries.ONE_MAP);
        //    MapsManager.getInstance().setBean(initMap);


        CreditCardsManager.getInstance().setBean(new CreditCardDTO());
        UserDTO user =new UserDTO();
        user.setEmail("");
        user.setPassword("");
        user.setPhoneNumber("");
        user.setLastName("");
        user.setFirstName("");
        user.setUsername("");
        UsersManager.getInstance().setBean(user);

        incomingMessageManager = new IncomingMessageManager(gcmClient);
        // MapsManager.getInstance().setPurchase(initMap);

        gcmClient.say("Initialized.");
        gcmClient.say("Opening Connection...");
        gcmClient.openConnection();
        gcmClient.say("Connection Established");
        gcmClient.say("Initializing GUI...");

        updater.launchTask();

        StageManager.initializeManager(primaryStage);
        stageManager = StageManager.getManager();
        showInitialScene();
        CreditCardDTO cc = new CreditCardDTO();
        cc.setAccountEmail("");
        cc.setCreditCardCSV("");
        cc.setCreditCardNumber("");
        cc.setCardholdersName("");
       // Date d = Date
       // cc.setCreditCardExpirationDate(new java.sql.Date());


    }

    private void showInitialScene() {
        stageManager.stageScene(FXMLFactory.WELCOME_SCREEN, Color.TRANSPARENT);
    }

}
