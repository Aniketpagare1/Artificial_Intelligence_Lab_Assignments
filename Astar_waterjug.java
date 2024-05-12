import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

// Class representing the state of the water jugs
class State {
    int x, y; // Current water levels in the two jugs
    State parent; // Parent state
    int cost; // Cost to reach this state
    int heuristic; // Heuristic value

    // Constructor to initialize the state
    public State(int x, int y, State parent, int cost, int heuristic) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
    }

    // Check if two states are equal
    public boolean equals(State other) {
        return this.x == other.x && this.y == other.y;
    }
}

// Main class for the A* algorithm solving the water jug problem
public class Astar_waterjug {

    // Main method
    public static void main(String[] args) {
        int jugXCapacity = 4; // Capacity of jug X
        int jugYCapacity = 3; // Capacity of jug Y
        int targetAmount = 2; // Amount of water to be measured in one of the jugs

        State initialState = new State(0, 0, null, 0, 0); // Initial state with both jugs empty
        State goalState = null; // Goal state

        // Priority queue for open states, using a comparator based on cost and heuristic
        PriorityQueue<State> openSet = new PriorityQueue<>((a, b) -> (a.cost + a.heuristic) - (b.cost + b.heuristic));

        // Set to keep track of visited states
        HashSet<State> closedSet = new HashSet<>();

        openSet.add(initialState);

        // A* search loop
        while (!openSet.isEmpty()) {
            State currentState = openSet.poll();
            closedSet.add(currentState);

            if (currentState.x == targetAmount || currentState.y == targetAmount) {
                goalState = currentState;
                break;
            }

            // Generate child states by pouring water
            generateChildStates(currentState, jugXCapacity, jugYCapacity, openSet, closedSet, targetAmount);
        }

        // Print the solution or indicate if no solution is found
        if (goalState == null) {
            System.out.println("No solution found.");
        } else {
            System.out.println("Solution found. Steps to reach the goal:");

            Stack<State> path = new Stack<>();
            while (goalState != null) {
                path.push(goalState);
                goalState = goalState.parent;
            }

            while (!path.isEmpty()) {
                State state = path.pop();
                System.out.println("Fill jug X: " + state.x + "L, Fill jug Y: " + state.y + "L");
            }
        }
    }

    // Generate child states by pouring water
    private static void generateChildStates(State currentState, int jugXCapacity, int jugYCapacity,
            PriorityQueue<State> openSet, HashSet<State> closedSet, int targetAmount) {
        int x = currentState.x;
        int y = currentState.y;

        // Fill jug X
        fillJugX(currentState, jugXCapacity, openSet, closedSet, targetAmount);

        // Fill jug Y
        fillJugY(currentState, jugYCapacity, openSet, closedSet, targetAmount);

        // Pour water from X to Y
        pourXtoY(currentState, jugYCapacity, openSet, closedSet, targetAmount);

        // Pour water from Y to X
        pourYtoX(currentState, jugXCapacity, openSet, closedSet, targetAmount);

        // Empty jug X
        emptyJugX(currentState, openSet, closedSet, targetAmount);

        // Empty jug Y
        emptyJugY(currentState, openSet, closedSet, targetAmount);
    }

    // Helper methods to generate child states

    // Fill jug X
    private static void fillJugX(State currentState, int jugXCapacity, PriorityQueue<State> openSet,
            HashSet<State> closedSet, int targetAmount) {
        int x = jugXCapacity;
        int y = currentState.y;

        if (!isStateVisited(x, y, closedSet)) {
            int cost = currentState.cost + 1;
            int heuristic = calculateHeuristic(x, y, targetAmount);
            openSet.add(new State(x, y, currentState, cost, heuristic));
        }
    }

    // Fill jug Y
    private static void fillJugY(State currentState, int jugYCapacity, PriorityQueue<State> openSet,
            HashSet<State> closedSet, int targetAmount) {
        int x = currentState.x;
        int y = jugYCapacity;

        if (!isStateVisited(x, y, closedSet)) {
            int cost = currentState.cost + 1;
            int heuristic = calculateHeuristic(x, y, targetAmount);
            openSet.add(new State(x, y, currentState, cost, heuristic));
        }
    }

    // Pour water from X to Y
    private static void pourXtoY(State currentState, int jugYCapacity, PriorityQueue<State> openSet,
            HashSet<State> closedSet, int targetAmount) {
        int x = currentState.x;
        int y = currentState.y;

        while (x > 0 && y < jugYCapacity) {
            x--;
            y++;

            if (!isStateVisited(x, y, closedSet)) {
                int cost = currentState.cost + 1;
                int heuristic = calculateHeuristic(x, y, targetAmount);
                openSet.add(new State(x, y, currentState, cost, heuristic));
            }
        }
    }

    // Pour water from Y to X
    private static void pourYtoX(State currentState, int jugXCapacity, PriorityQueue<State> openSet,
            HashSet<State> closedSet, int targetAmount) {
        int x = currentState.x;
        int y = currentState.y;

        while (y > 0 && x < jugXCapacity) {
            y--;
            x++;

            if (!isStateVisited(x, y, closedSet)) {
                int cost = currentState.cost + 1;
                int heuristic = calculateHeuristic(x, y, targetAmount);
                openSet.add(new State(x, y, currentState, cost, heuristic));
            }
        }
    }

    // Empty jug X
    private static void emptyJugX(State currentState, PriorityQueue<State> openSet, HashSet<State> closedSet,
            int targetAmount) {
        int x = 0;
        int y = currentState.y;

        if (!isStateVisited(x, y, closedSet)) {
            int cost = currentState.cost + 1;
            int heuristic = calculateHeuristic(x, y, targetAmount);
            openSet.add(new State(x, y, currentState, cost, heuristic));
        }
    }

    // Empty jug Y
    private static void emptyJugY(State currentState, PriorityQueue<State> openSet, HashSet<State> closedSet,
            int targetAmount) {
        int x = currentState.x;
        int y = 0;

        if (!isStateVisited(x, y, closedSet)) {
            int cost = currentState.cost + 1;
            int heuristic = calculateHeuristic(x, y, targetAmount);
            openSet.add(new State(x, y, currentState, cost, heuristic));
        }
    }

    // Check if a state has been visited
    private static boolean isStateVisited(int x, int y, HashSet<State> closedSet) {
        State newState = new State(x, y, null, 0, 0);
        return closedSet.contains(newState);
    }

    // Calculate the heuristic value (in this case, the absolute difference between
    // the current amount and the target amount)
    private static int calculateHeuristic(int x, int y, int targetAmount) {
        return Math.abs(x - targetAmount) + Math.abs(y - targetAmount);
    }
}
