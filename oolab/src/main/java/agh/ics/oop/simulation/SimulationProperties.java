package agh.ics.oop.simulation;

import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.enums.MovinType;
import agh.ics.oop.model.enums.MutationType;

public class SimulationProperties {
    private int width;
    private int height;
    private int animalNumber;
    private int grassNumber;
    private int dailySpawningGrass;
    private int startEnergy;
    private int maxEnergy;
    private int grassEnergy;
    private MapType mapType;
    // zamiast equatora stale = 20%, w naszej impelementacji mozna go ustawiac
    private int equatorHeight;
    private MutationType mutationType;
    private int minMutation;
    private int maxMutation;
    private int genesCount;
    private int energyLevelNeededToReproduce;
    private int energyLevelToPassToChild;
    private int moveEnergy;
    private int daysElapsed;
    private MovinType movingType;
    private String CSV;

    public  SimulationProperties(int width_, int height_, int equatorHeight_, int animalNumber_, int grassNumber_,
                                 int dailySpawningGrass_, int startEnergy_, int grassEnergy_, int maxEnergy_,
                                 MovinType movingType_, MutationType mutationType_, MapType mapType_, int genesCount_,
                                 int energyLevelNeededToReproduce_, int energyLevelToPassToChild_, int moveEnergy_,
                                 int minMutation_, int maxMutation_, String CSV_) {
        width = width_;
        height = height_;
        equatorHeight = equatorHeight_;
        animalNumber = animalNumber_;
        grassNumber = grassNumber_;
        dailySpawningGrass = dailySpawningGrass_;
        startEnergy = startEnergy_;
        grassEnergy = grassEnergy_;
        movingType = movingType_;
        mutationType = mutationType_;
        mapType = mapType_;
        maxEnergy = maxEnergy_;
        genesCount = genesCount_;
        energyLevelNeededToReproduce = energyLevelNeededToReproduce_;
        energyLevelToPassToChild = energyLevelToPassToChild_;
        moveEnergy = moveEnergy_;
        daysElapsed = 0;
        minMutation = minMutation_;
        maxMutation = maxMutation_;
        CSV = CSV_;
    }

    public synchronized void incrementDaysElapsed() {
        daysElapsed++;
    }

    public Integer getMapWidth(){
        return width;
    }

    public Integer getMapHeight(){
        return height;
    }

    public int getMaxEnergy(){
        return maxEnergy;
    }
    public int getDailySpawningGrass(){
        return dailySpawningGrass;
    }
    public int getEquatorHeight(){
        return equatorHeight;
    }

    public int getStartAnimalNumber(){
        return animalNumber;
    }

    public int getGrassNumber(){
        return grassNumber;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public MovinType getMovingType() {
        return movingType;
    }

    public MutationType getMutationType() {
        return mutationType;
    }

    public MapType getMapType() {
        return mapType;
    }

    public int getGrassEnergy(){
        return grassEnergy;
    }

    public int getGenesCount() {
        return genesCount;
    }

    public int getEnergyLevelNeededToReproduce() {
        return energyLevelNeededToReproduce;
    }

    public int getEnergyLevelToPassToChild() {
        return energyLevelToPassToChild;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public Integer getDaysElapsed() {
        return daysElapsed;
    }

    public int getMinMutation() {
        return minMutation;
    }

    public int getMaxMutation() {
        return maxMutation;
    }

    public String getCSV() {return CSV;}

    public void setMapWidth(int width){
        this.width = width;
    }

    public void setMapHeight(int height){
        this.height = height;
    }

    public void setGrassNumber(int grassNumber){
        this.grassNumber = grassNumber;
    }


}