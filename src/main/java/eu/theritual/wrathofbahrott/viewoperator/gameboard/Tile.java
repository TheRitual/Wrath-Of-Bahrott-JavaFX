package eu.theritual.wrathofbahrott.viewoperator.gameboard;

public final class Tile {
    private final int id;
    private final String name;
    private final boolean isTransparent;
    private final int size;

    public Tile(final int id, final String name, final boolean isTransparent, final int size) {
        this.id = id;
        this.name = name;
        this.isTransparent = isTransparent;
        this.size = size;
    }

    public Tile(final int id, final String name, final boolean isTransparent) {
        this(id, name, isTransparent, 16);
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

    public int getSize() {
        return size;
    }
}
