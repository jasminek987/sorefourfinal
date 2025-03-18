package model;

public class Referee {

    private String winningLine;
    private String winningPlayer;
    private String winningLineType;
    private Bead bead;
    private Position position;

    public boolean isLine(String a, String b, String c, String d){
        // Check that all positions have values, are not null, and are not empty spaces
        if(a != null && b != null && c != null && d != null && 
           !a.equals(" ") && !b.equals(" ") && !c.equals(" ") && !d.equals(" ")){
            return a.equals(b) && a.equals(c) && a.equals(d);
        }
        else
            return false;
    }

    public boolean checkLine(){

        verticalLine();
        horizontalLine();
        singlySkewedLine();
        doublySkewedLine();
        return verticalLine() || horizontalLine() || singlySkewedLine() || doublySkewedLine();

    }

    private boolean verticalLine() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (isLine(Position.getColor(row, col, 0),
                        Position.getColor(row, col, 1),
                        Position.getColor(row, col, 2),
                        Position.getColor(row, col, 3))) {
                    winningLine = "(" + row + ", " + col + ", 0) (" + row + ", " + col + ", 1) (" + row + ", " + col + ", 2) (" + row + ", " + col + ", 3)";
                    winningLineType = "Vertical";
                    return true;
                }
            }
        }
        return false;
    }

    private boolean horizontalLine(){
        for (int row=0; row<4; row++){
            for(int lev=0; lev<4; lev++){
                if(isLine(Position.getColor(row,0,lev),
                        Position.getColor(row,1,lev),
                        Position.getColor(row,2,lev),
                        Position.getColor(row,3,lev))){
                    winningLine = "("+row+", 0, "+lev+") ("+row+", 1, "+lev+") ("+row+", 2, "+lev+") ("+row+", 3, "+lev+")";
                    winningLineType = "Horizontal";
                    return true;
                }
            }
        }
        for (int col=0; col<4; col++){
            for(int lev=0; lev<4; lev++){
                if(isLine(Position.getColor(0,col,lev),
                        Position.getColor(1,col,lev),
                        Position.getColor(2,col,lev),
                        Position.getColor(3,col,lev))){
                    winningLine = "(0, "+col+", "+lev+") (1, "+col+", "+lev+") (2, "+col+", "+lev+") (3, "+col+", "+lev+")";
                    winningLineType = "Horizontal";
                    return true;
                }
            }
        }
        return false;
    }

    private boolean singlySkewedLine(){
        for(int lev=0; lev<4; lev++){
            if(isLine(Position.getColor(0,0,lev), Position.getColor(1,1,lev), Position.getColor(2,2,lev), Position.getColor(3,3,lev))){
                winningLine = "(0, 0, "+lev+") (1, 1, "+lev+") (2, 2, "+lev+") (3, 3, "+lev+")";
                winningLineType = "Singly Skewed";
                return true;

            }
            else if(isLine(Position.getColor(3,0,lev), Position.getColor(2,1,lev), Position.getColor(1,2,lev), Position.getColor(0,3,lev))){
                winningLine = "(3, 0, "+lev+") (2, 1, "+lev+") (1, 2, "+lev+") (0, 3, "+lev+")";
                winningLineType = "Singly Skewed";
                return true;
            }
        }
        for(int row=0; row<4; row++){
            if(isLine(Position.getColor(row,0,0), Position.getColor(row,1,1), Position.getColor(row,2,2), Position.getColor(row,3,3))){
                winningLine = "("+row+", 0, 0) ("+row+", 1, 1) ("+row+", 2, 2) ("+row+", 3, 3)";
                winningLineType = "Singly Skewed";
                return true;
            }
            else if(isLine(Position.getColor(row,3,0), Position.getColor(row,2,1), Position.getColor(row,1,2), Position.getColor(row,0,3))){
                winningLine = "("+row+", 3, 0) ("+row+", 2, 1) ("+row+", 1, 2) ("+row+", 0, 3)";
                winningLineType = "Singly Skewed";
                return true;
            }
        }
        for(int col=0; col<4; col++){
            if(isLine(Position.getColor(0,col,0), Position.getColor(1,col,1), Position.getColor(2,col,2), Position.getColor(3,col,3))){
                winningLine = "(0, "+col+", 0) (1, "+col+", 1) (2, "+col+", 2) (3, "+col+", 3)";
                winningLineType = "Singly Skewed";
                return true;
            }
            else if(isLine(Position.getColor(3,col,0), Position.getColor(2,col,1), Position.getColor(1,col,2), Position.getColor(0,col,3))){
                winningLine = "(3, "+col+", 0) (2, "+col+", 1) (1, "+col+", 2) (0, "+col+", 3)";
                winningLineType = "Singly Skewed";
                return true;
            }
        }
        return false;
    }

    private boolean doublySkewedLine() {
        if (isLine(Position.getColor(0, 0, 0), Position.getColor(1, 1, 1), Position.getColor(2, 2, 2), Position.getColor(3, 3, 3))) {
            winningLine = "(0, 0, 0) (1, 1, 1) (2, 2, 2) (3, 3, 3)";
            winningLineType = "Doubly Skewed";
            return true;
        } else if (isLine(Position.getColor(0, 3, 0), Position.getColor(1, 2, 1), Position.getColor(2, 1, 2), Position.getColor(3, 0, 3))) {
            winningLine = "(0, 3, 0) (1, 2, 1) (2, 1, 2) (3, 0, 3)";
            winningLineType = "Doubly Skewed";
            return true;
        } else if (isLine(Position.getColor(3, 0, 0), Position.getColor(2, 1, 1), Position.getColor(1, 2, 2), Position.getColor(0, 3, 3))) {
            winningLine = "(3, 0, 0) (2, 1, 1) (1, 2, 2) (0, 3, 3)";
            winningLineType = "Doubly Skewed";
            return true;
        } else if (isLine(Position.getColor(0, 0, 3), Position.getColor(1, 1, 2), Position.getColor(2, 2, 1), Position.getColor(3, 3, 0))) {
            winningLine = "(0, 0, 3) (1, 1, 2) (2, 2, 1) (3, 3, 0)";
            winningLineType = "Doubly Skewed";
            return true;
        }


        return false;
    }

    /**
     * Get the winning line type (Vertical, Horizontal, Singly Skewed, Doubly Skewed)
     * @return The type of line that won
     */
    public String getWinningLineType() {
        return winningLineType;
    }
    
    /**
     * Get the winning line coordinates
     * @return String representation of the winning line
     */
    public String getWinningLine() {
        return winningLine;
    }
    
    /**
     * Get the winning player
     * @return The player who won
     */
    public String getWinningPlayer() {
        return winningPlayer;
    }
    
    /**
     * Set the winning player
     * @param player The player who won
     */
    public void setWinningPlayer(String player) {
        this.winningPlayer = player;
    }
}