package Environments;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

            List<SimulationInstance> eventData = environment.getEnvironmentSimulationRuns();
            int numEventsToPrint = 10; // Number of events to print from beginning and end

            // Print first few events
            System.out.print("First " + numEventsToPrint + " events: ");
            for (int i = 0; i < Math.min(numEventsToPrint, eventData.size()); i++) {
                SimulationInstance instance = eventData.get(i);
                System.out.print("S(" + instance.getPktsInQueue() + "," + instance.getPktsDropped() + ")");
                if (i < Math.min(numEventsToPrint, eventData.size()) - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();

            // Print last few events (if available and different from the first few)
            if (eventData.size() > numEventsToPrint * 2) {
                System.out.print("Last " + numEventsToPrint + " events: ");
                for (int i = eventData.size() - numEventsToPrint; i < eventData.size(); i++) {
                    SimulationInstance instance = eventData.get(i);
                    System.out.print("S(" + instance.getPktsInQueue() + "," + instance.getPktsDropped() + ")");
                    if (i < eventData.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            } else if (eventData.size() > numEventsToPrint) { // Handle case where there are more events, but not enough
                                                              // for separate last events
                System.out.print("Last " + (eventData.size() - numEventsToPrint) + " events: ");
                for (int i = numEventsToPrint; i < eventData.size(); i++) {
                    SimulationInstance instance = eventData.get(i);
                    System.out.print("S(" + instance.getPktsInQueue() + "," + instance.getPktsDropped() + ")");
                    if (i < eventData.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }

            // Print some summary statistics
            System.out.println("Total Events: " + eventData.size());
            int totalDropped = 0;
            int maxQueueLength = 0;

            for (SimulationInstance instance : eventData) {
                totalDropped = Math.max(totalDropped, instance.getPktsDropped()); // Keep track of the maximum dropped
                                                                                  // count
                maxQueueLength = Math.max(maxQueueLength, instance.getPktsInQueue());
            }

            System.out.println("Total Packets Dropped: " + totalDropped);
            System.out.println("Maximum Queue Length: " + maxQueueLength);
        }
    }

    public void dumpResultsToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("simulation_results = {\n");

            for (Map.Entry<String, SimulationEnvironment> entry : simulationResults.entrySet()) {
                String simulationId = entry.getKey();
                SimulationEnvironment environment = entry.getValue();

                writer.write("    \"" + simulationId + "\": {\n");
                writer.write("        \"event_ids\": [");

                List<SimulationInstance> eventData = environment.getEnvironmentSimulationRuns();

                for (int i = 0; i < eventData.size(); i++) {
                    writer.write(String.valueOf(eventData.get(i).getEventId()));
                    if (i < eventData.size() - 1) {
                        writer.write(", ");
                    }
                }
                writer.write("],\n");

                writer.write("        \"packets_in_queue\": [");
                for (int i = 0; i < eventData.size(); i++) {
                    writer.write(String.valueOf(eventData.get(i).getPktsInQueue()));
                    if (i < eventData.size() - 1) {
                        writer.write(", ");
                    }
                }
                writer.write("],\n");

                writer.write("        \"packets_dropped\": [");
                for (int i = 0; i < eventData.size(); i++) {
                    writer.write(String.valueOf(eventData.get(i).getPktsDropped()));
                    if (i < eventData.size() - 1) {
                        writer.write(", ");
                    }
                }
                writer.write("]\n");

                writer.write("    },\n"); // End of this simulation's data
            }

            writer.write("}\n"); // End of the entire simulation_results dictionary
        }
    }
}
