package agh.ics.oop.model.maps;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.enums.MovinType;
import agh.ics.oop.model.enums.MutationType;
import agh.ics.oop.model.mapElements.Grass;
import agh.ics.oop.model.mapElements.WorldElement;
import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.simulation.SimulationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OwlBearMapTest {

    private OwlBearMap owlBearMap;
    private Vector2d owlBearPosition;
    private Vector2d grassPosition;
    private Grass grass;

    @BeforeEach
    void setUp() {
        // Initialize simulation properties with default values
        owlBearMap = new OwlBearMap(new SimulationProperties(10, 5, 100, 20, 5, 50, 30,20,50, MovinType.DEFAULT, MutationType.FULLRANDOM, MapType.GLOBE,5,25,25,2,1,5,"false"));

        // Set up positions for testing
        owlBearPosition = new Vector2d(5, 5);
        grassPosition = new Vector2d(4, 4);
        grass = new Grass(grassPosition);
    }

    @Test
    void testPlaceGrass() {
        // Place grass at a given position
        owlBearMap.placeGrass(grassPosition, grass);

        // Verify that the position is occupied by grass
        assertTrue(owlBearMap.isOccupied(grassPosition));
        assertNotNull(owlBearMap.objectAt(grassPosition).get());
    }

    @Test
    void testObjectAtGrass() {
        // Place the grass at a position
        owlBearMap.placeGrass(grassPosition, grass);

        // Ensure the object at the given position is the grass
        Optional<WorldElement> elementAtPosition = owlBearMap.objectAt(grassPosition);
        assertTrue(elementAtPosition.isPresent());
        assertEquals(grass, elementAtPosition.get());
    }

    @Test
    void testMoveOwlBear() {
        // Store the original position of the OwlBear
        Vector2d originalPosition = owlBearMap.getOwlBearPosition();

        // Move the OwlBear
        owlBearMap.moveOwlBear();

        // Check that the OwlBear's position has changed
        assertNotEquals(originalPosition, owlBearMap.getOwlBearPosition());
    }

    @Test
    void testGetMapType() {
        // Verify that the map type is OWLBEAR
        assertEquals(MapType.OWLBEAR, owlBearMap.getMapType());
    }

    @Test
    void testSubmapBoundsCalculation() {
        // Test submap bounds calculation
        int mapWidth = 10;
        int mapHeight = 10;
        owlBearMap.findSubmapBounds(mapWidth, mapHeight);

        assertNotNull(owlBearMap.getLowerLeftBoundary());
        assertNotNull(owlBearMap.getUpperRightBoundary());

        // Check if the upper and lower boundaries are within map limits
        Vector2d lowerLeft = owlBearMap.getLowerLeftBoundary();
        Vector2d upperRight = owlBearMap.getUpperRightBoundary();

        assertTrue(lowerLeft.getX() >= 0 && lowerLeft.getY() >= 0);
        assertTrue(upperRight.getX() < mapWidth && upperRight.getY() < mapHeight);
    }
}
