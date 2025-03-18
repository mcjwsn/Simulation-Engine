package agh.ics.oop.presenter;

import agh.ics.oop.simulation.SimulationApp;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.enums.MovinType;
import agh.ics.oop.model.enums.MutationType;
import agh.ics.oop.simulation.SimulationProperties;
import agh.ics.oop.presenter.CsvManager;
import agh.ics.oop.presenter.SimulationConfig;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.*;

public class SettingsScreenControler {
    private SimulationApp mainApp;

    @FXML private Spinner<Integer> widthSpinner;
    @FXML private Spinner<Integer> heightSpinner;
    @FXML private Spinner<Integer> equatorHeightSpinner;
    @FXML private Spinner<Integer> grassNumberSpinner;
    @FXML private Spinner<Integer> energyAdditionSpinner;
    @FXML private Spinner<Integer> plantRegenerationSpinner;
    @FXML private Spinner<Integer> numberOfAnimalsSpinner;
    @FXML private Spinner<Integer> startingAnimalEnergySpinner;
    @FXML private Spinner<Integer> energuNeededForReproductionSpinner;
    @FXML private Spinner<Integer> energyLosingWithReproductionSpinner;
    @FXML private Spinner<Integer> minGenMutationsSpinner;
    @FXML private Spinner<Integer> maxGenMutationsSpinner;
    @FXML private Spinner<Integer> genomLengthSpinner;
    @FXML private Spinner<MutationType> mutationTypeSpinner;
    @FXML private Spinner<Integer> maxEnergySpinner;
    @FXML private Spinner<MapType> mapTypeSpinner;
    @FXML private Spinner<Integer> moveEnergySpinner;
    @FXML private Spinner<String> CSVSpinner;

    private final CsvManager csvManager = new CsvManager();
    private static final String CSV_DIRECTORY = System.getProperty("user.dir") + "/CSV/Exports/";

    @FXML
    public void onStartSimulationClicked() {
        try {
            SimulationProperties simulationProperties = createSimulationProperties();
            Stage newStage = new Stage();
            mainApp.showSimulationScreen(newStage, simulationProperties);
        } catch (Exception e) {
            showErrorAlert("Simulation Error", "Failed to start simulation", e.getMessage());
        }
    }

    private SimulationProperties createSimulationProperties() {
        int mapWidth = widthSpinner.getValue() - 1;
        int mapHeight = heightSpinner.getValue() - 1;
        int equatorHeight = Math.min(equatorHeightSpinner.getValue(), mapWidth);
        int animalNumber = numberOfAnimalsSpinner.getValue();
        int grassNumber = Math.min(grassNumberSpinner.getValue(), mapHeight * mapWidth);
        int grassEnergy = energyAdditionSpinner.getValue();
        int dailySpawningGrass = Math.min(plantRegenerationSpinner.getValue(), mapHeight * mapWidth);
        int maxEnergy = maxEnergySpinner.getValue();
        int startEnergy = Math.min(startingAnimalEnergySpinner.getValue(), maxEnergy);
        MovinType movingType = MovinType.DEFAULT;
        MutationType mutationType = mutationTypeSpinner.getValue();
        MapType mapType = mapTypeSpinner.getValue();
        int genesCount = genomLengthSpinner.getValue();
        int energyLevelNeededToReproduce = energuNeededForReproductionSpinner.getValue();
        int energyLevelToPassToChild = energyLosingWithReproductionSpinner.getValue();
        int moveEnergy = moveEnergySpinner.getValue();
        int minMutation = minGenMutationsSpinner.getValue();
        int maxMutation = Math.max(maxGenMutationsSpinner.getValue(), minMutation);
        String CSV = CSVSpinner.getValue();

        return new SimulationProperties(
                mapWidth, mapHeight, equatorHeight, animalNumber, grassNumber,
                dailySpawningGrass, startEnergy, grassEnergy, maxEnergy,
                movingType, mutationType, mapType, genesCount,
                energyLevelNeededToReproduce, energyLevelToPassToChild, moveEnergy,
                minMutation, maxMutation, CSV
        );
    }

    @FXML
    private void onExportCsvClicked() {
        try {
            File selectedFile = showSaveFileDialog();
            if (selectedFile == null) {
                return;
            }

            SimulationConfig config = getCurrentConfig();
            csvManager.exportConfig(selectedFile, config);
        } catch (IOException e) {
            showErrorAlert("Export Error", "Failed to export configuration", e.getMessage());
        }
    }

    @FXML
    private void onImportCsvClicked() {
        try {
            File selectedFile = showOpenFileDialog();
            if (selectedFile == null) {
                return;
            }

            SimulationConfig config = csvManager.importConfig(selectedFile);
            applyConfigToUI(config);
        } catch (IOException e) {
            showErrorAlert("Import Error", "Failed to import configuration", e.getMessage());
        } catch (IllegalArgumentException e) {
            showErrorAlert("Import Error", "Invalid value in configuration file", e.getMessage());
        }
    }

    private File showSaveFileDialog() {
        FileChooser fileChooser = createFileChooser();
        return fileChooser.showSaveDialog(null);
    }

    private File showOpenFileDialog() {
        FileChooser fileChooser = createFileChooser();
        return fileChooser.showOpenDialog(null);
    }

    private FileChooser createFileChooser() {
        FileChooser fileChooser = new FileChooser();

        File initialDirectory = new File(CSV_DIRECTORY);
        if (!initialDirectory.exists()) {
            initialDirectory.mkdirs();
        }
        fileChooser.setInitialDirectory(initialDirectory);

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        return fileChooser;
    }

    private SimulationConfig getCurrentConfig() {
        return new SimulationConfig(
                widthSpinner.getValue(),
                heightSpinner.getValue(),
                equatorHeightSpinner.getValue(),
                grassNumberSpinner.getValue(),
                energyAdditionSpinner.getValue(),
                plantRegenerationSpinner.getValue(),
                numberOfAnimalsSpinner.getValue(),
                startingAnimalEnergySpinner.getValue(),
                energuNeededForReproductionSpinner.getValue(),
                energyLosingWithReproductionSpinner.getValue(),
                minGenMutationsSpinner.getValue(),
                maxGenMutationsSpinner.getValue(),
                genomLengthSpinner.getValue(),
                mutationTypeSpinner.getValue(),
                maxEnergySpinner.getValue(),
                mapTypeSpinner.getValue(),
                moveEnergySpinner.getValue(),
                CSVSpinner.getValue()
        );
    }

    private void applyConfigToUI(SimulationConfig config) {
        setSpinnerValue(widthSpinner, config.mapWidth());
        setSpinnerValue(heightSpinner, config.mapHeight());
        setSpinnerValue(equatorHeightSpinner, config.equatorHeight());
        setSpinnerValue(grassNumberSpinner, config.grassNumber());
        setSpinnerValue(energyAdditionSpinner, config.energyAddition());
        setSpinnerValue(plantRegenerationSpinner, config.plantRegeneration());
        setSpinnerValue(numberOfAnimalsSpinner, config.numberOfAnimals());
        setSpinnerValue(startingAnimalEnergySpinner, config.startingAnimalEnergy());
        setSpinnerValue(energuNeededForReproductionSpinner, config.energyNeededForReproduction());
        setSpinnerValue(energyLosingWithReproductionSpinner, config.energyLosingWithReproduction());
        setSpinnerValue(minGenMutationsSpinner, config.minGenMutations());
        setSpinnerValue(maxGenMutationsSpinner, config.maxGenMutations());
        setSpinnerValue(genomLengthSpinner, config.genomeLength());
        setSpinnerValue(mutationTypeSpinner, config.mutationType());
        setSpinnerValue(maxEnergySpinner, config.maxEnergy());
        setSpinnerValue(mapTypeSpinner, config.mapType());
        setSpinnerValue(moveEnergySpinner, config.moveEnergy());
        setSpinnerValue(CSVSpinner, config.csvSaveStats());
    }

    private <T> void setSpinnerValue(Spinner<T> spinner, T value) {
        SpinnerValueFactory<T> factory = spinner.getValueFactory();
        if (factory != null) {
            factory.setValue(value);
        }
    }

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setMainApp(SimulationApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        initializeMapTypeSpinner();
        initializeMutationTypeSpinner();
        initializeCsvSpinner();
    }

    private void initializeMapTypeSpinner() {
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

        SpinnerValueFactory<MapType> mapTypeFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(
                FXCollections.observableArrayList(MapType.OWLBEAR, MapType.GLOBE));
        mapTypeFactory.setConverter(mapTypeConverter);
        mapTypeSpinner.setValueFactory(mapTypeFactory);
        mapTypeFactory.setValue(MapType.OWLBEAR);
        mapTypeSpinner.setEditable(false);
    }

    private void initializeMutationTypeSpinner() {
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

        SpinnerValueFactory<MutationType> mutationTypeFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(
                FXCollections.observableArrayList(MutationType.values()));
        mutationTypeFactory.setConverter(mutationTypeConverter);
        mutationTypeSpinner.setValueFactory(mutationTypeFactory);
        mutationTypeFactory.setValue(MutationType.LITLLECHANGE);
        mutationTypeSpinner.setEditable(false);
    }

    private void initializeCsvSpinner() {
        SpinnerValueFactory<String> optionsCSV = new SpinnerValueFactory.ListSpinnerValueFactory<>(
                FXCollections.observableArrayList("Yes", "No"));
        CSVSpinner.setValueFactory(optionsCSV);
        optionsCSV.setValue("No");
    }
}