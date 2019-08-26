package eu.theritual.wrathofbahrott.viewoperator.sprites;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameSprite;

public final class OnBoardSprite {
    private final GameSprite sprite;
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    public OnBoardSprite(GameSprite sprite, double x, double y, double width, double height) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    GameSprite getSpriteType() {
        return sprite;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    double getWidth() {
        return width;
    }

    double getHeight() {
        return height;
    }
}
