package g6.gcm.client.entity;

import com.jfoenix.controls.JFXButton;
import g6.gcm.core.interfaces.Renderable;
import javafx.scene.layout.VBox;

public abstract class VBoxRendering<R extends Renderable> extends VBox {

    protected R renderable;

    public VBoxRendering(R renderable) {
        this.renderable = renderable;
        initializePreview();
        assemble();
    }

    abstract protected void initializePreview();

    abstract protected void assemble();

    protected void designJFXButton(JFXButton buttonToBeDesigned) {
    }


}
