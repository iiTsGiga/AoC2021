public class Main {
    public static void main(String[] args) {
        AbstractDay[] days = {
                new Day01(),
                new Day02(),
                new Day03()
        };

        for (AbstractDay day : days) {
            System.out.println("******** " + day.getClass().getName() + " ********");
            day.combined();
            System.out.println("***********************" + System.lineSeparator());
        }
    }
}
