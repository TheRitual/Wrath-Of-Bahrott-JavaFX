package eu.theritual.wrathofbahrott.viewoperator.gameboard;

import eu.theritual.wrathofbahrott.media.TileOperator;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;

public final class GameBoardMap {
    private int[][][] gameMap;
    private boolean[][] refreshMap;
    private int size;
    private TileOperator tileOperator;
    private final GraphicsContext gc;
    private boolean continueRedrawing;

    public GameBoardMap(int size, GraphicsContext graphicsContext) {
        this.tileOperator = new TileOperator();
        gameMap = new int[size][size][5];
        refreshMap = new boolean[size][size];
        for (boolean[] row : refreshMap) {
            Arrays.fill(row, true);
        }
        this.size = size;
        this.gc = graphicsContext;
        makeEmpty();
    }

    private byte[][] getFirstVisibleTileMap() {
        byte[][] firstVisibleTileMap = new byte[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                for (int l = 4; l >= 0; l--) {
                    int tileId = gameMap[x][y][l];
                    if (!tileOperator.getTile(tileId).isTransparent()) {
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
                if (refreshMap[x][y]) {
                    for (int l = visibleTileMap[x][y]; l < 5; l++) {
                        int tileId = gameMap[x][y][l];
                        if (tileId != 0) {
                            switch (l) {
                                case 3:
                                    gc.setGlobalAlpha(0.2);
                                    break;
                                case 4:
                                    gc.setGlobalAlpha(0.5);
                                    break;
                            }
                            gc.drawImage(tileOperator.translateTileId(tileId, x, y), x * 16, y * 16);
                            gc.setGlobalAlpha(1);
                            refreshMap[x][y] = false;
                            //System.out.println("Printed " + x + " / " + y + " / " + l + " | with: " + TileOperator.getTile(tileId).getName());
                        }
                    }
                }
            }
        }
    }

    private void setRefreshTile(int x, int y, boolean shouldRefresh) {
        if (x < size && y < size && x >= 0 && y >= 0) {
            refreshMap[x][y] = shouldRefresh;
        }
    }

    public void setRefreshField(int startX, int startY, int endX, int endY, boolean shouldRefresh) {
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                setRefreshTile(x, y, shouldRefresh);
            }
        }
    }

    public void setRefreshField(Point2D startTile, Point2D endTile, boolean shouldRefresh) {
        setRefreshField((int) startTile.getX(), (int) startTile.getY(), (int) endTile.getX(), (int) endTile.getY(), shouldRefresh);
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

    public void setGameField(int x, int y, int layer, int tileId) {
        try {
            this.gameMap[x][y][layer] = tileId;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR:" + e.toString());
        }
    }

    public int getTileWidth() {
        return (int) Math.floor((getSize() - 4) / 8.0);
    }

    public int getTileHeight() {
        return getTileWidth();
    }

    public int getGap() {
        return getSize() - 8 * getTileWidth() - 4;
    }

    public int getMargin() {
        return 2 + getTileWidth() / 2 + (getGap() / 2);
    }

    public int getBathrottXPosition() {
        return getMargin() + 3 * getTileWidth() - 1;
    }

    public int getBahrottSize() {
        return (getTileWidth() + 2) * 16;
    }

    public int getSize() {
        return size;
    }

    public Point2D getTileByPixel(double x, double y) {
        return new Point2D(Math.floor((x) / 16) - 1, Math.floor((y) / 16) - 1);

    }

    public double getMovementSpeed() {
        return (size * 16d) / 20d;
    }

    public double getSpriteWidth() {
        return (getTileWidth() - 1) * 16;
    }

    public double getSpriteHeight() {
        return (getTileHeight() - 1) * 16;
    }

    public TileOperator getTileOperator() {
        return tileOperator;
    }

    public boolean isContinueRedrawing() {
        return continueRedrawing;
    }

    public void setContinueRedrawing(boolean continueRedrawing) {
        this.continueRedrawing = continueRedrawing;
    }
}