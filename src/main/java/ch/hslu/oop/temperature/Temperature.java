package ch.hslu.oop.temperature;

import java.util.Objects;

public final class Temperature implements Comparable<Temperature> {
    private final float rawKelvinValue;

    private Temperature(float kelvin) {
        rawKelvinValue = guardValidTemperatureKelvin(kelvin);
    }

    public Temperature(Temperature temperature) {
        this(temperature.rawKelvinValue);
    }

    public float getKelvin() {
        return rawKelvinValue;
    }

    public float getCelsius() {
        return kelvinToCelsius(rawKelvinValue);
    }

    public float getFahrenheit() {
        return kelvinToFahrenheit(rawKelvinValue);
    }

    public Temperature updateKelvin(float kelvinRelative) {
        return Temperature.fromKelvin(getKelvin() + kelvinRelative);
    }

    public Temperature updateCelsius(float celsiusRelative) {
        return Temperature.fromCelsius(getCelsius() + celsiusRelative);
    }

    public Temperature updateFahrenheit(float fahrenheitRelative) {
        return Temperature.fromFahrenheit(getFahrenheit() + fahrenheitRelative);
    }

    private static float guardValidTemperatureKelvin(float kelvin) {
        if (kelvin < 0) {
            throw new IllegalArgumentException("Temperature value was below absolute zero (0K).");
        }

        return kelvin;
    }

    private static float kelvinToCelsius(float kelvin) {
        return kelvin - 273.15f;
    }

    private static float celsiusToKelvin(float celsius) {
        return celsius + 273.15f;
    }

    private static float kelvinToFahrenheit(float kelvin) {
        return kelvinToCelsius(kelvin) * 1.8f + 32;
    }

    private static float fahrenheitToKelvin(float fahrenheit) {
        return celsiusToKelvin((fahrenheit - 32) / 1.8f);
    }

    /**
     * Creates a new {@link Temperature} from a raw kelvin value.
     * @param kelvin Temperature value in kelvin. Cannot be below zero.
     * @return A new {@link Temperature} object representing the given temperature.
     */
    public static Temperature fromKelvin(float kelvin) {
        return new Temperature(kelvin);
    }

    /**
     * Creates a new {@link Temperature} from a raw celsius value.
     * @param celsius Temperature value in celsius. Cannot be below absolute zero.
     * @return A new {@link Temperature} object representing the given temperature.
     */
    public static Temperature fromCelsius(float celsius) {
        return new Temperature(celsiusToKelvin(celsius));
    }

    /**
     * Creates a new {@link Temperature} from a raw fahrenheit value.
     * @param fahrenheit Temperature value in fahrenheit. Cannot be below absolute zero.
     * @return A new {@link Temperature} object representing the given temperature.
     */
    public static Temperature fromFahrenheit(float fahrenheit) {
        return new Temperature(fahrenheitToKelvin(fahrenheit));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Since Temperature is final, using instanceof should act the same as using getClass.
        // In general, instanceof is preferred by some people (e.g. EqualsVerifier: https://jqno.nl/equalsverifier/manual/instanceof-or-getclass/)
        // because of the Liskov substitution principle so if B is a subclass of A, it can
        // be used in place of A and it still works. However, accepting subtypes usually results
        // in noncompliance with the contract (see IntelliJ generation dialog).
        if (!(o instanceof Temperature that)) return false;
        return Float.compare(that.getKelvin(), getKelvin()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKelvin());
    }

    // Temperatures are basically just numbers with fixed semantics so they have the same natural order as the underlying number
    @Override
    public int compareTo(Temperature o) {
        return Float.compare(this.getKelvin(), o.getKelvin());
    }

    @Override
    public String toString() {
        return "Temperature {" + rawKelvinValue + " K}";
    }
}
