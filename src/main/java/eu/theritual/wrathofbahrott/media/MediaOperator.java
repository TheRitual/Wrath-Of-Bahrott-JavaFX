package eu.theritual.wrathofbahrott.media;

import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MediaOperator {
    private Map<String, String> sounds;
    private Map<String, String> videos;
    private Map<String, String> images;
    private MediaPlayer musicMediaPlayer;
    private MediaPlayer videoMediaPlayer;

    public MediaOperator() {
        sounds = new HashMap<>();
        videos = new HashMap<>();
        images = new HashMap<>();
        //SOUNDS
        sounds.put("menuMusic", "sounds/menu.mp3");
        //VIDEOS
        videos.put("intro","videos/intro.mp4");
        //IMAGES
        images.put("menuBackground", "gfx/menuBackground.png");
        images.put("wobLogo", "gfx/WrathOfBohrottLogo.png");
        images.put("exitOn", "gfx/exitOn.png");
        images.put("exitOut", "gfx/exitOut.png");
        images.put("startOn", "gfx/startOn.png");
        images.put("startOut", "gfx/startOut.png");
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

    public void setMusic(String musicName) {
        URL sndUrl = getSoundUrl(musicName);
        if(sndUrl == null) {
            ViewOperator.error("Can't recognize file","Sound " + musicName + " can't be recognized!", "There's no such file on the media map.");
            throw new NullPointerException("sndURL can't be null");
        }
        Media sound = new Media(sndUrl.toString());
        this.musicMediaPlayer = new MediaPlayer(sound);
    }

    public MediaPlayer getMusicMediaPlayer() {
        return this.musicMediaPlayer;
    }

    private URL getVideoUrl(String name) {
        name = this.videos.get(name);
        try {
            return new File(MediaOperator.class.getResource(name).getFile()).toURI().toURL();
        }  catch (MalformedURLException e) {
            ViewOperator.error("MalformedURLException", "Can't load sound file (URL PROBLEM)", e.toString());
            return null;
        }
    }

    public void setVideo(String videoName) {
        URL vidUrl = getVideoUrl(videoName);
        if(vidUrl == null) {
            ViewOperator.error("Can't recognize file","Video " + videoName + " can't be recognized!", "There's no such file on the media map.");
            throw new NullPointerException("vidUrl can't be null");
        }
        Media video = new Media(vidUrl.toString());
        this.videoMediaPlayer = new MediaPlayer(video);
    }

    public MediaPlayer getVideoMediaPlayer() {
        return this.videoMediaPlayer;
    }

    private Image getImage(String name) {
        String imgFile = images.get(name);
        try {
            imgFile = MediaOperator.class.getResource(imgFile).toURI().toURL().toString();
        } catch (URISyntaxException e) {
            ViewOperator.error("URISyntaxException", "Can't load image (URI PROBLEM)", e.toString());
        } catch (MalformedURLException e) {
            ViewOperator.error("MalformedURLException", "Can't load image (URL PROBLEM)", e.toString());
        }
        return new Image(imgFile);
    }

    public Image getImage(String name, double width, double height) {
        String imgFile = images.get(name);
        try {
            imgFile = MediaOperator.class.getResource(imgFile).toURI().toURL().toString();
        } catch (URISyntaxException e) {
            ViewOperator.error("URISyntaxException", "Can't load image (URI PROBLEM)", e.toString());
        } catch (MalformedURLException e) {
            ViewOperator.error("MalformedURLException", "Can't load image (URL PROBLEM)", e.toString());
        }
        return new Image(imgFile, width, height, true, false);
    }

    public Background fullWindowBG(String imgName , double screenWidth, double screenHeight) {
        return new Background(new BackgroundImage(getImage(imgName, screenWidth,screenHeight), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)));
    }

    public ImageView getImageView(String name) {
        return new ImageView(getImage(name));
    }

    public ImageView getImageView(String name, double width, double height) {
        return new ImageView(getImage(name, width, height));
    }
}
