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
    private boolean isPaused = false;
    private Thread simulationThread;
    private final SimulationProperties simulationProperties;
    private final SimulationManager simulationManager;
    private final Map<int[], Integer> genesCount = new HashMap<>();

    // New field for GrassManager to initialize grass
    private final GrassManager grassManager;


    public List<Animal> getAnimals() {
        return animals;
    }

    public Simulation(AbstractWorldMap map, SimulationProperties simulationProperties) {
        map.setSimulation(this);
        this.map = map;
        this.simulationProperties = simulationProperties;

        // Create the simulation manager and its components
        this.simulationManager = new SimulationManager(map, simulationProperties, this);

        // Create a separate grass manager for initial grass placement
        this.grassManager = new GrassManager(map, simulationProperties);

        // Initialize animals
        initializeAnimals();

        // Initialize grass using the grass manager
        grassManager.generateGrass(simulationProperties.getGrassNumber());
    }

    private void initializeAnimals() {
        RandomPositionGenerator randomPositionGeneratorAnimals = new RandomPositionGenerator(
                simulationProperties.getMapHeight(),
                simulationProperties.getMapWidth(),
                simulationProperties.getStartAnimalNumber()
        );

        for (Vector2d animalPosition : randomPositionGeneratorAnimals) {
            Animal animal = Animal.createParent(animalPosition, simulationProperties);
            animals.add(animal);
            trackGenome(animal);
            map.place(animal.getPosition(), animal);
        }
    }

    private void trackGenome(Animal animal) {
        int[] genome = animal.getGenome();
        genesCount.merge(genome, 1, Integer::sum);
    }

    @Override
    public void run() {
        simulationManager.Init();
        try {
            Thread.sleep(800); // initialization delay
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
        trackGenome(animal);
    }

    public int getDays() {
        return days;
    }

    public Set<Vector2d> getPreferredPositions() {
        // This method should now delegate to the appropriate component
        // We'll need to expose this from the SimulationManager
        return simulationManager.getPreferredPositions();
    }
}