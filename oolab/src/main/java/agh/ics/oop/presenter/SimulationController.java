package agh.ics.oop.presenter;

import agh.ics.oop.model.mapElements.*;
import agh.ics.oop.model.util.*;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.maps.GrassField;
import agh.ics.oop.model.maps.OwlBearMap;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.simulation.SimulationEngine;
import agh.ics.oop.simulation.SimulationProperties;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

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
    private boolean showFieldsBool = false;
    private Set<Vector2d> prefPos;
    private boolean showGenotypeBool = false;
    List<Integer> popularGenotype;
    private boolean exportDailyStatisticsBool = false;

    private final int width = 25;
    private final int height = 25;

    private static final int MAPLIMITHIGHT = 100;
    private static final int MAPLIMITWIDTH = 100;

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @FXML
    private LineChart<Number, Number> animalChart;
    @FXML
    private LineChart<Number, Number> grassChart;
    @FXML
    private LineChart<Number,Number> energyChart;
    @FXML
    private VBox Charts;
    @FXML
    private Button showGenotype;
    @FXML
    private Button showFields;
    @FXML
    private VBox statsBox;
    @FXML
    private Label animalInfoLabelGenome;
    @FXML
    private Label animalInfoLabelGenomeIndex;
    @FXML
    private Label animalInfoLabelEnergy;
    @FXML
    private Label animalInfoLabelGrassEaten;
    @FXML
    private Label animalInfoLabelChildrenAmount;
    @FXML
    private Label animalInfoLabelPosition;
    @FXML
    private Label animalInfoLabelAge;
    @FXML
    private Label animalInfoLabelDeathDate;
    @FXML
    private VBox selectedAnimalStats;
    @FXML
    private Label animalAncestors;
    private final Object simulationLock = new Object();
    private volatile boolean uiUpdated = false;


    private void drawMap() {
        updateBounds();
        addElements();
    }
    private void showGeneralStatistics()
    {
        continueButton.setVisible(true);
        continueButton.setManaged(true);
        pauseButton.setVisible(true);
        pauseButton.setManaged(true);
        showFields.setVisible(true);
        showFields.setManaged(true);
        showGenotype.setVisible(true);
        showGenotype.setManaged(true);
    }

    public void updateBounds(){
        xMin = worldMap.getCurrentBounds().lowerLeft().getX();
        yMin = worldMap.getCurrentBounds().lowerLeft().getY();
        xMax = worldMap.getCurrentBounds().upperRight().getX();
        yMax = worldMap.getCurrentBounds().upperRight().getY();
        currentMapWidth = xMax - xMin + 1;
        currentMapHeight = yMax - yMin + 1;
    }

    public void addElements() {
        Integer size = (int) (500/Math.max(simulationProperties.getMapHeight(),simulationProperties.getMapWidth()))-2;
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                Vector2d position = new Vector2d(i, j);
                Optional<WorldElement> optionalElement = worldMap.objectAt(new Vector2d(i, j));
                if (prefPos.contains(new Vector2d(i, j)) && showFieldsBool)
                {
                    PrefferdCell prefCell = new PrefferdCell(new Vector2d(i, j));
                    WorldElementBox elementBoxPreferredField = new WorldElementBox(prefCell,size);
                    mapGrid.add(elementBoxPreferredField, i - xMin + 1, yMax - j + 1);
                }
                if (optionalElement.isPresent()) {
                    WorldElement worldElement = optionalElement.get();
                    WorldElementBox elementBox;

                    if (worldElement instanceof Animal) {
                        if (popularGenotype.equals(((Animal)worldElement).getGenotype()) && showGenotypeBool) {
                            GenotypeCell popGenomeCell = new GenotypeCell(position);
                            WorldElementBox elementBoxPopularGenome = new WorldElementBox(popGenomeCell,size);
                            if (prefPos.contains(position) && showFieldsBool) {
                                elementBoxPopularGenome.setPreferred(true);
                            }
                            mapGrid.add(elementBoxPopularGenome, i - xMin + 1, yMax - j + 1);
                        }
                    }

                    if (lastClickedAnimal != null && lastClickedAnimal.getPosition().equals(worldElement.getPosition())) {
                        if (lastClickedAnimal.getDeathDate() == -1) {
                            elementBox = new WorldElementBox(lastClickedAnimal,size);
                            elementBox.updateImageTrackedDown(lastClickedAnimal,size);
                            lastElementBox = elementBox;
                        } else {
                            elementBox = new WorldElementBox(worldElement,size);
                            lastClickedAnimal = null;
                        }
                    } else {
                        elementBox = new WorldElementBox(worldElement,size);
                    }

                    // Dodaj otoczkę jeśli pozycja jest preferowana
                    if (prefPos.contains(position) && showFieldsBool) {
                        elementBox.setPreferred(true);
                    }

                    elementBox.setOnMouseClicked(event -> {
                        if (worldElement instanceof Animal) {
                            Animal clickedAnimal = (Animal) worldElement;

                            if (lastClickedAnimal == clickedAnimal) {
                                clearAnimalInfo();
                                elementBox.updateImage(clickedAnimal,size);
                                lastClickedAnimal = null;

                                //selectedAnimalStats.setVisible(false);
                            } else if (lastClickedAnimal != null) {

                                lastElementBox.updateImage(lastClickedAnimal,size);
                                lastElementBox = elementBox;
                                lastClickedAnimal = clickedAnimal;
                                elementBox.updateImageTrackedDown(clickedAnimal,size);

                                showAnimalInfo(clickedAnimal);
                            } else {
                                lastElementBox = elementBox;
                                lastClickedAnimal = clickedAnimal;
                                elementBox.updateImageTrackedDown(clickedAnimal,size);
                                showAnimalInfo(clickedAnimal);
                            }
                        }
                    });

                    mapGrid.add(elementBox, i - xMin + 1, yMax - j + 1);

                }
                else {
                    // Dla pustych preferowanych pól dodaj pusty WorldElementBox z otoczką
                    if (prefPos.contains(position) && showFieldsBool) {
                        WorldElementBox emptyPreferredBox = new WorldElementBox(new PrefferdCell(position),size);
                        emptyPreferredBox.setPreferred(true);
                        mapGrid.add(emptyPreferredBox, i - xMin + 1, yMax - j + 1);
                    } else {
                        EmptyCell back = new EmptyCell(position);
                        WorldElementBox emptyBox = new WorldElementBox(back,size);
                        mapGrid.add(emptyBox, i - xMin + 1, yMax - j + 1);
                    }
                }
            }
        }
    }

    private void showAnimalInfo(WorldElement worldElement) {
        selectedAnimalStats.setVisible(true);
        if (worldElement instanceof Animal) {
            Animal animal = (Animal) worldElement;
            Platform.runLater(() -> {
                animalInfoLabelGenome.setText(Arrays.toString(animal.getGenome()));
                animalInfoLabelGenomeIndex.setText(String.valueOf(animal.getGeneIndex()));
                animalInfoLabelEnergy.setText(String.valueOf(animal.getEnergy()));
                animalInfoLabelGrassEaten.setText(String.valueOf(animal.getPlantsEaten()));
                animalInfoLabelChildrenAmount.setText(String.valueOf(animal.getChildrenMade()));
                animalInfoLabelPosition.setText(String.valueOf(animal.getPosition()));
                animalInfoLabelAge.setText(String.valueOf(animal.getAge()));
                animalInfoLabelDeathDate.setText(String.valueOf(animal.getDeathDate()));
            });
        }
    }
    private void clearAnimalInfo() {
        Platform.runLater(() -> {
            animalInfoLabelGenome.setText("");
            animalInfoLabelGenomeIndex.setText("");
            animalInfoLabelEnergy.setText("");
            animalInfoLabelGrassEaten.setText("");
            animalInfoLabelChildrenAmount.setText("");
            animalInfoLabelPosition.setText("");
            animalInfoLabelAge.setText("");
            animalInfoLabelDeathDate.setText("");
        });
    }
    @FXML
    public void onShowGenotype(ActionEvent actionEvent) {
        showGenotypeBool = !showGenotypeBool;
        clearGrid();
        drawMap();

    }
    @FXML
    public void onShowFields(ActionEvent actionEvent) {
        showFieldsBool = !showFieldsBool;
        clearGrid();
        drawMap();
        if(showFieldsBool){
            clearGrid();
            addElements();
            drawMap();
        }
        else
        {
            clearGrid();
            drawMap();
        }
    }
    @Override
    public void mapChanged(WorldMap worldMap, String message, Statistics statistics) {
        synchronized (simulationLock) {
            setWorldMap(worldMap);
            popularGenotype = worldMap.getMostPopularGenotype();

            Platform.runLater(() -> {
                try {
                    clearGrid();
                    drawMap();
                    this.displayGeneralStatistics(statistics);
                    updateCharts(statistics);
                    if (exportDailyStatisticsBool) {
                        exportCsvStatistics(worldMap, statistics);
                    }
                    if (lastClickedAnimal != null) {
                        showAnimalInfo(lastClickedAnimal);
                    }
                } finally {
                    synchronized (simulationLock) {
                        uiUpdated = true;
                        simulationLock.notify();
                    }
                }
            });

            // Czekaj na zakończenie aktualizacji UI
            while (!uiUpdated) {
                try {
                    simulationLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            uiUpdated = false;
        }
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

        energyChart.getData().get(0).getData().add(new XYChart.Data<>(currentDay, statistics.getAverageAnimalsEnergy()));
    }

    private void initializeCharts() {
        // Create series for animals
        XYChart.Series<Number, Number> animalSeries = new XYChart.Series<>();
        animalChart.getData().add(animalSeries);

        // Create series for grass
        XYChart.Series<Number, Number> grassSeries = new XYChart.Series<>();
        grassChart.getData().add(grassSeries);

        // energy
        XYChart.Series<Number, Number> energySeries = new XYChart.Series<>();
        energyChart.getData().add(energySeries);
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
            synchronized (simulationLock) {
                simulation.pause();
                Platform.runLater(() -> {
                    pauseButton.setDisable(true);
                    continueButton.setDisable(false);
                });
            }
        }
    }

    @FXML
    public void onContinueClicked(ActionEvent actionEvent) {
        if (simulation != null) {
            synchronized (simulationLock) {
                simulation.start();
                Platform.runLater(() -> {
                    pauseButton.setDisable(false);
                    continueButton.setDisable(true);
                });
            }
        }
    }
    public void initializeSimulation() {
        statsBox.setVisible(true);
        Charts.setVisible(true);
        continueButton.setDisable(true);

        AbstractWorldMap map1;

        try {
            if (simulationProperties.getMapType() == MapType.GLOBE) {
                map1 = new GrassField(simulationProperties);
                map1.addObserver((MapChangeListener) this);
                Simulation simulation1 = new Simulation(map1, simulationProperties);
                this.simulation = simulation1;
                SimulationEngine engine = new SimulationEngine(List.of(simulation1));
                engine.runAsync();
            } else {
                map1 = new OwlBearMap(simulationProperties);
                map1.addObserver((MapChangeListener) this);
                Simulation simulation1 = new Simulation(map1, simulationProperties);
                this.simulation = simulation1;
                SimulationEngine engine = new SimulationEngine(List.of(simulation1));
                engine.runAsync();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        showGeneralStatistics();
        prefPos = simulation.getPreferedPositions();

        if (Objects.equals(simulationProperties.getCSV(), "Yes"))
        {
            exportDailyStatisticsBool = true;
        }
    }

    public void setSimulationProperties(SimulationProperties properties) {
        this.simulationProperties = properties;
        initializeSimulation();
    }

    @FXML
    public void initialize() {
        statsBox.setVisible(true);
        Charts.setVisible(true);
        continueButton.setDisable(true);
        initializeCharts();
        showGeneralStatistics();
    }

    @FXML
    public void exportCsvStatistics(WorldMap worldMap, Statistics statistics) {
        if (worldMap == null || statistics == null) {
            return;
        }

        String projectPath = System.getProperty("user.dir");
        String filename = "General_Statistics_" + worldMap.getId() + ".csv";
        String filePath = projectPath + "/CSV/GeneralStatistics/" + filename;

        File csvFile = new File(filePath);
        File parentDirectory = csvFile.getParentFile();
        if (!parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }

        boolean fileExist = csvFile.exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            if (!fileExist) {
                CSV.writeCSVHeader(writer);
            }
            CSV.fillStatisticsDay(writer, worldMap.getId(), statistics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}