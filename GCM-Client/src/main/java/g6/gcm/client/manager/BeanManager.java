package g6.gcm.client.manager;

import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.core.interfaces.Renderable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import jfxtras.labs.scene.control.BeanPathAdapter;

public class BeanManager {

    protected ObjectProperty<Renderable> bean;
    protected BeanPathAdapter<Renderable> superBean;
    protected ListProperty<Renderable> beansList;

    public BeanManager() {
    }

/**************************************************************************************************/
/**         _____      _   _                            _____      _   _                         **/
/**        / ____|    | | | |                  ___     / ____|    | | | |                        **/
/**       | |  __  ___| |_| |_ ___ _ __ ___   ( _ )   | (___   ___| |_| |_ ___ _ __ ___          **/
/**       | | |_ |/ _ \ __| __/ _ \ '__/ __|  / _ \/\  \___ \ / _ \ __| __/ _ \ '__/ __|         **/
/**       | |__| |  __/ |_| ||  __/ |  \__ \ | (_>  <  ____) |  __/ |_| ||  __/ |  \__ \         **/
/**        \_____|\___|\__|\__\___|_|  |___/  \___/\/ |_____/ \___|\__|\__\___|_|  |___/         **/
/**                                                                                              **/
    /************************************************************************************************/

    // TODO synchronize?
    public Renderable getBean() {
        return bean.get();
    }

    public void setBean(Renderable bean) {
        this.bean.set(bean);
    }

    public ObjectProperty<Renderable> beanProperty() {
        return bean;
    }

    public BeanPathAdapter<Renderable> getSuperBean() {
        return superBean;
    }

    public ObservableList<? extends Renderable> getBeansList() {
        return beansList.get();
    }

    public void setBeansList(ObservableList<? super Renderable> beansList) {
        this.beansList.set((ObservableList<Renderable>) beansList);
    }

    public ListProperty<Renderable> beansListProperty() {
        return beansList;
    }


  protected void logBeanUpdate() {
    GCMClient.getClient().say(ConsoleTextColorsFactory.ANSI_YELLOW
        + ((AbstractDTO) getBean()).getType() + ConsoleTextColorsFactory.ANSI_RESET
        + " bean was just set to: " + getBean());
  }

}
