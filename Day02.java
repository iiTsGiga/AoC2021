public class Day02 extends AbstractDay {

    private final Direction[] directions;
    private final int[] distances;

    public Day02() {
        super(2, false);
        directions = new Direction[input.length];
        distances = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            String[] move = input[i].split(" ");
            directions[i] = switch (move[0]) {
                case "forward" -> Direction.FORWARD;
                case "up" -> Direction.NORTH;
                case "down" -> Direction.SOUTH;
                default -> throw new RuntimeException("Invalid direction supplied");
            };
            distances[i] = Integer.parseInt(move[1]);
        }
    }

    @Override
    public void part1() {
        int position = 0, depth = 0;
        for (int i = 0; i < directions.length; i++) {
            switch (directions[i]) {
                case NORTH -> depth -= distances[i];
                case SOUTH -> depth += distances[i];
                case FORWARD -> position += distances[i];
            }
        }
        System.out.println("Part 1: " + (position * depth));
    }

    @Override
    public void part2() {
        int position = 0, depth = 0, aim = 0;
        for (int i = 0; i < directions.length; i++) {
            switch (directions[i]) {
                case NORTH -> aim -= distances[i];
                case SOUTH -> aim += distances[i];
                case FORWARD -> {
                    position += distances[i];
                    depth += aim * distances[i];
                }
            }
        }
        System.out.println("Part 2: " + ((long) position * depth));
    }

    @Override
    public void combined() {
        // depth1 and aim are increased / decreased identically, so they can be combined into one variable
        int position = 0, depth1aim = 0, depth2 = 0;
        for (int i = 0; i < directions.length; i++) {
            switch (directions[i]) {
                case NORTH -> depth1aim -= distances[i];
                case SOUTH -> depth1aim += distances[i];
                case FORWARD -> {
                    position += distances[i];
                    depth2 += depth1aim * distances[i];
                }
            }
        }
        System.out.println("Part 1: " + (position * depth1aim));
        System.out.println("Part 2: " + ((long) position * depth2));
    }
}
