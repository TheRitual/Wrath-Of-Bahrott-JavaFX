package eu.theritual.wrathofbahrott.soundoperator.soundutils;

import eu.theritual.wrathofbahrott.soundoperator.SoundOperator;
import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundUtils {
    private static Map getSoundsMap(){
        Map<String, String> sounds = new HashMap<>();
        sounds.put("menuMusic", "sounds/menu.mp3");
        return sounds;
    }

    public static URL getSoundUrl(String name) throws MalformedURLException {
        name = getSoundsMap().get(name).toString();
        try {
            return new File(SoundOperator.class.getResource(name).getFile()).toURI().toURL();
        }  catch (MalformedURLException e) {
            ViewOperator.error("MalformedURLException", "Can't load sound file (URL PROBLEM)", e.toString());
            return new URL(name);
        }
    }

    public static MediaPlayer getMediaPlayer(URL musicFile) {
        Media sound = new Media(musicFile.toString());
        MediaPlayer mp = new MediaPlayer(sound);
        mp.setCycleCount(Timeline.INDEFINITE);
        return mp;
    }
}
