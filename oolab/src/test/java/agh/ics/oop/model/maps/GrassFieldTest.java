package agh.ics.oop.model.maps;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.enums.MovinType;
import agh.ics.oop.model.enums.MutationType;
import agh.ics.oop.model.mapElements.Grass;
import agh.ics.oop.simulation.SimulationProperties;
import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.model.mapElements.WorldElement;
import agh.ics.oop.model.util.Boundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    private GrassField grassField;
    private Vector2d animalPosition;
    private Vector2d grassPosition;
    private Grass grass;

    @BeforeEach
    void setUp() {
        // Initialize simulation properties with default values
        //simulationProperties = new SimulationProperties(10, 5, 100, 20, 5, 50, 30,20,50, MovinType.DEFAULT, MutationType.FULLRANDOM, MapType.GLOBE,5,25,25,2,1,5,"false");
        grassField = new GrassField(new SimulationProperties(10, 5, 100, 20, 5, 50, 30,20,50, MovinType.DEFAULT, MutationType.FULLRANDOM, MapType.GLOBE,5,25,25,2,1,5,"false"));  // Use GrassField directly

        // Set up positions for testing
        animalPosition = new Vector2d(5, 5);
        grassPosition = new Vector2d(4, 4);
        grass = new Grass(grassPosition);
    }

    @Test
    void testPlaceGrass() {
        // Place the grass at the given position
        grassField.placeGrass(grassPosition, grass);

        // Check if grass is correctly placed
        assertTrue(grassField.isOccupied(grassPosition));  // Grass should occupy the position
        assertNotNull(grassField.objectAt(grassPosition)); // Grass should be found at the position
    }

    @Test
    void testObjectAtGrass() {
        // Place the grass at a position
        grassField.placeGrass(grassPosition, grass);

        // Ensure that the object at the position is grass
        Optional<WorldElement> elementAtPosition = grassField.objectAt(grassPosition);
        assertTrue(elementAtPosition.isPresent());
        assertEquals(grass, elementAtPosition.get());
    }

    @Test
    void testIsOccupiedWithGrass() {
        // Initially the position should not be occupied
        assertFalse(grassField.isOccupied(grassPosition));

        // Place grass at the position
        grassField.placeGrass(grassPosition, grass);

        // The position should now be occupied by grass
        assertTrue(grassField.isOccupied(grassPosition));
    }

    @Test
    void testGetMapType() {
        // Verify that the map type is GLOBE as defined in GrassField
        assertEquals(MapType.GLOBE, grassField.getMapType());
    }

    @Test
    void testGetElements() {
        // Add some grass to the map
        grassField.placeGrass(grassPosition, grass);

        // Check that the elements list contains the grass
        List<WorldElement> elements = grassField.getElements();
        assertTrue(elements.contains(grass));  // The elements should contain the grass
    }

    @Test
    void testGetElementsWhenNoGrass() {
        // When no grass is placed, only the animals should be in the elements list
        assertTrue(grassField.getElements().isEmpty());  // No elements should be in the map
    }

}
