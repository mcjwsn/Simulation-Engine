package agh.ics.oop.model;

import agh.ics.oop.model.Enums.ElementType;

public class EmptyCell implements WorldElement {
    private final Vector2d position;
    public EmptyCell(Vector2d position) {
        this.position = position;
    }
    @Override
    public Vector2d getPosition() {
        return position;
    }
    @Override
    public String toString() {
        return " ";
    }
    @Override
    public String getImageResource() {
        return "empty.png";
    }
    public ElementType getType() {
        return ElementType.EMPTY_CELL;
    }
}