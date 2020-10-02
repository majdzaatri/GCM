package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.core.interfaces.Renderable;

import java.io.Serializable;

public class ExtentTransfer extends AbstractDTO implements Serializable {

    int cityID;
    double minLatitude, minLongitude, maxLatitude, maxLongitude;

    public ExtentTransfer(int cityID, double minLatitude, double minLongitude, double maxLatitude,
                          double maxLongitude) {
        this.cityID = cityID;
        this.minLatitude = minLatitude;
        this.minLongitude = minLongitude;
        this.maxLatitude = maxLatitude;
        this.maxLongitude = maxLongitude;
    }

    public ExtentTransfer(Renderable bean) {

    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public double getMinLatitude() {
        return minLatitude;
    }

    public void setMinLatitude(double minLatitude) {
        this.minLatitude = minLatitude;
    }

    public double getMinLongitude() {
        return minLongitude;
    }

    public void setMinLongitude(double minLongitude) {
        this.minLongitude = minLongitude;
    }

    public double getMaxLatitude() {
        return maxLatitude;
    }

    public void setMaxLatitude(double maxLatitude) {
        this.maxLatitude = maxLatitude;
    }

    public double getMaxLongitude() {
        return maxLongitude;
    }

    public void setMaxLongitude(double maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    @Override
    protected void setDTOType() {
        this.type = DTOsFactory.CITY;
    }
}
