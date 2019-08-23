package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameModule;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.MapElement;
import eu.theritual.wrathofbahrott.media.MediaOperator;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes.MapDrawer;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaView;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class GameController implements eu.theritual.wrathofbahrott.viewoperator.Controller {
    private DataOperator dataOperator;
    private double canvasSize;
    private GameBoardMap gbm;
    private ElementGenerator generator;
    private Random gen = new Random();

    @FXML
    private GridPane gamePane;
    @FXML
    private Canvas gameCanvas;
    @FXML
    private MediaView musicPlayer;

    private int getTilesAmount() {
        int size = (int) (canvasSize / 16.0);
        return size - (size % 2);
    }

    public void setDataOperator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
        this.generator = new ElementGenerator(dataOperator);
    }

    public void draw() {
        gamePane.setMinSize(dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight());
        gamePane.setPadding(new Insets(10, 10, 10, 10));
        gamePane.setAlignment(Pos.TOP_CENTER);
        gamePane.setBackground(dataOperator.getMediaOp().getBackgroundImg("gameBackground", dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight()));
        Label exitButton = generator.createLabelButton("Back", generator.getFontSize(15));
        gamePane.add(exitButton, 0, 0);
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> backToMainMenu());
        canvasSize = dataOperator.getView().getScreenWidth() * 0.52;
        gameCanvas.setWidth(canvasSize);
        gameCanvas.setHeight(canvasSize);
        GridPane.setValignment(gameCanvas, VPos.TOP);
        GridPane.setHalignment(gameCanvas, HPos.RIGHT);
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        int boardSize = getTilesAmount();
        this.gbm = new GameBoardMap(boardSize, gc);
        gc.clearRect(0, 0, canvasSize, canvasSize);
        //Thread boardThread = new Thread(() -> {
            drawBoard();
            drawSpecials(gc);
        //});
        //boardThread.start();
    }

    private void drawBoard() {
        MapDrawer drawer = new MapDrawer(gbm);
        System.out.println("GAME_TILE_BOARD:" + gbm.getSize());
        int tileWidth = (int) Math.floor((gbm.getSize() - 4) / 8.0);
        int tileHeight = (int) Math.floor((gbm.getSize() - 4) / 8.0);
        int gap = gbm.getSize() - 8 * tileWidth - 4;
        int margin = 2 + tileWidth / 2 + (gap / 2);
        System.out.println("BOARD_TILE_WIDTH:" + tileWidth);
        System.out.println("BOARD_TILE_HEIGHT:" + tileHeight);
        drawer.drawShape(MapElement.STONE_FLOOR, 0, 0, gbm.getSize() - 1, gbm.getSize() - 1, 0);
        drawer.drawShape(MapElement.SCOTT_FLOOR, 1, 1, gbm.getSize() - 2, gbm.getSize() - 2, 0);
        drawer.drawShape(MapElement.GRASS3_SQUARE, 2, 2, gbm.getSize() - 3, gbm.getSize() - 2, 1);
        drawer.drawRectangle(MapElement.STONE_FLOOR, 2 + (gap / 2) + 2 * tileWidth, 2, tileWidth * 4, tileHeight, 2);
        List<MapElement> tileColor = new ArrayList<>();
        tileColor.add(MapElement.TILE_FIELD_YELLOW);
        tileColor.add(MapElement.TILE_FIELD_RED);
        tileColor.add(MapElement.TILE_FIELD_BLUE);
        tileColor.add(MapElement.TILE_FIELD_GREEN);
        tileColor.add(MapElement.TILE_FIELD_VIOLET);
        for (int y = 1; y < 8; y++) {
            for (int x = 0; x < 7; x++) {
                drawer.drawRectangle(tileColor.get(gen.nextInt(tileColor.size())), margin + x * tileWidth, 2 + y * tileHeight, tileWidth, tileHeight, 2);
            }
        }
        gbm.draw();
    }

    private void drawSpecials(GraphicsContext gc) {
        MediaOperator mo = new MediaOperator();
        int tileWidth = (int) Math.floor((gbm.getSize() - 4) / 8.0);
        int tileHeight = (int) Math.floor((gbm.getSize() - 4) / 8.0);
        int gap = gbm.getSize() - 8 * tileWidth - 4;
        int margin = tileWidth / 2 + (gap / 2);
        tileHeight *= 16;
        tileWidth *= 16;
        margin *= 16;
        System.out.println("S:" + tileWidth + " x " + tileHeight);
        Image img = mo.getImage("texture", tileWidth, tileHeight);
        for (int y = 1; y < 8; y++) {
            for (int x = 0; x < 7; x++) {
                gc.setGlobalAlpha(0.4 * gen.nextDouble() + 0.15);
                gc.drawImage(img, 32 + margin + x * tileWidth, 32 + y * tileHeight, tileWidth, tileHeight);
            }
        }
        gc.setGlobalAlpha(1);
    }

    public void start() {

    }

    private void backToMainMenu() {
        System.out.println("Back to Menu!");
        dataOperator.getView().run(GameModule.MAIN_MENU);
    }
}
