package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.model.enums.ElementType;

public abstract class AbstractWorldElement implements WorldElement {
    protected final Vector2d position;

    public AbstractWorldElement(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public abstract ElementType getType();

    @Override
    public abstract String getImageResource();


}
