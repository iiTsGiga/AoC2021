public class Day09 extends AbstractDay {

    private final int[][] heightMap;

    public Day09() {
        super(9, false);
        heightMap = new int[input.length][input[0].length()];
        for (int y = 0; y < heightMap.length; y++)
            for (int x = 0; x < heightMap.length; x++)
                heightMap[y][x] = input[y].charAt(x) - '0';
    }

    @Override
    public void part1() {
        int sumRiskLevel = 0;
        for (int y = 0; y < heightMap.length; y++)
            for (int x = 0; x < heightMap.length; x++)
                if (isLowPoint(x, y)) sumRiskLevel += heightMap[y][x] + 1;
        System.out.println("Part 1: " + sumRiskLevel);
    }

    @Override
    public void part2() {
        int[] maxBasinSizes = new int[3];
        boolean[][] visited = new boolean[heightMap.length][heightMap[0].length];
        for (int y = 0; y < heightMap.length; y++)
            for (int x = 0; x < heightMap[y].length; x++) {
                insertMaxBasinSize(maxBasinSizes, getBasinSize(visited, x, y));
            }
        System.out.println("Part 2: " + (maxBasinSizes[0] * maxBasinSizes[1] * maxBasinSizes[2]));
    }

    @Override
    public void combined() {
        int sumRiskLevel = 0;
        int[] maxBasinSizes = new int[3];
        boolean[][] visited = new boolean[heightMap.length][heightMap[0].length];
        for (int y = 0; y < heightMap.length; y++)
            for (int x = 0; x < heightMap.length; x++) {
                if (isLowPoint(x, y)) sumRiskLevel += heightMap[y][x] + 1;
                insertMaxBasinSize(maxBasinSizes, getBasinSize(visited, x, y));
            }
        System.out.println("Part 1: " + sumRiskLevel);
        System.out.println("Part 2: " + (maxBasinSizes[0] * maxBasinSizes[1] * maxBasinSizes[2]));
    }

    private void insertMaxBasinSize(int[] maxBasinSizes, int basinSize) {
        if (basinSize != 0) {
            if (basinSize > maxBasinSizes[0]) {
                maxBasinSizes[2] = maxBasinSizes[1];
                maxBasinSizes[1] = maxBasinSizes[0];
                maxBasinSizes[0] = basinSize;
            } else if (basinSize > maxBasinSizes[1]) {
                maxBasinSizes[2] = maxBasinSizes[1];
                maxBasinSizes[1] = basinSize;
            } else if (basinSize > maxBasinSizes[2]) {
                maxBasinSizes[2] = basinSize;
            }
        }
    }

    private int getBasinSize(boolean[][] visited, int x, int y) {
        if (heightMap[y][x] == 9 || visited[y][x]) return 0;
        visited[y][x] = true;
        int basinSize = 1;
        if (x + 1 < heightMap[0].length) basinSize += getBasinSize(visited, x + 1, y);
        if (x - 1 >= 0) basinSize += getBasinSize(visited, x - 1, y);
        if (y + 1 < heightMap.length) basinSize += getBasinSize(visited, x, y + 1);
        if (y - 1 >= 0) basinSize += getBasinSize(visited, x, y - 1);
        return basinSize;
    }

    private boolean isLowPoint(int x, int y) {
        return (y - 1 < 0 || heightMap[y][x] < heightMap[y - 1][x]) &&
                (y + 1 >= heightMap.length || heightMap[y][x] < heightMap[y + 1][x]) &&
                (x - 1 < 0 || heightMap[y][x] < heightMap[y][x - 1]) &&
                (x + 1 >= heightMap[y].length || heightMap[y][x] < heightMap[y][x + 1]);
    }
}
