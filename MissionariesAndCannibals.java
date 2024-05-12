import java.util.*;

// Class representing the state of the problem
class State {
    int missionariesLeft;
    int cannibalsLeft;
    int boat; // 0 if boat is on the left side, 1 if on the right side

    // Constructor to initialize the state
    public State(int missionariesLeft, int cannibalsLeft, int boat) {
        this.missionariesLeft = missionariesLeft;
        this.cannibalsLeft = cannibalsLeft;
        this.boat = boat;
    }

    // Check if the current state is valid
    public boolean isValid() {
        return (missionariesLeft >= 0 && cannibalsLeft >= 0
                && missionariesLeft + cannibalsLeft <= 3
                && (missionariesLeft == 0 || missionariesLeft >= cannibalsLeft)
                && (3 - missionariesLeft == 0 || (3 - missionariesLeft) >= (3 - cannibalsLeft)));
    }

    // Check if the current state is the goal state
    public boolean isGoal() {
        return missionariesLeft == 0 && cannibalsLeft == 0 && boat == 1;
    }

    // Override equals method for comparing states
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        State state = (State) obj;
        return missionariesLeft == state.missionariesLeft &&
                cannibalsLeft == state.cannibalsLeft &&
                boat == state.boat;
    }

    // Override hashCode method for using states in collections
    @Override
    public int hashCode() {
        return Objects.hash(missionariesLeft, cannibalsLeft, boat);
    }
}

public class MissionariesAndCannibals {

    // Get the list of valid successors for the given state
    public static List<State> getSuccessors(State currentState) {
        List<State> successors = new ArrayList<>();

        // Loop through all possible combinations of missionaries and cannibals
        for (int m = 0; m <= 2; m++) {
            for (int c = 0; c <= 2; c++) {
                // Check if the combination is valid for the current state
                if (m + c >= 1 && m + c <= 2) {
                    // Calculate the new state after moving missionaries and cannibals
                    int newMissionariesLeft = currentState.missionariesLeft - m;
                    int newCannibalsLeft = currentState.cannibalsLeft - c;
                    int newBoat = 1 - currentState.boat;

                    // Create a new state and add it to the list if it's valid
                    State successor = new State(newMissionariesLeft, newCannibalsLeft, newBoat);
                    if (successor.isValid()) {
                        successors.add(successor);
                    }
                }
            }
        }

        return successors;
    }

    // Solve the problem using Breadth-First Search
    public static List<State> solve() {
        Queue<List<State>> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        // Initialize the initial state
        State initialState = new State(3, 3, 0);
        List<State> initialPath = new ArrayList<>();
        initialPath.add(initialState);

        // Add the initial path to the queue and mark the initial state as visited
        queue.add(initialPath);
        visited.add(initialState);

        // Explore states using BFS
        while (!queue.isEmpty()) {
            List<State> path = queue.poll(); // Get the path from the queue
            State currentState = path.get(path.size() - 1); // Get the current state

            // Check if the current state is the goal state
            if (currentState.isGoal()) {
                return path; // Return the solution path
            }

            // Get the valid successors of the current state
            List<State> successors = getSuccessors(currentState);

            // Explore each successor
            for (State successor : successors) {
                if (!visited.contains(successor)) {
                    visited.add(successor); // Mark the successor as visited
                    List<State> newPath = new ArrayList<>(path);
                    newPath.add(successor); // Add the successor to the new path
                    queue.add(newPath); // Add the new path to the queue for further exploration
                }
            }
        }

        return Collections.emptyList(); // No solution found
    }

    // Print the solution path
    public static void printSolution(List<State> solution) {
        for (State state : solution) {
            System.out.println("Missionaries left: " + state.missionariesLeft +
                    ", Cannibals left: " + state.cannibalsLeft +
                    ", Boat: " + (state.boat == 0 ? "Left" : "Right"));
        }
    }

    // Main method
    public static void main(String[] args) {
        // Solve the problem
        List<State> solution = solve();

        // Print the solution if found, otherwise, print a message indicating no solution
        if (!solution.isEmpty()) {
            System.out.println("Solution found:");
            printSolution(solution);
        } else {
            System.out.println("No solution found.");
        }
    }
}
