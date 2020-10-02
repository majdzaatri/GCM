package g6.gcm.client.MyButton;

import static java.util.Objects.requireNonNull;

import com.sothawo.mapjfx.Coordinate;
import javafx.event.Event;
import javafx.event.EventType;

public class MyButtonEvent extends Event {

    /**
     * base event type
     */
    public static final EventType<MyButtonEvent> ANY = new EventType<>("MY_BUTTON_EVENT_ANY");

    /**
     * pointer moved over button
     */
    public static final EventType<MyButtonEvent> POINTER_MOVED = new EventType<>(ANY,
            "MY_BUTTON_POINTER_MOVED");

    /**
     * the coordinate where the event happened, only set on MyButton event
     */
    private final Coordinate coordinate;

    public MyButtonEvent(EventType<? extends Event> eventType, Coordinate coordinate) {
        super(eventType);
        this.coordinate = requireNonNull(coordinate);
    }

    /**
     * @return the coordinate where the event happend.
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

}
