package model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.Random;

public class GameGui extends JFrame {
    private static final int SIZE = 4;
    private static String[][][] board = new String[SIZE][SIZE][SIZE];
    private static char currentTurn = 'R';
    private static Random random = new Random();
    private static final int PEG_HEIGHT = 150;
    private static final int BEAD_RADIUS = 15;
    private boolean vsAI;
    private Referee referee;
    private JLabel statusLabel;
    private JPanel gamePanel;
    // Add hover position tracking
    private int hoverRow = -1;
    private int hoverCol = -1;
    
    public GameGui(boolean vsAI) {
        this.vsAI = vsAI;
        this.referee = new Referee();
        
        setTitle("Score4 Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create status panel at the top
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.BLACK);
        statusLabel = new JLabel("Player RED's turn");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setForeground(Color.RED);
        statusPanel.add(statusLabel);
        
        // Create game panel in the center
        gamePanel = new GamePanel();
        gamePanel.setBackground(Color.BLACK);
        
        // Add components to frame
        setLayout(new BorderLayout());
        add(statusPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        newGameItem.addActionListener(e -> resetGame());
        exitItem.addActionListener(e -> {
            dispose();
            new MainMenu();
        });
        
        gameMenu.add(newGameItem);
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
        
        initializeBoard();
        setVisible(true);
        
        // Start with a coin toss to determine first player
        performCoinToss();
    }
    
    private void performCoinToss() {
        Toss toss = new Toss();
        currentTurn = toss.flipCoin();
        
        // Show the toss result to the player with custom styling
        String winner = (currentTurn == 'R') ? "RED" : "BLUE";
        Color winnerColor = (currentTurn == 'R') ? Color.RED : Color.BLUE;
        
        // Create custom label with icon and styled text
        JLabel messageLabel = new JLabel("Coin toss result: " + winner + " goes first!");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setForeground(winnerColor);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Create custom panel for the message
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(messageLabel, BorderLayout.CENTER);
        messagePanel.setBackground(Color.WHITE);
        
        // Show custom dialog
        JOptionPane.showMessageDialog(this,
            messagePanel,
            "Coin Toss",
            JOptionPane.PLAIN_MESSAGE,
            null);
        
        updateStatusLabel();
        
        if (currentTurn == 'B' && vsAI) {
            SwingUtilities.invokeLater(this::aiMove);
        }
    }
    
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    board[i][j][k] = " ";
                }
            }
        }
        // Update Position class with the board
        Position.initializeCoordinates(board);
    }
    
    private void handleWin(String winner, String lineType) {
        // Format line type to be more readable
        String readableLineType = lineType.toLowerCase()
            .replace("skewed", "diagonal")
            .replace("_", " ");
        
        // Determine winner color and text
        Color winnerColor = winner.equals("R") ? Color.RED : Color.BLUE;
        String winnerText = winner.equals("R") ? "Red Player" : 
                           winner.equals("B") ? "Blue Player" : 
                           winner;
        
        // Create styled message label
        JLabel messageLabel = new JLabel(winnerText + " wins with a " + readableLineType + " line!");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setForeground(winnerColor);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Create custom panel for the message
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(messageLabel, BorderLayout.CENTER);
        messagePanel.setBackground(Color.WHITE);
        
        // Create custom buttons with consistent styling
        JButton playAgainButton = new JButton("Play Again");
        JButton exitButton = new JButton("Exit");
        
        // Style the buttons
        for (JButton button : new JButton[]{playAgainButton, exitButton}) {
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setFocusPainted(false);
            button.setBorderPainted(true);
            button.setContentAreaFilled(true);
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        }
        
        // Style specific buttons
        playAgainButton.setBackground(new Color(46, 204, 113));
        playAgainButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(231, 76, 60));
        exitButton.setForeground(Color.WHITE);
        
        // Create button panel with spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);
        
        // Add buttons to message panel
        messagePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Create and show custom dialog
        JDialog dialog = new JDialog(this, "Game Over", true);
        dialog.setContentPane(messagePanel);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Add button actions
        playAgainButton.addActionListener(e -> {
            dialog.dispose();
            resetGame();
        });
        
        exitButton.addActionListener(e -> {
            dialog.dispose();
            dispose();
            new MainMenu();
        });
        
        // Size and position dialog
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void handleClick(int x, int y) {
        if (vsAI && currentTurn == 'B') {
            return;
        }

        int spacingX = getWidth() / (SIZE + 2);
        int spacingY = getHeight() / (SIZE + 2);
        int perspectiveShiftX = -40;
        int perspectiveShiftY = 20;
        
        // Calculate total board width to center it
        int totalWidth = (SIZE - 1) * perspectiveShiftX + (SIZE + 1) * spacingX;
        int startX = (getWidth() - totalWidth) / 2;
        
        double closestDistance = Double.MAX_VALUE;
        int selectedRow = -1;
        int selectedCol = -1;
        
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int baseX = startX + (col + 1) * spacingX + (row * perspectiveShiftX);
                int baseY = (row + 1) * spacingY + (col * perspectiveShiftY);
                
                // Calculate horizontal distance
                double horizontalDistance = Math.abs(x - baseX);
                
                // Calculate vertical distance considering the entire peg height
                double verticalDistance = 0;
                if (y < baseY - PEG_HEIGHT / 2) {
                    verticalDistance = baseY - PEG_HEIGHT / 2 - y;
                } else if (y > baseY + PEG_HEIGHT / 2) {
                    verticalDistance = y - (baseY + PEG_HEIGHT / 2);
                }
                
                // Combined distance (weighted to prefer horizontal accuracy)
                double distance = Math.sqrt(horizontalDistance * horizontalDistance + 0.5 * verticalDistance * verticalDistance);
                
                if (distance < closestDistance && distance < BEAD_RADIUS * 4) {
                    closestDistance = distance;
                    selectedRow = row;
                    selectedCol = col;
                }
            }
        }
        
        if (selectedRow != -1 && selectedCol != -1) {
            boolean isFull = true;
            for (int lvl = 0; lvl < SIZE; lvl++) {
                if (board[selectedRow][selectedCol][lvl].equals(" ")) {
                    isFull = false;
                    break;
                }
            }
            
            if (isFull) {
                JOptionPane.showMessageDialog(this, "This peg is full. Choose another one.", 
                    "Invalid Move", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (placeBead(selectedRow, selectedCol)) {
                repaint();
                
                if (referee.checkLine()) {
                    handleWin(String.valueOf(currentTurn), referee.getWinningLineType());
                    return;
                }
                
                switchTurn();
                
                if (vsAI && currentTurn == 'B') {
                    SwingUtilities.invokeLater(this::aiMove);
                }
            }
        }
    }
    
    private boolean placeBead(int row, int col) {
        for (int lvl = 0; lvl < SIZE; lvl++) {
            if (board[row][col][lvl].equals(" ")) {
                board[row][col][lvl] = String.valueOf(currentTurn);
                // Update Position with this move
                new Position(row, col, lvl, String.valueOf(currentTurn));
                return true;
            }
        }
        return false;
    }
    
    private void aiMove() {
        // Small delay to make AI move visible to the player
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simple AI strategy: try to find winning move, then random
        boolean moveMade = false;
        
        // Make a random move
        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (!placeBead(row, col));
        
        repaint();
        
        // Check if the AI won after making its move
        if (referee.checkLine()) {
            handleWin("B", referee.getWinningLineType());
            return;
        }
        
        switchTurn();
    }
    
    private boolean canPlaceInColumn(int col) {
        // Check if there's space in this column
        for (int row = 0; row < SIZE; row++) {
            for (int lvl = 0; lvl < SIZE; lvl++) {
                if (board[row][col][lvl].equals(" ")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void undoLastMove(int row, int col) {
        // Find the highest placed bead in this column and remove it
        for (int lvl = SIZE - 1; lvl >= 0; lvl--) {
            if (!board[row][col][lvl].equals(" ")) {
                board[row][col][lvl] = " ";
                return;
            }
        }
    }
    
    private void switchTurn() {
        currentTurn = (currentTurn == 'R') ? 'B' : 'R';
        updateStatusLabel();
    }
    
    private void updateStatusLabel() {
        String playerText = currentTurn == 'R' ? "RED" : "BLUE";
        if (vsAI && currentTurn == 'B') {
            statusLabel.setText("AI's turn (" + playerText + ")");
        } else {
            statusLabel.setText("Player " + playerText + "'s turn");
        }
        statusLabel.setForeground(currentTurn == 'R' ? Color.RED : Color.BLUE);
    }
    
    private void resetGame() {
        // Clear the board first
        initializeBoard();
        // Repaint to show cleared board
        repaint();
        // Small delay before coin toss
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(200);
                performCoinToss();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    // Inner class for the game panel
    private class GamePanel extends JPanel {
        public GamePanel() {
            setBackground(Color.BLACK);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    handleClick(e.getX(), e.getY());
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    hoverRow = -1;
                    hoverCol = -1;
                    repaint();
                }
            });
            
            // Add mouse motion listener for hover effect
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    updateHoverPosition(e.getX(), e.getY());
                }
            });
        }
        
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            
            int spacingX = getWidth() / (SIZE + 2);
            int spacingY = getHeight() / (SIZE + 2);
            int perspectiveShiftX = -40;
            int perspectiveShiftY = 20;
            
            // Calculate total board width to center it
            int totalWidth = (SIZE - 1) * perspectiveShiftX + (SIZE + 1) * spacingX;
            int startX = (getWidth() - totalWidth) / 2;
            
            // Draw the tilted board grid
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    // Calculate base position with perspective, centered
                    int baseX = startX + (col + 1) * spacingX + (row * perspectiveShiftX);
                    int baseY = (row + 1) * spacingY + (col * perspectiveShiftY);
                    
                    // Draw the vertical peg with perspective
                    g2.setStroke(new BasicStroke(2.0f));
                    g2.draw(new Line2D.Double(baseX, baseY - PEG_HEIGHT / 2, baseX, baseY + PEG_HEIGHT / 2));
                    
                    // Draw existing beads with perspective
                    for (int lvl = 0; lvl < SIZE; lvl++) {
                        if (!board[row][col][lvl].equals(" ")) {
                            g2.setColor(board[row][col][lvl].equals("R") ? Color.RED : Color.BLUE);
                            g2.fillOval(baseX - BEAD_RADIUS, 
                                      baseY + PEG_HEIGHT / 2 - (lvl + 1) * (BEAD_RADIUS * 2), 
                                      BEAD_RADIUS * 2, 
                                      BEAD_RADIUS * 2);
                            g2.setColor(Color.WHITE);
                        }
                    }
                    
                    // Draw hover preview
                    if (row == hoverRow && col == hoverCol) {
                        for (int lvl = 0; lvl < SIZE; lvl++) {
                            if (board[row][col][lvl].equals(" ")) {
                                Color hoverColor = currentTurn == 'R' ? Color.RED : Color.BLUE;
                                g2.setColor(new Color(
                                    hoverColor.getRed() / 255f,
                                    hoverColor.getGreen() / 255f,
                                    hoverColor.getBlue() / 255f,
                                    0.4f));
                                
                                g2.fillOval(baseX - BEAD_RADIUS,
                                          baseY + PEG_HEIGHT / 2 - (lvl + 1) * (BEAD_RADIUS * 2),
                                          BEAD_RADIUS * 2,
                                          BEAD_RADIUS * 2);
                                break;
                            }
                        }
                        g2.setColor(Color.WHITE);
                    }
                }
            }
        }
        
        private void updateHoverPosition(int x, int y) {
            if (vsAI && currentTurn == 'B') {
                hoverRow = -1;
                hoverCol = -1;
                return;
            }
            
            int spacingX = getWidth() / (SIZE + 2);
            int spacingY = getHeight() / (SIZE + 2);
            int perspectiveShiftX = -40;
            int perspectiveShiftY = 20;
            
            // Calculate total board width to center it
            int totalWidth = (SIZE - 1) * perspectiveShiftX + (SIZE + 1) * spacingX;
            int startX = (getWidth() - totalWidth) / 2;
            
            int oldHoverRow = hoverRow;
            int oldHoverCol = hoverCol;
            
            double closestDistance = Double.MAX_VALUE;
            hoverRow = -1;
            hoverCol = -1;
            
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    int baseX = startX + (col + 1) * spacingX + (row * perspectiveShiftX);
                    int baseY = (row + 1) * spacingY + (col * perspectiveShiftY);
                    
                    // Calculate horizontal distance
                    double horizontalDistance = Math.abs(x - baseX);
                    
                    // Calculate vertical distance considering the entire peg height
                    double verticalDistance = 0;
                    if (y < baseY - PEG_HEIGHT / 2) {
                        verticalDistance = baseY - PEG_HEIGHT / 2 - y;
                    } else if (y > baseY + PEG_HEIGHT / 2) {
                        verticalDistance = y - (baseY + PEG_HEIGHT / 2);
                    }
                    
                    // Combined distance (weighted to prefer horizontal accuracy)
                    double distance = Math.sqrt(horizontalDistance * horizontalDistance + 0.5 * verticalDistance * verticalDistance);
                    
                    if (distance < closestDistance && distance < BEAD_RADIUS * 4) {
                        boolean isFull = true;
                        for (int lvl = 0; lvl < SIZE; lvl++) {
                            if (board[row][col][lvl].equals(" ")) {
                                isFull = false;
                                break;
                            }
                        }
                        
                        if (!isFull) {
                            closestDistance = distance;
                            hoverRow = row;
                            hoverCol = col;
                        }
                    }
                }
            }
            
            if (oldHoverRow != hoverRow || oldHoverCol != hoverCol) {
                repaint();
            }
        }
    }
}