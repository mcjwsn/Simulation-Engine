package agh.ics.oop.model.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConvertUtilsTest {

    @Test
    void testNumberToStringWithRoundedValue() {
        // Test case to round a number to 3 decimal places and remove trailing zeros
        double number = 12.3456789;
        String result = ConvertUtils.numberToString(number);
        assertEquals("12.346", result);  // Rounded to 3 decimal places
    }

    @Test
    void testNumberToStringWithNoTrailingZeros() {
        // Test case where number does not have trailing zeros after rounding
        double number = 15.00;
        String result = ConvertUtils.numberToString(number);
        assertEquals("15", result);  // Trailing zeros should be removed
    }

    @Test
    void testNumberToStringWithZero() {
        // Test case for zero
        double number = 0.0;
        String result = ConvertUtils.numberToString(number);
        assertEquals("0", result);  // Zero should remain as "0"
    }

    @Test
    void testConvertGenotypeToString() {
        // Test case for converting a genotype list to string
        List<Integer> genotype = Arrays.asList(1, 0, 1, 1, 0);
        String result = ConvertUtils.convertGenotypeToString(genotype);
        assertEquals("(1 0 1 1 0)", result);  // Expected format: "(1 0 1 1 0)"
    }

    @Test
    void testConvertGenotypeToStringWithOneElement() {
        // Test case for a genotype list with a single element
        List<Integer> genotype = Arrays.asList(1);
        String result = ConvertUtils.convertGenotypeToString(genotype);
        assertEquals("(1)", result);  // Expected format: "(1)"
    }
}
