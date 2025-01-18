package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.presenter.SettingsScreenControler;
import agh.ics.oop.presenter.SimulationController;
import agh.ics.oop.presenter.StartScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationApp extends Application {
    private Stage primaryStage;

    public void start3(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        MapChangeListener listener = loader.getController();
        BorderPane viewRoot = loader.load();
        configureStage(primaryStage, viewRoot);
        primaryStage.show();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("startScreen.fxml"));
        Scene scene = new Scene(loader.load());
        StartScreenController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Start Screen");
        primaryStage.show();
    }

    public void showSettingsScreen(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("settingsScreen.fxml"));
        Scene scene = new Scene(loader.load());
        SettingsScreenControler controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Settings");
        primaryStage.show();
    }

    public void showSimulationScreen(SimulationProperties properties) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationScreen.fxml"));
        Scene scene = new Scene(loader.load());
        SimulationController controller = loader.getController();
        controller.setSimulationProperties(properties);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
    }


    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation App");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
