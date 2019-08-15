package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes.MapShape;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.mapshapes.ShapeDrawer;
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
        gamePane.setPrefSize(dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight());
        gamePane.setPadding(new Insets(10, 10, 10, 10));
        gamePane.setAlignment(Pos.TOP_CENTER);
        gamePane.setBackground(dataOperator.getMediaOp().getBackgroundImg("gameBackground", dataOperator.getView().getScreenWidth(), dataOperator.getView().getScreenHeight()));
        canvasSize = dataOperator.getView().getScreenWidth() * 0.52;
        gameCanvas.setWidth(canvasSize);
        gameCanvas.setHeight(canvasSize);
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
        ShapeDrawer drawer = new ShapeDrawer(gbm);
        drawer.draw(MapShape.GRASS3_SQUARE, 0, 0, gbm.getSize() - 1, gbm.getSize() - 1, 0);
        drawer.draw(MapShape.GRASS2_SQUARE, 5, 5, gbm.getSize() - 5, gbm.getSize() - 5, 2);
        drawer.draw(MapShape.GRASS2_HOLE, 10, 10, gbm.getSize() - 10, gbm.getSize() - 10, 2);
        drawer.draw(MapShape.SAND2, 7, 7, gbm.getSize() - 7, gbm.getSize() - 7, 1);
        drawer.draw(MapShape.GRASS1_SQUARE, gbm.getSize() / 2, 0, gbm.getSize() - 1, gbm.getSize() - 1, 3);


        gbm.draw();
    }

    public void start() {

    }
}
