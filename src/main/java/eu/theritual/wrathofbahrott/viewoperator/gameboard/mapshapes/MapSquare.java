package eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes;

import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;

public final class MapSquare {
    private final int leftTopCorner;
    private final int top;
    private final int rightTopCorner;
    private final int left;
    private final int fill;
    private final int right;
    private final int leftBottomCorner;
    private final int bottom;
    private final int rightBottomCorner;

    public MapSquare(int leftTopCorner, int top, int rightTopCorner, int left, int fill, int right, int leftBottomCorner, int bottom, int rightBottomCorner) {
        this.leftTopCorner = leftTopCorner;
        this.top = top;
        this.rightTopCorner = rightTopCorner;
        this.left = left;
        this.fill = fill;
        this.right = right;
        this.leftBottomCorner = leftBottomCorner;
        this.bottom = bottom;
        this.rightBottomCorner = rightBottomCorner;
    }

    public void draw(GameBoardMap gameBoardMap, int startX, int startY, int endX, int endY) {

    }
}
