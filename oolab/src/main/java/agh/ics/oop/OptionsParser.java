package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OptionsParser {
    public static List<MoveDirection> parse(String[] args) {
        return Arrays.stream(args)
                .map(arg -> switch (arg) {
                    case "0" -> MoveDirection.ZERO;
                    case "1" -> MoveDirection.ONE;
                    case "2" -> MoveDirection.TWO;
                    case "3" -> MoveDirection.THREE;
                    case "4" -> MoveDirection.FOUR;
                    case "5" -> MoveDirection.FIVE;
                    case "6" -> MoveDirection.SIX;
                    case "7" -> MoveDirection.SEVEN;
                    default -> throw new IllegalArgumentException(arg + " is not a legal move specification");
                }).collect(Collectors.toList());
    }
}