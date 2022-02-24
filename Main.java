public class Main {
    public static void main(String[] args) {
        long tStart = System.currentTimeMillis();
        AbstractDay[] days = {
                new Day01(),
                new Day02(),
                new Day03(),
                new Day04(),
                new Day05(),
                new Day06(),
                new Day07(),
                new Day08(),
                new Day09(),
                new Day10(),
                new Day11(),
                new Day12(),
                new Day13()
        };
        long tReadAndProcess = System.currentTimeMillis() - tStart;

        long tCalc = 0;
        for (AbstractDay day : days) {
            System.out.println("******** " + day.getClass().getName() + " ********");
            long tTmp = System.currentTimeMillis();
            // day.part1();
            // day.part2();
            day.combined();
            tTmp = System.currentTimeMillis() - tTmp;
            tCalc += tTmp;
            System.out.println("Calculation took " + tTmp + "ms");
            System.out.println("***********************" + System.lineSeparator());
        }
        System.out.println("Reading and processing input took " + tReadAndProcess + "ms");
        System.out.println("Calculations took " + tCalc + "ms");
        System.out.println("All took " + (tReadAndProcess + tCalc) + "ms");
    }
}
