package agh.ics.oop.simulation;

import agh.ics.oop.presenter.SettingsScreenControler;
import agh.ics.oop.presenter.SimulationController;
import agh.ics.oop.presenter.StartScreenController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SimulationApp extends Application {
    private SimulationEngine simulationEngine;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Globalna obsługa zamknięcia aplikacji
        Platform.setImplicitExit(true);

        // Inicjalizacja pierwszego ekranu
        showStartScreen(primaryStage);
    }

    public void showStartScreen(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("startScreen.fxml"));
        Image icon = new Image(String.valueOf(getClass().getClassLoader().getResource("icon.jpg")));
        Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 14);
        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(loader.load());
        StartScreenController controller = loader.getController();
        controller.setMainApp(this);

        // Ustawienie funkcji zamknięcia okna
        primaryStage.setOnCloseRequest(event -> handleApplicationClose());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Start Screen");
        primaryStage.show();
    }

    public void showSettingsScreen(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("settingsScreen.fxml"));
        Image icon = new Image(String.valueOf(getClass().getClassLoader().getResource("icon.jpg")));
        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(loader.load());
        SettingsScreenControler controller = loader.getController();
        controller.setMainApp(this);

        // Ustawienie funkcji zamknięcia okna
        primaryStage.setOnCloseRequest(event -> handleApplicationClose());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Settings");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public void showSimulationScreen(Stage primaryStage, SimulationProperties properties) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulationScreen.fxml"));
        Image icon = new Image(String.valueOf(getClass().getClassLoader().getResource("icon.jpg")));
        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(loader.load());
        SimulationController controller = loader.getController();
        controller.setSimulationProperties(properties);

        // Ustawienie funkcji zamknięcia okna dla symulacji
        primaryStage.setOnCloseRequest(event -> {
            // Zatrzymanie symulacji przed zamknięciem
            if (simulationEngine != null) {
                simulationEngine.stopSimulations();
            }
            handleApplicationClose();
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
        primaryStage.setMaximized(true);
        primaryStage.show();

        // Zapisz referencję do silnika symulacji, jeśli jest dostępny
        this.simulationEngine = controller.getSimulationEngine();
    }

    // Metoda do obsługi zamknięcia aplikacji
    private void handleApplicationClose() {
        // Zatrzymaj silnik symulacji, jeśli istnieje
        if (simulationEngine != null) {
            simulationEngine.stopSimulations();

            // Daj trochę czasu na zatrzymanie wątków
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Zamknij aplikację
        Platform.exit();
        System.exit(0);
    }

    // Dodaj metodę, która pozwala ustawić silnik symulacji z zewnątrz
    public void setSimulationEngine(SimulationEngine engine) {
        this.simulationEngine = engine;
    }
}