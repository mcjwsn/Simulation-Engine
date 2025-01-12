package agh.ics.oop.model;

import agh.ics.oop.model.util.*;
import agh.ics.oop.model.util.MapVisualizer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.UUID;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, List<Animal>> animals = new ConcurrentHashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    protected final List<MapChangeListener> observers = new CopyOnWriteArrayList<>();
    protected final String id;

    public AbstractWorldMap() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public boolean canMoveTo(Vector2d position)
    {
        return true;
    }

    @Override
    public void place(Animal animal) {
        animals.computeIfAbsent(animal.getPosition(), pos -> new CopyOnWriteArrayList<>()).add(animal);
        notifyObservers("Animal placed at " + animal.getPosition());
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        animal.move(direction, this);
        Vector2d newPosition = animal.getPosition();

        List<Animal> oldPositionAnimals = animals.get(oldPosition);
        if (oldPositionAnimals != null) {
            oldPositionAnimals.remove(animal);
            if (oldPositionAnimals.isEmpty()) {
                animals.remove(oldPosition);
            }
        }

        animals.computeIfAbsent(newPosition, pos -> new CopyOnWriteArrayList<>()).add(animal);
        notifyObservers("Animal moved from " + oldPosition + " to " + newPosition);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        List<Animal> positionAnimals = animals.get(position);
        return positionAnimals != null && !positionAnimals.isEmpty();
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        List<Animal> positionAnimals = animals.get(position);
        if (positionAnimals != null && !positionAnimals.isEmpty()) {
            return positionAnimals.get(0);
        }
        return null;
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> allElements = new ArrayList<>();
        for (List<Animal> animalList : animals.values()) {
            allElements.addAll(animalList);
        }
        return allElements;
    }

    public void moveAllAnimals() {
        List<Animal> allAnimals = new ArrayList<>();
        for (List<Animal> animalList : animals.values()) {
            allAnimals.addAll(animalList);
        }

        for (Animal currentAnimal : allAnimals) {
            MoveDirection gene = currentAnimal.getGenotype().getCurrentGene();
            currentAnimal.getGenotype().incrementIndex();

            this.move(currentAnimal, gene);
        }
    }

    @Override
    public abstract Boundary getCurrentBounds();

    @Override
    public String toString() {
        Boundary currentBounds = getCurrentBounds();
        return visualizer.draw(currentBounds.lowerLeft(), currentBounds.upperRight());
    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    @Override
    public String getId() {
        return id;
    }



}