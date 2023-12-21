package day_05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_05a {

    private static final String SEED_TO_SOIL = "seed-to-soil";
    private static final String SOIL_TO_FERTILIZER = "soil-to-fertilizer";
    private static final String FERTILIZER_TO_WATER = "fertilizer-to-water";
    private static final String WATER_TO_LIGHT = "water-to-light";
    private static final String LIGHT_TO_TEMPERATURE = "light-to-temperature";
    private static final String TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity";
    private static final String HUMIDITY_TO_LOCATION = "humidity-to-location";

    private static List<Long> seeds;
    private static List<Long[]> seedToSoilMap;
    private static List<Long[]> soilToFertilizerMap;
    private static List<Long[]> fertilizerToWaterMap;
    private static List<Long[]> waterToLightMap;
    private static List<Long[]> lightToTemperatureMap;
    private static List<Long[]> temperatureToHumidityMap;
    private static List<Long[]> humidityToLocationMap;

    public static void main(String[] args) {

        // Read input file and get seed and map values
        List<String> lines = readInputData();
        storeSeedValues(lines);
        storeMapValues(lines);
        calculateLocation();

        System.out.println("... to the end");
    }

    private static void calculateLocation() {
        long minimumLocation = -1;
        for (Long seed : seeds) { // For each seed
            long soil = calculateCategoryValue(seed, seedToSoilMap);
            long fertilizer = calculateCategoryValue(soil, soilToFertilizerMap);
            long water = calculateCategoryValue(fertilizer, fertilizerToWaterMap);
            long light = calculateCategoryValue(water, waterToLightMap);
            long temperature = calculateCategoryValue(light, lightToTemperatureMap);
            long humidity = calculateCategoryValue(temperature, temperatureToHumidityMap);
            long location = calculateCategoryValue(humidity, humidityToLocationMap);

            System.out.printf("Seed %s; soil %s; fertilizer %s; water %s; light %s; temperature %s; humidity %s; location %s\n", seed, soil, fertilizer, water, light, temperature, humidity, location);
            minimumLocation = minimumLocation == -1 ? location : Long.min(location, minimumLocation);
        }
        System.out.println("Minimum location: " + minimumLocation);
    }

    private static long calculateCategoryValue(Long input, List<Long[]> sectionMap) {
        long destinationRangeStart;
        long sourceRangeStart;
        long rangeLength;

        for (Long[] range : sectionMap) { // For each range (line)
            destinationRangeStart = range[0];
            sourceRangeStart = range[1];
            rangeLength = range[2];

            if (isWithinRange(input ,sourceRangeStart, sourceRangeStart + rangeLength - 1)) {
                // Calculate the destination range
                long offset = input - sourceRangeStart;
                return destinationRangeStart + offset;
            }

        }

        return input;
    }

    public static boolean isWithinRange(long input, long startOfRange, long endOfRange) {
        return input >= startOfRange && input <= endOfRange;
    }

    private static void storeMapValues(List<String> lines) {
        seedToSoilMap = getSectionMapValues(lines, SEED_TO_SOIL);
        soilToFertilizerMap = getSectionMapValues(lines, SOIL_TO_FERTILIZER);
        fertilizerToWaterMap = getSectionMapValues(lines, FERTILIZER_TO_WATER);
        waterToLightMap = getSectionMapValues(lines, WATER_TO_LIGHT);
        lightToTemperatureMap = getSectionMapValues(lines, LIGHT_TO_TEMPERATURE);
        temperatureToHumidityMap = getSectionMapValues(lines, TEMPERATURE_TO_HUMIDITY);
        humidityToLocationMap = getSectionMapValues(lines, HUMIDITY_TO_LOCATION);
    }

    private static List<Long[]> getSectionMapValues(List<String> lines, String section) {
        List<Long[]> sectionMap = new ArrayList<>();
        boolean mapStarted = false;

        for (String line : lines) {
            if (mapStarted) {
                if (line.isEmpty()) break; // End of map reached

                String[] values = line.trim().split("\\s+"); // Split by one or more spaces
                if (values.length == 3) {
                    try {
                        Long[] longValues = new Long[3];
                        for (int i = 0; i < 3; i++) {
                            longValues[i] = Long.parseLong(values[i]);
                        }
                        sectionMap.add(longValues);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid Long value in " +section+ " map: " + line);
                    }
                } else System.err.println("Wrong number of values in " +section+ " map: " + line);
            } else if (line.contains(section)) mapStarted = true;
        }

        return sectionMap;
    }

    private static void storeSeedValues(List<String> lines) {
        seeds = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(lines.get(0));

        while (matcher.find()) seeds.add(Long.parseLong(matcher.group()));
    }

    private static List<String> readInputData() {
        Path filePath = Paths.get("src/main/java/day_05/input.txt");

        try {
            // Read all lines from the input file
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            System.exit(0);
            return new ArrayList<>();
        }
    }
}