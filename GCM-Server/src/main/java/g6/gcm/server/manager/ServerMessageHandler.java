package g6.gcm.server.manager;

import g6.gcm.core.entity.MapDTO;
import g6.gcm.core.entity.ReportDTO;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.core.ocsf.server.ConnectionToClient;
import g6.gcm.core.ocsf.server.OriginatorMessage;
import g6.gcm.server.manager.daos.*;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Handles all messages received by the server.
 * <p>
 * Directs each message to its corresponding Data Access Object.
 * <p>
 * Observes {@see GCMServer}.
 * <p>
 * TODO: should be singleton.
 */
public class ServerMessageHandler implements Observer {

    private GCMServer gcmServer;

    /**
     * Constructor.
     */
    public ServerMessageHandler() {
        gcmServer = GCMServer.getServer();
        gcmServer.addObserver(this);
    }

    /**
     * This is the executed method when GCMServer notifies observers of changes.
     *
     * @param o                 is the observable (GCMServer).
     * @param originatorMessage is the received message.
     */
    @Override
    public void update(Observable o, Object originatorMessage) {

        ConnectionToClient client = ((OriginatorMessage) originatorMessage).getOriginator();
        Object message = ((OriginatorMessage) originatorMessage).getMessage();

        // If this is an internal message, no handling needed, print.
        if (client == null && !(message instanceof AbstractDTO)) {
            gcmServer.say(((String) message).substring(4));
        } else {

            // Not an internal message, process request, print.

            AbstractDTO request = (AbstractDTO) (message);
            Object answer;

            gcmServer.say("Hello master, a " + ConsoleTextColorsFactory.ANSI_YELLOW + request.getRequest()
                    + ConsoleTextColorsFactory.ANSI_RESET + " request has been received, processing...");

            answer = processRequest(request);

            if (answer != null) {
                gcmServer.say(
                        "Master, I just finished processing the " + ConsoleTextColorsFactory.ANSI_YELLOW
                                + request.getRequest() + ConsoleTextColorsFactory.ANSI_RESET
                                + " request, sending...");
            } else {
                gcmServer.say(
                        "Master, I just finished processing the " + ConsoleTextColorsFactory.ANSI_YELLOW
                                + request.getRequest() + ConsoleTextColorsFactory.ANSI_RESET
                                + " but " + ConsoleTextColorsFactory.ANSI_RED + "NULL"
                                + ConsoleTextColorsFactory.ANSI_RESET + "will be sent!.");

            }

            send(answer, client);
        }
    }

    private Object processRequest(AbstractDTO request) {

        // TODO add an option to send to all clients depending on some condition we
        // should think of

        Object result = null;

        // TODO: change cases
        switch (request.getType()) {

            case USER: {

                // Get UserDAO from factory
                UserDAO userDAO = DAOsFactory.getUserDAO();
                result = userDAO.processQuery(request);
                break;
            }
            case ACTIVITY: {

                ActivitiesDAO activityDAO = DAOsFactory.getActivitiesDAO();
                result = activityDAO.processQuery(request);
                break;
            }
            case NOTIFICATION: {

                NotificationsDAO notificationDAO = DAOsFactory.getNotificationsDAO();
                result = notificationDAO.processQuery(request);
                break;
            }

            case CITY: {
                CitiesDAO citiesDAO = DAOsFactory.getCitiesDAO();
                result = citiesDAO.processQuery(request);
                break;
            }
            case CREDIT_CARD: {
                CreditCardsDAO creditCardsDAO = DAOsFactory.getCreditCardsDAO();
                result = creditCardsDAO.processQuery(request);
                break;
            }

            case REPORT: {
                ReportDAO reportDao = new ReportDAO();

                System.out.println("ServerHandler");
                System.out.println(((ReportDTO) request).getReportType());
                result = reportDao.processQuery(request);

                break;
            }

            case MAP: {
                MapsDAO mapsDAO = DAOsFactory.getMapsDAO();

                MapDTO allMaps = new MapDTO();

                // allMaps.setRequest(RequestsFactory.ALL_MAPS);
                // result = mapsDAO.processQuery(allMaps);
                result = mapsDAO.processQuery(request);
                gcmServer.sendToAllClients(result);
                break;
            }

            case TOUR: {

                TourDAO toursDAO = DAOsFactory.getTourDAO();

                result = toursDAO.processQuery(request);
                break;
            }

            case SITE: {

                SitesDAO sitesDAO = DAOsFactory.getSitesDAO();

                result = sitesDAO.processQuery(request);
                break;
            }

            case SITE_CLASSIFICATION: {

                SiteClassificationsDAO classificationsDAO = DAOsFactory.getSiteClassificationsDAO();

                result = classificationsDAO.processQuery(request);
                break;
            }

            case ONE_SHOT_DEAL:
            case SUBSCRIPTION:
            case PURCHASE: {
                PurchasesDAO purchasesDAO = DAOsFactory.getPurchasesDAO();
                result = purchasesDAO.processQuery(request);
                break;
            }
            case PRICE_REQUEST: {
                PriceRequestDAO priceRequestDAO = DAOsFactory.getPriceRequestDAO();
                result = priceRequestDAO.processQuery((request));
                break;
            }

            default: {
                gcmServer.say(ConsoleTextColorsFactory.ANSI_RED + "Processing failed!"
                        + ConsoleTextColorsFactory.ANSI_RESET);
            }
        }
        return result;
    }


    private void send(Object answer, ConnectionToClient client) {
        try {
            client.sendToClient(answer);
            gcmServer.say("Processed request has been successfully sent to: " + client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
