package Environments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DataStructures.SimulationInstance;

// Class to facilitate all my simulation runs for static and dynamic cases
public class SimulationResults {

    // This is an instace that defines the details of the entire simulation
    // environment and it contains all the instance of simulation that we will use
    // for plotting
    private class SimulationEnvironment {
        private int lambda;
        private int mu;
        private int n;
        private ArrayList<SimulationInstance> eventData;

        // Constructor to initialize an instance of a simulation environment
        private SimulationEnvironment(int lambda, int mu, int n, ArrayList<SimulationInstance> simulationRuns) {
            this.lambda = lambda;
            this.mu = mu;
            this.n = n;
            this.eventData = simulationRuns;
        }

        // some getter functions for retriving the attributes
        private int getEnvironmentLambda() {
            return lambda;
        }

        private int getEnvironmentMu() {
            return mu;
        }

        private int getEnvironmentN() {
            return n;
        }

        private ArrayList<SimulationInstance> getEnvironmentSimulationRuns() {
            return eventData;
        }
    }

    // Now after finally declaring the above to private classes we can declare the
    // main data structure that will host all the run results, and the environment
    // details

    // we will now use a map data structure to map an evenID we create, to the run
    // time results of that environment
    // <Key {simulation ID}, Environment>
    // here the key/simulation id can be something like -- lambda30_mu50_n50
    private Map<String, SimulationEnvironment> simulationResults;

    // constructor to intialize our SimulationResults Instance
    public SimulationResults() {
        this.simulationResults = new HashMap<>();
    }

    // adding an environment to the Simulation Results, once it has been completed
    public void addEnvironment(String simulationId, NetworkSimulation simulation, int n) {
        // defining the environment
        SimulationEnvironment simulationEnvironment = new SimulationEnvironment(simulation.getSimulationLambda(),
                simulation.getSimulationMu(), n, simulation.getSimulationRuns());
        simulationResults.put(simulationId, simulationEnvironment);
    }

    // if we wanna access and get the run time results of an environment, we use the
    // generated simulationId
    public SimulationEnvironment getEnvironment(String simulationId) {
        return simulationResults.get(simulationId);
    }

    // if we wanna access and see the entire map data structure, of all our
    // environments and how they performed
    public Map<String, SimulationEnvironment> getSimulationResults() {
        return simulationResults;
    }

    // Method the view all the results we got once all 27 runs have been completed
    public void printAllResults() {
        System.out.println("--- All Simulation Results ---");
        for (Map.Entry<String, SimulationEnvironment> entry : simulationResults.entrySet()) {
            String simulationId = entry.getKey();
            SimulationEnvironment environment = entry.getValue();

            System.out.println("\n--- " + simulationId + " ---");
            System.out
                    .println("Lambda: " + environment.getEnvironmentLambda() + ", Mu: " + environment.getEnvironmentMu()
                            + ", N: " + environment.getEnvironmentN());

            for (SimulationInstance instance : environment.getEnvironmentSimulationRuns()) {
                System.out.println(
                        "Event " + instance.getEventId() + ": Packets in queue = " + instance.getPktsInQueue() +
                                ", Packets dropped = " + instance.getPktsDropped());
            }
        }
    }
}
