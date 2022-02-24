import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Day12 extends AbstractDay {

    private final int[][] neighbours;
    private final boolean[] isLowerCase;
    private final int startNodeId, endNodeId;

    public Day12() {
        super(12, false);
        HashMap<String, List<String>> neighboursMap = new HashMap<>();
        for (String line : input) {
            String[] nodes = line.split("-");
            if (!neighboursMap.containsKey(nodes[0]))
                neighboursMap.put(nodes[0], new LinkedList<>());
            if (!neighboursMap.containsKey(nodes[1]))
                neighboursMap.put(nodes[1], new LinkedList<>());
            neighboursMap.get(nodes[0]).add(nodes[1]);
            neighboursMap.get(nodes[1]).add(nodes[0]);
        }
        HashMap<String, Integer> nodeIds = new HashMap<>();
        for (String node : neighboursMap.keySet())
            nodeIds.put(node, nodeIds.size());
        neighbours = new int[nodeIds.size()][];
        isLowerCase = new boolean[nodeIds.size()];
        for (String node : nodeIds.keySet()) {
            int id = nodeIds.get(node);
            neighbours[id] = new int[neighboursMap.get(node).size()];
            isLowerCase[id] = node.charAt(0) >= 'a';
            for (int i = 0; i  < neighbours[id].length; i++) {
                neighbours[id][i] = nodeIds.get(neighboursMap.get(node).get(i));
            }
        }
        startNodeId = nodeIds.get("start");
        endNodeId = nodeIds.get("end");
    }

    @Override
    public void part1() {
        int[] visitCounters = new int[neighbours.length];
        System.out.println("Part 1: " + getAmtPaths(startNodeId, visitCounters, 1));
    }

    @Override
    public void part2() {
        int[] visitCounters = new int[neighbours.length];
        visitCounters[startNodeId] = 1;
        System.out.println("Part 2: " + getAmtPaths(startNodeId, visitCounters, 2));
    }

    @Override
    public void combined() {
        int[] visitCounters = new int[neighbours.length];
        System.out.println("Part 1: " + getAmtPaths(startNodeId, visitCounters, 1));
        visitCounters[startNodeId] = 1;
        System.out.println("Part 2: " + getAmtPaths(startNodeId, visitCounters, 2));
    }

    private int getAmtPaths(int curNodeId, int[] visitCounters, int maxVisits) {
        if (curNodeId == endNodeId) return 1;
        int amtPaths = 0;
        maxVisits = isLowerCase[curNodeId] && visitCounters[curNodeId] == 1 && curNodeId != startNodeId ? 1 : maxVisits;
        visitCounters[curNodeId]++;
        for (int neighbourNodeId : neighbours[curNodeId]) {
            if (!isLowerCase[neighbourNodeId] || visitCounters[neighbourNodeId] < maxVisits)
                amtPaths += getAmtPaths(neighbourNodeId, visitCounters, maxVisits);
        }
       visitCounters[curNodeId]--;
        return amtPaths;
    }
}
