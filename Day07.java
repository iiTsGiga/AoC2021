import java.util.Arrays;

public class Day07 extends AbstractDay {

    public Day07() {
        super(7, true);
    }

    @Override
    public void part1() {
        Arrays.sort(intInput);
        System.out.println("Part 1: " + bruteForceNearest(intInput[0], intInput[intInput.length - 1], true));
    }

    @Override
    public void part2() {
        Arrays.sort(intInput);
        System.out.println("Part 2: " + bruteForceNearest(intInput[0], intInput[intInput.length - 1], false));
    }

    @Override
    public void combined() {
        Arrays.sort(intInput);
        System.out.println("Part 1: " + bruteForceNearest(intInput[0], intInput[intInput.length - 1], true));
        System.out.println("Part 2: " + bruteForceNearest(intInput[0], intInput[intInput.length - 1], false));
    }

    private int bruteForceNearest(int min, int max, boolean stepCostEq1) {
        int minFuelCost = Integer.MAX_VALUE;
        for (int destPos = min; destPos <= max; destPos++) {
            int fuelCost = 0;
            for (int startPos : intInput) {
                int steps = Math.abs(destPos - startPos);
                fuelCost += stepCostEq1 ? steps : steps * (steps + 1) / 2;
            }
            if (fuelCost < minFuelCost)
                minFuelCost = fuelCost;
        }
        return minFuelCost;
    }
}
