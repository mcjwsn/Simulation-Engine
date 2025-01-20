package agh.ics.oop.model.util;

import agh.ics.oop.Statistics;

import java.io.PrintWriter;
import java.util.List;

public class CSV {

    public static void writeCSVHeader(PrintWriter writer) {
        writer.println("World ID;Day;Animals;Grasses;Free Fields;Popular Genotype;Average Animals Energy;Average Animals Age;Average Animals Children Count");
    }

    public static void fillStatisticsDay(PrintWriter writer, String worldId, Statistics statistics) {
        writer.println(worldId + ";" +
                statistics.getDaysPassed() + ";" +
                statistics.getAnimalAmount() + ";" +
                statistics.getGrassesAmount() + ";" +
                statistics.getFreeFieldsAmount() + ";" +
                formatPopularGenotype(statistics.getTheMostPopularGenotype()) + ";" +
                ConvertUtils.numberToString(statistics.getAverageAnimalsEnergy()) + ";" +
                ConvertUtils.numberToString(statistics.getAverageLifespan()) + ";" +
                ConvertUtils.numberToString(statistics.getAverageChildAmount())
        );
    }

    private static String formatPopularGenotype(List<Integer> genotypes) {
        if (genotypes == null || genotypes.isEmpty()) {
            return "Null";
        }
        return ConvertUtils.convertGenotypeToString(genotypes);
    }

}
