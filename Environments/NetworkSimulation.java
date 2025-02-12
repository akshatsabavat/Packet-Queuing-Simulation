package Environments;

import java.util.ArrayList;
import java.util.Random;

import DataStructures.BufferQueue;
import DataStructures.BufferState;
import DataStructures.Packet;
import DataStructures.SimulationInstance;
import DataStructures.VariableLambdaScheduleEntries;

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

    // Method to run a simulation for fixed values
    public void RunSimulation(BufferQueue PktQueue, BufferState SQ) {
        // for event generation
        Random random = new Random();

        // Simulation Probabilities
        double Pa = (double) lambda / (mu + lambda); // arrival event probability

        for (int i = 0; i < events; i++) {
            double y = random.nextDouble(); // random number b/w 0.0 & 1.0
            if (y < Pa) {
                // this triggers an arrival event so hence, the pkt enters
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

    // Method to run a simulation for variable lambda values
    public void RunVariableInputRateSimulation(BufferQueue PktQueue, BufferState SQ,
            VariableLambdaScheduleEntries scheduleEntries) {
        // the only fundamental difference here would be that we are now calculating the
        // Pa at each event as lambda is now coming in at a variable rate that depends
        // upon the completion of events
        Random random = new Random();

        for (int i = 0; i < events; i++) {
            // invoke the method to get the LambdaValues based on the scheule enties
            int variable_lambda = scheduleEntries.getLambda(i, events);
            double Pa = (double) variable_lambda / (mu + variable_lambda);
            double y = random.nextDouble();
            if (y < Pa) {
                Packet pkt = new Packet(i);
                PktQueue.EnqueuePktToBuffer(pkt, SQ);
            } else {
                PktQueue.DequeuePktFromBuffer(SQ);
            }

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
