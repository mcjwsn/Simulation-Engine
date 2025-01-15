package agh.ics.oop.model;

import agh.ics.oop.model.modes.MapType;
import agh.ics.oop.model.util.*;
import agh.ics.oop.model.util.RandomPointsGenerator;
import java.util.*;

public class GrassField extends AbstractWorldMap{
    private final Map<Vector2d, Grass> mapOfGrass = grass;

    public GrassField(SimulationProperties simulationProperties) {
        super(simulationProperties);
    }

    private void generateGrass(int grassNumber){
        RandomPointsGenerator randomPositionGenerator = new RandomPointsGenerator((int) Math.sqrt(10 * grassNumber), (int) Math.sqrt(10 * grassNumber), 2 * grassNumber);
        for (Vector2d grassPosition : randomPositionGenerator) {
            mapOfGrass.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        Optional<WorldElement> object = super.objectAt(position);
        if(object.isPresent()) return object;
        return Optional.ofNullable(mapOfGrass.get(position));
    }

    @Override
    public boolean isOccupied(Vector2d position){
        if(!super.isOccupied(position)){
            return mapOfGrass.containsKey(position);
        }
        return true;
    }

    @Override
    public Boundary getCurrentBounds() {
        Vector2d bottom = new Vector2d(0,0);
        Vector2d top = new Vector2d(height,width);
        return new Boundary(bottom, top);
    }

    @Override
    public MapType getMapType() {
        return MapType.GLOBE;
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(mapOfGrass.values());
        return elements;
    }

}