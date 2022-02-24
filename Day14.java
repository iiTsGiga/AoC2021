import java.util.Arrays;
import java.util.HashMap;

public class Day14 extends AbstractDay {

    private final long[] polymerPairCounters;
    private final int[][] insertionPairs;
    private final int[] pairsFirstElements;
    private final int ELEMENT_COUNT;
    private final int lastElementId;

    public Day14() {
        super(14, false);
        HashMap<String, String[]> insertionRules = new HashMap<>();
        HashMap<String, Integer> pairIds = new HashMap<>();
        HashMap<Character, Integer> elementIds = new HashMap<>();
        for (int i = 2; i < input.length; i++) {
            String[] pairInsertion = input[i].split(" -> ");
            String[] insertionStrPairs = new String[2];
            insertionStrPairs[0] = pairInsertion[0].charAt(0) + "" + pairInsertion[1].charAt(0);
            insertionStrPairs[1] = pairInsertion[1].charAt(0) + "" + pairInsertion[0].charAt(1);
            insertionRules.put(pairInsertion[0], insertionStrPairs);
            pairIds.put(pairInsertion[0], pairIds.size());
            if (!elementIds.containsKey(pairInsertion[1].charAt(0)))
                elementIds.put(pairInsertion[1].charAt(0), elementIds.size());
        }
        ELEMENT_COUNT = elementIds.size();
        lastElementId = elementIds.get(input[0].charAt(input[0].length() - 1));
        insertionPairs = new int[insertionRules.size()][2];
        pairsFirstElements = new int[insertionRules.size()];
        for (String pair : pairIds.keySet()) {
            int pairId = pairIds.get(pair);
            insertionPairs[pairId][0] = pairIds.get(insertionRules.get(pair)[0]);
            insertionPairs[pairId][1] = pairIds.get(insertionRules.get(pair)[1]);
            pairsFirstElements[pairId] = elementIds.get(pair.charAt(0));
        }
        polymerPairCounters = new long[pairIds.size()];
        for (int i = 1; i < input[0].length(); i++) {
            String pair = input[0].charAt(i - 1) + "" + input[0].charAt(i);
            polymerPairCounters[pairIds.get(pair)]++;
        }
    }

    @Override
    public void part1() {
        long[] polymerPairCounters = Arrays.copyOf(this.polymerPairCounters, this.polymerPairCounters.length);
        for (int i = 0; i < 10; i++)
            polymerPairCounters = doStep(polymerPairCounters);
        System.out.println("Part 1: " + getSubMaxMin(getElementCounts(polymerPairCounters)));
    }

    @Override
    public void part2() {
        long[] polymerPairCounters = Arrays.copyOf(this.polymerPairCounters, this.polymerPairCounters.length);
        for (int i = 0; i < 40; i++)
            polymerPairCounters = doStep(polymerPairCounters);
        System.out.println("Part 2: " + getSubMaxMin(getElementCounts(polymerPairCounters)));
    }

    @Override
    public void combined() {
        long[] polymerPairCounters = Arrays.copyOf(this.polymerPairCounters, this.polymerPairCounters.length);
        for (int i = 0; i < 10; i++)
            polymerPairCounters = doStep(polymerPairCounters);
        System.out.println("Part 1: " + getSubMaxMin(getElementCounts(polymerPairCounters)));
        for (int i = 10; i < 40; i++)
            polymerPairCounters = doStep(polymerPairCounters);
        System.out.println("Part 2: " + getSubMaxMin(getElementCounts(polymerPairCounters)));
    }

    private long[] doStep(long[] polymerPairCounters) {
        long[] newPolymerPairCounters = new long[polymerPairCounters.length];
        for (int pairId = 0; pairId < polymerPairCounters.length; pairId++) {
            if (polymerPairCounters[pairId] > 0) {
                newPolymerPairCounters[insertionPairs[pairId][0]] += polymerPairCounters[pairId];
                newPolymerPairCounters[insertionPairs[pairId][1]] += polymerPairCounters[pairId];
            }
        }
        return newPolymerPairCounters;
    }

    private long[] getElementCounts(long[] polymerPairCounters) {
        long[] elementCounts = new long[ELEMENT_COUNT];
        for (int pairId = 0; pairId < polymerPairCounters.length; pairId++)
            if (pairId > 0) elementCounts[pairsFirstElements[pairId]] += polymerPairCounters[pairId];
        elementCounts[lastElementId]++;
        return elementCounts;
    }

    private long getSubMaxMin(long[] arr) {
        long min = arr[0], max = arr[0];
        for (long n : arr) {
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        return max - min;
    }
}
