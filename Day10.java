import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Day10 extends AbstractDay {

    public Day10() {
        super(10, false);
    }

    @Override
    public void part1() {
        int corruptedScore = 0;
        for (String line : input)
            corruptedScore += getScores(line)[0];
        System.out.println("Part 1: " + corruptedScore);
    }

    @Override
    public void part2() {
        ArrayList<Long> incompleteScores = new ArrayList<>(input.length);
        for (String line : input) {
            long[] scores = getScores(line);
            if (scores[0] == 0) incompleteScores.add(scores[1]);
        }
        Collections.sort(incompleteScores);
        System.out.println("Part 2: " + (incompleteScores.get(incompleteScores.size() / 2)));
    }

    @Override
    public void combined() {
        int corruptedScore = 0;
        ArrayList<Long> incompleteScores = new ArrayList<>(input.length);
        for (String line : input) {
            long[] scores = getScores(line);
            if (scores[0] == 0) incompleteScores.add(scores[1]);
            else corruptedScore += getScores(line)[0];
        }
        Collections.sort(incompleteScores);
        System.out.println("Part 1: " + corruptedScore);
        System.out.println("Part 2: " + (incompleteScores.get(incompleteScores.size() / 2)));
    }

    /**
     * @return an array with the corrupted score as the first element
     * and the incomplete score as the second element.
     * The incomplete score is 0 when the line is corrupted.
     */
    public long[] getScores(String line) {
        Stack<Character> chunks = new Stack<>();
        for (char c : line.toCharArray()) {
            if (c == '(' || c == '{' || c == '[' || c == '<')
                chunks.push(c);
            else if (c != getClosing(chunks.pop())) {
                return switch (c) {
                    case ')' -> new long[] { 3, 0 };
                    case ']' -> new long[] { 57, 0 };
                    case '}' -> new long[] { 1197, 0 };
                    case '>' -> new long[] { 25137, 0 };
                    default -> throw new RuntimeException("Invalid input");
                };
            }
        }
        return new long[] { 0, getIncompleteScore(chunks) };
    }

    private long getIncompleteScore(Stack<Character> remainingChunks) {
        long incompleteScore = 0;
        while (!remainingChunks.isEmpty()) {
            incompleteScore = incompleteScore * 5 + switch (remainingChunks.pop()) {
                case '(' -> 1;
                case '[' -> 2;
                case '{' -> 3;
                case '<' -> 4;
                default -> throw new RuntimeException("Invalid input");
            };
        }
        return incompleteScore;
    }

    private char getClosing(char opening) {
        return opening == '(' ? ')' : (char) (opening + 2);
    }
}
