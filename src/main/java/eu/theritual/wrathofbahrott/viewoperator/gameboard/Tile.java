package eu.theritual.wrathofbahrott.viewoperator.gameboard;

import eu.theritual.wrathofbahrott.media.TileOperator;
import javafx.scene.image.Image;

public final class Tile {
    private final String fileName;
    private final boolean isTransparent;

    Tile(final String fileName, final boolean isTransparent) {
        this.fileName = fileName;
        this.isTransparent = isTransparent;
    }

    Image getImage() {
        return TileOperator.get16xTile(fileName);
    }
}
