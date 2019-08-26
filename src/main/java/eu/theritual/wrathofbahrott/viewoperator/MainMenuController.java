package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.*;
import eu.theritual.wrathofbahrott.utils.SaveLoadUtils;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

import static eu.theritual.wrathofbahrott.dataoperator.gameenums.GamePicture.*;

@Controller
public class MainMenuController extends eu.theritual.wrathofbahrott.viewoperator.Controller {
    @FXML
    GridPane menuPane;
    @FXML
    MediaView musicPlayer;
    @FXML
    RowConstraints row1, row2;

    private VBox currentMenu;
    private ImageView wobLogo;
    private SubView subView = SubView.MAIN_MENU;

    @FXML
    private void exitAction(Event e) {
        Platform.exit();
    }

    @Override
    public void draw() {
        menuPane.setAlignment(Pos.TOP_CENTER);
        menuPane.setPrefSize(view.getScreenWidth(), view.getScreenHeight());
        menuPane.setBackground(mediaOperator.getBackgroundImg(MENU_BACKGROUND, view.getScreenWidth(), view.getScreenHeight()));
        menuPane.getChildren().remove(wobLogo);
        wobLogo = mediaOperator.getImageView(WOB_LOGO, view.getScreenWidth() * 0.4, view.getScreenHeight() * 0.3);
        row1.setMaxHeight(view.getScreenHeight() * 0.1);
        GridPane.setValignment(wobLogo, VPos.TOP);
        GridPane.setHalignment(wobLogo, HPos.CENTER);
        view.getRoot().getScene().getStylesheets().add(mediaOperator.getCss(GameCss.MENU));
        menuPane.add(wobLogo, 1, 0);
        setSubView(subView);
    }

    @Override
    public void start() {
        musicPlayer.setMediaPlayer(null);
        mediaOperator.setMusic(GameSoundVideo.MENU_MUSIC);
        musicPlayer.setMediaPlayer(mediaOperator.getMusicMediaPlayer());
        musicPlayer.getMediaPlayer().stop();
        musicPlayer.getMediaPlayer().setVolume(gameOptions.getMusicVolume() / 100);
        musicPlayer.getMediaPlayer().play();
        if (!gameOptions.isFullScreen()) {
            view.getStage().setResizable(true);
        }
    }

    private void setSubView(SubView subView) {
        menuPane.getChildren().remove(currentMenu);
        if (this.subView != subView) {
            this.subView = subView;
            System.out.println("Changing Menu SubView to " + subView);
        }
        switch (subView) {
            case OPTIONS:
                currentMenu = getOptions();
                break;
            case CREDITS:
                currentMenu = getCredits();
                break;
            case MAIN_MENU:
            default:
                currentMenu = getMainMenu();
        }
        currentMenu.setMaxWidth(view.getScreenWidth() * 0.66);
        currentMenu.setMaxHeight(view.getScreenHeight() * 0.6);
        menuPane.add(currentMenu, 1, 1);
    }

    private VBox getMainMenu() {
        VBox mainMenu = new VBox();
        mainMenu.setAlignment(Pos.TOP_CENTER);
        ImageView exitButton = generator.createMenuButton(EXIT_OUT, EXIT_ON);
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::exitAction);
        ImageView startButton = generator.createMenuButton(START_OUT, START_ON);
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> startGame());
        ImageView optionsButton = generator.createMenuButton(OPTIONS_OUT, OPTIONS_ON);
        optionsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(SubView.OPTIONS));
        ImageView creditsButton = generator.createMenuButton(CREDITS_OUT, CREDITS_ON);
        creditsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(SubView.CREDITS));
        mainMenu.getChildren().add(startButton);
        mainMenu.getChildren().add(optionsButton);
        mainMenu.getChildren().add(creditsButton);
        mainMenu.getChildren().add(exitButton);
        return mainMenu;
    }

    private VBox getOptions() {
        VBox optionsMenu = new VBox();
        VBox innerMenu = new VBox();
        optionsMenu.setBackground(mediaOperator.getBackgroundImg(SUBMENU_BACKGROUND, view.getScreenWidth() * 0.6, view.getScreenHeight() * 0.40));
        optionsMenu.setAlignment(Pos.TOP_CENTER);
        Label topTitle = generator.createLabel("Set Things", "optLabel", generator.getFontSize(15));
        Label backButton = generator.createLabelButton("Back", generator.getFontSize(12));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(SubView.MAIN_MENU));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::saveOptions);
        Label fsButton = generator.createLabelButton("Fullscreen: " + gameOptions.isFullScreen(), generator.getFontSize(12));
        fsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::switchFullscreen);
        Slider volumeSlider = generator.createSlider(0, 100, gameOptions.getMusicVolume());
        volumeSlider.setMaxWidth(view.getScreenWidth() * 0.6);
        Label volumeLabel = generator.createLabel("Volume: " + (int) gameOptions.getMusicVolume() + "%", "optLabelMini", generator.getFontSize(12));
        volumeSlider.valueProperty().addListener(((observable, oldValue, newValue) -> changeMusicVolume(volumeSlider.getValue(), volumeLabel)));
        innerMenu.setAlignment(Pos.TOP_CENTER);
        innerMenu.getChildren().add(fsButton);
        innerMenu.getChildren().add(volumeLabel);
        innerMenu.getChildren().add(volumeSlider);
        return getMenuBox(optionsMenu, innerMenu, topTitle, backButton);
    }

    private void saveOptions(Event e) {
        SaveLoadUtils.saveOptions(gameOptions, "config");
    }

    private VBox getCredits() {
        VBox credits = new VBox();
        VBox innerMenu = new VBox();
        credits.setBackground(mediaOperator.getBackgroundImg(SUBMENU_BACKGROUND, view.getScreenWidth() * 0.6, view.getScreenHeight() * 0.40));
        credits.setAlignment(Pos.TOP_CENTER);
        Label topTitle = generator.createLabel("Credits", "optLabel", generator.getFontSize(15));
        Label backButton = generator.createLabelButton("Back", generator.getFontSize(12));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(SubView.MAIN_MENU));
        innerMenu.setAlignment(Pos.TOP_CENTER);
        ArrayList<Label> creditsList = new ArrayList<>();
        creditsList.add(generator.createLabel("Game Creator: Marcin Kawczynski", "creditsLabel", 11, GameFont.FIPPS));
        creditsList.add(generator.createLabel("Intro: Marcin Kawczynski", "creditsLabel", 11, GameFont.FIPPS));
        creditsList.add(generator.createLabel("Intro Voice: Victoria Sarbiewska", "creditsLabel", 11, GameFont.FIPPS));
        creditsList.add(generator.createLabel("Music: Marcin Kawczynski", "creditsLabel", 11, GameFont.FIPPS));
        creditsList.add(generator.createLabel("Design: Marcin Kawczynski", "creditsLabel", 11, GameFont.FIPPS));
        creditsList.add(generator.createLabel("Graphics: Marcin Kawczynski", "creditsLabel", 11, GameFont.FIPPS));
        creditsList.add(generator.createLabel("Game Idea: Marcin Kawczynski", "creditsLabel", 11, GameFont.FIPPS));
        creditsList.add(generator.createLabel("Ths game is Kodilla Course Project", "creditsLabel", 11, GameFont.FIPPS));
        innerMenu.getChildren().addAll(creditsList);
        innerMenu.setMinWidth(view.getScreenWidth() * 0.6);
        return getMenuBox(credits, innerMenu, topTitle, backButton);
    }

    private VBox getMenuBox(VBox credits, VBox innerMenu, Label topTitle, Label backButton) {
        StackPane stackPane = new StackPane();
        stackPane.setPrefWidth(currentMenu.getWidth());
        stackPane.setMinWidth(view.getScreenWidth() * 0.65);
        stackPane.setAlignment(Pos.TOP_CENTER);
        innerMenu.setFillWidth(true);
        stackPane.getChildren().add(innerMenu);
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setContent(stackPane);
        scroll.setStyle("-fx-background-color: transparent;");
        credits.getChildren().add(topTitle);
        credits.getChildren().add(scroll);
        credits.getChildren().add(backButton);
        credits.setOpacity(0.7);
        credits.setPrefWidth(view.getScreenWidth() * 0.6);
        return credits;
    }

    private void switchFullscreen(Event e) {
        boolean switchFs = !gameOptions.isFullScreen();
        gameOptions.setFullScreen(switchFs);
        view.getStage().setFullScreen(switchFs);
        Label btn = (Label) e.getSource();
        btn.setText("Fullscreen: " + gameOptions.isFullScreen());
    }

    private void changeMusicVolume(double volume, Label updateLabel) {
        int vol = (int) volume;
        gameOptions.setMusicVolume(volume);
        musicPlayer.getMediaPlayer().setVolume(volume / 100);
        updateLabel.setText("Volume: " + vol + "%");
    }

    private void startGame() {
        System.out.println("Game is starting!");
        musicPlayer.getMediaPlayer().dispose();
        view.run(GameModule.GAME);
    }
}
