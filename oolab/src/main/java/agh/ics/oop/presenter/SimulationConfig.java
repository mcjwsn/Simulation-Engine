package agh.ics.oop.presenter;


import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.enums.MutationType;

/**
 * Record representing the simulation configuration values.
 * This immutable data structure is used for importing/exporting configurations.
 */
public record SimulationConfig(
        int mapWidth,
        int mapHeight,
        int equatorHeight,
        int grassNumber,
        int energyAddition,
        int plantRegeneration,
        int numberOfAnimals,
        int startingAnimalEnergy,
        int energyNeededForReproduction,
        int energyLosingWithReproduction,
        int minGenMutations,
        int maxGenMutations,
        int genomeLength,
        MutationType mutationType,
        int maxEnergy,
        MapType mapType,
        int moveEnergy,
        String csvSaveStats
) {}