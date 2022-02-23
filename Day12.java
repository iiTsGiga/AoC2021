import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Day12 extends AbstractDay {

    private final HashMap<String, List<String>> neighbours;

    public Day12() {
        super(12, false);
        neighbours = new HashMap<>();
        for (String line : input) {
            String[] nodes = line.split("-");
            if (!neighbours.containsKey(nodes[0]))
                neighbours.put(nodes[0], new LinkedList<>());
            if (!neighbours.containsKey(nodes[1]))
                neighbours.put(nodes[1], new LinkedList<>());
            neighbours.get(nodes[0]).add(nodes[1]);
            neighbours.get(nodes[1]).add(nodes[0]);
        }
    }

    @Override
    public void part1() {
        HashMap<String, Integer> visitCounters = new HashMap<>();
        for (String node : neighbours.keySet())
            visitCounters.put(node, 0);
        System.out.println("Part 1: " + getAmtPaths("start", visitCounters, 1));
    }

    @Override
    public void part2() {
        HashMap<String, Integer> visitCounters = new HashMap<>();
        for (String node : neighbours.keySet())
            visitCounters.put(node, 0);
        visitCounters.replace("start", 1);
        System.out.println("Part 2: " + getAmtPaths("start", visitCounters, 2));
    }

    @Override
    public void combined() {
        HashMap<String, Integer> visitCounters = new HashMap<>();
        for (String node : neighbours.keySet())
            visitCounters.put(node, 0);
        System.out.println("Part 1: " + getAmtPaths("start", visitCounters, 1));
        for (String node : neighbours.keySet())
            visitCounters.replace(node, 0);
        visitCounters.replace("start", 1);
        System.out.println("Part 2: " + getAmtPaths("start", visitCounters, 2));
    }

    private int getAmtPaths(String curNode, HashMap<String, Integer> visitCounters, int maxVisits) {
        if (curNode.equals("end")) return 1;
        int amtPaths = 0;
        int visits = visitCounters.get(curNode);
        maxVisits = curNode.charAt(0) >= 'a' && visits == 1 && !curNode.equals("start") ? 1 : maxVisits;
        visitCounters.replace(curNode, visits + 1);
        for (String neighbourNode : neighbours.get(curNode)) {
            if (neighbourNode.charAt(0) <= 'Z' || visitCounters.get(neighbourNode) < maxVisits)
                amtPaths += getAmtPaths(neighbourNode, visitCounters, maxVisits);
        }
        visitCounters.replace(curNode, visits);
        return amtPaths;
    }
}
