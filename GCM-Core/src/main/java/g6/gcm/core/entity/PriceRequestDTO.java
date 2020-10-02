package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;

import java.sql.Date;

public class PriceRequestDTO extends AbstractDTO {

    private int requestID;
    private double subscribtionPrice;
    private double oneShotDealPrice;
    private String cityName;
    private String requestStatus;
    private Date requestDate;

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public double getSubscribtionPrice() {
        return subscribtionPrice;
    }

    public void setSubscribtionPrice(double subscribtionPrice) {
        this.subscribtionPrice = subscribtionPrice;
    }

    public double getOneShotDealPrice() {
        return oneShotDealPrice;
    }

    public void setOneShotDealPrice(double oneShotDealPrice) {
        this.oneShotDealPrice = oneShotDealPrice;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    protected void setDTOType() {
        this.type = DTOsFactory.PRICE_REQUEST;
    }


    @Override
    public String render() {
        return String.valueOf(this.getRequestStatus());
    }
}
