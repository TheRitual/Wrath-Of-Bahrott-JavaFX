package eu.theritual.wrathofbahrott.dataoperator.gamecontext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameContext {
    private final List<Player> players;
    private int currentPlayer;
    private final Map<Player, PlayerStatus> playersStatus;

    public GameContext() {
        currentPlayer = 0;
        players = new ArrayList<>(4);
        playersStatus = new HashMap<>();
    }

    public void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == 4) {
            currentPlayer = 0;
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        this.playersStatus.put(player, new PlayerStatus(players.size() - 1));
    }

    public PlayerStatus getPlayerStatus(Player player) {
        return this.playersStatus.get(player);
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

    @Override
    public String toString() {
        return "GameContext{" +
                "currentPlayer=" + currentPlayer +
                '}';
    }
}
