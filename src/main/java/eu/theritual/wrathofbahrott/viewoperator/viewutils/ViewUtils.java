package eu.theritual.wrathofbahrott.viewoperator.viewutils;

import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ViewUtils {
    public static Background fullWindowBG(String imgName , double screenWidth, double screenHeight) {
        return new Background(new BackgroundImage(getImage(imgName, screenWidth,screenHeight), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)));
    }

    private static Map getImagesMap(){
        Map<String, String> images = new HashMap<>();
        images.put("menuBackground", "gfx/menuBackground.png");
        images.put("wobLogo", "gfx/WrathOfBohrottLogo.png");
        images.put("exitOn", "gfx/exitOn.png");
        images.put("exitOut", "gfx/exitOut.png");
        images.put("startOn", "gfx/startOn.png");
        images.put("startOut", "gfx/startOut.png");
        return images;
    }

    public static ImageView getImageView(String name) {
        return new ImageView(getImage(name));
    }

    public static ImageView getImageView(String name, double width, double height) {
        return new ImageView(getImage(name, width, height));
    }

    private static Image getImage(String name) {
        String imgFile = getImagesMap().get(name).toString();
        try {
            imgFile = ViewOperator.class.getResource(imgFile).toURI().toURL().toString();
        } catch (URISyntaxException e) {
            ViewOperator.error("URISyntaxException", "Can't load image (URI PROBLEM)", e.toString());
        } catch (MalformedURLException e) {
            ViewOperator.error("MalformedURLException", "Can't load image (URL PROBLEM)", e.toString());
        }
        return new Image(imgFile);
    }

    public static Image getImage(String name, double width, double height) {
        String imgFile = getImagesMap().get(name).toString();
        try {
            imgFile = ViewOperator.class.getResource(imgFile).toURI().toURL().toString();
        } catch (URISyntaxException e) {
            ViewOperator.error("URISyntaxException", "Can't load image (URI PROBLEM)", e.toString());
        } catch (MalformedURLException e) {
            ViewOperator.error("MalformedURLException", "Can't load image (URL PROBLEM)", e.toString());
        }
        return new Image(imgFile, width, height, true, false);
    }
}
