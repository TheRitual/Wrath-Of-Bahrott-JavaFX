package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;

public abstract class Controller {
    DataOperator dataOperator;
    ElementGenerator generator;

    void setDataOperator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
        generator = new ElementGenerator(dataOperator);
    }

    void start() {
    }

    void stop() {
    }

    void draw() {
    }
}
