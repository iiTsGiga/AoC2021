import java.util.Arrays;

public class Day11 extends AbstractDay {

    private final int[][] origEnergyLevels;

    public Day11() {
        super(11, false);
        origEnergyLevels = new int[input.length][input[0].length()];
        for (int y = 0; y < origEnergyLevels.length; y++)
            for (int x = 0; x < origEnergyLevels[y].length; x++)
                origEnergyLevels[y][x] = input[y].charAt(x) - '0';
    }

    @Override
    public void part1() {
        int flashCounter = 0;
        int[][] energyLevels = getCopyOfEnergyLevels();
        for (int r = 0; r < 100; r++) {
            for (int y = 0; y < energyLevels.length; y++)
                for (int x = 0; x < energyLevels[y].length; x++)
                    energyLevels[y][x]++;
            flashCounter += getFlashes(energyLevels);
        }
        System.out.println("Part 1: " + flashCounter);
    }

    @Override
    public void part2() {
        int[][] energyLevels = getCopyOfEnergyLevels();
        int round = 0;
        do {
            round++;
            for (int y = 0; y < energyLevels.length; y++)
                for (int x = 0; x < energyLevels[y].length; x++)
                    energyLevels[y][x]++;
        } while (getFlashes(energyLevels) != energyLevels.length * energyLevels[0].length);
        System.out.println("Part 2: " + round);
    }

    @Override
    public void combined() {
        int[][] energyLevels = getCopyOfEnergyLevels();
        int round = 0, flashCounterSum = 0, flashCounter;
        do {
            round++;
            for (int y = 0; y < energyLevels.length; y++)
                for (int x = 0; x < energyLevels[y].length; x++)
                    energyLevels[y][x]++;
            flashCounter = getFlashes(energyLevels);
            if (round <= 100) flashCounterSum += flashCounter;
        } while (flashCounter != energyLevels.length * energyLevels[0].length);
        System.out.println("Part 1: " + flashCounterSum);
        System.out.println("Part 2: " + round);
    }

    private int[][] getCopyOfEnergyLevels() {
        int[][] energyLevels = new int[origEnergyLevels.length][];
        for (int i = 0; i < energyLevels.length; i++)
            energyLevels[i] = Arrays.copyOf(origEnergyLevels[i], origEnergyLevels[i].length);
        return energyLevels;
    }

    private int getFlashes(int[][] energyLevels) {
        int flashCounter = 0;
        for (int y = 0; y < energyLevels.length; y++)
            for (int x = 0; x < energyLevels.length; x++)
                if (energyLevels[y][x] >= 10) {
                    energyLevels[y][x] = 0;
                    flash(energyLevels, x, y);
                    flashCounter++;
                }
        return flashCounter > 0 ? flashCounter + getFlashes(energyLevels) : 0;
    }

    private void flash(int[][] energyLevels, int x, int y) {
        for (int ny = y - 1; ny <= y + 1; ny++)
            for (int nx = x - 1; nx <= x + 1; nx++)
                if ((nx != x || ny != y) && ny >= 0 && nx >= 0 &&
                        ny < energyLevels.length && nx < energyLevels[ny].length)
                    energyLevels[ny][nx] += (energyLevels[ny][nx] == 0 ? 0 : 1);
    }
}
