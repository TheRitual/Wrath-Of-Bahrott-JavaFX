package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameFont;
import eu.theritual.wrathofbahrott.dataoperator.gameenums.GamePicture;
import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ElementGenerator {
    private DataOperator dataOperator;

    ElementGenerator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
    }

    private double calculateButtonWidth() {
        return dataOperator.getView().getScreenWidth() * 0.1925;
    }

    private double calculateButtonHeight() {
        return dataOperator.getView().getScreenHeight() * 0.13;
    }

    public int getFontSize(double size) {
        size *= 0.2;
        double height = dataOperator.getView().getScreenHeight();
        return (int) ((height / 72) * size);
    }

    private void changeGfxAction(Event e, GamePicture changePicture) {
        Image img = dataOperator.getMediaOp().getImage(changePicture, calculateButtonWidth(), calculateButtonHeight());
        ImageView thisImg = (ImageView) e.getSource();
        thisImg.setImage(img);
    }

    private void changeCursor(Cursor cursor) {
        dataOperator.getView().getRoot().getScene().setCursor(cursor);
    }

    private void labelButtonHoverAction(Event e) {
        Label btn = (Label) e.getSource();
        btn.setTextFill(Color.rgb(145, 59, 158));
        changeCursor(Cursor.HAND);
    }

    private void labelButtonUnHoverAction(Event e) {
        Label btn = (Label) e.getSource();
        btn.setTextFill(Color.rgb(59, 158, 133));
        changeCursor(Cursor.DEFAULT);
    }

    public ImageView createMenuButton(GamePicture gfxName, GamePicture gfxOnHoverName) {
        ImageView btn = dataOperator.getMediaOp().getImageView(gfxName, calculateButtonWidth(), calculateButtonHeight());
        btn.setId(gfxName.toString());
        btn.setOnMouseEntered((e -> {
            changeGfxAction(e, gfxOnHoverName);
            changeCursor(Cursor.HAND);
        }));
        btn.setOnMouseExited(e -> {
            changeGfxAction(e, gfxName);
            changeCursor(Cursor.DEFAULT);
        });
        return btn;
    }

    public Label createLabelButton(String labelTxt, double size) {
        Font fnt = dataOperator.getMediaOp().getFont(GameFont.VERMIN, getFontSize(size));
        Label btn = new Label(labelTxt);
        btn.setFont(fnt);
        btn.setId(labelTxt);
        btn.getStyleClass().add("optLabelBtn");
        btn.setTextFill(Color.rgb(59, 158, 133));
        btn.setOnMouseEntered(this::labelButtonHoverAction);
        btn.setOnMouseExited(this::labelButtonUnHoverAction);
        return btn;
    }

    public Label createLabel(String labelTxt, String styleClass, double size) {
        return createLabel(labelTxt, styleClass, size, GameFont.VERMIN);
    }

    public Label createLabel(String labelTxt, String styleClass, double size, GameFont gameFont) {
        Font fnt = dataOperator.getMediaOp().getFont(gameFont, getFontSize(size));
        Label btn = new Label(labelTxt);
        btn.setFont(fnt);
        btn.getStyleClass().add(styleClass);
        btn.setTextFill(Color.rgb(4, 127, 158));
        return btn;
    }

    public Slider createSlider(double min, double max, double value) {
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
}
