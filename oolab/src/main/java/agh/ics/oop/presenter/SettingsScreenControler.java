package agh.ics.oop.presenter;

import agh.ics.oop.SimulationApp;
import agh.ics.oop.model.Enums.MapType;
import agh.ics.oop.model.Enums.MovinType;
import agh.ics.oop.model.Enums.MutationType;
import agh.ics.oop.model.SimulationProperties;
import agh.ics.oop.model.util.CSV;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.*;

public class SettingsScreenControler {
    private SimulationApp mainApp;

    @FXML
    private Spinner<Integer> widthSpinner;
    @FXML
    private Spinner<Integer> heightSpinner;
    @FXML
    private Spinner<Integer> equatorHeightSpinner;
    @FXML
    private Spinner<Integer> grassNumberSpinner;
    @FXML
    private Spinner<Integer> energyAdditionSpinner;
    @FXML
    // uzależnić od liczby zwierzakow i mapy
    private Spinner<Integer> plantRegenerationSpinner;
    @FXML
    private Spinner<Integer> numberOfAnimalsSpinner;
    @FXML
    private Spinner<Integer> startingAnimalEnergySpinner;
    @FXML
    private Spinner<Integer> energuNeededForReproductionSpinner;
    @FXML
    private Spinner<Integer> energyLosingWithReproductionSpinner;
    @FXML
    private Spinner<Integer> minGenMutationsSpinner;
    @FXML
    private Spinner<Integer> maxGenMutationsSpinner;
    @FXML
    private Spinner<Integer> genomLengthSpinner;
    @FXML
    private Spinner<MutationType> mutationTypeSpinner;
    @FXML
    private Spinner<Integer> maxEnergySpinner;
    @FXML
    private Spinner<MapType> mapTypeSpinner;
    @FXML
    private Spinner<Integer> moveEnergySpinner;
    @FXML
    private Spinner<String> CSVSpinner;

    @FXML
    public void onStartSimulationClicked() throws Exception {

        int mapWidth = widthSpinner.getValue()-1;
        int mapHeight = heightSpinner.getValue()-1;
        int equatorHeight = equatorHeightSpinner.getValue();
        equatorHeight = Math.min(equatorHeight, mapWidth);
        int animalNumber = numberOfAnimalsSpinner.getValue();
        int grassNumber = grassNumberSpinner.getValue();
        int grassEnergy = energyAdditionSpinner.getValue();
        int dailySpawningGrass = plantRegenerationSpinner.getValue();
        int startEnergy = startingAnimalEnergySpinner.getValue();
        int maxEnergy = maxEnergySpinner.getValue();
        MovinType movingType = MovinType.DEFAULT;
        MutationType mutationType = mutationTypeSpinner.getValue();
        MapType mapType = mapTypeSpinner.getValue();
        int genesCount = genomLengthSpinner.getValue();
        int energyLevelNeededToReproduce = energuNeededForReproductionSpinner.getValue();
        int energyLevelToPassToChild = energyLosingWithReproductionSpinner.getValue();
        int moveEnergy = moveEnergySpinner.getValue();
        int minMutation = minGenMutationsSpinner.getValue();
        int maxMutation = maxGenMutationsSpinner.getValue();
        String CSV = CSVSpinner.getValue();
        initialize();

        SimulationProperties simulationProperties = new SimulationProperties(mapWidth, mapHeight, equatorHeight, animalNumber, grassNumber,
                dailySpawningGrass, startEnergy, grassEnergy, maxEnergy,
                movingType,mutationType, mapType,  genesCount,
                energyLevelNeededToReproduce, energyLevelToPassToChild,moveEnergy,
                minMutation, maxMutation, CSV);
        Stage newStage = new Stage();
        mainApp.showSimulationScreen(newStage, simulationProperties);
    }

    @FXML
    private void onExportCsvClicked() throws Exception {
        String projectPath = System.getProperty("user.dir") + "/CSV";
        String defaultDirectory = projectPath + "/Exports/";

        FileChooser fileChooser = new FileChooser();

        File initialDirectory = new File(defaultDirectory);
        if (!initialDirectory.exists()) {
            initialDirectory.mkdirs();
        }
        fileChooser.setInitialDirectory(initialDirectory);

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile == null) {
            return;
        }

        String filePath = selectedFile.getAbsolutePath();

        if (!filePath.endsWith(".csv")) {
            filePath += ".csv";
        }

        File parentDirectory = selectedFile.getParentFile();
        if (!parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            if (!selectedFile.exists()) {
                writer.println("Parameter,Value");
            }

            writer.println("MapWidth," + widthSpinner.getValue());
            writer.println("MapHeight," + heightSpinner.getValue());
            writer.println("EquatorHeight," + equatorHeightSpinner.getValue());
            writer.println("GrassNumber," + grassNumberSpinner.getValue());
            writer.println("EnergyAddition," + energyAdditionSpinner.getValue());
            writer.println("PlantRegeneration," + plantRegenerationSpinner.getValue());
            writer.println("NumberOfAnimals," + numberOfAnimalsSpinner.getValue());
            writer.println("StartingAnimalEnergy," + startingAnimalEnergySpinner.getValue());
            writer.println("EnergyNeededForReproduction," + energuNeededForReproductionSpinner.getValue());
            writer.println("EnergyLosingWithReproduction," + energyLosingWithReproductionSpinner.getValue());
            writer.println("MinGenMutations," + minGenMutationsSpinner.getValue());
            writer.println("MaxGenMutations," + maxGenMutationsSpinner.getValue());
            writer.println("GenomLength," + genomLengthSpinner.getValue());
            writer.println("MutationType," + mutationTypeSpinner.getValue());
            writer.println("MaxEnergy," + maxEnergySpinner.getValue());
            writer.println("MapType," + mapTypeSpinner.getValue());
            writer.println("MoveEnergy," + moveEnergySpinner.getValue());
            writer.println("CSVSaveStats," + CSVSpinner.getValue());
        }
    }

    @FXML
    private void onImportCsvClicked() throws Exception {
        String projectPath = System.getProperty("user.dir");
        String defaultDirectory = projectPath + "/CSV/Exports/";

        FileChooser fileChooser = new FileChooser();
        File initialDirectory = new File(defaultDirectory);
        if (!initialDirectory.exists()) {
            initialDirectory.mkdirs();
        }
        fileChooser.setInitialDirectory(initialDirectory);

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) {
            return;
        }

        String filePath = selectedFile.getAbsolutePath();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String parameter = parts[0].trim();
                    String value = parts[1].trim();

                    try {
                        switch (parameter) {
                            case "MapWidth":
                                widthSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "MapHeight":
                                heightSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "EquatorHeight":
                                equatorHeightSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "GrassNumber":
                                grassNumberSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "EnergyAddition":
                                energyAdditionSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "PlantRegeneration":
                                plantRegenerationSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "NumberOfAnimals":
                                numberOfAnimalsSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "StartingAnimalEnergy":
                                startingAnimalEnergySpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "EnergyNeededForReproduction":
                                energuNeededForReproductionSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "EnergyLosingWithReproduction":
                                energyLosingWithReproductionSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "MinGenMutations":
                                minGenMutationsSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "MaxGenMutations":
                                maxGenMutationsSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "GenomLength":
                                genomLengthSpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "MutationType":
                                mutationTypeSpinner.getValueFactory().setValue(MutationType.valueOf(value));  // Parsing enum
                                break;
                            case "MaxEnergy":
                                maxEnergySpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "MapType":
                                mapTypeSpinner.getValueFactory().setValue(MapType.valueOf(value));  // Parsing enum
                                break;
                            case "MoveEnergy":
                                moveEnergySpinner.getValueFactory().setValue(Integer.parseInt(value));
                                break;
                            case "CSVSaveStats":
                                CSVSpinner.getValueFactory().setValue(String.valueOf(Integer.parseInt(value)));
                                break;
                            default:
                                break;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format for parameter " + parameter + ": " + value);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid enum value for parameter " + parameter + ": " + value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainApp(SimulationApp mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    public void initialize() {
        // Create StringConverter for MapType
        StringConverter<MapType> mapTypeConverter = new StringConverter<>() {
            @Override
            public String toString(MapType mapType) {
                if (mapType == null) return "";
                return mapType.name();
            }

            @Override
            public MapType fromString(String string) {
                if (string == null || string.trim().isEmpty()) return MapType.OWLBEAR;
                return MapType.valueOf(string.trim());
            }
        };

        // Create StringConverter for MutationType
        StringConverter<MutationType> mutationTypeConverter = new StringConverter<>() {
            @Override
            public String toString(MutationType mutationType) {
                if (mutationType == null) return "";
                return mutationType.name();
            }

            @Override
            public MutationType fromString(String string) {
                if (string == null || string.trim().isEmpty()) return MutationType.values()[0];
                return MutationType.valueOf(string.trim());
            }
        };


        SpinnerValueFactory<String> optionsCSV = new SpinnerValueFactory.ListSpinnerValueFactory<>(
                javafx.collections.FXCollections.observableArrayList("Yes", "No"));
        CSVSpinner.setValueFactory(optionsCSV);
        optionsCSV.setValue("Yes");


        // Initialize MapType spinner
        SpinnerValueFactory<MapType> mapTypeFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(
                FXCollections.observableArrayList(MapType.OWLBEAR, MapType.GLOBE)
        );
        mapTypeFactory.setConverter(mapTypeConverter);
        mapTypeSpinner.setValueFactory(mapTypeFactory);
        mapTypeFactory.setValue(MapType.OWLBEAR); // Set default value

        // Initialize MutationType spinner
        SpinnerValueFactory<MutationType> mutationTypeFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(
                FXCollections.observableArrayList(MutationType.values())
        );
        mutationTypeFactory.setConverter(mutationTypeConverter);
        mutationTypeSpinner.setValueFactory(mutationTypeFactory);
        mutationTypeFactory.setValue(MutationType.values()[0]); // Set default value

        // Disable editing to prevent invalid input
        mapTypeSpinner.setEditable(false);
        mutationTypeSpinner.setEditable(false);
    }
}