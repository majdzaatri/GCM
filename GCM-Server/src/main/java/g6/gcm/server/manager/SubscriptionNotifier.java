package g6.gcm.server.manager;

import g6.gcm.core.entity.NotificationDTO;
import g6.gcm.core.entity.PurchaseDTO;
import g6.gcm.core.entity.SubscriptionDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.server.manager.daos.DAOsFactory;
import g6.gcm.server.manager.daos.NotificationsDAO;
import g6.gcm.server.manager.daos.PurchasesDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SubscriptionNotifier {


  Timer myTimer = new Timer();
  private List<? extends SubscriptionDTO> subscriptionsList;

  TimerTask myTask = new TimerTask() {

    @Override
    public void run() {
      LocalDate now = LocalDate.now();
      NotificationDTO notificationDTO;
      NotificationsDAO notificationDAO = new NotificationsDAO();
      GCMServer.getServer().say("Subscription Notifier was activated - Running Scheduled Task...");
      SubscriptionDTO subscriptionsRequest = new SubscriptionDTO();
      PurchasesDAO purchasesDAO = DAOsFactory.getPurchasesDAO();

      subscriptionsRequest.setRequest(ClientsInquiries.ALL_SUBSCRIPTIONS);
      subscriptionsList = (List<SubscriptionDTO>) purchasesDAO.processQuery(subscriptionsRequest);

      Object isInserted;
      for (SubscriptionDTO subscription : subscriptionsList) {
        if (subscription.getSubscriptionExpirationDate().toLocalDate()
            .minusDays(3).equals(now))
        // TODO Add notifications!
        {
          Date currentDate = new Date(System.currentTimeMillis());
          notificationDTO = new NotificationDTO();
          //ADD DATA TO THE NOTIFICATION DTO
          notificationDTO.setFromUsername("System");
          notificationDTO.setToUsername(subscription.getEmail());
          notificationDTO.setNotification("You have 3 days remaining for your subscription. Click here to Renew now and get 10% discount");
          notificationDTO.setDateOfNotifying(currentDate);
          notificationDTO.setNotificationType("RENEWAL");
          notificationDTO.setUpdatee(subscription.getCityID());

          //PROCESS
          notificationDTO.setRequest(ClientsInquiries.ADD_NOTIFICATION);

          isInserted = notificationDAO.processQuery(notificationDTO);
          //if ((int)isInserted > 0)
          System.out.println("A new subscription  notification has been added to: " + subscription.getEmail());

        }
      }
    }

  };
  private long period = 86400000;

  // TODO: check for better alternatives
  public void launchNotifier() {

    myTimer.scheduleAtFixedRate(myTask, 200, period);

  }

}
