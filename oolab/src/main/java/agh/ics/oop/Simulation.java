package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.modes.MapType;
import agh.ics.oop.model.modes.MovinType;
import agh.ics.oop.model.modes.MutationType;
import agh.ics.oop.model.util.*;
import agh.ics.oop.presenter.*;
import javafx.application.Platform;

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
                simulationProperties.getMapWidth(),
                simulationProperties.getMapHeight(),
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
        try {
            Thread.sleep(2500); // initialization
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
                    Thread.sleep(500); // Simulation update delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                stop(); // Stop the simulation if there are no animals left
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
            notify(); // Resume the simulation thread
        }
    }

    public synchronized void pause() {
        isPaused = true; // Pause the simulation
    }

    public synchronized void stop() {
        isRunning = false; // Stop the simulation
        if (simulationThread != null && simulationThread.isAlive()) {
            simulationThread.interrupt();
        }
    }

    public synchronized void addAnimal(Animal animal) {
        animals.add(animal);
        int[] genome = animal.getGenome();
        genesCount.merge(genome, 1, Integer::sum);
    }

    public Map<int[], Integer> getGenomeNumber() {
        return genesCount;
    }

    public Integer getAliveAnimalsNumber() {
        return animals.size();
    }

    public int getDays() {
        return days;
    }
}
