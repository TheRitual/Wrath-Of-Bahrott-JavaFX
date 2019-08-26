package eu.theritual.wrathofbahrott.viewoperator.sprites;

import eu.theritual.wrathofbahrott.media.SpritesOperator;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public final class SpriteDrawer {
    private final GameBoardMap gbm;
    private final List<OnBoardSprite> sprites;
    private final GraphicsContext gc;
    private final double time;
    private final SpritesOperator spritesOperator;

    public SpriteDrawer(GameBoardMap gbm, GraphicsContext gc, double time) {
        this.spritesOperator = new SpritesOperator(0.12, gbm);
        this.gbm = gbm;
        this.gc = gc;
        this.time = time;
        sprites = new ArrayList<>();
    }

    public void add(OnBoardSprite onBoardSprite) {
        this.sprites.add(onBoardSprite);
    }

    public void draw() {
        for (OnBoardSprite sprite : sprites) {
            double startX = sprite.getX() - 1;
            double startY = sprite.getY() - 1;
            double endX = sprite.getX() + sprite.getWidth();
            double endY = sprite.getY() + sprite.getHeight();
            final Point2D startTile = gbm.getTileByPixel(startX, startY);
            final Point2D endTile = gbm.getTileByPixel(endX, endY);
            gbm.setRefreshField(startTile, endTile, true);
        }
        gbm.draw();
        for (OnBoardSprite sprite : sprites) {
            gc.drawImage(spritesOperator.getSprite(sprite.getSpriteType()).getFrame(time), sprite.getX(), sprite.getY());
        }

    }
}
