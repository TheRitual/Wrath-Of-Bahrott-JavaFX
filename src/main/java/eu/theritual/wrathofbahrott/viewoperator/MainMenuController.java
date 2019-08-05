package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Controller;

@Controller
public class MainMenuController {
    @FXML
    GridPane menuPane;

    private ViewOperator viewOperator;
    private DataOperator dataOperator;

    @FXML
    private void exitAction(Event e) {
        Platform.exit();
    }

    void setViewOperator(ViewOperator viewOperator) {
        this.viewOperator = viewOperator;
    }
    void setDataOperator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
    }

    void drawMenu() {
        Button exitButton = new Button("Exit");
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::exitAction);
        menuPane.add(new ImageView(),0,0);
        menuPane.add(exitButton, 0, 1);
    }
}
