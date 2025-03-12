package agh.ics.oop.simulation;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.maps.AbstractWorldMap;

import java.util.HashSet;
import java.util.Set;

public class AnimalLifecycleManager {
    private final AbstractWorldMap map;
    private final SimulationProperties simulationProperties;
    private final Simulation simulation;

    public AnimalLifecycleManager(AbstractWorldMap map, SimulationProperties simulationProperties, Simulation simulation) {
        this.map = map;
        this.simulationProperties = simulationProperties;
        this.simulation = simulation;
    }

    public void removeDeadAnimals() {
        Set<Animal> animalsToRemove = new HashSet<>(simulation.getAnimals());

        for (Animal animal : animalsToRemove) {
            if (animal.getEnergy() <= 0) {
                animal.setDeathDate(simulationProperties.getDaysElapsed());
                map.removeAnimal(animal);
                simulation.getAnimals().remove(animal);
            }
        }
    }

    public void incrementAge() {
        simulationProperties.incrementDaysElapsed();
        for (Animal animal : simulation.getAnimals()) {
            animal.addAge();
        }
    }
}
