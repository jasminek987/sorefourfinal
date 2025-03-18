package model;
import java.util.Scanner;

public class HumanPlayer extends Player {

    private Scanner input;
    public HumanPlayer(char symbol){
        super(symbol);
        this.input= new Scanner(System.in);
    }
    @Override
    public int chooseMove(char [][] board){
        System.out.println("Enter column(0-3)");
        int column=input.nextInt();
        return column;
    }
    @Override
    public void displayRandomChoice(){
        System.out.println("This methos is not used for human players");
    }
}