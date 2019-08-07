package eu.theritual.wrathofbahrott;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Game extends Application{
    private ConfigurableApplicationContext springContext;

    @Override
    public void start(Stage primaryStage) {
        GameController game = new GameController(primaryStage, springContext);
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
        System.out.println("Bye! Bye!");
    }
}
