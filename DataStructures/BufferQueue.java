package DataStructures;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.Queue;

// A class defining the buffer queue and all it's related operations
public class BufferQueue {
    private Queue<Packet> pktBufferQueue;
    private int pktBufferQueueSize;

    // constructor for initializing the buffer queue
    public BufferQueue(int pktBufferQueueSize) {
        this.pktBufferQueue = new LinkedList<>();
        this.pktBufferQueueSize = pktBufferQueueSize;
    }

    // method to push pkts to Buffer
    public void EnqueuePktToBuffer(Packet pkt, BufferState SQ) {
        // first we verify if the Buffer currently has space within it to accomadate the
        // queue, if not we increment the pktsDropped number in the state
        if (pktBufferQueue.size() < pktBufferQueueSize) {
            pktBufferQueue.offer(pkt);
            // incrementing the number of packets in queue
            SQ.updateState(true, true);
        } else {
            // incrementing the number of packets dropped in state
            SQ.updateState(true, false);
        }
    }

    // method to push pkts out of the buffer
    public void DequeuePktFromBuffer(BufferState SQ) {
        // we get the pkt that sits at the front of the queue waiting to be dequeued
        Packet removedPkt = pktBufferQueue.poll();
        if (removedPkt != null) {
            // update the BufferStateAccordingly
            SQ.updateState(false, true);
        }
    }
}