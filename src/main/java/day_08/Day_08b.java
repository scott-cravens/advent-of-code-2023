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
import java.util.stream.Collectors;

public class Day_08b {

    private final DefaultDirectedGraph<String, DefaultEdge> graph;
    private final Map<String, Map<String, String>> edgeMap;

    public static String instructions = null;
    public static List<String[]> maps = new ArrayList<>();

    public Day_08b() {
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        edgeMap = new HashMap<>();
    }

    public void createGraph() {
        for (String[] map : maps) {
            String source = map[0];
            String leftTarget = map[1];
            String rightTarget = map[2];

            graph.addVertex(source);
            graph.addVertex(leftTarget);
            graph.addVertex(rightTarget);

            graph.addEdge(source, leftTarget);
            graph.addEdge(source, rightTarget);

            edgeMap.computeIfAbsent(source, k -> new HashMap<>()).put("L", leftTarget);
            edgeMap.computeIfAbsent(source, k -> new HashMap<>()).put("R", rightTarget);
        }
    }

    public void traverseGraph(String startVertexRegex, String endVertexRegex, String instructions) {
        Pattern startPattern = Pattern.compile(startVertexRegex);
        Pattern endPattern = Pattern.compile(endVertexRegex);

        // Find all start vertices
        List<String> startVertices = graph.vertexSet().stream()
                .filter(v -> startPattern.matcher(v).matches())
                .toList();

        // Initialize current positions for each traversal
        Map<String, String> currentPositions = startVertices.stream()
                .collect(Collectors.toMap(v -> v, v -> v));

        int stepCount = 0;
        boolean allMatchedEnd;
        do {
            allMatchedEnd = true;
            for (String startVertex : startVertices) {
                String currentVertex = currentPositions.get(startVertex);
                String direction = String.valueOf(instructions.charAt(stepCount % instructions.length()));
                String nextVertex = edgeMap.get(currentVertex).get(direction);
                currentPositions.put(startVertex, nextVertex);

                // Check if the current vertex matches the endVertex pattern
                if (!endPattern.matcher(nextVertex).matches()) {
                    allMatchedEnd = false;
                }
            }
            stepCount++;

        } while (!allMatchedEnd);

        System.out.println(STR."All traversals reached an end vertex in \{stepCount} steps.");
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

    public static void main(String[] args) {

        // Read input data and format into maps and instructions
        readInputData();


        Day_08b graph = new Day_08b();
        graph.createGraph();
        graph.traverseGraph("[A-Z][A-Z]A", "[A-Z][A-Z]Z", instructions); // Start and end vertex regex
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
            System.err.println(STR."Invalid input format: \{input}");
        }
    }
}