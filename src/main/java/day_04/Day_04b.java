package day_04;

import java.io.*;
import java.util.*;

public class Day_04b {
    public static void main(String[] args) {
        String filePath = "src/main/java/day_04/input.txt";
        List<Card> cards = new ArrayList<>();

        // Read cards from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Card card = parseCard(line);
                cards.add(card);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // Process each card according to its instances value
        try {
            processCards(cards);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return;
        }

        // Calculate and print the total instances
        int totalInstances = cards.stream().mapToInt(Card::getInstances).sum();
        System.out.println("Total number of instances: " + totalInstances);
    }

    // Parse a line from the file into a Card object
    private static Card parseCard(String line) {
        String[] parts = line.split("\\|");
        String[] cardInfo = parts[0].trim().split(":");
        String[] winningNumbersArray = cardInfo[1].trim().split("\\s+");
        String[] guessedNumbersArray = parts[1].trim().split("\\s+");

        Set<Integer> winningNumbers = new HashSet<>();
        Set<Integer> guessedNumbers = new HashSet<>();

        for (String number : winningNumbersArray) {
            winningNumbers.add(Integer.parseInt(number));
        }

        for (String number : guessedNumbersArray) {
            guessedNumbers.add(Integer.parseInt(number));
        }

        return new Card(winningNumbers, guessedNumbers);
    }

    // Process the cards based on their instances value
    private static void processCards(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            Card currentCard = cards.get(i);
            for (int j = 0; j < currentCard.getInstances(); j++) {
                processCard(currentCard, cards, i);
            }
        }
    }

    // Process a single card
    private static void processCard(Card currentCard, List<Card> cards, int currentIndex) {
        int matches = currentCard.calculateMatches();

        if (currentIndex + matches > cards.size()) {
            throw new RuntimeException("Error: Matches exceed the number of remaining cards.");
        }

        for (int j = currentIndex + 1; j <= currentIndex + matches; j++) {
            cards.get(j).incrementInstances();
        }
    }
}

// Card class representing each card in the stack
class Card {
    private final Set<Integer> winningNumbers;
    private final Set<Integer> guessedNumbers;
    private int instances;

    public Card(Set<Integer> winningNumbers, Set<Integer> guessedNumbers) {
        this.winningNumbers = winningNumbers;
        this.guessedNumbers = guessedNumbers;
        this.instances = 1;
    }

    // Calculate the number of matches between winning and guessed numbers
    public int calculateMatches() {
        Set<Integer> intersection = new HashSet<>(winningNumbers);
        intersection.retainAll(guessedNumbers);
        return intersection.size();
    }

    // Increment the instances value
    public void incrementInstances() {
        instances++;
    }

    // Get the instances value
    public int getInstances() {
        return instances;
    }
}