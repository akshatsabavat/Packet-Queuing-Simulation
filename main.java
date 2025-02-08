public class main {

    public static void main(String[] args) {
        BufferQueue buffer = new BufferQueue(10);
        BufferState SQ = new BufferState(0, 0);

        buffer.EnqueuePktToBuffer(new Packet(1), SQ);
        buffer.EnqueuePktToBuffer(new Packet(2), SQ);
        buffer.EnqueuePktToBuffer(new Packet(3), SQ);
        buffer.EnqueuePktToBuffer(new Packet(4), SQ);
        buffer.EnqueuePktToBuffer(new Packet(5), SQ);
        buffer.EnqueuePktToBuffer(new Packet(6), SQ);
        buffer.EnqueuePktToBuffer(new Packet(7), SQ);
        buffer.EnqueuePktToBuffer(new Packet(8), SQ);
        buffer.EnqueuePktToBuffer(new Packet(9), SQ);
        buffer.EnqueuePktToBuffer(new Packet(10), SQ);
        buffer.EnqueuePktToBuffer(new Packet(11), SQ);
        buffer.EnqueuePktToBuffer(new Packet(12), SQ);
        buffer.EnqueuePktToBuffer(new Packet(13), SQ);

        buffer.DequeuePktFromBuffer(SQ);
        buffer.DequeuePktFromBuffer(SQ);
        buffer.DequeuePktFromBuffer(SQ);
        buffer.DequeuePktFromBuffer(SQ);

        System.out.println();
    }
}