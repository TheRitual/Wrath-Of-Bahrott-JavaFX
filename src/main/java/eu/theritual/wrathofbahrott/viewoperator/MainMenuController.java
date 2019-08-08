package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.SubView;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.springframework.stereotype.Controller;

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
        Font fnt = dataOperator.getMediaOperator().getFont("fipps", size);
        Label btn = new Label(labelTxt);
        btn.setFont(fnt);
        btn.setId(labelTxt);
        btn.setTextFill(Color.rgb(59, 158, 133));
        btn.setOnMouseEntered(this::labelButtonHoverAction);
        btn.setOnMouseExited(this::labelButtonUnHoverAction);
        return btn;
    }

    void startMenu() {
        menuPane.setPrefSize(dataOperator.getViewOperator().getScreenWidth(), dataOperator.getViewOperator().getScreenHeight());
        menuPane.setBackground(dataOperator.getMediaOperator().getBackgroundImg("menuBackground", dataOperator.getViewOperator().getScreenWidth(), dataOperator.getViewOperator().getScreenHeight()));
        ImageView wobLogo = dataOperator.getMediaOperator().getImageView("wobLogo", dataOperator.getViewOperator().getScreenWidth() * 0.50, dataOperator.getViewOperator().getScreenHeight() * 0.50);
        menuPane.setTop(wobLogo);
        BorderPane.setAlignment(wobLogo, Pos.TOP_CENTER);
        setSubView(SubView.MAIN_MENU);
        dataOperator.getMediaOperator().setMusic("menuMusic");
        musicPlayer.setMediaPlayer(dataOperator.getMediaOperator().getMusicMediaPlayer());
        musicPlayer.getMediaPlayer().play();
    }

    private void setSubView(SubView subView) {
        VBox subV;
        System.out.println("Changing Menu SubView to " + subView);
        switch (subView) {
            case OPTIONS:
                subV = getOptions();
                break;
            case MAIN_MENU:
            default:
                subV = getMainMenu();
        }
        menuPane.setCenter(subV);
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
        creditsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.out.println("SHOWING CREDITS"));
        mainMenu.getChildren().add(startButton);
        mainMenu.getChildren().add(optionsButton);
        mainMenu.getChildren().add(creditsButton);
        mainMenu.getChildren().add(exitButton);
        return mainMenu;
    }

    private VBox getOptions() {
        VBox optionsMenu = new VBox();
        optionsMenu.setBackground(dataOperator.getMediaOperator().getBackgroundImg("optionsBackground", menuPane.getCenter().getLayoutBounds().getWidth(), menuPane.getCenter().getLayoutBounds().getHeight()));
        optionsMenu.setAlignment(Pos.TOP_CENTER);
        Label backButton = createLabelButton("BACK", 40);
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setSubView(SubView.MAIN_MENU));
        optionsMenu.getChildren().add(backButton);
        optionsMenu.setOpacity(0.8);
        return optionsMenu;
    }
}
