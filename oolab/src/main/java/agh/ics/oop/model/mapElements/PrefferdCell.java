package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.enums.ElementType;
import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.presenter.WorldElementVisitor;


public class PrefferdCell extends AbstractWorldElement {
    public PrefferdCell(Vector2d pos) {
        super(pos);
    }
    @Override
    public String toString() {
        return "=";
    }
    @Override
    public String getImageResource() {
        return "empty.png";
    }
    public ElementType getType() {
        return ElementType.PREFERRED_CELL;
    }
    @Override
    public void accept(WorldElementVisitor visitor) {
        visitor.visit(this);
    }
}