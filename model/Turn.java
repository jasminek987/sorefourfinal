package model;

public class Turn{

    private Player currentPlayer;
    private Player player1;
    private Player player2;

    public Turn ( Player player1) {

        this.currentPlayer = player1;
    }

// Switch to the next player

    public void switchTurn(Player player1, Player player2) {
        this.currentPlayer = (this.currentPlayer == player1) ? player2 : player1;
    }

// Using method to get the current player's turn.

    public Player getCurrentPlayer() {

        return currentPlayer;

    }

}
