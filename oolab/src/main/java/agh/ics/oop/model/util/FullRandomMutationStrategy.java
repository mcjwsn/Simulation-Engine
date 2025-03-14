package agh.ics.oop.model.util;

import java.util.ArrayList;
import java.util.Random;

public class FullRandomMutationStrategy implements MutationStrategy {
    @Override
    public int[] mutate(int[] genes, int minMutation, int maxMutation) {
        Random random = new Random();
        int[] mutatedGenes = genes.clone();

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < genes.length; i++) {
            indexes.add(i);
        }

        int numberOfMutations = (minMutation == maxMutation) ?
                minMutation : random.nextInt(maxMutation - minMutation) + minMutation;

        for (int counter = 0; counter < numberOfMutations; counter++) {
            int i = indexes.remove(random.nextInt(indexes.size()));
            mutatedGenes[i] = random.nextInt(8);
        }

        return mutatedGenes;
    }
}

