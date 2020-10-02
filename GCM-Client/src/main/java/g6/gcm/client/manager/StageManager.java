package g6.gcm.client.manager;


import g6.gcm.client.boundary.FXMLFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

/**
 * StageManager is responsible for setting up the stage and switching\showing scenes. <br>
 * Lazy instantiation.
 */
public class StageManager {

    private static StageManager manager = null;
    public final Stage primaryStage;
    private final StageStyle DEFAULT_STAGE_STYLE = StageStyle.TRANSPARENT;
    private AnchorPane mainViewAP = new AnchorPane();


    /**
     * Private constructor to keep users from instantiating StageManagers. Only StageManager can
     * instantiate itself.
     *
     * @param primaryStage is the applications stage.
     */
    private StageManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * The only way to initialize the StageManager. Throws ExceptionInInitializerError.
     *
     * @param primaryStage is the applications stage.
     */
    public static void initializeManager(Stage primaryStage) throws ExceptionInInitializerError {
        if (manager != null) {
            throw new ExceptionInInitializerError("StageManager must not be initialized more than once.");
        }
        manager = new StageManager(primaryStage);
    }

    /**
     * The only way to instantiate StageManager.
     *
     * @return StageManager instance.
     */
    public static StageManager getManager() {
        try {
            Objects.requireNonNull(manager, "StageManager must be initialized first.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return manager;
    }

    public AnchorPane getMainViewAP() {
        return mainViewAP;
    }

    public void setMainViewAP(AnchorPane mainViewAP) {
        this.mainViewAP = mainViewAP;
    }

    /**
     * First 3 methods are built to unify scene's staging process while maintaining some flexibility.
     * <br>
     * This is the only way to stage scenes.
     *
     * @param fxmlView is the scene to be displayed.
     */
    public void stageScene(final FXMLFactory fxmlView) {
        stageScene(fxmlView, DEFAULT_STAGE_STYLE, null);
    }

    public void stageScene(final FXMLFactory fxmlView, StageStyle stageStyle) {
        stageScene(fxmlView, stageStyle, null);
    }

    public void stageScene(final FXMLFactory fxmlView, Paint sceneFill) {
        stageScene(fxmlView, DEFAULT_STAGE_STYLE, sceneFill);
    }

    public void stageScene(final FXMLFactory fxmlView, StageStyle stageStyle, Paint sceneFill) {
        Parent fxmlViewRootNode = getFXMLViewRootNode(fxmlView.getFXMLView());
        Scene preparedScene = prepareStage(fxmlViewRootNode, sceneFill);
        showScene(preparedScene, stageStyle);
    }

    /**
     * This is the only way to switch scenes, use when you don't want to change the whole stage.
     *
     * @param fxmlView   is the scene to be displayed.
     * @param parentNode is the pane to be loaded in.
     */
    public void switchScene(final FXMLFactory fxmlView, Parent parentNode) throws ClassCastException {
        Parent fxmlViewRootNode = getFXMLViewRootNode(fxmlView.getFXMLView());
        try {
            ((Pane) parentNode).getChildren().clear();
            ((Pane) parentNode).getChildren().setAll(fxmlViewRootNode);
        } catch (ClassCastException cce) {
            System.out.println(cce.getMessage());
        }
    }


    /**
     * The name says it all. :)
     *
     * @param fxmlViewPath is the FXML's URL.
     * @return the topmost node in nodes' hierarchy.
     */
    public Parent getFXMLViewRootNode(String fxmlViewPath) {
        Parent rootNode = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlViewPath));
        try {
            rootNode = fxmlLoader.load();
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null.");
        } catch (Exception exception) {
            System.out.println("Something went wrong trying to load fxml view!\n" + exception);
        }
        return rootNode;
    }


    /**
     * Shows the scene.
     */
    private void showScene(final Scene preparedScene, final StageStyle stageStyle) {

        primaryStage.setScene(preparedScene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        if (!primaryStage.isShowing()) {
            // TODO: add an option to instantiate a new stage?
            primaryStage.initStyle(stageStyle);
        }

        try {
            primaryStage.show();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    /**
     * @param fxmlViewRootNode is the (scene to be shown)'s topmost node.
     * @param sceneFill        is the scene's fill.
     * @return primaryStage's prepared scene.
     */
    private Scene prepareStage(Parent fxmlViewRootNode, Paint sceneFill) {
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(fxmlViewRootNode);
        }

        scene.setRoot(fxmlViewRootNode);
        scene.setFill(sceneFill);

        return scene;
    }


}
