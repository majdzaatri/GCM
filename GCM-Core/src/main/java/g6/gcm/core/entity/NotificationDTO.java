package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import java.sql.Date;

public class NotificationDTO extends AbstractDTO {

    public int getUpdatee() {
        return updatee;
    }

    public void setUpdatee(int updatee) {
        this.updatee = updatee;
    }

    private int updatee;

    private int notificationID;

    private String notification;

    private String fromUsername;

  private int read;

  private String toUsername;

  private Date dateOfNotifying;

  private String notificationType;

  private int update;

  public int getUpdate() {
    return update;
  }

  public void setUpdate(int update) {
    this.update = update;
  }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public enum NotificationType {
        CHANGE_PRICE, MAP_REQUEST, RENEWAL
    }

    /**
     * @return the notificationID
     */
    public int getNotificationID() {
        return notificationID;
    }

    /**
     * @param notificationID the notificationID to set
     */
    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    /**
     * @return the notification
     */
    public String getNotification() {
        return notification;
    }

    /**
     * @param notification the notification to set
     */
    public void setNotification(String notification) {
        this.notification = notification;
    }

    /**
     * @return the fromUsername
     */
    public String getFromUsername() {
        return fromUsername;
    }

    /**
     * @param fromUsername the fromUsername to set
     */
    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    /**
     * @return the toUsername
     */
    public String getToUsername() {
        return toUsername;
    }

    /**
     * @param toUsername the toUsername to set
     */
    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    /**
     * @return the dateOfNotifying
     */
    public Date getDateOfNotifying() {
        return dateOfNotifying;
    }

    /**
     * @param dateOfNotifying the dateOfNotifying to set
     */
    public void setDateOfNotifying(Date dateOfNotifying) {
        this.dateOfNotifying = dateOfNotifying;
    }

    @Override
    protected void setDTOType() {
        this.type = DTOsFactory.NOTIFICATION;

    }
    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((dateOfNotifying == null) ? 0 : dateOfNotifying.hashCode());
        result = prime * result + ((fromUsername == null) ? 0 : fromUsername.hashCode());
        result = prime * result + ((notification == null) ? 0 : notification.hashCode());
        result = prime * result + notificationID;
        result = prime * result + ((toUsername == null) ? 0 : toUsername.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        NotificationDTO other = (NotificationDTO) obj;
        if (dateOfNotifying == null) {
            if (other.dateOfNotifying != null)
                return false;
        } else if (!dateOfNotifying.equals(other.dateOfNotifying))
            return false;
        if (fromUsername == null) {
            if (other.fromUsername != null)
                return false;
        } else if (!fromUsername.equals(other.fromUsername))
            return false;
        if (notification == null) {
            if (other.notification != null)
                return false;
        } else if (!notification.equals(other.notification))
            return false;
        if (notificationID != other.notificationID)
            return false;
        if (toUsername == null) {
            if (other.toUsername != null)
                return false;
        } else if (!toUsername.equals(other.toUsername))
            return false;
        return true;
    }

    @Override
    public String render() {
        return this.getNotification();
    }

}


