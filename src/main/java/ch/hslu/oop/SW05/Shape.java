package ch.hslu.oop.SW05;

public abstract class Shape {
    private int x;
    private int y;

    public Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public void move(final int newX, final int newY) {
        x = newX;
        y = newY;
    }

    public abstract int getPerimeter();
    public abstract int getArea();
}
