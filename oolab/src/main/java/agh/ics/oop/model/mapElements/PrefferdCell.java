package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.enums.ElementType;
import agh.ics.oop.model.util.Vector2d;


public class PrefferdCell extends WorldElementAbstract {
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
}