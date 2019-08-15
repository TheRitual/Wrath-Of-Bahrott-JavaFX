package eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes;

import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;

import java.util.HashMap;
import java.util.Map;

public class ShapeDrawer {
    private GameBoardMap gbm;
    private Map<MapShape, Drawable> shapeMap;

    public ShapeDrawer(GameBoardMap gameBoardMap) {
        gbm = gameBoardMap;
        shapeMap = new HashMap<>();
        setUpTiles();
    }

    private void setUpTiles() {
        MapSquare grass1square = new MapSquare(1, 2, 3, 4, 5, 6, 7, 8, 9);
        grass1square.addFill(10);
        grass1square.addFill(11);
        MapSquare grass1hole = new MapSquare(12, 8, 13, 6, 0, 4, 14, 2, 15);
        MapSquare grass2square = new MapSquare(16, 17, 18, 19, 20, 21, 22, 23, 24);
        grass2square.addFill(25);
        grass2square.addFill(26);
        MapSquare grass2hole = new MapSquare(27, 23, 28, 21, 0, 19, 29, 17, 30);
        MapSquare grass3square = new MapSquare(31, 32, 33, 34, 35, 36, 37, 38, 39);
        grass3square.addFill(40);
        grass3square.addFill(41);
        MapSquare grass3hole = new MapSquare(42, 38, 43, 36, 35, 34, 44, 32, 45);
        MapSquare sand1 = new MapSquare(46);
        sand1.addFill(47);
        sand1.addFill(48);
        MapSquare sand2 = new MapSquare(49);
        sand2.addFill(50);
        sand2.addFill(51);
        MapSquare sand3 = new MapSquare(52);
        sand3.addFill(53);
        sand3.addFill(54);

        shapeMap.put(MapShape.GRASS1_SQUARE, grass1square);
        shapeMap.put(MapShape.GRASS1_HOLE, grass1hole);
        shapeMap.put(MapShape.GRASS2_SQUARE, grass2square);
        shapeMap.put(MapShape.GRASS2_HOLE, grass2hole);
        shapeMap.put(MapShape.GRASS3_SQUARE, grass3square);
        shapeMap.put(MapShape.GRASS3_HOLE, grass3hole);
        shapeMap.put(MapShape.SAND1, sand1);
        shapeMap.put(MapShape.SAND2, sand2);
        shapeMap.put(MapShape.SAND3, sand3);
    }

    public void draw(MapShape shape, int startX, int startY, int endX, int endY, int layer) {
        Drawable figure = shapeMap.get(shape);
        switch (shape) {
            case GRASS1_SQUARE:
            case GRASS1_HOLE:
            case GRASS2_SQUARE:
            case GRASS2_HOLE:
            case GRASS3_SQUARE:
            case GRASS3_HOLE:
                figure.draw(gbm, startX, startY, endX, endY, layer);
                break;
            case SAND1:
            case SAND2:
            case SAND3:
                figure.drawAllRandomFill(gbm, startX, startY, endX, endY, layer);
                break;
        }
    }
}
