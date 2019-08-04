package eu.theritual.wrathofbahrott.dataoperator;

import org.springframework.context.ConfigurableApplicationContext;

public class DataOperator {
    private GameModule module;
    private ConfigurableApplicationContext springContext;

    public GameModule getModule() {
        return module;
    }

    public ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }

    public void setSpringContext(ConfigurableApplicationContext context) {
        this.springContext = context;
    }

    public void setModule(GameModule module) {
        this.module = module;
    }
}
