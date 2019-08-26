package eu.theritual.wrathofbahrott.dataoperator;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameModule;
import eu.theritual.wrathofbahrott.media.MediaOperator;
import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import org.springframework.context.ConfigurableApplicationContext;

public final class DataOperator {
    private GameModule module;
    private GameOptions gameOptions;
    private final ConfigurableApplicationContext springContext;
    private final ViewOperator viewOperator;
    private final MediaOperator mediaOperator;

    public DataOperator(ConfigurableApplicationContext springContext, ViewOperator viewOperator) {
        gameOptions = new GameOptions();
        this.springContext = springContext;
        this.viewOperator = viewOperator;
        module = GameModule.SPLASH_SCREEN;
        this.mediaOperator = new MediaOperator();
    }

    public MediaOperator getMediaOp() {
        return mediaOperator;
    }

    public GameModule getModule() {
        return module;
    }

    public ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }

    public ViewOperator getView() {
        return viewOperator;
    }

    public GameOptions getGOptions() {
        return gameOptions;
    }

    public void setGameOptions(GameOptions gameOptions) {
        this.gameOptions = gameOptions;
    }
}
