package agh.ics.oop.simulation;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.util.RandomPositionGenerator;
import agh.ics.oop.model.util.Vector2d;

import java.util.*;

public class Simulation implements Runnable {
    private final AbstractWorldMap map;

    private int days = 0;
    private final List<Animal> animals = new ArrayList<>();
    private boolean isRunning = true;
    private boolean isPaused = false; // For pausing the simulation
    private Thread simulationThread; // Thread to run the simulation
    private final SimulationProperties simulationProperties;
    private final SimulationManager simulationManager;
    private final Map<int[], Integer> genesCount = new HashMap<>();

    public List<Animal> getAnimals() {
        return animals;
    }

    public Simulation(AbstractWorldMap map, SimulationProperties simulationProperties) {
        map.setSimulation(this);
        this.map = map;
        this.simulationProperties = simulationProperties;
        simulationManager = new SimulationManager(map, simulationProperties, this);

        RandomPositionGenerator randomPositionGeneratorAnimals = new RandomPositionGenerator(
                simulationProperties.getMapHeight(),
                simulationProperties.getMapWidth(),
                simulationProperties.getStartAnimalNumber()
        );
        for (Vector2d animalPosition : randomPositionGeneratorAnimals) {
            Animal animal = new Animal(animalPosition, simulationProperties);
            animals.add(animal);
            int[] genome = animal.getGenome();
            genesCount.merge(genome, 1, Integer::sum);
            map.place(animal.getPosition(), animal);
        }
        simulationManager.generateGrass(simulationProperties.getGrassNumber());
    }

    @Override
    public void run() {
        simulationManager.Init();
        try {
            Thread.sleep(1000); // initialization
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (isRunning) {
            synchronized (this) {
                while (isPaused) {
                    try {
                        wait(); // Pause the simulation thread
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            if (!animals.isEmpty()) {
                synchronized (this) {
                    this.days++;
                    simulationManager.Update();
                }
                try {
                    Thread.sleep(200); // Simulation update delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                stop();
            }
        }
    }

    public synchronized void start() {
        if (simulationThread == null || !simulationThread.isAlive()) {
            isRunning = true;
            isPaused = false;
            simulationThread = new Thread(this);
            simulationThread.start();
        } else if (isPaused) {
            isPaused = false;
            notify();
        }
    }

    public synchronized void pause() {
        isPaused = true;
    }

    public synchronized void stop() {
        isRunning = false;
        if (simulationThread != null && simulationThread.isAlive()) {
            simulationThread.interrupt();
        }
    }

    public synchronized void addAnimal(Animal animal) {
        animals.add(animal);
        int[] genome = animal.getGenome();
        genesCount.merge(genome, 1, Integer::sum);
    }

    public int getDays() {
        return days;
    }

    public Set<Vector2d> getPreferedPositions() {
        return simulationManager.getPreferredPositions();
    }
}