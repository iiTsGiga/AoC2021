public class Main {
    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        AbstractDay[] days = {
                new Day01(),
                new Day02(),
                new Day03(),
                new Day04(),
                new Day05()
        };

        for (AbstractDay day : days) {
            System.out.println("******** " + day.getClass().getName() + " ********");
            day.combined();
            System.out.println("***********************" + System.lineSeparator());
        }
        t = System.currentTimeMillis() - t;
        System.out.println("All Took " + t + "ms to run");
    }
}
