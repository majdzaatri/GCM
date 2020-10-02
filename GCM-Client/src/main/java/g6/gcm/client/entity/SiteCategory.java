package g6.gcm.client.entity;


import g6.gcm.core.entity.SiteClassificationDTO;
import g6.gcm.core.interfaces.Renderable;
import g6.gcm.core.interfaces.Request.RenderingRequests;

public class SiteCategory extends Renderable {

    public SiteCategory(String category) {
        super(category);
    }

    // @Override
    // public void setInitialRequest() {
    //   setRequest(RenderingRequests.SITE_RENDERING);
    // }

    @Override
    public String render() {
        return this.textToBeRendered;
    }
}
