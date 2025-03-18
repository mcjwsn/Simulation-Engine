package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.util.Vector2d;

public class ConstrainedMovementStrategy implements MovementStrategy {
    @Override
    public Vector2d getNewPosition(Vector2d currentPosition, MapDirection orientation, int width, int height) {
        Vector2d newPosition = currentPosition.add(orientation.toUnitVector());

        // Check boundaries
        if (newPosition.follows(new Vector2d(0, 0)) &&
                newPosition.precedes(new Vector2d(width, height))) {
            return newPosition;
        }

        // Stay in place if outside boundaries
        return currentPosition;
    }
}


