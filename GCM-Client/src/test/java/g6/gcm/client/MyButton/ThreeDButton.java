//package g6.gcm.client.MyButton;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXButton.ButtonType;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.beans.property.DoubleProperty;
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.geometry.Point2D;
//import javafx.geometry.Point3D;
//import javafx.geometry.Pos;
//import javafx.scene.PerspectiveCamera;
//import javafx.scene.Scene;
//import javafx.scene.SceneAntialiasing;
//import javafx.scene.control.Label;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.transform.Rotate;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.Stage;
//
//public class ThreeDButton extends Application {
//
//  private static final String TEXT = "O for a Muse of fire, that would ascend\n" +
//      "The brightest heaven of invention,\n" +
//      "A kingdom for a stage, princes to act\n" +
//      "And monarchs to behold the swelling scene!\n" +
//      "Then should the warlike Harry, like himself,\n" +
//      "Assume the port of Mars; and at his heels,\n" +
//      "Leash'd in like hounds, should famine, sword and fire\n" +
//      "Crouch for employment";
//
//  private static final String DEFAULT_STYLE =
//      "-fx-background-color: mediumseagreen;\n" +
//          "-fx-background-radius: 10px;\n" +
//          "-fx-padding: 10px;\n" +
//          "-fx-graphic-textToBeRendered-gap: 10px;\n" +
//          "-fx-font-family: 'American Typewriter';\n" +
//          "-fx-font-size: 18px;";
//  private static final String DEFAULT_BUTTON_STYLE = "-fx-background-color: rgba(250, 250, 250, 0.5);";
//
//  private DoubleProperty xPivot = new SimpleDoubleProperty(0);
//  private DoubleProperty yPivot = new SimpleDoubleProperty(0);
//
//  public static void main(String[] args) {
//    launch(args);
//  }
//
//  @Override
//  public void start(Stage stage) throws Exception {
//    // create label.
//    final Label label = new Label(TEXT);
//    label.setStyle(DEFAULT_STYLE);
//    final JFXButton button = new JFXButton("button");
//    button.setButtonType(ButtonType.FLAT);
//    button.setStyle(DEFAULT_BUTTON_STYLE);
//    button.setMinSize(200, 60);
//    VBox root = new VBox(10, label, button);
//    root.setAlignment(Pos.CENTER);
//
//
//    Rotate yAxisRotation = new Rotate(-30,);
////    yAxisRotation.setAxis();
////    yAxisRotation.setPivotY(button.getLayoutY());
//    yAxisRotation.angleProperty().bind(xPivot.divide(-10));
//
//
//    button.setOnMouseMoved(e -> {
//      JFXButton thisButton = (JFXButton) e.getSource();
//      Platform.runLater(() -> {
//        xPivot.setValue((e.getX() - thisButton.getLayoutX()) - (thisButton.getWidth() / 2));
//        yPivot.setValue((e.getY() - thisButton.getLayoutY()) - (thisButton.getHeight() / 2));
////                xPivot.setValue((e.getX() - thisButton.getLayoutX()));
//      });
//    });
//
//    button.getTransforms().setAll(yAxisRotation);
//
//    root.setStyle("-fx-background-color: null; -fx-padding: 10px;");
//
//    Scene scene = new Scene(root, 525, 400, true, SceneAntialiasing.BALANCED);
//    scene.setCamera(new PerspectiveCamera());
//    scene.setFill(Color.MIDNIGHTBLUE);
//    stage.setScene(scene);
//
//    stage.show();
//  }
//
//}
