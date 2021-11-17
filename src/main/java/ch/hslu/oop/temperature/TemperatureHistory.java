package ch.hslu.oop.temperature;

import java.util.*;
import java.util.stream.Collectors;

public class TemperatureHistory {
    private final Collection<Temperature> backingList;

    public TemperatureHistory() {
        backingList = new ArrayList<>();
    }

    public void add(Temperature temperature) {
        backingList.add(temperature);
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
}
