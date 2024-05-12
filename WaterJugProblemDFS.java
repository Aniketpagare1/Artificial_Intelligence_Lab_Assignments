import java.util.*;

public class WaterJugProblemDFS {

    // Define a class to represent the state of the water jugs.
    static class State {
        int x, y; // Current water levels in two jugs
        State parent; // Reference to the parent state

        State(int x, int y, State parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }

        // Override equals method to compare two states for equality
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            State state = (State) obj;
            return x == state.x && y == state.y;
        }

        // Override hashCode method to generate a unique hash code for each state
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    // Utility function to add a state to the stack and visited set
    static void addToStackAndVisited(Stack<State> stack, Set<State> visited, State state) {
        if (!visited.contains(state)) {
            stack.push(state);
            visited.add(state);
        }
    }

    // Utility function to pour water from one jug to another and add the new state to the stack
    static void pourAndAddToStack(int from, int to, int toCapacity, Stack<State> stack, Set<State> visited, State parent) {
        int poured = Math.min(from, toCapacity - to);
        State newState = new State(from - poured, to + poured, parent);
        addToStackAndVisited(stack, visited, newState);
    }

    // Depth-First Search algorithm to find a solution to the water jug problem
    public static void dfs(int jug1Capacity, int jug2Capacity, int target) {
        Stack<State> stack = new Stack<>(); // Stack to perform DFS
        Set<State> visited = new HashSet<>(); // Set to keep track of visited states
        stack.push(new State(0, 0, null)); // Initialize with both jugs empty

        while (!stack.isEmpty()) {
            State currentState = stack.pop();
            int x = currentState.x;
            int y = currentState.y;

            if (x == target || y == target) {
                System.out.println("\n\tDFS Solution:");
                printSteps(currentState); 
                return;
            }

            addToStackAndVisited(stack, visited, new State(jug1Capacity, y, currentState)); // Fill jug 1
            addToStackAndVisited(stack, visited, new State(x, jug2Capacity, currentState)); // Fill jug 2
            addToStackAndVisited(stack, visited, new State(0, y, currentState)); // Empty jug 1
            addToStackAndVisited(stack, visited, new State(x, 0, currentState)); // Empty jug 2
            pourAndAddToStack(x, y, jug2Capacity, stack, visited, currentState); // Pour jug 1 to jug 2
            pourAndAddToStack(y, x, jug1Capacity, stack, visited, currentState); // Pour jug 2 to jug 1
        }

        System.out.println("\n\tDFS Solution not found.");
    }

    // Utility function to print the steps of the solution
    static void printSteps(State state) {
        List<State> steps = new ArrayList<>();
        while (state != null) { //trace back from final state to initial state
            steps.add(state);
            state = state.parent; // Backtracking occurs by following the parent states
        }
        Collections.reverse(steps); // list contains the states in reverse order, from the initial state to the final state. reversing it for correct order.

        for (State step : steps) {
            System.out.println("\n\t(" + step.x + ", " + step.y + ")");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\t***WATER JUG PROBLEM USING DFS***\n");

        // Input capacities of the two jugs and the target amount
        System.out.print("\n\tEnter the capacity of jug 1: ");
        int jug1Capacity = scanner.nextInt();

        System.out.print("\n\tEnter the capacity of jug 2: ");
        int jug2Capacity = scanner.nextInt();

        System.out.print("\n\tEnter the target amount: ");
        int target = scanner.nextInt();

        // Call the DFS function to find a solution
        dfs(jug1Capacity, jug2Capacity, target);
    }
}
