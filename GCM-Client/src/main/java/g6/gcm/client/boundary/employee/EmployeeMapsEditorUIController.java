package g6.gcm.client.boundary.employee;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.Extent;
import com.sothawo.mapjfx.MapLabel;
import com.sothawo.mapjfx.MapType;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.Marker.Provided;
import com.sothawo.mapjfx.XYZParam;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import g6.gcm.client.boundary.FXMLFactory;
import g6.gcm.client.entity.MapRendering;
import g6.gcm.client.entity.RenderingsEngineer;
import g6.gcm.client.entity.RenderingsEngineer.EngineeredCellFactories;
import g6.gcm.client.entity.SiteRendering;
import g6.gcm.client.entity.TourRendering;
import g6.gcm.client.manager.CitiesManager;
import g6.gcm.client.manager.GCMClient;
import g6.gcm.client.manager.MapBindingsManager;
import g6.gcm.client.manager.MapsManager;
import g6.gcm.client.manager.SitesClassificationsManager;
import g6.gcm.client.manager.SitesManager;
import g6.gcm.client.manager.StageManager;
import g6.gcm.client.manager.ToursManager;
import g6.gcm.core.entity.CityDTO;
import g6.gcm.core.entity.ExtentTransfer;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.Renderable;
import java.time.LocalTime;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import jfxtras.labs.scene.control.BeanPathAdapter;
import org.controlsfx.control.MaskerPane;
// import org.controlsfx.control.MaskerPane;

public class EmployeeMapsEditorUIController {

  /**
   * Initialize initial coordinates and default zoom.
   */
  private final Coordinate ISRAEL = new Coordinate(31.494261815532752, 35.1947021484375);
  private final int DEFAILT_ZOOM = 8;
  BeanPathAdapter<Coordinate> centerCoordinateBean;
  private XYZParam xyzParams = new XYZParam()
      .withUrl(
          "https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x})")
      .withAttributions(
          "'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'");
  /**
   * Utils.
   */
  private ConcurrentHashMap<Renderable, MapRendering> mapsSites = new ConcurrentHashMap<>();
  private ConcurrentHashMap<Renderable, MapRendering> mapsTours = new ConcurrentHashMap<>();

  @FXML
  private AnchorPane mainViewAP;

  @FXML
  private MapView mapView;

  @FXML
  private Accordion accordion;

  @FXML
  private TitledPane quickGuideTP;

  @FXML
  private JFXButton shallTheEditingBeginBTN;

  @FXML
  private TitledPane chooserTP;

  @FXML
  private JFXComboBox<Renderable> citiesCB;

  @FXML
  private JFXTextField cityZoomLevelTF;

  @FXML
  private JFXTextField cityCenterCoordinatesTF;

  @FXML
  private Label cityCreateLBL;

  @FXML
  private JFXButton cityCreateBTN;

  @FXML
  private JFXComboBox<Renderable> mapsCB;

  @FXML
  private JFXTextField mapZoomLevelTF;

  @FXML
  private JFXTextField mapCenterCoordinatesTF;

  @FXML
  private Label mapAttachLBL;

  @FXML
  private JFXButton mapAttachBTN;

  @FXML
  private TitledPane sitesEditorTP;

  @FXML
  private JFXButton siteCreateBTN;

  @FXML
  private JFXTextField siteNameTF;

  @FXML
  private JFXToggleButton siteLockTB;

  @FXML
  private JFXComboBox<Renderable> siteCategoryCB;

  @FXML
  private JFXTextField siteVisitDurationTF;

  @FXML
  private JFXToggleButton siteAccessibilityTB;

  @FXML
  private JFXTextArea siteDescriptionTA;

  @FXML
  private JFXTextField siteCenterCoordinatesTF;

  @FXML
  private JFXButton siteDeleteBTN;

  @FXML
  private JFXButton siteSaveBTN;

  @FXML
  private TitledPane toursEditorTP;

  @FXML
  private JFXButton tourCreateBTN;

  @FXML
  private JFXComboBox<Renderable> toursCB;

  @FXML
  private JFXToggleButton tourLockTB;

  @FXML
  private JFXListView<Renderable> leftLV;

  @FXML
  private JFXButton moveLeftBTN;

  @FXML
  private JFXButton moveRightBTN;

  @FXML
  private JFXListView<Renderable> rightLV;

  @FXML
  private JFXButton tourDeleteBTN;

  @FXML
  private JFXButton tourSaveBTN;

  @FXML
  private JFXButton saveMapBTN;

  @FXML
  private JFXButton requestReleaseBTN;

  @FXML
  private JFXToggleButton mapTypeTB;

  @FXML
  private Label topPaneLBL;

  @FXML
  private MaskerPane maskerP;

  private ChangeListener cityCenterListener;
  private ChangeListener mapCenterListener;
  private ListChangeListener sitesOfMapListener;
  private ChangeListener sitesOfTourListener;


  private ObjectProperty<Renderable> selectedSite = new SimpleObjectProperty<>();
  private Marker newSiteMarker;
  private MapLabel newSiteLBL;

  private boolean toursFocused = false;

  @FXML
  private void initialize() {

    mainViewAP = StageManager.getManager().getMainViewAP();

    CitiesManager.getInstance().requestAllCities();

    // Initially, bind the maps view to the city bean in CitiesManager as it has ISRAEL_VIEW.
    MapBindingsManager.getInstance().bindThis(mapView, CitiesManager.getInstance());

    // TODO remove? #now
    selectedSite.bindBidirectional(SitesManager.getInstance().beanProperty());

    initializeEventHandlers();

    initializeChooserTitledPane();

    initializeSitesEditorTitledPane();

    initializeToursEditorTitledPane();

    // Initialize sites editor control components

    // Initialize listeners
    initializeListeners();

    // Expand "Quick Guide" pane
    accordion.setExpandedPane(quickGuideTP);

    mapTypeTB.setEffect(RenderingsEngineer.dropShadow);

    maskerP.setText("Please Wait...");

    maskerP.setVisible(true);

    mapView.initialize();

    cityCreateBTN.setOnMouseClicked(click -> {
      StageManager.getManager().switchScene(FXMLFactory.EMPLOYEE_MAPS_ATTACHER, mainViewAP);
    });

    mapAttachBTN.setOnMouseClicked(click -> {
      StageManager.getManager().switchScene(FXMLFactory.EMPLOYEE_MAPS_ATTACHER, mainViewAP);
    });

    SitesManager.getInstance().beansListProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != oldValue && newValue != null) {
        renderSites();
      }
    });

    saveMapBTN.setOnMouseClicked(click -> {
      System.out.println("Maps Current Zoom: " + mapView.getZoom());
      System.out.println("CitiesManager: ");
      // System.out.println("getZoom(): " + CitiesManager.getInstance().getZoom());
      System.out.println(
          "getBean().getZoom(): " + ((CityDTO) CitiesManager.getInstance().getBean()).getZoom());
      System.out.println("MapsManager: ");
      // System.out.println("getZoom(): " + MapsManager.getInstance().getZoom());
    });

    saveMapBTN.setOnMouseClicked(click -> {

      MapsManager.getInstance().requestMapSave();
      pauseScreen(2);
    });

    MapsManager.getInstance().beanProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.getRequest() == ServersCommands.MAP_SAVED) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Map was saved");

        // Header Text: null
        alert.setContentText("Time to request a release?");
        alert.showAndWait();
      }
    });

    sitesOfTourListener = new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != oldValue && newValue != null) {
          renderTours();
        }
      }
    };

    toursEditorTP.expandedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != oldValue) {
        if (newValue) {
          ToursManager.getInstance().beansListProperty().addListener(sitesOfTourListener);
        } else {
          ToursManager.getInstance().beansListProperty().remove(sitesOfTourListener);
        }
      }
    });
  }

  private void initializeEventHandlers() {

    // Add an event handler for "Quick Guide" button's clicks
    shallTheEditingBeginBTN.setOnMouseClicked(click -> accordion.setExpandedPane(chooserTP));

    // Temporary TODO remove? #now
    mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, event -> {
      event.consume();
      System.out.println("\n" + LocalTime.now() + " consumed");

      Renderable matchingSite;
      if ((matchingSite = mapsSites.entrySet().stream()
          .filter(entry -> ((SiteRendering) entry.getValue()).getMarker() == event.getMarker())
          .findAny().map(Entry::getKey).orElse(null)) != null) {
        selectedSite.set(matchingSite);
      }

      System.out.println(LocalTime.now() + " SET: " + selectedSite.get());
      System.out
          .println(LocalTime.now() + " getBean(): " + SitesManager.getInstance().getBean());
      System.out
          .println(
              LocalTime.now() + " getSuperBean(): " + SitesManager.getInstance()
                  .getSuperBean());
    });

    // Add an event handler for MapViewEvent#MAP_EXTENT and set the extent in the map
    mapView.addEventHandler(MapViewEvent.MAP_EXTENT, event -> {
      event.consume();
      mapView.setExtent(event.getExtent());
      System.out.println("Coordinate: " + event.getCoordinate());
      System.out.println("Extent: " + event.getExtent());
      Extent ex = event.getExtent();
      ExtentTransfer extent = new ExtentTransfer(
          ((CityDTO) CitiesManager.getInstance().getBean()).getCityID(),
          ex.getMin().getLatitude(),
          ex.getMin().getLongitude(), ex.getMax().getLatitude(), ex.getMax().getLongitude());
      extent.setRequest(ClientsInquiries.EXTENT);
      GCMClient.send(extent);

    });

    // Add an event handler for extent changes and display them in the status label
    mapView.addEventHandler(MapViewEvent.MAP_BOUNDING_EXTENT, event -> {
      event.consume();
      topPaneLBL.setText(event.getExtent().toString());
    });

    // Add an event handler for all map view clicks
    mapView.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
      event.consume();
      final Coordinate newClickCoordinates = event.getCoordinate().normalize();
      System.out.println("Event: map clicked at: " + newClickCoordinates);
      if (newSiteMarker.getVisible()) {
        final Coordinate oldClickCoordinates = newSiteMarker.getPosition();
        if (oldClickCoordinates != null) {
          animateClickMarker(oldClickCoordinates, newClickCoordinates);
        } else {
          newSiteMarker.setPosition(newClickCoordinates);
          // adding can only be done after coordinate is set
          mapView.addMarker(newSiteMarker);
        }
      }
    });


  }

  private void initializeChooserTitledPane() {

    // Initialize cities check box
    setUpCitiesComboBox();

    // Initialize maps check box
    setUpMapsComboBox();

    mapView.centerProperty().addListener((observable, oldValue, newValue) -> {
      System.out
          .println(
              "City:" + ((CityDTO) CitiesManager.getInstance().getBean()).getCoordinates());
      // System.out.println("Map:" + ((MapDTO) MapsManager.getInstance().getBean()).getCoordinates());
    });
  }

  private void setUpCitiesComboBox() {

    // Bind the combobox's list to the always up to date CitiesManager list of cities
    citiesCB.itemsProperty().bind(CitiesManager.getInstance().beansListProperty());

    // Bind selected city's info to the CitiesManager
    citiesCB.valueProperty().bindBidirectional(CitiesManager.getInstance().beanProperty());

    cityCenterListener = new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (oldValue != newValue && newValue != null) {
          mapView.setCenter(CitiesManager.getInstance().getSelectedBeanCoordinates());
        }
      }
    };

    // Listen to cities ComboBox's focus for correct bindings manipulations
    citiesCB.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          if (newValue != null) {

            // Unbind previous bindings before creating a new one
            mapCenterCoordinatesTF.textProperty().unbind();
            mapZoomLevelTF.textProperty().unbind();

            // Bind selected city's info to the CitiesManager
            // MapBindingsManager.getInstance().bindThis(mapView, CitiesManager.getInstance());

            // MapsManager.getInstance().beanProperty().removeListener(mapCenterListener);
            CitiesManager.getInstance().beanProperty().addListener(cityCenterListener);

            // SitesManager.getInstance().setBeansList(FXCollections.emptyObservableList());

            // Bind selected city to the CitiesManager and to TextFields
            CitiesManager.getInstance().getSuperBean()
                .bindBidirectional("zoom", mapView.zoomProperty());
            cityCenterCoordinatesTF.textProperty().bind(
                Bindings.when(mapView.centerProperty().isNotNull())
                    .then(mapView.centerProperty().asString()).otherwise(""));
            cityZoomLevelTF.textProperty().bind(mapView.zoomProperty().asString());

            // CitiesManager.getInstance().getSuperBean()
            //     .bindBidirectional("zoom", cityZoomLevelTF.textProperty());
            // cityZoomLevelTF.textProperty().bindBidirectional(
            //     (Property<String>) mapView.zoomProperty().asString());
          }
        });
    // Set the renderings' cell factory
    citiesCB.setCellFactory(EngineeredCellFactories::callLV);
    citiesCB.setButtonCell(EngineeredCellFactories.callLV(null));
  }

  @SuppressWarnings("Duplicates")
  /**
   * Sets up the maps combo box.
   */
  private void setUpMapsComboBox() {

    // Bind the combobox's list to the always up to date MapsManager list of maps
    mapsCB.itemsProperty().bind(MapsManager.getInstance().beansListProperty());

    // Bind selected map's info to the MapsManager
    mapsCB.valueProperty().bindBidirectional(MapsManager.getInstance().beanProperty());

    mapCenterListener = new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (oldValue != newValue && newValue != null) {
          // mapView.setCenter(MapsManager.getInstance().getSelectedBeanCoordinates());
        }
      }
    };

    // Listen to maps ComboBox's focus for correct bindings manipulations
    mapsCB.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          if (newValue != null) {

            // Unbind previous bindings before creating a new one
            CitiesManager.getInstance().beanProperty().removeListener(cityCenterListener);
            CitiesManager.getInstance().getSuperBean()
                .unBindBidirectional("zoom", mapView.zoomProperty());
            cityCenterCoordinatesTF.textProperty().unbind();
            cityZoomLevelTF.textProperty().unbind();

            // Bind selected city to the CitiesManager and to TextFields
            mapCenterCoordinatesTF.textProperty().bind(
                Bindings.when(mapView.centerProperty().isNotNull())
                    .then(mapView.centerProperty().asString()).otherwise(""));
            mapZoomLevelTF.textProperty().bind(mapView.zoomProperty().asString());
          }
        });

    // Set the renderings cell factory
    mapsCB.setCellFactory(EngineeredCellFactories::callLV);
    mapsCB.setButtonCell(EngineeredCellFactories.callLV(null));
  }

  private void initializeSitesEditorTitledPane() {

    selectedSite.bindBidirectional(SitesManager.getInstance().beanProperty());

    // Initialize new site marker
    newSiteMarker = Marker.createProvided(Provided.ORANGE).setVisible(false);
    newSiteLBL = new MapLabel("New Site");
    newSiteMarker.attachLabel(newSiteLBL);

    // Set ComboBox list to all available categories
    siteCategoryCB.itemsProperty()
        .bind(SitesClassificationsManager.getInstance().beansListProperty());

    siteCategoryCB.valueProperty()
        .bindBidirectional(SitesClassificationsManager.getInstance().beanProperty());

    // Bind SitesManager's bean to site's TextFields, ToggleButtons, etc...
    SitesManager.getInstance().getSuperBean()
        .bindBidirectional("siteName", siteNameTF.textProperty());
    SitesManager.getInstance().getSuperBean()
        .bindBidirectional("description", siteDescriptionTA.textProperty());
    SitesManager.getInstance().getSuperBean()
        .bindBidirectional("recommendedVisitDuration", siteVisitDurationTF.textProperty());
    SitesManager.getInstance().getSuperBean()
        .bindBidirectional("accessible", siteAccessibilityTB.selectedProperty());
    siteCenterCoordinatesTF.textProperty().bind(
        Bindings.createStringBinding(
            () -> SitesManager.getInstance().getSelectedBeanCoordinates().toString(),
            SitesManager.getInstance().beanProperty()));

    // Set the renderings cell factory
    siteCategoryCB.setCellFactory(EngineeredCellFactories::callLV);
    siteCategoryCB.setButtonCell(EngineeredCellFactories.callLV(null));

    // Listen to sites list changes
    SitesManager.getInstance().sitesOfSelectedMapProperty().addListener(
        (ListChangeListener<? super Renderable>) change -> {
          while (change.next()) {
            if (!change.getList().isEmpty()) {
              mapView.setExtent(
                  Extent.forCoordinates(SitesManager.getInstance().getSitesCoordinates()));
            }
          }
        });

    // Add event handler for new site creation
    siteCreateBTN.setOnMouseClicked(click -> {
      createNewSite();
    });

    siteSaveBTN
        .setOnMouseClicked(click -> {
          if (siteNameTF.getText().isEmpty() || siteVisitDurationTF.getText().isEmpty()
              || siteDescriptionTA.getText().isEmpty() || siteCategoryCB.getValue() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Missing fields");

            // Header Text: null
            alert.setContentText("Please fill in all fields.");

            alert.showAndWait();
          } else {
            pauseScreen(2);
            SitesManager.getInstance().requestSiteCreation();
          }

        });

    siteDeleteBTN.setOnMouseClicked(click -> {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setHeaderText(null);
      alert.setTitle("Confirm");

      // Header Text: null
      alert.setContentText("Are you sure?");

      alert.showAndWait();
      if (alert.getResult() == ButtonType.OK) {
        SitesManager.getInstance().requestSiteDeletion();
        pauseScreen(1);
      }
    });
  }

  private void createNewSite() {
    // Create a new site
    SitesManager.getInstance().setSelectedBeanCoordinates(mapView.getCenter());
    SitesManager.getInstance().createNewSite();

    siteLockTB.setSelected(false);

    newSiteMarker.setPosition(mapView.getCenter());
    newSiteMarker.setVisible(true);

    // Add new site marker to the map's view
    mapView.addMarker(newSiteMarker);

    SitesManager.getInstance().beanProperty().addListener(((observable, oldValue, newValue) -> {
      if (newValue.getRequest() == ServersCommands.SUCCESSFULL_SITE_CREATION) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Successful creation");

        // Header Text: null
        alert.setContentText("Site is added.");

        alert.showAndWait();
        SitesManager.getInstance().beansListProperty()
            .add(SitesManager.getInstance().getBean());
      } else if (newValue.getRequest() == ServersCommands.SUCCESSFULL_SITE_DELETION) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Successfully deleted");

        // Header Text: null
        alert.setContentText("");

        alert.showAndWait();
        SitesManager.getInstance().beansListProperty()
            .add(SitesManager.getInstance().getBean());
        SitesManager.getInstance().beansListProperty()
            .remove(SitesManager.getInstance().getBean());
      }
    }));


  }

  /**
   * creates new rendering
   */
  private void renderSites() {

    // Remove sites' renderings from the map's view
    mapsSites.forEach((renderable, mapRendering) -> mapView
        .removeMarker(((SiteRendering) mapRendering).getMarker()));

    // Generate new sites' renderings
    /*ConcurrentHashMap<Renderable, MapRendering>*/
    mapsSites = RenderingsEngineer
        .createSitesMapRenderings();

    // Reverse the hash map
    /*for (Entry<Renderable, MapRendering> entry : reversedMapsSites.entrySet()) {
      mapsSites.put(entry.getValue(), entry.getKey());
    }*/

    // Add sites' renderings to the map's view
    mapsSites.forEach((renderable, mapRendering) -> mapView
        .addMarker(((SiteRendering) mapRendering).getMarker()));
  }

  private void initializeToursEditorTitledPane() {

    leftLV.setCellFactory(EngineeredCellFactories::callLV);
    rightLV.setCellFactory(EngineeredCellFactories::callLV);
    leftLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    rightLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    moveLeftBTN.setOnMouseClicked(click -> {
      if (!rightLV.getItems().isEmpty()) {
        ObservableList selectedItems = rightLV.getSelectionModel().getSelectedItems();
        leftLV.getItems().addAll(selectedItems);
        rightLV.getItems().removeAll(selectedItems);
      }
    });

    moveRightBTN.setOnMouseClicked(click -> {
      if (!leftLV.getItems().isEmpty()) {
        ObservableList selectedItems = leftLV.getSelectionModel().getSelectedItems();
        rightLV.getItems().addAll(selectedItems);
        leftLV.getItems().removeAll(selectedItems);
      }
    });

    moveRightBTN.disableProperty().bind(
        Bindings.createBooleanBinding(
            () -> leftLV.getSelectionModel().getSelectedItems().isEmpty() ? true : false,
            leftLV.getSelectionModel().getSelectedItems()));
    moveLeftBTN.disableProperty().bind(
        Bindings.createBooleanBinding(
            () -> rightLV.getSelectionModel().getSelectedItems().isEmpty() ? true : false,
            rightLV.getSelectionModel().getSelectedItems()));

    leftLV.itemsProperty()
        .bindBidirectional(SitesManager.getInstance().sitesOfSelectedTourProperty());
    rightLV.itemsProperty()
        .bindBidirectional(SitesManager.getInstance().sitesOfSelectedMapProperty());

    // Bind the combobox's list to the always up to date ToursManager list of tours
    toursCB.setItems(ToursManager.getInstance().beansListProperty());

    // Bind selected tour's info to the ToursManager
    toursCB.valueProperty().bindBidirectional(ToursManager.getInstance().beanProperty());

    // toursCB.valueProperty().addListener((observable, oldValue, newValue) -> {
    //   System.out.println(newValue);
    //
    //   if (newValue != oldValue && newValue != null) {
    //     mapsTours.entrySet().stream()
    //         // .peek(entry -> entry.getKey().setVisible(entry.getValue() == newValue));
    //         .peek(entry -> entry.getKey().setVisible(true));
    //
    //   }
    // });
    toursCB.valueProperty().addListener((observable, newValue, oldValue) -> {
      if (newValue != null && toursEditorTP.isExpanded()) {
        mapsTours.forEach((tour, coordLine) -> {
          coordLine.setVisible(tour == newValue);
        });
        mapsSites.forEach((site, marker) -> {
          marker.setVisible(SitesManager.getInstance().getSitesOfSelectedTour().contains(site));
        });
      }
    });

    // Set the renderings' cell factory
    toursCB.setCellFactory(EngineeredCellFactories::callLV);
    toursCB.setButtonCell(EngineeredCellFactories.callLV(null));

    SitesManager.getInstance().sitesOfSelectedMapProperty().addListener(
        (ListChangeListener<? super Renderable>) c -> {
          while (c.next()) {

          }
        });

    SitesManager.getInstance().sitesOfSelectedTourProperty().addListener(
        (ListChangeListener<? super Renderable>) c -> {
          while (c.next()) {

          }
        });

    // tourSitesLSV.sourceItemsProperty()
    //     .bindBidirectional(SitesManager.getInstance().beansListProperty());
    // tourSitesLSV.targetItemsProperty()
    //     .bindBidirectional(SitesManager.getInstance().sitesOfSelectedTourProperty());

    tourSaveBTN.setOnMouseClicked(click -> {

      System.out.println(ConsoleTextColorsFactory.ANSI_RED + "\nSave clicked"
          + ConsoleTextColorsFactory.ANSI_RESET);

      System.out.println("TourManager.getBean(): " + ToursManager.getInstance().getBean());
      System.out.println("getBeansList: " + SitesManager.getInstance().getBeansList());
      System.out.println(
          "SitesOfMap: " + (SitesManager.getInstance().getSitesOfSelectedMap() == null ? "null"
              : SitesManager.getInstance().getSitesOfSelectedMap()));
      System.out
          .println(
              "SitesOfTour: " + (SitesManager.getInstance().getSitesOfSelectedTour() == null
                  ? "null"
                  : SitesManager.getInstance().getSitesOfSelectedTour()));
    });
  }

  private void renderTours() {
    // Remove sites' renderings from the map's view
    mapsTours.forEach((renderable, mapRendering) -> mapView
        .removeCoordinateLine(((TourRendering) mapRendering).getTourLine()));

    System.out.println("removing " + mapsTours);

    // Generate new sites' renderings
    /*ConcurrentHashMap<Renderable, MapRendering>*/
    mapsTours = RenderingsEngineer
        .createToursMapRenderings();

    // Reverse the hash map

    System.out.println("reversed to " + mapsTours);
    // Add sites' renderings to the map's view
    mapsTours.forEach((renderable, mapRendering) -> mapView
        .addCoordinateLine(((TourRendering) mapRendering).getTourLine()));

    System.out.println("added to " + mapsTours);

  }

  private void initializeListeners() {

    // watch the MapView's initialized property to finish initialization
    mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        CitiesManager.getInstance().setBean(null);
        // mapView.setXYZParam(xyzParams);
        String bingMapsApiKey = "jRBBC3c6oCO3ebBAITlD~SCTs0-piL0zBf3eqp61CiA~All99rEJA33I8lQpEffyBcbCUZRDs1aZrem6CfTA5m5svwt_JU07CF-fSd1dyXsf";
        mapView.setBingMapsApiKey(bingMapsApiKey);
        mapView.setMapType(MapType.BINGMAPS_AERIAL);
        // mapView.setMapType(MapType.XYZ);
        pauseScreen(1.5);
        MapBindingsManager.setCurrentMapView(mapView);
        System.out.println("Map was loaded bro");
      }
    });

    mapTypeTB.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != oldValue) {
        if (newValue) {
          mapView.setMapType(MapType.BINGMAPS_AERIAL);
        } else {
          mapView.setMapType(MapType.BINGMAPS_ROAD);
        }
      }
    });

    siteLockTB.selectedProperty()
        .addListener((observable, oldValue, newValue) -> toggleSiteComponents(newValue));

    tourLockTB.selectedProperty()
        .addListener((observable, oldValue, newValue) -> toggleTourComponents(newValue));

    sitesOfMapListener = new ListChangeListener() {
      @Override
      public void onChanged(Change c) {
        while (c.next()) {
          Platform.runLater(() -> {
            for (Object site : c.getRemoved()) {
              (mapsSites.get(site)).setVisible(false);
            }
          });
          Platform.runLater(() -> {
            for (Object site : c.getAddedSubList()) {
              (mapsSites.get(site)).setVisible(true);
            }
          });
        }
      }
    };

    // Add a listener to the SitesManager's always up to date sites list

    // Add a listener to the SitesManager's always up to date sites list
    // SitesManager.getInstance().sitesOfSelectedTourProperty()
    //     .addListener((observable, oldValue, newValue) -> {
    //       renderSites();
    //     });
    // SitesManager.getInstance().sitesOfSelectedTourProperty()
    //     .addListener((observable, oldValue, newValue) -> {
    //       renderSites();
    //     });

    SitesManager.getInstance().sitesOfSelectedMapProperty().addListener(sitesOfMapListener);
    // SitesManager.getInstance().sitesOfSelectedTourProperty().addListener(sitesOfMapListener);

    // SitesManager.getInstance().beanProperty().bind(selectedSite);
  }

  private void toggleTourComponents(Boolean lock) {
    leftLV.setDisable(lock);
    rightLV.setDisable(lock);
    moveLeftBTN.setDisable(lock);
    moveRightBTN.setDisable(lock);
  }

  private void toggleSiteComponents(Boolean lock) {
    siteNameTF.setDisable(lock);
    siteCategoryCB.setDisable(lock);
    siteDescriptionTA.setDisable(lock);
    siteAccessibilityTB.setDisable(lock);
    siteVisitDurationTF.setDisable(lock);
  }

  private void pauseScreen(double seconds) {
    PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
    pause.setOnFinished((event) -> {
      maskerP.setVisible(false);
    });
    maskerP.setVisible(true);
    pause.play();
  }

  private void animateClickMarker(Coordinate oldPosition, Coordinate newPosition) {
    // animate the marker to the new position
    final Transition transition = new Transition() {
      private final Double oldPositionLongitude = oldPosition.getLongitude();
      private final Double oldPositionLatitude = oldPosition.getLatitude();
      private final double deltaLatitude = newPosition.getLatitude() - oldPositionLatitude;
      private final double deltaLongitude = newPosition.getLongitude() - oldPositionLongitude;

      {
        setCycleDuration(Duration.seconds(1.0));
        setOnFinished(evt -> newSiteMarker.setPosition(newPosition));
      }

      @Override
      protected void interpolate(double v) {
        final double latitude = oldPosition.getLatitude() + v * deltaLatitude;
        final double longitude = oldPosition.getLongitude() + v * deltaLongitude;
        newSiteMarker.setPosition(new Coordinate(latitude, longitude));
      }
    };
    transition.play();
  }

}