package agh.ics.oop.model.mapElements;
import agh.ics.oop.model.enums.ElementType;
import agh.ics.oop.model.util.Vector2d;

public class GenotypeCell implements WorldElement {
    private Vector2d position;
    public GenotypeCell(Vector2d position) {
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
        return "try.png";
    }
    public ElementType getType() {
        return ElementType.PREFERRED_CELL;
    }
}