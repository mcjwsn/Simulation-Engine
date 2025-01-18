package agh.ics.oop.presenter;

import agh.ics.oop.Statistics;
import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import agh.ics.oop.SimulationApp;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StartScreenController implements MapChangeListener {
    private SimulationApp mainApp;

    @FXML
    private Button startButton;

    @Override
    public void mapChanged(WorldMap worldMap, String message, Statistics statistics) {
    }

    @FXML
    public void onStartClicked() throws Exception {
        // Pobierz aktualne okno z przycisku
        Stage currentStage = (Stage) startButton.getScene().getWindow();
        // Zamknij aktualne okno
        currentStage.close();

        // Otwórz nowe okno
        Stage newStage = new Stage();
        mainApp.showSettingsScreen(newStage);
    }

    public void setMainApp(SimulationApp mainApp) {
        this.mainApp = mainApp;
    }
}