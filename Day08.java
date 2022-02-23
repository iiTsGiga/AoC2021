import java.util.HashMap;

public class Day08 extends AbstractDay {

    private static final int[] FIRST_PRIMES = { 2, 3, 5, 7, 11, 13, 17 };

    private final String[][] rndSegments;
    private final String[][] outSegments;

    public Day08() {
        super(8, false);
        rndSegments = new String[input.length][];
        outSegments = new String[input.length][];
        for (int i = 0; i < input.length; i++) {
            String[] rndSegmentsOutNumbers = input[i].split(" \\| ");
            rndSegments[i] = rndSegmentsOutNumbers[0].split(" ");
            outSegments[i] = rndSegmentsOutNumbers[1].split(" ");
        }
    }

    @Override
    public void part1() {
        int amtUniqueOutSegments = 0;
        for (String[] outSegmentsLine : outSegments)
            for (String outSegment : outSegmentsLine)
                if ((outSegment.length() >= 2 && outSegment.length() <= 4) || outSegment.length() == 7)
                    amtUniqueOutSegments++;
        System.out.println("Part 1: " + amtUniqueOutSegments);
    }

    @Override
    public void part2() {
        int outputSum = 0;
        for (int i = 0; i < outSegments.length; i++)
            outputSum += getOutSegmentsNum(i);
        System.out.println("Part 2: " + outputSum);
    }

    @Override
    public void combined() {
        int amtUniqueOutSegments = 0;
        int outputSum = 0;
        for (int i = 0; i < outSegments.length; i++) {
            for (String outSegment : outSegments[i])
            if ((outSegment.length() >= 2 && outSegment.length() <= 4) || outSegment.length() == 7)
                amtUniqueOutSegments++;
            outputSum += getOutSegmentsNum(i);
        }
        System.out.println("Part 1: " + amtUniqueOutSegments);
        System.out.println("Part 2: " + outputSum);
    }

    private int getOutSegmentsNum(int lineIndex) {
        int[][] segmentsAsPrimes = strSegmentsToPrimes(lineIndex);
        int[] segments = findFirstSegments(segmentsAsPrimes);
        int[] nums = findFirstNums(segmentsAsPrimes);
        buildNumbers(nums, segments);
        return buildOutputNumber(nums, lineIndex);
    }

    private int buildOutputNumber(int[] nums, int lineIndex) {
        HashMap<Integer, Integer> primeNumTranslation = new HashMap<>();
        for (int i = 0; i < nums.length; i++)
            primeNumTranslation.put(nums[i], i);
        int res = 0;
        for (int i = 0; i < outSegments[lineIndex].length; i++) {
            String digit = outSegments[lineIndex][i];
            res *= 10;
            res += primeNumTranslation.get(translateToPrimeProduct(digit));
        }
        return res;
    }

    private void buildNumbers(int[] firstNums, int[] firstSegments) {
        firstNums[2] = firstNums[8] / (firstSegments[1] * firstSegments[5]);
        firstNums[9] = firstNums[8] / firstSegments[4];
        firstNums[3] = firstNums[9] / firstSegments[1];
        firstSegments[2] = firstNums[1] / firstSegments[5];
        firstNums[6] = firstNums[8] / firstSegments[2];
        firstNums[5] = firstNums[6] / firstSegments[4];
        firstSegments[3] = firstNums[4] / (firstSegments[1] * firstSegments[2] * firstSegments[5]);
        firstNums[0] = firstNums[8] / firstSegments[3];
    }

    private int[] findFirstNums(int[][] segmentsAsPrimes) {
        int[] nums = new int[10];
        for (int[] segmentPrimes : segmentsAsPrimes)
            switch (segmentPrimes.length) {
                case 2 -> nums[1] = arrProduct(segmentPrimes); // only number 1 consists of 2 segments
                case 3 -> nums[7] = arrProduct(segmentPrimes); // only number 7 consists of 3 segments
                case 4 -> nums[4] = arrProduct(segmentPrimes); // only number 4 consists of 4 segments
                case 7 -> nums[8] = arrProduct(segmentPrimes); // only number 8 consists of 7 segments
            }
        return nums;
    }

    private int[] findFirstSegments(int[][] segmentsAsPrimes) {
        int[] segments = new int[8];
        for (int prime : FIRST_PRIMES) {
            int segmentCount = 0;
            for (int[] segmentPrimes : segmentsAsPrimes)
                for (int segment : segmentPrimes)
                    if (segment == prime) segmentCount++;
            switch (segmentCount) {
                case 6 -> segments[1] = prime;  // segment 1 is used in 6 numbers
                case 4 -> segments[4] = prime;  // segment 4 is used in 4 numbers
                case 9 -> segments[5] = prime;  // segment 5 is used in 9 numbers
            }
        }
        return segments;
    }

    private int[][] strSegmentsToPrimes(int lineIndex) {
        int[][] segmentsPrimes = new int[rndSegments[lineIndex].length][];
        for (int i = 0; i < rndSegments[i].length; i++) {
            segmentsPrimes[i] = new int[rndSegments[lineIndex][i].length()];
            for (int j = 0; j < rndSegments[lineIndex][i].length(); j++)
                segmentsPrimes[i][j] = FIRST_PRIMES[rndSegments[lineIndex][i].charAt(j) - 'a'];
        }
        return segmentsPrimes;
    }

    private int translateToPrimeProduct(String digit) {
        int res = 1;
        for (char c : digit.toCharArray())
            res *= FIRST_PRIMES[c - 'a'];
        return res;
    }

    private int arrProduct(int[] arr) {
        int res = 1;
        for (int n : arr)
            res *= n;
        return res;
    }
}
