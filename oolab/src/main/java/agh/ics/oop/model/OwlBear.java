package agh.ics.oop.model;

import agh.ics.oop.model.modes.ElementType;

public class OwlBear implements WorldElement {
    private Vector2d position;

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public ElementType getType(){
        return ElementType.OWLBEAR;
    }
}