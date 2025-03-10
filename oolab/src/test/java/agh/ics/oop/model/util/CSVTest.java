package agh.ics.oop.model.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

class CSVTest {

    @Test
    void testFormatPopularGenotypeWithNonEmptyList() {
        // Prepare a non-empty genotype list
        List<Integer> genotype = Arrays.asList(1, 0, 1, 0);

        // Call the private method indirectly using fillStatisticsDay
        String result = CSV.formatPopularGenotype(genotype);

        // Verify that the genotype is formatted correctly
        assertEquals("(1 0 1 0)", result);
    }

    @Test
    void testFormatPopularGenotypeWithEmptyList() {
        // Prepare an empty genotype list
        List<Integer> genotype = Arrays.asList();

        // Call the private method indirectly using fillStatisticsDay
        String result = CSV.formatPopularGenotype(genotype);

        // Verify that the result is "Null" for an empty genotype list
        assertEquals("Null", result);
    }

    @Test
    void testFormatPopularGenotypeWithNullList() {
        // Prepare a null genotype list
        List<Integer> genotype = null;

        // Call the private method indirectly using fillStatisticsDay
        String result = CSV.formatPopularGenotype(genotype);

        // Verify that the result is "Null" for a null genotype list
        assertEquals("Null", result);
    }
}
