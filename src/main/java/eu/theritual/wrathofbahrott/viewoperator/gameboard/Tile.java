package eu.theritual.wrathofbahrott.viewoperator.gameboard;

public final class Tile {
    private final int id;
    private final String name;
    private final boolean isTransparent;

    public Tile(final int id, final String name, final boolean isTransparent) {
        this.id = id;
        this.name = name;
        this.isTransparent = isTransparent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isTransparent() {
        return isTransparent;
    }
}
