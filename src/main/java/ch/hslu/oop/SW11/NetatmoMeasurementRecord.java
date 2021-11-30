package ch.hslu.oop.SW11;

import java.time.LocalDateTime;

public record NetatmoMeasurementRecord(int id, LocalDateTime timestamp, float temperature, int humidity) {
}
