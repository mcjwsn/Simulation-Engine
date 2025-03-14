package agh.ics.oop.simulation;

import agh.ics.oop.model.mapElements.Grass;
import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.util.Vector2d;

import java.util.HashSet;
import java.util.Set;

public class PositionManager {
    private final AbstractWorldMap map;
    private final SimulationProperties simulationProperties;
    private final Set<Vector2d> preferredPositions = new HashSet<>();
    private final Set<Vector2d> lessPreferredPositions = new HashSet<>();

    public PositionManager(AbstractWorldMap map, SimulationProperties simulationProperties) {
        this.map = map;
        this.simulationProperties = simulationProperties;
    }

    public Set<Vector2d> getPreferredPositions() {
        return preferredPositions;
    }

    public Set<Vector2d> getLessPreferredPositions() {
        return lessPreferredPositions;
    }

    public void restoreEatenPlantPosition(Grass eatenGrass) {
        Vector2d availablePosition = eatenGrass.getPosition();
        if (isInEquator(availablePosition)) {
            preferredPositions.add(availablePosition);
        } else {
            lessPreferredPositions.add(availablePosition);
        }
    }

    public void initializePositions() {
        int width = map.getWidth();
        int height = map.getHeight();
        int[] equatorBounds = calculateEquatorBounds();
        int startEquatorRow = equatorBounds[0];
        int endEquatorRow = equatorBounds[1];

        preferredPositions.clear();
        lessPreferredPositions.clear();

        // Add all positions to appropriate sets
        for (int x = 0; x <= height; x++) {
            for (int y = 0; y <= width; y++) {
                Vector2d position = new Vector2d(x, y);
                if (y >= startEquatorRow && y <= endEquatorRow) {
                    preferredPositions.add(position); // Positions within the equator
                } else {
                    lessPreferredPositions.add(position); // Positions outside the equator
                }
            }
        }

        // Remove positions that already have grass
        for (Vector2d position : map.getPlants().keySet()) {
            preferredPositions.remove(position);
            lessPreferredPositions.remove(position);
        }
    }

    private boolean isInEquator(Vector2d position) {
        int[] equatorBounds = calculateEquatorBounds();
        int startEquatorRow = equatorBounds[0];
        int endEquatorRow = equatorBounds[1];

        return position.getY() >= startEquatorRow && position.getY() <= endEquatorRow;
    }

    private int[] calculateEquatorBounds() {
        int equatorHeight = simulationProperties.getEquatorHeight();
        int width = map.getWidth();
        int height = map.getHeight();

        int centerRow = width / 2;
        int startEquatorRow = centerRow - ((equatorHeight - 1) / 2);
        int endEquatorRow = startEquatorRow + equatorHeight - 1;

        startEquatorRow = Math.max(startEquatorRow, 0);
        endEquatorRow = Math.min(endEquatorRow, height - 1);

        return new int[] {startEquatorRow, endEquatorRow};
    }
}