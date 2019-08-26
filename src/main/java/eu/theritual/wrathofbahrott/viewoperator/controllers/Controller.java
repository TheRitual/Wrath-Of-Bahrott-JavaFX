package eu.theritual.wrathofbahrott.viewoperator.controllers;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameOptions;
import eu.theritual.wrathofbahrott.media.MediaOperator;
import eu.theritual.wrathofbahrott.viewoperator.ElementGenerator;
import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;

public abstract class Controller {
    GameOptions gameOptions;
    MediaOperator mediaOperator;
    ViewOperator view;
    ElementGenerator generator;

    public void setDataOperator(DataOperator dataOperator) {
        gameOptions = dataOperator.getGOptions();
        mediaOperator = dataOperator.getMediaOp();
        view = dataOperator.getView();
        generator = view.getGenerator();
    }

    public void start() {
    }

    public void stop() {
    }

    public void draw() {
    }
}
