package day_03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day_03a {

    // List to store extracted valid part numbers from the schematic
    public static List<String> validPartNumbers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Path filePath = Paths.get("src/main/java/day_03/input.txt");
        List<String> lines;

        try {
            // Read all lines from the input file
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return;
        }

        // Extract the number of columns and rows from the first line
        int cols = lines.get(0).length();
        int rows = lines.size();

        // Convert the list of strings to a 2D character array representing the schematic
        char[][] engineSchematic = convertListToArray(lines, rows, cols);

        // Find and store valid part numbers identified from the schematic
        findValidPartNumbers(engineSchematic, rows, cols);

        // Calculate the sum of integers parsed from the valid part numbers
        int sum = validPartNumbers.stream().mapToInt(Integer::parseInt).sum();
        System.out.println("Sum of valid part numbers: " + sum);
    }

    private static void findValidPartNumbers(char[][] schematic, int rows, int cols) {
        StringBuilder partNumberBuilder = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            boolean hasAdjacentSymbol = false;
            for (int col = 0; col < cols; col++) {
                char currentChar = schematic[row][col];
                if (Character.isDigit(currentChar)) {
                    partNumberBuilder.append(currentChar);
                    if (checkForAdjacentSymbol(schematic, row, col)) hasAdjacentSymbol = true;
                } else {
                    if (hasAdjacentSymbol) {
                        validPartNumbers.add(partNumberBuilder.toString());
                        hasAdjacentSymbol = false;
                    }
                    partNumberBuilder = new StringBuilder();
                }
            }
            // Check if a valid number is at the end of the line
            if (!partNumberBuilder.isEmpty() && hasAdjacentSymbol) {
                validPartNumbers.add(partNumberBuilder.toString());
            }
        }
    }


    private static boolean checkForAdjacentSymbol(char[][] schematic, int row, int col) {
        // Iterate through surrounding cells (excluding current) searching for non-alphanumeric symbols
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Skip checking the current cell
                int newRow = row + dx, newCol = col + dy;
                if (newRow >= 0 && newRow < schematic.length && newCol >= 0 && newCol < schematic[newRow].length) {
                    char neighborChar = schematic[newRow][newCol];
                    if (!Character.isLetterOrDigit(neighborChar) && neighborChar != '.') {
                        // Found a non-alphanumeric symbol next to a digit, signifying a valid part number
                        return true;
                    }
                }
            }
        }
        return false; // No non-alphanumeric symbol found in surrounding cells
    }

    private static char[][] convertListToArray(List<String> lines, int rows, int cols) {
        // Create a 2D character array with dimensions matching the number of rows and columns
        char[][] array = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            // Convert each line from the list to a character array and assign it to a row in the 2D array
            array[i] = lines.get(i).toCharArray();
        }
        return array;
    }
}