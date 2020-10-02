package g6.gcm.client.entity;

import com.sothawo.mapjfx.Coordinate;
import g6.gcm.core.entity.CoordinatesDTO;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Convenience class for {@see MapBindingsManager} Abstract to restrict instantiation.
 */
public interface MapBindableManager {

    CoordinatesDTO selectedBeanCoordinates = new CoordinatesDTO();

    default Coordinate getSelectedBeanCoordinates() {
        return new Coordinate(selectedBeanCoordinates.getxCoordinate(),
                selectedBeanCoordinates.getyCoordinate());
    }

    default void setSelectedBeanCoordinates(Coordinate coordinates) {
        this.selectedBeanCoordinates.setxCoordinate(coordinates.getLatitude());
        this.selectedBeanCoordinates.setyCoordinate(coordinates.getLongitude());
    }

}