package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.Statistics;
import agh.ics.oop.model.*;
import agh.ics.oop.model.Enums.MapType;
import agh.ics.oop.model.Enums.MovinType;
import agh.ics.oop.model.Enums.MutationType;
import agh.ics.oop.model.util.ConvertUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SimulationController implements MapChangeListener {
    private SimulationProperties simulationProperties;

    @FXML
    private GridPane mapGrid;
    @FXML
    private Label generalAllAnimalsLabel;
    @FXML
    private Label generalAllGrassesLabel;
    @FXML
    private Label generalFreeFieldsLabel;
    @FXML
    private Label generalPopularGenotypeLabel;
    @FXML
    private Label generalAvgEnergyLivingLabel;
    @FXML
    private Label generalAvgLifeSpanDeadLabel;
    @FXML
    private Label generalAvgChildrenLabel;
    @FXML
    private Label generalDaysPassed;
    @FXML
    private Label animalInfoLabel;
    @FXML
    private Button pauseButton;
    @FXML
    private Button continueButton;
    @FXML
    private VBox configBox3;

    Simulation simulation;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private int currentMapWidth;
    private int currentMapHeight;
    private WorldMap worldMap;
    private Animal lastClickedAnimal = null;
    private WorldElementBox lastElementBox = null;

    private final int width = 25;
    private final int height = 25;

    private static final int MAPLIMITHIGHT = 100;
    private static final int MAPLIMITWIDTH = 100;

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }
    public void setSimulationProperties(SimulationProperties properties) {
        this.simulationProperties = properties;
    }
    @FXML
    private LineChart<Number, Number> animalChart;
    @FXML
    private LineChart<Number, Number> grassChart;

    private void drawMap() {
        updateBounds();
        xyLabel();
        columnsFunction();
        rowsFunction();
        addElements();
        mapGrid.setGridLinesVisible(true);
    }
    @FXML
    public void onSimulationStartClicked(){
        configBox3.setVisible(false);
        configBox3.setManaged(false);
        continueButton.setDisable(true);
        AbstractWorldMap map1;
        configBox3.setVisible(false);

        int mapWidth = simulationProperties.getMapWidth();
        int mapHeight = simulationProperties.getMapHeight();
        int equatorHeight = simulationProperties.getEquatorHeight();
        int animalNumber = simulationProperties.getStartAnimalNumber();
        int grassNumber = simulationProperties.getGrassNumber();
        int grassEnergy = simulationProperties.getGrassEnergy();
        int dailySpawningGrass = simulationProperties.getDailySpawningGrass();
        int startEnergy = simulationProperties.getStartEnergy();
        int maxEnergy = simulationProperties.getMaxEnergy();
        MovinType movingType = MovinType.DEFAULT;
        MutationType mutationType = simulationProperties.getMutationType();
        MapType mapType = simulationProperties.getMapType();
        int genesCount = simulationProperties.getGenesCount();
        int energyLevelNeededToReproduce = simulationProperties.getEnergyLevelNeededToReproduce();
        int energyLevelToPassToChild = simulationProperties.getEnergyLevelToPassToChild();
        int moveEnergy = simulationProperties.getMoveEnergy();
        int minMutation = simulationProperties.getMinMutation();
        int maxMutation = simulationProperties.getMaxMutation();
        try{
            if (mapType == MapType.GLOBE) {
                map1 = new GrassField(simulationProperties);
                map1.addObserver((MapChangeListener) this);
                Simulation simulation1 = new Simulation(map1, simulationProperties);
                this.simulation = simulation1;
                SimulationEngine engine = new SimulationEngine(List.of(simulation1));
                //engine.runAsync();
                engine.runAsync();
            }
            else{map1 = new OwlBearMap(simulationProperties);
                map1.addObserver((MapChangeListener) this);
                Simulation simulation1 = new Simulation(map1, simulationProperties);
                this.simulation = simulation1;
                SimulationEngine engine = new SimulationEngine(List.of(simulation1));
                //engine.runAsync();
                engine.runAsync();
            }} catch (Exception e) {
            throw new RuntimeException(e);
        }
        showGeneralStatistics();

    }
    private void showGeneralStatistics()
    {
        continueButton.setVisible(true);
        continueButton.setManaged(true);
        pauseButton.setVisible(true);
        pauseButton.setManaged(true);
    }


    public void xyLabel(){
        mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
        mapGrid.getRowConstraints().add(new RowConstraints(height));
        Label label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }
    public void updateBounds(){
        xMin = worldMap.getCurrentBounds().lowerLeft().getX();
        yMin = worldMap.getCurrentBounds().lowerLeft().getY();
        xMax = worldMap.getCurrentBounds().upperRight().getX();
        yMax = worldMap.getCurrentBounds().upperRight().getY();
        currentMapWidth = xMax - xMin + 1;
        currentMapHeight = yMax - yMin + 1;
    }
    public void columnsFunction(){
        for(int i = 0; i< currentMapWidth; i++){
            Label label = new Label(Integer.toString(i+xMin));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
            mapGrid.add(label, i+1, 0);
        }
    }
    public void rowsFunction(){
        for(int i=0; i<currentMapHeight; i++){
            Label label = new Label(Integer.toString(yMax-i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(height));
            mapGrid.add(label, 0, i+1);
        }
    }

    public void addElements() {
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                Optional<WorldElement> optionalElement = worldMap.objectAt(new Vector2d(i, j));
                if (optionalElement.isPresent()) {
                    WorldElement worldElement = optionalElement.get();
                    WorldElementBox elementBox;
                    if(lastClickedAnimal!=null && lastClickedAnimal.getPosition().equals(worldElement.getPosition()))
                    {
                        if(lastClickedAnimal.getDeathDate() == -1)
                        {
                            elementBox = new WorldElementBox(lastClickedAnimal);
                            elementBox.updateImageTrackedDown(lastClickedAnimal);
                            lastElementBox = elementBox;
                        }
                        else
                        {
                            elementBox = new WorldElementBox(worldElement);
                            lastClickedAnimal = null;
                        }
                    }
                    else
                    {
                        elementBox = new WorldElementBox(worldElement);
                    }

                    elementBox.setOnMouseClicked(event -> {
                        if (worldElement instanceof Animal) {
                            Animal clickedAnimal = (Animal) worldElement;

                            if (lastClickedAnimal == clickedAnimal) {
                                clearAnimalInfo();
                                elementBox.updateImage(clickedAnimal);
                                lastClickedAnimal = null;
                            }
                            else if(lastClickedAnimal != null) {
                                lastElementBox.updateImage(lastClickedAnimal);
                                lastElementBox = elementBox;
                                lastClickedAnimal = clickedAnimal;
                                elementBox.updateImageTrackedDown(clickedAnimal);
                                showAnimalInfo(clickedAnimal);
                            }
                            else {

                                lastElementBox = elementBox;
                                lastClickedAnimal = clickedAnimal;
                                elementBox.updateImageTrackedDown(clickedAnimal);
                                showAnimalInfo(clickedAnimal);
                            }
                        }

                    });


                    mapGrid.add(elementBox, i - xMin + 1, yMax - j + 1);
                } else {
                    mapGrid.add(new Label(" "), i - xMin + 1, yMax - j + 1);
                }
            }
        }
    }
    private void showAnimalInfo(WorldElement worldElement) {
        if (worldElement instanceof Animal) {
            Animal animal = (Animal) worldElement;
            Platform.runLater(() -> {
                animalInfoLabel.setText("Animal Info:\n" +
                        "Genome: " +  Arrays.toString(animal.getGenome()) + "\n" +
                        "Genome Index: " + animal.getGeneIndex() + "\n" +
                        "Energy: " + animal.getEnergy() + "\n" +
                        "Grasses eaten: " + animal.getPlantsEaten() + "\n" +
                        "Children amount: " + animal.getChildrenMade() + "\n" +
                        "Position: " + animal.getPosition() + "\n" +
                        "Age: " + animal.getAge() + "\n" +
                        "Death date: " + animal.getDeathDate() + "\n");
            });
        }
    }
    private void clearAnimalInfo() {
        Platform.runLater(() -> {
            animalInfoLabel.setText("");  // Clear the information label
        });
    }
    @Override
    public void mapChanged(WorldMap worldMap, String message, Statistics statistics) {
        setWorldMap(worldMap);
        this.displayGeneralStatistics(statistics);

        if (lastClickedAnimal != null) {
            showAnimalInfo(lastClickedAnimal);
        }

        Platform.runLater(() -> {
            clearGrid();
            drawMap();
            //updateCharts(statistics);
        });
    }

    private void updateCharts(Statistics statistics) {
        // Pobieramy aktualny dzień i wartości z `Statistics`
        int currentDay = statistics.getDaysPassed();
        int animalCount = statistics.getAnimalAmount();
        int grassCount = statistics.getGrassesAmount();

        // Aktualizujemy dane na wykresie dla zwierząt
        animalChart.getData().get(0).getData().add(new XYChart.Data<>(currentDay, animalCount));

        // Aktualizujemy dane na wykresie dla traw
        grassChart.getData().get(0).getData().add(new XYChart.Data<>(currentDay, grassCount));
    }

    public void displayGeneralStatistics(Statistics statistics) {
        String animalCount = ConvertUtils.numberToString(statistics.getAnimalAmount());
        String grassesCount = ConvertUtils.numberToString(statistics.getGrassesAmount());
        String FreeFields = ConvertUtils.numberToString(statistics.getFreeFieldsAmount());
        String theMostPopularGenotype = ConvertUtils.convertGenotypeToString(statistics.getTheMostPopularGenotype());
        String averageAliveAnimalsEnergy = ConvertUtils.numberToString(statistics.getAverageAnimalsEnergy());
        String averageAnimalLifeSpan = ConvertUtils.numberToString(statistics.getAverageLifespan());
        String averageChildAmount = ConvertUtils.numberToString(statistics.getAverageChildAmount());
        String days = ConvertUtils.numberToString(statistics.getDaysPassed());

        Platform.runLater(() -> {
            generalAllAnimalsLabel.setText(animalCount);
            generalAllGrassesLabel.setText(grassesCount);
            generalDaysPassed.setText(days);
            generalFreeFieldsLabel.setText(FreeFields);
            generalPopularGenotypeLabel.setText(theMostPopularGenotype);
            generalAvgLifeSpanDeadLabel.setText(averageAnimalLifeSpan);
            generalAvgChildrenLabel.setText(averageChildAmount);
            generalAvgEnergyLivingLabel.setText(averageAliveAnimalsEnergy);
        });
    }


    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }


    @FXML
    public void onPauseClicked(ActionEvent actionEvent) {
        if (simulation != null) {
            simulation.pause();
            Platform.runLater(() -> pauseButton.setDisable(true));
            Platform.runLater(() -> continueButton.setDisable(false));
        }
    }

    @FXML
    public void onContinueClicked(ActionEvent actionEvent) {
        if (simulation != null) {
            simulation.start();
            Platform.runLater(() -> pauseButton.setDisable(false));
            Platform.runLater(() -> continueButton.setDisable(true));
        }
    }
}
