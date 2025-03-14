package agh.ics.oop.simulation;

import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.util.Statistics;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.mapElements.Grass;
import agh.ics.oop.model.util.Vector2d;

import java.util.*;

public class SimulationManager {
    private final AbstractWorldMap map;
    private final SimulationProperties simulationProperties;
    private final Simulation simulation;
    private final Statistics statistics = new Statistics();
    private final Object updateLock = new Object();

    // Keep these static fields in SimulationManager as they were in the original
    private static final double PREFERRED_POSITION_PROBABILITY = 0.8; // Pareto rule
    private static final Set<Vector2d> preferredPositions = new HashSet<>();
    private static final Set<Vector2d> lessPreferredPositions = new HashSet<>();
    private static int DAILY_GRASS_NUMBER = 0;

    protected static final Random random = new Random();

    // Components with single responsibilities
    private final AnimalLifecycleManager animalLifecycleManager;
    private final MovementManager movementManager;
    private final ReproductionManager reproductionManager;
    private final FeedingManager feedingManager;
    private final OwlBearManager owlBearManager;

    public SimulationManager(AbstractWorldMap map_, SimulationProperties simulationProperties_, Simulation simulation_) {
        map = map_;
        simulationProperties = simulationProperties_;
        simulation = simulation_;
        DAILY_GRASS_NUMBER = simulationProperties.getDailySpawningGrass();

        // Initialize specialized managers
        animalLifecycleManager = new AnimalLifecycleManager(map, simulationProperties, simulation);
        movementManager = new MovementManager(map, simulationProperties);
        reproductionManager = new ReproductionManager(map, simulationProperties, simulation);
        feedingManager = new FeedingManager(map, simulationProperties, this);
        owlBearManager = new OwlBearManager(map, simulationProperties, simulation);

        // Initialize positions - keep this in SimulationManager as it was in the original
        initializePositions(map);
    }

    public void Init() {
        map.setStatistics(statistics, simulation.getDays());
        map.mapChanged(statistics, "Dzien sie zakonczyl");
    }

    public void Update() {
        synchronized (updateLock) {
            animalLifecycleManager.removeDeadAnimals();
            movementManager.moveAllAnimals(simulation.getAnimals());

            if (map.getMapType() == MapType.OWLBEAR) {
                owlBearManager.owlBearEat();
            }

            feedingManager.feedAnimals();

            if (map.getMapType() == MapType.OWLBEAR) {
                owlBearManager.moveOwlBear();
                owlBearManager.owlBearEat();
            }

            reproductionManager.reproduceAnimals();
            growGrass(); // Keep this method here as in the original
            animalLifecycleManager.incrementAge();

            map.setStatistics(statistics, simulation.getDays());
            map.mapChanged(statistics, "Dzien sie zakonczyl");
        }
    }

    // Keep this method in SimulationManager to maintain the original behavior
    public Set<Vector2d> getPreferredPositions() {
        int equatorHeight = simulationProperties.getEquatorHeight();
        int width = map.getWidth();
        int height = map.getHeight();
        Set<Vector2d> preferred = new HashSet<>();
        int centerRow = width / 2;
        int startEquatorRow = centerRow - ((equatorHeight - 1) / 2);
        int endEquatorRow = startEquatorRow + equatorHeight - 1;
        startEquatorRow = Math.max(startEquatorRow, 0);
        endEquatorRow = Math.min(endEquatorRow, height - 1);
        for (int x = 0; x <= height; x++) {
            for (int y = 0; y <= width; y++) {
                Vector2d position = new Vector2d(x, y);
                if (y >= startEquatorRow && y <= endEquatorRow) {
                    preferred.add(position);
                }
            }
        }
        return preferred;
    }

    // Keep this method in SimulationManager to maintain the original behavior
    public void restoreEatenPlantPosition(Grass eatenGrass) {
        int equatorHeight = simulationProperties.getEquatorHeight();
        int width = map.getWidth();
        int height = map.getHeight();
        int centerRow = width / 2;
        int startEquatorRow = centerRow - ((equatorHeight - 1) / 2);
        int endEquatorRow = startEquatorRow + equatorHeight - 1;
        startEquatorRow = Math.max(startEquatorRow, 0);
        endEquatorRow = Math.min(endEquatorRow, height - 1);
        Vector2d availablePosition = eatenGrass.getPosition();
        if (availablePosition.getY() >= startEquatorRow && availablePosition.getY() <= endEquatorRow) {
            preferredPositions.add(availablePosition);
        } else {
            lessPreferredPositions.add(availablePosition);
        }
    }

    // Keep this method in SimulationManager to maintain the original behavior
    public void initializePositions(AbstractWorldMap map) {
        int equatorHeight = simulationProperties.getEquatorHeight(); // The height of the equator
        int width = map.getWidth();
        int height = map.getHeight();

        Set<Vector2d> preferred = new HashSet<>();
        Set<Vector2d> lessPreferred = new HashSet<>();
        int centerRow = width / 2; // The central row of the map
        int startEquatorRow = centerRow - ((equatorHeight - 1) / 2);
        int endEquatorRow = startEquatorRow + equatorHeight - 1;

        startEquatorRow = Math.max(startEquatorRow, 0);
        endEquatorRow = Math.min(endEquatorRow, height - 1);

        for (int x = 0; x <= height; x++) {
            for (int y = 0; y <= width; y++) {
                Vector2d position = new Vector2d(x, y);
                if (y >= startEquatorRow && y <= endEquatorRow) {
                    preferred.add(position); // Positions within the equator
                } else {
                    lessPreferred.add(position); // Positions outside the equator
                }
            }
        }
        preferredPositions.clear();
        preferredPositions.addAll(preferred);
        lessPreferredPositions.clear();
        lessPreferredPositions.addAll(lessPreferred);
    }

    // Keep this method in SimulationManager to maintain the original behavior
    public void generateGrass(int numberOfPlants) {
        for (int i = 0; i < numberOfPlants; i++) {
            double probability = random.nextDouble();
            Vector2d plantPosition;
            if ((probability < PREFERRED_POSITION_PROBABILITY && !preferredPositions.isEmpty()) || (lessPreferredPositions.isEmpty() && !preferredPositions.isEmpty())) {
                List<Vector2d> preferredList = new ArrayList<>(preferredPositions);
                plantPosition = preferredList.get(random.nextInt(preferredList.size()));
                preferredPositions.remove(plantPosition);
                map.placeGrass(plantPosition, new Grass(plantPosition));
            } else if (!lessPreferredPositions.isEmpty()) {
                List<Vector2d> lessPreferredList = new ArrayList<>(lessPreferredPositions);
                plantPosition = lessPreferredList.get(random.nextInt(lessPreferredList.size()));
                lessPreferredPositions.remove(plantPosition);
                map.placeGrass(plantPosition, new Grass(plantPosition));
            }
        }
    }

    // Keep this method in SimulationManager to maintain the original behavior
    public void growGrass() {
        generateGrass(DAILY_GRASS_NUMBER);
    }
}
