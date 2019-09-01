package eu.theritual.wrathofbahrott.dataoperator.gamecontext;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.Direction;

public class PlayerStatus {
    private int xPos, yPos;
    private Direction direction;

    PlayerStatus(int playerNumber) {
        switch (playerNumber) {
            case 0:
                setPosition(0, 0);
                break;
            case 1:
                setPosition(6, 0);
                break;
            case 2:
                setPosition(0, 6);
                break;
            case 3:
            default:
                setPosition(6, 6);
                break;
        }
        this.direction = Direction.BOTTOM;
    }

    private void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "PlayerStatus{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                ", direction=" + direction +
                '}';
    }
}
