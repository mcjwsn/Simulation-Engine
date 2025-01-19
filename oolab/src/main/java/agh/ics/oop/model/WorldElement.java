package agh.ics.oop.model;

import agh.ics.oop.model.Enums.ElementType;

public interface WorldElement {
    Vector2d getPosition();
    ElementType getType();
    String getImageResource();
}