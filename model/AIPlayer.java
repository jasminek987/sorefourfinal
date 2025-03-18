package model;


public class AIPlayer extends Player {

    public AIPlayer(char symbol){
        super(symbol);
    }

    @Override
    public int chooseMove(char[][] board) {
        for (int col = 0; col < board[0].length; col++) {
            if (canWin(board, col, symbol)) {
                return col;
                // this loop checks every column on the board to see if Ai can win by choosing that column.
                // if AI can win it will play that column.
            }
        }
        // block oppponent from winning
        char AI = (symbol == 'R') ? 'B' : 'R';
        for (int col = 0; col < board[0].length; col++) {
            if (canWin(board, col, AI)) {
                return col;
            }
        }
        // Ai plays in any available column if it isn't trying to win or block
        for (int col = 0; col < board[0].length; col++) {
            if (isAvailable(board, col)) {
                return col;
            }
        }
        return -1;// if no moves are available.
    }

    private boolean isAvailable(char[][] board, int col) {
        if (col < 0 || col > board[0].length) {
            return false;
        }
        if (board[0][col] != 0) {
            return false;
        }
        return true;
    }

    private boolean canWin(char[][] board, int col, char playerSymbol) {
        // loop to look through every slot
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][col] == ' ') {
                board[row][col] = playerSymbol;
                boolean win = checkWin(board, playerSymbol);
                if (win) return true;
                break;
            }
        }
        return false;
    }

    private boolean checkWin(char[][] board, char playerSymbol) {
        // Check horizontal, vertical, and diagonal lines for a win

        // Check horizontal (across rows)
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == playerSymbol &&
                        board[row][col + 1] == playerSymbol &&
                        board[row][col + 2] == playerSymbol &&
                        board[row][col + 3] == playerSymbol) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void displayRandomChoice(){
    }
}

