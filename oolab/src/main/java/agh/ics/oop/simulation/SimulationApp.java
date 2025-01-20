package agh.ics.oop.simulation;

import agh.ics.oop.presenter.SettingsScreenControler;
import agh.ics.oop.presenter.SimulationController;
import agh.ics.oop.presenter.StartScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("startScreen.fxml"));
        Image icon = new Image(String.valueOf(getClass().getClassLoader().getResource("icon.jpg")));
        Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 14);
        primaryStage.getIcons().add(icon);
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
        Image icon = new Image(String.valueOf(getClass().getClassLoader().getResource("icon.jpg")));
        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(loader.load());
        SettingsScreenControler controller = loader.getController();
        controller.setMainApp(this);
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
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}