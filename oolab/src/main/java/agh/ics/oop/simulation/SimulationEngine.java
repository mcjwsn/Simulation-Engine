package agh.ics.oop.simulation;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();

    public SimulationEngine(List< Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runAsync(){
        for (Simulation simulation : simulations) {
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
    }
}