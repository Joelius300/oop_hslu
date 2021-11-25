package ch.hslu.oop.temperature;

import java.util.EventObject;
import java.util.Objects;

public final class TemperatureExtremumEvent extends EventObject {
    private final ExtremumType type;
    private final Temperature newExtremum;
    private final Temperature previousExtremum;

    public TemperatureExtremumEvent(Object source, ExtremumType type, Temperature newExtremum, Temperature previousExtremum) {
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

    public Temperature getNewExtremum() {
        return newExtremum;
    }

    public Temperature getPreviousExtremum() {
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
