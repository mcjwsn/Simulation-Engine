package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private int animalAmount = 0;
    private int plantsAmount = 0;
    private int freeFieldsAmount = 0;
    private List<List<Integer>> theMostPopularGenotypes = new ArrayList<>();
    private double averageAnimalsEnergy = 0.0;
    private double averageLifespan = 0.0;
    private double averageChildAmount = 0.0;
    private int daysPassed = 0;

    public void setStatisticsParameters(int animalAmount, int daysPassed) {
        this.animalAmount = animalAmount;
        this.daysPassed = daysPassed;
    }

//    public void setStatisticsParameters(int animalCount, int plantsCount, int freeFieldsCount,
//                                       List<List<Integer>> mostPopularGenotype, double averageAnimalEnergy,
//                                       double lifeExpectancy, double averageChildCount, int dayCount) {
//        this.animalAmount = animalAmount;
//        this.plantsAmount = plantsAmount;
//        this.freeFieldsAmount = freeFieldsAmount;
//        this.theMostPopularGenotypes = theMostPopularGenotypes;
//        this.averageAnimalsEnergy = averageAnimalsEnergy;
//        this.averageLifespan = averageLifespan;
//        this.averageChildAmount = averageChildAmount;
//        this.daysPassed = daysPassed;
//    }

    public int getAnimalAmount() {
        return animalAmount;
    }

    public int getPlantsAmount() {
        return plantsAmount;
    }

    public int getFreeFieldsAmount() {
        return freeFieldsAmount;
    }

    public List<List<Integer>> getTheMostPopularGenotypes() {
        return theMostPopularGenotypes;
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
