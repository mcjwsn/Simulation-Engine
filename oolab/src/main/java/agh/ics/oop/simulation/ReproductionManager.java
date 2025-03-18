package agh.ics.oop.simulation;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.util.Genes;
import agh.ics.oop.model.util.Vector2d;

import java.util.List;

public class ReproductionManager {
    private final AbstractWorldMap map;
    private final SimulationProperties simulationProperties;
    private final Simulation simulation;

    public ReproductionManager(AbstractWorldMap map, SimulationProperties simulationProperties, Simulation simulation) {
        this.map = map;
        this.simulationProperties = simulationProperties;
        this.simulation = simulation;
    }

    public void reproduceAnimals() {
        map.prepareForProcessing();
        for (Vector2d position : map.getAnimals().keySet()) {
            List<Animal> animalList = map.getAnimals().get(position);
            if (animalList.size() > 1) {
                Animal a1 = animalList.get(0);
                Animal a2 = animalList.get(1);

                if (canReproduce(a1, a2)) {
                    createOffspring(position, a1, a2);
                }
            }
        }
    }

    private boolean canReproduce(Animal a1, Animal a2) {
        return a1.getEnergy() > simulationProperties.getEnergyLevelNeededToReproduce() &&
                a2.getEnergy() > simulationProperties.getEnergyLevelNeededToReproduce();
    }

    private void createOffspring(Vector2d position, Animal parent1, Animal parent2) {
        int[] childGenome = Genes.mixGenesFromParents(parent1, parent2, simulationProperties);
        Animal child = Animal.createChild(position, simulationProperties, childGenome);

        synchronized (this) {
            map.getAnimals().get(position).add(child);
            simulation.addAnimal(child);

            transferEnergyAndRegisterChild(parent1, child);
            transferEnergyAndRegisterChild(parent2, child);
        }
    }

    private void transferEnergyAndRegisterChild(Animal parent, Animal child) {
        parent.removeEnergy(simulationProperties.getEnergyLevelToPassToChild());
        parent.addChildToList(simulation.getAnimals().get(simulation.getAnimals().indexOf(child)));
        parent.increaseChildrenNumber();
    }
}
