package g6.gcm.client.entity;

import com.jfoenix.controls.JFXButton;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.core.entity.CityDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CityPreviewRendering<C extends CityDTO> extends VBoxRendering<C> {

  private Label cityNameLBL;
  private Label dateLBL;//TODO remove?
  private Label subscriptionDateLBL;//TODO remove?
  private Label numberOfMapsLBL;
  private Label numberOfSitesLBL;
  private Label descriptionLBL;
  private HBox firstLevelButtonHB;
  private HBox secondLevelButtonsHB;
  private Label expirationDateLBL;





  public void setExpirationDateLBL(Label expirationDateLBL) {
    this.expirationDateLBL = expirationDateLBL;
  }

  public String getNumberOfMaps() {
    return numberOfMapsLBL.getText().substring(16);
  }

  public String getNumberOfSites() {
    return numberOfSitesLBL.getText().substring(17);
  }

  public String getExpirationDate(){
    return expirationDateLBL.getText().substring(12);
  }

  public Label getExpirationDateLBL(){
    return expirationDateLBL;
  }

  /**
   * to compare city name with search field result
   * @return String
   */
  public String getCityName() {
    return cityNameLBL.getText();
  }

  /**
   * to compare city description with search field result
   * @return String
   */
  public String getDescription() {
    return descriptionLBL.getText();
  }

  /**
   *  to decide which which date to show (Request Date ,Subscription Date)
   * @return String
   */

  public String getDate (){return  dateLBL.getText().substring(15);}

  public void setDate (String text){ this.dateLBL.setText(text);}

  //TODO: add city version?
  public CityPreviewRendering(C renderable) {
    super(renderable);
  }

  @Override
  protected void initializePreview() {
    // Setup container VBox as needed
    setMinHeight(500);
    setAlignment(Pos.TOP_LEFT);
    setSpacing(10);
    setPadding(new Insets(30, 35, 45, 35));
    setFillWidth(true);
    getStyleClass().add("label-20");

    // Initialize labels
    cityNameLBL = new Label(renderable.getCityName());
    cityNameLBL.getStyleClass().addAll("label-34", "label-city-preview-title");
    cityNameLBL.setEffect(new Glow(0.88));
    subscriptionDateLBL = new Label("Subscription Date: "); // TODO: remove?
    numberOfMapsLBL = new Label("Number of Maps: " + renderable.getCollectionsMapsNumber());
    numberOfSitesLBL = new Label("Number of Sites: " + renderable.getCollectionsNumberOfSites());

    String description = renderable.getCityDescription();
    descriptionLBL = new Label(description == null ? "No available description at this time."
        : description.substring(0, description.length() - 1) + " km^2.");
    descriptionLBL.setWrapText(true);
    descriptionLBL.getStyleClass().add("label-18");

    firstLevelButtonHB = new HBox();
    secondLevelButtonsHB = new HBox();
  }

  private void styleLabels(Label... labels) {
    for (Label label : labels) {
      label.getStyleClass().add("colored-label-white");
    }
  }

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

    //TODO: add CityName Effect

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

    // Insert initialized labels next
    getChildren().addAll(subscriptionDateLBL, numberOfMapsLBL, numberOfSitesLBL);

    // Create the second Region for the nice spacing
    Region secondRegion = new Region();
    secondRegion.setMaxHeight(15);
    VBox.setVgrow(secondRegion, Priority.ALWAYS);
    getChildren().add(secondRegion);

    // Insert description labels next
    getChildren().add(descriptionLBL);

    // Create the third Region for the nice look completion
    Region thirdRegion = new Region();
    thirdRegion.setPrefHeight(20);
    VBox.setVgrow(thirdRegion, Priority.ALWAYS);
    getChildren().add(thirdRegion);

    // Prepare & insert HBoxes
    firstLevelButtonHB.setMaxHeight(45);
    secondLevelButtonsHB.setMaxHeight(45);
    secondLevelButtonsHB.setSpacing(10);
    VBox.setVgrow(firstLevelButtonHB, Priority.ALWAYS);
    VBox.setVgrow(secondLevelButtonsHB, Priority.ALWAYS);
    getChildren().add(firstLevelButtonHB);
    getChildren().add(secondLevelButtonsHB);

    styleLabels(cityNameLBL, subscriptionDateLBL, numberOfMapsLBL, numberOfSitesLBL, descriptionLBL);
  }


  public HBox getFirstLevelButtonHB() {
    return firstLevelButtonHB;
  }

  public HBox getSecondLevelButtonsHB() {
    return secondLevelButtonsHB;
  }

  public int getCityID() {
    return renderable.getCityID();
  }

  protected void designJFXButton(JFXButton button) {
    button.getStyleClass().add("city-catalog-buttons");
    button.setMaxWidth(Double.MAX_VALUE);
    button.setMaxHeight(Double.MAX_VALUE);
    button.setOnMouseClicked(e -> {
      CitiesManager.getInstance().setBean(renderable);
      System.out.println("works clicked");
      RenderingsEngineer.CatalogEngineer
          .catalogMouseClickHandler(((JFXButton) e.getSource()).getText());
    });
    HBox.setHgrow(button, Priority.ALWAYS);
    RenderingsEngineer.toggleButtonsEnablement(button);
  }

}

