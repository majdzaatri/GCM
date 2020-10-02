package g6.gcm.client.entity;

import g6.gcm.core.entity.SiteDTO;
import g6.gcm.core.entity.TourDTO;
import g6.gcm.core.interfaces.Renderable;
import g6.gcm.core.interfaces.Request.RenderingRequests;

/**
 * A factory for all kinds of map rendering. If & when {@see RenderingRequests} grow, it is possible
 * to migrate this design to a Builder or an Abstract Factory design pattern.
 */
@SuppressWarnings("Duplicates")
public class MapRenderingsBuilder {

  /**
   * Creates a new {@see MapRendering} based on {@see RenderingRequests} and renders on the {@see
   * #mapView} passed to {@see MapRenderingsBuilder(MapView mapView)} constructor.
   *
   * @param toBeRendered the object to be rendered.
   * @return a converted renderable, i.e., {@see MapRendering} instance.
   */
  public MapRendering createRendering(Renderable toBeRendered) {
    MapRendering mapRendering = null;

    switch ((RenderingRequests) toBeRendered.getRequest()) {
      case SITE_RENDERING: {
        mapRendering = new SiteRendering((SiteDTO) toBeRendered);
        break;
      }
      case TOUR_RENDERING: {
        mapRendering = new TourRendering((TourDTO) toBeRendered);
        break;
      }
    }
    return mapRendering;
  }


}
