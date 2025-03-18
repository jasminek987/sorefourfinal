package model;

public class Position {
    private static String[][][] coordinate = new String[4][4][4];
    private int row;
    private int column;
    private int level;
    private String color;
    //private Bead bead;
    //private Player owner;  // ✅ Tracks which player owns this bead

    Position(int row, int column, int level, String color) {
        this.row = row;
        this.column = column;
        this.level = level;
        coordinate[this.row][this.column][this.level] = color;
    }

    public boolean isOccupied(int row, int col, int level) {
        return coordinate[row][col][level] != null;
    }

    /*public void placeBead(Player player) {
        if (!isOccupied() && player.hasBeadsLeft()) {
            this.bead = player.getBead();  // ✅ Use player's bead
            this.owner = player;
        }
    }*/

   /*public Player getOwner() {
        return owner;
    }*/

    public String toString() {
        return this.row + "," + this.column + "," + this.level;
    }

    public static String getColor(int row, int column, int level) {
        return coordinate[row][column][level];
    }

    /**
     * Initialize the coordinates array with the provided board
     * @param board The game board to initialize with
     */
    public static void initializeCoordinates(String[][][] board) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    coordinate[i][j][k] = board[i][j][k];
                }
            }
        }
    }

    /*public void placeBead(int row, int column, int level) {
        coordinate[row][column][level] = this.color;
    }*/
}
