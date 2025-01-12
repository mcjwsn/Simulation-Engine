package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.*;


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
       // System.out.println("\n=== Starting reproduction phase ===");
       // System.out.println("Total positions with animals: " + map.getAnimals().keySet().size());
        System.out.println(map.getAnimals().keySet());

        for (Vector2d position : map.getAnimals().keySet()) {
            List<Animal> animalList = map.getAnimals().get(position);
            //System.out.println("\nChecking position " + position + " with " + animalList.size() + " animals");
            if (animalList.size() > 1) {
                Animal a1 = animalList.get(0);
                Animal a2 = animalList.get(1);
                System.out.println("Potential parents found:");
                System.out.println("Parent 1 energy: " + a1.getEnergy());
                System.out.println("Parent 2 energy: " + a2.getEnergy());
                System.out.println("Energy needed to reproduce: " + simulationProperties.getEnergyLevelNeededToReproduce());
                System.out.println(position);
                if (a1.getEnergy() > simulationProperties.getEnergyLevelNeededToReproduce() &&
                        a2.getEnergy() > simulationProperties.getEnergyLevelNeededToReproduce()) {

                    System.out.println("Both parents have enough energy - creating child");
                    int[] getGenome = Genes.mixGenesFromParents(a1,a2,simulationProperties);
                    Animal child = new Animal(position, simulationProperties,getGenome);
                    System.out.println("First partent genome");
                    System.out.println(Arrays.toString(a1.getGenome()));
                    System.out.println("Second parent genowe");
                    System.out.println(Arrays.toString(a2.getGenome()));
                    System.out.println("child genome");
                    System.out.print(Arrays.toString(child.getGenome()));
                    synchronized (this) {
                        System.out.println("Before reproduction:");
                        System.out.println("Total animals at position before birth: " +
                                map.getAnimals().get(position).size());
                        System.out.println("Parent 1 energy: " + a1.getEnergy());
                        System.out.println("Parent 2 energy: " + a2.getEnergy());

                        map.getAnimals().get(position).add(child);
                        simulation.addAnimal(child);

                        a1.removeEnergy(simulationProperties.getEnergyLevelToPassToChild());
                        a1.addChildToList(simulation.getAnimals().get(simulation.getAnimals().indexOf(child)));
                        a2.removeEnergy(simulationProperties.getEnergyLevelToPassToChild());
                        a2.addChildToList(simulation.getAnimals().get(simulation.getAnimals().indexOf(child)));

                        System.out.println("After reproduction:");
                        System.out.println("Parent 1 energy: " + a1.getEnergy());
                        System.out.println("Parent 2 energy: " + a2.getEnergy());
                        System.out.println("Child energy: " + child.getEnergy());
                        System.out.println("Total animals at position after birth: " +
                                map.getAnimals().get(position).size());
                        System.out.println("\n");
                    }
                } else {
                    System.out.println("Not enough energy for reproduction");
                }
            } else {
               // System.out.println("Not enough animals for reproduction at this position");
            }
        }
        System.out.println("\n=== Reproduction phase completed ===");
        System.out.println("Total animals after reproduction: " + simulation.getAnimals().size());
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
