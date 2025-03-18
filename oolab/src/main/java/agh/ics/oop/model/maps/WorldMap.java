package agh.ics.oop.model.maps;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.Grass;
import agh.ics.oop.model.mapElements.MovementStrategy;
import agh.ics.oop.model.mapElements.WorldElement;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapChangeListener;
import agh.ics.oop.model.util.Statistics;
import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.simulation.Simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // Keep this method in the interface for backward compatibility
    List<Integer> getMostPopularGenotype();

    void addObserver(MapChangeListener observer);

    void mapChanged(Statistics statistics, String msg);

    MapType getMapType();

    Map<Vector2d, List<Animal>> getAnimals();

    List<Animal> getDeadAnimals();

    HashMap<Vector2d, Grass> getPlants();

    Simulation getSimulation();

    int getWidth();

    int getHeight();

    MovementStrategy getMovementStrategy();
}