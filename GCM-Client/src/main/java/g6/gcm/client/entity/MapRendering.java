package g6.gcm.client.entity;

import com.sothawo.mapjfx.MapLabel;
import com.sothawo.mapjfx.Marker;
import g6.gcm.core.interfaces.Renderable;

public abstract class MapRendering<R extends Renderable> extends Rendering<R> {

  public MapRendering(R renderable) {
    super(renderable);
  }

}
