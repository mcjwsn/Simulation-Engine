package agh.ics.oop.model;

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
}
