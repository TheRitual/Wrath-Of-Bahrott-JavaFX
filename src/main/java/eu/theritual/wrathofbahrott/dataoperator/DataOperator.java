package eu.theritual.wrathofbahrott.dataoperator;

import eu.theritual.wrathofbahrott.soundoperator.SoundOperator;
import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import org.springframework.context.ConfigurableApplicationContext;

public class DataOperator {
    private GameModule module;
    private ConfigurableApplicationContext springContext;
    private ViewOperator viewOperator;
    private SoundOperator soundOperator;

    public GameModule getModule() {
        return module;
    }

    public ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }

    public ViewOperator getViewOperator() {
        return viewOperator;
    }

    public SoundOperator getSoundOperator() {
        return soundOperator;
    }

    public void setSpringContext(ConfigurableApplicationContext context) {
        this.springContext = context;
    }

    public void setModule(GameModule module) {
        this.module = module;
    }

    public void setViewOperator(ViewOperator operator) {
        this.viewOperator = operator;
    }

    public void setSoundOperator(SoundOperator operator) {
        this.soundOperator = operator;
    }
}
