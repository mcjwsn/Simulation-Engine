package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.model.enums.ElementType;

public interface WorldElement {
    Vector2d getPosition();
    ElementType getType();
    String getImageResource();
}