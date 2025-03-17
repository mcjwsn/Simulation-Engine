package agh.ics.oop.model.maps;

import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.mapElements.Grass;
import agh.ics.oop.model.mapElements.MovementStrategy;
import agh.ics.oop.model.mapElements.WrappedMovementStrategy;
import agh.ics.oop.simulation.SimulationProperties;
import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.model.mapElements.WorldElement;
import agh.ics.oop.model.util.*;
import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> mapOfGrass = grass;

    public GrassField(SimulationProperties simulationProperties) {
        super(simulationProperties);
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        Optional<WorldElement> object = super.objectAt(position);
        if(object != null) return object;
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
        return new Boundary(new Vector2d(0,0), new Vector2d(height,width));
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

    @Override
    public MovementStrategy getMovementStrategy() {
        return new WrappedMovementStrategy();
    }

}