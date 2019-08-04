package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ViewOperator {
    private final FXMLLoader fxmlLoader;
    private Parent root;
    private final Stage mainStage;
    private double screenWidth, screenHeight;
    private DataOperator dataOperator;

    public ViewOperator(Stage mainStage, DataOperator dataOperator) {
        this.fxmlLoader = new FXMLLoader();
        this.mainStage = mainStage;
        this.dataOperator = dataOperator;
        this.mainStage.setMinWidth(800);
        this.mainStage.setMinHeight(600);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        this.mainStage.setX(primaryScreenBounds.getMinX());
        this.mainStage.setY(primaryScreenBounds.getMinY());
        this.screenWidth= primaryScreenBounds.getWidth();
        this.screenHeight= primaryScreenBounds.getHeight();
    }

    public void setUpBeforeRun(boolean isMaximalized, boolean isFullScreen) {
        mainStage.setTitle("Wrath Of Bahrott");
        mainStage.setWidth(screenWidth);
        mainStage.setHeight(screenHeight);
        mainStage.setMaximized(isMaximalized);
        mainStage.setResizable(false);
        mainStage.setFullScreen(isFullScreen);
        mainStage.setAlwaysOnTop(true);
    }

    public void run() {
        mainStage.show();
    }
}
