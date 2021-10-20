package ch.hslu.oop.SW05;

public final class Square extends Shape {
    private int side;

    public Square(int x, int y, int side) {
        super(x, y);
        this.side = side;
    }

    public int getSide() {
        return side;
    }

    @Override
    public int getPerimeter() {
        return 4 * side;
    }

    @Override
    public int getArea() {
        return side * side;
    }

    public void setSide(final int newSide) {
        side = newSide;
    }
}
