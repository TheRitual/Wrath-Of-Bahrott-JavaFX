package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameModule;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameSoundVideo;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.springframework.stereotype.Controller;


@Controller
public class SplashScreenController extends eu.theritual.wrathofbahrott.viewoperator.Controller {
    @FXML
    private GridPane splashPane;

    private MediaView splashVideo;

    private void skip(MediaPlayer player) {
        player.stop();
        splashVideo.setMediaPlayer(null);
        view.run(GameModule.MAIN_MENU);
    }

    private void skip(Event event, MediaPlayer player) {
        System.out.println("Intro Skipped: " + event.getEventType());
        splashVideo.getMediaPlayer().pause();
        skip(player);
    }

    @Override
    public void start() {
        splashVideo = new MediaView();
        splashPane.add(splashVideo, 1, 1);
        mediaOperator.setVideo(GameSoundVideo.INTRO);
        MediaPlayer player = mediaOperator.getVideoMediaPlayer();
        splashVideo.setMediaPlayer(player);
        DoubleProperty width = splashVideo.fitWidthProperty();
        DoubleProperty height = splashVideo.fitHeightProperty();
        width.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "height"));
        splashVideo.setPreserveRatio(true);
        player.setOnPaused(() -> skip(player));
        player.setOnEndOfMedia(() -> skip(player));
        splashVideo.setOnMouseClicked(e -> skip(e, player));
        splashVideo.setOnError(e -> skip(e, player));
        view.getStage().getScene().setOnKeyPressed(e -> skip(e, player));
        player.play();
    }
}
