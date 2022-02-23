public class Day13 extends AbstractDay {

    private final int[][] dots;
    private final Direction[] foldDirections;
    private final int[] foldCoordinates;

    public Day13() {
        super(13, false);
        int blankIndex = 0;
        while (!input[blankIndex].isBlank()) blankIndex++;
        dots = new int[blankIndex][2];
        foldDirections = new Direction[input.length - blankIndex - 1];
        foldCoordinates = new int[foldDirections.length];
        for (int i = 0; i < dots.length; i++) {
            String[] xy = input[i].split(",");
            dots[i][0] = Integer.parseInt(xy[0]);
            dots[i][1] = Integer.parseInt(xy[1]);
        }
        for (int i = 0; i < foldDirections.length; i++) {
            String[] dirCoord = input[blankIndex + 1 + i].split(" ")[2].split("=");
            foldDirections[i] = dirCoord[0].charAt(0) == 'y' ? Direction.NORTH : Direction.WEST;
            foldCoordinates[i] = Integer.parseInt(dirCoord[1]);
        }
    }

    @Override
    public void part1() {
        boolean[][] paper = createPaper();
        paper = foldPaper(paper, 0);
        System.out.println("Part 1: " + countDots(paper));
    }

    @Override
    public void part2() {
        boolean[][] paper = createPaper();
        for (int i = 0; i < foldDirections.length; i++)
            paper = foldPaper(paper, i);
        System.out.println("Part 2:");
        printPaper(paper);
    }

    @Override
    public void combined() {
        boolean[][] paper = createPaper();
        paper = foldPaper(paper, 0);
        System.out.println("Part 1: " + countDots(paper));
        for (int i = 1; i < foldDirections.length; i++)
            paper = foldPaper(paper, i);
        System.out.println("Part 2:");
        printPaper(paper);
    }

    private void printPaper(boolean[][] paper) {
        for (boolean[] line : paper) {
            for (boolean dot : line)
                System.out.print(dot ? "\u2588".repeat(2) : " ".repeat(2));
            System.out.println();
        }
    }

    private int countDots(boolean[][] paper) {
        int sum = 0;
        for (boolean[] line : paper)
            for (boolean dot : line)
                if (dot) sum++;
        return sum;
    }

    private boolean[][] foldPaper(boolean[][] paper, int foldIndex) {
        boolean[][] newPaper;
        if (foldDirections[foldIndex] == Direction.WEST) {
            int fx = foldCoordinates[foldIndex];
            newPaper = new boolean[paper.length][fx];
            for (int y = 0; y < newPaper.length; y++)
                for (int x = 0; x < fx && fx+1+x < paper[0].length; x++)
                    newPaper[y][fx-1-x] = paper[y][fx-1-x] || paper[y][fx+1+x];
        } else {
            int fy = foldCoordinates[foldIndex];
            newPaper = new boolean[fy][paper[0].length];
            for (int x = 0; x < newPaper[0].length; x++)
                for (int y = 0; y < fy && fy+1+y < paper.length; y++)
                    newPaper[fy-1-y][x] = paper[fy-1-y][x] || paper[fy+1+y][x];
        }
        return newPaper;
    }

    private boolean[][] createPaper() {
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (int[] dot : dots) {
            maxX = Math.max(maxX, dot[0]);
            maxY = Math.max(maxY, dot[1]);
        }
        boolean[][] paper = new boolean[maxY+1][maxX+1];
        for (int[] dot : dots) paper[dot[1]][dot[0]] = true;
        return paper;
    }
}
