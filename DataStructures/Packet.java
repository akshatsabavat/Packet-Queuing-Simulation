package DataStructures;

// A class defining the data structure for a packet
public class Packet {
    int pktNumber;

    // consturtor method for creating a pkt we will be just using a number to
    // identify the pkt, No additional property like packet size was added, cause
    // then the state would vary
    public Packet(int pktNumber) {
        this.pktNumber = pktNumber;
    }
}
