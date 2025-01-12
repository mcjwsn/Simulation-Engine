package agh.ics.oop;
import agh.ics.oop.model.*;
import agh.ics.oop.model.modes.MapType;
import agh.ics.oop.model.modes.MovinType;
import agh.ics.oop.model.modes.MutationType;
import agh.ics.oop.model.util.*;
import javafx.application.Platform;
import agh.ics.oop.presenter.*;

import java.util.*;

public class Simulation implements Runnable {
    private final AbstractWorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private boolean isRunning = true; // do pozniejszego stopowania
    private SimulationProperties simulationProperties;
    private final SimulationManager simulationManager ;
    private final Map<int[], Integer> genesCount = new HashMap<>();
    public List<Animal> getAnimals() {
        return animals;
    }

    public Simulation(AbstractWorldMap map, SimulationProperties simulationProperties) {
        map.setSimulation(this);
        this.map = map;
        this.simulationProperties = simulationProperties;
        simulationManager = new SimulationManager(map, simulationProperties, this);

        RandomPositionGenerator randomPositionGeneratorAnimals = new RandomPositionGenerator(simulationProperties.getMapWidth(), simulationProperties.getMapHeight(), simulationProperties.getStartAnimalNumber());
        for(Vector2d animalPosition : randomPositionGeneratorAnimals) {
            Animal animal = new Animal( animalPosition, simulationProperties);
            animals.add(animal);
            int[] genome = animal.getGenome();
            genesCount.merge(genome, 1, Integer::sum);
            map.place(animal.getPosition(), animal);
        }

        simulationManager.generateGrass(simulationProperties.getStartAnimalNumber());
//        RandomPositionGenerator randomPositionGeneratorPlants = new RandomPositionGenerator(simulationProperties.getMapWidth(), simulationProperties.getMapHeight(), simulationProperties.getGrassNumber());
//        for(Vector2d plantPosition : randomPositionGeneratorPlants) {
//            map.placeGrass(plantPosition, new Grass(plantPosition));
//        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2500); // inicjalizacja
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!animals.isEmpty()) {
            if (isRunning) {
                synchronized (this) {
                    simulationManager.Update();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void addAnimal(Animal animal){
        animals.add(animal);
        int[] genome = animal.getGenome();
        genesCount.merge(genome, 1, Integer::sum);
    }

    public Map<int[], Integer> getGenomeNumber() { return genesCount; }

    public Integer getAliveAnimalsNumber() {
        return animals.size();
    }
}