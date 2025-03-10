package agh.ics.oop.model.maps;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.enums.MovinType;
import agh.ics.oop.model.enums.MutationType;
import agh.ics.oop.model.mapElements.*;
import agh.ics.oop.model.util.*;
import agh.ics.oop.simulation.SimulationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {

    private AbstractWorldMap worldMap;
    private agh.ics.oop.model.mapElements.Animal animal;
    private Vector2d position;
    private SimulationProperties simulationProperties;

    @BeforeEach
    void setUp() {
        simulationProperties = new SimulationProperties(10, 5, 100, 20, 5, 50, 30,20,50, MovinType.DEFAULT, MutationType.FULLRANDOM, MapType.GLOBE,5,25,25,2,1,5,"false");
        worldMap = new GrassField(simulationProperties);  // Using a concrete subclass or implementation
        position = new Vector2d(5, 5);
        animal = new Animal(position, simulationProperties);  // A simple Animal with energy and no genome for testing
    }

    @Test
    void testPlaceAnimal() {
        worldMap.place(position, animal);
        assertTrue(worldMap.isOccupied(position));  // Check if the position is occupied
    }

    @Test
    void testMoveAnimal() {
        worldMap.place(position, animal);
        worldMap.move(animal);  // Moving the animal
        assertNotEquals(position, animal.getPosition());  // Ensure position has been updated
    }

    @Test
    void testIsOccupied() {
        worldMap.place(position, animal);
        assertTrue(worldMap.isOccupied(position));  // Should return true as animal is placed at position

        Vector2d emptyPosition = new Vector2d(6, 6);
        assertFalse(worldMap.isOccupied(emptyPosition));  // Should return false as the position is empty
    }

    @Test
    void testObjectAt() {
        worldMap.place(position, animal);
        Optional<WorldElement> elementAtPosition = worldMap.objectAt(position);
        assertTrue(elementAtPosition.isPresent());
        assertEquals(animal, elementAtPosition.get());
    }
    @Test
    void testGetElements() {
        worldMap.place(position, animal);
        List<WorldElement> elements = worldMap.getElements();
        assertEquals(1, elements.size());  // Should contain exactly 1 animal
        assertTrue(elements.contains(animal));
    }

    @Test
    void testRemoveAnimal() {
        worldMap.place(position, animal);
        worldMap.removeAnimal(animal);
        assertFalse(worldMap.isOccupied(position));  // After removal, the position should be empty
    }

    @Test
    void testPlaceGrass() {
        Vector2d grassPosition = new Vector2d(4, 4);
        Grass grass = new Grass(grassPosition);
        worldMap.placeGrass(grassPosition, grass);
        assertTrue(worldMap.getPlants().containsKey(grassPosition));  // Ensure the grass was placed
    }

    @Test
    void testGetEmptyCount() {
        worldMap.place(position, animal);
        assertEquals(49, worldMap.getEmptyCount());  // 100 total positions, 1 occupied by the animal
    }
}
