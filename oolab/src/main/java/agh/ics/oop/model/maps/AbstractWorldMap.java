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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.UUID;

public abstract class AbstractWorldMap implements WorldMap {
    protected Map<Vector2d, List<Animal>> animals = new ConcurrentHashMap<>();
    protected List<Animal> deadAnimals = new CopyOnWriteArrayList<>();
    protected List<MapChangeListener> observers = new CopyOnWriteArrayList<>();
    protected String id;
    protected HashMap<Vector2d, Grass> grass;
    protected int width;
    protected int height;
    protected Simulation simulation;
    SimulationProperties simulationProperties;

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
     public void place(Vector2d animalPosition,Animal animal) {
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
        List<Animal> oldPositionAnimals = animals.get(oldPosition);
        if (oldPositionAnimals != null) {
            oldPositionAnimals.remove(animal);
            if (oldPositionAnimals.isEmpty()) {
                animals.remove(oldPosition);
            }
        }
        place(newPosition, animal);
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

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void mapChanged(Statistics statistics, String msg) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, msg, statistics);
        }
    }

    public void setStatistics(Statistics stats, int newDay) {
        stats.setStatisticsParameters(this.getNumberOfAnimals(),
                this.getNumberOfGrasses(),
                this.getNumberOfFreeFields(),
                this.getMostPopularGenotype(),
                this.getAverageAliveAnimalsEnergy(),
                this.getAverageAnimalLifeSpan(),
                this.getAverageChildrenAmount(),
                newDay);
    }

    private int getNumberOfAnimals() {
        return simulation.getAnimals().size();
}

    private int getNumberOfGrasses() {
        return grass.size();}

    protected int getNumberOfFreeFields() {
        Set<Vector2d> usedPositions = new HashSet<>();
        usedPositions.addAll(animals.keySet());
        usedPositions.addAll(grass.keySet());
        return (width+1) * (height+1) - usedPositions.size();
    }

    @Override
    public List<Integer> getMostPopularGenotype() {
        List<Animal> animalsList = simulation.getAnimals();
        Map<List<Integer>, Integer> genotypePopularity = new HashMap<>();

        for (Animal animal : animalsList) {
            List<Integer> genotype = Arrays.stream(animal.getGenome())
                    .boxed()
                    .toList();

            genotypePopularity.put(genotype, genotypePopularity.getOrDefault(genotype, 0) + 1);
        }

        return genotypePopularity.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(Collections.emptyList());
    }

    protected int getAverageAliveAnimalsEnergy()
    {
        List<Animal> animalsList = simulation.getAnimals();
        if (animalsList == null || animalsList.isEmpty()) {
            return 0;
        }
        int averageEnergy = 0;
        for (Animal animal : animalsList) {
            averageEnergy += animal.getEnergy();
        }
        return averageEnergy/animalsList.size();
    }

    protected int getAverageAnimalLifeSpan()
    {
        if(deadAnimals.isEmpty())
        {
            return 0;
        }
        int meanAge = 0;
        for(Animal animal : deadAnimals)
        {
            meanAge += animal.getAge();
        }
        return meanAge/deadAnimals.size();
    }

    protected double getAverageChildrenAmount()
    {
        List<Animal> animalsList = simulation.getAnimals();
        int avgChildrenAmount = 0;
        if (animalsList.isEmpty()) {
            return 0;
        }
        for(Animal animal : animalsList)
        {
            avgChildrenAmount += animal.getChildren().size();
        }
        return (double)avgChildrenAmount/animalsList.size();
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

    public abstract MovementStrategy getMovementStrategy();
}