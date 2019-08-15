package eu.theritual.wrathofbahrott.viewoperator.gameboard;

import eu.theritual.wrathofbahrott.media.TileOperator;
import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;
import java.util.stream.IntStream;

public final class GameBoardMap {
    private int[][] gameMap;
    private int size;
    private final GraphicsContext gc;

    public GameBoardMap(int size, GraphicsContext graphicsContext) {
        gameMap = new int[size][size];
        this.size = size;
        this.gc = graphicsContext;
        IntStream stream = Arrays.stream(gameMap).flatMapToInt(Arrays::stream);
        stream.forEach(e -> e = 0);
    }

    public void draw() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                gc.drawImage(TileOperator.get16xTile("sand1a"), x * 16, y * 16);
            }
        }
    }

}
