package agh.ics.oop.model;

import agh.ics.oop.model.util.*;

import java.util.List;
import java.util.Optional;


/*
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap {
    void place(Vector2d position, Animal animal);

    /*
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal);

    /*
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    default boolean isOccupied(Vector2d position){
        return objectAt(position).isPresent();
    }

    /*
     * Return an element at a given position.
     *
     * @param position The position of the animal.
     * @return WorldElement or null if the position is not occupied.
     */
    Optional<WorldElement> objectAt(Vector2d position);

    /*
     * Return a collection of all elements (animals and grass) on the map.
     */
    List<WorldElement> getElements();

    String getId();

    Boundary getCurrentBounds();
}
