package g6.gcm.client.entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.PriceRequestManager;
import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.PriceRequestDTO;
import g6.gcm.core.interfaces.Renderable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * This class responsible for generating new VBox and build it from scratch
 *
 * @param <C>
 */
public class MapsPricesRenddering<C extends CityDTO> extends VBoxRendering<C> {

    private Label cityNameLBL;
    private Label requestStatusLBL;
    private Label requestDateLBL;
    private Label collectionsVersionLBL;
    private Label subscriptionPriceLBL;
    private Label oneShotDealPriceLBL;
    private HBox buttonHB;
    private HBox requestStatusHB;
    private HBox requestDateHB;
    private HBox subscriptionPriceHB;
    private HBox oneShotDealHB;
    private String Statusfilter;
    private JFXTextField subscriptionPriceTF;
    private JFXTextField oneShotDealTF;


    public MapsPricesRenddering(C renderable) {
        super(renderable);
    }

    public String getStatusfilter() {
        return Statusfilter;
    }

    public void setStatusfilter(String statusfilter) {
        Statusfilter = statusfilter;
    }

    /**
     * Setup container VBox as needed
     * Initializing all the labels for the texts that should be displayed in the VBox
     * some of the labels we could set them the data we want from specific city so to do that we called renderable in some labels
     */
    @Override
    protected void initializePreview() {

        setMinHeight(500);
        setAlignment(Pos.TOP_LEFT);
        setSpacing(10);
        setPadding(new Insets(30, 35, 45, 35));
        setFillWidth(true);
        getStyleClass().add("label-20");

        cityNameLBL = new Label(renderable.getCityName());
        cityNameLBL.getStyleClass().addAll("label-34", "label-city-previe-title");
        cityNameLBL.setEffect(new Glow(0.88));
        requestStatusLBL = new Label("Request Status:");
        requestDateLBL = new Label("Request Date: ");
        collectionsVersionLBL = new Label("Collection Version: " + renderable.getCollectionVersion());
        subscriptionPriceLBL = new Label("Subscription Price: ");
        subscriptionPriceLBL.setPrefWidth(250);
        oneShotDealPriceLBL = new Label("One Shot Deal Price: ");
        oneShotDealPriceLBL.setPrefWidth(250);
        subscriptionPriceTF = new JFXTextField();
        subscriptionPriceTF.setMaxWidth(100);
        oneShotDealTF = new JFXTextField();
        oneShotDealTF.setMaxWidth(100);

        buttonHB = new HBox();
        requestStatusHB = new HBox();
        requestDateHB = new HBox();
        subscriptionPriceHB = new HBox();
        oneShotDealHB = new HBox();
    }


    private void styleLabels(Label... labels) {
        for (Label label : labels) {
            label.getStyleClass().add("colored-label-white");
        }
    }

    /**
     * in this method we give every VBox the relevant StyleClass we do that by the index of the city in the bean list
     * then it setup the other elements for the VBox
     */
    @Override
    protected void assemble() {
        // StyleClass is decided based on the VBox's location
        switch (CitiesManager.getInstance().getBeansList().indexOf(renderable) % 3) {

            case 0: {
                getStyleClass().add("tile-leftmost-background");
                break;
            }
            case 1: {
                getStyleClass().add("tile-middle-background");
                break;
            }
            case 2: {
                getStyleClass().add("tile-rightmost-background");
                break;
            }
        }
        // Create HBox to keep cityNameLBL in the middle
        HBox cityNameHB = new HBox();
        cityNameHB.setAlignment(Pos.CENTER);

        // Insert cityNameLBL into the HBox
        cityNameHB.getChildren().add(cityNameLBL);

        // Insert cityNameHB into the container VBox
        getChildren().add(cityNameHB);

        // Create the first Region for a nice spacing
        Region firstRegion = new Region();
        firstRegion.setMinHeight(15);
        firstRegion.setPrefHeight(15);
        VBox.setVgrow(firstRegion, Priority.NEVER);
        getChildren().add(firstRegion);

        setHBoxes();

        Region secondRegion = new Region();
        secondRegion.setMinHeight(100);
        secondRegion.setPrefHeight(100);
        VBox.setVgrow(secondRegion, Priority.NEVER);
        getChildren().add(secondRegion);

        buttonHB.setMaxHeight(45);
        VBox.setVgrow(buttonHB, Priority.ALWAYS);
        getChildren().add(buttonHB);

        styleLabels(cityNameLBL, collectionsVersionLBL, requestDateLBL, subscriptionPriceLBL, oneShotDealPriceLBL);
    }


    public JFXTextField getSubscriptionPriceTF() {
        return subscriptionPriceTF;
    }


    public JFXTextField getOneShotDealTF() {
        return oneShotDealTF;
    }

    /**
     * In this method we set the labels and the TextFields to the HBoxes and the when we setup every HBox we einsert it to the VBox
     */
    private void setHBoxes() {
        requestStatusHB.setAlignment(Pos.CENTER_LEFT);
        requestStatusHB.getChildren().add(requestStatusLBL);
        getChildren().add(requestStatusHB);

        requestDateHB.setAlignment(Pos.CENTER_LEFT);
        requestDateHB.getChildren().addAll(requestDateLBL);
        getChildren().add(requestDateHB);

        subscriptionPriceHB.setAlignment(Pos.CENTER_LEFT);
        subscriptionPriceHB.getChildren().addAll(subscriptionPriceLBL, getSubscriptionPriceTF());
        getChildren().add(subscriptionPriceHB);

        oneShotDealHB.setAlignment(Pos.CENTER_LEFT);
        oneShotDealHB.getChildren().addAll(oneShotDealPriceLBL, getOneShotDealTF());
        getChildren().add(oneShotDealHB);
    }

    public HBox getButtonHB() {
        return buttonHB;
    }

    public HBox getRequestStatusHB() {
        return requestStatusHB;
    }

    public HBox getRequestDateHB() {
        return requestDateHB;
    }

    public HBox getSubscriptionPriceHB() {
        return subscriptionPriceHB;
    }

    public HBox getOneShotDealHB() {
        return oneShotDealHB;
    }


    /**
     * This method setup the button that it take as a parameter and give it a listener,
     * and in the listener we set the relevant PriceRequest and City beans so we can handle any request we want when the method catalogMouseClickHandler called
     *
     * @param button
     */
    protected void designJFXButton(JFXButton button) {
        button.getStyleClass().add("city-catalog-buttons");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        button.setOnMouseClicked(e -> {

            for (Renderable priceRequest : PriceRequestManager.getInstance().getBeansList())
                if (((PriceRequestDTO) priceRequest).getCityName().equals(renderable.getCityName()))
                    PriceRequestManager.getInstance().setBean(priceRequest);
            PriceRequestDTO priceRequest = new PriceRequestDTO();
            priceRequest.setCityName(renderable.getCityName());
            priceRequest.setSubscribtionPrice(Double.parseDouble(getSubscriptionPriceTF().getText()));
            priceRequest.setOneShotDealPrice(Double.parseDouble(getOneShotDealTF().getText()));
            PriceRequestManager.getInstance().setBean(priceRequest);
            CitiesManager.getInstance().setBean(renderable);
            MapsPricesRenderingsEngineer.catalogMouseClickHandler(((JFXButton) e.getSource()).getText());
        });
        HBox.setHgrow(button, Priority.ALWAYS);
    }

}
