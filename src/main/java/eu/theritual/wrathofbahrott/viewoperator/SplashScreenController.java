package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameModule;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.springframework.stereotype.Controller;


@Controller
public class SplashScreenController {
    private DataOperator dataOperator;

    @FXML
    private MediaView splashVideo;

    @FXML
    public void skip(Event e) {
        splashVideo.getMediaPlayer().stop();
        System.out.println("Intro Skipped: " + e.toString());
        splashVideo.setMediaPlayer(null);
        dataOperator.getViewOperator().run(GameModule.MAIN_MENU);
    }

    void playVideo() {
            dataOperator.getMediaOperator().setVideo("intro");
            MediaPlayer player = dataOperator.getMediaOperator().getVideoMediaPlayer();
            splashVideo.setMediaPlayer(player);
            DoubleProperty width = splashVideo.fitWidthProperty();
            DoubleProperty height = splashVideo.fitHeightProperty();
            width.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "height"));
            splashVideo.setPreserveRatio(true);
            player.setOnEndOfMedia(() -> dataOperator.getViewOperator().run(GameModule.MAIN_MENU));
            player.play();
    }

    void setDataOperator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
    }
}
