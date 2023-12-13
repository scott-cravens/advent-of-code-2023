package day_01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day_01a {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/day_01/input.txt"))) {
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

    private static int processLine(String line) {
        if (!hasDigits(line)) {
            System.err.println("Error: Line contains no digits - " + line);
            return 0;
        }

        String digitOnlyLine = line.replaceAll("\\D", "");
        int firstDigit = Character.getNumericValue(digitOnlyLine.charAt(0));
        int lastDigit = Character.getNumericValue(digitOnlyLine.charAt(digitOnlyLine.length() - 1));

        try {
            return Integer.parseInt(String.valueOf(firstDigit) + lastDigit);
        } catch (NumberFormatException e) {
            System.err.println("Error: Unable to parse combined digits - " + line);
            return 0;
        }
    }

    private static boolean hasDigits(String line) {
        return line.matches(".*\\d.*");
    }
}
