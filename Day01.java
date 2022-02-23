public class Day01 extends AbstractDay {

    public Day01() {
        super(1, true);
    }

    @Override
    public void part1() {
        int increasedCounter = 0;
        for (int i = 1; i < intInput.length; i++)
            if (intInput[i] > intInput[i - 1]) increasedCounter++;
        System.out.println("Part 1: " + increasedCounter);
    }

    @Override
    public void part2() {
        int increasedCounter = 0;
        for (int i = 3; i < intInput.length; i++)
            if (intInput[i] > intInput[i - 3]) increasedCounter++;
        System.out.println("Part 2: " + increasedCounter);
    }

    @Override
    public void combined() {
        int singleIncreasedCounter = 0, blockIncreasedCounter = 0;
        if (intInput[1] > intInput[0]) singleIncreasedCounter++;
        if (intInput[2] > intInput[1]) singleIncreasedCounter++;
        for (int i = 3; i < intInput.length; i++) {
            if (intInput[i] > intInput[i - 1]) singleIncreasedCounter++;
            if (intInput[i] > intInput[i - 3]) blockIncreasedCounter++;
        }
        System.out.println("Part 1: " + singleIncreasedCounter);
        System.out.println("Part 2: " + blockIncreasedCounter);
    }
}