package agh.ics.oop.model;
import agh.ics.oop.model.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import agh.ics.oop.model.util.IncorrectPositionException;

public class GrassFieldTest {
    @Test
    public void DoesMapWork() {
        WorldMap map = new GrassField(10);
        Animal animal = new Animal();
        assertDoesNotThrow(() -> map.place(animal));
        map.move(animal, MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 3), animal.getPosition());
    }
    @Test
    public void CanAnimalMove1() {
        WorldMap map = new GrassField(4);
        Animal animal1 = new Animal();
        assertDoesNotThrow(() -> map.place(animal1));
        assertTrue(map.canMoveTo(new Vector2d(2, 3)));
        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
        map.move(animal1, MoveDirection.FORWARD);
        assertFalse(map.canMoveTo(new Vector2d(2, 3)));
        assertTrue(map.canMoveTo(new Vector2d(2, 2)));
        assertTrue(map.canMoveTo(new Vector2d(2, 4)));
    }
    @Test
    public void CanAnimalMoveOnOtherAnimal(){
        WorldMap map = new GrassField(10);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(2, 3));
        assertDoesNotThrow(() -> map.place(animal1));
        assertDoesNotThrow(() -> map.place(animal2));
        assertFalse(map.canMoveTo(new Vector2d(2, 3)));
    }
    @Test
    public void CanBePlaced(){
        WorldMap map = new GrassField(10);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(2, 2));
        assertDoesNotThrow(() -> map.place(animal1));
        assertThrows(IncorrectPositionException.class, () -> map.place(animal2));
    }
    @Test
    public void AreAnimalsMovingCorrectly(){
        WorldMap map = new GrassField(10);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(3, 2));
        assertDoesNotThrow(() -> map.place(animal1));
        assertDoesNotThrow(() -> map.place(animal2));
        map.move(animal1, MoveDirection.FORWARD);
        map.move(animal2, MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 3), animal1.getPosition());
        assertEquals(new Vector2d(3, 1), animal2.getPosition());
    }
    @Test
    public void IsPlaceFree(){
        WorldMap map = new GrassField(10);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(2, 2));
        assertDoesNotThrow(() -> map.place(animal1));
        assertThrows(IncorrectPositionException.class, () -> map.place(animal2));
        assertTrue(map.isOccupied(new Vector2d(2, 2)));
    }
    @Test
    public void IsSthHere(){
        WorldMap map = new GrassField(1);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(2, 2));
        assertDoesNotThrow(() -> map.place(animal1));
        assertThrows(IncorrectPositionException.class, () -> map.place(animal2));
        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)));
    }

    @Test
    public void GettingAllElements(){
        WorldMap map = new GrassField(100);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(2, 3));
        assertDoesNotThrow(() -> map.place(animal1));
        assertDoesNotThrow(() -> map.place(animal2));
        assertEquals(102, map.getElements().size());
    }
}