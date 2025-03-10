package agh.ics.oop.model.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtils {

    public static String numberToString(double number) {
        double roundedNumber = Math.round(number * 1000) / 1000.0;
        return removeTrailingZeros(roundedNumber);
    }

    private static String removeTrailingZeros(double number) {
        return new BigDecimal(Double.toString(number)).stripTrailingZeros().toPlainString();
    }

    public static String convertGenotypeToString(List<Integer> genotype) {
        return "(" + genotype.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" ")) + ")";
    }
}