package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.enums.MovinType;
import agh.ics.oop.model.enums.MutationType;
import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.simulation.SimulationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private SimulationProperties simulationProperties;
    private Vector2d position;
    private Animal animal;

    @BeforeEach
    void setUp() {
        simulationProperties = new SimulationProperties(10, 5, 100, 20, 5, 50, 30,20,50, MovinType.DEFAULT, MutationType.FULLRANDOM, MapType.GLOBE,5,25,25,2,1,5,"false"); // Example values for simulation properties
        position = new Vector2d(2, 3);
        animal = new Animal(position, simulationProperties);
    }

    @Test
    void testAnimalConstructor() {
        // Test initial state of the animal
        assertNotNull(animal.getPosition());
        assertEquals(position, animal.getPosition());
        assertNotNull(animal.getGenome());
        assertEquals(simulationProperties.getStartEnergy(), animal.getEnergy());
        assertEquals(0, animal.getAge());
        assertEquals(0, animal.getPlantsEaten());
        assertEquals(0, animal.getChildrenMade());
    }

    @Test
    void testEat() {
        int initialEnergy = animal.getEnergy();
        int grassEnergy = 20;

        animal.eat(grassEnergy);

        assertTrue(animal.getEnergy() > initialEnergy);
        assertTrue(animal.getPlantsEaten() > 0);
    }

    @Test
    void testEatWithMaxEnergy() {
        int maxEnergy = simulationProperties.getMaxEnergy();
        animal.setEnergy(maxEnergy); // Set animal to maximum energy

        animal.eat(20); // Attempt to eat

        assertEquals(maxEnergy, animal.getEnergy()); // Should remain at max energy
    }
    @Test
    void testAddChild() {
        Animal child = new Animal(new Vector2d(1, 1), simulationProperties);
        animal.addChildToList(child);

        assertEquals(1, animal.getChildren().size()); // The animal should have one child
        assertEquals(child, animal.getChildren().get(0)); // The child should be the one added
    }

    @Test
    void testIncreaseChildrenNumber() {
        animal.increaseChildrenNumber();

        assertEquals(1, animal.getChildrenMade()); // The number of children made should increase by 1
    }

    @Test
    void testGetEnergyLevelResource() {
        animal.setEnergy(30); // Set energy to a test value
        String energyResource = animal.getEnergyLevelResource();

        assertNotNull(energyResource); // There should be a resource string for the energy level
    }

    @Test
    void testAgeIncrease() {
        int initialAge = animal.getAge();
        animal.addAge();

        assertEquals(initialAge + 1, animal.getAge()); // Age should increase by 1
    }

    @Test
    void testDeathDate() {
        int deathDate = 100;
        animal.setDeathDate(deathDate);

        assertEquals(deathDate, animal.getDeathDate()); // The death date should match the one set
    }
}