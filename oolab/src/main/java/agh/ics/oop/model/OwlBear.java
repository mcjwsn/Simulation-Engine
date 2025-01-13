package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.modes.ElementType;
import agh.ics.oop.model.modes.MovinType;

import java.util.Map;

public class OwlBear implements WorldElement {
    private Vector2d position;
    private Genes genotype;
    private MapDirection orientation;

    public OwlBear(Vector2d position, SimulationProperties simulationProperties)
    {
        this.genotype = new Genes(simulationProperties.getGenesCount());
        this.position = position;
        this.orientation = MapDirection.NORTH;
    }

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

    public void move(OwlBearMap map) {
        MoveDirection direction = genotype.getCurrentGene();
        genotype.incrementIndex();
        int steps = direction.ordinal();

        for (int i = 0; i < steps; i++) {
            this.orientation = this.orientation.next();
        }

        Vector2d newPosition = this.position.add(this.orientation.toUnitVector());
        if (isWithinBounds(newPosition, map.getLowerLeftBoundary(), map.getUpperRightBoundary()) && map.canMoveTo(newPosition)) {
            this.position = newPosition;
        }
    }

    private boolean isWithinBounds(Vector2d position, Vector2d lowerLeftBoundary, Vector2d upperRightBoundary) {
        return position.getX() >= lowerLeftBoundary.getX() && position.getX() <= upperRightBoundary.getX() &&
                position.getY() >= lowerLeftBoundary.getY() && position.getY() <= upperRightBoundary.getY();
    }


    @Override
    public String toString() {
        return "O";
    }
}