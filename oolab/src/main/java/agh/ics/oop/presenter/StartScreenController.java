package agh.ics.oop.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import agh.ics.oop.SimulationApp;

public class StartScreenController {
    private SimulationApp mainApp;

    @FXML
    private Button startButton;

    @FXML
    public void onStartClicked() throws Exception {
        mainApp.showSettingsScreen();
    }

    public void setMainApp(SimulationApp mainApp) {
        this.mainApp = mainApp;
    }
}