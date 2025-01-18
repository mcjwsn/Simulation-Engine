package agh.ics.oop.presenter;

import agh.ics.oop.model.SimulationProperties;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class SimulationController {
    private SimulationProperties simulationProperties;

    @FXML
    private GridPane mapGrid;

    public void setSimulationProperties(SimulationProperties properties) {
        this.simulationProperties = properties;
        initializeSimulation();
    }

    private void initializeSimulation() {
        // Ustawienie mapy i rozpoczęcie symulacji
    }
}
