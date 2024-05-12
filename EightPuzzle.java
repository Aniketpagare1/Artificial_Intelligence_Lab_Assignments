import java.util.*;
//Astar 8 puzzle
class PuzzleNode implements Comparable<PuzzleNode> {
    int[] state;// Current state of the puzzle
    PuzzleNode parent;// Parent node in the search tree
    char action;// Action taken to reach this state from the parent
    int cost;// Cost to reach this state from the initial state
    int heuristic;// Heuristuc value for this state

    @Override
    public int compareTo(PuzzleNode other) {
        return Integer.compare(this.cost + this.heuristic, other.cost + other.heuristic);
    }
}

public class EightPuzzle {
    public static void main(String[] args) {
        int[] initial = {1, 0, 5, 3, 8, 2, 6, 7, 4};
        int[] goal = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        solvePuzzle(initial, goal);
    }

    public static void solvePuzzle(int[] initial, int[] goal) {
        // Create initial node with the provided initial state
        PuzzleNode initialNode = new PuzzleNode();
        initialNode.state = initial;
        initialNode.parent = null;
        initialNode.action = '\0';
        initialNode.cost = 0;
        initialNode.heuristic = calculateHeuristic(initial, goal);

        // Priority queue for open nodes and a set for closed nodes
        PriorityQueue<PuzzleNode> openList = new PriorityQueue<>();
        HashSet<String> closedList = new HashSet<>();
        openList.add(initialNode);

        while (!openList.isEmpty()) {
            PuzzleNode currentNode = openList.poll();// Get the node with the lowest combined cost and heuristic
            closedList.add(Arrays.toString(currentNode.state));

            if (Arrays.equals(currentNode.state, goal)) {
                printSolution(currentNode);
                return;
            }

            int emptyIndex = findEmptyTile(currentNode.state);

            // Generate and explore possible actions from the current state
            for (char action : getPossibleActions(emptyIndex)) {
                int[] newState = applyAction(currentNode.state, emptyIndex, action);
                if (!closedList.contains(Arrays.toString(newState))) {
                    PuzzleNode newNode = new PuzzleNode();
                    newNode.state = newState;
                    newNode.parent = currentNode;
                    newNode.action = action;
                    newNode.cost = currentNode.cost + 1;
                    newNode.heuristic = calculateHeuristic(newState, goal);
                    openList.add(newNode);
                }
            }
        }

        System.out.println("No solution found.");
    }

    // Calculates the heuristic value for a given state using the number of
    // misplaced tiles
    public static int calculateHeuristic(int[] state, int[] goal) {
        int heuristic = 0;
        for (int i = 0; i < state.length; i++) {
            if (state[i] != goal[i] && state[i] != 0) {
                heuristic++;
            }
        }
        return heuristic;
    }

    public static int findEmptyTile(int[] state) {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    // returns an array of possible actions (L, R, U, D) based on the empty tiles position
    public static char[] getPossibleActions(int emptyIndex) {
        char[] actions = new char[4];
        int row = emptyIndex / 3;
        int col = emptyIndex % 3;
        int index = 0;
        if (col > 0) {
            actions[index++] = 'L';
        }
        if (col < 2) {
            actions[index++] = 'R';
        }
        if (row > 0) {
            actions[index++] = 'U';
        }
        if (row < 2) {
            actions[index++] = 'D';
        }
        return Arrays.copyOfRange(actions, 0, index);
    }

    // Applies a given action to a puzzle state and returns the new state
    public static int[] applyAction(int[] state, int emptyIndex, char action) {
        int[] newState = Arrays.copyOf(state, state.length);
        int newRow = emptyIndex / 3;
        int newCol = emptyIndex % 3;
        switch (action) {
            case 'L':
                newCol--;
                break;
            case 'R':
                newCol++;
                break;
            case 'U':
                newRow--;
                break;
            case 'D':
                newRow++;
                break;
        }
        int newIndex = newRow * 3 + newCol;
        newState[emptyIndex] = state[newIndex];
        newState[newIndex] = 0;
        return newState;
    }

    // Prints the solution path from the initial state to the goal state
    public static void printSolution(PuzzleNode node) {
        ArrayList<PuzzleNode> solutionPath = new ArrayList<>();
        while (node != null) {
            solutionPath.add(0, node);
            node = node.parent;
        }

        for (PuzzleNode solutionNode : solutionPath) {
            printState(solutionNode.state);
            System.out.println("Action: " + solutionNode.action);
            System.out.println();
        }
    }

    // Prints a puzzle state in a readable format
    public static void printState(int[] state) {
        for (int i = 0; i < state.length; i++) {
            System.out.print(state[i] + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }
}    
