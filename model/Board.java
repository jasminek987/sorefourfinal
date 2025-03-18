package model;

public class Board {

    private Peg[][] board = new Peg[4][4];
    private final int BOARDSIZE = 4;

    public Board() {
        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
                this.board[i][j] = new Peg(i,j);
            }
        }

    }

    public boolean addBead(int row, int col, String beadColor) {
        if (row >= 0 && row < 4 && col >= 0 && col < 4 && !this.board[row][col].isFull()) {
            this.board[row][col].addBead(beadColor);
            return true;
        } else {
            return false;
        }
    }

    public void clearBoard(){
        for(int i = 0; i < 4; ++i){
            for(int j = 0; j < 4; ++j){
                board[i][j].removeBeads();
            }
        }
    }

    public boolean isFull(){
        for (Peg[] peg: board) {
            for (Peg p : peg) {
                if (!p.isFull()) {
                    return false;
                }
            }
        }
        return true;
    }

    public String showBoard() {
        StringBuilder boardString = new StringBuilder();

        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
                boardString.append((char)(65 + i));
                boardString.append(j + 1);
                boardString.append(":");
                boardString.append(this.board[i][j] == null ? " " : this.board[i][j].toString());
                if (i != 3 || j != 3) {
                    boardString.append("\n");
                }
            }
        }

        return boardString.toString();
    }

    public void drawBoard() {
        for (int level = 0; level < 4; level++) { // Iterate through levels (depth)
            for (int row = 3; row >= 0; row--) { // Iterate rows from top to bottom
                // Add indentation for 3D effect
                System.out.print("  ".repeat(level));

                for (int col = 0; col < 4; col++) {
                    char bead = board[level][row].getBead(level).getSymbol(); // Retrieve bead ('R', 'B', or empty)
                    System.out.print("|" + (bead == '\0' ? "|" : bead)); // Empty slots as '|', pieces as 'R' or 'B'
                }
                System.out.println("|"); // Close row with '|'
            }
        }
    }

    public char getBeadChar(int row, int col, int level){
        Bead bead = board[row][col].getBead(level);
        if(!(bead == null)){
            return bead.getSymbol();
        }
        else return '|';
    }
}