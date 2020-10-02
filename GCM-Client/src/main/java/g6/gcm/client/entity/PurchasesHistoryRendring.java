package g6.gcm.client.entity;

import com.jfoenix.controls.JFXButton;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.PurchasesManager;
import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.PurchaseDTO;
import g6.gcm.core.interfaces.Renderable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class PurchasesHistoryRendring<C extends CityDTO> extends VBoxRendering<C> {

    private Label cityNameLBL;
    private Label numberOfMapsLBL;
    private Label numberOfSitesLBL;
    private Label numberOfToursLBL;
    private Label descriptionLBL;
    private Label purchaseTypeLBL;
    private Label purchasedOnLBL;
    private Label purchaseExpiredOnLBL;
    private HBox mainHBox;
    private HBox purchaseTypeHB;
    private HBox purchasedOnHB;
    private HBox purchaseExpiredOnHB;
    private VBox purchasesVbox;
    private VBox ButtonsVbox;
    private String purchaseTypeString;

    public String getPurchaseTypeString() {
        return purchaseTypeString;
    }

    public void setPurchaseTypeString(String purchaseTypeString) {
        this.purchaseTypeString = purchaseTypeString;
    }

    public PurchasesHistoryRendring(C renderable) {
        super(renderable);
    }

    public VBox getButtonsVbox() {
        return ButtonsVbox;
    }

    public VBox getPurchasesVbox() {
        return purchasesVbox;
    }

    public void setPurchasesVbox(VBox purchasesVbox) {
        this.purchasesVbox = purchasesVbox;
    }

    public HBox getPurchaseTypeHB() {
        return purchaseTypeHB;
    }

    public void setPurchaseTypeHB(HBox purchaseTypeHB) {
        this.purchaseTypeHB = purchaseTypeHB;
    }

    public HBox getPurchasedOnHB() {
        return purchasedOnHB;
    }

    public void setPurchasedOnHB(HBox purchasedOnHB) {
        this.purchasedOnHB = purchasedOnHB;
    }

    public HBox getPurchaseExpiredOnHB() {
        return purchaseExpiredOnHB;
    }

    public void setPurchaseExpiredOnHB(HBox purchaseExpiredOnHB) {
        this.purchaseExpiredOnHB = purchaseExpiredOnHB;
    }

    @Override
    protected void initializePreview() {

        setMinHeight(240);
        setMaxHeight(240);
        setAlignment(Pos.TOP_LEFT);
//        setSpacing(10);
        setPadding(new Insets(35, 40, 35, 40));
        setMinWidth(1095);

        cityNameLBL = new Label(renderable.getCityName());
        cityNameLBL.getStyleClass().addAll("label-34", "label-city-previe-title");
        cityNameLBL.setEffect(new Glow(0.88));

        descriptionLBL = new Label(renderable.getCityDescription());
        descriptionLBL.setWrapText(true);
        descriptionLBL.setPrefHeight(90);
        descriptionLBL.setPrefWidth(400);

        purchaseTypeLBL = new Label("Purchase Type:");
        purchasedOnLBL = new Label("Purchased On:");
        purchaseExpiredOnLBL = new Label("Expired On:");

        mainHBox = new HBox();
        mainHBox.setSpacing(30);
        mainHBox.setAlignment(Pos.CENTER);
        purchaseTypeHB = new HBox();
        purchaseTypeHB.setSpacing(10);
        purchasedOnHB = new HBox();
        purchasedOnHB.setSpacing(10);
        purchaseExpiredOnHB = new HBox();
        purchaseExpiredOnHB.setSpacing(10);
        purchasesVbox = new VBox();
        purchasesVbox.setAlignment(Pos.CENTER);
        purchasesVbox.setSpacing(20);
        ButtonsVbox = new VBox();
        ButtonsVbox.setAlignment(Pos.CENTER);
    }

    private void styleLabels(Label... labels) {
        for (Label label : labels) {
            label.getStyleClass().add("colored-label-white");
            label.getStyleClass().add("label-20");
        }
    }

    @Override
    protected void assemble() {
        getStyleClass().add("rounded-background");

        Region firstRegion = new Region();
        firstRegion.setMinHeight(60);
        firstRegion.setPrefHeight(60);
        VBox.setVgrow(firstRegion, Priority.NEVER);

        purchaseTypeHB.getChildren().add(purchaseTypeLBL);
        purchasedOnHB.getChildren().add(purchasedOnLBL);
        purchaseExpiredOnHB.getChildren().add(purchaseExpiredOnLBL);

        purchasesVbox.getChildren().addAll(purchaseTypeHB, purchasedOnHB, purchaseExpiredOnHB);

        mainHBox.getChildren().addAll(descriptionLBL, purchasesVbox, firstRegion, ButtonsVbox);

        getChildren().addAll(cityNameLBL, mainHBox);

        styleLabels(cityNameLBL, descriptionLBL, purchaseTypeLBL, purchasedOnLBL, purchaseExpiredOnLBL);

    }

    protected void designJFXButton(JFXButton button) {
        button.getStyleClass().add("city-catalog-buttons");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        button.setOnMouseClicked(e -> {
            for (Renderable purchases : PurchasesManager.getInstance().getBeansList())
                if (((PurchaseDTO) purchases).getCityID() == renderable.getCityID()) {
                    PurchasesManager.getInstance().setBean(purchases);
                    CitiesManager.getInstance().setBean(renderable);
                }
            PurchaseHistoryRenderingsEngineer.catalogMouseClickHandler(((JFXButton) e.getSource()).getText());
        });
        HBox.setHgrow(button, Priority.ALWAYS);
    }
}
