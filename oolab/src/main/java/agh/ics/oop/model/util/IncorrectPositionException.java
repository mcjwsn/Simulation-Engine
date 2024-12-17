package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

public class IncorrectPositionException extends Exception {
    private final Vector2d position;

    public IncorrectPositionException(Vector2d position) {
        super("Position " + position + " is already occupied.");
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }
}
