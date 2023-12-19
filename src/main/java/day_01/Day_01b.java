package day_01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class Day_01b {

    // Array of number words and their corresponding numeric values
    private static final String[] NUMBER_PAIRS = {"one", "1", "two", "2", "three", "3", "four", "4", "five", "5", "six", "6", "seven", "7", "eight", "8", "nine", "9"};

    // Find all occurrences of numbers in the given line
    public static TreeMap<Integer, String> findAllNumbers(String line) {
        TreeMap<Integer, String> resultMap = new TreeMap<>();
        line = line.toLowerCase();

        for (String number : NUMBER_PAIRS) {
            int index = 0;
            while ((index = line.indexOf(number, index)) != -1) {
                resultMap.put(index, convertToNumeric(number));
                index += number.length();
            }
        }
        return resultMap;
    }

    // Process a line to find and combine numeric values
    private static int processLine(String line) {
        TreeMap<Integer, String> result = findAllNumbers(line);

        // Check if no numbers are found in the line
        if (result.isEmpty()) return 0;

        // Get the first and last numeric values
        String firstValue = result.firstEntry().getValue();
        String lastValue = result.lastEntry().getValue();

        try {
            // Convert numeric values to integers and combine them
            return Integer.parseInt(firstValue + lastValue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric values: " + firstValue + ", " + lastValue);
        }
    }

    // Convert number words or numeric values to their corresponding numeric representation
    public static String convertToNumeric(String input) {
        for (int i = 0; i < NUMBER_PAIRS.length; i += 2) {
            if (NUMBER_PAIRS[i].equalsIgnoreCase(input)) {
                return NUMBER_PAIRS[i + 1];
            }
        }

        // If the input is already a numeric value between "1" and "9", return it as is
        if (input.matches("[1-9]")) return input;

        // Handle the case where an invalid input is provided
        throw new IllegalArgumentException("Invalid input: " + input);
    }

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/day_01/input.txt"))) {
            int sumOfCombinedDigits = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                int combinedDigits = processLine(line);
                sumOfCombinedDigits += combinedDigits;
            }

            System.out.println("Sum of combined digits: " + sumOfCombinedDigits);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}