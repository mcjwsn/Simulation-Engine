package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassTest {

    @Test
    void isGrassPositionCorrect() {
        Grass grass = new Grass(new Vector2d(2, 2));
        assert grass.getPosition().equals(new Vector2d(2, 2));
    }

    @Test
    void isToStringCorrect() {
        Grass grass = new Grass(new Vector2d(2, 2));
        assert grass.toString().equals("*");
    }
}