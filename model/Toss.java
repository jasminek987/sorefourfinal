package model;

import java.util.Random;

public class Toss {
    private final char firstPlayerSymbol = 'R'; // Always Red
    private final char secondPlayerSymbol = 'B'; // Always Blue
    private Random random;
    
    public Toss() {
        random = new Random();
    }
    
    /**
     * Simulates a coin toss to determine which player goes first
     * @return 'R' for Red (player) or 'B' for Blue (AI)
     */
    public char flipCoin() {
        return random.nextBoolean() ? 'R' : 'B';
    }
    
    /**
     * Displays a message about the toss result
     * @param result the toss result ('R' or 'B')
     */
    public void displayTossResult(char result) {
        String winner = (result == 'R') ? "Red" : "Blue";
        System.out.println("Coin toss result: " + winner + " goes first!");
    }

    public void toss(Player player1, Player player2){

        Random rand = new Random();
        boolean toss = rand.nextBoolean();

        if(toss){
            player1.setColor("Red");
            player2.setColor("Blue");
        }
        else {
            player1.setColor("Blue");
            player2.setColor("Red");
        }

    }
}


