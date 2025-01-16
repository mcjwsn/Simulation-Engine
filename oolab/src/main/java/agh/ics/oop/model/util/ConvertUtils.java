package agh.ics.oop.model.util;
import java.math.BigDecimal;
import java.util.List;


public class ConvertUtils {

    public static String numberToString(double number) {
        double roundedNumber = Math.round(number * 1000) / 1000.0;
        return removeTrailingZeros(roundedNumber);
    }

    private static String removeTrailingZeros(double number) {
        return new BigDecimal(Double.toString(number)).stripTrailingZeros().toPlainString();
    }

    public static String convertGenotypeToString(List<Integer> genotype) {
        StringBuilder convertedString = new StringBuilder("(");

        for (Integer integer : genotype) {
            convertedString.append(integer).append(" ");
        }

        return convertedString.deleteCharAt(convertedString.length() - 1).append(")").toString();
    }
}