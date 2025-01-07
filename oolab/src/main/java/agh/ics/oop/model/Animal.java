package agh.ics.oop.model;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private Genes AnimalGenes;

//    public Animal() {
//        this.orientation = MapDirection.NORTH;
//        this.position = new Vector2d(2,2);
//        this.energy = 10;
//        // Zmienić w zależności od wyboru
//    }
//    public Animal(MapDirection orientation, Vector2d position){
//        this.orientation = orientation;
//        this.position = position;
//        this.energy = 10;
//    }

    public Animal(MapDirection orientation, Vector2d position, Genes AnimalGenes) {
        this.AnimalGenes = AnimalGenes;
        this.orientation = orientation;
        this.position = position;
        this.energy = 10;
    }

    public Animal(MapDirection orientation, Vector2d position) {
        this(orientation, position, new Genes(10));
    }


    public Animal() {
        this(MapDirection.NORTH, new Vector2d(2, 2), new Genes(10)); // Call the second constructor
    }



    @Override
    public String toString() {
        return String.valueOf(orientation);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }



    public void move(MoveDirection direction, MoveValidator map) {
        // Calculate the number of steps based on the MoveDirection
        int steps = direction.ordinal();

        // Update orientation based on how many steps we need to rotate
        for (int i = 0; i < steps; i++) {
            this.orientation = this.orientation.next();
        }

        // Move 1 step in the final direction after rotating the orientation
        Vector2d newPosition = this.position.add(this.orientation.toUnitVector());
        if (map.canMoveTo(newPosition)) {
            this.position = newPosition;
        }
    }
}
