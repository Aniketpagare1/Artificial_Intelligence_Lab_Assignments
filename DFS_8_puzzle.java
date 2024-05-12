import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class DFS{

    static int[][] moves = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };

    static boolean isGoal(int[][] initial, int[][] goal){
        return Arrays.deepEquals(initial, goal);
    }

    static int[] findEmptyPosition(int[][] grid){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if(grid[i][j] == 0){
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }
    
    static int[][] copyArray(int[][] grid){
        int[][] matrix = new int[grid.length][];

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = Arrays.copyOf(grid[i] ,grid[i].length);
        }
        return matrix;
    }

    static List<String> solve(int[][] initial, int[][] goal){
        int n = initial.length;
        Stack<int[][]> stack = new Stack<>();
        Map<int[][], int[][]> parentMap = new HashMap<>();

        stack.add(initial);
        parentMap.put(initial, null);

        while (!stack.isEmpty()) {
            int[][] curr = stack.pop();
            

            if(isGoal(curr, goal)){
                return Path(parentMap,curr);
            }

            int[] emptyPos = findEmptyPosition(curr);
            int emptyRow = emptyPos[0];
            int emptyCol = emptyPos[1];

            for (int[] move : moves) {
                int newRow = emptyRow + move[0];
                int newCol = emptyCol + move[1];

                if(newRow >= 0 && newRow < n && newCol >= 0 && newCol < n){
                    int[][] next = copyArray(curr);

                    next[emptyRow][emptyCol] = curr[newRow][newCol];
                    next[newRow][newCol] = 0;

                    if(!parentMap.containsKey(next)){
                        parentMap.put(next, curr);
                        stack.add(next);
                    }
                }
            }
        }
        return null;
    }

    static List<String> Path(Map<int[][], int[][]> parentMap, int[][] goal)
    {

        List<String> road = new ArrayList<>();
        int[][] cur = goal;

        while (parentMap.get(cur) != null) 
        {
            int[][] parent = parentMap.get(cur);
            int[] emptyPos = findEmptyPosition(cur);
            int[] parentEmptyPos = findEmptyPosition(parent);

            int rowDiff = parentEmptyPos[0] - emptyPos[0];
            int colDiff = parentEmptyPos[1] - emptyPos[1];

            if(rowDiff == 1) road.add("U");
            else if(rowDiff == -1) road.add("D");
            else if(colDiff == 1) road.add("L");
            else if(colDiff == -1) road.add("R");

            cur = parent;
        }

        Collections.reverse(road);
        return road;
    }
    
    public static void main(String[] args) {
        int[][] initial = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        int[][] goal = {
            {4, 1, 2},
            {0, 5, 3},
            {7, 8, 6} 
        };

        List<String> path = solve(initial, goal);
        if (path != null) {
            System.out.println("Optimal path: " + path);
        } else {
            System.out.println("No solution found.");
        }
    }
}

