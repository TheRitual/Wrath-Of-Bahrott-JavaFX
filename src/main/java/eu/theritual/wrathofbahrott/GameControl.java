package eu.theritual.wrathofbahrott;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameModule;
import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import javafx.stage.Stage;

class GameControl {
    final private ViewOperator viewOperator;
    private DataOperator dataOperator;

    GameControl(Stage mainStage) {
        dataOperator = new DataOperator();
        viewOperator = new ViewOperator(mainStage , dataOperator);
        dataOperator.setModule(GameModule.SPLASH_SCREEN);
        start();
    }

    void start() {
        viewOperator.setUpBeforeRun(true, true);
        viewOperator.run(dataOperator.getModule());
    }
}