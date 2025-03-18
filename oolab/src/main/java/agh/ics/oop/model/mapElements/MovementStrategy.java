package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.util.Vector2d;

public interface MovementStrategy {
    Vector2d getNewPosition(Vector2d currentPosition, MapDirection orientation, int width, int height);
}