package g6.gcm.core.interfaces;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.factories.ServersCommands;

public interface IDTO {


    /**
     * Must be implemented by subclasses.
     */
    void setDTOType();

    /**
     * Public getter to simplify differentiation between DTOs.
     *
     * @return the type
     */
    DTOsFactory getType();

}
