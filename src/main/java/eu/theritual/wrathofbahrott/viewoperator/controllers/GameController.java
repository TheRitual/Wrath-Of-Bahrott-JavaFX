package eu.theritual.wrathofbahrott.viewoperator.controllers;

import eu.theritual.wrathofbahrott.dataoperator.gamecontext.GameContext;
import eu.theritual.wrathofbahrott.dataoperator.gamecontext.Player;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.*;
import eu.theritual.wrathofbahrott.viewoperator.controllers.GameObjects.CharacterSelection;
import eu.theritual.wrathofbahrott.viewoperator.controllers.GameObjects.ClassSelectorImage;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes.MapDrawer;
import eu.theritual.wrathofbahrott.viewoperator.sprites.OnBoardSprite;
import eu.theritual.wrathofbahrott.viewoperator.sprites.SpriteDrawer;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaView;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static eu.theritual.wrathofbahrott.dataoperator.gameenums.GamePicture.GAME_BG_PANEL;
import static eu.theritual.wrathofbahrott.dataoperator.gameenums.GamePicture.PRE_MENU_BG;


@Controller
public class GameController extends eu.theritual.wrathofbahrott.viewoperator.controllers.Controller {
    private double canvasSize;
    private GameBoardMap gbm;
    private AnimationTimer animationTimer;
    private GraphicsContext gc;
    private Random gen;
    private VBox currentMenu;
    private GameSubView subView;
    private MediaView music;
    private GameContext gameContext;
    private CharacterSelection currentSelection;
    private MapDrawer drawer;
    private Thread boardThread;

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
    }

    @Override
    public void draw() {
        gamePane.setMinSize(view.getScreenWidth(), view.getScreenHeight());
        gamePane.setPadding(new Insets(0, 0, 0, 0));
        gamePane.setAlignment(Pos.TOP_CENTER);
        gamePane.getColumnConstraints().get(0).setMaxWidth(view.getScreenWidth() * 0.45);
        gamePane.getColumnConstraints().get(1).setMaxWidth(view.getScreenWidth() * 0.55);
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
        drawer = new MapDrawer(gbm);
        gc.clearRect(0, 0, canvasSize, canvasSize);
        boardThread = new Thread(this::drawEmptyBoard);
        boardThread.start();
    }

    private AnimationTimer getGameAnimation() {
        final long startNanoTime = System.nanoTime();
        final byte[] direction = {0};
        return new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                final SpriteDrawer spriteDrawer = new SpriteDrawer(gbm, gc, t);
                GameSprite sprite = GameSprite.NUN_R;
                spriteDrawer.add(new OnBoardSprite(sprite, (t * gbm.getMovementSpeed()) % ((gbm.getSize() - 4) * 16), 32 + (gbm.getTileHeight() * 16) * 5, gbm.getSpriteWidth(), gbm.getSpriteHeight()));
                spriteDrawer.add(new OnBoardSprite(GameSprite.BAHROTT, gbm.getBathrottXPosition() * 16, 8, gbm.getBahrottSize(), gbm.getBahrottSize()));
                spriteDrawer.draw();
            }
        };
    }

    @Override
    public void start() {
        gen = new Random();
        music = new MediaView();
        view.getStage().setResizable(false);
        gameContext = new GameContext();
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
                currentMenu = getStartGameMenu(gameContext.getCurrentPlayerNumber());
                break;
            case PLAYERS_CHECKOUT:
                currentMenu = getPlayersCheckout();
                break;
            case LOAD_GAME:
                currentMenu = getLoadGameMenu();
                break;
            case IN_GAME:
                currentMenu = getIn_GameView();
                break;
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
        Label topTitle = generator.createLabel("Shall we?", "optLabel", generator.getFontSize(17));
        Label startButton = generator.createLabelButton("New Wrath", generator.getFontSize(13));
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(GameSubView.START_GAME));
        Label loadButton = generator.createLabelButton("Old Wrath", generator.getFontSize(13));
        loadButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(GameSubView.LOAD_GAME));
        Label exitButton = generator.createLabelButton("Back", generator.getFontSize(13));
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> backToMainMenu());
        preMenu.getChildren().add(topTitle);
        preMenu.getChildren().add(startButton);
        preMenu.getChildren().add(loadButton);
        preMenu.getChildren().add(exitButton);
        preMenu.setOpacity(0.9);
        return preMenu;
    }

    private ImageView getClassImage(CharacterClass characterClass, double width, double height) {
        GamePicture resPicture;
        switch (characterClass) {
            case WITCH:
                resPicture = GamePicture.WITCH_FRONT;
                break;
            case NUN:
                resPicture = GamePicture.NUN_FRONT;
                break;
            case WORKER:
                resPicture = GamePicture.WORKER_FRONT;
                break;
            case COURIER:
            default:
                resPicture = GamePicture.COURIER_FRONT;
        }
        return mediaOperator.getImageView(resPicture, width, height);
    }

    private ImageView getClassImage(CharacterClass characterClass) {
        return getClassImage(characterClass, (view.getScreenWidth() * 0.45) / 8, (view.getScreenWidth() * 0.45) / 8);
    }

    private VBox getStartGameMenu(int player) {
        currentSelection = new CharacterSelection(player);
        VBox preMenu = new VBox();
        preMenu.setBackground(mediaOperator.getBackgroundImg(GAME_BG_PANEL, view.getScreenWidth() * 0.5, view.getScreenHeight()));
        preMenu.setAlignment(Pos.TOP_CENTER);
        Label topTitle = generator.createLabel("Tell me your story", "optLabel", generator.getFontSize(14));
        Label nameTextField = generator.createLabel("PLAYER [ " + (player + 1) + " ]", "optLabel", generator.getFontSize(10));
        Label separator = generator.createLabel(" * * * ", "optLabel", generator.getFontSize(12));
        Label classTextField = generator.createLabel("Class", "optLabel", generator.getFontSize(12));
        TextField charName = generator.createTextField(generator.getFontSize(7), GameFont.ADDLG);
        Label infoField = generator.createLabel("", "errorLabel", generator.getFontSize(10));
        Label nextButton;
        if (player != 3) {
            nextButton = generator.createLabelButton("NEXT HERO", generator.getFontSize(14));
            nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setPlayer());
        } else {
            nextButton = generator.createLabelButton("START GAME!", generator.getFontSize(14));
            nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                setPlayer();
                startGame(e);
            });
        }
        nextButton.setDisable(true);
        charName.setOnKeyTyped(e -> checkName(e, infoField, nextButton));
        Label exitButton = generator.createLabelButton("Back", generator.getFontSize(10));
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(GameSubView.PRE_MENU));
        HBox classes = new HBox();
        classes.setAlignment(Pos.CENTER);
        ArrayList<ClassSelectorImage> classImages = new ArrayList<>();
        classImages.add(new ClassSelectorImage(mediaOperator.getImageView(GamePicture.WITCH_FRONT, (view.getScreenWidth() * 0.45) / 8, (view.getScreenWidth() * 0.45) / 8), CharacterClass.WITCH));
        classImages.add(new ClassSelectorImage(mediaOperator.getImageView(GamePicture.NUN_FRONT, (view.getScreenWidth() * 0.45) / 8, (view.getScreenWidth() * 0.45) / 8), CharacterClass.NUN));
        classImages.add(new ClassSelectorImage(mediaOperator.getImageView(GamePicture.WORKER_FRONT, (view.getScreenWidth() * 0.45) / 8, (view.getScreenWidth() * 0.45) / 8), CharacterClass.WORKER));
        classImages.add(new ClassSelectorImage(mediaOperator.getImageView(GamePicture.COURIER_FRONT, (view.getScreenWidth() * 0.45) / 8, (view.getScreenWidth() * 0.45) / 8), CharacterClass.COURIER));
        classImages.forEach(ci -> ci.getImage().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> selectClass(e, ci.getCharacterClass(), classImages)));
        classImages.forEach(ci -> classes.getChildren().add(ci.getImage()));
        selectClass(CharacterClass.COURIER, classImages);
        preMenu.getChildren().add(topTitle);
        preMenu.getChildren().add(nameTextField);
        preMenu.getChildren().add(charName);
        preMenu.getChildren().add(classTextField);
        preMenu.getChildren().add(classes);
        preMenu.getChildren().add(nextButton);
        preMenu.getChildren().add(separator);
        preMenu.getChildren().add(exitButton);
        preMenu.getChildren().add(infoField);
        preMenu.setOpacity(0.9);
        return preMenu;
    }

    private void setPlayer() {
        gameContext.addPlayer(new Player(currentSelection.getName(), currentSelection.getCharacterClass()));
        System.out.println("Name: " + currentSelection.getName());
        gameContext.nextPlayer();
        setSubView(GameSubView.START_GAME);
    }

    private void selectClass(Event e, CharacterClass chClass, List<ClassSelectorImage> selectorList) {
        System.out.println("Choosing character:" + chClass + " with " + e.getEventType());
        selectClass(chClass, selectorList);
    }

    private void selectClass(CharacterClass chClass, List<ClassSelectorImage> selectorList) {
        selectorList.stream().filter(cl -> cl.getCharacterClass() != chClass).forEach(cl -> cl.selected(false));
        selectorList.stream().filter(cl -> cl.getCharacterClass() == chClass).forEach(cl -> cl.selected(true));
        currentSelection.setCharacterClass(chClass);
    }

    private void checkName(Event e, Label infoField, Label blockLabelElement) {
        TextField textField = (TextField) e.getSource();
        String name = textField.getText();
        if (name.length() >= 3 && name.length() <= 10) {
            blockLabelElement.setDisable(false);
            infoField.setText("");
            currentSelection.setName(name);
        } else {
            blockLabelElement.setDisable(true);
            infoField.setText("NAME MUST BE AT LEAST 3\nAND MAXIMUM 10 CHARS LONG");
        }
    }

    private void startGame(Event e) {
        boardThread = new Thread(this::drawGameBoard);
        boardThread.start();
        setSubView(GameSubView.PLAYERS_CHECKOUT);
    }

    private void realGameStart() {
        setSubView(GameSubView.IN_GAME);
    }

    private void drawEmptyBoard() {
        System.out.println("GAME_TILE_BOARD:" + gbm.getSize());
        System.out.println("BOARD_TILE_WIDTH:" + gbm.getTileWidth());
        System.out.println("BOARD_TILE_HEIGHT:" + gbm.getTileHeight());
        drawer.drawShape(MapElement.STONE_FLOOR, 0, 0, gbm.getSize() - 1, gbm.getSize() - 1, 0);
        gbm.draw();
    }

    private void drawGameBoard() {
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
                int lay;
                if ((x % 2 == 0) == (y % 2 == 0)) {
                    lay = 3;
                } else {
                    lay = 4;
                }
                drawer.drawRectangle(MapElement.TEXTURE, gbm.getMargin() + x * gbm.getTileWidth(), 2 + y * gbm.getTileHeight(), gbm.getTileWidth(), gbm.getTileHeight(), lay);
            }
        }
        gbm.setRefreshField(0, 0, gbm.getSize(), gbm.getSize(), true);
        gbm.draw();
        animationTimer.start();
    }

    private VBox getLoadGameMenu() {
        VBox loadMenu = new VBox();
        loadMenu.setBackground(mediaOperator.getBackgroundImg(GAME_BG_PANEL, view.getScreenWidth() * 0.45, view.getScreenHeight()));
        loadMenu.setAlignment(Pos.TOP_CENTER);
        Label topTitle = generator.createLabel("They see me Loadin'\nThey savin'", "optLabel", generator.getFontSize(9), GameFont.O4B);
        Label exitButton = generator.createLabelButton("Back", generator.getFontSize(13));
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(GameSubView.PRE_MENU));
        loadMenu.getChildren().add(topTitle);
        loadMenu.getChildren().add(exitButton);
        loadMenu.setOpacity(0.9);
        return loadMenu;
    }

    private VBox getPlayersCheckout() {
        VBox checkoutMenu = new VBox();
        checkoutMenu.setBackground(mediaOperator.getBackgroundImg(GAME_BG_PANEL, view.getScreenWidth() * 0.45, view.getScreenHeight()));
        checkoutMenu.setAlignment(Pos.TOP_CENTER);
        Label topTitle = generator.createLabel("Is is right?", "optLabel", generator.getFontSize(17));
        Label startButton = generator.createLabelButton("BEGIN!", generator.getFontSize(13));
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> realGameStart());
        Label exitButton = generator.createLabelButton("Back", generator.getFontSize(13));
        Label separator = generator.createLabel(" * * * ", "optLabel", generator.getFontSize(12));
        checkoutMenu.getChildren().add(topTitle);
        HBox[] playersList = new HBox[4];
        for (int i = 0; i < 4; i++) {
            playersList[i] = new HBox();
            playersList[i].setAlignment(Pos.CENTER);
            playersList[i].getChildren().add(getClassImage(gameContext.getPlayer(i).getCharacterClass()));
            playersList[i].getChildren().add(mediaOperator.getImageView(GamePicture.SHIELD, 32, 32));
            playersList[i].getChildren().add(generator.createLabel(gameContext.getPlayer(i).getName(), "nameTag", generator.getFontSize(7), GameFont.ADDLG));
            checkoutMenu.getChildren().add(playersList[i]);
        }
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> backToMainMenu());
        checkoutMenu.getChildren().add(startButton);
        checkoutMenu.getChildren().add(separator);
        checkoutMenu.getChildren().add(exitButton);
        checkoutMenu.setOpacity(0.9);
        return checkoutMenu;
    }

    private VBox getIn_GameView() {
        VBox inGamePanel = new VBox();
        int itemsAmount = 6;
        double panelWidth = view.getScreenWidth() * 0.45;
        double elementSize = panelWidth / itemsAmount;
        inGamePanel.setBackground(mediaOperator.getBackgroundImg(GAME_BG_PANEL, view.getScreenWidth() * 0.45, view.getScreenHeight()));
        inGamePanel.setAlignment(Pos.TOP_CENTER);
        Label topTitle = generator.createLabel("WRATH OF BAGROTT!", "optLabel", generator.getFontSize(20));
        Label exitButton = generator.createLabelButton("Back", generator.getFontSize(13));
        Label separator = generator.createLabel(" * * * ", "optLabel", generator.getFontSize(12));
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(GameSubView.PRE_MENU));
        HBox player = new HBox();
        player.setAlignment(Pos.CENTER);
        player.getChildren().add(getClassImage(gameContext.getCurrentPlayer().getCharacterClass(), elementSize, elementSize));
        player.getChildren().add(mediaOperator.getImageView(GamePicture.SHIELD, elementSize / 2, elementSize / 2));
        player.getChildren().add(generator.createLabel(gameContext.getCurrentPlayer().getName(), "nameTag", generator.getFontSize(10), GameFont.ADDLG));
        HBox items = new HBox();
        items.setPrefWidth(panelWidth);
        items.setPrefHeight(elementSize);
        List<Pane> itemSlots = new ArrayList<>(itemsAmount);
        List<ImageView> itemImages = new ArrayList<>(itemsAmount);
        for (int i = 0; i < itemsAmount; i++) {
            Pane pane = new Pane();
            itemImages.add(new ImageView());
            pane.getChildren().add(itemImages.get(i));
            pane.setPrefHeight(elementSize);
            pane.setPrefWidth(elementSize);
            pane.setBackground(new Background(new BackgroundImage(mediaOperator.getImage(GamePicture.ITEM_SLOT, elementSize, elementSize), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
            itemSlots.add(pane);
        }


        items.getChildren().addAll(itemSlots);
        inGamePanel.getChildren().add(topTitle);
        inGamePanel.getChildren().add(player);
        inGamePanel.getChildren().add(items);
        inGamePanel.getChildren().add(separator);
        inGamePanel.getChildren().add(exitButton);
        inGamePanel.setOpacity(0.9);
        return inGamePanel;
    }
}
