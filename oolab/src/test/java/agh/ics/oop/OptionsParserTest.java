package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void parse_f_toForward() {
        //Given
        String[] args = {"f"};
        List<MoveDirection> expected = List.of(MoveDirection.FORWARD);
        //When
        List<MoveDirection> actual = OptionsParser.parse(args);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void parse_b_toBackward() {
        //Given
        String[] args = {"b"};
        List<MoveDirection> expected = List.of(MoveDirection.BACKWARD);
        //When
        List<MoveDirection> actual = OptionsParser.parse(args);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void parse_l_toLeft() {
        //Given
        String[] args = {"l"};
        List<MoveDirection> expected = List.of(MoveDirection.LEFT);
        //When
        List<MoveDirection> actual = OptionsParser.parse(args);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void parse_r_toRight() {
        //Given
        String[] args = {"r"};
        List<MoveDirection> expected = List.of(MoveDirection.RIGHT);
        //When
        List<MoveDirection> actual = OptionsParser.parse(args);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void parse_X_toNothing(){
        String[] args = {"x"};
        List<MoveDirection> expected = List.of();
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(args));
    }
}