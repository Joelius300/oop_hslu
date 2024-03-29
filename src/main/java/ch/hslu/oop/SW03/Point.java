package ch.hslu.oop.SW03;

import java.util.Objects;

public final class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        x = other.getX();
        y = other.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Quadrant getQuadrant() throws PointLiesOnAxisException {
        if (x == 0 || y == 0)
            throw new PointLiesOnAxisException("Cannot get Quadrant since point lies on an axis.");

        if (x > 0) {
            if (y > 0) {
                return Quadrant.UPPER_RIGHT;
            } else {
                return Quadrant.LOWER_RIGHT;
            }
        } else {
            if (y > 0) {
                return Quadrant.UPPER_LEFT;
            } else {
                return Quadrant.LOWER_LEFT;
            }
        }
    }

    // Since Point should be immutable, we return a new point instead of mutating the current instance.
    public Point moveRelative(int x, int y) {
        return new Point(getX() + x, getY() + y);
    }

    public Point moveRelative(Point vec) {
        return moveRelative(vec.getX(), vec.getY());
    }

    public enum Quadrant {
        UPPER_RIGHT,
        UPPER_LEFT,
        LOWER_LEFT,
        LOWER_RIGHT
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Since Point is final, using instanceof should act the same as using getClass.
        if (!(o instanceof Point point)) return false;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    // In my opinion there's no natural order for 2d points, so I'm not implementing Comparable
}
