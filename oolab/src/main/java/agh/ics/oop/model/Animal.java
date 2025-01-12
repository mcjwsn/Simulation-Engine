package agh.ics.oop.model;

import agh.ics.oop.model.modes.ElementType;
import agh.ics.oop.model.modes.MovinType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static agh.ics.oop.model.Genes.getStartingGenes;

public class Animal implements WorldElement {
    // glowne atrybuty animala
    private MapDirection orientation;
    private Vector2d position;
    private MovinType movinType;

    // dodawane przez symulacje
    private int energy;
    private int[] genome;
    private int geneIndex;
    private int birthdate; // zmienic na final po poprawce
    private int grassEaten;
    private int childrenNumber;
    private SimulationProperties simulationProperties;
    int age;
    private int deathDate;

    private final List<Animal> children = new ArrayList<Animal>();
    private static final Random random = new Random();

    public Animal(Vector2d position, SimulationProperties simulationProperties) {
        this.geneIndex = 0;
        this.genome = getStartingGenes(simulationProperties.getGenesCount());
        this.orientation = MapDirection.values()[random.nextInt(8)]; // losowa orientaja na poczatku
        this.position = position;
        this.energy = simulationProperties.getStartEnergy();
        this.movinType = simulationProperties.getMovingType();
        this.age = 0;
        this.grassEaten = 0;
        this.simulationProperties = simulationProperties;
        this.childrenNumber = 0;
        this.birthdate = simulationProperties.getDaysElapsed();
    }

    public Animal(Vector2d position, SimulationProperties simulationProperties, int[] gotGenome) {
        // konstruktor dla dzieci
        this.geneIndex = 0;
        this.genome = gotGenome;
        this.orientation = MapDirection.values()[random.nextInt(8)]; // losowa orientaja na poczatku
        this.position = position;
        this.energy = 2*simulationProperties.getEnergyLevelToPassToChild();;
        this.movinType = simulationProperties.getMovingType();
        this.age = 0;
        this.grassEaten = 0;
        this.simulationProperties = simulationProperties;
        this.childrenNumber = 0;
        this.birthdate = simulationProperties.getDaysElapsed();
    }

    public List<Animal> getChildren() {
        return children;
    }

    public Animal(MapDirection orientation, Vector2d position) { // do pozniejszej poprawki
        //this(orientation, position, new Genes(10), 100);
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

    public int getEnergy(){
        return energy;
    }


    public ElementType getType(){
        return ElementType.ANIMAL;
    }

    public MovinType getMoveType() { return movinType; }
    public Integer getAge() { return age; }
    public Integer getBirthDate() { return birthdate; }
    public int[] getGenome() { return genome; }
    public Integer getGeneIndex() { return geneIndex; }
    public Integer getChildrenMade() { return childrenNumber; }
    public Integer getPlantsEaten() { return grassEaten; }

    public void addChildToList(Animal child) {
        this.children.add(child);
    }

    public void eat(int grassEnergyLevel) { // jedzenie
        this.energy += grassEnergyLevel;
        this.grassEaten+=1;
    }

    public void addAge(){
        this.age+=1;
    }

    public void setDeathDate(int deathDate){
        this.deathDate = deathDate;
    }

//    public void move(MoveValidator map) {
//        if(movinType == MovinType.DEFAULT) {
//            this.geneIndex = (this.geneIndex + 1) % this.genome.length;
//        }
//        MapDirection newOrientation = this.orientation.rotate(this.genome[this.geneIndex]);
//        Vector2d newPosition = this.position.add(newOrientation.toUnitVector());
//        this.orientation = newOrientation;
//        this.position = newPosition;
//    }
    public void move(AbstractWorldMap map) {
        if (movinType == MovinType.DEFAULT) {
            this.geneIndex = (this.geneIndex + 1) % this.genome.length;
        }

        MapDirection newOrientation = this.orientation.rotate(this.genome[this.geneIndex]);
        Vector2d newPosition = this.position.add(newOrientation.toUnitVector());
        this.orientation = newOrientation;
        Vector2d wrappedPosition = wrapPosition(newPosition, map.getWidth(), map.getHeight());
        this.position = wrappedPosition;
    }

    private Vector2d wrapPosition(Vector2d position, int mapWidth, int mapHeight) {
        int wrappedX = (position.getX() + mapWidth+1) % (1+mapWidth);
        int wrappedY = (position.getY() + mapHeight+1) % (mapHeight+1);
        return new Vector2d(wrappedX, wrappedY);
    }





    public void removeEnergy(int energy) { this.energy -= energy; }

    // dodac funckje do liczenia dzieci/przodkow
}