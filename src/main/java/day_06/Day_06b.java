package day_06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_06b {

    public static void main(String[] args) {

        // Read input file and get time and distance values
        List<String> lines = readInputData();
        long times = extractIntegers(lines.get(0));
        long distances = extractIntegers(lines.get(1));

        // Calculate winners for race
        long waysToWIn = calculateWaysToWinRace(times, distances);
        System.out.println(STR."Ways to win the race with a record time of \{times} and race distance of \{distances} is \{waysToWIn}");
    }

    public static int calculateWaysToWinRace(long recordTime, long recordDistance) {
        int winners = 0;
        for (int i = 0; i <= recordTime; i++) {
            long distance = i * (recordTime - i);
            if (distance > recordDistance) winners++;
        }
        return winners;
    }

    public static long extractIntegers(String line) {

        // Compile a regular expression to search for the number and color pattern
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(line);

        StringBuilder combinedValue = new StringBuilder();

        // Check if the pattern matches
        while (matcher.find()) {
            // Extract the number and append to string
            combinedValue.append(matcher.group());
        }
        return Long.parseLong(String.valueOf(combinedValue));
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
