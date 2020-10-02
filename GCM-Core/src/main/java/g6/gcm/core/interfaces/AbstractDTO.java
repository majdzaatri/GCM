package g6.gcm.core.interfaces;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.factories.ServersCommands;

/**
 * This is a abstract class for all Data Transfer Objects. Every object that might be transferred
 * between client and server in any given point should extend this class. Enables using
 * <code>AbstractDAO</code>.
 */
public abstract class AbstractDTO extends Renderable {

    protected DTOsFactory type;

    public AbstractDTO() {
        super();
        setDTOType();
    }

    /**
     * Must be implemented by subclasses.
     */
    protected abstract void setDTOType();

    /**
     * Public getter to simplify differentiation between DTOs.
     *
     * @return the type
     */
    public DTOsFactory getType() {
        return type;
    }

}
