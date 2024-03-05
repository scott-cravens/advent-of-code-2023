package day_01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day_01a {
    public static void main(String[] args) {

        // Read input file containing lines with digits
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/day_01/input.txt"))) {
            int sumOfCombinedDigits = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                int combinedDigits = processLine(line); // Process each line
                sumOfCombinedDigits += combinedDigits; // Accumulate the sum
            }

            System.out.println("Sum of combined digits: " + sumOfCombinedDigits);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int processLine(String line) {
        if (!hasDigits(line)) {
            System.err.println("Error: Line contains no digits - " + line);
            return 0; // Return 0 if line has no digits
        }

        String digitOnlyLine = line.replaceAll("\\D", ""); // Remove non-digits
        int firstDigit = Character.getNumericValue(digitOnlyLine.charAt(0)); // Get first digit
        int lastDigit = Character.getNumericValue(digitOnlyLine.charAt(digitOnlyLine.length() - 1)); // Get last digit

        try {
            return Integer.parseInt(String.valueOf(firstDigit) + lastDigit); // Combine and parse digits
        } catch (NumberFormatException e) {
            System.err.println("Error: Unable to parse combined digits - " + line);
            return 0; // Return 0 if parsing fails
        }
    }

    private static boolean hasDigits(String line) {
        return line.matches(".*\\d.*"); // Check if line contains any digits
    }
}
