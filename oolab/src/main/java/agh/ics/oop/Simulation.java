package agh.ics.oop;
import agh.ics.oop.model.*;
import agh.ics.oop.model.modes.MapType;
import agh.ics.oop.model.modes.MovinType;
import agh.ics.oop.model.modes.MutationType;
import agh.ics.oop.model.util.*;
import javafx.application.Platform;
import agh.ics.oop.presenter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Simulation implements Runnable {
    private final List<Animal> animals;
    private List<MoveDirection> directions;
    private final WorldMap map;
    private MapChangeListener listener;
    private int currentAnimalIndex = 0;

    public Simulation(List<MoveDirection> directions,List<Vector2d> positions, WorldMap map) {
        this.animals = new ArrayList<>();
        SimulationProperties simulationProperties = new SimulationProperties(5, 5, 1, 3, 5, 1, 10, 1, 15, MovinType.DEFAULT, MutationType.FULLRANDOM, MapType.GLOBE, 5, 4, 2, 1, 0, 2);
        for (Vector2d position : positions) {
            try {
                Animal animal = new Animal(position, simulationProperties);
                map.place(position,animal);
                this.animals.add(animal);
            } catch (IncorrectPositionException e) {
                e.printStackTrace();
            }
        }

        this.directions = directions;
        this.map = map;
    }
    public void run(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(true){
            map.moveAllAnimals();

            Platform.runLater(() -> {
                if (listener != null) {
                    listener.mapChanged(map, "Animal moved to new position");
                }});
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            currentAnimalIndex++;
            currentAnimalIndex %= animals.size();
        }

    }
    public List<Animal> getAnimals() {
        return List.copyOf(animals);
    }
}

