package ch.hslu.oop.temperature;

import java.util.*;

public class TemperatureHistory {
    private final Collection<Temperature> backingList;
    private final List<TemperatureExtremumEventListener> listeners;

    public TemperatureHistory() {
        backingList = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    private void raiseExtremumEvent(TemperatureExtremumEvent.ExtremumType type, Temperature newExtremum, Temperature prevExtremum) {
        for (TemperatureExtremumEventListener listener:
             listeners) {
            listener.newExtremumReached(new TemperatureExtremumEvent(this, type, newExtremum, prevExtremum));
        }
    }

    public void add(Temperature temperature) {
        if (temperature == null)
            throw new IllegalArgumentException("Cannot add null values to TemperatureHistory.");

        Temperature currentMax = getMaximum();
        Temperature currentMin = getMinimum();

        backingList.add(temperature);

        if (currentMin == null || temperature.compareTo(currentMin) < 0) {
            raiseExtremumEvent(TemperatureExtremumEvent.ExtremumType.MINIMUM, temperature, currentMin);
        }

        if (currentMax == null || temperature.compareTo(currentMax) > 0) {
            raiseExtremumEvent(TemperatureExtremumEvent.ExtremumType.MAXIMUM, temperature, currentMax);
        }
    }

    public void clear() {
        backingList.clear();
    }

    public int getCount() {
        return backingList.size();
    }

    public Temperature getAverage() {
        if (backingList.isEmpty())
            return null;

        return Temperature.fromKelvin((float)backingList.stream()
                .mapToDouble(Temperature::getKelvin)
                .average()
                .orElseThrow());
    }

    public Temperature getMinimum() {
        if (backingList.isEmpty())
            return null;

        return Collections.min(backingList);
    }

    public Temperature getMaximum() {
        if (backingList.isEmpty())
            return null;

        return Collections.max(backingList);
    }

    public void addExtremumEventListener(TemperatureExtremumEventListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("listener cannot be null");

        listeners.add(listener);
    }

    public void removeExtremumEventListener(TemperatureExtremumEventListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("listener cannot be null");

        listeners.remove(listener);
    }
}
