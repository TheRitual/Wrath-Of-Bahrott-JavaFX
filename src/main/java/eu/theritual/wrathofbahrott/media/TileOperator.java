package eu.theritual.wrathofbahrott.media;

import eu.theritual.wrathofbahrott.viewoperator.Actions;
import javafx.scene.image.Image;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class TileOperator {

    public TileOperator() {
    }

    public static Image get16xTile(String name) {
        String tile = null;
        try {
            tile = TileOperator.class.getResource("gfx/tiles/" + name + ".png").toURI().toURL().toString();
        } catch (URISyntaxException e) {
            Actions.error("URISyntaxException", "Can't load image (URI PROBLEM)", e.toString());
        } catch (MalformedURLException e) {
            Actions.error("MalformedURLException", "Can't load image (URL PROBLEM)", e.toString());
        }
        return new Image(tile, 16, 16, true, false);
    }
}
