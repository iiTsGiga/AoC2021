public class DijkstraNode {
    public final int cost;
    public final DijkstraNode[] neighbours;
    public DijkstraNode predecessor;
    public long distance;

    public DijkstraNode(int cost, int neighbourCount) {
        this.cost = cost;
        neighbours = new DijkstraNode[neighbourCount];
        predecessor = null;
        distance = Integer.MAX_VALUE;
    }

    public void addNeighbour(DijkstraNode neighbour) {
        int i = 0;
        while (neighbours[i] != null) i++;
        neighbours[i] = neighbour;
    }
}
