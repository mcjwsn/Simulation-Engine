package agh.ics.oop.model.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2dTest {

    @Test
    void testConstructorAndToString() {
        // Create a vector with coordinates (3, 4)
        Vector2d vector = new Vector2d(3, 4);

        // Test the toString method
        assertEquals("(3, 4)", vector.toString());
    }

    @Test
    void testPrecedes() {
        Vector2d vector1 = new Vector2d(2, 3);
        Vector2d vector2 = new Vector2d(4, 5);

        // Test if vector1 precedes vector2
        assertTrue(vector1.precedes(vector2));

        // Test if vector2 does not precede vector1
        assertFalse(vector2.precedes(vector1));

        // Test if a vector precedes itself
        assertTrue(vector1.precedes(vector1));
    }

    @Test
    void testFollows() {
        Vector2d vector1 = new Vector2d(2, 3);
        Vector2d vector2 = new Vector2d(4, 5);

        // Test if vector2 follows vector1
        assertTrue(vector2.follows(vector1));

        // Test if vector1 does not follow vector2
        assertFalse(vector1.follows(vector2));

        // Test if a vector follows itself
        assertTrue(vector1.follows(vector1));
    }

    @Test
    void testAdd() {
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(3, 4);

        // Test adding two vectors
        Vector2d result = vector1.add(vector2);
        assertEquals(new Vector2d(4, 6), result);
    }

    @Test
    void testSubtract() {
        Vector2d vector1 = new Vector2d(5, 6);
        Vector2d vector2 = new Vector2d(2, 3);

        // Test subtracting two vectors
        Vector2d result = vector1.subtract(vector2);
        assertEquals(new Vector2d(3, 3), result);
    }

    @Test
    void testUpperRight() {
        Vector2d vector1 = new Vector2d(2, 4);
        Vector2d vector2 = new Vector2d(3, 2);

        // Test upperRight function (should give the maximum of each coordinate)
        Vector2d result = vector1.upperRight(vector2);
        assertEquals(new Vector2d(3, 4), result);
    }

    @Test
    void testLowerLeft() {
        Vector2d vector1 = new Vector2d(5, 6);
        Vector2d vector2 = new Vector2d(3, 7);

        // Test lowerLeft function (should give the minimum of each coordinate)
        Vector2d result = vector1.lowerLeft(vector2);
        assertEquals(new Vector2d(3, 6), result);
    }

    @Test
    void testOpposite() {
        Vector2d vector = new Vector2d(3, -4);

        // Test opposite function (should negate both coordinates)
        Vector2d result = vector.opposite();
        assertEquals(new Vector2d(-3, 4), result);
    }

    @Test
    void testEquals() {
        Vector2d vector1 = new Vector2d(2, 3);
        Vector2d vector2 = new Vector2d(2, 3);
        Vector2d vector3 = new Vector2d(3, 4);

        // Test if two vectors with the same coordinates are equal
        assertTrue(vector1.equals(vector2));

        // Test if vectors with different coordinates are not equal
        assertFalse(vector1.equals(vector3));

        // Test equality with null and different class type
        assertFalse(vector1.equals(null));
        assertFalse(vector1.equals("Not a vector"));
    }

    @Test
    void testHashCode() {
        Vector2d vector1 = new Vector2d(2, 3);
        Vector2d vector2 = new Vector2d(2, 3);

        // Test if two vectors with the same coordinates have the same hash code
        assertEquals(vector1.hashCode(), vector2.hashCode());
    }

    @Test
    void testHashCodeDifferentVectors() {
        Vector2d vector1 = new Vector2d(2, 3);
        Vector2d vector2 = new Vector2d(3, 4);

        // Test if two vectors with different coordinates have different hash codes
        assertNotEquals(vector1.hashCode(), vector2.hashCode());
    }
}
