package day_02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_02a {

    public static final int redCubes = 12;
    public static final int greenCubes = 13;
    public static final int blueCubes = 14;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/day_02/input.txt"))) {

            int sumOfGameIds = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (processGame(line)) {
                    sumOfGameIds += parseGameId(line);
                }
            }
            System.out.println("Sum of valid Game ID's: " + sumOfGameIds);

        } catch (IOException e) {
            System.out.println("Error reading input file");
            System.exit(0);
        }
    }

    private static boolean processGame(String line) {
        String substring = line.replaceAll("Game (\\d+): ", "");

        // Split the string into substrings based on the semicolon delimiter
        String[] rounds = substring.split(";");

        // Loop through each substring and print it after trimming
        for (String round : rounds) {
            if (!processRound(round.trim())) return false;
        }
        return true;
    }

    private static boolean processRound(String round) {

        // Find number of each dice in this round
        int red = findNumberBeforeColor(round, "red");
        int blue = findNumberBeforeColor(round, "blue");
        int green = findNumberBeforeColor(round, "green");

        // Verify number of each cube is equal to or less than allowed
        return red <= redCubes && blue <= blueCubes && green <= greenCubes;
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

    private static int parseGameId(String line) {
        // Regular expression to match the game id format
        Pattern pattern = Pattern.compile("Game (\\d+):");
        Matcher matcher = pattern.matcher(line);

        // Check if the pattern matches the text
        if (matcher.find()) {
            // Extract the game id and convert it to an integer
            return Integer.parseInt(matcher.group(1));
        } else {
            throw new IllegalArgumentException("Invalid game string format.");
        }
    }
}
