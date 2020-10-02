package g6.gcm.client.manager;

import g6.gcm.core.entity.NotificationDTO;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ClientsInquiries;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import java.util.List;

public class NotificationsManager {


  private static ObjectProperty<NotificationDTO> notificationProperty = new SimpleObjectProperty<NotificationDTO>();

  private static ListProperty<NotificationDTO> listProperty = new SimpleListProperty<>(
      FXCollections.observableArrayList());


  public static void updateMapState(int mapID) {

  }

  public static void updateCityPriceState(String cityName) {


  }

  public static void getUserNotifications(UserDTO thisUser) {
    NotificationDTO notification = new NotificationDTO();
    notification.setToUsername(thisUser.getEmail());
    notification.setRequest(ClientsInquiries.GET_NOTIFICATIONS);
    GCMClient.send(notification);
  }

  public static void getUserNotificationsString(String thisUser) {
    NotificationDTO notification = new NotificationDTO();
    notification.setToUsername(thisUser);
    notification.setRequest(ClientsInquiries.GET_NOTIFICATIONS);
    GCMClient.send(notification);
  }

  public static void getNotificationsFromNotificationDTO(NotificationDTO thisUser) {
    NotificationDTO notification = new NotificationDTO();
    notification.setToUsername(thisUser.getToUsername());
    notification.setRequest(ClientsInquiries.GET_NOTIFICATIONS);
    GCMClient.send(notification);
  }

  public static void addPriceRequestNotification() {
    NotificationsManager.getNotification().setRequest(ClientsInquiries.ADD_NOTIFICATION);
    GCMClient.send(NotificationsManager.getNotification());
  }

  public static void deleteNotification() {
    NotificationsManager.getNotification()
        .setRequest((ClientsInquiries.DELETE_NOTIFICATION_BY_CITY_ID));
    GCMClient.send(NotificationsManager.getNotification());
  }

  //UPDATED VERSION OF getUserNotifications *******   update the list property
/*
  public static void updateList(Object thisUser)
  {
    NotificationDTO notification = new NotificationDTO();
    if (thisUser instanceof UserDTO)
      notification.setToUsername(((UserDTO)thisUser).getEmail());
    else
      if(thisUser instanceof  String) {
        notification.setToUsername(((String) thisUser));
      }
    notification.setRequest(ClientsInquiries.GET_NOTIFICATIONS);
    GCMClient.send(notification);
  }
*/





  /**
   * @return the notification
   */
  public static NotificationDTO getNotification() {
    return notificationProperty.get();
  }

  /**
   * @param notification the notification to set
   */
  public static void setNotificationprop(NotificationDTO notification) {
    notificationProperty.set(notification);
  }

  /**
   * @return the notificationProperty
   */
  public static ObjectProperty<NotificationDTO> getNotificationProperty() {
    return notificationProperty;
  }

  /**
   * @return the notificationssList
   */
  public static List<NotificationDTO> getNotificationssList() {
    return listProperty.get();
  }

  /**
   * @param notificationssList the notificationssList to set
   */
  public static void setNotificationssList(List<NotificationDTO> notificationssList) {
    listProperty.setAll(notificationssList);
  }

  /**
   * @return the listProperty
   */
  public static ListProperty<NotificationDTO> getListProperty() {
    return listProperty;
  }



}