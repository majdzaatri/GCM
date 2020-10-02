package g6.gcm.client.boundary.fix;

import com.jfoenix.controls.JFXButton;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapLabel;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.Marker.Provided;
import g6.gcm.core.entity.SiteDTO;
import java.util.List;
import javafx.fxml.FXML;

public class MapViewController {

  Coordinate BraudeMain = new Coordinate(32.909784282405, 35.281992584314);
  private Coordinate israel = new Coordinate(31.840232667909362, 35.2056884765625);
  @FXML
  private MapView mapView;

  @FXML
  private JFXButton button;

  private List<SiteDTO> sitesList;

  @FXML
  private void initialize() {

    mapView
        .setCustomMapviewCssURL(getClass().getResource("/css/custom_mapview.css"));
    mapView.initialize();
    mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        afterMapIsInitialized();
      }
    });

    button.setOnMouseClicked(e -> {
      Coordinate Braude = new Coordinate(32.909784282405, 35.281992584314);
      MapLabel braudeLabel = new MapLabel("Zabbatnako :)").setPosition(Braude).setVisible(true);
      Marker braudeMarker = Marker.createProvided(Provided.BLUE).setPosition(Braude)
          .setVisible(true);
      braudeMarker.attachLabel(braudeLabel);
      mapView.addMarker(braudeMarker);
    });


  }

  private void afterMapIsInitialized() {
    mapView.setZoom(12);
    mapView.setCenter(BraudeMain);
//    mapView.addLabel(braudeLabel);
//    mapView.addMarker(braudeMarker);

  }

}
