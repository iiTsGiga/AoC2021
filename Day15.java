import java.util.ArrayList;
import java.util.List;

public class Day15 extends AbstractDay {

    private final int[][] riskMap;

    public Day15() {
        super(15, false);
        riskMap = new int[input.length][input[0].length()];
        for (int y = 0; y < riskMap.length; y++)
            for (int x = 0; x < riskMap.length; x++)
                riskMap[y][x] = input[y].charAt(x) - '0';
    }

    @Override
    public void part1() {
        List<DijkstraNode> nodes = createNodes(riskMap);
        System.out.println("Part 1: " + new DijkstraPathFinder().shortestPathLength(nodes.get(0), nodes.get(nodes.size() - 1), nodes));
    }

    @Override
    public void part2() {
        int[][] expandedRiskMap = expandRiskMap(riskMap, 5);
        List<DijkstraNode> nodes = createNodes(expandedRiskMap);
        System.out.println("Part 2: " + new DijkstraPathFinder().shortestPathLength(nodes.get(0), nodes.get(nodes.size() - 1), nodes));
    }

    @Override
    public void combined() {
        List<DijkstraNode> nodes = createNodes(riskMap);
        System.out.println("Part 1: " + new DijkstraPathFinder().shortestPathLength(nodes.get(0), nodes.get(nodes.size() - 1), nodes));
        int[][] expandedRiskMap = expandRiskMap(riskMap, 5);
        nodes = createNodes(expandedRiskMap);
        System.out.println("Part 2: " + new DijkstraPathFinder().shortestPathLength(nodes.get(0), nodes.get(nodes.size() - 1), nodes));
    }

    private int[][] expandRiskMap(int[][] riskMap, final int AMOUNT) {
        int[][] expandedRiskMap = new int[riskMap.length * AMOUNT][riskMap[0].length * AMOUNT];
        for (int my = 0; my < AMOUNT; my++) {
            for (int mx = 0; mx < AMOUNT; mx++) {
                for (int y = 0; y < riskMap.length; y++) {
                    for (int x = 0; x < riskMap.length; x++) {
                        expandedRiskMap[my*riskMap.length+y][mx*riskMap[0].length+x] = (riskMap[y][x] + mx + my - 1) % 9 + 1;
                    }
                }
            }
        }
        return expandedRiskMap;
    }

    private List<DijkstraNode> createNodes(int[][] riskMap) {
        DijkstraNode[][] nodeMap = new DijkstraNode[riskMap.length][riskMap.length];
        for (int y = 0; y < riskMap.length; y++)
            for (int x = 0; x < riskMap[y].length; x++) {
                int neighbourCount = (y == 0 || y == riskMap.length - 1) && (x == 0 || x == riskMap[0].length - 1) ?
                                     2 : y == 0 || y == riskMap.length - 1 || x == 0 || x == riskMap[0].length - 1 ? 3 : 4;
                nodeMap[y][x] = new DijkstraNode(y == 0 && x == 0 ? 0 : riskMap[y][x], neighbourCount);
            }
        List<DijkstraNode> nodes = new ArrayList<>(nodeMap.length * nodeMap[0].length);
        for (int y = 0; y < nodeMap.length; y++)
            for (int x = 0; x < nodeMap[y].length; x++) {
                if (x > 0) nodeMap[y][x].addNeighbour(nodeMap[y][x - 1]);
                if (x < nodeMap[0].length - 1) nodeMap[y][x].addNeighbour(nodeMap[y][x + 1]);
                if (y > 0) nodeMap[y][x].addNeighbour(nodeMap[y - 1][x]);
                if (y < nodeMap.length - 1) nodeMap[y][x].addNeighbour(nodeMap[y + 1][x]);
                nodes.add(nodeMap[y][x]);
            }
        return nodes;
    }
}
