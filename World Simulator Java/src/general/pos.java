package general;

public class pos {
    public int x;
    public int y;

    public pos() {
    }

    public pos(int i, int i1) {
        x = i;
        y = i1;
    }

    public pos add(pos other) {
        if (other == null) return null;
        return new pos(x + other.x, y + other.y);
    }

    public pos minus(pos other) {
        if (other == null) return null;
        return new pos(x - other.x, y - other.y);
    }
}
