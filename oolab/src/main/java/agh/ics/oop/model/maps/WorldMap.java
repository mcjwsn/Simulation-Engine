package agh.ics.oop.model.maps;

import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.model.mapElements.WorldElement;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.util.*;
import java.util.List;
import java.util.Optional;

public interface WorldMap {
    void place(Vector2d position, Animal animal);

    void move(Animal animal);

    default boolean isOccupied(Vector2d position){
        return objectAt(position).isPresent();
    }

    Optional<WorldElement> objectAt(Vector2d position);

    List<WorldElement> getElements();

    String getId();

    Boundary getCurrentBounds();

    List<Integer> getMostPopularGenotype();
}