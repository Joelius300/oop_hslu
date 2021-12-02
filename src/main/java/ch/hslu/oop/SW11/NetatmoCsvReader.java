package ch.hslu.oop.SW11;

import java.io.BufferedReader;
import java.io.Closeable;
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

// This whole class could probably be replaced by using a csv library with the NetatmoMeasurementRecord model.
public class NetatmoCsvReader implements Closeable {
    private static final String TIMESTAMP_FORMAT = "\"yyyy/MM/dd HH:mm:ss\"";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);

    private final BufferedReader reader;

    public NetatmoCsvReader(BufferedReader reader) {
        if (reader == null)
            throw new IllegalArgumentException("reader cannot be null");

        this.reader = reader;
    }

    public NetatmoMeasurementRecord parseLine() throws IOException {
        String line = reader.readLine();
        if (line == null)
            return null;

        return parseLine(line);
    }

    public Stream<NetatmoMeasurementRecord> parseStream() {
        // Inspired by BufferedReader.lines(), and C# IEnumerable if it didn't have yield..
        Iterator<NetatmoMeasurementRecord> iterator = new Iterator<>() {
            private NetatmoMeasurementRecord nextRecord = null;

            @Override
            public boolean hasNext() {
                if (nextRecord != null)
                    return true;

                try {
                    nextRecord = parseLine();
                    return nextRecord != null;
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                } catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("Error parsing stream: " + ex.getMessage(), ex);
                }
            }

            @Override
            public NetatmoMeasurementRecord next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                NetatmoMeasurementRecord record = nextRecord;
                nextRecord = null; // make sure new record is fetched next time
                return record;
            }
        };

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    private static NetatmoMeasurementRecord parseLine(String line) {
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
}
