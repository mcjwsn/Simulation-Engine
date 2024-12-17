package agh.ics.oop;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.*;
import javafx.application.Platform;
import agh.ics.oop.presenter.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final  List<Animal> animals;
    private final List<MoveDirection> directions;
    private final WorldMap map;
    private MapChangeListener listener;

    public Simulation(List<MoveDirection> directions,List<Vector2d> positions, WorldMap map) {
        this.animals = new ArrayList<>();
        for (Vector2d position : positions) {
            try {
                Animal animal = new Animal(MapDirection.NORTH, position);
                map.place(animal);
                this.animals.add(animal);
            } catch (IncorrectPositionException e) {
                //System.out.println("Warning: " + e.getMessage());
                e.printStackTrace();
            }
        }
        this.directions = directions;
        this.map = map;
    }
    public void run(){
            for(int i = 0; i < directions.size(); i++){
                map.move(animals.get(i % animals.size()), directions.get(i));
                Platform.runLater(() -> {
                    if (listener != null) {
                        listener.mapChanged(map, "Animal moved to new position");
                    }});
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

        }

    }
    public List<Animal> getAnimals() {
        return List.copyOf(animals); // collections.unmodifiableList
    }
}

