package g6.gcm.core.entity;

import com.sothawo.mapjfx.Coordinate;
import g6.gcm.core.interfaces.AbstractDTO;

/**
 * Convenience class.
 */
public abstract class CoordinatizedDTO extends AbstractDTO {

    private CoordinatesDTO coordinates = new CoordinatesDTO();

    private double zoom;

    public Coordinate getCoordinates() {
        return new Coordinate(coordinates.getxCoordinate(), coordinates.getyCoordinate());
    }

    public void setCoordinates(Coordinate coordinates) {
        this.coordinates.setxCoordinate(coordinates.getLatitude());
        this.coordinates.setyCoordinate(coordinates.getLongitude());
    }

    public CoordinatesDTO getCoordinatesDTO() {
        return coordinates;
    }

    public void setCoordinatesDTO(CoordinatesDTO coordinates) {
        this.coordinates = coordinates;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
