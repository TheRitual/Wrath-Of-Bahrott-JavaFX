package eu.theritual.wrathofbahrott;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.utils.SaveLoadUtils;
import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

class GameManager {
    private DataOperator dataOperator;
    private ViewOperator view;

    GameManager(Stage mainStage, ConfigurableApplicationContext context) {
        dataOperator = new DataOperator(context, new ViewOperator(mainStage));
        view = dataOperator.getView();
        dataOperator.setGameOptions(SaveLoadUtils.loadOptions("config"));
        view.setDataOperator(dataOperator);
        start();
    }

    void start() {
        dataOperator.getView().initiate();
        dataOperator.getView().run(dataOperator.getModule());
    }
}