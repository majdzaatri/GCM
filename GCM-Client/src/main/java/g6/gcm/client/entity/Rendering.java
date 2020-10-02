package g6.gcm.client.entity;

import g6.gcm.core.interfaces.Renderable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class Rendering<R extends Renderable> {

  protected R renderable;

  protected BooleanProperty visible;

  public Rendering(R renderable) {
    this.visible = new SimpleBooleanProperty(true);
    this.renderable = renderable;
    assemble();
    bindVisibilities();
  }

  protected abstract void assemble();

  protected abstract void bindVisibilities();

  protected boolean isVisible() {
    return visible.get();
  }

  public void setVisible(boolean visible) {
    this.visible.set(visible);
  }

  public BooleanProperty visibleProperty() {
    return visible;
  }
}
