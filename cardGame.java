
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class cardGame {
    private ArrayList<Integer> deck;
    private ArrayList<Integer> playerHand;
    private ArrayList<Integer> dealerHand;
    private Random rand;

    // la method ou or the function of the class
    public cardGame() {
        deck = new ArrayList<Integer>();
        playerHand = new ArrayList<Integer>();
        dealerHand = new ArrayList<Integer>();
        rand = new Random();
        // Fill the deck with cards
        for (int i = 1; i <= 10; i++) {
            deck.add(i);
        }
        // Shuffle the deck
        for (int i = 0; i <= deck.size() - 1; i++) {
            int j = rand.nextInt(deck.size());
            int temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    public void play() {
        try (Scanner input = new Scanner(System.in)) {
            boolean playing = true;
            int score = 0;

            while (playing) {
                // Draw a card
                int card = deck.get(0);
                deck.remove(0);
                playerHand.add(card);
                System.out.println("You drew a " + card);

                // Calculate the score
                score = 0;

                for (int i = 0; i < playerHand.size(); i++) {
                    score += playerHand.get(i);
                }
                System.out.println("Your score is " + score);

                // Check if the player has gone bust
                if (score >21) {
                    System.out.println("You went bust! Game over.");
                    playing = false;
                    break;
                }

                // Ask the player whether to keep playing
                System.out.println("Do you want to draw another card? (Y/N)");
                String answer = input.nextLine().toLowerCase();

                if (answer.equals("n")) {
                    playing=false;
                    score=0;
                    for (int i = 0; i < dealerHand.size(); i++) {
                        score += dealerHand.get(i);
                    }
                    while (true) {
                        if (score >= 21) {
                            System.out.println("The dealer went bust! You win!");
                            break;
                        } else if (score >= 17) {
                            dealerPlay();
                            break;
                        } else {
                            int card1 = deck.get(0);
                            deck.remove(0);
                            dealerHand.add(card1);
                            score += card1;
                            System.out.println("The dealer drew a card. The new score is " + score);
                        }

                    }

                }
            }

            // Ask the player whether to restart the game
            System.out.println("Do you want to play again? (Y/N)");
            String answer = input.nextLine().toLowerCase();

            if (answer.equals("y")) {
                // Reset the game and start again
                // deck.clear();
                playerHand.clear();
                dealerHand.clear();
                play();
                dealerPlay();

            } else {
                // Exit the game
                System.out.println("Thanks for playing!");
            }
        }
    }

    public void dealerPlay() {
        System.out.println("The dealer reveals their face-down card: " + dealerHand.get(1));
        int score = dealerHand.get(0) + dealerHand.get(1);
        System.out.println("The dealer's score is: " + score);

        // Check if the dealer has an ace and if counting it as 11 would bring the score
        // to 17 or more
        boolean hasAce = false;
        int aceIndex = 1;
        for (int i = 0; i < dealerHand.size(); i++) {
            if (dealerHand.get(i) == 1) {
                hasAce = true;
                aceIndex = i;
                if (score + 10 <= 21) {
                    dealerHand.set(i, 11);
                    score += 10;
                }
            }
        }

        // Dealer must draw cards if score is 16 or under
        while (score <= 16) {
            int card = deck.get(0);
            deck.remove(0);
            dealerHand.add(card);
            System.out.println("The dealer drew a " + card);
            score += card;

            // Check for aces again
            if (hasAce && score + 10 >= 17 && score + 10 <= 21) {
                dealerHand.set(aceIndex, 11);
                score += 10;
            }
        }

        if (score >= 17 && score <= 21) {
            if (score > dealerHand.get(aceIndex)) {
                System.out.println("The dealer wins!");
            } else if (score > dealerHand.get(aceIndex)) {
                System.out.println("You win!");
            } else {
                System.out.println("It's a tie!");
            }
        }

        // System.out.println("The dealer's final score is: " + score);

        if (score > 21) {
            System.out.println("The dealer went bust! You win!");
            return;
        }

    }

    public static void main(String[] args) {
        cardGame game = new cardGame();
        game.play();
    }
}
