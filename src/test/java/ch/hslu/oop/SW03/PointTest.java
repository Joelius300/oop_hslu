package ch.hslu.oop.SW03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    @Test
    void getQuadrantUpperRight() throws PointLiesOnAxisException {
        // Arrange
        final Point point = new Point(10, 10);
        final Point.Quadrant expected = Point.Quadrant.UPPER_RIGHT;

        // Act
        final Point.Quadrant actual = point.getQuadrant();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getQuadrantUpperLeft() throws PointLiesOnAxisException {
        // Arrange
        final Point point = new Point(-10, 10);
        final Point.Quadrant expected = Point.Quadrant.UPPER_LEFT;

        // Act
        final Point.Quadrant actual = point.getQuadrant();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getQuadrantLowerRight() throws PointLiesOnAxisException {
        // Arrange
        final Point point = new Point(10, -10);
        final Point.Quadrant expected = Point.Quadrant.LOWER_RIGHT;

        // Act
        final Point.Quadrant actual = point.getQuadrant();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getQuadrantLowerLeft() throws PointLiesOnAxisException {
        // Arrange
        final Point point = new Point(-10, -10);
        final Point.Quadrant expected = Point.Quadrant.LOWER_LEFT;

        // Act
        final Point.Quadrant actual = point.getQuadrant();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getQuadrantOnYAxisThrows() {
        // Arrange
        final Point point = new Point(0, 10);

        // Assert
        assertThrows(PointLiesOnAxisException.class, point::getQuadrant);
    }

    @Test
    void getQuadrantOnXAxisThrows() {
        // Arrange
        final Point point = new Point(10, 0);

        // Assert
        assertThrows(PointLiesOnAxisException.class, point::getQuadrant);
    }

    @Test
    void getQuadrantOnOriginThrows() {
        // Arrange
        final Point point = new Point(0, 0);

        // Assert
        assertThrows(PointLiesOnAxisException.class, point::getQuadrant);
    }
}