package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.enums.*;
import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.model.maps.OwlBearMap;
import agh.ics.oop.simulation.SimulationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OwlBearTest {
    private SimulationProperties simulationProperties;
    private Vector2d position;
    private OwlBear owlBear;
    private OwlBearMap map;

    @BeforeEach
    void setUp() {
        simulationProperties = new SimulationProperties(10, 5, 100, 20, 5, 50, 30,20,50, MovinType.DEFAULT, MutationType.FULLRANDOM, MapType.GLOBE,5,25,25,2,1,5,"false");
        position = new Vector2d(2, 2);
        owlBear = new OwlBear(position, simulationProperties);
        map = new OwlBearMap(simulationProperties);  // Assuming OwlBearMap is an implementation of the map
    }

    @Test
    void testConstructor() {
        assertNotNull(owlBear); // Ensure the OwlBear object is correctly initialized
        assertEquals(position, owlBear.getPosition()); // Position should match the one passed in constructor
        assertEquals(MapDirection.NORTH, owlBear.getOrientation()); // The initial orientation should be NORTH
    }

    @Test
    void testGetPosition() {
        assertEquals(position, owlBear.getPosition()); // Ensure the position returned is correct
    }

    @Test
    void testIsAt() {
        assertTrue(owlBear.isAt(position)); // Should return true for the current position
        assertFalse(owlBear.isAt(new Vector2d(3, 3))); // Should return false for a different position
    }

    @Test
    void testGetType() {
        assertEquals(ElementType.OWLBEAR, owlBear.getType()); // Ensure the type returned is OWLBEAR
    }

    @Test
    void testToString() {
        assertEquals("O", owlBear.toString()); // Ensure the toString method returns "O"
    }

    @Test
    void testGetImageResource() {
        String expectedImageResource = "owlbear.png";
        assertEquals(expectedImageResource, owlBear.getImageResource()); // Ensure the image resource is correct
    }

}