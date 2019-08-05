package eu.theritual.wrathofbahrott.viewoperator;

import javafx.event.Event;
import org.springframework.stereotype.Controller;
import eu.theritual.wrathofbahrott.dataoperator.GameModule;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Controller
public class SplashScreenController {
    private ViewOperator viewOperator;

    @FXML
    private MediaView splashVideo;

    @FXML
    public void skip(Event e) {
        splashVideo.getMediaPlayer().stop();
        System.out.println("Intro Skipped: " + e.toString());
    }

    void playVideo() {
        try {
            URL movieURL = ViewOperator.class.getResource("video/intro.mp4").toURI().toURL();
            Media intro = new Media(movieURL.toString());
            MediaPlayer player = new MediaPlayer(intro);
            splashVideo.setMediaPlayer(player);
            DoubleProperty width = splashVideo.fitWidthProperty();
            DoubleProperty height = splashVideo.fitHeightProperty();
            width.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "height"));
            splashVideo.setPreserveRatio(true);
            player.setOnEndOfMedia(() -> viewOperator.run(GameModule.MAIN_MENU));
            player.setOnStopped(() -> viewOperator.run(GameModule.MAIN_MENU));
            player.play();
        } catch (MalformedURLException e) {
            ViewOperator.error("MalformedURLException", "Can't load intro video", e.toString());
        } catch (URISyntaxException e) {
            ViewOperator.error("URISyntaxException", "Can't load intro video (URI PROBLEM)", e.toString());
        }
    }

    void setViewOperator(ViewOperator viewOperator) {
        this.viewOperator= viewOperator;
    }
}
