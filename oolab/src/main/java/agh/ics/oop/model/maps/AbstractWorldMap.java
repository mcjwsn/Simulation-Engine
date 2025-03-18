package agh.ics.oop.model.maps;

import agh.ics.oop.model.mapElements.MovementStrategy;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.model.util.Statistics;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.Grass;
import agh.ics.oop.model.mapElements.WorldElement;
import agh.ics.oop.model.util.*;
import agh.ics.oop.simulation.SimulationProperties;

import java.util.*;
import java.util.UUID;

public abstract class AbstractWorldMap implements WorldMap {
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected final List<Animal> deadAnimals = new ArrayList<>();
    protected List<MapChangeListener> observers = new ArrayList<>();
    protected String id;
    protected HashMap<Vector2d, Grass> grass;
    protected int width;
    protected int height;
    protected Simulation simulation;
    protected SimulationProperties simulationProperties;
    protected MapStatisticsCalculator statisticsCalculator;

    protected List<Vector2d> freePositionsForPlants = new ArrayList<>();

    public AbstractWorldMap(SimulationProperties simulationProperties){
        this.id = UUID.randomUUID().toString();
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
    public void place(Vector2d animalPosition, Animal animal) {
        animals.computeIfAbsent(animalPosition, k -> new ArrayList<>()).add(animal);
    }

    public void prepareForProcessing() {
        animals.values().forEach(animalList ->
                animalList.sort(
                        Comparator.comparing(Animal::getEnergy, Comparator.reverseOrder())
                                .thenComparing(Animal::getAge, Comparator.reverseOrder())
                                .thenComparing(Animal::getPlantsEaten, Comparator.reverseOrder())
                )
        );
    }

    @Override
    public synchronized void move(Animal animal) {
        Vector2d oldPosition = animal.getPosition();
        List<Animal> oldPositionAnimals = animals.get(oldPosition);
        oldPositionAnimals.remove(animal);

        // Clean up empty lists
        if (oldPositionAnimals.isEmpty()) {
            animals.remove(oldPosition);
        }

        // Move the animal
        animal.move(this);
        Vector2d newPosition = animal.getPosition();

        animals.computeIfAbsent(newPosition, k -> new ArrayList<>()).add(animal);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        List<Animal> positionAnimals = animals.get(position);
        return positionAnimals != null && !positionAnimals.isEmpty();
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        List<Animal> positionAnimals = animals.get(position);
        if (positionAnimals != null && !positionAnimals.isEmpty()) {
            return Optional.ofNullable(positionAnimals.getFirst());
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
    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    @Override
    public void mapChanged(Statistics statistics, String msg) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, msg, statistics);
        }
    }

    // For backward compatibility, delegate to statistics calculator
    @Override
    public List<Integer> getMostPopularGenotype() {
        ensureStatisticsCalculator();
        return statisticsCalculator.getMostPopularGenotype();
    }

    // New method to set statistics using the calculator
    public void setStatistics(Statistics stats, int newDay) {
        ensureStatisticsCalculator();
        statisticsCalculator.updateStatistics(stats, newDay);
    }

    private void ensureStatisticsCalculator() {
        if (statisticsCalculator == null) {
            statisticsCalculator = new MapStatisticsCalculator(this);
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public abstract MapType getMapType();

    @Override
    public Map<Vector2d, List<Animal>> getAnimals(){
        return animals;
    }

    @Override
    public List<Animal> getDeadAnimals() {
        return Collections.unmodifiableList(deadAnimals);
    }

    public synchronized void removeAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        animals.get(animal.getPosition()).remove(animal);
        if (animals.get(position).isEmpty()) {
            animals.remove(position);
        }
        deadAnimals.add(animal);
    }

    public synchronized void placeGrass(Vector2d plantPosition, Grass grassObject) {
        grass.put(plantPosition, grassObject);
        freePositionsForPlants.remove(plantPosition);
    }


    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public Simulation getSimulation() {
        return simulation;
    }

    public Integer getEmptyCount() {
        Set<Vector2d> position = new HashSet<>();
        for (Vector2d pos : animals.keySet())
            if (!animals.get(pos).isEmpty())
                position.add(pos);
        position.addAll(grass.keySet());
        return width*height - position.size();
    }

    @Override
    public HashMap<Vector2d, Grass> getPlants() {
        return grass;
    }

    public List<Vector2d> getFreePositionsForPlants() {
        return freePositionsForPlants;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight(){
        return height;
    }

    @Override
    public abstract MovementStrategy getMovementStrategy();
}