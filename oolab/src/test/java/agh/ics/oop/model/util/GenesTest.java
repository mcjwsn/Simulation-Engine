package agh.ics.oop.model.util;


import agh.ics.oop.model.enums.MoveDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class GenesTest {

    @Test
    void testGenesConstructorCreatesRandomGenes() {
        // Create a Genes object with a specific number of genes
        int numGenes = 10;
        Genes genes = new Genes(numGenes);

        // Ensure the number of genes created is correct
        assertEquals(numGenes, genes.getGenes().size());

        // Ensure the genes are randomly selected from the MoveDirection values
        for (MoveDirection gene : genes.getGenes()) {
            assertTrue(Arrays.asList(MoveDirection.values()).contains(gene));
        }
    }

    @Test
    void testGetCurrentGene() {
        // Create a Genes object with 5 genes
        Genes genes = new Genes(5);

        // Test that the current gene is the first one
        MoveDirection currentGene = genes.getCurrentGene();
        assertNotNull(currentGene);

        // Test that the current gene changes as we increment the index
        genes.incrementIndex();
        assertNotEquals(currentGene, genes.getCurrentGene());
    }

    @Test
    void testIncrementIndex() {
        // Create a Genes object with 3 genes
        Genes genes = new Genes(3);

        // Verify the starting index is 0
        assertEquals(0, genes.getCurrentGeneIndex());

        // Increment the index
        genes.incrementIndex();
        assertEquals(1, genes.getCurrentGeneIndex());

        // Increment the index again (should wrap around due to modulus)
        genes.incrementIndex();
        assertEquals(2, genes.getCurrentGeneIndex());

        genes.incrementIndex();
        assertEquals(0, genes.getCurrentGeneIndex()); // Should wrap around
    }

    @Test
    void testRandIntMethod() {
        // Test the randint method for random number generation
        int min = 1;
        int max = 5;
        int randomValue = Genes.randint(min, max);

        // Ensure the value is within the given range
        assertTrue(randomValue >= min && randomValue <= max);
    }

    @Test
    void testGetStartingGenes() {
        // Generate starting genes for a specific number of genes
        int numGenes = 8;
        int[] startingGenes = Genes.getStartingGenes(numGenes);

        // Verify the number of genes returned matches the requested count
        assertEquals(numGenes, startingGenes.length);

        // Ensure that all genes are within the expected range [0, 7]
        for (int gene : startingGenes) {
            assertTrue(gene >= 0 && gene <= 7);
        }
    }
}
