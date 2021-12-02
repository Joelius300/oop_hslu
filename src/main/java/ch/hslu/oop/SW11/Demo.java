package ch.hslu.oop.SW11;

import ch.hslu.oop.temperature.Temperature;
import ch.hslu.oop.temperature.TemperatureHistory;
import ch.hslu.oop.temperature.TemperatureRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collector;

public class Demo {
    public static void main(String[] args) throws IOException {
        testMultithreadingHistoryAddition();
    }

    // Cool cool, it does seem to be threadsafe
    private static void testMultithreadingHistoryAddition() {
        final TemperatureHistory history = new TemperatureHistory();

        ExecutorService service = Executors.newFixedThreadPool(3);
        try {
            List<Callable<Object>> todos = new ArrayList<>();
            todos.add(Executors.callable(() -> {
                for (int j = 1; j <= 1000; j++) {
                    threadTestBody(history, j);
                }
            }));

            todos.add(Executors.callable(() -> {
                for (int j = 1001; j <= 2000; j++) {
                    threadTestBody(history, j);
                }
            }));

            todos.add(Executors.callable(() -> {
                for (int j = 1; j <= 100; j++) {
                    System.out.printf("Min %s, Max %s, Avg %s%n", history.getMinimum(), history.getMinimum(), history.getAverage());
                    try {
                        Thread.sleep((int)(Math.random() * 10) + 1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));

            service.invokeAll(todos); // waits for all to complete
        } catch (InterruptedException e) {
            System.err.println("Threads were interrupted.");
        } finally {
            service.shutdown();
        }

        System.out.println(history);
    }

    private static void threadTestBody(TemperatureHistory history, int j) {
        history.add(new TemperatureRecord(LocalDateTime.now(), Temperature.fromCelsius(j)));
        if (j % 10 == 0) {
            try {
                Thread.sleep((int)(Math.random() * 10) + 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void readAndParseCsvIntoHistory() throws IOException {
        // will be closed when the reader is closed
        InputStream csvStream = Demo.class.getClassLoader().getResourceAsStream("netatmo-export-201801-201804.csv");
        if (csvStream == null) {
            System.err.println("boooi the resource isn't there");
            return;
        }

        try (NetatmoCsvReader csvReader = new NetatmoCsvReader(new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8)))) {
            TemperatureHistory history = csvReader.parseStream()
                    //.parallel() see below why this doesn't make sense. I'm learning so much today :)
                    .map(Demo::convertToTemperatureRecord)
                    // .collect(Collectors.toList()) would get you a list, then you can one-by-one add to history but that would be lame :)
                    // .forEach(history::add) this would work too, and I think as long as the csvReaders stream can't be parallelized, it's just as good as the collector below
                    .collect(Collector.of(TemperatureHistory::new, TemperatureHistory::add,
                            // this would be used if the collecting is done in batches so parallel but
                            // AFAIK this won't happen because the stream I return in parseStream isn't parallel.
                            // I can force it to be parallel by calling .parallel() but this won't speed it up because
                            // the file can't be read faster (or in parallel). One working solution is:
                            // (left, right) -> { right.stream().forEach(left::add); return left; })
                            (left, right) -> {
                                throw new RuntimeException("look man, I'm sorry but it doesn't make sense you're callin me.");
                            }));

            System.out.println(history);
        }
    }

    private static TemperatureRecord convertToTemperatureRecord(NetatmoMeasurementRecord measurementRecord) {
        return new TemperatureRecord(measurementRecord.timestamp(), Temperature.fromCelsius(measurementRecord.temperature()));
    }

    private static void oneA() throws IOException {
        File file = File.createTempFile("reeee", ".txt");
        System.out.println(file.getAbsolutePath());

        try (DataOutputStream output = new DataOutputStream(Files.newOutputStream(file.toPath()))) {
            output.writeInt(7);
            output.writeInt(100);
            output.writeInt(99);
            output.writeChar('F');
            output.writeUTF("is this different? ඞ\n");
            output.writeUTF("no? ඞ");
        }

        try (DataInputStream input = new DataInputStream(Files.newInputStream(file.toPath()))) {
            System.out.println(input.readInt());
            System.out.println(input.readInt());
            System.out.println(input.readInt());
            System.out.println(input.readChar());
            System.out.println(input.readUTF());
            System.out.println(input.readUTF());
        }

        Files.deleteIfExists(file.toPath());
    }
}
