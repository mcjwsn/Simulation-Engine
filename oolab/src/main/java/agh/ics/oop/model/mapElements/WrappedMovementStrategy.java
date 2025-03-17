package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.util.Vector2d;

public class WrappedMovementStrategy implements MovementStrategy {
    @Override
    public Vector2d getNewPosition(Vector2d currentPosition, MapDirection orientation, int width, int height) {
        Vector2d newPosition = currentPosition.add(orientation.toUnitVector());

        // Wrap around
        int wrappedX = (newPosition.getX() + width + 1) % (width + 1);
        int wrappedY = (newPosition.getY() + height + 1) % (height + 1);

        return new Vector2d(wrappedX, wrappedY);
    }
}