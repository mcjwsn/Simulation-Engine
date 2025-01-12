package agh.ics.oop;
import agh.ics.oop.model.*;
import agh.ics.oop.model.modes.MapType;
import agh.ics.oop.model.modes.MovinType;
import agh.ics.oop.model.modes.MutationType;
import agh.ics.oop.model.util.Boundary;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        try {
            ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();

            //AbstractWorldMap map3 = new GrassField(22);
            SimulationProperties simulationProperties1 = new SimulationProperties(8,8,0,5,10,5,10,3,10, MovinType.DEFAULT, MutationType.FULLRANDOM, MapType.GLOBE,5,6,5,1,0,1);
            AbstractWorldMap map1 = new OwlBearMap(simulationProperties1);
            //SimulationProperties simulationProperties3 = new SimulationProperties();

            map1.addObserver(consoleMapDisplay);
            //map3.addObserver(consoleMapDisplay);
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