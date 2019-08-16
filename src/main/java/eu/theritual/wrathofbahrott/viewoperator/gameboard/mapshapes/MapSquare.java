package eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes;

import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class MapSquare implements Drawable {
    private final Random gen;
    private final int topLeftCorner;
    private final int top;
    private final int topRightCorner;
    private final int left;
    private final int right;
    private final int bottomLeftCorner;
    private final int bottom;
    private final int bottomRightCorner;
    private List<Integer> fillArray;

    MapSquare(int topLeftCorner, int top, int topRightCorner, int left, int fill, int right, int bottomLeftCorner, int bottom, int bottomRightCorner) {
        this.fillArray = new ArrayList<>();
        this.gen = new Random();
        this.topLeftCorner = topLeftCorner;
        this.top = top;
        this.topRightCorner = topRightCorner;
        this.left = left;
        this.fillArray.add(fill);
        this.right = right;
        this.bottomLeftCorner = bottomLeftCorner;
        this.bottom = bottom;
        this.bottomRightCorner = bottomRightCorner;
    }

    void addFill(int id) {
        this.fillArray.add(id);
    }

    MapSquare(int oneID) {
        this(oneID, oneID, oneID, oneID, oneID, oneID, oneID, oneID, oneID);
    }

    public void draw(GameBoardMap board, int startX, int startY, int endX, int endY, int layer) {
        if (startX < 0) {
            startX = 0;
        }
        if (startY < 0) {
            startY = 0;
        }
        if (layer < 0) {
            layer = 0;
        }
        if (layer > 5) {
            layer = 5;
        }
        if (endX >= board.getSize()) {
            endX = board.getSize() - 1;
        }
        if (endY >= board.getSize()) {
            endY = board.getSize() - 1;
        }
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                int tile;
                if (y == startY && x == startX) {
                    tile = topLeftCorner;
                } else if (y == startY && x == endX) {
                    tile = topRightCorner;
                } else if (y == endY && x == startX) {
                    tile = bottomLeftCorner;
                } else if (y == endY && x == endX) {
                    tile = bottomRightCorner;
                } else if (y == startY) {
                    tile = top;
                } else if (y == endY) {
                    tile = bottom;
                } else if (x == startX) {
                    tile = left;
                } else if (x == endX) {
                    tile = right;
                } else {
                    tile = getFill();
                }
                board.setGameField(x, y, layer, tile);
            }
        }
    }

    public void drawAllRandomFill(GameBoardMap board, int startX, int startY, int endX, int endY, int layer) {
        if (startX < 0) {
            startX = 0;
        }
        if (startY < 0) {
            startY = 0;
        }
        if (layer < 0) {
            layer = 0;
        }
        if (layer > 5) {
            layer = 5;
        }
        if (endX >= board.getSize()) {
            endX = board.getSize() - 1;
        }
        if (endY >= board.getSize()) {
            endY = board.getSize() - 1;
        }
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                board.setGameField(x, y, layer, getFill());
            }
        }
    }
    
    private int getFill() {
        int randomTile = gen.nextInt(fillArray.size());
        return fillArray.get(randomTile);
    }
}
