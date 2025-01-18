package agh.ics.oop.model;

import agh.ics.oop.Statistics;

@FunctionalInterface
public interface MapChangeListener {
    void mapChanged(WorldMap worldMap, String message, Statistics statistics);
}
