package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    // When został pominięty/złączony z Then, żeby nie tworzyć dodatkowych zmiennych, nie wszędzie sie da zrobić when i then
    // Given (dla wszystkich testów)
    Vector2d vector1 = new Vector2d(0,0);
    Vector2d vector2 = new Vector2d(-1,1);
    Vector2d vector3 = new Vector2d(-2147483647,2147483647);
    Vector2d vector4 = new Vector2d(20,-7);
    Vector2d vector5 = new Vector2d(5,5);
    Vector2d vector6 = new Vector2d(-5,-5);

    @Test
    void TakingMaxXandYFromTwoVectors2d() {
        //Given
        // Initializing vectors: vector1, vector6, vector5, vector3, and vector4
        //When & Then
        assertEquals(vector1, vector1.upperRight(vector6));
        assertEquals(vector5, vector2.upperRight(vector5));
        assertEquals(new Vector2d(20,2147483647), vector3.upperRight(vector4));
    }

    @Test
    void TakingMinXandYFromTwoVectors2d() {
        //Given
        // Initializing vectors: vector1, vector2, vector5, vector3, and vector4
        //When & Then
        assertEquals(vector1, vector1.lowerLeft(vector5));
        assertEquals(vector2, vector2.lowerLeft(vector5));
        assertEquals(new Vector2d(-2147483647,-7), vector3.lowerLeft(vector4));
    }


    @Test
    void CheckingIfBothCoordinatesAreEqualOrGreater() {
        //Given
        // Initializing vectors: vector2, vector6, vector5, vector3
        //When & Then
        assertFalse(vector5.precedes(vector6));
        assertTrue(vector2.precedes(vector5));
        assertTrue(vector3.precedes(vector3));
    }

    @Test
    void CheckingIfBothCoordinatesAreEqualOrSmaller() {
        //Given
        // Initializing vectors: vector2, vector6, vector5, vector3
        //When & Then
        assertTrue(vector5.follows(vector6));
        assertFalse(vector3.follows(vector2));
        assertTrue(vector6.follows(vector6));
    }

    @Test
    void CreatingStringFromVector2d(){
        //Given
        // Initializing vectors: vector1, vector3
        //When & Then
        assertEquals(vector1.toString(), "(0, 0)");
        assertEquals(vector3.toString(), "(-2147483647, 2147483647)");}

    @Test
    void SubtractingTwoVectors2d() {
        //Given
        // Initializing vectors: vector1, vector2, vector3, and vector4
        //When & Then
        assertEquals(vector3,vector3.subtract(vector1));
        assertEquals(new Vector2d(21,-8),vector4.subtract(vector2));
    }
    @Test
    void AddingTwoVectors2d() {
        //Given
        // Initializing vectors: vector1, vector2, vector3, and vector4
        //When & Then
        assertEquals(vector2,vector1.add(vector2));
        assertEquals(new Vector2d(-2147483627,2147483640),vector4.add(vector3));
    }
    @Test
    void CreatingOppositeVector2d() {
        //Given
        // Initializing vectors: vector1, vector2, vector3
        //When & Then
        assertEquals(new Vector2d(0,0), vector1.opposite());
        assertEquals(new Vector2d(1,-1), vector2.opposite());
        assertEquals(new Vector2d(2147483647,-2147483647), vector3.opposite());
    }
    @Test
    void CheckingIfTheyAreEqual() {
        //Given
        // Initializing vectors: vector1, vector6, vector5
        //When & Then
        assertNotEquals(vector1, vector6);
        assertEquals(vector5, vector6.opposite());
        assertEquals(vector1, vector5.add(vector6));
    }
}