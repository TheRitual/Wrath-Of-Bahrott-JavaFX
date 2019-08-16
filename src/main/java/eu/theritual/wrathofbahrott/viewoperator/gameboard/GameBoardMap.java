package eu.theritual.wrathofbahrott.viewoperator.gameboard;

import eu.theritual.wrathofbahrott.media.TileOperator;
import javafx.scene.canvas.GraphicsContext;

public final class GameBoardMap {
    private int[][][] gameMap;
    private int size;
    private final GraphicsContext gc;

    public GameBoardMap(int size, GraphicsContext graphicsContext) {
        gameMap = new int[size][size][5];
        this.size = size;
        this.gc = graphicsContext;
        makeEmpty();
    }

    public byte[][] getFirstVisibleTileMap() {
        byte[][] firstVisibleTileMap = new byte[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                for (int l = 4; l >= 0; l--) {
                    int tileId = gameMap[x][y][l];
                    if (!TileOperator.getTile(tileId).isTransparent()) {
                        firstVisibleTileMap[x][y] = (byte) l;
                        break;
                    }
                }
            }
        }
        return firstVisibleTileMap;
    }

    public void draw() {
        byte[][] visibleTileMap = getFirstVisibleTileMap();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                for (int l = visibleTileMap[x][y]; l < 5; l++) {
                    int tileId = gameMap[x][y][l];
                    if (tileId != 0) {
                        Tile tile = TileOperator.getTile(tileId);
                        //System.out.println("x(" + x + ")" +  "y(" + y + ")" +  "l(" + l + ")" + "[" + tile.getName() + "]" + tile.getSize() + "<" + x * 16 + "," +  y * 16 +">");
                        gc.drawImage(TileOperator.translateTileId(tileId, x, y), x * 16, y * 16);
                    }
                }
            }
        }
    }

    private void makeEmpty() {
        for (int l = 0; l < 5; l++) {
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    gameMap[x][y][l] = 0;
                }
            }
        }
    }

    public int getGameField(int x, int y, int layer) {
        return gameMap[x][y][layer];
    }

    public void setGameField(int x, int y, int layer, int tileId) {
        try {
            this.gameMap[x][y][layer] = tileId;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR:" + e.toString());
        }
    }

    public int getSize() {
        return size;
    }
}