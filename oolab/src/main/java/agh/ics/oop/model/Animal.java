package agh.ics.oop.model;

import agh.ics.oop.model.Enums.ElementType;
import agh.ics.oop.model.Enums.MapDirection;
import agh.ics.oop.model.Enums.MapType;
import agh.ics.oop.model.Enums.MovinType;

import java.util.ArrayList;
import java.util.Arrays;
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
    private int deathDate = -1;

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

    @Override
    public String toString() {
        return String.valueOf(orientation);
    }

    public List<Integer> getGenotype() {
        List<Integer> genotype = Arrays.stream(this.getGenome())
                .boxed()
                .toList();
        return genotype;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy(){
        return energy;
    }

    public void setEnergy(int var) {energy = var;}

    public ElementType getType(){
        return ElementType.ANIMAL;
    }

    public MovinType getMoveType() { return movinType; }
    public Integer getAge() { return age; }
    public Integer getBirthDate() { return birthdate; }
    public int[] getGenome() { return genome; }
    public Integer getGeneIndex() { return geneIndex; }
    public Integer getChildrenMade() { return childrenNumber; }
    public void increaseChildrenNumber() { childrenNumber++; }
    public Integer getPlantsEaten() { return grassEaten; }

    public void addChildToList(Animal child) {
        this.children.add(child);
    }

    public void eat(int grassEnergyLevel) { // jedzenie
        if(this.energy+grassEnergyLevel <= simulationProperties.getMaxEnergy())
        {
            this.energy += grassEnergyLevel;
            this.grassEaten+=1;
        }
        else if (this.energy!= simulationProperties.getMaxEnergy())
        {
            this.energy = simulationProperties.getMaxEnergy();
            this.grassEaten+=1;
        }
        else
        {
            this.energy = simulationProperties.getMaxEnergy();
        }

    }

    public void addAge(){
        this.age+=1;
    }

    public void setDeathDate(int deathDate){
        this.deathDate = deathDate;
    }

    public int getDeathDate()
    {
        return deathDate;
    }

    public void move(AbstractWorldMap map) {
        if(map.getMapType() == MapType.OWLBEAR)
        {
            if (movinType == MovinType.DEFAULT) {
                this.geneIndex = (this.geneIndex + 1) % this.genome.length;
            }

            MapDirection newOrientation = this.orientation.rotate(this.genome[this.geneIndex]);
            Vector2d newPosition = this.position.add(newOrientation.toUnitVector());
            this.orientation = newOrientation;
            if(newPosition.precedes(map.getCurrentBounds().upperRight()) && newPosition.follows(map.getCurrentBounds().lowerLeft()))
            {
                this.position = newPosition;
            }
        }
        else
        {
            if (movinType == MovinType.DEFAULT) {
                this.geneIndex = (this.geneIndex + 1) % this.genome.length;
            }

            MapDirection newOrientation = this.orientation.rotate(this.genome[this.geneIndex]);
            Vector2d newPosition = this.position.add(newOrientation.toUnitVector());
            this.orientation = newOrientation;
            Vector2d wrappedPosition = wrapPosition(newPosition, map.getWidth(), map.getHeight());
            this.position = wrappedPosition;
        }

    }

    private Vector2d wrapPosition(Vector2d position, int mapWidth, int mapHeight) {
        int wrappedX = (position.getX() + mapWidth+1) % (1+mapWidth);
        int wrappedY = (position.getY() + mapHeight+1) % (mapHeight+1);
        return new Vector2d(wrappedX, wrappedY);
    }

    @Override
    public String getImageResource() {
        return "animal.jpg";
    }

    public String getTrackedDownAnimalImageResource() {
        return "animal1.jpg";
    }

    public String getEnergyLevelResource() {
        int maxEnergy = simulationProperties.getMaxEnergy();
        int energyLevel = (int) (5.0 * this.energy / maxEnergy);
        return switch (energyLevel) {
            case 0 -> "energy/energy0.png";
            case 1 -> "energy/energy1.png";
            case 2 -> "energy/energy2.png";
            case 3 -> "energy/energy3.png";
            default -> "energy/energy4.png";
        };
    }

    public void removeEnergy(int energy) { this.energy -= energy; }

    // dodac funckje do liczenia dzieci/przodkow
}