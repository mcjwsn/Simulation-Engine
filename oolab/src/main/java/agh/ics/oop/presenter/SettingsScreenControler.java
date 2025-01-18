package agh.ics.oop.presenter;

import agh.ics.oop.SimulationApp;
import agh.ics.oop.model.Enums.MapType;
import agh.ics.oop.model.Enums.MovinType;
import agh.ics.oop.model.Enums.MutationType;
import agh.ics.oop.model.SimulationProperties;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.Button;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SettingsScreenControler {
    private SimulationApp mainApp;
    @FXML
    private Button startSimulationButton;

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
        initialize();

        SimulationProperties simulationProperties = new SimulationProperties(mapWidth, mapHeight, equatorHeight, animalNumber, grassNumber,
                dailySpawningGrass, startEnergy, grassEnergy, maxEnergy,
                movingType,mutationType, mapType,  genesCount,
                energyLevelNeededToReproduce, energyLevelToPassToChild,moveEnergy,
                minMutation, maxMutation);
        Stage newStage = new Stage();
        mainApp.showSimulationScreen(newStage, simulationProperties);
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
