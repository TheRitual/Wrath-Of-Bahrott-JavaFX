package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameOptions;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameModule;
import eu.theritual.wrathofbahrott.utils.SaveLoadUtils;
import eu.theritual.wrathofbahrott.viewoperator.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

public class ViewOperator {
    private final Stage mainStage;
    private double screenWidth, screenHeight;
    private double screenRatio;
    private DataOperator dataOperator;
    private Group root;
    private Controller controller;
    private GameOptions gameOptions;
    private ElementGenerator generator;

    public ViewOperator(Stage mainStage) {
        this.mainStage = mainStage;
        root = new Group();
        Scene mainScene = new Scene(root, Color.BLACK);
        this.mainStage.setScene(mainScene);
    }

    public void initiate() {
        mainStage.setTitle("Wrath Of Bahrott");
        mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        reloadViewOptions();
        mainStage.widthProperty().addListener((a, b, c) -> reDraw(controller));
    }

    private void reDraw(Controller controller) {
        if (mainStage.getWidth() < 1000) {
            mainStage.setWidth(1000);
        }
        checkResolution();
        controller.stop();
        controller.draw();
        SaveLoadUtils.saveOptions(gameOptions, "config");
    }

    public void setDataOperator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
        this.gameOptions = dataOperator.getGOptions();
        this.generator = new ElementGenerator(dataOperator);
    }

    private void checkResolution() {
        Rectangle2D screenBounds;
        if (gameOptions.isFullScreen()) {
            screenBounds = Screen.getPrimary().getBounds();
        } else if (gameOptions.isMaximized()) {
            screenBounds = Screen.getPrimary().getVisualBounds();
        } else {
            screenBounds = new Rectangle2D(0, 0, mainStage.getWidth(), mainStage.getHeight());
        }
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight();
        gameOptions.setScreenWidth(screenWidth);
        gameOptions.setScreenHeight(screenHeight);
        mainStage.setX(screenBounds.getMinX());
        mainStage.setY(screenBounds.getMinY());
        screenBounds = Screen.getPrimary().getBounds();
        screenRatio = screenBounds.getWidth() / screenBounds.getHeight();
    }

    private void reloadViewOptions() {
        mainStage.setWidth(gameOptions.getScreenWidth());
        checkResolution();
        mainStage.minHeightProperty().bind(mainStage.widthProperty().divide(screenRatio));
        mainStage.maxHeightProperty().bind(mainStage.widthProperty().divide(screenRatio));
        mainStage.setMaximized(gameOptions.isMaximized());
        mainStage.setResizable(!gameOptions.isMaximized());
        mainStage.setFullScreen(gameOptions.isFullScreen());
        mainStage.setAlwaysOnTop(gameOptions.isFullScreen());
        mainStage.show();
    }

    private ViewData loadView(String fileName) {
        FXMLLoader loader = new FXMLLoader();
        root = new Group();
        try {
            loader = new FXMLLoader();
            URL url = ViewOperator.class.getResource("layouts/" + fileName);

            loader.setLocation(url);
            loader.setControllerFactory(dataOperator.getSpringContext()::getBean);
            root.getChildren().add(loader.load());
        } catch (Exception e) {
            SpecialActions.error("EXCEPTION HAPPEND!", "Can't load fxml file ", e.toString() + "\n" + e.getCause());
        }
        return new ViewData(loader, root);
    }

    public void run(GameModule module) {
        controller = getController(module);
        controller.draw();
        controller.start();
    }

    private Controller getController(GameModule gameModule) {
        ViewData view = loadView(gameModule.toString() + ".fxml");
        mainStage.getScene().setRoot(view.getRoot());
        Controller controller = view.getLoader().getController();
        controller.setDataOperator(dataOperator);
        checkResolution();
        return controller;
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
    }

    public Group getRoot() {
        return root;
    }

    public Stage getStage() {
        return mainStage;
    }

    public ElementGenerator getGenerator() {
        return generator;
    }
}
