import java.util.HashMap;
import java.util.LinkedList;

public class Day04 extends AbstractDay {

    private final int[] rounds;
    private final int[][][] boards;

    public Day04() {
        super(4, false);
        String[] arrStrNums = input[0].split(",");
        rounds = new int[arrStrNums.length];
        for (int i = 0; i < arrStrNums.length; i++)
            rounds[i] = Integer.parseInt(arrStrNums[i]);
        int boardCounter = 0;
        for (int i = 1; i < input.length; i++)
            if (input[i].isBlank()) boardCounter++;
        this.boards = new int[boardCounter][5][5];
        for (int i = 2, boardIndex = 0, rowIndex = 0; i < input.length; i++, rowIndex++) {
            if (input[i].isBlank()) {
                boardIndex++;
                rowIndex = -1;
            } else {
                for (int j = 0; j < input[i].length(); j += 3)
                    boards[boardIndex][rowIndex][j/3] = Integer.parseInt(input[i].substring(j, j+2).trim());
            }
        }
    }

    @Override
    public void part1() {
        int[][][] processedBoards = processBoards();
        var roundWinnerTable = getRoundWinnerTable(processedBoards);
        int minWinRound = Integer.MAX_VALUE;
        for (int winRound : roundWinnerTable.keySet())
            minWinRound = Math.min(winRound, minWinRound);
        System.out.println("Part 1: " + (getScore(minWinRound, roundWinnerTable.get(minWinRound).get(0), processedBoards)) * rounds[minWinRound]);
    }

    @Override
    public void part2() {
        int[][][] processedBoards = processBoards();
        var roundWinnerTable = getRoundWinnerTable(processedBoards);
        int maxWinRound = Integer.MIN_VALUE;
        for (int winRound : roundWinnerTable.keySet())
            maxWinRound = Math.max(winRound, maxWinRound);
        System.out.println("Part 2: " + (getScore(maxWinRound, roundWinnerTable.get(maxWinRound).get(0), processedBoards)) * rounds[maxWinRound]);
    }

    @Override
    public void combined() {
        int[][][] processedBoards = processBoards();
        var roundWinnerTable = getRoundWinnerTable(processedBoards);
        int minWinRound = Integer.MAX_VALUE, maxWinRound = Integer.MIN_VALUE;
        for (int winRound : roundWinnerTable.keySet()) {
            minWinRound = Math.min(minWinRound, winRound);
            maxWinRound = Math.max(maxWinRound, winRound);
        }
        System.out.println("Part 1: " + (getScore(minWinRound, roundWinnerTable.get(minWinRound).get(0), processedBoards)) * rounds[minWinRound]);
        System.out.println("Part 2: " + (getScore(maxWinRound, roundWinnerTable.get(maxWinRound).get(0), processedBoards)) * rounds[maxWinRound]);
    }

    private int getScore(int winRound, int winBoardIndex, int[][][] processedBoards) {
        int sum = 0;
        for (int i = 0; i < boards[winBoardIndex].length; i++)
            for (int j = 0; j < boards[winBoardIndex][i].length; j++)
                if (processedBoards[winBoardIndex][i][j] > winRound) sum += boards[winBoardIndex][i][j];
        return sum;
    }

    /**
     * @return a table with the round-index in which the boards win as the key
     * and a list of the board-indices which win in the specific round as the value
     */
    private HashMap<Integer, LinkedList<Integer>> getRoundWinnerTable(int[][][] processedBoards) {
        HashMap<Integer, LinkedList<Integer>> roundWinnerTable = new HashMap<>();
        for (int boardIndex = 0; boardIndex < boards.length; boardIndex++) {
            int winRoundBoard = Integer.MAX_VALUE;
            for (int k = 0; k < boards[boardIndex].length; k++) {
                int winRoundHor = 0, winRoundVer = 0;
                for (int i = 0; i < boards[boardIndex].length; i++) {
                    winRoundHor = Math.max(winRoundHor, processedBoards[boardIndex][k][i]);
                    winRoundVer = Math.max(winRoundVer, processedBoards[boardIndex][i][k]);
                }
                winRoundBoard = Math.min(winRoundBoard, Math.min(winRoundHor, winRoundVer));
            }
            if (!roundWinnerTable.containsKey(winRoundBoard))
                roundWinnerTable.put(winRoundBoard, new LinkedList<>());
            roundWinnerTable.get(winRoundBoard).add(boardIndex);
        }
        return roundWinnerTable;
    }

    /**
     * @return the boards with the values replaced with the round-index in which the values get drawn
     */
    private int[][][] processBoards() {
        HashMap<Integer, Integer> roundIndices = new HashMap<>();
        for (int i = 0; i < rounds.length; i++)
            roundIndices.put(rounds[i], i);
        int[][][] processedBoards = new int[boards.length][boards[0].length][boards[0][0].length];
        for (int boardIndex = 0; boardIndex < boards.length; boardIndex++) {
            for (int i = 0; i < boards[boardIndex].length; i++) {
                for (int j = 0; j < boards[boardIndex][i].length; j++) {
                    processedBoards[boardIndex][i][j] = roundIndices.get(boards[boardIndex][i][j]);
                }
            }
        }
        return processedBoards;
    }
}
