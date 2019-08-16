package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

class ElementGenerator {
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

    int getFontSize(double size) {
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

    ImageView createMenuButton(String gfxName) {
        ImageView btn = dataOperator.getMediaOp().getImageView(gfxName + "Out", calculateButtonWidth(), calculateButtonHeight());
        btn.setId(gfxName);
        btn.setOnMouseEntered(this::buttonHoverAction);
        btn.setOnMouseExited(this::buttonUnHoverAction);
        return btn;
    }

    Label createLabelButton(String labelTxt, double size) {
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

    Label createLabel(String labelTxt, String styleClass, double size) {
        return createLabel(labelTxt, styleClass, size, "vermin");
    }

    Label createLabel(String labelTxt, String styleClass, double size, String fontName) {
        Font fnt = dataOperator.getMediaOp().getFont(fontName, getFontSize(size));
        Label btn = new Label(labelTxt);
        btn.setFont(fnt);
        btn.getStyleClass().add(styleClass);
        btn.setTextFill(Color.rgb(4, 127, 158));
        return btn;
    }

    Slider createSlider(double min, double max, double value) {
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
