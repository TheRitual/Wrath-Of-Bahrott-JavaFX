package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameModule;
import eu.theritual.wrathofbahrott.dataoperator.SubView;
import eu.theritual.wrathofbahrott.utils.SaveLoadUtils;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class MainMenuController implements eu.theritual.wrathofbahrott.viewoperator.Controller {
    @FXML
    GridPane menuPane;
    @FXML
    MediaView musicPlayer;
    @FXML
    RowConstraints row1, row2;

    private VBox currentMenu;
    private ImageView wobLogo;
    private DataOperator dataOperator;
    private SubView subView = SubView.MAIN_MENU;

    @FXML
    private void exitAction(Event e) {
        Platform.exit();
    }

    private double calculateButtonWidth() {
        return dataOperator.getView().getScreenWidth() * 0.1925;
    }

    private double calculateButtonHeight() {
        return dataOperator.getView().getScreenHeight() * 0.13;
    }

    private int getFontSize(double size) {
        size *= 0.2;
        double height = dataOperator.getView().getScreenHeight();
        return (int) ((height / 72) * size);
    }

    private void buttonHoverAction(Event e) {
        ImageView img = (ImageView) e.getSource();
        img.setImage(dataOperator.getMediaOp().getImage(img.getId() + "On", calculateButtonWidth(), calculateButtonHeight()));
        dataOperator.getView().getRoot().getScene().setCursor(Cursor.HAND);
    }

    private void buttonUnHoverAction(Event e) {
        ImageView img = (ImageView) e.getSource();
        img.setImage(dataOperator.getMediaOp().getImage(img.getId() + "Out", calculateButtonWidth(), calculateButtonHeight()));
        dataOperator.getView().getRoot().getScene().setCursor(Cursor.DEFAULT);
    }

    private void labelButtonHoverAction(Event e) {
        Label btn = (Label) e.getSource();
        btn.setTextFill(Color.rgb(145, 59, 158));
        dataOperator.getView().getRoot().getScene().setCursor(Cursor.HAND);
    }

    private void labelButtonUnHoverAction(Event e) {
        Label btn = (Label) e.getSource();
        btn.setTextFill(Color.rgb(59, 158, 133));
        dataOperator.getView().getRoot().getScene().setCursor(Cursor.DEFAULT);
    }

    public void setDataOperator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
    }

    private ImageView createMenuButton(String gfxName) {
        ImageView btn = dataOperator.getMediaOp().getImageView(gfxName + "Out", calculateButtonWidth(), calculateButtonHeight());
        btn.setId(gfxName);
        btn.setOnMouseEntered(this::buttonHoverAction);
        btn.setOnMouseExited(this::buttonUnHoverAction);
        return btn;
    }

    private Label createLabelButton(String labelTxt, double size) {
        Font fnt = dataOperator.getMediaOp().getFont("vermin", getFontSize(size));
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
        return createLabel(labelTxt, styleClass, size, "vermin");
    }

    private Label createLabel(String labelTxt, String styleClass, double size, String fontName) {
        Font fnt = dataOperator.getMediaOp().getFont(fontName, getFontSize(size));
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
        Label backButton = createLabelButton("Back", getFontSize(15));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(SubView.MAIN_MENU));
        return backButton;
    }

    public void draw() {
        menuPane.setAlignment(Pos.TOP_CENTER);
        menuPane.setPrefSize(dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight());
        menuPane.setBackground(dataOperator.getMediaOp().getBackgroundImg("menuBackground", dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight()));
        menuPane.getChildren().remove(wobLogo);
        wobLogo = dataOperator.getMediaOp().getImageView("wobLogo", dataOperator.getView().getScreenWidth() * 0.4, dataOperator.getView().getScreenHeight() * 0.3);
        row1.setMaxHeight(dataOperator.getView().getScreenHeight() * 0.1);
        GridPane.setValignment(wobLogo, VPos.TOP);
        GridPane.setHalignment(wobLogo, HPos.CENTER);
        dataOperator.getView().getRoot().getScene().getStylesheets().add(dataOperator.getMediaOp().getCss("menu"));
        menuPane.add(wobLogo, 1, 0);
        setSubView(subView);
    }

    public void start() {
        musicPlayer.setMediaPlayer(null);
        dataOperator.getMediaOp().setMusic("menuMusic");
        musicPlayer.setMediaPlayer(dataOperator.getMediaOp().getMusicMediaPlayer());
        musicPlayer.getMediaPlayer().stop();
        musicPlayer.getMediaPlayer().setVolume(dataOperator.getGOptions().getMusicVolume() / 100);
        musicPlayer.getMediaPlayer().play();
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
        currentMenu.setMaxWidth(dataOperator.getView().getScreenWidth() * 0.66);
        currentMenu.setMaxHeight(dataOperator.getView().getScreenHeight() * 0.6);
        menuPane.add(currentMenu, 1, 1);
    }

    private VBox getMainMenu() {
        VBox mainMenu = new VBox();
        mainMenu.setAlignment(Pos.TOP_CENTER);
        ImageView exitButton = createMenuButton("exit");
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::exitAction);
        ImageView startButton = createMenuButton("start");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> startGame());
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
        VBox innerMenu = new VBox();
        optionsMenu.setBackground(dataOperator.getMediaOp().getBackgroundImg("optionsBackground", dataOperator.getView().getScreenWidth() * 0.6, dataOperator.getView().getScreenHeight() * 0.40));
        optionsMenu.setAlignment(Pos.TOP_CENTER);
        Label topTitle = createLabel("Set Things", "optLabel", getFontSize(15));
        Label backButton = getBackButton();
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::saveOptions);
        Label fsButton = createLabelButton("Fullscreen: " + dataOperator.getGOptions().isFullScreen(), getFontSize(12));
        fsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::switchFullscreen);
        Slider volumeSlider = createSlider(0, 100, dataOperator.getGOptions().getMusicVolume());
        volumeSlider.setMaxWidth(dataOperator.getView().getScreenWidth() * 0.6);
        Label volumeLabel = createLabel("Volume: " + (int) dataOperator.getGOptions().getMusicVolume() + "%", "optLabelMini", getFontSize(12));
        volumeSlider.valueProperty().addListener(((observable, oldValue, newValue) -> changeMusicVolume(volumeSlider.getValue(), volumeLabel)));
        innerMenu.setAlignment(Pos.TOP_CENTER);
        innerMenu.getChildren().add(fsButton);
        innerMenu.getChildren().add(volumeLabel);
        innerMenu.getChildren().add(volumeSlider);
        return getMenuBox(optionsMenu, innerMenu, topTitle, backButton);
    }

    private void saveOptions(Event e) {
        SaveLoadUtils.saveOptions(dataOperator.getGOptions(), "config");
    }

    private VBox getCredits() {
        VBox credits = new VBox();
        VBox innerMenu = new VBox();
        credits.setBackground(dataOperator.getMediaOp().getBackgroundImg("optionsBackground", dataOperator.getView().getScreenWidth() * 0.6, dataOperator.getView().getScreenHeight() * 0.40));
        credits.setAlignment(Pos.TOP_CENTER);
        Label topTitle = createLabel("Credits", "optLabel", getFontSize(15));
        Label backButton = getBackButton();
        innerMenu.setAlignment(Pos.TOP_CENTER);
        ArrayList<Label> creditsList = new ArrayList<>();
        creditsList.add(createLabel("Game Creator: Marcin Kawczynski", "creditsLabel", 11, "fipps"));
        creditsList.add(createLabel("Intro: Marcin Kawczynski", "creditsLabel", 11, "fipps"));
        creditsList.add(createLabel("Intro Voice: Victoria Sarbiewska", "creditsLabel", 11, "fipps"));
        creditsList.add(createLabel("Music: Marcin Kawczynski", "creditsLabel", 11, "fipps"));
        creditsList.add(createLabel("Design: Marcin Kawczynski", "creditsLabel", 11, "fipps"));
        creditsList.add(createLabel("Graphics: Marcin Kawczynski", "creditsLabel", 11, "fipps"));
        creditsList.add(createLabel("Game Idea: Marcin Kawczynski", "creditsLabel", 11, "fipps"));
        creditsList.add(createLabel("Ths game is Kodilla Course Project", "creditsLabel", 11, "fipps"));
        innerMenu.getChildren().addAll(creditsList);
        innerMenu.setMinWidth(dataOperator.getView().getScreenWidth() * 0.6);
        return getMenuBox(credits, innerMenu, topTitle, backButton);
    }

    private VBox getMenuBox(VBox credits, VBox innerMenu, Label topTitle, Label backButton) {
        StackPane stackPane = new StackPane();
        stackPane.setPrefWidth(currentMenu.getWidth());
        stackPane.setMinWidth(dataOperator.getView().getScreenWidth() * 0.65);
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
        credits.setPrefWidth(dataOperator.getView().getScreenWidth() * 0.6);
        return credits;
    }

    private void switchFullscreen(Event e) {
        boolean switchFs = !dataOperator.getGOptions().isFullScreen();
        dataOperator.getGOptions().setFullScreen(switchFs);
        dataOperator.getView().getStage().setFullScreen(switchFs);
        Label btn = (Label) e.getSource();
        btn.setText("Fullscreen: " + dataOperator.getGOptions().isFullScreen());
    }

    private void changeMusicVolume(double volume, Label updateLabel) {
        int vol = (int) volume;
        dataOperator.getGOptions().setMusicVolume(volume);
        musicPlayer.getMediaPlayer().setVolume(volume / 100);
        updateLabel.setText("Volume: " + vol + "%");
    }

    private void startGame() {
        System.out.println("Game is starting!");
        musicPlayer.getMediaPlayer().dispose();
        dataOperator.getView().run(GameModule.GAME);
    }
}
