package agh.ics.oop;
import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        try {
            ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
            List<Vector2d> positions2 = List.of(new Vector2d(2,1), new Vector2d(2,3), new Vector2d(5,5));
            AbstractWorldMap map1 = new GrassField(10);
            AbstractWorldMap map3 = new GrassField(22);
            map1.addObserver(consoleMapDisplay);
            map3.addObserver(consoleMapDisplay);
            Simulation simulation1 = new Simulation(directions, positions, (WorldMap) map1);
            Simulation simulation3 = new Simulation(directions, positions2, (WorldMap) map3);
            SimulationEngine engine = new SimulationEngine(List.of(simulation1, simulation3));

            engine.runAsyncInThreadPool();
            engine.awaitSimulationEnd();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println("System zakonczyl dzialanie");
}
}