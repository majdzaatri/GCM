package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;

/**
 * Decorator class for the Coordinate class because it is final and cannot be extended
 */
public class CoordinatesDTO extends AbstractDTO {

    private int ID;
    private double xCoordinate = 31.494261815532752;
    private double yCoordinate = 35.1947021484375;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(xCoordinate);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(yCoordinate);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CoordinatesDTO other = (CoordinatesDTO) obj;
        if (Double.doubleToLongBits(xCoordinate) != Double.doubleToLongBits(other.xCoordinate)) {
            return false;
        }
        if (Double.doubleToLongBits(yCoordinate) != Double.doubleToLongBits(other.yCoordinate)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "Coordinates [xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate + "]";
    }

    @Override
    protected void setDTOType() {
        this.type = DTOsFactory.COORDINATES;
    }

    @Override
    public String render() {
        return this.getxCoordinate() + this.getyCoordinate() + "";
    }

}
