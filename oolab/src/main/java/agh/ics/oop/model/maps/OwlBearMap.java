package agh.ics.oop.model.maps;

import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.mapElements.Grass;
import agh.ics.oop.model.mapElements.OwlBear;
import agh.ics.oop.model.mapElements.WorldElement;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.simulation.SimulationProperties;
import java.util.*;

public class OwlBearMap extends AbstractWorldMap {
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
    public Optional<WorldElement> objectAt(Vector2d position) {
        if(owlBear.isAt(position)){
            return Optional.ofNullable(owlBear);
        }
        Optional<WorldElement> object = super.objectAt(position);
        if(object != null) return object;
        return Optional.ofNullable(mapOfGrass.get(position));
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
        return new Boundary(new Vector2d(0,0), new Vector2d(height, width));
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

    @Override
    protected int getNumberOfFreeFields() {
        Set<Vector2d> usedPositions = new HashSet<>();
        usedPositions.addAll(animals.keySet());
        usedPositions.addAll(grass.keySet());
        usedPositions.add(owlBear.getPosition());
        return (width+1) * (height+1) - usedPositions.size();
    }

    public void findSubmapBounds(int mapWidth, int mapHeight) {
        double submapArea = mapWidth * mapHeight * 0.2;
        int sideLength = (int) Math.sqrt(submapArea);

        int maxX = mapHeight - sideLength;
        int maxY = mapWidth - sideLength;

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

    @Override
    public MapType getMapType() {
        return MapType.OWLBEAR;
    }
}