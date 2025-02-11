package DataStructures;

import java.util.ArrayList;

// We will also need to have a data structure that holds the variable
// input rates mentioned in table 2 along with their corresponding percentage completion marks

// so the data structure will hold a list of percentage values of event completion, and
// the associated lambda values

public class VariableLambdaScheduleEntries {
    private class VariableLambdaScheduleEntry {
        public double percentage;
        public int lambda;

        // Constructor to define an instance of this variable schedule entry
        public VariableLambdaScheduleEntry(double percentage, int lambda) {
            this.percentage = percentage;
            this.lambda = lambda;
        }

    }

    public ArrayList<VariableLambdaScheduleEntry> lambdaScheduleEntries;

    // Construtor to define an instance for these variable schedule entries
    public VariableLambdaScheduleEntries() {
        this.lambdaScheduleEntries = new ArrayList<>();
    }

    // method to add a schedule entry to the data structure
    public void AddToScheduleEntries(double percentage, int lambda) {
        VariableLambdaScheduleEntry scheduleEntry = new VariableLambdaScheduleEntry(percentage, lambda);
        lambdaScheduleEntries.add(scheduleEntry);
    }

    // Method to get the lambda value based on the current event completion %
    public int getLambda(int currentEventCount, int totalEvents) {
        double percentageCompleted = (double) currentEventCount / totalEvents;

        // we then check from our entries, which lambda value to return
        for (VariableLambdaScheduleEntry lambdaEntry : lambdaScheduleEntries) {
            if (percentageCompleted <= lambdaEntry.percentage) {
                return lambdaEntry.lambda;
            }
        }

        // never reaches this case but even if it does we can default back to the
        // original lambda, which according to this is 70 at the start and end
        return lambdaScheduleEntries.get(0).lambda;
    }
}
