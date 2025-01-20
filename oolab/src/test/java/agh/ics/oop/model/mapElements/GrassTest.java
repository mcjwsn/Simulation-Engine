package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.enums.ElementType;
import agh.ics.oop.model.util.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassTest {
    private Vector2d position;
    private Grass grass;

    @BeforeEach
    void setUp() {
        position = new Vector2d(3, 4);  // Example position for the grass
        grass = new Grass(position);
    }

    @Test
    void testConstructor() {
        assertNotNull(grass); // Ensure the grass object is correctly initialized
        assertEquals(position, grass.getPosition()); // Position should match the one passed in constructor
    }

    @Test
    void testGetPosition() {
        assertEquals(position, grass.getPosition()); // Ensure the position returned is correct
    }

    @Test
    void testToString() {
        assertEquals("*", grass.toString()); // Ensure the toString method returns a "*"
    }

    @Test
    void testGetImageResource() {
        String expectedImageResource = "grassmix.jpg";
        assertEquals(expectedImageResource, grass.getImageResource()); // Ensure the image resource returned is correct
    }

    @Test
    void testGetType() {
        assertEquals(ElementType.GRASS, grass.getType()); // Ensure the type returned is GRASS
    }

}