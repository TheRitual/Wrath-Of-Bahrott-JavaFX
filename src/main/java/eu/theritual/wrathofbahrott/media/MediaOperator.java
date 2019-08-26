package eu.theritual.wrathofbahrott.media;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameCss;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameFont;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.GamePicture;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameSoundVideo;
import eu.theritual.wrathofbahrott.viewoperator.SpecialActions;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MediaOperator {
    private Map<GameSoundVideo, String> sounds;
    private Map<GameSoundVideo, String> videos;
    private Map<GamePicture, String> images;
    private Map<GameFont, String> fonts;
    private Map<GameCss, String> css;
    private MediaPlayer musicMediaPlayer;

    public MediaOperator() {
        sounds = new HashMap<>();
        videos = new HashMap<>();
        images = new HashMap<>();
        fonts = new HashMap<>();
        css = new HashMap<>();
        //FONTS
        fonts.put(GameFont.FIPPS, "fonts/Fipps-Regular.otf");
        fonts.put(GameFont.O4B, "fonts/04B30.TTF");
        fonts.put(GameFont.ADDLG, "fonts/ADDLG.TTF");
        fonts.put(GameFont.VERMIN, "fonts/VerminVibes1989.ttf");
        //CSS
        css.put(GameCss.MENU, "css/standard.css");
        //SOUNDS
        sounds.put(GameSoundVideo.MENU_MUSIC, "sounds/menu.mp3");
        //VIDEOS
        videos.put(GameSoundVideo.INTRO, "videos/intro.mp4");
        //IMAGES
        images.put(GamePicture.MENU_BACKGROUND, "gfx/menuBackground.png");
        images.put(GamePicture.GAME_BACKGROUND, "gfx/cityBackground.png");
        images.put(GamePicture.WOB_LOGO, "gfx/WrathOfBohrottLogo2.png");
        images.put(GamePicture.EXIT_ON, "gfx/exitOn.png");
        images.put(GamePicture.EXIT_OUT, "gfx/exitOut.png");
        images.put(GamePicture.START_ON, "gfx/startOn.png");
        images.put(GamePicture.START_OUT, "gfx/startOut.png");
        images.put(GamePicture.CREDITS_ON, "gfx/creditsOn.png");
        images.put(GamePicture.CREDITS_OUT, "gfx/creditsOut.png");
        images.put(GamePicture.OPTIONS_ON, "gfx/settingsOn.png");
        images.put(GamePicture.OPTIONS_OUT, "gfx/settingsOut.png");
        images.put(GamePicture.SUBMENU_BACKGROUND, "gfx/optBg.jpg");
        images.put(GamePicture.EYE_ICON, "gfx/eyeSlider.jpg");
        images.put(GamePicture.BIG_TILE_TEXTURE, "gfx/3d.png");
        images.put(GamePicture.PRE_MENU_BG, "gfx/back.jpg");
        images.put(GamePicture.GAME_BG_PANEL, "gfx/bgGamePanel.png");
        images.put(GamePicture.WITCH_FRONT, "gfx/sprites/characters/witch_bs.png");
        images.put(GamePicture.NUN_FRONT, "gfx/sprites/characters/nun_bs.png");
        images.put(GamePicture.WORKER_FRONT, "gfx/sprites/characters/worker_bs.png");
        images.put(GamePicture.COURIER_FRONT, "gfx/sprites/characters/courier_bs.png");
        images.put(GamePicture.SHIELD, "gfx/shield.png");
        images.put(GamePicture.ITEM_SLOT, "gfx/itemslot.png");
    }

    private URL getSoundUrl(GameSoundVideo soundName) {
        String name = this.sounds.get(soundName);
        try {
            return MediaOperator.class.getResource(name).toURI().toURL();
        }  catch (MalformedURLException e) {
            SpecialActions.error("MalformedURLException", "Can't load sound file (URL PROBLEM)", e.toString());
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            SpecialActions.error("URISyntaxException", "Can't load sound file (URI PROBLEM)", e.toString());
            return null;
        }
    }

    public void setMusic(GameSoundVideo musicName) {
        URL sndUrl = getSoundUrl(musicName);
        if(sndUrl == null) {
            SpecialActions.error("Can't recognize file", "Sound " + musicName + " can't be recognized!", "There's no such file on the media map.");
            throw new NullPointerException("sndURL can't be null");
        }
        Media sound = new Media(sndUrl.toString());
        this.musicMediaPlayer = new MediaPlayer(sound);
        this.musicMediaPlayer.setCycleCount(Timeline.INDEFINITE);
    }

    public MediaPlayer getMusicMediaPlayer() {
        return this.musicMediaPlayer;
    }

    public URL getVideoUrl(GameSoundVideo videoName) {
        String name = this.videos.get(videoName);
        try {
            return MediaOperator.class.getResource(name).toURI().toURL();
        }  catch (MalformedURLException e) {
            SpecialActions.error("MalformedURLException", "Can't load video file (URL PROBLEM)", e.toString());
            return null;
        }catch (URISyntaxException e) {
            SpecialActions.error("URISyntaxException", "Can't load video file (URI PROBLEM)", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public Image getImage(GamePicture name, double width, double height) {
        String imgFile = images.get(name);
        try {
            imgFile = MediaOperator.class.getResource(imgFile).toURI().toURL().toString();
        } catch (URISyntaxException e) {
            SpecialActions.error("URISyntaxException", "Can't load image (URI PROBLEM)", e.toString());
        } catch (MalformedURLException e) {
            SpecialActions.error("MalformedURLException", "Can't load image (URL PROBLEM)", e.toString());
        }
        return new Image(imgFile, width, height, true, false);
    }

    public Background getBackgroundImg(GamePicture imgName, double screenWidth, double screenHeight) {
        return new Background(new BackgroundImage(getImage(imgName, screenWidth,screenHeight), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)));
    }

    public ImageView getImageView(GamePicture name, double width, double height) {
        return new ImageView(getImage(name, width, height));
    }

    public Font getFont(GameFont gameFont, double size) {
        String fontName = fonts.get(gameFont);
        try {
            fontName = MediaOperator.class.getResource(fontName).toURI().toURL().toString();
        } catch (URISyntaxException e) {
            SpecialActions.error("URISyntaxException", "Can't load font (URI PROBLEM)", e.toString());
        } catch (MalformedURLException e) {
            SpecialActions.error("MalformedURLException", "Can't load dont (URL PROBLEM)", e.toString());
        }
        return Font.loadFont(fontName, size);
    }

    public String getCss(GameCss cssName) {
        String cssFileName = css.get(cssName);
        try {
            cssFileName = MediaOperator.class.getResource(cssFileName).toURI().toURL().toString();
        } catch (URISyntaxException e) {
            SpecialActions.error("URISyntaxException", "Can't load CSS file (URI PROBLEM)", e.toString());
        } catch (MalformedURLException e) {
            SpecialActions.error("MalformedURLException", "Can't load CSS file (URL PROBLEM)", e.toString());
        }
        return cssFileName;
    }

    static Image getImage(String name, double width, double height) {
        String image;
        try {
            image = TileOperator.class.getResource("gfx/" + name + ".png").toURI().toURL().toString();
        } catch (URISyntaxException e) {
            image = "";
            SpecialActions.error("URISyntaxException", "Can't load image (URI PROBLEM)", e.toString());
        } catch (MalformedURLException e) {
            image = "";
            SpecialActions.error("MalformedURLException", "Can't load image (URL PROBLEM)", e.toString());
        }
        return new Image(image, width, height, true, false);
    }
}
