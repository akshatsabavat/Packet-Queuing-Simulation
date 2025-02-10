package Environments;

import java.util.Random;

import DataStructures.BufferQueue;
import DataStructures.BufferState;
import DataStructures.Packet;

public class NetworkSimulation {
    private int lambda;
    private int mu;
    private int events;

    // Constructor to define a simulation instance
    public NetworkSimulation(int lambda, int mu, int events) {
        this.events = events;
        this.lambda = lambda;
        this.mu = mu;
    }

    public void RunSimulation(BufferQueue PktQueue, BufferState SQ) {
        // for event generation
        Random random = new Random();

        // Simulation Probabilities
        double Pa = (double) lambda / (mu + lambda); // arrival event probability

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
    }

}
