package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes.MapDrawer;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes.MapElement;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
        gamePane.setMinSize(dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight());
        gamePane.setPadding(new Insets(10, 10, 10, 10));
        gamePane.setAlignment(Pos.TOP_CENTER);
        gamePane.setBackground(dataOperator.getMediaOp().getBackgroundImg("gameBackground", dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight()));
        canvasSize = dataOperator.getView().getScreenWidth() * 0.52;
        gameCanvas.setWidth(canvasSize);
        gameCanvas.setHeight(canvasSize);
        System.out.println(gameCanvas.getWidth());
        GridPane.setValignment(gameCanvas, VPos.TOP);
        GridPane.setHalignment(gameCanvas, HPos.RIGHT);
        gc = gameCanvas.getGraphicsContext2D();
        int boardSize = getTilesAmount();
        System.out.println("Board size:" + boardSize);
        this.gbm = new GameBoardMap(boardSize, gc);
        gc.clearRect(0, 0, canvasSize, canvasSize);
        drawBoard();
    }

    private void drawBoard() {
        MapDrawer drawer = new MapDrawer(gbm);
        drawer.drawShape(MapElement.STONE_FLOOR, 0, 0, gbm.getSize() - 1, gbm.getSize() - 1, 0);
        drawer.drawShape(MapElement.GRASS3_SQUARE, 2, 2, gbm.getSize() - 3, gbm.getSize() - 3, 0);


        gbm.draw();
    }

    public void start() {

    }
}
