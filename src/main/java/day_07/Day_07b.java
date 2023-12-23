package day_07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day_07b {

    static List<Hand> hands = new ArrayList<>();
    public static void main(String[] args) {
        // Read input file and get time and distance values
        readInputData();

        // Sort hands using the HandComparator
        hands.sort(new HandComparator());

        // Calculate ranks and total winnings
        int totalWinnings = 0;
        for (int i = 0; i < hands.size(); i++) {
            int bid = hands.get(i).getBid();
            totalWinnings += bid * (i + 1);
        }
        System.out.println(STR."Total winnings: \{totalWinnings}");
    }

    private static void readInputData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/day_07/input.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                processTheLine(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading input file");
            System.exit(0);
        }
    }

    private static void processTheLine(String line) {
        String[] parts = line.split("\\s+");
        hands.add(new Hand(parts[0], Integer.parseInt(parts[1])));
    }

    public static class Hand {

        String hand;
        int bid;
        int type;

        public Hand(String hand, int bid) {
            this.hand = hand;
            this.bid = bid;
            this.type = -1;
        }

        public int getBid() {
            return bid;
        }

        public String getHand() {
            return hand;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class HandComparator implements Comparator<Hand> {

        @Override
        public int compare(Hand o1, Hand o2) {
            // Ensure hand types are calculated beforehand
            if (o1.getType() == -1) {
                o1.setType(HandType.getTypeFromString(o1.getHand()));
            }
            if (o2.getType() == -1) {
                o2.setType(HandType.getTypeFromString(o2.getHand()));
            }

            // Compare hand types
            if (o1.getType() == o2.getType()) {
                // If hand types are equal, compare individual cards
                return compareHands(o1.getHand(), o2.getHand());
            } else if (o1.getType() > o2.getType()) {
                return 1; // Hand A wins
            } else {
                return -1; // Hand B wins
            }
        }

        public static int compareHands(String a, String b) {
            // Get the hand types (using getKey())
            double handTypeA = HandType.getTypeFromString(a);
            double handTypeB = HandType.getTypeFromString(b);


            // Compare hand types
            if (handTypeA == handTypeB) {
                // If hand types are equal, compare individual cards
                if (a.equals(b)) {
                    return 0; // Hands are identical
                }
                for (int i = 0; i < a.length(); i++) {
                    char cardA = a.charAt(i);
                    char cardB = b.charAt(i);
                    if (HandType.labels.indexOf(cardA) < HandType.labels.indexOf(cardB)) {
                        return 1; // Hand A wins
                    } else if (HandType.labels.indexOf(cardA) > HandType.labels.indexOf(cardB)) {
                        return -1; // Hand B wins
                    }
                }
                // If all cards are equal, return a tie
                return 0;
            } else if (handTypeA > handTypeB) {
                return 1; // Hand A wins
            } else {
                return -1; // Hand B wins
            }
        }
    }

    public static class HandType {

        static final String labels = "AKQJT98765432";

        public static int getType(int[] hand) {
            Map<Character, Integer> cardCounts = new HashMap<>();
            for (int x : hand) {
                cardCounts.put(labels.charAt(x), cardCounts.getOrDefault(labels.charAt(x), 0) + 1);
            }

            // Sort the entries by value and directly collect the values into a list
            List<Integer> amounts = cardCounts.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

            // Mapping of amounts to hand type values
            Map<List<Integer>, Integer> handTypeMap = Map.of(
                    List.of(5), 7,
                    List.of(1, 4), 6,
                    List.of(2, 3), 5,
                    List.of(1, 1, 3), 4,
                    List.of(1, 2, 2), 3,
                    List.of(1, 1, 1, 2), 2
            );

            return handTypeMap.getOrDefault(amounts, 1);
        }

        // Create array of index values based on labels
        public static int getTypeFromString(String hand) {
            int[] handValues = new int[hand.length()];
            for (int i = 0; i < hand.length(); i++) {
                char cardLabel = hand.charAt(i);
                int cardValue = labels.indexOf(cardLabel);
                handValues[i] = cardValue;
            }

            return getType(handValues);
        }
    }
}