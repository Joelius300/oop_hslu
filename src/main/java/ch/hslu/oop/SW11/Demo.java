package ch.hslu.oop.SW11;

import ch.hslu.oop.temperature.Temperature;
import ch.hslu.oop.temperature.TemperatureHistory;
import ch.hslu.oop.temperature.TemperatureRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collector;

public class Demo {
    public static void main(String[] args) throws IOException {
        // will be closed when the reader is closed
        InputStream csvStream = Demo.class.getClassLoader().getResourceAsStream("netatmo-export-201801-201804.csv");
        if (csvStream == null) {
            System.err.println("boooi the resource isn't there");
            return;
        }

        NetatmoCsvReader csvReader = new NetatmoCsvReader();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8))) {
            TemperatureHistory history = csvReader.parseStream(fileReader)
                    //.parallel() see below why this doesn't make sense. I'm learning so much today :)
                    .map(Demo::convertToTemperatureRecord)
                    // .collect(Collectors.toList()) then one-by-one add to history but that would be lame :)
                    .collect(Collector.of(TemperatureHistory::new, TemperatureHistory::add,
                            // this would be used if the collecting is done in batches so parallel but
                            // AFAIK this won't happen because the stream I return in parseStream isn't parallel.
                            // I can force it to be parallel by calling .parallel but this won't speed it up because
                            // the file can't be read faster (or in parallel). One working solution is:
                            // (left, right) -> { right.stream().forEach(left::add); return left; })
                            (left, right) -> { throw new RuntimeException("look man, I'm sorry but it doesn't make sense you're callin me."); }));

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
