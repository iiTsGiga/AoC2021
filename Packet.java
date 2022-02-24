import java.util.LinkedList;
import java.util.List;

public class Packet {
    int version, typeID, bitSize, lengthTypeID;
    int subPacketBits, subPacketAmt;
    long value;
    List<Packet> subPackets;

    public Packet(int version, int typeID, long value, int bitSize, int lengthTypeID) {
        this.version = version;
        this.typeID = typeID;
        this.value = value;
        this.bitSize = bitSize;
        this.lengthTypeID = lengthTypeID;
        subPackets = new LinkedList<>();
        subPacketBits = subPacketAmt = 0;
    }

    public long evaluateExpression() {
        if (typeID == 4) return value;
        long[] values = new long[subPackets.size()];
        for (int i = 0; i < subPackets.size(); i++)
            values[i] = subPackets.get(i).evaluateExpression();
        return switch (typeID) {
            case 0 -> sum(values);
            case 1 -> product(values);
            case 2 -> minimum(values);
            case 3 -> maximum(values);
            case 5 -> values[0] > values[1] ? 1 : 0;
            case 6 -> values[0] < values[1] ? 1 : 0;
            case 7 -> values[0] == values[1] ? 1 : 0;
            default -> throw new RuntimeException("Invalid typeID");
        };
    }

    private long maximum(long[] values) {
        long max = values[0];
        for (int i = 1; i < values.length; i++)
            max = Math.max(values[i], max);
        return max;
    }

    private long minimum(long[] values) {
        long min = values[0];
        for (int i = 1; i < values.length; i++)
            min = Math.min(values[i], min);
        return min;
    }

    private long product(long[] values) {
        long res = values[0];
        for (int i = 1; i < values.length; i++)
            res *= values[i];
        return res;
    }

    private long sum(long[] values) {
        long res = values[0];
        for (int i = 1; i < values.length; i++)
            res += values[i];
        return res;
    }
}
