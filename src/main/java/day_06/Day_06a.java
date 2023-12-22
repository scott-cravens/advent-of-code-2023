package day_06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day_06a {

    public static void main(String[] args) {

        // Read input file and get time and distance values
        List<String> lines = readInputData();
        List<Integer> times = extractIntegers(lines.get(0));
        List<Integer> distances = extractIntegers(lines.get(1));

        // Calculate winners for each race and multiply together
        int waysToWinMultiple = 1;
        for (int i = 0; i < times.size(); i++) {
            int waysToWIn = calculateWaysToWinRace(times.get(i), distances.get(i));
            System.out.println(STR."Ways to win race \{i+1} with a record time of \{times.get(i)} and race distance of \{distances.get(i)} is \{waysToWIn}");
            waysToWinMultiple *= waysToWIn;
        }
        System.out.println(STR."Ways to win multiplier: \{waysToWinMultiple}");
    }

    public static int calculateWaysToWinRace(int recordTime, int recordDistance) {
        int winners = 0;
        for (int i = 0; i <= recordTime; i++) {
            int distance = i * (recordTime - i);
            if (distance > recordDistance) winners++;
        }
        return winners;
    }

    public static List<Integer> extractIntegers(String line) {
        List<Integer> integers = new ArrayList<>();

        // Split the string by whitespace, handling potential extra spaces
        String[] splitText = line.trim().split("\\s+");

        // Skip the first element (e.g. "Time:") and convert remaining elements to integers
        for (int i = 1; i < splitText.length; i++) {
            try {
                integers.add(Integer.parseInt(splitText[i]));
            } catch (NumberFormatException e) {
                // Handle non-integer values gracefully (e.g., log a warning)
                System.err.println(STR."Invalid integer found in string: \{splitText[i]}");
            }
        }

        return integers;
    }

    private static List<String> readInputData() {
        Path filePath = Paths.get("src/main/java/day_06/input.txt");

        try {
            // Read all lines from the input file
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println(STR."Error reading the file: \{e.getMessage()}");
            System.exit(0);
            return new ArrayList<>();
        }
    }
}