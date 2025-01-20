package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.enums.ElementType;
import agh.ics.oop.model.util.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyCellTest {
    private Vector2d position;
    private EmptyCell emptyCell;

    @BeforeEach
    void setUp() {
        position = new Vector2d(1, 2);  // Example position for the empty cell
        emptyCell = new EmptyCell(position);
    }

    @Test
    void testConstructor() {
        assertNotNull(emptyCell); // Ensure the empty cell is correctly initialized
        assertEquals(position, emptyCell.getPosition()); // Position should match the one passed in constructor
    }

    @Test
    void testGetPosition() {
        assertEquals(position, emptyCell.getPosition()); // Ensure the position returned is correct
    }

    @Test
    void testToString() {
        assertEquals(" ", emptyCell.toString()); // Ensure the toString method returns a space
    }

    @Test
    void testGetImageResource() {
        String expectedImageResource = "empty.png";
        assertEquals(expectedImageResource, emptyCell.getImageResource()); // Ensure the image resource returned is correct
    }

    @Test
    void testGetType() {
        assertEquals(ElementType.EMPTY_CELL, emptyCell.getType()); // Ensure the type returned is EMPTY_CELL
    }

}