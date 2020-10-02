package g6.gcm.client.entity;

import com.sothawo.mapjfx.CoordinateLine;
import g6.gcm.client.manager.SitesManager;
import g6.gcm.core.entity.SiteDTO;
import g6.gcm.core.entity.TourDTO;
import javafx.scene.paint.Color;

public class TourRendering<T extends TourDTO> extends MapRendering<T> {

  private CoordinateLine tourLine;

  public TourRendering(T toBeRendered) {
    super(toBeRendered);
  }

  @Override
  protected void assemble() {
    tourLine = new CoordinateLine(SitesManager.getInstance().getSitesOfTour(renderable))
        .setColor(Color.AQUA).setWidth(7);
  }

  @Override
  protected void bindVisibilities() {
    tourLine.visibleProperty().bind(visibleProperty());
  }

  public CoordinateLine getTourLine() {
    return tourLine;
  }

}
