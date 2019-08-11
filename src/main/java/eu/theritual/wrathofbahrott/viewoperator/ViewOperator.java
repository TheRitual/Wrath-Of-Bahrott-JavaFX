package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameModule;
import eu.theritual.wrathofbahrott.utils.SaveLoadUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    public ViewOperator(Stage mainStage) {
        this.mainStage = mainStage;
        root = new Group();
        Scene mainScene = new Scene(root, Color.BLACK);
        this.mainStage.setScene(mainScene);
    }

    public void initiate() {
        mainStage.setTitle("Wrath Of Bahrott");
        reloadViewOptions();
        mainStage.widthProperty().addListener((a, b, c) -> reDraw(controller));
    }

    private void reDraw(Controller controller) {
        checkResolution();
        controller.draw();
        SaveLoadUtils.saveOptions(dataOperator.getGOptions(), "config");
    }

    public void setDataOperator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
    }

    private void checkResolution() {
        Rectangle2D screenBounds;
        if (dataOperator.getGOptions().isFullScreen()) {
            screenBounds = Screen.getPrimary().getBounds();
        } else if (dataOperator.getGOptions().isMaximized()) {
            screenBounds = Screen.getPrimary().getVisualBounds();
        } else {
            screenBounds = new Rectangle2D(0, 0, mainStage.getWidth(), mainStage.getHeight());
        }
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight();
        dataOperator.getGOptions().setScreenWidth(screenWidth);
        dataOperator.getGOptions().setScreenHeight(screenHeight);
        mainStage.setX(screenBounds.getMinX());
        mainStage.setY(screenBounds.getMinY());
        screenBounds = Screen.getPrimary().getBounds();
        screenRatio = screenBounds.getWidth() / screenBounds.getHeight();
    }

    private void reloadViewOptions() {
        mainStage.setWidth(dataOperator.getGOptions().getScreenWidth());
        checkResolution();
        mainStage.minHeightProperty().bind(mainStage.widthProperty().divide(screenRatio));
        mainStage.maxHeightProperty().bind(mainStage.widthProperty().divide(screenRatio));
        mainStage.setMaximized(dataOperator.getGOptions().isMaximized());
        mainStage.setResizable(!dataOperator.getGOptions().isMaximized());
        mainStage.setFullScreen(dataOperator.getGOptions().isFullScreen());
        mainStage.setAlwaysOnTop(dataOperator.getGOptions().isFullScreen());
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
            error("EXCEPTION HAPPEND!", "Can't load fxml file ", e.toString() + "\n" + e.getCause());
        }
        return new ViewData(loader, root);
    }

    public void run(GameModule module) {
        controller = getController(module);
        controller.draw();
        controller.start();
    }

    private Controller getController(GameModule gameModule) {
        ViewData view = loadView(gameModule + ".fxml");
        mainStage.getScene().setRoot(view.getRoot());
        Controller controller = view.getLoader().getController();
        controller.setDataOperator(dataOperator);
        checkResolution();
        return controller;
    }

    public static void error(String title, String info, String msg) {
        System.out.println(title + " -> " + info + "\n\t" + msg);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(info);
        alert.setContentText(msg);
        if (!alert.isShowing()) {
            alert.show();
        }
    }

    double getScreenWidth() {
        return screenWidth;
    }

    double getScreenHeight() {
        return screenHeight;
    }

    Group getRoot() {
        return root;
    }

    Stage getStage() {
        return mainStage;
    }
}
