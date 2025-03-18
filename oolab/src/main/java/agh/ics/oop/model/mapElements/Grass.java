package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.model.enums.ElementType;
import agh.ics.oop.presenter.WorldElementVisitor;

public class Grass extends AbstractWorldElement {
    public Grass(Vector2d pos) {
        super(pos);
    }
    @Override
    public String toString() {return "*";}
    @Override
    public String getImageResource() {return "grassmix.jpg";}
    @Override
    public ElementType getType() {return ElementType.GRASS;}
    @Override
    public void accept(WorldElementVisitor visitor) {
        visitor.visit(this);
    }
}