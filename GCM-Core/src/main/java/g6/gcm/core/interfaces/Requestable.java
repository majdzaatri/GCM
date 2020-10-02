package g6.gcm.core.interfaces;

import g6.gcm.core.factories.ServersCommands;
import java.io.Serializable;

public abstract class Requestable implements Serializable {

    private Request request;

    protected Requestable() {
        this.request = ServersCommands.INITIAL_STATE;
    }

    /**
     * @return the request.
     */
    public Request getRequest() {
        return this.request;
    }

    /**
     * To be used by subclasses to indicate which request this is. Server uses this field to decide
     * which query to run when it's a {@see ClientsInquiry}.
     */
    public void setRequest(Request request) {
        this.request = request;
    }

}
