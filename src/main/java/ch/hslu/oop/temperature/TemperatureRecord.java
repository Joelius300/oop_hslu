package ch.hslu.oop.temperature;

import java.time.LocalDateTime;

public record TemperatureRecord(LocalDateTime timestamp, Temperature temperature) {
}
