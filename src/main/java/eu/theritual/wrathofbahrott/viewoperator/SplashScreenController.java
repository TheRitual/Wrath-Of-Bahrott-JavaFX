package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameModule;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.springframework.stereotype.Controller;


@Controller
public class SplashScreenController implements eu.theritual.wrathofbahrott.viewoperator.Controller {
    private DataOperator dataOperator;

    @FXML
    private MediaView splashVideo;


    private void skip() {
        splashVideo.getMediaPlayer().stop();
        splashVideo.setMediaPlayer(null);
        dataOperator.getView().run(GameModule.MAIN_MENU);
    }

    private void skip(MouseEvent mouseEvent) {
        System.out.println("Intro Skipped with Mouse Click");
        splashVideo.getMediaPlayer().dispose();
        skip();
    }

    public void draw() {
    }

    public void start() {
        dataOperator.getMediaOp().setVideo("intro");
        MediaPlayer player = dataOperator.getMediaOp().getVideoMediaPlayer();
        splashVideo.setMediaPlayer(player);
        DoubleProperty width = splashVideo.fitWidthProperty();
        DoubleProperty height = splashVideo.fitHeightProperty();
        width.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(splashVideo.sceneProperty(), "height"));
        splashVideo.setPreserveRatio(true);
        player.setOnPaused(this::skip);
        player.setOnEndOfMedia(this::skip);
        splashVideo.setOnMouseClicked(this::skip);
        player.play();
    }

    public void setDataOperator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
    }
}
