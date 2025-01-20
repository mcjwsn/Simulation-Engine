package agh.ics.oop.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Statistics {
    private int animalAmount = 0;
    private int grassesAmount = 0;
    private int freeFieldsAmount = 0;
    private List<Integer> theMostPopularGenotype = new ArrayList<>();
    private double averageAnimalsEnergy = 0.0;
    private double averageLifespan = 0.0;
    private double averageChildAmount = 0.0;
    private int daysPassed = 0;

    public void setStatisticsParameters(int animalAmount,
                                        int grassesAmount,
                                        int freeFieldsAmount,
                                        List<Integer> theMostPopularGenotype,
                                        int averageAnimalsEnergy,
                                        int averageLifespan,
                                        double averageChildAmount,
                                        int daysPassed) {
        this.animalAmount = animalAmount;
        this.daysPassed = daysPassed;
        this.grassesAmount = grassesAmount;
        this.theMostPopularGenotype = theMostPopularGenotype;
        this.averageAnimalsEnergy = averageAnimalsEnergy;
        this.averageLifespan = averageLifespan;
        this.averageChildAmount = averageChildAmount;
        this.freeFieldsAmount = freeFieldsAmount;
    }
    public int getAnimalAmount() {
        return animalAmount;
    }

    public int getGrassesAmount() {
        return grassesAmount;
    }

    public int getFreeFieldsAmount() {
        return freeFieldsAmount;
    }

    public List<Integer> getTheMostPopularGenotype() {
        return theMostPopularGenotype;
    }

    public double getAverageAnimalsEnergy() {
        return averageAnimalsEnergy;
    }

    public double getAverageLifespan() {
        return averageLifespan;
    }

    public double getAverageChildAmount() {
        return averageChildAmount;
    }

    public int getDaysPassed() {
        return daysPassed;
    }

}