package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import java.sql.Date;

public class SubscriptionDTO extends PurchaseDTO {

    private Date subscriptionExpirationDate;

    /**
     * @return the subscriptionExpirationDate
     */
    public Date getSubscriptionExpirationDate() {
        return subscriptionExpirationDate;
    }

    /**
     * @param subscriptionExpirationDate the subscriptionExpirationDate to set
     */
    public void setSubscriptionExpirationDate(Date subscriptionExpirationDate) {
        this.subscriptionExpirationDate = subscriptionExpirationDate;
    }


    @Override
    public String render() {
        return "Purchase #" + this.getPurchaseID() + ", Purchase date: " + this
                .getPurchaseDate() + ", Expiration date: " + this
                .getSubscriptionExpirationDate();
    }


    @Override
    protected void setDTOType() {
        this.type = DTOsFactory.SUBSCRIPTION;
    }

}
