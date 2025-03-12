package agh.ics.oop.simulation;

import agh.ics.oop.model.mapElements.Grass;
import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.util.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrassManager {
    private final AbstractWorldMap map;
    private final SimulationProperties simulationProperties;
    private final PositionManager positionManager;
    private final Random random = new Random();

    private static final double PREFERRED_POSITION_PROBABILITY = 0.8; // Pareto rule

    public GrassManager(AbstractWorldMap map, SimulationProperties simulationProperties, PositionManager positionManager) {
        this.map = map;
        this.simulationProperties = simulationProperties;
        this.positionManager = positionManager;
    }

    // Constructor for standalone usage (e.g., in Simulation)
    public GrassManager(AbstractWorldMap map, SimulationProperties simulationProperties) {
        this.map = map;
        this.simulationProperties = simulationProperties;
        this.positionManager = new PositionManager(map, simulationProperties);
        this.positionManager.initializePositions();
    }

    public void growGrass() {
        generateGrass(simulationProperties.getDailySpawningGrass());
    }

    public void generateGrass(int numberOfPlants) {
        for (int i = 0; i < numberOfPlants; i++) {
            double probability = random.nextDouble();
            Vector2d plantPosition = null;

            if ((probability < PREFERRED_POSITION_PROBABILITY && !positionManager.getPreferredPositions().isEmpty())
                    || (positionManager.getLessPreferredPositions().isEmpty() && !positionManager.getPreferredPositions().isEmpty())) {
                // Choose from preferred positions
                List<Vector2d> preferredList = new ArrayList<>(positionManager.getPreferredPositions());
                plantPosition = preferredList.get(random.nextInt(preferredList.size()));
                positionManager.getPreferredPositions().remove(plantPosition);
            } else if (!positionManager.getLessPreferredPositions().isEmpty()) {
                // Choose from less preferred positions
                List<Vector2d> lessPreferredList = new ArrayList<>(positionManager.getLessPreferredPositions());
                plantPosition = lessPreferredList.get(random.nextInt(lessPreferredList.size()));
                positionManager.getLessPreferredPositions().remove(plantPosition);
            }

            if (plantPosition != null) {
                map.placeGrass(plantPosition, new Grass(plantPosition));
            }
        }
    }
}