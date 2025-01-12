package agh.ics.oop.model;

import agh.ics.oop.model.modes.MutationType;

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

    public void incrementIndex(Animal animal) {
        currentGeneIndex++;
        currentGeneIndex%=genes.size();
    }
    public static int[] getStartingGenes(int genNumb) {
        Random random = new Random();
        int[] genes = new int[genNumb];
        for (int i = 0; i < genNumb; i++){
            genes[i] = random.nextInt(0, 7);
        }
        return genes;
    }

    public static int[] mixGenesFromParents(Animal parent1, Animal parent2, SimulationProperties simulationProperties) {
        Random random = new Random();
        int genesCount = simulationProperties.getGenesCount();
        MutationType mutationStyle = simulationProperties.getMutationType();
        int[] genes = new int[genesCount];
        int[] parent1Genes = parent1.getGenome();
        int[] parent2Genes = parent2.getGenome();
        int parent1Energy = parent1.getEnergy();
        int parent2Energy = parent2.getEnergy();
        int minMutation = simulationProperties.getMinMutation();
        int maxMutation = simulationProperties.getMaxMutation();

        int splitPoint = (int) (((double) parent1Energy / (double)(parent1Energy + parent2Energy))*genesCount);

        // wybor strony
        if (random.nextBoolean()){
            for (int i = 0; i < splitPoint; i++){
                genes[i] = parent1Genes[i];
            }
            for (int i = splitPoint; i < genesCount; i++){
                genes[i] = parent2Genes[i];
            }
        } else {
            for (int i = 0; i < splitPoint; i++){
                genes[i] = parent2Genes[i];
            }
            for (int i = splitPoint; i < genesCount; i++){
                genes[i] = parent1Genes[i];
            }
        }

        // mutacje

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < genes.length; i++) {indexes.add(i);}
        if (minMutation > genes.length) {minMutation = genes.length;}
        if (maxMutation > genes.length) {maxMutation = genes.length;}
        if (minMutation > maxMutation) {minMutation = maxMutation;}
        if (minMutation < 0) {minMutation = 0;}
        if (maxMutation < 0) {maxMutation = 0;}
        if (minMutation == 0 && maxMutation == 0) {return genes;}

        if (mutationStyle == MutationType.FULLRANDOM) {
            int numberOfMutations = random.nextInt(maxMutation - minMutation) + minMutation;
            for (int counter = 0; counter < numberOfMutations; counter++) {
                int i = indexes.remove(random.nextInt(indexes.size()));
                genes[i] = random.nextInt(8);
            }
        }
        // dwa sie zamieniaja miejscami
        if (mutationStyle == MutationType.LITLLECHANGE) {
            int numberOfMutations = random.nextInt(maxMutation - minMutation) + minMutation;

            for (int counter = 0; counter < Math.floor(numberOfMutations/2.0); counter++) {
                int i = indexes.remove(random.nextInt(indexes.size()));
                int j = indexes.remove(random.nextInt(indexes.size()));
                int a = genes[i];
                genes[i] = genes[j];
                genes[j] = a;
            }
        }
        return genes;
    }
}
