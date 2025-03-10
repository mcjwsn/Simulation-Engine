package agh.ics.oop.model.enums;

import agh.ics.oop.model.util.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void testToString() {
        assertEquals("^", MapDirection.NORTH.toString());
        assertEquals(">", MapDirection.EAST.toString());
        assertEquals("v", MapDirection.SOUTH.toString());
        assertEquals("<", MapDirection.WEST.toString());
        assertEquals("↗", MapDirection.NORTHEAST.toString());
        assertEquals("↘", MapDirection.SOUTHEAST.toString());
        assertEquals("↙", MapDirection.SOUTHWEST.toString());
        assertEquals("↖", MapDirection.NORTHWEST.toString());
    }

    @Test
    void testNext() {
        assertEquals(MapDirection.NORTHEAST, MapDirection.NORTH.next());
        assertEquals(MapDirection.EAST, MapDirection.NORTHEAST.next());
        assertEquals(MapDirection.SOUTHEAST, MapDirection.EAST.next());
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTHEAST.next());
        assertEquals(MapDirection.SOUTHWEST, MapDirection.SOUTH.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTHWEST.next());
        assertEquals(MapDirection.NORTHWEST, MapDirection.WEST.next());
        assertEquals(MapDirection.NORTH, MapDirection.NORTHWEST.next());
    }

    @Test
    void testToUnitVector() {
        assertEquals(new Vector2d(0, 1), MapDirection.NORTH.toUnitVector());
        assertEquals(new Vector2d(1, 1), MapDirection.NORTHEAST.toUnitVector());
        assertEquals(new Vector2d(1, 0), MapDirection.EAST.toUnitVector());
        assertEquals(new Vector2d(1, -1), MapDirection.SOUTHEAST.toUnitVector());
        assertEquals(new Vector2d(0, -1), MapDirection.SOUTH.toUnitVector());
        assertEquals(new Vector2d(-1, -1), MapDirection.SOUTHWEST.toUnitVector());
        assertEquals(new Vector2d(-1, 0), MapDirection.WEST.toUnitVector());
        assertEquals(new Vector2d(-1, 1), MapDirection.NORTHWEST.toUnitVector());
    }

    @Test
    void testRotate() {
        assertEquals(MapDirection.NORTHEAST, MapDirection.NORTH.rotate(1));
        assertEquals(MapDirection.EAST, MapDirection.NORTH.rotate(2));
        assertEquals(MapDirection.SOUTH, MapDirection.NORTH.rotate(4));
        assertEquals(MapDirection.NORTHWEST, MapDirection.NORTH.rotate(7));
        assertEquals(MapDirection.NORTH, MapDirection.NORTH.rotate(8));
        assertEquals(MapDirection.SOUTHWEST, MapDirection.SOUTH.rotate(2));
        assertEquals(MapDirection.WEST, MapDirection.SOUTHWEST.rotate(1));
    }
}
