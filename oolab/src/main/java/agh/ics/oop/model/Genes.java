package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class Genes {
    private List<MoveDirection> genes; // Store the genes
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

    public int getGenesCount() {
        return genes.size();
    }
}
