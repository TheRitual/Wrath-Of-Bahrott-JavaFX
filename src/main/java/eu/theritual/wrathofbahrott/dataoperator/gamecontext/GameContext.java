package eu.theritual.wrathofbahrott.dataoperator.gamecontext;

import java.util.ArrayList;
import java.util.List;

public class GameContext {
    private final List<Player> players;
    private int currentPlayer;

    public GameContext() {
        currentPlayer = 0;
        players = new ArrayList<>(4);
    }

    public void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == 4) {
            currentPlayer = 0;
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public Player getPlayer(int index) {
        if (index >= 0 && index < 4) {
            return players.get(index);
        } else {
            return null;
        }
    }

    public int getCurrentPlayerNumber() {
        return currentPlayer;
    }

    public Player getCurrentPlayer() {
        return getPlayer(getCurrentPlayerNumber());
    }
}
