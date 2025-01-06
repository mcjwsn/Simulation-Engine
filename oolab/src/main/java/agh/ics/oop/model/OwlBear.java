package agh.ics.oop.model;

public class OwlBear implements WorldElement {
    private Vector2d position;

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

}
