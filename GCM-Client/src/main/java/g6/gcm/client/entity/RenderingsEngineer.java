package g6.gcm.client.entity;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.manager.*;
import g6.gcm.core.entity.*;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.Renderable;
import g6.gcm.core.interfaces.Request.RenderingRequests;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


public class RenderingsEngineer {

  public static DropShadow dropShadow = new DropShadow();

  static {
    // Initialize DropShadow effect
    dropShadow.setRadius(10);
    dropShadow.setWidth(21);
    dropShadow.setHeight(21);
    dropShadow.setOffsetX(-2);
    dropShadow.setOffsetY(4);
    dropShadow.setColor(Color.BLACK);
  }

  public static ConcurrentHashMap<Renderable, MapRendering> createSitesMapRenderings() {

    // Get Map Renderings Builder
    MapRenderingsBuilder mapRenderingsBuilder = new MapRenderingsBuilder();

    ConcurrentHashMap<Renderable, MapRendering> sitesRenderings = new ConcurrentHashMap<>();

    MapRendering siteRendering;

    // Loop over all sites to be rendered
    for (Renderable site : SitesManager.getInstance().getBeansList()) {

      site.setRequest(RenderingRequests.SITE_RENDERING);

      // Create rendering from renderable and store it in HashMap
      siteRendering = mapRenderingsBuilder.createRendering(site);
      sitesRenderings.put(site, siteRendering);
    }
    return sitesRenderings;
  }

  public static ConcurrentHashMap<Renderable, MapRendering> createToursMapRenderings() {

    // Get Map Renderings Builder
    MapRenderingsBuilder mapRenderingsBuilder = new MapRenderingsBuilder();

    ConcurrentHashMap<Renderable, MapRendering> toursRenderings = new ConcurrentHashMap<>();

    MapRendering tourRendering;

    // Loop over all sites to be rendered
    for (Renderable tour : ToursManager.getInstance().getBeansList()) {

      tour.setRequest(RenderingRequests.TOUR_RENDERING);

      // Create rendering from renderable and store it in HashMap
      tourRendering = mapRenderingsBuilder.createRendering(tour);
      toursRenderings.put(tour, tourRendering);
    }
    return toursRenderings;
  }

  public static void MapsRequestsBuilder(TilePane pane) {
    pane.getChildren().clear();

    for (Renderable mapRequest : MapsManager.getInstance().getBeansList()) {
      VBox vbox1 = new VBox();
      HBox hbox = new HBox();
      hbox.setAlignment(Pos.TOP_LEFT);
      hbox.setCenterShape(true);
      hbox.setScaleShape(true);
      hbox.setCacheShape(true);
      hbox.setMinWidth(Region.USE_COMPUTED_SIZE);
      hbox.setMinHeight(Region.USE_COMPUTED_SIZE);
      hbox.setPrefWidth(200);
      hbox.setPrefHeight(100);
      hbox.setMaxWidth(Region.USE_COMPUTED_SIZE);
      hbox.setMaxHeight(Region.USE_COMPUTED_SIZE);
      hbox.setFillHeight(true);

      VBox HboxsVbox1 = new VBox();
      HboxsVbox1.setAlignment(Pos.CENTER_LEFT);
      HboxsVbox1.setSpacing(20);

      VBox HboxsVbox2 = new VBox();
      Region region = new Region();
      region.setPrefHeight(212);
      region.setPrefWidth(169);

      Label city = new Label();
      Label MapId = new Label();
      Label MapVersion = new Label();
      Label RequestDate = new Label();
      Label Status = new Label();
      JFXButton button = new JFXButton();
      button.setTextFill(Color.WHITE);
      button.setTextOverrun(OverrunStyle.ELLIPSIS);
      button.setButtonType(JFXButton.ButtonType.FLAT);
      button.setMinWidth(Region.USE_COMPUTED_SIZE);
      button.setMinHeight(Region.USE_COMPUTED_SIZE);
      button.setPrefHeight(Region.USE_COMPUTED_SIZE);
      button.setPrefWidth(Region.USE_COMPUTED_SIZE);

      city.setText("City: " + String.valueOf(((MapDTO) mapRequest).getCityID()));
      city.setTextFill(Color.WHITE);
      MapId.setText("Map ID: " + String.valueOf(((MapDTO) mapRequest).getMapID()));
      MapId.setTextFill(Color.WHITE);
      MapVersion.setText("Map Version: " + String.valueOf(((MapDTO) mapRequest).getVersion()));
      MapVersion.setTextFill(Color.WHITE);
      for (ActivityDTO activity : ActivityManager.getListProperty()) {
        if (activity.getDoneTo() == ((MapDTO) mapRequest).getMapID()) {
          RequestDate.setText("Request Date: " + String.valueOf(activity.getDoneTo()));
        }
      }
      RequestDate.setTextFill(Color.WHITE);
      HboxsVbox1.getChildren().addAll(city, MapId, MapVersion, RequestDate);
      hbox.getChildren().add(HboxsVbox1);
      hbox.getChildren().add(region);
      Status.setText(String.valueOf(((MapDTO) mapRequest).getStatus()));

      Status.setTextAlignment(TextAlignment.CENTER);
      Status.setTextFill(Color.WHITE);
      HboxsVbox2.getChildren().add(Status);
      Region region2 = new Region();
      region2.setPrefHeight(16);
      region2.setPrefWidth(536);
      HboxsVbox2.getChildren().add(region2);

      button.setText("Approve");
      button.setPrefWidth(536);
      button.setPrefHeight(66);
      HboxsVbox2.getChildren().add(button);
      button.setOnMouseClicked(event -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Request Approval");
        alert.setHeaderText(null);
        alert.setContentText(
            "You have successfully Approved the request");

        alert.showAndWait();
        HboxsVbox2.getChildren().remove(button);
        Status.setText("Approved");
        MapDTO mapDto = new MapDTO();
        mapDto.setMapID(((MapDTO) mapRequest).getMapID());
        mapDto.setRequest(ClientsInquiries.UPDATE_MAP_STATUS);
        GCMClient.send(mapDto);
      });
      hbox.getChildren().add(HboxsVbox2);

      vbox1.getChildren().add(hbox);
      vbox1.getStyleClass().add("rounded-background");
      vbox1.setPadding(new Insets(20, 40, 20, 40));
      vbox1.setPrefHeight(252);
      vbox1.setPrefWidth(1095);
      pane.getChildren().add(vbox1);

    }


  }


  public static void PricesRequestsBuilder(TilePane pane) {
    pane.getChildren().clear();

    for (Renderable priceRequest : PriceRequestManager.getInstance().getBeansList()) {
      VBox vbox1 = new VBox();
      System.out.println("F");
      HBox hbox = new HBox();
      hbox.setAlignment(Pos.TOP_LEFT);
      hbox.setCenterShape(true);
      hbox.setScaleShape(true);
      hbox.setCacheShape(true);
      hbox.setMinWidth(Region.USE_COMPUTED_SIZE);
      hbox.setMinHeight(Region.USE_COMPUTED_SIZE);
      hbox.setPrefWidth(200);
      hbox.setPrefHeight(100);
      hbox.setMaxWidth(Region.USE_COMPUTED_SIZE);
      hbox.setMaxHeight(Region.USE_COMPUTED_SIZE);
      hbox.setFillHeight(true);

      VBox HboxsVbox1 = new VBox();
      HboxsVbox1.setAlignment(Pos.CENTER_LEFT);
      HboxsVbox1.setSpacing(20);

      VBox HboxsVbox2 = new VBox();
      Region region = new Region();
      region.setPrefHeight(212);
      region.setPrefWidth(169);

      Label city = new Label();
      Label MapId = new Label();
      Label MapVersion = new Label();
      Label RequestDate = new Label();
      Label Status = new Label();
      JFXButton button = new JFXButton();
      button.setTextFill(Color.WHITE);
      button.setTextOverrun(OverrunStyle.ELLIPSIS);
      button.setButtonType(JFXButton.ButtonType.FLAT);
      button.setMinWidth(Region.USE_COMPUTED_SIZE);
      button.setMinHeight(Region.USE_COMPUTED_SIZE);
      button.setPrefHeight(Region.USE_COMPUTED_SIZE);
      button.setPrefWidth(Region.USE_COMPUTED_SIZE);

      city.setText("City: " + String.valueOf(((PriceRequestDTO) priceRequest).getCityName()));
      city.setTextFill(Color.WHITE);
      MapId.setText("One Shot Deal Price: " + String.valueOf(((PriceRequestDTO) priceRequest).getOneShotDealPrice()));
      MapId.setTextFill(Color.WHITE);
      MapVersion.setText("Subscription Price: " + String.valueOf(((PriceRequestDTO) priceRequest).getSubscribtionPrice()));
      MapVersion.setTextFill(Color.WHITE);

/*      for (ActivityDTO activity : ActivityManager.getListProperty()) {
        if (activity.getDoneTo() == ((MapDTO) priceRequest).getMapID()) {
          RequestDate.setText("Request Date: " + String.valueOf(activity.getDoneTo()));
        }
      }*/

     // RequestDate.setTextFill(Color.WHITE);
      HboxsVbox1.getChildren().addAll(city, MapId, MapVersion, RequestDate);
      hbox.getChildren().add(HboxsVbox1);
      hbox.getChildren().add(region);
      Status.setText(String.valueOf(((PriceRequestDTO) priceRequest).getRequestStatus()));

      Status.setTextAlignment(TextAlignment.CENTER);
      Status.setTextFill(Color.WHITE);
      HboxsVbox2.getChildren().add(Status);
      Region region2 = new Region();
      region2.setPrefHeight(16);
      region2.setPrefWidth(536);
      HboxsVbox2.getChildren().add(region2);

      button.setText("Approve");
      button.setPrefWidth(536);
      button.setPrefHeight(66);
      HboxsVbox2.getChildren().add(button);
      button.setOnMouseClicked(event -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Request Approval");
        alert.setHeaderText(null);
        alert.setContentText(
            "You have successfully Approved the request");

        alert.showAndWait();
        HboxsVbox2.getChildren().remove(button);
        Status.setText("Approved");
        PriceRequestDTO pR = new PriceRequestDTO();
        pR.setRequestID((((PriceRequestDTO) priceRequest).getRequestID()));
        pR.setRequest(ClientsInquiries.DELETE_PRICE_REQUEST);
        GCMClient.send(pR);
      });
      hbox.getChildren().add(HboxsVbox2);

      vbox1.getChildren().add(hbox);
      vbox1.getStyleClass().add("rounded-background");
      vbox1.setPadding(new Insets(20, 40, 20, 40));
      vbox1.setPrefHeight(252);
      vbox1.setPrefWidth(1095);
      pane.getChildren().add(vbox1);

    }


  }

  public static void toggleButtonsEnablement(Button... buttons) {
    boolean disable =
        UsersManager.getInstance().getBean().getRequest() != ServersCommands.SIGN_IN_COSTUMER;
    for (Button button : buttons) {
      button.setDisable(disable);
    }
  }

  public static class CatalogEngineer {

    private static JFXDrawer theDrawer = null;

    public static ConcurrentHashMap<Renderable, CityPreviewRendering> refreshCatalogsCitiesView(
        TilePane citiesTP,
        JFXDrawer catalogDRAWER) {
      // Clean existing cities
      // TODO: improve upon, add ability to check, add and remove only necessary cities' renderings
      theDrawer = catalogDRAWER;

      List<CityDTO> renderablecities = (List<CityDTO>) CitiesManager.getInstance().getBeansList();

      //List of city renderables
      ConcurrentHashMap<Renderable, CityPreviewRendering> cityPreview = new ConcurrentHashMap<>();

      if (renderablecities.get(0) != null) {

        citiesTP.getChildren().clear();

        VBoxRendering cityRendering;

        // Loop over all cities to be rendered
        for (CityDTO renderableCity : renderablecities) {

          cityRendering = new CityPreviewRendering(renderableCity);

          // Apply a DropShadow effect

          cityRendering.setEffect(dropShadow);

          // Create and start setting up first level button
          JFXButton subscribeOrExtendBTN = new JFXButton();
          cityRendering.designJFXButton(subscribeOrExtendBTN);
          ((CityPreviewRendering) cityRendering).getFirstLevelButtonHB().getChildren()
              .add(subscribeOrExtendBTN);

          // Create and start setting up second level button(s)
          JFXButton secondLevelBTN = new JFXButton();
          cityRendering.designJFXButton(secondLevelBTN);
          ((CityPreviewRendering) cityRendering).getSecondLevelButtonsHB().getChildren()
              .addAll(secondLevelBTN);
          PurchaseDTO matchingPurchase;

          if ((matchingPurchase = (PurchaseDTO) PurchasesManager.getInstance().getBeansList()
                  .stream().filter(o -> ((PurchaseDTO) o).getCityID() == renderableCity.getCityID())
                  .findAny().orElse(null)) != null) {
            // If the current user has this city as an active subscription show "Extend" option
            subscribeOrExtendBTN.setText("Extend Subscription");

            // Also, show a "Download" option for the city & a "View" button to view the map
            secondLevelBTN.setText("Download");
            JFXButton viewBTN = new JFXButton("View");
            cityRendering.designJFXButton(viewBTN);
            HBox.setHgrow(viewBTN, Priority.NEVER);

            ((CityPreviewRendering) cityRendering).getSecondLevelButtonsHB().getChildren()
                .addAll(viewBTN);


          } else {

            // If the current user doesn't have an active subscription to this city ...
            subscribeOrExtendBTN.setText("Subscribe");
            secondLevelBTN.setText("One Shot Deal");
          }

          citiesTP.getChildren().add(cityRendering);
          cityPreview.put(renderableCity, (CityPreviewRendering) cityRendering);
        }
      }
      return cityPreview;
    }

    public static void catalogMouseClickHandler(String buttonsText) {
      switch (buttonsText) {
        case "View": {
          AnchorPane customerCityExplorer = new AnchorPane();
          StageManager.getManager()
              .switchScene(FXMLFactory.CUSTOMER_CITY_EXPLORER, customerCityExplorer);
          theDrawer.setSidePane(customerCityExplorer);
          theDrawer.open();
          break;
        }
        case "Subscribe": {
          PurchasesManager.getInstance().subscribeToCity();
          System.out.println("Subscribe clicked");
          break;
        }
        case "Extend Subscription": {
          PurchasesManager.getInstance().extendSubscription();
          System.out.println("Extend Subscription clicked");
          break;
        }
        case "One Shot Deal": {
          PurchasesManager.getInstance().createOneShotDeal();
          System.out.println("One Shot Deal clicked");
          break;
        }
        case "Download": {
          System.out.println("Download clicked");
          // TODO: implement city download
          break;
        }
      }
    }
  }

  public static class EngineeredCellFactories {

    public static ListCell<Renderable> callLV(ListView<Renderable> renderingsListView) {
      return new ListCell<Renderable>() {
        @Override
        protected void updateItem(Renderable item, boolean empty) {
          super.updateItem(item, empty);
          this.setVisible(item != null || !empty);
          this.setManaged(false);
          setText(empty ? "" : item.render());
        }
      };
    }

    public static TreeCell<Renderable> callTV(TreeView<Renderable> renderingsTreeView) {
      return new TreeCell<Renderable>() {
        @Override
        protected void updateItem(Renderable item, boolean empty) {
          super.updateItem(item, empty);
          setText(empty ? "callCTV Error" : item.render());
        }
      };
    }

    public static TreeCell<Renderable> callCTV(TreeView<Renderable> renderingsTreeView) {
      return new CheckBoxTreeCell<Renderable>() {
        @Override
        public void updateItem(Renderable item, boolean empty) {
          super.updateItem(item, empty);
          setText(empty ? "" : item.render());
        }
      };
    }
  }


}
