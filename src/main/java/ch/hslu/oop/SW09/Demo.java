package ch.hslu.oop.SW09;

import ch.hslu.oop.temperature.Temperature;

import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        main2(args);
    }

    public static void main1(String[] args) {
        String input;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Bitte Temperatur in ° C eingeben ('exit' zum Beenden): ");
            input = scanner.next();

            Temperature temp;
            try {
                float value = Float.parseFloat(input);
                temp = Temperature.fromCelsius(value);
                // This println shouldn't be inside the try block, we're not interested in exceptions thrown from it. To avoid we need to use continue.
                System.out.println(temp);
            } catch (NumberFormatException e) {
                // Here exit is also caught. To improve this, I'd probably use a while true loop with break to exit before the parsing.
                System.err.printf("'%s' isn't a valid number.%n", input);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        } while (!input.equals("exit"));
        System.out.println("Programm beendet.");
    }

    public static void main2(String[] args) {
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
                System.err.printf("'%s' isn't a valid number.%n", input);
                continue;
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                continue;
            }

            System.out.println(temp);
        }

        System.out.println("Programm beendet.");
    }
}
