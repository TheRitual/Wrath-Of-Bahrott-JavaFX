package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.GameModule;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;
import java.net.MalformedURLException;

public class SplashScreenController {
    @FXML
    private MediaView splashVideo;

    private ViewOperator viewOperator;

    void setViewOperator(ViewOperator viewOperator) {
        this.viewOperator= viewOperator;
    }

    @FXML
    protected void initialize() {
        playVideo();
    }

    private void playVideo() {
        try {
            System.out.println("Playing...");
            String movieURL = new File(getClass().getResource("video/intro.mp4").getFile()).toURI().toURL().toString();
            Media intro = new Media(movieURL);
            MediaPlayer player = new MediaPlayer(intro);
            splashVideo.setMediaPlayer(player);
            DoubleProperty width = splashVideo.fitWidthProperty();
            DoubleProperty height = splashVideo.fitHeightProperty();
            width.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "height"));
            splashVideo.setPreserveRatio(true);
            player.setOnEndOfMedia(() -> viewOperator.run(GameModule.MAIN_MENU));
            player.play();
        } catch (MalformedURLException e) {
            ViewOperator.error("MalformedURLException", "Can't load intro video", e.toString());
        }
    }
}
