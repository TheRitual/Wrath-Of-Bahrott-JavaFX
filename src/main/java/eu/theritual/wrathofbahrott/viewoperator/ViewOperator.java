package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameModule;
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

    public ViewOperator(Stage mainStage) {
        this.mainStage = mainStage;
        root = new Group();
        Scene mainScene = new Scene(root, Color.BLACK);
        this.mainStage.setScene(mainScene);
    }

    public void initiate() {
        mainStage.setTitle("Wrath Of Bahrott");
        reloadViewOptions();
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
        mainStage.minHeightProperty().bind(mainStage.widthProperty().divide(screenRatio));
        mainStage.maxHeightProperty().bind(mainStage.widthProperty().divide(screenRatio));
    }

    private void reloadViewOptions() {
        checkResolution();
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

    private void runSplashScreen() {
        dataOperator.setModule(GameModule.SPLASH_SCREEN);
        ViewData view = loadView("SplashScreen.fxml");
        mainStage.getScene().setRoot(view.getRoot());
        SplashScreenController controller = view.getLoader().getController();
        controller.setDataOperator(dataOperator);
        checkResolution();
        controller.playVideo();
    }

    private void runMainMenu() {
        dataOperator.setModule(GameModule.MAIN_MENU);
        ViewData view = loadView("MainMenu.fxml");
        mainStage.getScene().setRoot(view.getRoot());
        MainMenuController controller = view.getLoader().getController();
        controller.setDataOperator(dataOperator);
        checkResolution();
        controller.startMenu();
    }

    public void run(GameModule module) {
        switch (module) {
            case SPLASH_SCREEN:
                runSplashScreen();
                break;
            case MAIN_MENU:
                runMainMenu();
                break;
        }
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
