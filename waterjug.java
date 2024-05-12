import java.util.*;

public class waterjug {

    // Define a State class to represent the state of the water jugs
    static class State {
        int x, y;         // The current water levels in the jugs
        State parent;     // The previous state leading to this state

        // Constructor to initialize the state
        State(int x, int y, State parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }

        // Override the equals method for comparing states
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            State state = (State) obj;
            return x == state.x && y == state.y;
        }

        // Override the hashCode method for using states in collections like HashSet
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    // Helper method to add a state to the queue and visited set if it's not already visited
    static void addToQueueAndVisited(Queue<State> queue, Set<State> visited, State state) {
        if (!visited.contains(state)) {
            queue.add(state);
            visited.add(state);
        }
    }

    // Helper method to pour water from one jug to another and add the resulting state to the queue
    static void pourAndAddToQueue(int from, int to, int toCapacity, Queue<State> queue, Set<State> visited, State parent) {
        int poured = Math.min(from, toCapacity - to);
        State newState = new State(from - poured, to + poured, parent);
        addToQueueAndVisited(queue, visited, newState);
    }

    // Perform a breadth-first search to find a solution to the water jug problem
    public static void bfs(int jug1Capacity, int jug2Capacity, int target) {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        
        // Initialize the queue and visited set with the initial state
        queue.add(new State(0, 0, null));
        visited.add(new State(0, 0, null));

        while (!queue.isEmpty()) {
            State currentState = queue.poll(); // Get and remove the front of the queue
            int x = currentState.x;            // Current water level in jug 1
            int y = currentState.y;            // Current water level in jug 2

            if (x == target || y == target) {
                System.out.println("\n\tBFS Solution:");
                printSteps(currentState); // If the target is reached, print the solution
                return;
            }

            // Generate and add possible next states to the queue
            addToQueueAndVisited(queue, visited, new State(jug1Capacity, y, currentState)); // Fill jug 1
            addToQueueAndVisited(queue, visited, new State(x, jug2Capacity, currentState)); // Fill jug 2
            addToQueueAndVisited(queue, visited, new State(0, y, currentState));             // Empty jug 1
            addToQueueAndVisited(queue, visited, new State(x, 0, currentState));             // Empty jug 2
            pourAndAddToQueue(x, y, jug2Capacity, queue, visited, currentState);             // Pour jug 1 to jug 2
            pourAndAddToQueue(y, x, jug1Capacity, queue, visited, currentState);             // Pour jug 2 to jug 1
        }

        System.out.println("\n\tBFS Solution not found.");
    }

    // Helper method to print the steps to reach the solution
    static void printSteps(State state) {
        List<State> steps = new ArrayList<>();
        while (state != null) {
            steps.add(state);
            state = state.parent;
        }
        Collections.reverse(steps);

        for (State step : steps) {
            System.out.println("\n\t(" + step.x + ", " + step.y + ")");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\t***WATER JUG PROBLEM USING BFS***\n");

        // Get user input for jug capacities and target amount
        System.out.print("\n\tEnter the capacity of jug 1: ");
        int jug1Capacity = scanner.nextInt();

        System.out.print("\n\tEnter the capacity of jug 2: ");
        int jug2Capacity = scanner.nextInt();

        System.out.print("\n\tEnter the target amount: ");
        int target = scanner.nextInt();

        // Call the BFS algorithm to solve the problem
        bfs(jug1Capacity, jug2Capacity, target);
    }
}
