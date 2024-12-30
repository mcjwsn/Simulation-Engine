package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class SimulationEngine {
    private final List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public SimulationEngine(List< Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runSync(){
        for (Simulation simulation : simulations) {
            simulation.run();
        }}
    public void runAsync(){
        for (Simulation simulation : simulations) {
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
    }

    public void awaitSimulationEnd() {
        try{
            for (Thread thread: threads){
                thread.join();
            }
            threadPool.shutdown();
            if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Wymuszenie zamknietcia systemu");
                threadPool.shutdownNow();
            }
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt(); // reset flagi przerwania
            System.out.println(e.getMessage());
        }
    }

    public void runAsyncInThreadPool() {
        for (Simulation simulation : simulations) {
            threadPool.submit(simulation);
        }
    }

//    @Override
//    public void run() {
//        System.out.println("Thread started.");
//    }
}
