package agh.ics.oop.model;
import agh.ics.oop.model.modes.MapType;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.RandomPointsGenerator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class OwlBearMap extends AbstractWorldMap{
    private final Map<Vector2d, Grass> mapOfGrass = grass;

    public OwlBearMap(SimulationProperties simulationProperties) {
        super(simulationProperties);
    }

    public WorldElement objectAt(Vector2d position) {
        WorldElement object = super.objectAt(position);
        if(object != null) return object;
        return mapOfGrass.get(position);
    }


    public boolean isOccupied(Vector2d position){
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

    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(mapOfGrass.values());
        return elements;
    }

    public static int integerPart(double number) {
        return (int) number ;
    }

    public int findCoordinates(int mapWidth, int mapHeight) {
        int value = integerPart(Math.sqrt(mapWidth * mapHeight * 0.2)) + 1;
        // maksymalny indeks
        int possibleIndex = Math.min(mapWidth,mapHeight) - value;
        Random random = new Random();
        return random.nextInt(possibleIndex+1);
    }

    private void generateGrass(int grassNumber){
        RandomPointsGenerator randomPositionGenerator = new RandomPointsGenerator((int) Math.sqrt(10 * grassNumber), (int) Math.sqrt(10 * grassNumber), 2 * grassNumber);
        for (Vector2d grassPosition : randomPositionGenerator) {
            mapOfGrass.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public MapType getMapType() {
        return MapType.OWLBEAR;
    }
}
// dodac reszte metod