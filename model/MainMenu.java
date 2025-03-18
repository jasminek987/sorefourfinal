package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {
    private static final long serialVersionUID = 1L;
    
    public MainMenu() {
        setTitle("Score4 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        
        JLabel titleLabel = new JLabel("SCORE 4");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton startButton = new JButton("Start Game");
        JButton exitButton = new JButton("Exit");
        
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        startButton.setMaximumSize(new Dimension(200, 50));
        exitButton.setMaximumSize(new Dimension(200, 50));
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGameModeSelection();
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Add components with spacing
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(startButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(exitButton);
        panel.add(Box.createVerticalGlue());
        
        add(panel);
        setVisible(true);
    }
    
    private void showGameModeSelection() {
        JFrame modeFrame = new JFrame("Select Game Mode");
        modeFrame.setSize(400, 300);
        modeFrame.setLocationRelativeTo(this);
        modeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        
        JLabel modeLabel = new JLabel("Select Game Mode");
        modeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        modeLabel.setForeground(Color.WHITE);
        modeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton humanButton = new JButton("Play with Human");
        JButton aiButton = new JButton("Play with AI");
        
        humanButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        aiButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        humanButton.setMaximumSize(new Dimension(200, 50));
        aiButton.setMaximumSize(new Dimension(200, 50));
        
        humanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modeFrame.dispose();
                startGame(false); // Human vs Human
            }
        });
        
        aiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modeFrame.dispose();
                startGame(true); // Human vs AI
            }
        });
        
        panel.add(Box.createVerticalGlue());
        panel.add(modeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(humanButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(aiButton);
        panel.add(Box.createVerticalGlue());
        
        modeFrame.add(panel);
        modeFrame.setVisible(true);
    }
    
    private void startGame(boolean vsAI) {
        dispose(); // Close the main menu
        new GameGui(vsAI);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainMenu();
            }
        });
    }
} 