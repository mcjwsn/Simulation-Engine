package agh.ics.oop.model;

import agh.ics.oop.model.util.*;
import java.util.UUID;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

    public RectangularMap(int width, int height){
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width-1, height-1);
    }

    public RectangularMap(){
        this(5,5);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position)
                && position.follows(lowerLeft)
                && position.precedes(upperRight);
    }

    @Override
    public Boundary getCurrentBounds(){
        return new Boundary(lowerLeft, upperRight);
    }

    @Override
    public String getId() {
        return id;
    }
}
