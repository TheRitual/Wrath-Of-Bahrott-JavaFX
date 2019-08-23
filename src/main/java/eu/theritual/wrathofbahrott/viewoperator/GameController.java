package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameModule;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.GamePicture;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.MapElement;
import eu.theritual.wrathofbahrott.media.MediaOperator;
import eu.theritual.wrathofbahrott.media.SpritesOperator;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes.MapDrawer;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.Cursor;
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
public class GameController extends eu.theritual.wrathofbahrott.viewoperator.Controller {
    private double canvasSize;
    private GameBoardMap gbm;
    private Random gen = new Random();
    private Thread boardThread;
    private AnimationTimer animationTimer;
    private GraphicsContext gc;
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

    @Override
    public void stop() {
        animationTimer.stop();
        boardThread.interrupt();
    }

    @Override
    public void draw() {
        boardThread = new Thread();
        gamePane.setMinSize(dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight());
        gamePane.setPadding(new Insets(10, 10, 10, 10));
        gamePane.setAlignment(Pos.TOP_CENTER);
        gamePane.setBackground(dataOperator.getMediaOp().getBackgroundImg(GamePicture.GAME_BACKGROUND, dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight()));
        Label exitButton = generator.createLabelButton("Back", generator.getFontSize(15));
        gamePane.add(exitButton, 0, 0);
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> backToMainMenu());
        canvasSize = dataOperator.getView().getScreenWidth() * 0.52;
        gameCanvas.setWidth(canvasSize);
        gameCanvas.setHeight(canvasSize);
        GridPane.setValignment(gameCanvas, VPos.TOP);
        GridPane.setHalignment(gameCanvas, HPos.RIGHT);
        gameCanvas.setOnMouseEntered(e -> dataOperator.getView().getRoot().getScene().setCursor(Cursor.CROSSHAIR));
        gameCanvas.setOnMouseExited(e -> dataOperator.getView().getRoot().getScene().setCursor(Cursor.DEFAULT));
        gc = gameCanvas.getGraphicsContext2D();
        animationTimer = getGameAnimation(gc);
        int boardSize = getTilesAmount();
        this.gbm = new GameBoardMap(boardSize, gc);
        gc.clearRect(0, 0, canvasSize, canvasSize);
        boardThread = new Thread(this::drawBoard);
        boardThread.start();
    }

    private void drawBoard() {
        MapDrawer drawer = new MapDrawer(gbm);
        System.out.println("GAME_TILE_BOARD:" + gbm.getSize());
        System.out.println("BOARD_TILE_WIDTH:" + gbm.getTileWidth());
        System.out.println("BOARD_TILE_HEIGHT:" + gbm.getTileHeight());
        drawer.drawShape(MapElement.STONE_FLOOR, 0, 0, gbm.getSize() - 1, gbm.getSize() - 1, 0);
        drawer.drawShape(MapElement.SCOTT_FLOOR, 1, 1, gbm.getSize() - 2, gbm.getSize() - 2, 0);
        drawer.drawShape(MapElement.GRASS3_SQUARE, 2, 2, gbm.getSize() - 3, gbm.getSize() - 2, 1);
        drawer.drawRectangle(MapElement.STONE_FLOOR, 2 + (gbm.getGap() / 2) + 2 * gbm.getTileWidth(), 2, gbm.getTileWidth() * 4, gbm.getTileHeight(), 2);
        List<MapElement> tileColor = new ArrayList<>();
        tileColor.add(MapElement.TILE_FIELD_YELLOW);
        tileColor.add(MapElement.TILE_FIELD_RED);
        tileColor.add(MapElement.TILE_FIELD_BLUE);
        tileColor.add(MapElement.TILE_FIELD_GREEN);
        tileColor.add(MapElement.TILE_FIELD_VIOLET);

        for (int y = 1; y < 8; y++) {
            for (int x = 0; x < 7; x++) {
                drawer.drawRectangle(tileColor.get(gen.nextInt(tileColor.size())), gbm.getMargin() + x * gbm.getTileWidth(), 2 + y * gbm.getTileHeight(), gbm.getTileWidth(), gbm.getTileHeight(), 2);
            }
        }
        gbm.draw();
        drawSpecials(gc);
        animationTimer.start();
    }

    private void drawSpecials(GraphicsContext gc) {
        MediaOperator mo = new MediaOperator();
        int tileHeight = gbm.getTileHeight() * 16;
        int tileWidth = gbm.getTileWidth() * 16;
        int margin = gbm.getMargin() * 16;
        System.out.println("S:" + tileWidth + " x " + tileHeight);
        Image img = mo.getImage(GamePicture.BIG_TILE_TEXTURE, tileWidth, tileHeight);
        for (int y = 1; y < 8; y++) {
            for (int x = 0; x < 7; x++) {
                gc.setGlobalAlpha(0.4 * gen.nextDouble() + 0.15);
                gc.drawImage(img, margin + x * tileWidth, 32 + y * tileHeight, tileWidth, tileHeight);
            }
        }
        gc.setGlobalAlpha(1);
    }

    private AnimationTimer getGameAnimation(GraphicsContext gc) {
        final long startNanoTime = System.nanoTime();
        final Point2D[] startTile = new Point2D[1];
        final Point2D[] endTile = new Point2D[1];
        return new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                endTile[0] = gbm.getTileByPixel(gbm.getBathrottXPosition() * 16 + gbm.getBahrottSize() * 16, 8 + gbm.getBahrottSize() * 16);
                startTile[0] = gbm.getTileByPixel(gbm.getBathrottXPosition() * 16, 8);
                gbm.setRefreshField(startTile[0], endTile[0], true);
                gbm.draw();
                gc.drawImage(SpritesOperator.getSprite(0, gbm.getBahrottSize() * 16, gbm.getBahrottSize() * 16).getFrame(t), gbm.getBathrottXPosition() * 16, 8);
            }
        };
    }

    @Override
    public void start() {
        dataOperator.getView().getStage().setResizable(false);
    }

    private void backToMainMenu() {
        System.out.println("Back to Menu!");
        dataOperator.getView().run(GameModule.MAIN_MENU);
        stop();
    }
}
