package agh.ics.oop;
import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {

    public static List<MoveDirection> parse(String[] args) {
        List<MoveDirection> list = new ArrayList<>();
        for (String arg : args) {
            switch (arg) {
                case "f" -> list.add(MoveDirection.FORWARD);
                case "b" -> list.add(MoveDirection.BACKWARD);
                case "l" -> list.add(MoveDirection.LEFT);
                case "r" -> list.add(MoveDirection.RIGHT);
                default -> throw new IllegalArgumentException(arg +
                        " is not legal move specification");
            }
        }
        return list;
    }
}
