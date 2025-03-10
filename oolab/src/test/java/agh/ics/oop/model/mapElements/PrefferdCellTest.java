package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.enums.ElementType;
import agh.ics.oop.model.util.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrefferdCellTest {
    private Vector2d position;
    private PrefferdCell prefferdCell;

    @BeforeEach
    void setUp() {
        position = new Vector2d(3, 4);  // Example position for the preferred cell
        prefferdCell = new PrefferdCell(position);
    }

    @Test
    void testConstructor() {
        assertNotNull(prefferdCell); // Ensure the PrefferdCell object is correctly initialized
        assertEquals(position, prefferdCell.getPosition()); // Position should match the one passed in constructor
    }

    @Test
    void testGetPosition() {
        assertEquals(position, prefferdCell.getPosition()); // Ensure the position returned is correct
    }

    @Test
    void testToString() {
        assertEquals("=", prefferdCell.toString()); // Ensure the toString method returns "="
    }

    @Test
    void testGetImageResource() {
        String expectedImageResource = "empty.png";  // Since it returns "empty.png" based on the code
        assertEquals(expectedImageResource, prefferdCell.getImageResource()); // Ensure the image resource is correct
    }

    @Test
    void testGetType() {
        assertEquals(ElementType.PREFERRED_CELL, prefferdCell.getType()); // Ensure the type returned is PREFERRED_CELL
    }

}