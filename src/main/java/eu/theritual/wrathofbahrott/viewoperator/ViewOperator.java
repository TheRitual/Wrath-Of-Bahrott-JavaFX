package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameModule;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

public class ViewOperator {
    private final Stage mainStage;
    private double screenWidth, screenHeight;
    private DataOperator dataOperator;
    private Group root;

    public ViewOperator(Stage mainStage, DataOperator dataOperator) {
        this.mainStage = mainStage;
        root = new Group();
        Scene mainScene = new Scene(root);
        this.mainStage.setScene(mainScene);
        this.dataOperator = dataOperator;
        this.mainStage.setMinWidth(800);
        this.mainStage.setMinHeight(600);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        this.mainStage.setX(primaryScreenBounds.getMinX());
        this.mainStage.setY(primaryScreenBounds.getMinY());
        this.screenWidth = primaryScreenBounds.getWidth();
        this.screenHeight = primaryScreenBounds.getHeight();
    }

    public void setUpBeforeRun(boolean isMaximalized, boolean isFullScreen) {
        mainStage.setTitle("Wrath Of Bahrott");
        mainStage.setWidth(screenWidth);
        mainStage.setHeight(screenHeight);
        mainStage.setMaximized(isMaximalized);
        mainStage.setResizable(false);
        mainStage.setFullScreen(isFullScreen);
        mainStage.setAlwaysOnTop(true);
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
            root.getChildren().add(errorLabel(e.toString()));
        }
        return new ViewData(loader, root);
    }

    private Label errorLabel(String msg) {
        return new Label(msg);
    }

    private void runSplashScreen() {
        dataOperator.setModule(GameModule.SPLASH_SCREEN);
        ViewData view = loadView("SplashScreen.fxml");
        mainStage.getScene().setRoot(view.getRoot());
        SplashScreenController controller = view.getLoader().getController();
        controller.setDataOperator(dataOperator);
        controller.playVideo();
    }

    private void runMainMenu() {
        dataOperator.setModule(GameModule.MAIN_MENU);
        ViewData view = loadView("MainMenu.fxml");
        mainStage.getScene().setRoot(view.getRoot());
        MainMenuController controller = view.getLoader().getController();
        controller.setDataOperator(dataOperator);
        controller.startMenu();
    }

    public void run(GameModule module) {
        mainStage.setFullScreen(true);
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

    public Group getRoot() {
        return root;
    }
}
