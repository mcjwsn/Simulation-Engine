package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;



public class SimulationManager {
    private final AbstractWorldMap map;
    private final SimulationProperties simulationProperties;
    private final Simulation simulation;

    protected static final Random random = new Random();

    public SimulationManager (AbstractWorldMap map_, SimulationProperties simulationProperties_, Simulation simulation_) {
        map = map_;
        simulationProperties= simulationProperties_;
        simulation = simulation_;
    }

    // operacja podczas nowego dnia
    public void Update() {
        deleteDeadAnimals();
        moveALlAnimals();
        eat();
        reproduceAnimals();
        growGrass();
        addAge();

        map.mapChanged("Dzien sie zakonczyl");
    }

    private void deleteDeadAnimals() {
        Set<Animal> animalsToRemove = new HashSet<>(simulation.getAnimals());
        System.out.println("Checking " + animalsToRemove.size() + " animals for death");

        for(Animal animal : animalsToRemove) {
            if (animal.getEnergy() <= 0) {
                System.out.println("Animal at " + animal.getPosition() + " died with energy: " + animal.getEnergy());
                animal.setDeathDate(simulationProperties.getDaysElapsed());
                map.removeAnimal(animal);
                simulation.getAnimals().remove(animal);
                System.out.println("Remaining animals: " + simulation.getAnimals().size());
            }
        }
    }

    private void addAge() {
        simulationProperties.incrementDaysElapsed();
        for(Animal animal : simulation.getAnimals()){
            animal.addAge();
        }
    }

    private void moveALlAnimals() {
        for(Animal animal : simulation.getAnimals()){
            map.move(animal);
            animal.removeEnergy(simulationProperties.getMoveEnergy());
        }
    }

    public void reproduceAnimals() {
        for (Vector2d position : map.getAnimals().keySet()) {
            List<Animal> animalList = map.getAnimals().get(position);
            if (animalList.size() > 1) {
                Animal a1 = animalList.get(0);
                Animal a2 = animalList.get(1);
                if (a1.getEnergy() > simulationProperties.getEnergyLevelNeededToReproduce() && a2.getEnergy() > simulationProperties.getEnergyLevelNeededToReproduce()) {
                    Animal child = new Animal(position, simulationProperties);
                    synchronized (this) {
                        map.getAnimals().get(position).add(child);
                        simulation.addAnimal(child);
                        a1.removeEnergy(simulationProperties.getEnergyLevelToPassToChild());
                        a1.addChildToList(simulation.getAnimals().get(simulation.getAnimals().indexOf(child)));
                        a2.removeEnergy(simulationProperties.getEnergyLevelToPassToChild());
                        a2.addChildToList(simulation.getAnimals().get(simulation.getAnimals().indexOf(child)));
                    }
                }
            }
        }
    }

    public void eat() {
        Set<Vector2d> keys = new HashSet<>(map.getPlants().keySet());
        for (Vector2d position : keys) {
            if (map.getAnimals().containsKey(position)) {
                List<Animal> animalList = map.getAnimals().get(position);
                if (!animalList.isEmpty()) {
                    Animal animal = animalList.get(0);
                    System.out.println("Animal at " + position + " eating grass. Current energy: " + animal.getEnergy());
                    synchronized (this) {
                        animal.eat(simulationProperties.getGrassEnergy());
                        map.getPlants().remove(position);
                        map.getFreePositionsForPlants().add(position);
                    }
                    System.out.println("After eating energy: " + animal.getEnergy());
                }
            }
        }
    }

    public void spawnPlant() {
        List<Vector2d> freePositions = map.getFreePositionsForPlants();
        if (freePositions.isEmpty()) return;

        Vector2d plantPosition = freePositions.get(random.nextInt(freePositions.size()));
        map.placeGrass(plantPosition, new Grass(plantPosition));
    }

    private void growGrass() {
        int plantsToAdd = simulationProperties.getDailySpawningGrass();
        for (int i = 0; i<plantsToAdd; i++) {
            spawnPlant();
        }
    }

}
