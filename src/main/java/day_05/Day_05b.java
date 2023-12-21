package day_05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_05b {

    private static final String SEED_TO_SOIL = "seed-to-soil";
    private static final String SOIL_TO_FERTILIZER = "soil-to-fertilizer";
    private static final String FERTILIZER_TO_WATER = "fertilizer-to-water";
    private static final String WATER_TO_LIGHT = "water-to-light";
    private static final String LIGHT_TO_TEMPERATURE = "light-to-temperature";
    private static final String TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity";
    private static final String HUMIDITY_TO_LOCATION = "humidity-to-location";

    private static List<Long> seeds;
    private static List<long[]> seedToSoilMap;
    private static List<long[]> soilToFertilizerMap;
    private static List<long[]> fertilizerToWaterMap;
    private static List<long[]> waterToLightMap;
    private static List<long[]> lightToTemperatureMap;
    private static List<long[]> temperatureToHumidityMap;
    private static List<long[]> humidityToLocationMap;

    public static void main(String[] args) {

        // Read input file and get seed and map values
        List<String> lines = readInputData();
        storeSeedValues(lines);
        storeMapValues(lines);
        calculateLocation();
    }

    private static void calculateLocation() {
        long minimumLocation = -1;
        for (long seed : seeds) { // For each seed
            long soil = calculateCategoryValue(seed, seedToSoilMap);
            long fertilizer = calculateCategoryValue(soil, soilToFertilizerMap);
            long water = calculateCategoryValue(fertilizer, fertilizerToWaterMap);
            long light = calculateCategoryValue(water, waterToLightMap);
            long temperature = calculateCategoryValue(light, lightToTemperatureMap);
            long humidity = calculateCategoryValue(temperature, temperatureToHumidityMap);
            long location = calculateCategoryValue(humidity, humidityToLocationMap);

            minimumLocation = minimumLocation == -1 ? location : Long.min(location, minimumLocation);
        }
        System.out.println(STR."Minimum location: \{minimumLocation}");
    }

    private static long calculateCategoryValue(long input, List<long[]> sectionMap) {
        long destinationRangeStart;
        long sourceRangeStart;
        long rangeLength;

        for (long[] range : sectionMap) { // For each range (line)
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

    private static List<long[]> getSectionMapValues(List<String> lines, String section) {
        List<long[]> sectionMap = new ArrayList<>();
        boolean mapStarted = false;

        for (String line : lines) {
            if (mapStarted) {
                if (line.isEmpty()) break; // End of map reached

                String[] values = line.trim().split("\\s+"); // Split by one or more spaces
                if (values.length == 3) {
                    try {
                        long[] longValues = new long[3];
                        for (int i = 0; i < 3; i++) {
                            longValues[i] = Long.parseLong(values[i]);
                        }
                        sectionMap.add(longValues);
                    } catch (NumberFormatException e) {
                        System.err.println(STR."Invalid Long value in \{section} map: \{line}");
                    }
                } else System.err.println(STR."Wrong number of values in \{section} map: \{line}");
            } else if (line.contains(section)) mapStarted = true;
        }

        return sectionMap;
    }

    private static void storeSeedValues(List<String> lines) {
        seeds = new ArrayList<>();
        List<Long> theSeeds = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(lines.getFirst());

        // Get seed ranges from lines
        while (matcher.find()) {
            theSeeds.add(Long.parseLong(matcher.group()));
        }

        // Calculate seeds from ranges and add to seeds ArrayList
        for (int i = 0; i < theSeeds.size(); i += 2) {
            for (int j = 0; j < theSeeds.get(i + 1); j++) {
                seeds.add((theSeeds.get(i) + j));
            }
        }
    }

    private static List<String> readInputData() {
        Path filePath = Paths.get("src/main/java/day_05/input.txt");

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