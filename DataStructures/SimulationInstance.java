package DataStructures;

// This is one instance of a simulation that will be coupled up in an array
public class SimulationInstance {
    public int eventId;
    public int pktsInQueue;
    public int pktsDropped;

    // Constructor to intialize that Simulation Instance
    public SimulationInstance(int eventId, int pktsInQueue, int pktsDropped) {
        this.eventId = eventId;
        this.pktsInQueue = pktsInQueue;
        this.pktsDropped = pktsDropped;
    }

    // Getters for accessing attributes
    public int getEventId() {
        return eventId;
    }

    public int getPktsInQueue() {
        return pktsInQueue;
    }

    public int getPktsDropped() {
        return pktsDropped;
    }
}
