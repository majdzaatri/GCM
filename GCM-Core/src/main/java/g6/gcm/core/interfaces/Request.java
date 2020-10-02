package g6.gcm.core.interfaces;

import java.io.Serializable;

/**
 * An interfaces for uniting Client and Server requests
 */
public interface Request extends Serializable {


    /**
     * All possible rendering types.
     */
    enum RenderingRequests implements Request {
        SITE_RENDERING, TOUR_RENDERING
    }


}
