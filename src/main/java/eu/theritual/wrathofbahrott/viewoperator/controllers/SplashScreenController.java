package eu.theritual.wrathofbahrott.viewoperator.controllers;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameModule;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameSoundVideo;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.springframework.stereotype.Controller;


@Controller
public class SplashScreenController extends eu.theritual.wrathofbahrott.viewoperator.controllers.Controller {
    @FXML
    private GridPane splashPane;
    private boolean skip;

    private void goToMainMenu() {
        if (skip) {
            System.out.println("Going to Main Menu");
            skip = false;
            view.run(GameModule.MAIN_MENU);
        } else {
            System.out.println("Skipping ignored");
        }
    }

    @Override
    public void start() {
        skip = true;
        MediaView splashVideo = new MediaView();
        MediaPlayer player = new MediaPlayer(new Media(mediaOperator.getVideoUrl(GameSoundVideo.INTRO).toString()));
        splashVideo.setMediaPlayer(player);
        splashPane.add(splashVideo, 1, 1);
        DoubleProperty width = splashVideo.fitWidthProperty();
        DoubleProperty height = splashVideo.fitHeightProperty();
        width.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "height"));
        splashVideo.setPreserveRatio(true);
        splashVideo.setOnMouseClicked(e -> player.stop());
        view.getStage().getScene().setOnKeyPressed(e -> player.stop());
        player.setOnEndOfMedia(player::stop);
        player.setOnStopped(this::goToMainMenu);
        player.play();
    }
}