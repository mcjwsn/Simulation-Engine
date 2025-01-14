package agh.ics.oop.model;

import agh.ics.oop.Statistics;

public class ConsoleMapDisplay implements MapChangeListener {
    private int updatesCounter = 0;

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message, Statistics statistics) {
            System.out.println("Update #" + (++updatesCounter) + ": " + message + " on Map with ID: " + worldMap.getId());
            System.out.println(worldMap);
    }
}