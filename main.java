import java.text.MessageFormat;
import java.util.Random;

import DataStructures.BufferQueue;
import DataStructures.BufferState;
import DataStructures.Packet;

public class main {

    public static void main(String[] args) {
        // Parameters for the network Simulation Environment
        int lambda = 80; // arrival rate (pkts/sec)
        int mu = 100; // departure rate (pkts/sec)
        int n = 100; // buffer size
        int events = 1000; // event counts for simulation

        // for event generation
        Random random = new Random();

        // Simulation Probabilities
        double Pa = (double) lambda / (mu + lambda); // arrival event probability

        // initializing my PktQueue & BufferState (SQ)
        BufferQueue PktQueue = new BufferQueue(n);
        BufferState SQ = new BufferState(0, 0);

        for (int i = 0; i < events; i++) {
            double y = random.nextDouble(); // random number b/w 0.0 & 1.0
            if (y < Pa) {
                // this triggers an arrival event so hence, the pkt enters
                System.out.println(y + Pa);
                Packet pkt = new Packet(i);
                PktQueue.EnqueuePktToBuffer(pkt, SQ);
            } else {
                // this triggers a departure event
                PktQueue.DequeuePktFromBuffer(SQ);
            }
        }

        String resultString = MessageFormat.format(
                "Simulations Results\n Packets in Queue : {0} \n Packets dropped from Queue : {1}", SQ.pktsInQueue,
                SQ.pktsDropped);
        System.out.println(resultString);
    }
}