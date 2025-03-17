package agh.ics.oop.model.mapElements;

import agh.ics.oop.simulation.SimulationProperties;
import agh.ics.oop.model.util.Vector2d;
import agh.ics.oop.model.enums.ElementType;
import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.enums.MovinType;
import agh.ics.oop.model.maps.AbstractWorldMap;

import java.util.*;

import static agh.ics.oop.model.util.Genes.getStartingGenes;

public class Animal implements WorldElement {
    private UUID id = UUID.randomUUID();
    // glowne atrybuty animala
    private MapDirection orientation;
    private Vector2d position;
    private MovinType movinType;

    // dodawane przez symulacje
    private int energy;
    private final int[] genome;
    private int geneIndex;
    private final int birthdate;
    private int grassEaten;
    private int childrenNumber;
    private final SimulationProperties simulationProperties;
    private int age;
    private int deathDate = -1;
    private final int ORIENTATIONNUMBER = 8;

    private final List<Animal> children = new ArrayList<Animal>();
    private final Set<UUID> descendants = new HashSet<>();
    private static final Random RANDOM = new Random();

    /**
     * Single constructor for Animal class
     * @param position The initial position
     * @param simulationProperties The simulation properties
     * @param customGenome Optional custom genome, null for randomly generated genome
     * @param isChild Whether this animal is a child (affects initial energy)
     */
    public Animal(Vector2d position, SimulationProperties simulationProperties, int[] customGenome, boolean isChild) {
        this.geneIndex = 0;
        this.genome = customGenome != null ? customGenome : getStartingGenes(simulationProperties.getGenesCount());
        this.orientation = MapDirection.values()[RANDOM.nextInt(ORIENTATIONNUMBER)]; // losowa orientaja na poczatku
        this.position = position;
        this.movinType = simulationProperties.getMovingType();
        this.age = 0;
        this.grassEaten = 0;
        this.simulationProperties = simulationProperties;
        this.childrenNumber = 0;
        this.birthdate = simulationProperties.getDaysElapsed();

        // Set energy based on whether this is a child or not
        if (isChild) {
            this.energy = 2 * simulationProperties.getEnergyLevelToPassToChild();
        } else {
            this.energy = simulationProperties.getStartEnergy();
        }
    }

    // Factory methods to replace the old constructors

    /**
     * Creates a new parent animal
     */
    public static Animal createParent(Vector2d position, SimulationProperties simulationProperties) {
        return new Animal(position, simulationProperties, null, false);
    }

    /**
     * Creates a new child animal
     */
    public static Animal createChild(Vector2d position, SimulationProperties simulationProperties, int[] genome) {
        return new Animal(position, simulationProperties, genome, true);
    }

    public List<Animal> getChildren() {
        return children;
    }

    public UUID getId() {
        return id;
    }

    public int getDescendantsCount() {
        Set<UUID> desc = getDescendants(this);
        return desc.size();
    }

    public Set<UUID> getDescendants(Animal animal) {
        Set<UUID> desc = new HashSet<>();
        if (animal.getChildren().isEmpty()) {
            return desc;
        }
        for (Animal child : animal.getChildren()) {
            desc.add(child.getId());
            if(child.getChildrenMade()>0) {
                desc.addAll(getDescendants(child));
            }
        }
        return desc;
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
        //return Arrays.asList(getGenome()); nie mozna uzyc z powodu ze geter zwraca tablice intow
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
        // Update gene index based on movement type
        if (movinType == MovinType.DEFAULT) {
            this.geneIndex = (this.geneIndex + 1) % this.genome.length;
        }

        // Update orientation based on genes
        this.orientation = this.orientation.rotate(this.genome[this.geneIndex]);

        // Get the movement strategy from the map and use it to calculate new position
        MovementStrategy movementStrategy = map.getMovementStrategy();
        this.position = movementStrategy.getNewPosition(
                this.position,
                this.orientation,
                map.getWidth(),
                map.getHeight()
        );
    }

    @Override
    public String getImageResource() {
        return switch (orientation) {
            case NORTH -> "N.png";
            case NORTHEAST -> "NE.png";
            case EAST -> "E.png";
            case SOUTHEAST -> "SE.png";
            case SOUTH -> "S.png";
            case SOUTHWEST -> "SW.png";
            case WEST -> "W.png";
            case NORTHWEST -> "NW.png";
        };
    }

    public String getTrackedDownAnimalImageResource() {
        return "animal1.png";
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
}