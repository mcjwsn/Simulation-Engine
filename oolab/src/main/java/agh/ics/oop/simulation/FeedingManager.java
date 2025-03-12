package agh.ics.oop.simulation;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.Grass;
import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.util.Vector2d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeedingManager {
    private final AbstractWorldMap map;
    private final SimulationProperties simulationProperties;
    private final PositionManager positionManager;

    public FeedingManager(AbstractWorldMap map, SimulationProperties simulationProperties, PositionManager positionManager) {
        this.map = map;
        this.simulationProperties = simulationProperties;
        this.positionManager = positionManager;
    }

    public void feedAnimals() {
        Set<Vector2d> keys = new HashSet<>(map.getPlants().keySet());

        for (Vector2d position : keys) {
            if (map.getAnimals().containsKey(position)) {
                List<Animal> animalList = map.getAnimals().get(position);

                if (!animalList.isEmpty()) {
                    Animal animal = animalList.getFirst(); // Strongest animal eats first
                    synchronized (this) {
                        animal.eat(simulationProperties.getGrassEnergy());
                        Grass eatenGrass = map.getPlants().get(position);
                        map.getPlants().remove(position);
                        map.getFreePositionsForPlants().add(position);
                        positionManager.restoreEatenPlantPosition(eatenGrass);
                    }
                }
            }
        }
    }
}