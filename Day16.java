import java.util.LinkedList;
import java.util.List;

public class Day16 extends AbstractDay {

    private final byte[] data;

    public Day16() {
        super(16, false);
        data = new byte[input[0].length() / 2];
        for (int i = 0; i < data.length; i++)
            data[i] = (byte) Integer.parseInt(input[0].substring(i * 2, i * 2 + 2), 16);
    }

    @Override
    public void part1() {
        List<Packet> packets = getPackets(data);
        int versionNumberSum = 0;
        for (Packet packet : packets)
            versionNumberSum += packet.version;
        System.out.println("Part 1: " + versionNumberSum);
    }

    @Override
    public void part2() {
        List<Packet> packets = getPackets(data);
        Packet mainPacket = buildHierarchy(packets, 0);
        System.out.println("Part 2: " + mainPacket.evaluateExpression());
    }

    @Override
    public void combined() {
        List<Packet> packets = getPackets(data);
        int versionNumberSum = 0;
        for (Packet packet : packets)
            versionNumberSum += packet.version;
        System.out.println("Part 1: " + versionNumberSum);
        Packet mainPacket = buildHierarchy(packets, 0);
        System.out.println("Part 2: " + mainPacket.evaluateExpression());
    }

    private Packet buildHierarchy(List<Packet> packets, int packetIndex) {
        Packet curPacket = packets.get(packetIndex);
        if (curPacket.typeID == 4) return curPacket;
        if (curPacket.lengthTypeID == 1) {
            for (int i = 0; i < curPacket.value; i++) {
                Packet subPacket = buildHierarchy(packets, packetIndex + 1 + curPacket.subPacketAmt);
                curPacket.subPacketBits += subPacket.subPacketBits + subPacket.bitSize;
                curPacket.subPacketAmt += 1 + subPacket.subPacketAmt;
                curPacket.subPackets.add(subPacket);
            }
        } else {
            while (curPacket.subPacketBits != curPacket.value) {
                Packet subPacket = buildHierarchy(packets, packetIndex + 1 + curPacket.subPacketAmt);
                curPacket.subPacketBits += subPacket.subPacketBits + subPacket.bitSize;
                curPacket.subPacketAmt += 1 + subPacket.subPacketAmt;
                curPacket.subPackets.add(subPacket);
            }
        }
        return curPacket;
    }

    private List<Packet> getPackets(byte[] data) {
        List<Packet> packets = new LinkedList<>();
        int bitIndex = 0;
        while (bitIndex / 8 < data.length - 1) {
            int bitIndexStart = bitIndex;
            int version = getValue(data, bitIndex, 3);
            bitIndex += 3;
            int typeID = getValue(data, bitIndex, 3);
            bitIndex += 3;
            long value = 0;
            int lengthTypeID = 0;
            if (typeID == 4) {
                int groupID;
                do {
                    groupID = getValue(data, bitIndex, 1);
                    bitIndex++;
                    value = value << 4 | getValue(data, bitIndex, 4);
                    bitIndex += 4;
                } while (groupID != 0);
            } else {
                lengthTypeID = getValue(data, bitIndex, 1);
                bitIndex++;
                if (lengthTypeID == 0) {
                    value = getValue(data, bitIndex, 15);
                    bitIndex += 15;
                } else {
                    value = getValue(data, bitIndex, 11);
                    bitIndex += 11;
                }
            }
            packets.add(new Packet(version, typeID, value, bitIndex - bitIndexStart, lengthTypeID));
        }
        return packets;
    }

    private int getValue(byte[] data, int bitIndex, int length) {
        int resData = 0;
        for (int i = 0; i < length; i++) {
            resData <<= 1;
            resData |= (data[(bitIndex + i) / 8] >>> (7 - ((bitIndex + i) % 8))) & 0x1;
        }
        return resData;
    }
}
