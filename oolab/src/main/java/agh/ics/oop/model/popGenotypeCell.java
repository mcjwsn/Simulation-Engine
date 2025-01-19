package agh.ics.oop.model;
import agh.ics.oop.model.Enums.ElementType;
public class popGenotypeCell implements WorldElement{
    private Vector2d position;
    public popGenotypeCell(Vector2d position) {
        this.position = position;
    }
    @Override
    public Vector2d getPosition() {
        return position;
    }
    @Override
    public String toString() {
        return "+";
    }
    @Override
    public String getImageResource() {
        //return "popGenotype.png";
        //return "pop.jpg";
        return "try.png";
    }
    public ElementType getType() {
        return ElementType.PREFERRED_CELL;
    }
}
