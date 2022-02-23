public class Day06 extends AbstractDay {

    public Day06() {
        super(6, true);
    }

    @Override
    public void part1() {
        long[] fishes = createFishes();
        simulate(fishes, 80);
        System.out.println("Part 1: " + countFishes(fishes));
    }

    @Override
    public void part2() {
        long[] fishes = createFishes();
        simulate(fishes, 256);
        System.out.println("Part 2: " + countFishes(fishes));
    }

    @Override
    public void combined() {
        long[] fishes = createFishes();
        simulate(fishes, 80);
        System.out.println("Part 1: " + countFishes(fishes));
        simulate(fishes, 256-80);
        System.out.println("Part 2: " + countFishes(fishes));
    }

    /**
     * @return an array in which each element represents the number of fishes
     * with the specific index as the days left to reproduce
     */
    private long[] createFishes() {
        long[] fishes = new long[9];
        for (int n : intInput)
            fishes[n]++;
        return fishes;
    }

    private long countFishes(long[] fishes) {
        long sum = 0;
        for (long n : fishes)
            sum += n;
        return sum;
    }

    private void simulate(long[] fishes, int days) {
        for (int d = 0; d < days; d++) {
            long n0 = fishes[0];
            System.arraycopy(fishes, 1, fishes, 0, fishes.length - 1);
            fishes[8] = n0;
            fishes[6] += n0;
        }
    }
}
