package ch.hslu.oop.SW11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class NetatmoCsvReader {
    private static final String TIMESTAMP_FORMAT = "\"yyyy/MM/dd HH:mm:ss\"";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);

    public NetatmoMeasurementRecord parseLine(String line) {
        String[] parts = line.split(";");

        int id;
        LocalDateTime timestamp;
        float temperature;
        int humidity;
        try {
            id = Integer.parseInt(parts[0]);
            timestamp = LocalDateTime.parse(parts[1], TIMESTAMP_FORMATTER);
            temperature = Float.parseFloat(parts[2]);
            humidity = Integer.parseInt(parts[3]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Number in line '" + line + "' could not be parsed: " + ex.getMessage(), ex);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Timestamp in line '" + line + "' could not be parsed: " + ex.getMessage(), ex);
        }

        return new NetatmoMeasurementRecord(id, timestamp, temperature, humidity);
    }

    public Stream<NetatmoMeasurementRecord> parseStream(BufferedReader reader) {
        // Inspired by BufferedReader.lines()
        Iterator<NetatmoMeasurementRecord> iterator = new Iterator<>() {
            NetatmoMeasurementRecord nextRecord = null;

            @Override
            public boolean hasNext() {
                if (nextRecord != null) {
                    return true;
                } else {
                    try {
                        String line = reader.readLine();
                        if (line == null)
                            return false;

                        nextRecord = parseLine(line);
                        return true;
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    } catch (IllegalArgumentException ex) {
                        throw new IllegalArgumentException("Error parsing stream: " + ex.getMessage(), ex);
                    }
                }
            }

            @Override
            public NetatmoMeasurementRecord next() {
                if (nextRecord != null || hasNext()) {
                    NetatmoMeasurementRecord record = nextRecord;
                    nextRecord = null;
                    return record;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }
}
