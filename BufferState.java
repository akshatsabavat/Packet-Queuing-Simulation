// A class defining the state of the buffer that indicates how many packets we have dropped and how many packets that currently reside in the buffer
// Denoted by S(Q,D) | Q --> number of pkts in queue, D --> number of pkts dropped from queue
public class BufferState {
    public int pktsInQueue;
    public int pktsDropped;

    // constructor for initializing the bufferState
    public BufferState(int pktsInQueue, int pktsDropped) {
        this.pktsDropped = pktsDropped;
        this.pktsInQueue = pktsInQueue;
    }

    // method to update the state, following scenarios of updatation and
    // decrementation for both pktsDropped and pktsInQueue

    // No need for an else for decrementing pktsDropped, cause there is no
    // retransmission procedure mentioned in the given simulation guideline
    public void updateState(boolean isIncrement, boolean isPktsInQueue) {
        if (isIncrement && isPktsInQueue) {
            this.pktsInQueue++;
        } else if (!isIncrement && isPktsInQueue) {
            // Decrement pktsInQueue
            this.pktsInQueue--;
        } else if (isIncrement && !isPktsInQueue) {
            // Increment pktsDropped
            this.pktsDropped++;
        }
    }
}
