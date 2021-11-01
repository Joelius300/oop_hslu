package ch.hslu.oop.temperature;

import java.util.Objects;

public final class Element {
    private final String symbol;
    private final Temperature meltingPoint;
    private final Temperature boilingPoint;

    public Element(String symbol, Temperature meltingPoint, Temperature boilingPoint) {
        if (symbol == null || symbol.isEmpty())
            throw new IllegalArgumentException("Symbol cannot be null or empty.");

        if (meltingPoint == null)
            throw new IllegalArgumentException("Melting point cannot be null.");

        if (boilingPoint == null)
            throw new IllegalArgumentException("Boiling point cannot be null.");

        this.symbol = symbol;
        this.meltingPoint = meltingPoint;
        this.boilingPoint = boilingPoint;
    }

    public String getSymbol() {
        return symbol;
    }

    public Temperature getMeltingPoint() {
        return meltingPoint;
    }

    public Temperature getBoilingPoint() {
        return boilingPoint;
    }

    public Phase getPhaseAt(Temperature temperature) {
        if (temperature == null)
            throw new IllegalArgumentException("Temperature cannot be null.");

        if (temperature.compareTo(meltingPoint) < 0) // temperature < meltingPoint scheiss java
            return Phase.Solid;

        if (temperature.compareTo(boilingPoint) > 0) // temperature > boilingPoint
            return Phase.Gas;

        return Phase.Liquid;
    }

    public enum Phase {
        Solid,
        Liquid,
        Gas
    }

    /* Since I implemented Element immutable and final instead
     * of creating subclasses for each element, this equals implementation
     * is "normal". If I had chosen the other way, I wouldn't be able to use
     * instanceof! Because with those, the identity is _only_ determined by
     * the type of the object. There you'd have an identity check and then a
     * getClass check. That's all the identity of such an Element would be made of.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element element)) return false;
        return symbol.equals(element.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
