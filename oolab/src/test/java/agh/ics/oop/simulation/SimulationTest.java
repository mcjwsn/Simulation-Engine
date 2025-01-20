package agh.ics.oop.simulation;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.enums.MovinType;
import agh.ics.oop.model.enums.MutationType;
import agh.ics.oop.model.mapElements.WorldElement;
import agh.ics.oop.model.maps.GrassField;
import agh.ics.oop.model.util.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    private GrassField grassField;
    private SimulationProperties simulationProperties;

    @BeforeEach
    void setUp() {
        simulationProperties = new SimulationProperties(10, 5, 100, 20, 5, 50, 30,20,50, MovinType.DEFAULT, MutationType.FULLRANDOM, MapType.GLOBE,5,25,25,2,1,5,"false");
        simulationProperties.setMapHeight(5); // Wysokość mapy
        simulationProperties.setMapWidth(5);  // Szerokość mapy
        simulationProperties.setGrassNumber(10); // Liczba traw
        grassField = new GrassField(simulationProperties);
    }

    @Test
    void testObjectAtWithNoGrass() {
        Vector2d position = new Vector2d(0, 0);

        Optional<WorldElement> elementAtPosition = grassField.objectAt(position);

        assertFalse(elementAtPosition.isPresent());
    }

    @Test
    void testIsOccupiedWithGrass() {
        // Test sprawdzający, czy pole nie jest zajęte przez trawę, nie blokuje
        Vector2d position = new Vector2d(3, 3);

        assertFalse(grassField.isOccupied(position));
    }

    @Test
    void testIsOccupiedWithAnimal() {
        Vector2d position = new Vector2d(1, 1);

        assertFalse(grassField.isOccupied(position));
    }

    @Test
    void testGetElements() {
        assertFalse(grassField.getElements().size() > 0);
    }

    @Test
    void testGetMapType() {
        // Test sprawdzający typ mapy
        assertEquals(MapType.GLOBE, grassField.getMapType());
    }

}