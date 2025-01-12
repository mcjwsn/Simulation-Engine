package agh.ics.oop.model;

import agh.ics.oop.model.modes.ElementType;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private final Genes genotype;

    public Animal(MapDirection orientation, Vector2d position, Genes genotype, int energy) {
        this.genotype = genotype;
        this.orientation = orientation;
        this.position = position;
        this.energy = energy;
    }

    public Animal(MapDirection orientation, Vector2d position) {
        this(orientation, position, new Genes(10), 100);
    }

    public Animal() {
        this(MapDirection.NORTH, new Vector2d(2, 2), new Genes(10), 100); // Call the second constructor
    }

    @Override
    public String toString() {
        return String.valueOf(orientation);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public Genes getGenotype() {
        return genotype;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public int getEnergy(){
        return energy;
    }

    public void move(MoveDirection direction, MoveValidator map) {
        int steps = direction.ordinal();
        for (int i = 0; i < steps; i++) {
            this.orientation = this.orientation.next();
        }
        this.position = this.position.add(this.orientation.toUnitVector());
    }

    public ElementType getType(){
        return ElementType.ANIMAL;
    }
}