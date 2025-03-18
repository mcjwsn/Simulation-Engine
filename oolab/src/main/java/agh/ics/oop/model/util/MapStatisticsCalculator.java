package agh.ics.oop.model.util;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.maps.WorldMap;

import java.util.*;
import java.util.stream.Collectors;

public class MapStatisticsCalculator {

    private final WorldMap map;

    public MapStatisticsCalculator(WorldMap map) {
        this.map = map;
    }

    public int getNumberOfAnimals() {
        return map.getSimulation().getAnimals().size();
    }

    public int getNumberOfGrasses() {
        return map.getPlants().size();
    }

    public int getNumberOfFreeFields() {
        Set<Vector2d> usedPositions = new HashSet<>();
        usedPositions.addAll(map.getAnimals().keySet());
        usedPositions.addAll(map.getPlants().keySet());
        return (map.getWidth()+1) * (map.getHeight()+1) - usedPositions.size();
    }

    public List<Integer> getMostPopularGenotype() {
        List<Animal> animalsList = map.getSimulation().getAnimals();
        Map<List<Integer>, Integer> genotypePopularity = new HashMap<>();

        for (Animal animal : animalsList) {
            List<Integer> genotype = Arrays.stream(animal.getGenome())
                    .boxed()
                    .toList();

            genotypePopularity.put(genotype, genotypePopularity.getOrDefault(genotype, 0) + 1);
        }

        return genotypePopularity.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(Collections.emptyList());
    }

    public int getAverageAliveAnimalsEnergy() {
        List<Animal> animalsList = map.getSimulation().getAnimals();
        if (animalsList == null || animalsList.isEmpty()) {
            return 0;
        }
        int averageEnergy = 0;
        for (Animal animal : animalsList) {
            averageEnergy += animal.getEnergy();
        }
        return averageEnergy/animalsList.size();
    }

    public int getAverageAnimalLifeSpan() {
        List<Animal> deadAnimals = map.getDeadAnimals();
        if (deadAnimals.isEmpty()) {
            return 0;
        }
        int meanAge = 0;
        for (Animal animal : deadAnimals) {
            meanAge += animal.getAge();
        }
        return meanAge/deadAnimals.size();
    }

    public double getAverageChildrenAmount() {
        List<Animal> animalsList = map.getSimulation().getAnimals();
        int avgChildrenAmount = 0;
        if (animalsList.isEmpty()) {
            return 0;
        }
        for (Animal animal : animalsList) {
            avgChildrenAmount += animal.getChildren().size();
        }
        return (double)avgChildrenAmount/animalsList.size();
    }

    public void updateStatistics(Statistics stats, int newDay) {
        stats.setStatisticsParameters(
                getNumberOfAnimals(),
                getNumberOfGrasses(),
                getNumberOfFreeFields(),
                getMostPopularGenotype(),
                getAverageAliveAnimalsEnergy(),
                getAverageAnimalLifeSpan(),
                getAverageChildrenAmount(),
                newDay
        );
    }
}