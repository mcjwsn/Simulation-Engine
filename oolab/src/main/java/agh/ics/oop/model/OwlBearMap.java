package agh.ics.oop.model;
import agh.ics.oop.model.modes.MapType;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.RandomPointsGenerator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class OwlBearMap extends AbstractWorldMap{
    private final Map<Vector2d, Grass> mapOfGrass = new HashMap<>();

    public OwlBearMap(int mapWidth, int mapHeight,int grassNumber) {
        // polozenie przestrzeni sowoniedzwiedza na mapie
        int locationIndex = findCoordinates(mapWidth, mapHeight);
        int submapLength = integerPart(Math.sqrt(mapWidth * mapHeight * 0.2));
        generateGrass(grassNumber);
    }

    public static int integerPart(double number) {
        return (int) number ;
    }
    // do poprawy bo pewnie nie dziala
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
    public Boundary getCurrentBounds() {
        Vector2d bottom = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Vector2d top = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        List<WorldElement> elements = getElements();
        for (WorldElement element: elements) {
            bottom = bottom.lowerLeft(element.getPosition());
            top = top.upperRight(element.getPosition());
        }
        return new Boundary(bottom, top);
    }

    @Override
    public MapType getMapType() {
        return MapType.OWLBEAR;
    }
}
// dodac reszte metod