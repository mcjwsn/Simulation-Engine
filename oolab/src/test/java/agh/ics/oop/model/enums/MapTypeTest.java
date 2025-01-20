package agh.ics.oop.model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTypeTest {

    @Test
    void testToString() {
        assertEquals("GLOBE", MapType.GLOBE.toString());
        assertEquals("OWLBEAR", MapType.OWLBEAR.toString());
    }

    @Test
    void testEnumValues() {
        MapType[] expectedValues = {MapType.GLOBE, MapType.OWLBEAR};
        assertArrayEquals(expectedValues, MapType.values());
    }

    @Test
    void testValueOf() {
        assertEquals(MapType.GLOBE, MapType.valueOf("GLOBE"));
        assertEquals(MapType.OWLBEAR, MapType.valueOf("OWLBEAR"));
    }
}