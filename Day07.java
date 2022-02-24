import java.util.Arrays;

public class Day07 extends AbstractDay {

    public Day07() {
        super(7, true);
    }

    @Override
    public void part1() {
        Arrays.sort(intInput);
        System.out.println("Part 1: " + bruteForceNearest(intInput[0], intInput[intInput.length - 1])[0]);
    }

    @Override
    public void part2() {
        Arrays.sort(intInput);
        System.out.println("Part 2: " + bruteForceNearest(intInput[0], intInput[intInput.length - 1])[1]);
    }

    @Override
    public void combined() {
        Arrays.sort(intInput);
        int[] minFuelCosts = bruteForceNearest(intInput[0], intInput[intInput.length - 1]);
        System.out.println("Part 1: " + minFuelCosts[0]);
        System.out.println("Part 2: " + minFuelCosts[1]);
    }

    private int[] bruteForceNearest(int min, int max) {
        int minFuelCost1 = Integer.MAX_VALUE, minFuelCostN = Integer.MAX_VALUE;
        for (int destPos = min; destPos <= max; destPos++) {
            int fuelCost1 = 0, fuelCostN = 0;
            for (int startPos : intInput) {
                int steps = Math.abs(destPos - startPos);
                fuelCost1 += steps;
                fuelCostN += steps * (steps + 1) / 2;
            }
            minFuelCost1 = Math.min(fuelCost1, minFuelCost1);
            minFuelCostN = Math.min(fuelCostN, minFuelCostN);
        }
        return new int[] { minFuelCost1, minFuelCostN };
    }
}
