package ch.hslu.oop.temperature;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
Unit-Tests for Temperature as a replacement for implementing Calculator. If Calculator will be needed in the future,
I can still implement it.
 */
class TemperatureTest {
    private static final float BOILING_KELVIN = 373.15f;
    private static final float BOILING_CELSIUS = 100f;
    private static final float BOILING_FAHRENHEIT = 212f;

    @Test
    void getCelsiusFromKelvin() {
        // Arrange
        final Temperature temp = Temperature.fromKelvin(BOILING_KELVIN);
        final float expected = BOILING_CELSIUS;

        // Act
        final float actual = temp.getCelsius();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getCelsiusFromFahrenheit() {
        // Arrange
        final Temperature temp = Temperature.fromFahrenheit(BOILING_FAHRENHEIT);
        final float expected = BOILING_CELSIUS;

        // Act
        final float actual = temp.getCelsius();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getKelvinFromCelsius() {
        // Arrange
        final Temperature temp = Temperature.fromCelsius(BOILING_CELSIUS);
        final float expected = BOILING_KELVIN;

        // Act
        final float actual = temp.getKelvin();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getKelvinFromFahrenheit() {
        // Arrange
        final Temperature temp = Temperature.fromFahrenheit(BOILING_FAHRENHEIT);
        final float expected = BOILING_KELVIN;

        // Act
        final float actual = temp.getKelvin();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getFahrenheitFromKelvin() {
        // Arrange
        final Temperature temp = Temperature.fromKelvin(BOILING_KELVIN);
        final float expected = BOILING_FAHRENHEIT;

        // Act
        final float actual = temp.getFahrenheit();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getFahrenheitFromCelsius() {
        // Arrange
        final Temperature temp = Temperature.fromCelsius(BOILING_CELSIUS);
        final float expected = BOILING_FAHRENHEIT;

        // Act
        final float actual = temp.getFahrenheit();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void updateKelvin() {
        // Arrange
        final Temperature temp = Temperature.fromKelvin(BOILING_KELVIN);
        final float difference = 5;

        // Act
        final Temperature newValue = temp.updateKelvin(difference);

        // Assert
        assertEquals(Temperature.fromKelvin(BOILING_KELVIN + difference), newValue);
    }

    @Test
    void updateCelsius() {
        // Arrange
        final Temperature temp = Temperature.fromCelsius(BOILING_CELSIUS);
        final float difference = 5;

        // Act
        final Temperature newValue = temp.updateCelsius(difference);

        // Assert
        assertEquals(Temperature.fromCelsius(BOILING_CELSIUS + difference), newValue);
    }

    @Test
    void updateFahrenheit() {
        // Arrange
        final Temperature temp = Temperature.fromFahrenheit(BOILING_FAHRENHEIT);
        final float difference = 5;

        // Act
        final Temperature newValue = temp.updateFahrenheit(difference);

        // Assert
        assertEquals(Temperature.fromFahrenheit(BOILING_FAHRENHEIT + difference), newValue);
    }

    @Test
    void fromKelvin() {
        // Arrange
        final float value = BOILING_KELVIN;

        // Act
        final Temperature temp = Temperature.fromKelvin(value);

        // Assert
        assertEquals(value, temp.getKelvin());
    }

    @Test
    void fromCelsius() {
        // Arrange
        final float value = BOILING_CELSIUS;

        // Act
        final Temperature temp = Temperature.fromCelsius(value);

        // Assert
        assertEquals(value, temp.getCelsius());
    }

    @Test
    void fromFahrenheit() {
        // Arrange
        final float value = BOILING_FAHRENHEIT;

        // Act
        final Temperature temp = Temperature.fromFahrenheit(value);

        // Assert
        assertEquals(value, temp.getFahrenheit());
    }

    @Test
    void differentUnitsEqualObjects() {
        // Act
        final Temperature kelvin = Temperature.fromKelvin(BOILING_KELVIN);
        final Temperature celsius = Temperature.fromCelsius(BOILING_CELSIUS);
        final Temperature fahrenheit = Temperature.fromFahrenheit(BOILING_FAHRENHEIT);

        // Assert
        assertEquals(kelvin, celsius);
        assertEquals(celsius, fahrenheit);
        assertEquals(fahrenheit, kelvin);
    }

    @Test
    void differentUnitsEqualHashes() {
        // Act
        final Temperature kelvin = Temperature.fromKelvin(BOILING_KELVIN);
        final Temperature celsius = Temperature.fromCelsius(BOILING_CELSIUS);
        final Temperature fahrenheit = Temperature.fromFahrenheit(BOILING_FAHRENHEIT);

        // Assert
        assertEquals(kelvin.hashCode(), celsius.hashCode());
        assertEquals(celsius.hashCode(), fahrenheit.hashCode());
        assertEquals(fahrenheit.hashCode(), kelvin.hashCode());
    }

    @Test
    void compareToLessThan() {
        // Arrange
        Temperature a = Temperature.fromKelvin(9);
        Temperature b = Temperature.fromKelvin(10);

        // Act
        int actual = a.compareTo(b);

        // Assert
        assertTrue(actual < 0);
    }

    @Test
    void compareToGreaterThan() {
        // Arrange
        Temperature a = Temperature.fromFahrenheit(100);
        Temperature b = Temperature.fromFahrenheit(50);

        // Act
        int actual = a.compareTo(b);

        // Assert
        assertTrue(actual > 0);
    }

    @Test
    void compareToEqual() {
        // Arrange
        Temperature a = Temperature.fromKelvin(0);
        Temperature b = Temperature.fromKelvin(0);

        // Act
        int actual = a.compareTo(b);

        // Assert
        assertEquals(0, actual);
    }

    @Test
    void belowAbsoluteZeroThrowsIllegalArgument() {
        // Arrange
        final float belowZeroKelvin = -0.1f;
        final float belowZeroCelsius = -274f;
        final float belowZeroFahrenheit = -1000f;

        // Act & Assert
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Temperature.fromKelvin(belowZeroKelvin)),
                () -> assertThrows(IllegalArgumentException.class, () -> Temperature.fromCelsius(belowZeroCelsius)),
                () -> assertThrows(IllegalArgumentException.class, () -> Temperature.fromFahrenheit(belowZeroFahrenheit))
        );
    }

    @Test
    void temperaturesEqualsContract() {
        EqualsVerifier.forClass(Temperature.class).verify();
    }
}