package eu.theritual.wrathofbahrott;

import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.application.Application;

@SpringBootApplication
public class Game extends Application{
    private ConfigurableApplicationContext springContext;

    @Override
    public void start(Stage primaryStage) {
        GameControl game = new GameControl(primaryStage, springContext);
        game.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init(){
        springContext = SpringApplication.run(Game.class);
    }

    @Override
    public void stop() {
        springContext.stop();
    }
}
