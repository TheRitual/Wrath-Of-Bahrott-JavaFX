package eu.theritual.wrathofbahrott.dataoperator;
import eu.theritual.wrathofbahrott.media.MediaOperator;
import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import org.springframework.context.ConfigurableApplicationContext;

public class DataOperator {
    private GameModule module;
    private ConfigurableApplicationContext springContext;
    private ViewOperator viewOperator;
    private MediaOperator mediaOperator;
    private GameOptions gameOptions;

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

    public void setModule(GameModule module) {
        this.module = module;
    }

    public GameOptions getGOptions() {
        return gameOptions;
    }

    public void setGameOptions(GameOptions gameOptions) {
        this.gameOptions = gameOptions;
    }
}
