package eu.theritual.wrathofbahrott;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameModule;
import eu.theritual.wrathofbahrott.media.MediaOperator;
import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

class GameControl {
    private DataOperator dataOperator;

    GameControl(Stage mainStage, ConfigurableApplicationContext context) {
        dataOperator = new DataOperator();
        dataOperator.setViewOperator(new ViewOperator(mainStage , dataOperator));
        dataOperator.setMediaOperator(new MediaOperator());
        dataOperator.setModule(GameModule.SPLASH_SCREEN);
        dataOperator.setSpringContext(context);
        dataOperator.setViewOperator(dataOperator.getViewOperator());
        start();
    }

    void start() {
        dataOperator.getViewOperator().setUpBeforeRun(true, true);
        dataOperator.getViewOperator().run(dataOperator.getModule());
    }
}