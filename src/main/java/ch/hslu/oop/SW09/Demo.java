package ch.hslu.oop.SW09;

import ch.hslu.oop.temperature.Temperature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Demo {
    private static final Logger LOG = LogManager.getLogger(Demo.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Bitte Temperatur in ° C eingeben ('exit' zum Beenden): ");
            String input = scanner.next();

            if (input.equals("exit"))
                break;

            Temperature temp;
            try {
                float value = Float.parseFloat(input);
                temp = Temperature.fromCelsius(value);
            } catch (NumberFormatException e) {
                LOG.error(String.format("'%s' isn't a valid number.", input), e);
                continue;
            } catch (IllegalArgumentException e) {
                LOG.error(e.getMessage(), e);
                continue;
            }

            System.out.println(temp);
        }

        LOG.info("Programm beendet.");
    }
}
