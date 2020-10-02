package g6.gcm.client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class ThreeDStyle extends Application {

    private static final String TEXT =
            "O for a Muse of fire, that would ascend\n" +
                    "The brightest heaven of invention,\n" +
                    "A kingdom for a stage, princes to act\n" +
                    "And monarchs to behold the swelling scene!\n" +
                    "Then should the warlike Harry, like himself,\n" +
                    "Assume the port of Mars; and at his heels,\n" +
                    "Leash'd in like hounds, should famine, sword and fire\n" +
                    "Crouch for employment";
    private static final String IMAGE_LOC =
            "http://ia.media-imdb.com/images/M/MV5BMTI1ODg1Mjk5NF5BMl5BanBnXkFtZTcwNzQyNjUxMQ@@._V1_SX214_AL_.jpg";
    private static final String DEFAULT_STYLE =
            "-fx-background-color: mediumseagreen;\n" +
                    "-fx-background-radius: 10px;\n" +
                    "-fx-padding: 10px;\n" +
                    "-fx-graphic-text-gap: 10px;\n" +
                    "-fx-font-family: 'American Typewriter';\n" +
                    "-fx-font-size: 18px;";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // create label.
        final Label label = new Label(TEXT);
        label.setGraphic(new ImageView(new Image(IMAGE_LOC)));
        label.setStyle(DEFAULT_STYLE);

        // create 3D rotation transform on label.
        Rotate rotate = new Rotate(-30, Rotate.Y_AXIS);
        label.getTransforms().setAll(
                rotate
        );

        // layout scene.
        VBox root = new VBox(10,
                createControls(label, rotate),
                new Group(label)
        );
        root.setStyle("-fx-background-color: null; -fx-padding: 10px;");

        // create a 3D scene with perspective capability.
        Scene scene = new Scene(root, 525, 400, true, SceneAntialiasing.BALANCED);
        scene.setCamera(new PerspectiveCamera());
        scene.setFill(Color.MIDNIGHTBLUE);
        stage.setScene(scene);

        stage.show();
    }

    private Node createControls(Label label, Rotate rotate) {
        TextArea style = new TextArea(label.getStyle());

        Button apply = new Button("Apply Style");
        apply.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        apply.setDefaultButton(true);
        apply.setOnAction(event -> label.setStyle(style.getText()));

        Slider rotateSlider = new Slider(0, 60, rotate.getAngle() * -1);
        rotate.angleProperty().bind(rotateSlider.valueProperty().multiply(-1));

        VBox controlPanel = new VBox(10,
                new HBox(10, style, apply),
                new HBox(new Label("Rotate"), rotateSlider)
        );
        controlPanel.setPadding(new Insets(10));
        controlPanel.setStyle("-fx-background-color: antiquewhite;");

        return controlPanel;
    }
}