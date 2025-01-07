package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {

    public static List<MoveDirection> parse(String[] args) {
        List<MoveDirection> list = new ArrayList<>();
        for (String arg : args) {
            switch (arg) {
                case "0" -> list.add(MoveDirection.ZERO);
                case "1" -> list.add(MoveDirection.ONE);
                case "2" -> list.add(MoveDirection.TWO);
                case "3" -> list.add(MoveDirection.THREE);
                case "4" -> list.add(MoveDirection.FOUR);
                case "5" -> list.add(MoveDirection.FIVE);
                case "6" -> list.add(MoveDirection.SIX);
                case "7" -> list.add(MoveDirection.SEVEN);
                default -> throw new IllegalArgumentException(arg +
                        " is not a legal move specification");
            }
        }
        return list;
    }
}
