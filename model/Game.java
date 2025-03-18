package model;

import java.util.*;

public class Game {
    private static String[][][] board = new String[4][4][4]; // Pegs with max 4 beads each
    private static char currentTurn = 'R';
    private static Random random = new Random();

    public static void main(String[] args) {
        initializeBoard();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard();

            if (currentTurn == 'R') {
                System.out.println("Player " + currentTurn + "'s turn. Enter row (0-3) and column (0-3):");
                playerMove(scanner);
            } else {
                System.out.println("AI's turn (B)...");
                aiMove();
            }

            if (checkWin()) {
                printBoard();
                System.out.println("Player " + currentTurn + " wins!");
                break;
            }
            switchTurn();
        }
        scanner.close();
    }

    private static void initializeBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    board[i][j][k] = " ";
                }
            }
        }
    }

    private static void printBoard() {
        for (int lvl = 3; lvl >= 0; lvl--) { // Print from top to bottom
            System.out.println("Level " + lvl + ":");
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    System.out.print("[" + board[row][col][lvl] + "] ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < 4 && col >= 0 && col < 4 && board[row][col][3].equals(" ");
    }

    private static void playerMove(Scanner scanner) {
        int row, col;
        while (true) {
            try {
                System.out.print("Row: ");
                row = scanner.nextInt();
                System.out.print("Column: ");
                col = scanner.nextInt();

                if (isValidMove(row, col)) {
                    placeBead(row, col);
                    break;
                } else {
                    System.out.println("Invalid move. The peg is full or out of bounds. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter numbers between 0-3.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static void aiMove() {
        int row, col;
        do {
            row = random.nextInt(4);
            col = random.nextInt(4);
        } while (!isValidMove(row, col));

        placeBead(row, col);
        System.out.println("AI placed at row " + row + ", column " + col);
    }

    private static void placeBead(int row, int col) {
        for (int lvl = 0; lvl < 4; lvl++) {
            if (board[row][col][lvl].equals(" ")) {
                board[row][col][lvl] = String.valueOf(currentTurn);
                break;
            }
        }
    }

    private static void switchTurn() {
        currentTurn = (currentTurn == 'R') ? 'B' : 'R';
    }

    private static boolean checkWin() {
        return checkLine();
    }

    private static boolean checkLine() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (isLine(board[row][col][0], board[row][col][1], board[row][col][2], board[row][col][3])) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isLine(String a, String b, String c, String d) {
        return !a.equals(" ") && a.equals(b) && a.equals(c) && a.equals(d);
    }
}
