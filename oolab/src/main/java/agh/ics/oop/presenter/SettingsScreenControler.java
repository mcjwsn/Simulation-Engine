package agh.ics.oop.presenter;

import agh.ics.oop.SimulationApp;
import agh.ics.oop.model.SimulationProperties;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.Button;

public class SettingsScreenControler {
    private SimulationApp mainApp;

    @FXML
    private Spinner<Integer> widthSpinner;
    @FXML
    private Spinner<Integer> heightSpinner;

    @FXML
    private Button startSimulationButton;

    @FXML
    public void onStartSimulationClicked() throws Exception {
       // SimulationProperties properties = new SimulationProperties(
               // widthSpinner.getValue(),
               // heightSpinner.getValue()
                // Dodaj inne ustawienia
       // );
        //mainApp.showSimulationScreen(properties);
    }

    public void setMainApp(SimulationApp mainApp) {
        this.mainApp = mainApp;
    }
}
