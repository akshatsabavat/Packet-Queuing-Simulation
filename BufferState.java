// A class defining the state of the buffer that indicates how many packets we have dropped and how many packets that currently reside in the buffer
// Denoted by S(Q,D) | Q --> number of pkts in queue, D --> number of pkts dropped from queue
public class BufferState {
    int pktsInQueue;
    int pktsDropped;

    // constructor for initializing the bufferState
    public BufferState(int pktsInQueue, int pktsDropped) {
        this.pktsDropped = pktsDropped;
        this.pktsInQueue = pktsInQueue;
    }
}
