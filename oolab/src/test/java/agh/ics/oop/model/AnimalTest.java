package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void isChangingtoStringCorrect() {
        Animal animal = new Animal();
        assert animal.toString().equals("N");
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(2, 2));
        assert animal2.toString().equals("S");
        Animal animal3 = new Animal(MapDirection.EAST, new Vector2d(2, 2));
        assert animal3.toString().equals("E");
        Animal animal4 = new Animal(MapDirection.WEST, new Vector2d(2, 2));
        assert animal4.toString().equals("W");
    }

    @Test
    void isPositionCorrect() {
        Animal animal = new Animal();
        assert animal.getPosition().equals(new Vector2d(2, 2));
    }

    @Test
    void areOrientationsCorrect() {
        Animal animal = new Animal();
        assert animal.getOrientation().equals(MapDirection.NORTH);
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(2, 2));
        assert animal2.getOrientation().equals(MapDirection.SOUTH);
        Animal animal3 = new Animal(MapDirection.EAST, new Vector2d(2, 2));
        assert animal3.getOrientation().equals(MapDirection.EAST);
        Animal animal4 = new Animal(MapDirection.WEST, new Vector2d(2, 2));
        assert animal4.getOrientation().equals(MapDirection.WEST);

    }

    @Test
    void isAnimalAt() {
        Animal animal = new Animal(MapDirection.SOUTH, new Vector2d(2, 2));
        assert animal.isAt(new Vector2d(2, 2));
    }
}