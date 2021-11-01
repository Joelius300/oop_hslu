package ch.hslu.oop.SW03;

import java.util.Objects;

public final class Line {
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        setStart(start);
        setEnd(end);
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    // Yea these setters work, but now we have the issue they wanted me to
    // run into with Point. If something uses Lines internally, and we are able
    // to get them out, we could modify them. This means cloning is required
    // which makes many things more cumbersome. We could/should instead design
    // immutable types to avoid such pitfalls even if it means more objects
    // will be created.
    public void setStart(Point start) {
        if (start == null)
            throw new IllegalArgumentException("start can't be null");
        
        this.start = start;
    }

    public void setEnd(Point end) {
        if (end == null)
            throw new IllegalArgumentException("end can't be null");

        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Since Line is final, using instanceof should act the same as using getClass.
        if (!(o instanceof Line line)) return false;
        return start.equals(line.start) && end.equals(line.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
