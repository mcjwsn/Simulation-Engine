package agh.ics.oop;
import agh.ics.oop.model.*;
import agh.ics.oop.model.modes.MapType;
import agh.ics.oop.model.modes.MovinType;
import agh.ics.oop.model.modes.MutationType;

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
            SimulationProperties simulationProperties1 = new SimulationProperties(5,5,0,3,10,2,10,5,25, MovinType.DEFAULT, MutationType.FULLRANDOM, MapType.GLOBE,5,10,5,1,0,1);
            //SimulationProperties simulationProperties3 = new SimulationProperties();

            map1.addObserver(consoleMapDisplay);
            map3.addObserver(consoleMapDisplay);
            Simulation simulation1 = new Simulation(map1,simulationProperties1);
            //Simulation simulation3 = new Simulation(map3,simulationProperties3 );
            SimulationEngine engine = new SimulationEngine(List.of(simulation1));

            engine.runAsyncInThreadPool();
            engine.awaitSimulationEnd();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println("System zakonczyl dzialanie");
}
}