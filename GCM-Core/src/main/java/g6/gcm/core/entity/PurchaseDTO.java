package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;

import java.sql.Date;

public class PurchaseDTO extends AbstractDTO {

    private Date purchaseDate;
    private int wasExtended;
    private String Email;
    private int purchaseID;
    private int cityID;

    public int getWasExtended() {
        return wasExtended;
    }

    public void setWasExtended(int wasExtended) {
        this.wasExtended = wasExtended;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    /**
     * @return the purchaseDate
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @param purchaseDate the purchaseDate to set
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String render() {
        return "Purchase #" + this.getPurchaseID() + ", Purchase date: " + this
                .getPurchaseDate();
    }


    @Override
    protected void setDTOType() {
        this.type = DTOsFactory.PURCHASE;
    }

}
