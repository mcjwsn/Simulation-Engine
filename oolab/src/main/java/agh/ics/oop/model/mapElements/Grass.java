package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.model.enums.ElementType;

public class Grass implements WorldElement {
    private Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public String getImageResource() {
        return "grassmix.jpg";
    }

    public ElementType getType() {
        return ElementType.GRASS;
    }
}