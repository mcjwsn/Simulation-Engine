package agh.ics.oop.model;
import agh.ics.oop.model.modes.MapType;
import agh.ics.oop.model.util.Boundary;

import java.util.*;

public class OwlBearMap extends AbstractWorldMap{
    private final Map<Vector2d, Grass> mapOfGrass = grass;
    private OwlBear owlBear;
    private Vector2d lowerLeftBoundary;
    private Vector2d upperRightBoundary;

    public Vector2d getUpperRightBoundary() {
        return upperRightBoundary;
    }

    public Vector2d getLowerLeftBoundary() {
        return lowerLeftBoundary;
    }

    public OwlBearMap(SimulationProperties simulationProperties) {
        super(simulationProperties);
        findSubmapBounds(this.width, this.height);
        owlBear = new OwlBear(lowerLeftBoundary, simulationProperties);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if(owlBear.isAt(position)){
            return owlBear;
        }
        WorldElement object = super.objectAt(position);
        if(object != null) return object;
        return mapOfGrass.get(position);
    }

    @Override
    public boolean isOccupied(Vector2d position){
        if(owlBear.isAt(position)){
            return true;
        }
        if(!super.isOccupied(position)){
            return mapOfGrass.containsKey(position);
        }
        return true;
    }

    @Override
    public Boundary getCurrentBounds() {
        Vector2d bottom = new Vector2d(0,0);
        Vector2d top = new Vector2d(height, width);
        return new Boundary(bottom, top);
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(mapOfGrass.values());
        elements.add(owlBear);
        return elements;
    }

    public void moveOwlBear()
    {
        owlBear.move(this);
    }

    public Vector2d getOwlBearPosition() {
        return owlBear.getPosition();
    }

//    public Set<Animal> eatAnimals()
//    {
//        Set<Animal> deletedAnimals = new HashSet<>();
//        Set<Vector2d> keys = new HashSet<>(this.getAnimals().keySet());
//        for (Vector2d position : keys) {
//            if (owlBear.getPosition().equals(position)) {
//                List<Animal> animalList = this.getAnimals().get(position);
//                if (!animalList.isEmpty() && animalList != null) {
//                    for(Animal animal : animalList){
//                        deletedAnimals.add(animal);
//                        this.removeAnimal(animal);
//                    }
//
//                }
//                animals.remove(position);
//                break;
//            }
//        }
//        return deletedAnimals;
//    }


    public static int integerPart(double number) {
        return (int) number ;
    }

//    public int findCoordinates(int mapWidth, int mapHeight) {
//        int value = integerPart(Math.sqrt(mapWidth * mapHeight * 0.2)) + 1;
//        int possibleIndex = Math.min(mapWidth,mapHeight) - value;
//        Random random = new Random();
//        return random.nextInt(possibleIndex+1);
//    }
    public void findSubmapBounds(int mapWidth, int mapHeight) {
        double submapArea = mapWidth * mapHeight * 0.2;
        int sideLength = (int) Math.sqrt(submapArea);

        int maxX = mapWidth - sideLength;
        int maxY = mapHeight - sideLength;

        if (maxX < 0 || maxY < 0) {
            throw new IllegalArgumentException("Map too small for 20% submap");
        }

        Random random = new Random();
        int startX = random.nextInt(maxX + 1);
        int startY = random.nextInt(maxY + 1);

        int endX = startX + sideLength;
        int endY = startY + sideLength;

        upperRightBoundary = new Vector2d(endX, endY);
        lowerLeftBoundary = new Vector2d(startX, startY);
    }


//    private void generateGrass(int grassNumber){
//        RandomPointsGenerator randomPositionGenerator = new RandomPointsGenerator((int) Math.sqrt(10 * grassNumber), (int) Math.sqrt(10 * grassNumber), 2 * grassNumber);
//        for (Vector2d grassPosition : randomPositionGenerator) {
//            mapOfGrass.put(grassPosition, new Grass(grassPosition));
//        }
//    }

    @Override
    public MapType getMapType() {
        return MapType.OWLBEAR;
    }
}