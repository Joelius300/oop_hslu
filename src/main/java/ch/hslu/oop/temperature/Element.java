package ch.hslu.oop.temperature;

public class Element {
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
}
