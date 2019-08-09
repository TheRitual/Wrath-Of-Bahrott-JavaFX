package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.SubView;
import eu.theritual.wrathofbahrott.utils.SaveLoadUtils;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class MainMenuController {
    @FXML
    BorderPane menuPane;
    @FXML
    MediaView musicPlayer;
    private DataOperator dataOperator;

    @FXML
    private void exitAction(Event e) {
        Platform.exit();
    }

    private void buttonHoverAction(Event e) {
        ImageView img = (ImageView) e.getSource();
        img.setImage(dataOperator.getMediaOperator().getImage(img.getId() + "On", 263, 100));
        dataOperator.getViewOperator().getRoot().getScene().setCursor(Cursor.HAND);
    }

    private void buttonUnHoverAction(Event e) {
        ImageView img = (ImageView) e.getSource();
        img.setImage(dataOperator.getMediaOperator().getImage(img.getId() + "Out", 263, 100));
        dataOperator.getViewOperator().getRoot().getScene().setCursor(Cursor.DEFAULT);
    }

    private void labelButtonHoverAction(Event e) {
        Label btn = (Label) e.getSource();
        btn.setTextFill(Color.rgb(145, 59, 158));
        dataOperator.getViewOperator().getRoot().getScene().setCursor(Cursor.HAND);
    }

    private void labelButtonUnHoverAction(Event e) {
        Label btn = (Label) e.getSource();
        btn.setTextFill(Color.rgb(59, 158, 133));
        dataOperator.getViewOperator().getRoot().getScene().setCursor(Cursor.DEFAULT);
    }

    void setDataOperator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
    }

    private ImageView createMenuButton(String gfxName) {
        ImageView btn = dataOperator.getMediaOperator().getImageView(gfxName + "Out", 263, 100);
        btn.setId(gfxName);
        btn.setOnMouseEntered(this::buttonHoverAction);
        btn.setOnMouseExited(this::buttonUnHoverAction);
        return btn;
    }

    private Label createLabelButton(String labelTxt, double size) {
        Font fnt = dataOperator.getMediaOperator().getFont("vermin", size);
        Label btn = new Label(labelTxt);
        btn.setFont(fnt);
        btn.setId(labelTxt);
        btn.getStyleClass().add("optLabelBtn");
        btn.setTextFill(Color.rgb(59, 158, 133));
        btn.setOnMouseEntered(this::labelButtonHoverAction);
        btn.setOnMouseExited(this::labelButtonUnHoverAction);
        return btn;
    }

    private Label createLabel(String labelTxt, String styleClass, double size) {
        Font fnt = dataOperator.getMediaOperator().getFont("vermin", size);
        Label btn = new Label(labelTxt);
        btn.setFont(fnt);
        btn.getStyleClass().add(styleClass);
        btn.setTextFill(Color.rgb(4, 127, 158));
        return btn;
    }

    private Slider createSlider(double min, double max, double value) {
        Slider slider = new Slider();
        slider.setMax(max);
        slider.setMin(min);
        slider.setValue(value);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        return slider;
    }

    private Label getBackButton() {
        Label backButton = createLabelButton("Back", 40);
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(SubView.MAIN_MENU));
        return backButton;
    }

    void startMenu() {
        menuPane.setPrefSize(dataOperator.getViewOperator().getScreenWidth(), dataOperator.getViewOperator().getScreenHeight());
        menuPane.setBackground(dataOperator.getMediaOperator().getBackgroundImg("menuBackground", dataOperator.getViewOperator().getScreenWidth(), dataOperator.getViewOperator().getScreenHeight()));
        ImageView wobLogo = dataOperator.getMediaOperator().getImageView("wobLogo", dataOperator.getViewOperator().getScreenWidth() * 0.50, dataOperator.getViewOperator().getScreenHeight() * 0.50);
        menuPane.setTop(wobLogo);
        menuPane.getBottom().prefHeight(10);
        BorderPane.setAlignment(wobLogo, Pos.TOP_CENTER);
        setSubView(SubView.MAIN_MENU);
        dataOperator.getViewOperator().getRoot().getScene().getStylesheets().add(dataOperator.getMediaOperator().getCss("menu"));
        dataOperator.getMediaOperator().setMusic("menuMusic");
        musicPlayer.setMediaPlayer(dataOperator.getMediaOperator().getMusicMediaPlayer());
        musicPlayer.getMediaPlayer().setVolume(dataOperator.getGameOptions().getMusicVolume() / 100);
        musicPlayer.getMediaPlayer().play();
    }

    private void setSubView(SubView subView) {
        VBox subV;
        System.out.println("Changing Menu SubView to " + subView);
        switch (subView) {
            case OPTIONS:
                subV = getOptions();
                break;
            case CREDITS:
                subV = getCredits();
                break;
            case MAIN_MENU:
            default:
                subV = getMainMenu();
        }
        double prefWidth = dataOperator.getViewOperator().getScreenWidth() * 0.5;
        subV.setMaxWidth(prefWidth);
        menuPane.setCenter(subV);
        System.out.println("WIDTH:" + prefWidth);
        BorderPane.setAlignment(subV, Pos.TOP_CENTER);
    }

    private VBox getMainMenu() {
        VBox mainMenu = new VBox();
        mainMenu.setAlignment(Pos.TOP_CENTER);
        ImageView exitButton = createMenuButton("exit");
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::exitAction);
        ImageView startButton = createMenuButton("start");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.out.println("STARTING GAME!"));
        ImageView optionsButton = createMenuButton("options");
        optionsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(SubView.OPTIONS));
        ImageView creditsButton = createMenuButton("credits");
        creditsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(SubView.CREDITS));
        mainMenu.getChildren().add(startButton);
        mainMenu.getChildren().add(optionsButton);
        mainMenu.getChildren().add(creditsButton);
        mainMenu.getChildren().add(exitButton);
        return mainMenu;
    }

    private VBox getOptions() {
        VBox optionsMenu = new VBox();
        optionsMenu.setBackground(dataOperator.getMediaOperator().getBackgroundImg("optionsBackground", dataOperator.getViewOperator().getScreenWidth() * 0.5, menuPane.getCenter().getLayoutBounds().getHeight()));
        optionsMenu.setAlignment(Pos.TOP_CENTER);
        Label topTitle = createLabel("Set Things", "optLabel", 60);
        Label fsButton = createLabelButton("Fullscreen: " + dataOperator.getGameOptions().isFullScreen(), 40);
        fsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::switchFullscreen);
        Slider volumeSlider = createSlider(0, 100, dataOperator.getGameOptions().getMusicVolume());
        volumeSlider.setMaxWidth(dataOperator.getViewOperator().getScreenWidth() * 0.4);
        Label volumeLabel = createLabel("Volume: " + (int) dataOperator.getGameOptions().getMusicVolume() + "%", "optLabelMini", 40);
        volumeSlider.valueProperty().addListener(((observable, oldValue, newValue) -> changeMusicVolume(volumeSlider.getValue(), volumeLabel)));
        Label backButton = getBackButton();
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::saveOptions);
        optionsMenu.getChildren().add(topTitle);
        optionsMenu.getChildren().add(fsButton);
        optionsMenu.getChildren().add(volumeLabel);
        optionsMenu.getChildren().add(volumeSlider);
        optionsMenu.getChildren().add(backButton);
        optionsMenu.setOpacity(0.7);
        return optionsMenu;
    }

    private void saveOptions(Event e) {
        SaveLoadUtils.saveOptions(dataOperator.getGameOptions(), "config");
    }

    private VBox getCredits() {
        VBox creditsMenu = new VBox();
        creditsMenu.setBackground(dataOperator.getMediaOperator().getBackgroundImg("optionsBackground", menuPane.getCenter().getLayoutBounds().getWidth(), menuPane.getCenter().getLayoutBounds().getHeight()));
        creditsMenu.setAlignment(Pos.TOP_CENTER);
        Label topTitle = createLabel("Credits", "optLabel", 60);
        double creditsSize = 40;
        ArrayList<Label> creditsLines = new ArrayList<>();
        creditsLines.add(createLabel("Game Creator: Marcin \"Shinobi\" Kawczynski", "optLabelMini", creditsSize));
        creditsLines.add(createLabel("Music: Marcin \"Shinobi\" Kawczynski", "optLabelMini", creditsSize));
        creditsLines.add(createLabel("Intro: Marcin \"Shinobi\" Kawczynski and Victoria Sarbiewska", "optLabelMini", creditsSize));
        creditsLines.add(createLabel("GFX: Marcin \"Shinobi\" Kawczynski", "optLabelMini", creditsSize));
        creditsLines.add(createLabel("Made as project for Kodilla Bootcamp", "optLabelMini", creditsSize));
        creditsMenu.getChildren().add(topTitle);
        creditsLines.forEach(creditLine -> creditsMenu.getChildren().add(creditLine));
        creditsMenu.getChildren().add(getBackButton());
        creditsMenu.setOpacity(0.7);
        creditsMenu.prefHeight(dataOperator.getViewOperator().getScreenHeight() * 0.66);
        return creditsMenu;
    }

    private void switchFullscreen(Event e) {
        boolean switchFs = !dataOperator.getGameOptions().isFullScreen();
        dataOperator.getGameOptions().setFullScreen(switchFs);
        dataOperator.getViewOperator().getStage().setFullScreen(switchFs);
        Label btn = (Label) e.getSource();
        btn.setText("Fullscreen: " + dataOperator.getGameOptions().isFullScreen());
    }

    private void changeMusicVolume(double volume, Label updateLabel) {
        int vol = (int) volume;
        dataOperator.getGameOptions().setMusicVolume(volume);
        musicPlayer.getMediaPlayer().setVolume(volume / 100);
        updateLabel.setText("Volume: " + vol + "%");
    }
}
