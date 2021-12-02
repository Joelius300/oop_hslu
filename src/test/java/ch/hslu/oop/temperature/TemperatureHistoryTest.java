package ch.hslu.oop.temperature;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.time.LocalDateTime;

class TemperatureHistoryTest {
    private final LocalDateTime IRRELEVANT_DATETIME = LocalDateTime.MIN;

    @Test
    void addUpdatesCount() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(0)));

        // Assert
        assertEquals(1, history.getCount());
    }

    @Test
    void cannotAddNull() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> history.add(null));
    }

    @Test
    void clearZeroesCount() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(0)));
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
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(100)));

        // Assert
        assertEquals(Temperature.fromKelvin(100), history.getAverage());
    }

    @Test
    void getMinimumWithSingleElement() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(100)));

        // Assert
        assertEquals(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(100)), history.getMinimum());
    }

    @Test
    void getMaximumWithSingleElement() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(100)));

        // Assert
        assertEquals(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(100)), history.getMaximum());
    }

    @Test
    void getAverageWithMultipleElements() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(75)));
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(100)));
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(200)));

        // Assert
        assertEquals(Temperature.fromKelvin(125), history.getAverage());
    }

    @Test
    void getMinimumWithMultipleElements() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(75)));
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(100)));
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(200)));

        // Assert
        assertEquals(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(75)), history.getMinimum());
    }

    @Test
    void getMaximumWithMultipleElements() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(75)));
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(100)));
        history.add(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(200)));

        // Assert
        assertEquals(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(200)), history.getMaximum());
    }

    @Test
    void raiseMaximumExtremumEventForEmptyHistory() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();
        TemperatureExtremumEventListener listener = Mockito.mock(TemperatureExtremumEventListener.class);
        history.addExtremumEventListener(listener);

        // Act
        history.add(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(100)));

        // Assert
        Mockito.verify(listener).newExtremumReached(new TemperatureExtremumEvent(history,
                TemperatureExtremumEvent.ExtremumType.MAXIMUM,
                new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(100)),
                null));
    }

    @Test
    void raiseMinimumExtremumEventForEmptyHistory() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();
        TemperatureExtremumEventListener listener = Mockito.mock(TemperatureExtremumEventListener.class);
        history.addExtremumEventListener(listener);

        // Act
        history.add(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(100)));

        // Assert
        Mockito.verify(listener).newExtremumReached(new TemperatureExtremumEvent(history,
                TemperatureExtremumEvent.ExtremumType.MINIMUM,
                new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(100)),
                null));
    }

    @Test
    void raiseMaximumExtremumEventForNewMaximum() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(200)));
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(300)));
        history.add(new TemperatureRecord(LocalDateTime.of(5000, 1,1,4,20), Temperature.fromKelvin(400)));
        TemperatureExtremumEventListener listener = Mockito.mock(TemperatureExtremumEventListener.class);
        history.addExtremumEventListener(listener);

        // Act
        history.add(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(500)));

        // Assert
        Mockito.verify(listener).newExtremumReached(new TemperatureExtremumEvent(history,
                TemperatureExtremumEvent.ExtremumType.MAXIMUM,
                new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(500)),
                new TemperatureRecord(LocalDateTime.of(5000, 1,1,4,20), Temperature.fromKelvin(400))));
    }

    @Test
    void raiseMinimumExtremumEventForNewMinimum() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(400)));
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(300)));
        history.add(new TemperatureRecord(LocalDateTime.of(5000, 1,1,4,20), Temperature.fromKelvin(200)));
        TemperatureExtremumEventListener listener = Mockito.mock(TemperatureExtremumEventListener.class);
        history.addExtremumEventListener(listener);

        // Act
        history.add(new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(100)));

        // Assert
        Mockito.verify(listener).newExtremumReached(new TemperatureExtremumEvent(history,
                TemperatureExtremumEvent.ExtremumType.MINIMUM,
                new TemperatureRecord(LocalDateTime.of(2000, 1,1,4,20), Temperature.fromKelvin(100)),
                new TemperatureRecord(LocalDateTime.of(5000, 1,1,4,20), Temperature.fromKelvin(200))));
    }

    @Test
    void raisesEventsForConsecutiveNewExtrema() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();
        TemperatureExtremumEventListener listener = Mockito.mock(TemperatureExtremumEventListener.class);
        history.addExtremumEventListener(listener);

        // Act
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(200))); // max & min -> 2
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(300))); // max -> 3
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(400))); // max -> 4
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(250))); // nothing -> 4
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(100))); // min -> 5
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(0))); // min -> 6

        // Assert
        Mockito.verify(listener, Mockito.times(6)).newExtremumReached(Mockito.any());
    }

    @Test
    void dontRaiseEventForNonExtremum() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(100)));
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(1000)));
        TemperatureExtremumEventListener listener = Mockito.mock(TemperatureExtremumEventListener.class);
        history.addExtremumEventListener(listener);

        // Act
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(500)));

        // Assert
        Mockito.verify(listener, Mockito.never()).newExtremumReached(Mockito.any());
    }

    @Test
    void dontRaiseEventForSameValue() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(500)));
        TemperatureExtremumEventListener listener = Mockito.mock(TemperatureExtremumEventListener.class);
        history.addExtremumEventListener(listener);

        // Act
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(500)));

        // Assert
        Mockito.verify(listener, Mockito.never()).newExtremumReached(Mockito.any());
    }

    @Test
    void canRemoveListener() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(100)));
        TemperatureExtremumEventListener listener = Mockito.mock(TemperatureExtremumEventListener.class);
        history.addExtremumEventListener(listener);

        // Act
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(500))); // max -> 1
        history.removeExtremumEventListener(listener);
        history.add(new TemperatureRecord(IRRELEVANT_DATETIME, Temperature.fromKelvin(1000)));

        // Assert
        Mockito.verify(listener, Mockito.atMostOnce()).newExtremumReached(Mockito.any());
    }

    @Test
    void cannotAddNullListener() {
        // Arrange
        TemperatureHistory history = new TemperatureHistory();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> history.addExtremumEventListener(null));
    }

    /* Things that technically aren't tested but I'm unsure if they need to be:
     * - Multiple listeners
     * - Same listener added multiple times
     * - Remove null listeners, but it's tested that null listeners can't ever enter
     * - What happens on clear? Currently only non-null extrema ar reported so I don't think it should raise
     */
}