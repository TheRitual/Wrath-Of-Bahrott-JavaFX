package eu.theritual.wrathofbahrott;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.utils.SaveLoadUtils;
import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

class GameController {
    private DataOperator dataOperator;

    GameController(Stage mainStage, ConfigurableApplicationContext context) {
        dataOperator = new DataOperator(context, new ViewOperator(mainStage));
        dataOperator.getViewOperator().setDataOperator(dataOperator);
        dataOperator.setGameOptions(SaveLoadUtils.loadOptions("config"));
        start();
    }

    void start() {
        dataOperator.getViewOperator().setUpBeforeRun();
        dataOperator.getViewOperator().run(dataOperator.getModule());
    }
}