import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        ArrayList<int[]> lstDots = new ArrayList<>(dots.length);
        for (int[] dot : dots) lstDots.add(Arrays.copyOf(dot, 2));
        foldDots(lstDots, 0);
        System.out.println("Part 1: " + countDots(createPaper(lstDots)));
    }

    @Override
    public void part2() {
        ArrayList<int[]> lstDots = new ArrayList<>(dots.length);
        for (int[] dot : dots) lstDots.add(Arrays.copyOf(dot, 2));
        for (int i = 0; i < foldDirections.length; i++)
            foldDots(lstDots, i);
        System.out.println("Part 2:");
        printPaper(createPaper(lstDots));
    }

    @Override
    public void combined() {
        ArrayList<int[]> lstDots = new ArrayList<>(dots.length);
        for (int[] dot : dots) lstDots.add(Arrays.copyOf(dot, 2));
        foldDots(lstDots, 0);
        System.out.println("Part 1: " + countDots(createPaper(lstDots)));
        for (int i = 1; i < foldDirections.length; i++)
            foldDots(lstDots, i);
        System.out.println("Part 2:");
        printPaper(createPaper(lstDots));
    }

    private boolean[][] createPaper(List<int[]> dots) {
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (int[] dot : dots) {
            maxX = Math.max(maxX, dot[0]);
            maxY = Math.max(maxY, dot[1]);
        }
        boolean[][] paper = new boolean[maxY + 1][maxX + 1];
        for (int[] dot : dots) paper[dot[1]][dot[0]] = true;
        return paper;
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

    private void foldDots(List<int[]> dots, int foldId) {
        int coordinate = foldCoordinates[foldId];
        int whichCoordinate = foldDirections[foldId] == Direction.WEST ? 0 : 1;
        for (int i = 0; i < dots.size(); i++) {
            if (dots.get(i)[whichCoordinate] > coordinate) {
                dots.get(i)[whichCoordinate] = 2 * coordinate - dots.get(i)[whichCoordinate];
                if (dots.get(i)[whichCoordinate] < 0) dots.remove(i--);
            }
        }
    }
}
