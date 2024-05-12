import java.util.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class GraphColoringCSP {

    // Class to represent the graph
    static class Graph {
        int vertices;
        List<Integer>[] adjList;

        public Graph(int vertices) {
            this.vertices = vertices;
            // Use List<Integer>[] directly
            this.adjList = new List[vertices];
            for (int i = 0; i < vertices; i++) {
                adjList[i] = new ArrayList<>();
            }
        }

        // Method to add an edge between two vertices
        public void addEdge(int v, int u) {
            adjList[v].add(u);
            adjList[u].add(v);
        }
    }

    // Class to represent an assignment of colors to vertices
    static class Assignment {
        Map<Integer, Integer> colors;

        public Assignment() {
            this.colors = new HashMap<>();
        }

        // Check if the assignment is complete (all vertices are colored)
        public boolean isComplete(int totalVertices) {
            return colors.size() == totalVertices;
        }

        // Check if the assignment is valid (no adjacent vertices have the same color)
        public boolean isValid(Graph graph) {
            for (int vertex : colors.keySet()) {
                for (int neighbor : graph.adjList[vertex]) {
                    if (colors.containsKey(neighbor) && colors.get(vertex).equals(colors.get(neighbor))) {
                        return false; // Adjacent vertices have the same color
                    }
                }
            }
            return true;
        }

        // Create a clone of the assignment
        public Assignment clone() {
            Assignment clonedAssignment = new Assignment();
            clonedAssignment.colors.putAll(this.colors);
            return clonedAssignment;
        }

        // Assign a color to a vertex
        public void assignColor(int vertex, int color) {
            colors.put(vertex, color);
        }

        // Check if a vertex has been assigned a color
        public boolean hasColor(int vertex) {
            return colors.containsKey(vertex);
        }

        // Get the color assigned to a vertex
        public int getColor(int vertex) {
            return colors.get(vertex);
        }
    }

    // Method to solve the graph coloring CSP using backtracking
    static boolean graphColoring(Graph graph, int totalColors) {
        return graphColoring(graph, totalColors, new Assignment());
    }

    static boolean graphColoring(Graph graph, int totalColors, Assignment assignment) {
        if (assignment.isComplete(graph.vertices)) {
            // All vertices are colored
            System.out.println("Solution: " + assignment.colors);
            return true;
        }

        int vertex = selectUnassignedVertex(graph, assignment);

        for (int color = 1; color <= totalColors; color++) {
            Assignment newAssignment = assignment.clone();
            newAssignment.assignColor(vertex, color);

            if (newAssignment.isValid(graph)) {
                if (graphColoring(graph, totalColors, newAssignment)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Method to select an unassigned vertex
    static int selectUnassignedVertex(Graph graph, Assignment assignment) {
        for (int vertex = 0; vertex < graph.vertices; vertex++) {
            if (!assignment.hasColor(vertex)) {
                return vertex;
            }
        }
        return -1; // All vertices are assigned colors
    }

    // Main method
    public static void main(String[] args) {
        int vertices = 4;
        Graph graph = new Graph(vertices);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        int totalColors = 3;

        if (!graphColoring(graph, totalColors)) {
            System.out.println("No solution exists.");
        }
    }
}
