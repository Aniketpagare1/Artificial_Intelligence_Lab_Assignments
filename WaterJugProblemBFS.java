import java.util.*;

public class WaterJugProblemBFS {

    // Define a class to represent the state of the water jugs
    static class State {
        int x, y;         // The current water levels in the jugs
        State parent;     // The previous state leading to this state
        int heuristic;    // Heuristic value

        // Constructor to initialize the state
        State(int x, int y, State parent, int heuristic) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.heuristic = heuristic;
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

    // Helper method to add a state to the priority queue and visited set if it's not already visited
    static void addToQueueAndVisited(PriorityQueue<State> queue, Set<State> visited, State state) {
        if (!visited.contains(state)) {
            queue.add(state);
            visited.add(state);
        }
    }

    // Helper method to pour water from one jug to another and add the resulting state to the priority queue
    static void pourAndAddToQueue(int from, int to, int toCapacity, PriorityQueue<State> queue, Set<State> visited, State parent) {
        int poured = Math.min(from, toCapacity - to);
        int heuristic = Math.abs(parent.x - from - poured) + Math.abs(parent.y - to - poured);
        State newState = new State(from - poured, to + poured, parent, heuristic);
        addToQueueAndVisited(queue, visited, newState);
    }

    // Best-First Search function
    public static void bestFirstSearch(int jug1Capacity, int jug2Capacity, int target) {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.heuristic));
        Set<State> visited = new HashSet<>();

        // Initialize the queue and visited set with the initial state
        State initialState = new State(0, 0, null, Math.abs(jug1Capacity - target) + Math.abs(jug2Capacity - target));//heuristic- difference between jug capacities and target water
        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            int x = currentState.x;
            int y = currentState.y;

            if (x == target || y == target) {
                System.out.println("\n\tBest-First Search Solution:");
                printSteps(currentState);
                return;
            }

            // Generate and add possible next states to the queue
            addToQueueAndVisited(queue, visited, new State(jug1Capacity, y, currentState, Math.abs(jug1Capacity - target) + Math.abs(y - target))); // Fill jug 1
            addToQueueAndVisited(queue, visited, new State(x, jug2Capacity, currentState, Math.abs(x - target) + Math.abs(jug2Capacity - target))); // Fill jug 2
            addToQueueAndVisited(queue, visited, new State(0, y, currentState, Math.abs(0 - target) + Math.abs(y - target)));             // Empty jug 1
            addToQueueAndVisited(queue, visited, new State(x, 0, currentState, Math.abs(x - target) + Math.abs(0 - target)));             // Empty jug 2
            pourAndAddToQueue(x, y, jug2Capacity, queue, visited, currentState);             // Pour jug 1 to jug 2
            pourAndAddToQueue(y, x, jug1Capacity, queue, visited, currentState);             // Pour jug 2 to jug 1
        }

        System.out.println("\n\tBest-First Search Solution not found.");
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
            if (step.parent != null) {
                System.out.println("\n\t(" + step.x + ", " + step.y + ")");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\t***WATER JUG PROBLEM USING BEST-FIRST SEARCH***\n");

        System.out.print("\n\tEnter the capacity of jug 1: ");
        int jug1Capacity = scanner.nextInt();

        System.out.print("\n\tEnter the capacity of jug 2: ");
        int jug2Capacity = scanner.nextInt();

        System.out.print("\n\tEnter the target amount: ");
        int target = scanner.nextInt();

        bestFirstSearch(jug1Capacity, jug2Capacity, target);
    }
}
