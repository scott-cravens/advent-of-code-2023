package day_01a;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Main <file_path>");
            System.exit(1);
        }

        String filePath = args[0];

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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
