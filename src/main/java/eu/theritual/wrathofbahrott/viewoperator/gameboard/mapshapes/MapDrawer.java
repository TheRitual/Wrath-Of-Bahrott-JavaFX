package eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.MapElement;
import eu.theritual.wrathofbahrott.media.TileOperator;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.Tile;

import java.util.HashMap;
import java.util.Map;

public class MapDrawer {
    private GameBoardMap gbm;
    private Map<MapElement, Drawable> shapeMap;
    private Map<MapElement, Tile> singularTileMap;
    private TileOperator tileOperator;

    public MapDrawer(GameBoardMap gameBoardMap) {
        gbm = gameBoardMap;
        shapeMap = new HashMap<>();
        singularTileMap = new HashMap<>();
        tileOperator = gbm.getTileOperator();
        setUpTiles();
    }

    private void setUpTiles() {
        singularTileMap.put(MapElement.STONE_FLOOR, tileOperator.getTile(54));
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
        MapSquare grass3hole = new MapSquare(42, 38, 43, 36, 0, 34, 44, 32, 45);
        MapSquare sand1 = new MapSquare(46);
        sand1.addFill(47);
        sand1.addFill(48);
        MapSquare sand2 = new MapSquare(49);
        sand2.addFill(50);
        sand2.addFill(51);
        MapSquare sand3 = new MapSquare(52);
        sand3.addFill(53);
        sand3.addFill(54);
        MapSquare stoneFloor = new MapSquare(54);
        MapSquare scottFloor = new MapSquare(55);
        MapSquare egyptFloor = new MapSquare(56);
        egyptFloor.addFill(57);
        egyptFloor.addFill(58);
        egyptFloor.addFill(59);
        egyptFloor.addFill(60);
        MapSquare fireStone = new MapSquare(61, 62, 64, 65, 67, 71, 73, 74, 76);
        fireStone.addFill(68);
        fireStone.addFill(69);
        fireStone.addFill(70);
        fireStone.addTop(63);
        fireStone.addBottom(75);
        fireStone.addLeft(66);
        fireStone.addRight(72);
        MapSquare texture = new MapSquare(157);

        MapSquare tileField = new MapSquare(77);
        for (int f = 78; f <= 92; f++) {
            tileField.addFill(f);
        }

        MapSquare redTileField = new MapSquare(93);
        for (int f = 94; f <= 108; f++) {
            redTileField.addFill(f);
        }

        MapSquare violetTileField = new MapSquare(109);
        for (int f = 110; f <= 124; f++) {
            violetTileField.addFill(f);
        }

        MapSquare greenTileField = new MapSquare(125);
        for (int f = 126; f <= 140; f++) {
            greenTileField.addFill(f);
        }

        MapSquare blueTileField = new MapSquare(141);
        for (int f = 142; f <= 156; f++) {
            blueTileField.addFill(f);
        }

        shapeMap.put(MapElement.GRASS1_SQUARE, grass1square);
        shapeMap.put(MapElement.GRASS1_HOLE, grass1hole);
        shapeMap.put(MapElement.GRASS2_SQUARE, grass2square);
        shapeMap.put(MapElement.GRASS2_HOLE, grass2hole);
        shapeMap.put(MapElement.GRASS3_SQUARE, grass3square);
        shapeMap.put(MapElement.GRASS3_HOLE, grass3hole);
        shapeMap.put(MapElement.SAND1, sand1);
        shapeMap.put(MapElement.SAND2, sand2);
        shapeMap.put(MapElement.SAND3, sand3);
        shapeMap.put(MapElement.STONE_FLOOR, stoneFloor);
        shapeMap.put(MapElement.TEXTURE, texture);
        shapeMap.put(MapElement.SCOTT_FLOOR, scottFloor);
        shapeMap.put(MapElement.EGYPT_FLOOR, egyptFloor);
        shapeMap.put(MapElement.FIRE_STONE, fireStone);
        shapeMap.put(MapElement.TILE_FIELD_YELLOW, tileField);
        shapeMap.put(MapElement.TILE_FIELD_RED, redTileField);
        shapeMap.put(MapElement.TILE_FIELD_BLUE, blueTileField);
        shapeMap.put(MapElement.TILE_FIELD_VIOLET, violetTileField);
        shapeMap.put(MapElement.TILE_FIELD_GREEN, greenTileField);
    }

    public void drawShape(MapElement shape, int startX, int startY, int endX, int endY, int layer) {
        Drawable figure = shapeMap.get(shape);
        switch (shape) {
            case GRASS1_SQUARE:
            case GRASS1_HOLE:
            case GRASS2_SQUARE:
            case GRASS2_HOLE:
            case GRASS3_SQUARE:
            case GRASS3_HOLE:
            case STONE_FLOOR:
            case TEXTURE:
            case SCOTT_FLOOR:
            case FIRE_STONE:
                figure.draw(gbm, startX, startY, endX, endY, layer);
                break;
            case SAND1:
            case SAND2:
            case SAND3:
            case EGYPT_FLOOR:
            case TILE_FIELD_YELLOW:
            case TILE_FIELD_RED:
            case TILE_FIELD_GREEN:
            case TILE_FIELD_VIOLET:
            case TILE_FIELD_BLUE:
                figure.drawAllRandomFill(gbm, startX, startY, endX, endY, layer);
                break;
        }
    }

    public void drawTile(MapElement tile, int x, int y, int layer) {
        gbm.setGameField(x, y, layer, singularTileMap.get(tile).getId());
    }

    public void drawRectangle(MapElement shape, int startX, int startY, int width, int height, int layer) {
        drawShape(shape, startX, startY, startX + width - 1, startY + height - 1, layer);
    }
}
