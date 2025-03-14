package agh.ics.oop.model.util;

import agh.ics.oop.model.enums.MutationType;

public class MutationStrategyFactory {
    public static MutationStrategy getStrategy(MutationType type) {
        return switch (type) {
            case FULLRANDOM -> new FullRandomMutationStrategy();
            case LITLLECHANGE -> new LittleChangeMutationStrategy();
            default -> throw new IllegalArgumentException("Unknown mutation type: " + type);
        };
    }
}