package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.viewoperator.viewutils.ViewUtils;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Controller;

@Controller
public class MainMenuController {
    @FXML
    BorderPane menuPane;

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
        menuPane.setPrefSize(viewOperator.getScreenWidth(),viewOperator.getScreenHeight());
        menuPane.setBackground(ViewUtils.fullWindowBG("menuBackground", viewOperator.getScreenWidth(), viewOperator.getScreenHeight()));
        ImageView wobLogo = ViewUtils.getImageView("wobLogo", viewOperator.getScreenWidth()* 0.50, viewOperator.getScreenHeight() * 0.50);
        menuPane.setTop(wobLogo);
        BorderPane.setAlignment(wobLogo, Pos.TOP_CENTER);
        Button exitButton = new Button("Exit");
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::exitAction);
        VBox menuList = new VBox();
        menuList.getChildren().add(exitButton);
        menuPane.setCenter(menuList);
        BorderPane.setAlignment(menuList, Pos.CENTER);
    }
}
