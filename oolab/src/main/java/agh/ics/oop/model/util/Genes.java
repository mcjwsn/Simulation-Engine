package agh.ics.oop.model.util;

import agh.ics.oop.model.enums.MoveDirection;
import agh.ics.oop.model.enums.MutationType;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.simulation.SimulationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genes {
    private final List<MoveDirection> genes;
    private int currentGeneIndex = 0;

    public Genes(int n) {
        this.genes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            genes.add(MoveDirection.values()[randint(0, 7)]);
        }
    }

    public static int randint(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public MoveDirection getCurrentGene() {
        return genes.get(currentGeneIndex);
    }

    public void incrementIndex() {
        currentGeneIndex++;
        currentGeneIndex%=genes.size();
    }
    public static int[] getStartingGenes(int number) {
        Random random = new Random();
        int[] genes = new int[number];
        for (int i = 0; i < number; i++){
            genes[i] = random.nextInt(0, 7);
        }
        return genes;
    }

    public static int[] mixGenesFromParents(Animal parent1, Animal parent2, SimulationProperties simulationProperties) {
        Random random = new Random();
        int genesCount = simulationProperties.getGenesCount();
        int[] genes = new int[genesCount];
        int[] parent1Genes = parent1.getGenome();
        int[] parent2Genes = parent2.getGenome();
        int parent1Energy = parent1.getEnergy();
        int parent2Energy = parent2.getEnergy();
        int minMutation = simulationProperties.getMinMutation();
        int maxMutation = simulationProperties.getMaxMutation();

        int splitPoint = (int) (((double) parent1Energy / (double)(parent1Energy + parent2Energy))*genesCount);

        if (random.nextBoolean()){
            if (splitPoint >= 0) System.arraycopy(parent1Genes, 0, genes, 0, splitPoint);
            if (genesCount - splitPoint >= 0)
                System.arraycopy(parent2Genes, splitPoint, genes, splitPoint, genesCount - splitPoint);
        } else {
            if (splitPoint >= 0) System.arraycopy(parent2Genes, 0, genes, 0, splitPoint);
            if (genesCount - splitPoint >= 0)
                System.arraycopy(parent1Genes, splitPoint, genes, splitPoint, genesCount - splitPoint);
        }

        // Validate mutation parameters
        minMutation = Math.min(minMutation, genes.length);
        maxMutation = Math.min(maxMutation, genes.length);
        minMutation = Math.max(0, minMutation);
        maxMutation = Math.max(0, maxMutation);

        if (minMutation > maxMutation) minMutation = maxMutation;
        if (minMutation == 0 && maxMutation == 0) return genes;

        // Use the strategy pattern for mutation
        MutationStrategy strategy = MutationStrategyFactory.getStrategy(simulationProperties.getMutationType());
        return strategy.mutate(genes, minMutation, maxMutation);
    }

    public int getCurrentGeneIndex() {
        return currentGeneIndex;
    }

    public List<MoveDirection> getGenes() {
        return genes;
    }
}
