package day_04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;

public class Day_04a {

    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("[^0-9]");

    public static void main(String[] args) {
        Path filePath = Paths.get("src/main/java/day_04/input.txt");
        int totalPoints = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                int points = calculatePoints(line);
                totalPoints += points;
            }
            System.out.println("Total Points: " + totalPoints);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private static int calculatePoints(String line) {
        String[] parts = line.split("[:|]");
        String[] winningNumbers = parts[1].trim().split("\\s+");
        String[] guessedNumbers = parts[2].trim().split("\\s+");

        int matches = countMatches(winningNumbers, guessedNumbers);

        return matches >= 1 ? (int) Math.pow(2, matches - 1) : 0;
    }

    private static int countMatches(String[] winningNumbers, String[] guessedNumbers) {
        int count = 0;
        for (String guess : guessedNumbers) {
            for (String winner : winningNumbers) {
                if (Objects.equals(guess, winner)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    private static int getCardNumber(String line) {
        String numericPart = CARD_NUMBER_PATTERN
                .matcher(line.split(":")[0])
                .replaceAll("")
                .trim();
        return Integer.parseInt(numericPart);
    }
}