package ch.hslu.oop.temperature;

import java.util.EventObject;
import java.util.Objects;

public final class TemperatureExtremumEvent extends EventObject {
    private final ExtremumType type;
    private final TemperatureRecord newExtremum;
    private final TemperatureRecord previousExtremum;

    public TemperatureExtremumEvent(Object source, ExtremumType type, TemperatureRecord newExtremum, TemperatureRecord previousExtremum) {
        super(source);

        if (type == null)
            throw new IllegalArgumentException("type cannot be null.");

        if (newExtremum == null)
            throw new IllegalArgumentException("newExtremum cannot be null.");

        this.type = type;
        this.newExtremum = newExtremum;
        this.previousExtremum = previousExtremum;
    }

    public ExtremumType getType() {
        return type;
    }

    public TemperatureRecord getNewExtremum() {
        return newExtremum;
    }

    public TemperatureRecord getPreviousExtremum() {
        return previousExtremum;
    }

    public enum ExtremumType {
        MINIMUM,
        MAXIMUM
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemperatureExtremumEvent that = (TemperatureExtremumEvent) o;
        return type == that.type && newExtremum.equals(that.newExtremum) && Objects.equals(previousExtremum, that.previousExtremum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, newExtremum, previousExtremum);
    }
}
