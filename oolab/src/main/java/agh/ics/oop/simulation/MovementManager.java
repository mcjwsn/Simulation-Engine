package agh.ics.oop.simulation;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.maps.AbstractWorldMap;

import java.util.List;

public class MovementManager {
    private final AbstractWorldMap map;
    private final SimulationProperties simulationProperties;

    public MovementManager(AbstractWorldMap map, SimulationProperties simulationProperties) {
        this.map = map;
        this.simulationProperties = simulationProperties;
    }

    public void moveAllAnimals(List<Animal> animals) {
        for (Animal animal : animals) {
            map.move(animal);
            animal.removeEnergy(simulationProperties.getMoveEnergy());
        }
    }
}