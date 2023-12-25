package day_08;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_08a {

    public static String instructions = null;
    public static List<String[]> maps = new ArrayList<>();

    public static void main(String[] args) {

        // Read input data and format into maps and instructions
        readInputData();

        // Initialize and create the directed graph
        DirectedGraph graph = new DirectedGraph();
        graph.createGraph(maps);

        // Traverse the graph
        graph.traverseGraph("AAA", "ZZZ", instructions);
    }

    private static void readInputData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/day_08/input.txt"))) {

            String line;
            instructions = reader.readLine();
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                addMap(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading input file");
            System.exit(0);
        }
    }

    public static void addMap(String input) {
        // Define the pattern to match the input format "XYZ = (ABC, DEF)"
        Pattern pattern = Pattern.compile("([A-Z]{3})\\s*=\\s*\\(([A-Z]{3}),\\s*([A-Z]{3})\\)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            // Extract the three character strings
            String vertex1 = matcher.group(1);
            String vertex2 = matcher.group(2);
            String vertex3 = matcher.group(3);

            // Add them to the maps list
            maps.add(new String[]{vertex1, vertex2, vertex3});
        } else {
            System.err.println("Invalid input format: " + input);
        }
    }

    public static class DirectedGraph {
        private final DefaultDirectedGraph<String, DefaultEdge> graph;
        private final Map<String, Map<String, String>> edgeMap;

        public DirectedGraph() {
            graph = new DefaultDirectedGraph<>(DefaultEdge.class);
            edgeMap = new HashMap<>();
        }

        public void createGraph(List<String[]> maps) {
            for (String[] map : maps) {
                String source = map[0];
                String leftTarget = map[1];
                String rightTarget = map[2];

                // Add the vertices
                graph.addVertex(source);
                graph.addVertex(leftTarget);
                graph.addVertex(rightTarget);

                // Add the edges
                graph.addEdge(source, leftTarget);
                graph.addEdge(source, rightTarget);

                // Record the connections in edgeMap for easy traversal later
                edgeMap.computeIfAbsent(source, k -> new HashMap<>()).put("L", leftTarget);
                edgeMap.computeIfAbsent(source, k -> new HashMap<>()).put("R", rightTarget);
            }
        }

        public void traverseGraph(String startVertex, String endVertex, String instructions) {
            if (!graph.containsVertex(startVertex) || !graph.containsVertex(endVertex)) {
                System.out.println("Error: start or end vertex not found in the graph.");
                return;
            }

            String currentVertex = startVertex;
            int stepCount = 0;
            int instructionIndex = 0;

            while (!currentVertex.equals(endVertex)) {
                if (instructionIndex == instructions.length()) {
                    instructionIndex = 0; // Reset instructions to the start
                }

                String direction = String.valueOf(instructions.charAt(instructionIndex++));
                currentVertex = edgeMap.get(currentVertex).get(direction);
                stepCount++;
            }

            System.out.println(STR."Reached \{endVertex} from \{startVertex} in \{stepCount} steps.");
        }
    }
}