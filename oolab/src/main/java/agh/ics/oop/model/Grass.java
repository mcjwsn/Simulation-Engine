package agh.ics.oop.model;

import agh.ics.oop.model.Enums.ElementType;

import java.util.Random;

public class Grass implements WorldElement{
    private Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public String getImageResource() {
        //Random random = new Random();
//        return switch(random.nextInt(5)){
//        case 0 -> "grass/grass1.jpg";
//        case 1 -> "grass/grass2.jpg";
//        case 2 -> "grass/grass3.jpg";
//        case 3 -> "grass/grass4.jpg";
//        case 4 -> "grass/grass5.jpg";
//        default -> "grass/grass.png"; // wizualizacja bledow
//        };
        return "grass/grassmix.jpg";
    }

    public ElementType getType() {
        return ElementType.GRASS;
    }
}
