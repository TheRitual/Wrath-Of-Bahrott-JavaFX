package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaView;
import org.springframework.stereotype.Controller;

@Controller
public class GameController implements eu.theritual.wrathofbahrott.viewoperator.Controller {
    private DataOperator dataOperator;
    private double canvasSize;
    private GraphicsContext gc;
    private GameBoardMap gbm;

    @FXML
    private GridPane gamePane;
    @FXML
    private Canvas gameCanvas;
    @FXML
    private MediaView musicPlayer;

    private int getTilesAmount() {
        return (int) (canvasSize / (double) 16);
    }

    public void setDataOperator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
    }

    public void draw() {
        gamePane.setPrefSize(dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight());
        gamePane.setAlignment(Pos.TOP_CENTER);
        gamePane.setBackground(dataOperator.getMediaOp().getBackgroundImg("gameBackground", dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight()));
        canvasSize = dataOperator.getView().getScreenWidth() * 0.55;
        gameCanvas.setWidth(canvasSize);
        gameCanvas.setHeight(canvasSize);
        gc = gameCanvas.getGraphicsContext2D();
        int boardSize = getTilesAmount();
        System.out.println("Board size:" + boardSize);
        this.gbm = new GameBoardMap(boardSize, gc);
        drawBoard();
    }

    private void drawBoard() {
        gbm.draw();
    }

    public void start() {

    }
}
