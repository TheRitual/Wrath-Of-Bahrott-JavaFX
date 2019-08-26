package eu.theritual.wrathofbahrott.media;

import eu.theritual.wrathofbahrott.viewoperator.gameboard.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TileOperator {
    private List<Tile> tileList;

    public TileOperator() {
        tileList = getTileList();
    }

    public WritableImage translateTileId(int id, int x, int y) {
        Tile tile = tileList.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        if (tile == null) {
            try {
                tile = tileList.get(0);
            } catch (NullPointerException e) {
                System.out.println("ERROR " + e.toString());
            }
        }
        Tile til = getTile(id);
        Image img = MediaOperator.getImage("tiles/" + Objects.requireNonNull(tile).getName(), til.getSize(), til.getSize());
        int startx = x * 16 % til.getSize();
        int starty = y * 16 % til.getSize();
        return new WritableImage(img.getPixelReader(), startx, starty, 16, 16);
    }

    public Tile getTile(int id) {
        return tileList.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    private List<Tile> getTileList() {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(new Tile(0, "empty", true));
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
        tileList.add(new Tile(54, "stonefloor", false, 32));
        tileList.add(new Tile(55, "scottfloor", false, 32));
        tileList.add(new Tile(56, "egypt1", true));
        tileList.add(new Tile(57, "egypt2", true));
        tileList.add(new Tile(58, "egypt3", true));
        tileList.add(new Tile(59, "egypt4", true));
        tileList.add(new Tile(60, "egypt5", true));
        tileList.add(new Tile(61, "firestone_tlc", false));
        tileList.add(new Tile(62, "firestone_t0", false));
        tileList.add(new Tile(63, "firestone_t1", false));
        tileList.add(new Tile(64, "firestone_trc", false));
        tileList.add(new Tile(65, "firestone_l0", false));
        tileList.add(new Tile(66, "firestone_l1", false));
        tileList.add(new Tile(67, "firestone_f0", false));
        tileList.add(new Tile(68, "firestone_f1", false));
        tileList.add(new Tile(69, "firestone_f2", false));
        tileList.add(new Tile(70, "firestone_f3", false));
        tileList.add(new Tile(71, "firestone_r0", false));
        tileList.add(new Tile(72, "firestone_r1", false));
        tileList.add(new Tile(73, "firestone_blc", false));
        tileList.add(new Tile(74, "firestone_b0", false));
        tileList.add(new Tile(75, "firestone_b1", false));
        tileList.add(new Tile(76, "firestone_brc", false));
        tileList.add(new Tile(157, "texture", true, 32));

        for (int f = 0; f <= 15; f++) {
            tileList.add(new Tile(77 + f, "fieldtile_" + f, false));
        }

        for (int f = 0; f <= 15; f++) {
            tileList.add(new Tile(93 + f, "red_fieldtile_" + f, false));
        }

        for (int f = 0; f <= 15; f++) {
            tileList.add(new Tile(109 + f, "violet_fieldtile_" + f, false));
        }

        for (int f = 0; f <= 15; f++) {
            tileList.add(new Tile(125 + f, "green_fieldtile_" + f, false));
        }

        for (int f = 0; f <= 15; f++) {
            tileList.add(new Tile(141 + f, "blue_fieldtile_" + f, false));
        }

        return tileList;
    }
}
