import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public abstract class AbstractDay {
    private static final String INPUT_PATH = "inputs/";
    protected final String[] input;

    public AbstractDay(int day) {
        input = readFile(day);
    }

    public abstract void part1();
    public abstract void part2();
    public abstract void combined();

    protected int[] convertInputToInt() {
        int[] intInput = new int[input.length];
        for (int i = 0; i < intInput.length; i++)
            intInput[i] = Integer.parseInt(input[i]);
        return intInput;
    }

    private static String[] readFile(int day) {
        final String FILE_PATH = INPUT_PATH + "day" + (day < 10 ? "0" : "") + day + ".dat";
        try (Stream<String> lines = Files.lines(Paths.get(FILE_PATH))) {
            return lines.toList().toArray(new String[0]);
        } catch (IOException e) {
            System.err.println("Error while reading " + FILE_PATH);
            return new String[0];
        }
    }
}
