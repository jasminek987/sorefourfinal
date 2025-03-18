//PLAYER- A.I. movements, players, A.I. players, human players.
package model;
import java.util.Random;


public abstract class Player {
    protected char symbol;// this would be our R(red) or B(blue)
    protected static char currentTurn = 'X';

    public Player(char symbol) {
        this.symbol = symbol;
    }


    public char getSymbol() {
        return symbol;
    }

    public abstract int chooseMove(char[][] board); // im assuming our board is called board

    public static void swapTurn() {
        currentTurn = (currentTurn == 'R') ? 'B' : 'R'; // Toggle between red and blue
    }

    // Method to get the current player's turn (static so that it's shared across all players)
    public static char getCurrentTurn() {
        return currentTurn;
    }
    public char getRandomChoice() {
        Random random = new Random();
        return random.nextBoolean() ? 'R' : 'B';
    }
    public abstract void displayRandomChoice();// to pick who's turn is first

    public String getName() {

        return "";
    }

    public void setColor(String red) {
    }
}