import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CSP {

    private int boardSize;
    private List<Integer> solution;

    
    public CSP(int boardSize) {
        this.boardSize = boardSize;
        this.solution = new ArrayList<>(); // Initialize the solution as an empty list
    }

    public boolean solve() {
        return backtrack(0); // Start the backtracking process from the first row (row 0)
    }

    
    private boolean backtrack(int row) {
        // If all queens are placed successfully, return true (base case)
        if (row == boardSize) {
            return true;
        }

        // Iterate through each column in the current row
        for (int col = 0; col < boardSize; col++) {
            // Check if it's safe to place a queen at the current row and column
            if (isSafe(row, col)) {
                // Place the queen in this column
                solution.add(col);
                // Recursively try to place queens in the next row
                if (backtrack(row + 1)) {
                    return true; // If a solution is found, return true
                }
                // If no solution is found, backtrack by removing the last queen placement
                solution.remove(solution.size() - 1);
            }
        }

        return false; // If no safe placement is found in this row, return false
    }

    // Private method to check if it's safe to place a queen at a given row and column
    private boolean isSafe(int row, int col) {
        // Iterate through all previously placed queens
        for (int i = 0; i < row; i++) {
            int prevCol = solution.get(i); // Get the column of a previously placed queen
            // Check if the current placement conflicts with any previously placed queens
            if (prevCol == col || Math.abs(prevCol - col) == Math.abs(i - row)) {
                return false; // If there is a conflict, return false
            }
        }
        return true; // If no conflicts are found, return true (safe placement)
    }

    // Method to print the N-Queens solution to the console
    public void printSolution() {
        if (solution.isEmpty()) {
            System.out.println("No solution found."); // If there's no solution, print a message
        } else {
            // Iterate through the solution and print the chessboard with queens and dots
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    if (col == solution.get(row)) {
                        System.out.print("Q "); // Print 'Q' for queens
                    } else {
                        System.out.print(". "); // Print '.' for empty spaces
                    }
                }
                System.out.println(); // Move to the next row
            }
        }
    }

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of queens (n) n should be greater than 3: ");
        int n = scanner.nextInt(); 
        scanner.close(); 

        CSP csp = new CSP(n); 
        boolean isSolved = csp.solve(); // Attempt to solve the N-Queens problem

        
        if (isSolved) {
            System.out.println("Solution found:");
            csp.printSolution();
        } else {
            System.out.println("No solution found.");
        }
    }
}
