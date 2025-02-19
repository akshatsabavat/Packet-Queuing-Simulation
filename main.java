import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import DataStructures.BufferQueue;
import DataStructures.BufferState;
import DataStructures.VariableLambdaScheduleEntries;
import Environments.NetworkSimulation;
import Environments.SimulationResults;

public class main {

    public static void main(String[] args) {

        // Parameter Arrays (from Table 1)
        int[] lambdaValues = { 30, 80, 120 }; // arrival rates (pkts/sec)
        int[] muValues = { 50, 100, 120 }; // departure rates (pkts/sec)
        int[] nValues = { 50, 100, 150 }; // buffer sizes
        int events = 1000000; // 1,000,000 event counts for simulation

        // now to run all 27 simulation combinations above is going to be an expensive
        // process
        // O(n^3), and that will cause the whole simulation to slow down

        // So now I am going use the Executor interface to scheduele runnable tasks
        // so now running these tasks parallel will be of great help, and reduce the
        // time complexity

        // First we instantiate our simulation results class
        SimulationResults simulation_results = new SimulationResults();

        // we then introduce parrallelization to distribute our tasks in running these
        // simulation results among differnt available cores
        int cores = Runtime.getRuntime().availableProcessors();

        // we then define the executor that will parallely distribute the tasks among
        // these cores
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        for (int lambda : lambdaValues) {
            for (int mu : muValues) {
                for (int n : nValues) {
                    final int currentRunningLambda = lambda;
                    final int currentRunningMu = mu;
                    final int currentRunningN = n;

                    // now we submit a task to the thread pool to distribute it amongst the cores
                    executor.submit(() -> {
                        // task definition
                        BufferState SQ = new BufferState(0, 0);
                        BufferQueue PktQueue = new BufferQueue(currentRunningN);

                        // running simulations

                        // define a simulation with the current running values in the thread
                        NetworkSimulation currentRunningSimulation = new NetworkSimulation(currentRunningLambda,
                                currentRunningMu, events);
                        // now we run the simmulation
                        String simulationRunMessage = MessageFormat.format(
                                "Running Simulation for Environment : lambda = {0}, mu = {1}, n = {2}",
                                currentRunningLambda, currentRunningMu, currentRunningN);
                        System.err.println(simulationRunMessage);
                        currentRunningSimulation.RunSimulation(PktQueue, SQ);

                        // once it's done running we can add it to our simulation Results
                        String simulationId = "lambda" + currentRunningLambda + "_mu" + currentRunningMu + "_n"
                                + currentRunningN;
                        simulation_results.addEnvironment(simulationId, currentRunningSimulation, currentRunningN);
                        String simulationEndMessage = MessageFormat.format("Finished running simulation : {0}",
                                simulationId);
                        System.out.println(simulationEndMessage);
                    });
                }
            }
        }
        executor.shutdown(); // Shutdown the executor
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // Wait for all tasks to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All simulations completed.");
        // we print out the the entire object to see for ourselves
        simulation_results.printAllResults();

        try {
            simulation_results.dumpSimulationResults("simulation_results");
            System.out.println("Simulation results dumped to simulation_results folder");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Simulating the variable environment
        int n = 100;
        int mu = 120;
        int totalEvents = 1000000;

        // we now define our variable lambda schedule entries list, and add enties to it
        VariableLambdaScheduleEntries scheduleEntries = new VariableLambdaScheduleEntries();
        scheduleEntries.AddToScheduleEntries(0.10, 70);
        scheduleEntries.AddToScheduleEntries(0.70, 200);
        scheduleEntries.AddToScheduleEntries(0.80, 130);
        scheduleEntries.AddToScheduleEntries(0.90, 120);
        scheduleEntries.AddToScheduleEntries(1, 70);

        // we redefine a new buffer instance and a state instace to record the above
        // observations
        BufferState SQ = new BufferState(0, 0);
        BufferQueue PktQueue = new BufferQueue(n);

        // we now create a new simulation to run this variable enviroment
        // I pass 70 to lambda, as it starts and ends with 70 even in the table
        NetworkSimulation variableSimulation = new NetworkSimulation(70, mu, totalEvents);

        // Now run the variable simulation
        System.out.println("Running Simulation instance for variable input rates");
        variableSimulation.RunVariableInputRateSimulation(PktQueue, SQ, scheduleEntries);

        // now we difine a new results instance to dump the data and store our run
        // values
        SimulationResults variableSimulationResults = new SimulationResults();
        variableSimulationResults.addEnvironment("variable_lambda_input_rate",
                variableSimulation,
                n);
        try {
            variableSimulationResults.dumpSimulationResults("var_sim_results");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}