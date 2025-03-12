package agh.ics.oop.simulation;

import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.util.Statistics;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.util.Vector2d;

import java.util.Set;

public class SimulationManager {
    private final AbstractWorldMap map;
    private final SimulationProperties simulationProperties;
    private final Simulation simulation;
    private final Statistics statistics = new Statistics();
    private final Object updateLock = new Object();

    // Components with single responsibilities
    private final AnimalLifecycleManager animalLifecycleManager;
    private final MovementManager movementManager;
    private final ReproductionManager reproductionManager;
    private final FeedingManager feedingManager;
    private final GrassManager grassManager;
    private final OwlBearManager owlBearManager;
    private final PositionManager positionManager;

    public SimulationManager(AbstractWorldMap map_, SimulationProperties simulationProperties_, Simulation simulation_) {
        map = map_;
        simulationProperties = simulationProperties_;
        simulation = simulation_;

        // Initialize the position manager first since other managers depend on it
        positionManager = new PositionManager(map, simulationProperties);

        // Initialize specialized managers
        animalLifecycleManager = new AnimalLifecycleManager(map, simulationProperties, simulation);
        movementManager = new MovementManager(map, simulationProperties);
        reproductionManager = new ReproductionManager(map, simulationProperties, simulation);
        feedingManager = new FeedingManager(map, simulationProperties, positionManager);
        grassManager = new GrassManager(map, simulationProperties, positionManager);
        owlBearManager = new OwlBearManager(map, simulationProperties, simulation);

        // Initialize positions
        positionManager.initializePositions();
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
            grassManager.growGrass();
            animalLifecycleManager.incrementAge();

            map.setStatistics(statistics, simulation.getDays());
            map.mapChanged(statistics, "Dzien sie zakonczyl");
        }
    }

    // Delegate to position manager
    public Set<Vector2d> getPreferredPositions() {
        return positionManager.getPreferredPositions();
    }
}