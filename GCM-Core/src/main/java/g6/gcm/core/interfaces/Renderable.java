package g6.gcm.core.interfaces;

import g6.gcm.core.entity.SiteClassificationDTO;
import java.io.Serializable;

public abstract class Renderable extends Requestable implements Serializable {

    protected String textToBeRendered;

    protected Renderable() {

    }

    /**
     * Default constructor.
     */
    public Renderable(String textToBeRendered) {
        super();
        this.textToBeRendered = textToBeRendered;
    }


    /**
     * @return the default String to be rendered.
     */
    public String render() {
        return this.textToBeRendered;
    }
}
