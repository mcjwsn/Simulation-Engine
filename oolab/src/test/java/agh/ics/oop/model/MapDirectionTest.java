package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {
    @Test
    void ChangeDirectionToNextOne(){
        assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
        assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
        assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
    }
    @Test
    void ChangeDirectionToPreviousOne(){
        assertEquals(MapDirection.WEST, MapDirection.NORTH.previous());
        assertEquals(MapDirection.NORTH, MapDirection.EAST.previous());
        assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous());
        assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous());
    }
    @Test
    void ChangingDirectionToString(){
        assertEquals("E", MapDirection.EAST.toString());
        assertEquals("S", MapDirection.SOUTH.toString());
        assertEquals("W", MapDirection.WEST.toString());
        assertEquals("N", MapDirection.NORTH.toString());
    }
    @Test
    void ChangingDirectionToUnitVector()
    {
        //Given
        Vector2d ExpectedNorth = new Vector2d(0,1);
        Vector2d ExpectedWest = new Vector2d(-1,0);
        Vector2d ExpectedSouth = new Vector2d(0,-1);
        Vector2d ExpectedEast = new Vector2d(1,0);
        //When
        Vector2d north = MapDirection.NORTH.toUnitVector();
        Vector2d south = MapDirection.SOUTH.toUnitVector();
        Vector2d west = MapDirection.WEST.toUnitVector();
        Vector2d east = MapDirection.EAST.toUnitVector();
        //Then
        assertEquals(ExpectedNorth, north);
        assertEquals(ExpectedSouth, south);
        assertEquals(ExpectedWest, west);
        assertEquals(ExpectedEast, east);
    }
}