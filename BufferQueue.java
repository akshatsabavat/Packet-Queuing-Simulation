import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.Queue;

// A class defining the data structure for a packet
class Packet {
    int pktNumber;

    // consturtor method for creating a pkt we will be just using a number to
    // identify the pkt, No additional property like packet size was added, cause
    // then the state would vary
    public Packet(int pktNumber) {
        this.pktNumber = pktNumber;
    }
}

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
    public void PushPktToBuffer(Packet pkt, BufferState SQ) {
        // first we verify if the Buffer currently has space within it to accomadate the
        // queue, if not we increment the pktsDropped number in the state
        if (pktBufferQueue.size() < pktBufferQueueSize) {
            pktBufferQueue.add(pkt);
            SQ.pktsInQueue += 1;
            String outputMsg = MessageFormat.format("packet : {0} is now in queue", pkt.pktNumber);
            System.out.println(outputMsg);
        } else {
            SQ.pktsDropped--;
            String outputMsg = MessageFormat.format("packet : {0} has been dropped, queue full {1}", pkt.pktNumber,
                    SQ.pktsInQueue);
            System.out.println(outputMsg);
        }
    }
}