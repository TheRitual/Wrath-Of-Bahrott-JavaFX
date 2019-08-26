package eu.theritual.wrathofbahrott.viewoperator.controllers;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.*;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes.MapDrawer;
import eu.theritual.wrathofbahrott.viewoperator.sprites.OnBoardSprite;
import eu.theritual.wrathofbahrott.viewoperator.sprites.SpriteDrawer;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import org.springframework.stereotype.Controller;

import java.util.Random;

import static eu.theritual.wrathofbahrott.dataoperator.gameenums.GamePicture.PRE_MENU_BG;

@Controller
public class GameController extends eu.theritual.wrathofbahrott.viewoperator.controllers.Controller {
    private double canvasSize;
    private GameBoardMap gbm;
    private Random gen = new Random();
    private Thread boardThread;
    private AnimationTimer animationTimer;
    private GraphicsContext gc;
    private VBox currentMenu;
    private GameSubView subView;
    private MediaView music;

    @FXML
    private GridPane gamePane;
    @FXML
    private Canvas gameCanvas;

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
        gamePane.setMinSize(view.getScreenWidth(), view.getScreenHeight());
        gamePane.setPadding(new Insets(0, 0, 0, 0));
        gamePane.setAlignment(Pos.TOP_CENTER);
        gamePane.setBackground(mediaOperator.getBackgroundImg(GamePicture.GAME_BACKGROUND, view.getScreenWidth(), view.getScreenHeight()));
        setSubView(GameSubView.PRE_MENU);
        drawCanvas();
    }

    private void drawCanvas() {
        canvasSize = view.getScreenWidth() * 0.55;
        gameCanvas.setWidth(canvasSize);
        gameCanvas.setHeight(canvasSize);
        GridPane.setValignment(gameCanvas, VPos.TOP);
        GridPane.setHalignment(gameCanvas, HPos.LEFT);
        gameCanvas.setOnMouseEntered(e -> view.getRoot().getScene().setCursor(Cursor.CROSSHAIR));
        gameCanvas.setOnMouseExited(e -> view.getRoot().getScene().setCursor(Cursor.DEFAULT));
        gc = gameCanvas.getGraphicsContext2D();
        animationTimer = getGameAnimation();
        int boardSize = getTilesAmount();
        this.gbm = new GameBoardMap(boardSize, gc);
        gc.clearRect(0, 0, canvasSize, canvasSize);
        boardThread = new Thread(this::drawBoard);
        boardThread.start();
    }

    private void drawBoard() {
        MapDrawer drawer = new MapDrawer(gbm);
        /*System.out.println("GAME_TILE_BOARD:" + gbm.getSize());
        System.out.println("BOARD_TILE_WIDTH:" + gbm.getTileWidth());
        System.out.println("BOARD_TILE_HEIGHT:" + gbm.getTileHeight());*/
        drawer.drawShape(MapElement.STONE_FLOOR, 0, 0, gbm.getSize() - 1, gbm.getSize() - 1, 0);
        /*drawer.drawShape(MapElement.SCOTT_FLOOR, 1, 1, gbm.getSize() - 2, gbm.getSize() - 2, 0);
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
                int lay;
                if ((x % 2 == 0) == (y % 2 == 0)) {
                    lay = 3;
                } else {
                    lay = 4;
                }
                drawer.drawRectangle(MapElement.TEXTURE, gbm.getMargin() + x * gbm.getTileWidth(), 2 + y * gbm.getTileHeight(), gbm.getTileWidth(), gbm.getTileHeight(), lay);
            }
        }*/
        gbm.draw();
        //animationTimer.start();
    }

    private AnimationTimer getGameAnimation() {
        final long startNanoTime = System.nanoTime();
        return new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                final SpriteDrawer spriteDrawer = new SpriteDrawer(gbm, gc, t);
                spriteDrawer.add(new OnBoardSprite(GameSprite.WITCH_T, (gbm.getTileWidth() - 1) * 16, (gbm.getTileHeight() - 1) * 16, gbm.getBathrottXPosition() * 16 + (gbm.getTileWidth() * 16), (gbm.getSize() - 2) * 16 - t * gbm.getMovementSpeed()));
                spriteDrawer.add(new OnBoardSprite(GameSprite.BAHROTT, gbm.getBahrottSize() * 16, gbm.getBahrottSize() * 16, gbm.getBathrottXPosition() * 16, 8));
                spriteDrawer.draw();
            }
        };
    }

    @Override
    public void start() {
        music = new MediaView();
        view.getStage().setResizable(false);
    }

    private void backToMainMenu() {
        System.out.println("Back to Menu!");
        view.run(GameModule.MAIN_MENU);
        stop();
    }

    private void setSubView(GameSubView subView) {
        gamePane.getChildren().remove(currentMenu);
        if (this.subView != subView) {
            this.subView = subView;
            System.out.println("Changing Game SubView to " + subView);
        }
        switch (subView) {
            case START_GAME:
                currentMenu = getStartGameMenu();
                break;
            /*case LOAD_GAME:
                currentMenu = getLoadGameMenu();
                break;
            case IN_GAME:
                currentMenu = getIn_GameView();
                break;*/
            case PRE_MENU:
            default:
                currentMenu = getPreMenu();
        }
        currentMenu.setMinWidth(view.getScreenWidth() * 0.45);
        currentMenu.setMaxHeight(view.getScreenHeight());
        gamePane.add(currentMenu, 0, 0);
    }

    private VBox getPreMenu() {
        VBox preMenu = new VBox();
        preMenu.setBackground(mediaOperator.getBackgroundImg(PRE_MENU_BG, view.getScreenWidth() * 0.45, view.getScreenHeight()));
        preMenu.setAlignment(Pos.TOP_CENTER);
        Label topTitle = generator.createLabel("Shall we?", "optLabel", generator.getFontSize(15));
        Label startButton = generator.createLabelButton("New Wrath", generator.getFontSize(11));
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(GameSubView.START_GAME));
        Label exitButton = generator.createLabelButton("Back", generator.getFontSize(11));
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> backToMainMenu());
        preMenu.getChildren().add(topTitle);
        preMenu.getChildren().add(startButton);
        preMenu.getChildren().add(exitButton);
        preMenu.setOpacity(0.9);
        return preMenu;
    }

    private VBox getStartGameMenu() {
        VBox preMenu = new VBox();
        preMenu.setBackground(mediaOperator.getBackgroundImg(PRE_MENU_BG, view.getScreenWidth() * 0.5, view.getScreenHeight()));
        preMenu.setAlignment(Pos.TOP_CENTER);
        Label topTitle = generator.createLabel("Tell me your story", "optLabel", generator.getFontSize(12));
        Label nameTextField = generator.createLabel("Name", "gameLabel", generator.getFontSize(11));
        TextField charName = new TextField();
        Label exitButton = generator.createLabelButton("Back", generator.getFontSize(12));
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(GameSubView.PRE_MENU));
        preMenu.getChildren().add(topTitle);
        preMenu.getChildren().add(nameTextField);
        preMenu.getChildren().add(charName);
        preMenu.getChildren().add(exitButton);
        preMenu.setOpacity(0.9);
        return preMenu;
    }
}
