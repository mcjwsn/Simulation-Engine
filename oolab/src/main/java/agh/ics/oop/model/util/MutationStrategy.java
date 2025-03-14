package agh.ics.oop.model.util;

public interface MutationStrategy {
    int[] mutate(int[] genes, int minMutation, int maxMutation);
}