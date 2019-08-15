package eu.theritual.wrathofbahrott.media;

import eu.theritual.wrathofbahrott.viewoperator.Actions;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.Tile;
import javafx.scene.image.Image;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TileOperator {

    private static Image get16xTile(String name) {
        String tile;
        try {
            tile = TileOperator.class.getResource("gfx/tiles/" + name + ".png").toURI().toURL().toString();
        } catch (URISyntaxException e) {
            tile = "";
            Actions.error("URISyntaxException", "Can't load image (URI PROBLEM)", e.toString());
        } catch (MalformedURLException e) {
            tile = "";
            Actions.error("MalformedURLException", "Can't load image (URL PROBLEM)", e.toString());
        }
        return new Image(tile, 16, 16, true, false);
    }

    public static Image translateTileId(int id) {
        Tile tile = getTileList().stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        if (tile == null) {
            try {
                tile = getTileList().get(0);
            } catch (NullPointerException e) {
                System.out.println("ERROR " + e.toString());
            }
        }
        return get16xTile(Objects.requireNonNull(tile).getName());
    }

    public static List<Tile> getTileList() {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(new Tile(1, "grass1tlc", true));
        tileList.add(new Tile(2, "grass1t", true));
        tileList.add(new Tile(3, "grass1trc", true));
        tileList.add(new Tile(4, "grass1l", true));
        tileList.add(new Tile(5, "grass1fa", false));
        tileList.add(new Tile(6, "grass1r", true));
        tileList.add(new Tile(7, "grass1blc", true));
        tileList.add(new Tile(8, "grass1b", true));
        tileList.add(new Tile(9, "grass1brc", true));
        tileList.add(new Tile(10, "grass1fb", false));
        tileList.add(new Tile(11, "grass1fc", false));
        tileList.add(new Tile(12, "grass1tlhc", true));
        tileList.add(new Tile(13, "grass1trhc", true));
        tileList.add(new Tile(14, "grass1blhc", true));
        tileList.add(new Tile(15, "grass1brhc", true));
        tileList.add(new Tile(16, "grass2tlc", true));
        tileList.add(new Tile(17, "grass2t", true));
        tileList.add(new Tile(18, "grass2trc", true));
        tileList.add(new Tile(19, "grass2l", true));
        tileList.add(new Tile(20, "grass2fa", false));
        tileList.add(new Tile(21, "grass2r", true));
        tileList.add(new Tile(22, "grass2blc", true));
        tileList.add(new Tile(23, "grass2b", true));
        tileList.add(new Tile(24, "grass2brc", true));
        tileList.add(new Tile(25, "grass2fb", false));
        tileList.add(new Tile(26, "grass2fc", false));
        tileList.add(new Tile(27, "grass2tlhc", true));
        tileList.add(new Tile(28, "grass2trhc", true));
        tileList.add(new Tile(29, "grass2blhc", true));
        tileList.add(new Tile(30, "grass2brhc", true));
        tileList.add(new Tile(31, "grass3tlc", true));
        tileList.add(new Tile(32, "grass3t", true));
        tileList.add(new Tile(33, "grass3trc", true));
        tileList.add(new Tile(34, "grass3l", true));
        tileList.add(new Tile(35, "grass3fa", false));
        tileList.add(new Tile(36, "grass3r", true));
        tileList.add(new Tile(37, "grass3blc", true));
        tileList.add(new Tile(38, "grass3b", true));
        tileList.add(new Tile(39, "grass3brc", true));
        tileList.add(new Tile(40, "grass3fb", false));
        tileList.add(new Tile(41, "grass3fc", false));
        tileList.add(new Tile(42, "grass3tlhc", true));
        tileList.add(new Tile(43, "grass3trhc", true));
        tileList.add(new Tile(44, "grass3blhc", true));
        tileList.add(new Tile(45, "grass3brhc", true));
        tileList.add(new Tile(46, "sand1a", false));
        tileList.add(new Tile(47, "sand1b", false));
        tileList.add(new Tile(48, "sand1c", false));
        tileList.add(new Tile(49, "sand2a", false));
        tileList.add(new Tile(50, "sand2b", false));
        tileList.add(new Tile(51, "sand2c", false));
        tileList.add(new Tile(52, "sand3a", false));
        tileList.add(new Tile(53, "sand3b", false));
        return tileList;
    }
}
