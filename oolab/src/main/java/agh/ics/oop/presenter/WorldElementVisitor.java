package agh.ics.oop.presenter;


import agh.ics.oop.model.mapElements.*;
import javafx.scene.layout.VBox;

public interface WorldElementVisitor {
    void visit(Animal animal, boolean isTracked);
    void visit(Grass grass);
    void visit(OwlBear owlBear);
    void visit(EmptyCell emptyCell);
    void visit(PrefferdCell prefferdCell);
    void visit(GenotypeCell genotypeCell);
}