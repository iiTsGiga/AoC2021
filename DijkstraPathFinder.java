import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraPathFinder {
    public int shortestPathLength(DijkstraNode startNode, DijkstraNode destNode, List<DijkstraNode> nodes) {
        PriorityQueue<DijkstraNode> Q = new PriorityQueue<>(Comparator.comparingLong(o -> o.distance));
        startNode.distance = 0;
        Q.add(startNode);
        while (!Q.isEmpty()) {
            DijkstraNode u = Q.poll();
            if (u == destNode) return (int) destNode.distance;
            for (DijkstraNode v : u.neighbours) {
                long alternativeDistance = u.distance + v.cost;
                if (alternativeDistance < v.distance) {
                    v.distance = alternativeDistance;
                    v.predecessor = u;
                    Q.offer(v);
                }
            }
        }
        return -1;
    }
}
