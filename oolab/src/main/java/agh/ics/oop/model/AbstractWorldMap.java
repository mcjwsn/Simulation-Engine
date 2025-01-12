package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.modes.MapType;
import agh.ics.oop.model.util.*;
import agh.ics.oop.model.util.MapVisualizer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.UUID;

import static agh.ics.oop.OptionsParser.parse;

public abstract class AbstractWorldMap implements WorldMap {
    protected Map<Vector2d, List<Animal>> animals = new ConcurrentHashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    protected List<MapChangeListener> observers = new CopyOnWriteArrayList<>();
    protected String id;
    protected HashMap<Vector2d, Grass> grass;
    protected int width;
    protected int height;
    protected Simulation simulation;
    SimulationProperties simulationProperties;

    protected List<Vector2d> freePositionsForPlants = new ArrayList<>();

    public AbstractWorldMap() {
        this.id = UUID.randomUUID().toString();
        this.animals = new ConcurrentHashMap<>();
        this.grass = new HashMap<>();
        this.observers = new CopyOnWriteArrayList<>();
        this.freePositionsForPlants = new ArrayList<>();
    }

    public AbstractWorldMap(SimulationProperties simulationProperties){
        animals = new HashMap<>();
        grass = new HashMap<>();
        observers = new LinkedList<>();

        width = simulationProperties.getMapWidth();
        height = simulationProperties.getMapHeight();

        this.simulationProperties = simulationProperties;

        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                Vector2d position = new Vector2d(x,y);
                freePositionsForPlants.add(position);
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position)
    {
        return true;
    }

     public void place(Vector2d animalPosition,Animal animal) {
        //animals.computeIfAbsent(animal.getPosition(), pos -> new CopyOnWriteArrayList<>()).add(animal);
        //notifyObservers("Animal placed at " + animal.getPosition());
        if (animals.containsKey(animalPosition)) {
            synchronized (this) {
                animals.get(animalPosition).add(animal);
                animals.get(animalPosition).sort(
                        Comparator.comparing(Animal::getEnergy, Comparator.reverseOrder())
                                .thenComparing(Animal::getAge, Comparator.reverseOrder())
                                .thenComparing(Animal::getPlantsEaten, Comparator.reverseOrder())
                );
            }
        }
        else {
            List<Animal> animalList = new ArrayList<>();
            animalList.add(animal);
            synchronized (this) {
                animals.put(animalPosition, animalList);
            }
        }
    }

    public synchronized void move(Animal animal)  {
        Vector2d oldPosition = animal.getPosition();
        animals.get(oldPosition).remove(animal);
        animal.move(this);
        Vector2d newPosition = animal.getPosition();
        place(newPosition, animal);
        List<Animal> oldPositionAnimals = animals.get(oldPosition);
        if (oldPositionAnimals != null) {
            oldPositionAnimals.remove(animal);
            if (oldPositionAnimals.isEmpty()) {
                animals.remove(oldPosition);
            }
        }
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

    public void mapChanged(String msg) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, msg);
        }
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

    public abstract MapType getMapType();

    public Map<Vector2d, List<Animal>> getAnimals(){
        return animals;
    }

    public synchronized void removeAnimal(Animal animal) {
        animals.get(animal.getPosition()).remove(animal);
    }
    public synchronized void placeGrass(Vector2d plantPosition, Grass grassObject) {
        grass.put(plantPosition, grassObject);
        freePositionsForPlants.remove(plantPosition);
    }
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public Integer getGrassNumber() {
        return grass.size();
    }

    public Integer getEmptyCount() {
        Set<Vector2d> position = new HashSet<>();
        for (Vector2d pos : animals.keySet())
            if (!animals.get(pos).isEmpty())
                position.add(pos);
        position.addAll(grass.keySet());
        return width*height - position.size();
    }

    public HashMap<Vector2d, Grass> getPlants() { return grass; }

    public List<Vector2d> getFreePositionsForPlants() { return freePositionsForPlants; }


    public int getWidth() {
        return width;
    }

    public int getHeight(){
        return height;
    }
}