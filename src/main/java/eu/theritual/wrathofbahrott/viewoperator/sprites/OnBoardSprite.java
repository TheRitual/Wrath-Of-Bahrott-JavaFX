package eu.theritual.wrathofbahrott.viewoperator.sprites;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameSprite;

public final class OnBoardSprite {
    private final GameSprite sprite;
    private final double width;
    private final double height;
    private final double x;
    private final double y;

    public OnBoardSprite(GameSprite sprite, double width, double height, double x, double y) {
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    GameSprite getSpriteType() {
        return sprite;
    }

    double getWidth() {
        return width;
    }

    double getHeight() {
        return height;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }
}
