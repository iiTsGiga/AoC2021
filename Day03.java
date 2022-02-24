import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Day03 extends AbstractDay {

    private final int BITS_PER_VALUE;

    public Day03() {
        super(3, false);
        BITS_PER_VALUE = input[0].length();
    }

    @Override
    public void part1() {
        int[][] bitCount = countAllBits();
        StringBuilder gammaRateBin = new StringBuilder();
        for (int[] bitPositionCount : bitCount)
            gammaRateBin.append(bitPositionCount[1] > bitPositionCount[0] ? '1' : '0');
        int gammaRate = Integer.parseInt(gammaRateBin.toString(), 2);
        int epsilonRate = ~gammaRate & ((1 << BITS_PER_VALUE) - 1);
        System.out.println("Part 1: " + (gammaRate * epsilonRate));
    }

    @Override
    public void part2() {
        LinkedList<String> oxGenRatingLst = new LinkedList<>();
        Collections.addAll(oxGenRatingLst, input);
        LinkedList<String> co2ScrRatingLst = new LinkedList<>(oxGenRatingLst);
        filterRatings(oxGenRatingLst, true);
        filterRatings(co2ScrRatingLst, false);
        int oxGenRating = Integer.parseInt(oxGenRatingLst.get(0), 2);
        int co2ScrRating = Integer.parseInt(co2ScrRatingLst.get(0), 2);
        System.out.println("Part 2: " + (oxGenRating * co2ScrRating));
    }

    @Override
    public void combined() {
        // part 1 and part 2 do completely different things, so no need to combine them further
        part1();
        part2();
    }

    private int[][] countAllBits() {
        int[][] bitCount = new int[BITS_PER_VALUE][2];
        List<String> inputLst = Arrays.asList(input);
        for (int bitIndex = 0; bitIndex < BITS_PER_VALUE; bitIndex++)
            bitCount[bitIndex] = countBits(inputLst, bitIndex);
        return bitCount;
    }

    private int[] countBits(List<String> input, int position) {
        int[] bitCount = new int[2];
        for (String strBinary : input) {
            switch (strBinary.charAt(position)) {
                case '0' -> bitCount[0]++;
                case '1' -> bitCount[1]++;
                default -> throw new RuntimeException("Incorrect input format");
            }
        }
        return bitCount;
    }

    private void filterRatings(List<String> ratings, boolean isOxygen) {
        for (int bitIndex = 0; bitIndex < BITS_PER_VALUE && ratings.size() > 1; bitIndex++) {
            int[] bitCount = countBits(ratings, bitIndex);
            for (int j = 0; j < ratings.size(); j++)
                if (ratings.get(j).charAt(bitIndex) != ((isOxygen && bitCount[1] >= bitCount[0]) || (!isOxygen && bitCount[1] < bitCount[0]) ? '1' : '0'))
                    ratings.remove(j--);
        }
    }
}
