package g6.gcm.client.boundary.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapType;
import com.sothawo.mapjfx.MapView;
import g6.gcm.client.entity.MapRendering;
import g6.gcm.client.entity.RenderingsEngineer;
import g6.gcm.client.entity.RenderingsEngineer.EngineeredCellFactories;
import g6.gcm.client.entity.SiteRendering;
import g6.gcm.client.manager.*;
import g6.gcm.core.entity.CoordinatizedDTO;
import g6.gcm.core.interfaces.Renderable;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.CheckTreeView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("Duplicates")
public class CustomerCityExplorerUIController {

  /**
   * Initialize initial coordinates and default zoom.
   */
  private final Coordinate ISRAEL = new Coordinate(31.840232667909362, 35.2056884765625);
  private final int DEFAILT_ZOOM = 9;

  /**
   * Utils.
   */
  private final FileChooser fileChooser = new FileChooser();
  private final CheckBoxTreeItem<Renderable> sitesRootDummy = new CheckBoxTreeItem<>();
  private ConcurrentHashMap<Renderable, MapRendering> availableSites = new ConcurrentHashMap<>();


  /**
   * FXML elements.
   */

  @FXML
  private MapView mapView;

  @FXML
  private JFXComboBox<Renderable> mapsCB;

  @FXML
  private CheckTreeView<Renderable> sitesCTV;

  @FXML
  private CheckListView<Renderable> toursCLV;

  @FXML
  private JFXButton downloadSnapshotBTN;

  /**
   * Initializes basic fxml view and its variables.
   */
  @FXML
  private void initialize() {
    // Create map's bindings
    MapsManager.getInstance().bindMap(mapView);

    // Initialize FileChooser
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
    fileChooser.setTitle("Choose location for map's snapshot...");

    // Initialize sites renderings HashMap
    // availableSites = new ConcurrentHashMap<>();
    // Initialize maps check box
    setUpMapsComboBox();

    // Initialize sites check list view
    setUpSitesCheckTreeView();

    // Initialize tours check list view
    setUpToursCheckListView();

    // Initialize listeners
    initializeListeners();

    // initialize map's view
    mapView.initialize();

    //  TODO: delete this
    downloadSnapshotBTN.setOnMouseClicked(e -> downloadSnapshot());
  }

  private void initializeListeners() {

    // Watch the map's view initialized property to finish initialization
    // initialize is called in initialize()
    mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        mapView.setCenter(new Coordinate(31.494261815532752, 35.1947021484375));
        mapView.setZoom(8);
        String bingMapsApiKey = "jRBBC3c6oCO3ebBAITlD~SCTs0-piL0zBf3eqp61CiA~All99rEJA33I8lQpEffyBcbCUZRDs1aZrem6CfTA5m5svwt_JU07CF-fSd1dyXsf";
        mapView.setBingMapsApiKey(bingMapsApiKey);
        mapView.setMapType(MapType.BINGMAPS_AERIAL);
        // mapView.setMapType(MapType.XYZ);
        MapBindingsManager.setCurrentMapView(mapView);
        System.out.println("Map was loaded bro");
      }
    });
  }

  /**
   * Sets up the maps combo box.
   */
  private void setUpMapsComboBox() {

    // Bind the combobox's list to the always up to date MapsManager list of maps
    mapsCB.itemsProperty().bind(MapsManager.getInstance().beansListProperty());

    // Bind the combobox's selection to the MapsManager map
    mapsCB.valueProperty().bindBidirectional(MapsManager.getInstance().beanProperty());

    // Set the renderings cell factory
    mapsCB.setCellFactory(EngineeredCellFactories::callLV);
    mapsCB.setButtonCell(EngineeredCellFactories.callLV(null));
  }


  /**
   * Sets up the sites' check tree view.
   */
  private void setUpSitesCheckTreeView() {

    // Create a dummy root to hide first level of tree
    sitesCTV.setRoot(sitesRootDummy);

    // Hide root level in order to have same level categories
    sitesCTV.setShowRoot(false);

    // Add a listener to the SitesManager's always up to date sites list
    SitesManager.getInstance().beansListProperty().addListener((observable, oldValue, newValue) -> {
      renderSites();
    });

    // Enable multiple selections for the Site's ListView
    sitesCTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    // Set the renderings cell factory
    sitesCTV.setCellFactory(EngineeredCellFactories::callCTV);
  }

  /**
   * Sets up the tours' check list view.
   */
  private void setUpToursCheckListView() {

    // Bind the list view's items to the always up to date ToursManager list of tours
    toursCLV.itemsProperty().bind(ToursManager.getInstance().beansListProperty());

    // Set the renderings cell factory
    toursCLV.setCellFactory(EngineeredCellFactories::callLV);
  }

  /**
   * creates new rendering
   */
  private void renderSites() {

    // Remove sites' renderings from the map's view
    availableSites
        .forEach((renderable, mapRendering) -> mapView
            .removeMarker(((SiteRendering) mapRendering).getMarker()));

    // Generate new sites' renderings
    availableSites = RenderingsEngineer.createSitesMapRenderings();

    // Clean existing sites from CTV
    sitesRootDummy.getChildren().clear();

    // Create each category and its sites
    for (Renderable renderableCategory : SitesManager.getInstance().getSitesCategoriesList()) {
      CheckBoxTreeItem<Renderable> categoryItem = new CheckBoxTreeItem<>(renderableCategory);
      SitesManager.getInstance().getSitesPerCategory(renderableCategory.render()).
          forEach(site -> {
            CheckBoxTreeItem<Renderable> siteItem = new CheckBoxTreeItem<>(site);
            // Bind the checkbox's checked property to the site's generated rendering visibility
            availableSites.get(site).visibleProperty().bind(siteItem.selectedProperty());
            categoryItem.getChildren().add(siteItem);
          });
      categoryItem.setExpanded(true);
      sitesRootDummy.getChildren().add(categoryItem);
    }

    // Add sites' renderings to the map's view
    availableSites
        .forEach((renderable, mapRendering) -> mapView
            .addMarker(((SiteRendering) mapRendering).getMarker()));

    // Check all check boxes
    sitesCTV.getCheckModel().checkAll();
  }


  private void downloadSnapshot() {

    // Set FileChooser's initial file name
    fileChooser.setInitialFileName(
        CitiesManager.getInstance().getBean().render() + "Map Number " + MapsManager.getInstance()
            .getBean().render());

    // Take snapshot of map's view
    WritableImage image = mapView.snapshot(new SnapshotParameters(), null);

    // Open save as dialog
    File file = fileChooser.showSaveDialog(StageManager.getManager().primaryStage);

    // Save snapshot as ".png" file
    try {
      ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    } catch (IOException e) {
      // TODO: handle exception!!!!!!!!!!!!!!!!!!!!!!
    }
  }

  private void printIt() {
    //TODO delete
    System.out.println(
        "\n*********************************************************************************");

    System.out.println("MapsManager map: " + MapsManager.getInstance().getBean().render());
    System.out.println("MapsManager mapBean: " + MapsManager.getInstance().getSuperBean());
    System.out.println(
        "MapsManager mapBean.getBean(): " + MapsManager.getInstance().getSuperBean().getBean()
            .render());
    System.out.println(
        "MapsManager beanProperty: " + MapsManager.getInstance().beanProperty());
    System.out.println(
        "MapsManager beanProperty.get(): " + MapsManager.getInstance().beanProperty().get()
            .render());
    System.out
        .println("\n----------------------------------------------------------------------");
    System.out.println("\nmapView.getZoom(): " + mapView.getZoom());

    System.out.println("\nmapView.getCenter(): " + mapView.getCenter());
    System.out
        .println(
            "MapsManager map's center: " + ((CoordinatizedDTO) MapsManager.getInstance().getBean())
                .getCoordinates());
    System.out.println(
        "*********************************************************************************\n");
  }
}