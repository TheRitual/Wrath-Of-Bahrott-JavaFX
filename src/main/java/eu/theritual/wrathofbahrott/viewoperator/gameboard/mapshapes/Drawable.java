package eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes;

import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;

public interface Drawable {
    void draw(GameBoardMap board, int startX, int startY, int endX, int endY, int layer);

    void drawAllRandomFill(GameBoardMap board, int startX, int startY, int endX, int endY, int layer);
}
