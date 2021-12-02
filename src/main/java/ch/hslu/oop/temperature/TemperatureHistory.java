package ch.hslu.oop.temperature;

import java.util.*;
import java.util.stream.Stream;

/**
 * Class for archiving temperature data. It _should_ be fully synchronized so
 * you can add new records and get statistics concurrently. Adding a record while
 * streaming the contents with {@link #stream()} will still raise a {@link ConcurrentModificationException}.
 */
// I don't want the statistic methods blocking the other processes but
// synchronization outside this class would be harder (although more customizable).
// So ultimately, I don't know what is better but with my current knowledge I think both are reasonable.
// I went with locking for the statistic methods too and used two locks and avoided locking on this (sync methods).
public class TemperatureHistory {
    private final Collection<TemperatureRecord> backingList;
    private final List<TemperatureExtremumEventListener> listeners;
    private final Object addLock = new Object();
    private final Object listenerLock = new Object();

    public TemperatureHistory() {
        backingList = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    private void raiseExtremumEvent(TemperatureExtremumEvent.ExtremumType type, TemperatureRecord newExtremum, TemperatureRecord prevExtremum) {
        synchronized (listenerLock) {
            for (TemperatureExtremumEventListener listener :
                    listeners) {
                listener.newExtremumReached(new TemperatureExtremumEvent(this, type, newExtremum, prevExtremum));
            }
        }
    }

    private String getSummary() {
        if (backingList.isEmpty())
            return "Leerer Temperaturverlauf";

        TemperatureRecord min = getMinimum();
        TemperatureRecord max = getMaximum();
        // German as the assignment states
        return "Temperaturverlauf: " + '\n' +
                "   Minimum: " + min.temperature().getCelsius() + " °C um " + min.timestamp() + '\n' +
                "   Maximum: " + max.temperature().getCelsius() + " °C um " + max.timestamp() + '\n' +
                "   Durchschnitt: " + getAverage().getCelsius() + " °C";
    }

    public void add(TemperatureRecord temperature) {
        if (temperature == null)
            throw new IllegalArgumentException("Cannot add null values to TemperatureHistory.");

        // ArrayList isn't synchronized so manual sync is required to avoid concurrency issues.
        // As of sw11 I'm not going to have issues with that but I could if I implemented the file parsing differently.
        synchronized (addLock) {
            TemperatureRecord currentMax = getMaximum();
            TemperatureRecord currentMin = getMinimum();

            backingList.add(temperature);

            if (currentMin == null || temperature.temperature().compareTo(currentMin.temperature()) < 0) {
                raiseExtremumEvent(TemperatureExtremumEvent.ExtremumType.MINIMUM, temperature, currentMin);
            }

            if (currentMax == null || temperature.temperature().compareTo(currentMax.temperature()) > 0) {
                raiseExtremumEvent(TemperatureExtremumEvent.ExtremumType.MAXIMUM, temperature, currentMax);
            }
        }
    }

    public void clear() {
        synchronized (addLock) {
            backingList.clear();
        }
    }

    public int getCount() {
        return backingList.size();
    }

    public Temperature getAverage() {
        if (backingList.isEmpty())
            return null;

        synchronized (addLock) {
            return Temperature.fromKelvin((float) stream()
                    .mapToDouble(t -> t.temperature().getKelvin())
                    .average()
                    .orElseThrow());
        }
    }

    public TemperatureRecord getMinimum() {
        synchronized (addLock) {
            return stream()
                    .min(Comparator.comparing(TemperatureRecord::temperature))
                    .orElse(null);
        }
    }

    public TemperatureRecord getMaximum() {
        synchronized (addLock) {
            return stream()
                    .max(Comparator.comparing(TemperatureRecord::temperature))
                    .orElse(null);
        }
    }

    public Stream<TemperatureRecord> stream() {
        return backingList.stream();
    }

    public void addExtremumEventListener(TemperatureExtremumEventListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("listener cannot be null");

        synchronized (listenerLock) {
            listeners.add(listener);
        }
    }

    public void removeExtremumEventListener(TemperatureExtremumEventListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("listener cannot be null");

        synchronized (listenerLock) {
            listeners.remove(listener);
        }
    }

    @Override
    public String toString() {
        return getSummary();
    }
}
