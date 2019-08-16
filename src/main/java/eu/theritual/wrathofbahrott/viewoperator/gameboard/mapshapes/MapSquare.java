package eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes;

import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class MapSquare implements Drawable {
    private final Random gen;
    private List<Integer> topLeftCorner;
    private List<Integer> top;
    private List<Integer> topRightCorner;
    private List<Integer> left;
    private List<Integer> right;
    private List<Integer> bottomLeftCorner;
    private List<Integer> bottom;
    private List<Integer> bottomRightCorner;
    private List<Integer> fillArray;

    MapSquare(int topLeftCorner, int top, int topRightCorner, int left, int fill, int right, int bottomLeftCorner, int bottom, int bottomRightCorner) {
        this.topLeftCorner = new ArrayList<>();
        this.top = new ArrayList<>();
        this.topRightCorner = new ArrayList<>();
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
        this.bottomLeftCorner = new ArrayList<>();
        this.bottom = new ArrayList<>();
        this.bottomRightCorner = new ArrayList<>();
        this.fillArray = new ArrayList<>();
        this.gen = new Random();
        this.topLeftCorner.add(topLeftCorner);
        this.top.add(top);
        this.topRightCorner.add(topRightCorner);
        this.left.add(left);
        this.fillArray.add(fill);
        this.right.add(right);
        this.bottomLeftCorner.add(bottomLeftCorner);
        this.bottom.add(bottom);
        this.bottomRightCorner.add(bottomRightCorner);
    }

    void addFill(int id) {
        this.fillArray.add(id);
    }

    void addTop(int id) {
        this.top.add(id);
    }

    void addTopRightCorner(int id) {
        this.topRightCorner.add(id);
    }

    void addLeft(int id) {
        this.left.add(id);
    }

    void addRight(int id) {
        this.right.add(id);
    }

    void addBottomLeftCorner(int id) {
        this.bottomLeftCorner.add(id);
    }

    void addBottom(int id) {
        this.bottom.add(id);
    }

    void addBottomRightCorner(int id) {
        this.bottomRightCorner.add(id);
    }

    void addTopLeftCorner(int id) {
        this.topLeftCorner.add(id);
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
                    tile = getRandomTile(topLeftCorner);
                } else if (y == startY && x == endX) {
                    tile = getRandomTile(topRightCorner);
                } else if (y == endY && x == startX) {
                    tile = getRandomTile(bottomLeftCorner);
                } else if (y == endY && x == endX) {
                    tile = getRandomTile(bottomRightCorner);
                } else if (y == startY) {
                    tile = getRandomTile(top);
                } else if (y == endY) {
                    tile = getRandomTile(bottom);
                } else if (x == startX) {
                    tile = getRandomTile(left);
                } else if (x == endX) {
                    tile = getRandomTile(right);
                } else {
                    tile = getRandomTile(fillArray);
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
                board.setGameField(x, y, layer, getRandomTile(fillArray));
            }
        }
    }

    private int getRandomTile(List<Integer> list) {
        int randomTile = gen.nextInt(list.size());
        return list.get(randomTile);
    }
}
