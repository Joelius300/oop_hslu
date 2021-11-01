package ch.hslu.oop.temperature;

import java.util.Objects;

public final class Temperature implements Comparable<Temperature> {
    private float rawKelvinValue;

    private Temperature(float kelvin) {
        setKelvin(kelvin);
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

    public void setKelvin(float kelvin) {
        rawKelvinValue = guardValidTemperatureKelvin(kelvin);
    }

    public void setCelsius(float celsius) {
        setKelvin(celsiusToKelvin(celsius));
    }

    public void setFahrenheit(float fahrenheit) {
        setKelvin(fahrenheitToKelvin(fahrenheit));
    }

    public float updateKelvin(float kelvinRelative) {
        setKelvin(getKelvin() + kelvinRelative);
        
        return getKelvin();
    }

    public float updateCelsius(float celsiusRelative) {
        setCelsius(getCelsius() + celsiusRelative);

        return getCelsius();
    }

    public float updateFahrenheit(float fahrenheitRelative) {
        setFahrenheit(getFahrenheit() + fahrenheitRelative);

        return getFahrenheit();
    }

    public String getPhase(String element) {
        /* Possible improvements:
            - Use enums instead of magic strings
            - Use lookup table/dictionary instead of switch
            - Don't have a method like this at all since it doesn't make sense this way around..
              It would be so much more logical to me to have an element class with a
              "getPhaseAt(Temperature temperature)" method. It makes sense to know for
              an element when it melts and when it boils but it doesn't make sense at all
              to know for a temperature when every element there is melts and boils, it's
              not its responsibility.
              If Java supported it you could also do an extension method on Temperature
              which takes an Element and gets the corresponding phase.
         */
        float meltingPoint;
        float boilingPoint;

        switch (element) {
            case "N" -> {
                meltingPoint = 63.05f;
                boilingPoint = 77.15f;
            }
            case "Hg" -> {
                meltingPoint = 234.32f;
                boilingPoint = 630.2f;
            }
            case "Pb" -> {
                meltingPoint = 600.61f;
                boilingPoint = 2017;
            }
            default -> throw new IllegalArgumentException(element + "isn't one of the allowed elements.");
        }

        float temp = getKelvin();
        if (temp < meltingPoint) {
            return "solid";
        }

        if (temp > boilingPoint) {
            return "gas";
        }

        return "liquid";
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

    @Override
    public int compareTo(Temperature o) {
        return Float.compare(this.getKelvin(), o.getKelvin());
    }
}
