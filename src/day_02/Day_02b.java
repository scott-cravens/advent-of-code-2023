package day_02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_02b {

    public static int maxRedCubes;
    public static int maxGreenCubes;
    public static int maxBlueCubes;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/day_02/input.txt"))) {

            int sumOfPowerSets = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                sumOfPowerSets += processGame(line);
            }
            System.out.println("Sum of the minimum set of cubes for each game: " + sumOfPowerSets);

        } catch (IOException e) {
            System.out.println("Error reading input file");
            System.exit(0);
        }
    }

    private static int processGame(String line) {
        maxRedCubes = 0;
        maxGreenCubes = 0;
        maxBlueCubes = 0;

        String substring = line.replaceAll("Game (\\d+): ", "");

        // Split the string into substrings based on the semicolon delimiter
        String[] rounds = substring.split(";");

        // Loop through each substring and print it after trimming
        for (String round : rounds) {
            processRound(round.trim());
        }

        // Return the setPower (power of the minimum set of cubes for this game)
        return maxBlueCubes * maxRedCubes * maxGreenCubes;
    }

    private static void processRound(String round) {

        // Set max variables to the max values
        maxRedCubes = Math.max(maxRedCubes, findNumberBeforeColor(round, "red"));
        maxGreenCubes = Math.max(maxGreenCubes, findNumberBeforeColor(round, "green"));
        maxBlueCubes = Math.max(maxBlueCubes, findNumberBeforeColor(round, "blue"));
    }

    public static int findNumberBeforeColor(String round, String color) {
        // Compile a regular expression to search for the number and color pattern
        Pattern pattern = Pattern.compile("(\\d+) " + color);
        Matcher matcher = pattern.matcher(round);

        // Check if the pattern matches the round string
        if (matcher.find()) {
            // Extract the number and convert it to an integer
            return Integer.parseInt(matcher.group(1));
        } else {
            return 0;
        }
    }
}
