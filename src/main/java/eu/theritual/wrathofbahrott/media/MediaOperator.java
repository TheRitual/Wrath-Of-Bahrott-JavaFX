package eu.theritual.wrathofbahrott.media;

import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MediaOperator {
    private Map<String, String> sounds;

    public MediaOperator() {
        sounds = new HashMap<>();
        sounds.put("menuMusic", "sounds/menu.mp3");
    }

    private URL getSoundUrl(String name) {
        name = this.sounds.get(name);
        try {
            return new File(MediaOperator.class.getResource(name).getFile()).toURI().toURL();
        }  catch (MalformedURLException e) {
            ViewOperator.error("MalformedURLException", "Can't load sound file (URL PROBLEM)", e.toString());
            return null;
        }
    }

    public MediaPlayer getMediaPlayerWithMusic(String musicName, int timeLine) {
        URL sndUrl = getSoundUrl(musicName);
        if(sndUrl == null) {
            ViewOperator.error("Can't recognize file","Sound " + musicName + " can't be recognized!", "There's no such file on the media map.");
            return null;
        }
        Media sound = new Media(sndUrl.toString());
        MediaPlayer mp = new MediaPlayer(sound);
        mp.setCycleCount(timeLine);
        return mp;
    }

    private Map getSoundsMap(){
        return sounds;
    }
}
