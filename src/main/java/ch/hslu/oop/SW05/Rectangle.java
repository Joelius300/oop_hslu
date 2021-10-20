package ch.hslu.oop.SW05;

public final class Rectangle extends Shape {
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int getPerimeter() {
        return 2 * width + 2 * height;
    }

    @Override
    public int getArea() {
        return width * height;
    }

    public void changeDimension(final int newWidth, final int newHeight) {
        width = newWidth;
        height = newHeight;
    }
}
