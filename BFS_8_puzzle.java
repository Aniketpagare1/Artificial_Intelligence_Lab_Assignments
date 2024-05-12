import java.util.*;

public class BFS_8_puzzle {

    // Possible moves: Up, Down, Left, Right
    static int[][] moves = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    // Check if the current state is equal to the goal state
    static boolean isGoal(int[][] state, int[][] goal) {
        return Arrays.deepEquals(state, goal);
    }

    // Find the position of the empty space (0) in the grid
    static int[] findEmptyPosition(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // Create a deep copy of a 2D array
    static int[][] copyArray(int[][] source) {
        int[][] copy = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            copy[i] = Arrays.copyOf(source[i], source[i].length);
        }
        return copy;
    }

    // Solve the 8-puzzle using BFS and return the optimal path
    public static List<String> solve(int[][] initial, int[][] goal) {
        int n = initial.length;

        // Queue to store states to be explored
        Queue<int[][]> queue = new LinkedList<>();
        // Map to store parent-child relationships
        Map<int[][], int[][]> parentMap = new HashMap<>();
        // Add initial state with no parent
        queue.add(initial);
        parentMap.put(initial, null);

        // BFS loop
        while (!queue.isEmpty()) {
            int[][] curr = queue.poll();

            // Check if the goal is reached
            if (isGoal(curr, goal)) {
                return reconstructPath(parentMap, curr);
            }

            // Find the position of the empty space
            int[] emptyPos = findEmptyPosition(curr);
            int emptyRow = emptyPos[0];
            int emptyCol = emptyPos[1];

            // Explore possible moves
            for (int[] move : moves) {
                int newRow = emptyRow + move[0];
                int newCol = emptyCol + move[1];

                // Check if the new position is within bounds
                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                    // Create a new board by swapping the empty space with an adjacent tile
                    int[][] newBoard = copyArray(curr);
                    newBoard[emptyRow][emptyCol] = newBoard[newRow][newCol];
                    newBoard[newRow][newCol] = 0;

                    // Check if the new state has not been visited
                    if (!parentMap.containsKey(newBoard)) {
                        // Update parent-child relationship and enqueue the new state
                        parentMap.put(newBoard, curr);
                        queue.add(newBoard);
                    }
                }
            }
        }
        // No solution found
        return null;
    }

    // Reconstruct the path from the goal state to the initial state
    static List<String> reconstructPath(Map<int[][], int[][]> parentMap, int[][] goal) {
        List<String> path = new ArrayList<>();
        int[][] cur = goal;

        // Backtrack through parent-child relationships
        while (parentMap.get(cur) != null) {
            int[][] parent = parentMap.get(cur);
            int[] emptyPos = findEmptyPosition(cur);
            int[] parentEmptyPos = findEmptyPosition(parent);

            // Determine the move applied to transition from the parent to the current state
            int rowDiff = parentEmptyPos[0] - emptyPos[0];
            int colDiff = parentEmptyPos[1] - emptyPos[1];

            // Add the corresponding move to the path
            if (rowDiff == 1) path.add("U");
            else if (rowDiff == -1) path.add("D");
            else if (colDiff == 1) path.add("L");
            else if (colDiff == -1) path.add("R");

            cur = parent;
        }

        // Reverse the path to get the correct order
        Collections.reverse(path);
        return path;
    }

    // Main method
    public static void main(String[] args) {
        // Initial state
        int[][] initial = {
            {1, 2, 3},
            {4, 0, 5},
            {6, 7, 8}
        };

        // Goal state
        int[][] goal = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        // Solve the puzzle and get the optimal path
        List<String> path = solve(initial, goal);

        // Print the result
        if (path != null) {
            System.out.println("Optimal path: " + path);
        } else {
            System.out.println("No solution found.");
        }
    }
}
