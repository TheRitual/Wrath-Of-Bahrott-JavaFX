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

    public SpriteDrawer(GameBoardMap gbm, GraphicsContext gc, double time) {
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
            final Point2D startTile = gbm.getTileByPixel(sprite.getX(), sprite.getY());
            final Point2D endTile = gbm.getTileByPixel(sprite.getX() + sprite.getWidth() + 32, sprite.getY() + sprite.getHeight() + 32);
            gbm.setRefreshField(startTile, endTile, true);
        }
        gbm.draw();
        for (OnBoardSprite sprite : sprites) {
            gc.drawImage(SpritesOperator.getSprite(sprite.getSpriteType(), sprite.getWidth(), sprite.getHeight()).getFrame(time), sprite.getX(), sprite.getY());
        }

    }
}
