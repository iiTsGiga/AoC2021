public class Day05 extends AbstractDay {

    private final int[][][] lines;

    public Day05() {
        super(5);
        lines = new int[input.length][2][2]; // indices: 1. line, 2. start / end, 3. x / y
        for (int i = 0; i < input.length; i++) {
            String[] startEnd = input[i].split(" -> ");
            String[] start = startEnd[0].split(",");
            String[] end = startEnd[1].split(",");
            lines[i][0][0] = Integer.parseInt(start[0]);
            lines[i][0][1] = Integer.parseInt(start[1]);
            lines[i][1][0] = Integer.parseInt(end[0]);
            lines[i][1][1] = Integer.parseInt(end[1]);
            boolean isDiagonal = lines[i][0][0] != lines[i][1][0] && lines[i][0][1] != lines[i][1][1];
            if (lines[i][1][1] < lines[i][0][1] || (!isDiagonal && lines[i][1][0] < lines[i][0][0])) {
                int[] tmp = lines[i][0];
                lines[i][0] = lines[i][1];
                lines[i][1] = tmp;
            }
        }
    }

    @Override
    public void part1() {
        int[][][] counterMap = getCounterMaps();
        int sum = 0;
        for (int[] row : counterMap[0])
            for (int count : row)
                if (count > 1) sum++;
        System.out.println("Part 1: " + sum);
    }

    @Override
    public void part2() {
        int[][][] counterMap = getCounterMaps();
        int sum = 0;
        for (int y = 0; y < counterMap[0].length; y++)
            for (int x = 0; x < counterMap[0][y].length; x++)
                if (counterMap[0][y][x] + counterMap[1][y][x] > 1) sum++;
        System.out.println("Part 2: " + sum);
    }

    @Override
    public void combined() {
        int[][][] counterMap = getCounterMaps();
        int sumWithoutDiagonals = 0, sumWithDiagonals = 0;
        for (int y = 0; y < counterMap[0].length; y++)
            for (int x = 0; x < counterMap[0][y].length; x++) {
                if (counterMap[0][y][x] > 1) sumWithoutDiagonals++;
                if (counterMap[0][y][x] + counterMap[1][y][x] > 1) sumWithDiagonals++;
            }
        System.out.println("Part 1: " + sumWithoutDiagonals);
        System.out.println("Part 2: " + sumWithDiagonals);
    }

    private int[][][] getCounterMaps() {
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
        for (int[][] line : lines)
            for (int[] point : line) {
                minX = Math.min(point[0], minX);
                minY = Math.min(point[1], minY);
                maxX = Math.max(point[0], maxX);
                maxY = Math.max(point[1], maxY);
            }
        int[][][] counterMap = new int[2][maxY - minY + 1][maxX - minX + 1];
        for (int[][] line : lines) {
            if (line[0][0] == line[1][0]) {  // vertical line
                for (int y = line[0][1]; y <= line[1][1]; y++) {
                    counterMap[0][y - minY][line[0][0] - minX]++;
                }
            } else if (line[0][1] == line[1][1]) {  // horizontal line
                for (int x = line[0][0]; x <= line[1][0]; x++) {
                    counterMap[0][line[0][1] - minY][x - minX]++;
                }
            } else {
                if (line[0][0] < line[1][0]) {  // diagonal top left to bottom right
                    for (int y = line[0][1], x = line[0][0]; y <= line[1][1]; x++, y++) {
                        counterMap[1][y - minY][x - minY]++;
                    }
                } else {  // diagonal top right to bottom left
                    for (int y = line[0][1], x = line[0][0]; y <= line[1][1]; x--, y++) {
                        counterMap[1][y - minY][x - minY]++;
                    }
                }
            }
        }
        return counterMap;
    }
}
