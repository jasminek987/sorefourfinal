package model;

public class Bead {
    private char symbol;// 'R' for Red, 'B' for Blue
    private String color;
    private int row;
    private int column;
    private int level;
    private Position position;


    Bead(int row, int column, int level, String color) {

        this.color = color;
        this.row = row;
        this.column = column;
        this.level = level;
        this.position = new Position(row, column, level, color);
        if (color.equals("red")){this.symbol = 'R';}
        else if (color.equals("blue")){this.symbol = 'B';}
    }

    /*public Bead(char symbol) {
        this.symbol = symbol;
    }*/

    public String getColor(){
        return color;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public int getLevel(){
        return level;
    }

    public Position getPosition(){
        return position;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return String.valueOf((char)symbol);
    }
}
