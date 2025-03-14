package agh.ics.oop.simulation;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.maps.OwlBearMap;
import agh.ics.oop.model.util.Vector2d;

import java.util.HashSet;
import java.util.Set;

public class OwlBearManager {
    private final AbstractWorldMap map;
    private final SimulationProperties simulationProperties;
    private final Simulation simulation;

    public OwlBearManager(AbstractWorldMap map, SimulationProperties simulationProperties, Simulation simulation) {
        this.map = map;
        this.simulationProperties = simulationProperties;
        this.simulation = simulation;
    }

    public void moveOwlBear() {
        if (map instanceof OwlBearMap) {
            ((OwlBearMap) map).moveOwlBear();
        }
    }

    public void owlBearEat() {
        if (map instanceof OwlBearMap) {
            Set<Animal> animalsToRemove = new HashSet<>(simulation.getAnimals());
            Vector2d owlBearPosition = ((OwlBearMap) map).getOwlBearPosition();

            for (Animal animal : animalsToRemove) {
                if (animal.getPosition().equals(owlBearPosition)) {
                    animal.setDeathDate(simulationProperties.getDaysElapsed());
                    map.removeAnimal(animal);
                    simulation.getAnimals().remove(animal);
                }
            }
        }
    }
}