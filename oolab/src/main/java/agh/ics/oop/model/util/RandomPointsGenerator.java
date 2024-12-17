package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomPointsGenerator implements Iterable<Vector2d> {
    private final List<Vector2d> availablePositions;
    private final int count;
    private final Random random;

    public RandomPointsGenerator(int maxWidth, int maxHeight, int count) {
        if (count > maxWidth * maxHeight) {
            throw new IllegalArgumentException("Liczba pozycji nie może być większa niż liczba wszystkich możliwych pól.");
        }

        this.availablePositions = generateAllPositions(maxWidth, maxHeight);
        this.count = count;
        this.random = new Random();
    }

    private List<Vector2d> generateAllPositions(int maxWidth, int maxHeight) {
        List<Vector2d> positions = new ArrayList<>();
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                positions.add(new Vector2d(x, y));
            }
        }
        return positions;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<Vector2d>() {
            private int generatedCount = 0;

            @Override
            public boolean hasNext() {
                return generatedCount < count;
            }

            @Override
            public Vector2d next() {
                if (!hasNext()) {
                    throw new UnsupportedOperationException("Nie ma wiecej punktow");
                }

                int randomIndex = random.nextInt(availablePositions.size());
                Vector2d position = availablePositions.remove(randomIndex);

                generatedCount++;
                return position;
            }
        };
    }
}
