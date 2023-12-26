package day_09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day_09a {

    static final List<int[]> sequences = new ArrayList<>();

    public static void main(String[] args) {

        processInputData();

        long totalResult = 0;
        for (int[] sequence : sequences) totalResult += predictNextNumber(sequence);

        System.out.println(STR."Predicted next number: \{totalResult}");
    }

    private static void processInputData() {

        // Get input data from file
        List<String> lines = readInputData();

        // Convert input to int[] and add to sequences list
        lines.forEach(line -> sequences.add(stringToIntArray(line)));
    }

    public static int[] stringToIntArray(String numbersString) {
        String[] numbersList = numbersString.split("\\s+");  // Split using one or more spaces

        int[] intArray = new int[numbersList.length];
        for (int i = 0; i < numbersList.length; i++) {
            try {
                intArray[i] = Integer.parseInt(numbersList[i]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(STR."Invalid number format in string: \{numbersString}");
            }
        }

        return intArray;
    }

    // Method to predict the next number in a sequence.
    public static long predictNextNumber(int[] sequence) {
        if (sequence == null || sequence.length == 0) {
            System.err.println("Invalid sequence.");
            System.exit(0);
            return 0;
        }

        // Initialize sum with the last number of the sequence
        long sum = sequence[sequence.length - 1];

        // Continue until all numbers in the sequence are 0
        while (!allZeros(sequence)) {
            // Calculate the differences and update the sequence.
            for (int i = 0; i < sequence.length - 1; i++) {
                sequence[i] = sequence[i + 1] - sequence[i];
            }

            // Pop the last entry from sequence
            int[] newSequence = new int[sequence.length - 1];
            System.arraycopy(sequence, 0, newSequence, 0, newSequence.length);
            sequence = newSequence;  // Assign the new array to the original reference

            // Add the last number of the updated sequence to sum
            sum += sequence[sequence.length - 1];
        }

        return sum;
    }

    // Check if all elements in the array are 0.
    private static boolean allZeros(int[] array) {
        for (int j : array) {
            if (j != 0) {
                return false;
            }
        }
        return true;
    }

    private static List<String> readInputData() {
        Path filePath = Paths.get("src/main/java/day_09/input.txt");

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