package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;

public interface Controller {
    void setDataOperator(DataOperator dataOperator);
    void start();
    void draw();
}
