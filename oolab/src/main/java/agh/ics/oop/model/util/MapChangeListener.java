package agh.ics.oop.model.util;

import agh.ics.oop.model.maps.WorldMap;

@FunctionalInterface
public interface MapChangeListener {
    void mapChanged(WorldMap worldMap, String message, Statistics statistics);
}