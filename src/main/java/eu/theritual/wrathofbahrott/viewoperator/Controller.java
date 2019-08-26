package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameOptions;
import eu.theritual.wrathofbahrott.media.MediaOperator;

public abstract class Controller {
    GameOptions gameOptions;
    MediaOperator mediaOperator;
    ElementGenerator generator;
    ViewOperator view;

    void setDataOperator(DataOperator dataOperator) {
        gameOptions = dataOperator.getGOptions();
        mediaOperator = dataOperator.getMediaOp();
        view = dataOperator.getView();
        generator = new ElementGenerator(dataOperator);
    }

    void start() {
    }

    void stop() {
    }

    void draw() {
    }
}
