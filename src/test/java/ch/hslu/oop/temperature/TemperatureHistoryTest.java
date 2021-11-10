package ch.hslu.oop.temperature;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureHistoryTest {
    @Test
    void addUpdatesCount() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(Temperature.fromKelvin(0));

        // Assert
        assertEquals(1, history.getCount());
    }

    @Test
    void clearZeroesCount() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(Temperature.fromKelvin(0));
        history.clear();

        // Assert
        assertEquals(0, history.getCount());
    }

    @Test
    void getAverageWithEmptyHistory() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Assert
        assertNull(history.getAverage());
    }

    @Test
    void getMinimumWithEmptyHistory() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Assert
        assertNull(history.getMinimum());
    }

    @Test
    void getMaximumWithEmptyHistory() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Assert
        assertNull(history.getMaximum());
    }

    @Test
    void getAverageWithSingleElement() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(Temperature.fromKelvin(100));

        // Assert
        assertEquals(Temperature.fromKelvin(100), history.getAverage());
    }

    @Test
    void getMinimumWithSingleElement() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(Temperature.fromKelvin(100));

        // Assert
        assertEquals(Temperature.fromKelvin(100), history.getMinimum());
    }

    @Test
    void getMaximumWithSingleElement() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(Temperature.fromKelvin(100));

        // Assert
        assertEquals(Temperature.fromKelvin(100), history.getMaximum());
    }

    @Test
    void getAverageWithMultipleElements() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(Temperature.fromKelvin(75));
        history.add(Temperature.fromKelvin(100));
        history.add(Temperature.fromKelvin(200));

        // Assert
        assertEquals(Temperature.fromKelvin(125), history.getAverage());
    }

    @Test
    void getMinimumWithMultipleElements() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(Temperature.fromKelvin(75));
        history.add(Temperature.fromKelvin(100));
        history.add(Temperature.fromKelvin(200));

        // Assert
        assertEquals(Temperature.fromKelvin(75), history.getMinimum());
    }

    @Test
    void getMaximumWithMultipleElements() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(Temperature.fromKelvin(75));
        history.add(Temperature.fromKelvin(100));
        history.add(Temperature.fromKelvin(200));

        // Assert
        assertEquals(Temperature.fromKelvin(200), history.getMaximum());
    }
}