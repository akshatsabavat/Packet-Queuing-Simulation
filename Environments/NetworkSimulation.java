package Environments;

import java.util.ArrayList;
import java.util.Random;

import DataStructures.BufferQueue;
import DataStructures.BufferState;
import DataStructures.Packet;
import DataStructures.SimulationInstance;

public class NetworkSimulation {
    private int lambda;
    private int mu;
    private int events;

    // I am pretty much instantiating this here as we will need this so we can
    // define it here and define a getter function to then store it in our final
    // results
    private ArrayList<SimulationInstance> simulationRuns;

    // Constructor to define a simulation instance
    public NetworkSimulation(int lambda, int mu, int events) {
        this.events = events;
        this.lambda = lambda;
        this.mu = mu;
        this.simulationRuns = new ArrayList<>();
    }

    public void RunSimulation(BufferQueue PktQueue, BufferState SQ) {
        // for event generation
        Random random = new Random();

        // Simulation Probabilities
        double Pa = (double) lambda / (mu + lambda); // arrival event probability

        for (int i = 0; i < events; i++) {
            double y = random.nextDouble(); // random number b/w 0.0 & 1.0
            if (y < Pa) {
                // this triggers an arrival event so hence, the pkt enters
                System.out.println(y + Pa);
                Packet pkt = new Packet(i);
                PktQueue.EnqueuePktToBuffer(pkt, SQ);
            } else {
                // this triggers a departure event
                PktQueue.DequeuePktFromBuffer(SQ);
            }
            // once a run is completed, we will define that instance and store it in our
            // simulation runs ArrayList
            SimulationInstance simulationInstance = new SimulationInstance(i, SQ.pktsInQueue, SQ.pktsDropped);
            simulationRuns.add(simulationInstance);
        }
    }

    // getter function to get all our simulation runs for the respective environment
    public ArrayList<SimulationInstance> getSimulationRuns() {
        return simulationRuns;
    }

    // Other getter functions for environment attributes
    public int getSimulationLambda() {
        return lambda;
    }

    public int getSimulationMu() {
        return mu;
    }
}
