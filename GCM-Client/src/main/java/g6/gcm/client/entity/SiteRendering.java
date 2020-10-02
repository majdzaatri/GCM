package g6.gcm.client.entity;

import com.sothawo.mapjfx.MapLabel;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.Marker.Provided;
import g6.gcm.core.entity.SiteDTO;

public class SiteRendering<S extends SiteDTO> extends MapRendering<S> {

    private MapLabel label;
    private Marker marker;

    public SiteRendering(S siteToBeRendered) {
        super(siteToBeRendered);
    }

    @Override
    protected void assemble() {
        label = new MapLabel(renderable.getSiteName()).setPosition(renderable.getCoordinates())
                .setVisible(true);
        marker = Marker.createProvided(Provided.BLUE).setPosition(renderable.getCoordinates())
                .setVisible(true);
        marker.attachLabel(label);
    }

    @Override
    protected void bindVisibilities() {
        label.visibleProperty().bind(visibleProperty());
        marker.visibleProperty().bind(visibleProperty());
    }

    public int getID() {
        return renderable.getSiteID();
    }

    public MapLabel getLabel() {
        return label;
    }

    public Marker getMarker() {
        return marker;
    }


}
