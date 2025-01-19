package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.Statistics;
import agh.ics.oop.model.Enums.MapType;
import agh.ics.oop.model.util.ConvertUtils;

import java.util.*;


public class SimulationManager {
    private final AbstractWorldMap map;
    private final SimulationProperties simulationProperties;
    private final Simulation simulation;
    private final Statistics statistics = new Statistics();
    private Set<Vector2d> equatorField = new HashSet<>();

    private static final double PREFERRED_POSITION_PROBABILITY = 0.9; // Pareto rule
    private static final Set<Vector2d> preferredPositions = new HashSet<>();
    private static final Set<Vector2d> lessPreferredPositions = new HashSet<>();
    private static int DAILY_GRASS_NUMBER = 0;

    protected static final Random random = new Random();

    public SimulationManager (AbstractWorldMap map_, SimulationProperties simulationProperties_, Simulation simulation_) {
        map = map_;
        simulationProperties= simulationProperties_;
        simulation = simulation_;
        DAILY_GRASS_NUMBER = simulationProperties.getDailySpawningGrass();
        initializePositions(map);
    }

    public void Init() {
        map.setStatistics(statistics, simulation.getDays());
        map.mapChanged(statistics, "Dzien sie zakonczyl");
    }

    public Set<Vector2d> getPreferredPositions() {
        int equatorHeight = simulationProperties.getEquatorHeight();
        int width = map.getWidth();
        int height = map.getHeight();
        Set<Vector2d> preferred = new HashSet<>();
        int centerRow = width / 2;
        int startEquatorRow = centerRow - ((equatorHeight - 1) / 2);
        int endEquatorRow = startEquatorRow + equatorHeight - 1;
        startEquatorRow = Math.max(startEquatorRow, 0);
        endEquatorRow = Math.min(endEquatorRow, height - 1);
        for (int x = 0; x <= height; x++) {
            for (int y = 0; y <= width; y++) {
                Vector2d position = new Vector2d(x, y);
                if (y >= startEquatorRow && y <= endEquatorRow) {
                    preferred.add(position);
                }
            }
        }
        return preferred;
    }

    // operacja podczas nowego dnia
    public void Update() {
        deleteDeadAnimals();
        moveALlAnimals();
        if(map.getMapType()== MapType.OWLBEAR)
        {
            owlBearEat();
        }
        eat();
        if(map.getMapType()== MapType.OWLBEAR)
        {
            moveOwlBear();
            owlBearEat();
        }
        reproduceAnimals();
        growGrass();
        addAge();

        map.setStatistics(statistics, simulation.getDays());
        map.mapChanged(statistics, "Dzien sie zakonczyl");
    }

    protected void restoreEatenPlantPosition(Grass eatenGrass) {
        int equatorHeight = simulationProperties.getEquatorHeight();
        int width = map.getWidth();
        int height = map.getHeight();
        int centerRow = width / 2;
        int startEquatorRow = centerRow - ((equatorHeight - 1) / 2);
        int endEquatorRow = startEquatorRow + equatorHeight - 1;
        startEquatorRow = Math.max(startEquatorRow, 0);
        endEquatorRow = Math.min(endEquatorRow, height - 1);
        Vector2d availablePosition = eatenGrass.getPosition();
        if (availablePosition.getY() >= startEquatorRow && availablePosition.getY() <= endEquatorRow) {
            preferredPositions.add(availablePosition);
        } else {
            lessPreferredPositions.add(availablePosition);
        }
    }

    private void deleteDeadAnimals() {
        Set<Animal> animalsToRemove = new HashSet<>(simulation.getAnimals());
       // System.out.println("Checking " + animalsToRemove.size() + " animals for death");

        for(Animal animal : animalsToRemove) {
            if (animal.getEnergy() <= 0) {
              //  System.out.println("Animal at " + animal.getPosition() + " died with energy: " + animal.getEnergy());
                animal.setDeathDate(simulationProperties.getDaysElapsed());
                map.removeAnimal(animal);
                simulation.getAnimals().remove(animal);
               // System.out.println("Remaining animals: " + simulation.getAnimals().size());
            }
        }
    }

    private void addAge() {
        simulationProperties.incrementDaysElapsed();
        for(Animal animal : simulation.getAnimals()){
            animal.addAge();
        }
    }

    private void moveALlAnimals() {
        for(Animal animal : simulation.getAnimals()) {
            map.move(animal);
            animal.removeEnergy(simulationProperties.getMoveEnergy());
        }
    }

    private void moveOwlBear()
    {
        ((OwlBearMap)map).moveOwlBear();
    }

    private void owlBearEat()
    {
//        Set<Animal> animalsToRemove = new HashSet<>(((OwlBearMap)map).eatAnimals());
//        System.out.println("Checking " + animalsToRemove.size() + " animals for death");
//
//        for(Animal animal : animalsToRemove) {
//            System.out.println("Animal at " + animal.getPosition() + " eaten by OwlBear with energy: " + animal.getEnergy());
//            animal.setDeathDate(simulationProperties.getDaysElapsed());
//            simulation.getAnimals().remove(animal);
//            System.out.println("Remaining animals: " + simulation.getAnimals().size());
//        }
        Set<Animal> animalsToRemove = new HashSet<>(simulation.getAnimals());
       // System.out.println("Checking " + animalsToRemove.size() + " animals for being eaten");

        for(Animal animal : animalsToRemove) {
            if (animal.getPosition().equals(((OwlBearMap)map).getOwlBearPosition()))
            {
              //  System.out.println("Animal at " + animal.getPosition() + " eaten by OwlBear with energy: " + animal.getEnergy());
                animal.setDeathDate(simulationProperties.getDaysElapsed());
                simulation.getAnimals().remove(animal);
                map.removeAnimal(animal);
              //  System.out.println("Remaining animals: " + simulation.getAnimals().size());
            }
        }
    }


    public void reproduceAnimals() {
       // System.out.println("\n=== Starting reproduction phase ===");
       // System.out.println("Total positions with animals: " + map.getAnimals().keySet().size());
      //  System.out.println(map.getAnimals().keySet());

        for (Vector2d position : map.getAnimals().keySet()) {
            List<Animal> animalList = map.getAnimals().get(position);
            //System.out.println("\nChecking position " + position + " with " + animalList.size() + " animals");
            if (animalList.size() > 1) {
                Animal a1 = animalList.get(0);
                Animal a2 = animalList.get(1);
               // System.out.println("Potential parents found:");
                //System.out.println("Parent 1 energy: " + a1.getEnergy());
               // System.out.println("Parent 2 energy: " + a2.getEnergy());
               // System.out.println("Energy needed to reproduce: " + simulationProperties.getEnergyLevelNeededToReproduce());
               // System.out.println(position);
                if (a1.getEnergy() > simulationProperties.getEnergyLevelNeededToReproduce() &&
                        a2.getEnergy() > simulationProperties.getEnergyLevelNeededToReproduce()) {

                    //System.out.println("Both parents have enough energy - creating child");
                    int[] getGenome = Genes.mixGenesFromParents(a1,a2,simulationProperties);
                    Animal child = new Animal(position, simulationProperties,getGenome);
                   // System.out.println("First partent genome");
                    //System.out.println(Arrays.toString(a1.getGenome()));
                   // System.out.println("Second parent genowe");
                    //System.out.println(Arrays.toString(a2.getGenome()));
                   // System.out.println("child genome");
                   // System.out.print(Arrays.toString(child.getGenome()));
                    synchronized (this) {
                       // System.out.println("Before reproduction:");
                        //System.out.println("Total animals at position before birth: " +
                              //  map.getAnimals().get(position).size());
                       // System.out.println("Parent 1 energy: " + a1.getEnergy());
                      //  System.out.println("Parent 2 energy: " + a2.getEnergy());

                        map.getAnimals().get(position).add(child);
                        simulation.addAnimal(child);

                        a1.removeEnergy(simulationProperties.getEnergyLevelToPassToChild());
                        a1.addChildToList(simulation.getAnimals().get(simulation.getAnimals().indexOf(child)));
                        a1.increaseChildrenNumber();
                        a2.removeEnergy(simulationProperties.getEnergyLevelToPassToChild());
                        a2.addChildToList(simulation.getAnimals().get(simulation.getAnimals().indexOf(child)));
                        a2.increaseChildrenNumber();

                       // System.out.println("After reproduction:");
                      //  System.out.println("Parent 1 energy: " + a1.getEnergy());
                       // System.out.println("Parent 2 energy: " + a2.getEnergy());
                       // System.out.println("Child energy: " + child.getEnergy());
                      //  System.out.println("Total animals at position after birth: " +
                           //     map.getAnimals().get(position).size());
                       // System.out.println("\n");
                    }
                } else {
                   // System.out.println("Not enough energy for reproduction");
                }
            } else {
               // System.out.println("Not enough animals for reproduction at this position");
            }
        }
        //System.out.println("\n=== Reproduction phase completed ===");
        //System.out.println("Total animals after reproduction: " + simulation.getAnimals().size());
    }

    public void eat() {
        Set<Vector2d> keys = new HashSet<>(map.getPlants().keySet());
        for (Vector2d position : keys) {
            if (map.getAnimals().containsKey(position)) {
                List<Animal> animalList = map.getAnimals().get(position);
                if (!animalList.isEmpty()) {
                    Animal animal = animalList.get(0);
                    //System.out.println("Animal at " + position + " eating grass. Current energy: " + animal.getEnergy());
                    synchronized (this) {
                        animal.eat(simulationProperties.getGrassEnergy());
                        Grass eatenGrass = map.getPlants().get(position);
                        map.getPlants().remove(position);
                        map.getFreePositionsForPlants().add(position);
                        restoreEatenPlantPosition(eatenGrass);
                    }
                    //System.out.println("After eating energy: " + animal.getEnergy());
                }
            }
        }
    }

//    public void initializePositions(AbstractWorldMap map) {
//        int equatorHeight = simulationProperties.getEquatorHeight();
//        int width = map.getWidth();
//        int height = map.getHeight();
//        Set<Vector2d> preferred = new HashSet<>();
//        Set<Vector2d> lessPreferred = new HashSet<>();
//        int startEquatorRow = (height - 1) / 2;
//        int endEquatorRow = height / 2;
//        for (int x = 0; x <= height; x++) {
//            for (int y = 0; y <= width; y++) {
//                Vector2d position = new Vector2d(x, y);
//                if (y >= startEquatorRow + 1 && y <= endEquatorRow + 1) {
//                    preferred.add(position);
//                } else {
//                    lessPreferred.add(position);
//                }
//            }
//        }
//        preferredPositions.clear();
//        preferredPositions.addAll(preferred);
//        lessPreferredPositions.clear();
//        lessPreferredPositions.addAll(lessPreferred);
//    }
public void initializePositions(AbstractWorldMap map) {
    int equatorHeight = simulationProperties.getEquatorHeight(); // The height of the equator
    int width = map.getWidth();
    int height = map.getHeight();

    Set<Vector2d> preferred = new HashSet<>();
    Set<Vector2d> lessPreferred = new HashSet<>();

    // Calculate the start and end rows for the equator based on equatorHeight
    int centerRow = width / 2; // The central row of the map
    int startEquatorRow = centerRow - ((equatorHeight - 1) / 2);
    int endEquatorRow = startEquatorRow + equatorHeight - 1;

    startEquatorRow = Math.max(startEquatorRow, 0);
    endEquatorRow = Math.min(endEquatorRow, height - 1);

    // Loop through all positions on the map
    for (int x = 0; x <= height; x++) {
        for (int y = 0; y <= width; y++) {
            Vector2d position = new Vector2d(x, y);
            if (y >= startEquatorRow && y <= endEquatorRow) {
                preferred.add(position); // Positions within the equator
            } else {
                lessPreferred.add(position); // Positions outside the equator
            }
        }
    }

    // Update the position sets
    preferredPositions.clear();
    preferredPositions.addAll(preferred);
    lessPreferredPositions.clear();
    lessPreferredPositions.addAll(lessPreferred);
}


    public void generateGrass(int numberOfPlants) {
        for (int i = 0; i < numberOfPlants; i++) {
            double probability = random.nextDouble();
            Vector2d plantPosition;
            if ((probability < PREFERRED_POSITION_PROBABILITY && !preferredPositions.isEmpty()) || (lessPreferredPositions.isEmpty()&& !preferredPositions.isEmpty())) {
                List<Vector2d> preferredList = new ArrayList<>(preferredPositions);
                plantPosition = preferredList.get(random.nextInt(preferredList.size()));
                preferredPositions.remove(plantPosition);
                map.placeGrass(plantPosition, new Grass(plantPosition));
            } else if (!lessPreferredPositions.isEmpty()) {
                List<Vector2d> lessPreferredList = new ArrayList<>(lessPreferredPositions);
                plantPosition = lessPreferredList.get(random.nextInt(lessPreferredList.size()));
                lessPreferredPositions.remove(plantPosition);
                map.placeGrass(plantPosition, new Grass(plantPosition));
            }
        }
    }
    public void growGrass() {
        generateGrass(DAILY_GRASS_NUMBER);
    }

}
