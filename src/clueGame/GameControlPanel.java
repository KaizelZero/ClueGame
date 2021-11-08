package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JFrame {

    private static Board board;
    private static HumanPlayer player;
    private static ArrayList<Card> playerCards;

    private int roll;
    private static String whoseTurn = "No player yet";
    private String resultString = "So you have nothing";
    private String guessString = "I have no guess";
    private Color color;

    static Player p = new ComputerPlayer("Col. Mustard", "Orange", 1, 1);

    public GameControlPanel() {
        setTitle("Clue Game");
        setSize(750, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createLayout();
    }

    public void createLayout() {

        JPanel mainPanel = new JPanel(new BorderLayout()); // Whole GUI
        JPanel controlPanel = new JPanel(new GridLayout(2, 0)); // Control panel at the bottom
        JPanel cardPanel = new JPanel(new GridLayout(3, 0)); // Shows cards
        JPanel boardPanel = new JPanel(new BorderLayout()); // Shows Game Board
        // set panel sizes
        boardPanel.setMaximumSize(new Dimension(600, 600));
        boardPanel.setMinimumSize(new Dimension(600, 600));
        boardPanel.setPreferredSize(new Dimension(600, 600));
        cardPanel.setPreferredSize(new Dimension(150, 680));

        // --- Buttons and Whose Turn ---
        JPanel topControl = new JPanel(new GridLayout(1, 3)); // Top row with 3 columns

        // Whose Turn
        JPanel whoseTurnPanel = new JPanel();
        JTextField whoseTurnField = new JTextField();
        whoseTurnPanel.setBorder(new TitledBorder("Whose Turn"));
        whoseTurnField.setEditable(false);
        whoseTurnField.setText(whoseTurn);
        whoseTurnField.setBackground(p.getColor());
        topControl.add(whoseTurnPanel);

        // Buttons
        JButton nextButton = new JButton("Next Player");
        topControl.add(nextButton);
        JButton accuseButton = new JButton("Make Accusation");
        topControl.add(accuseButton);

        // --- Dice Roll and Guess ---
        JPanel bottomControl = new JPanel(new GridLayout(1, 3)); // Bottom row with 3 columns

        // Dice rolls
        JPanel dicePanel = new JPanel(new GridLayout(1, 2));
        dicePanel.setBorder(new TitledBorder("Dice"));
        JTextField diceRoll = new JTextField();
        diceRoll.setEditable(false);
        diceRoll.setText(String.valueOf(roll));
        bottomControl.add(dicePanel);

        // Guess
        JPanel guessPanel = new JPanel(new GridLayout(1, 2));
        guessPanel.setBorder(new TitledBorder("Guess"));
        JTextField guess = new JTextField(guessString);
        guess.setEditable(false);

        JPanel guessResultPanel = new JPanel(new GridLayout(1, 2));
        guessResultPanel.setBorder(new TitledBorder("Guess Result"));
        JTextField guessResult = new JTextField(resultString);
        guessResult.setEditable(false);
        guessResultPanel.add(guessResult);
        bottomControl.add(guessPanel);
        bottomControl.add(guessResultPanel);

        controlPanel.add(topControl);
        controlPanel.add(bottomControl);

        // Cards Section
        cardPanel.setBorder(new TitledBorder("Card Panel"));
        JPanel peopleCardPanel = new JPanel();
        JPanel weaponCardPanel = new JPanel();
        JPanel roomCardPanel = new JPanel();
        JLabel myCards = new JLabel("My Cards");

        // Weapons
        JTextField weaponsText = new JTextField();
        weaponCardPanel.setBorder(new TitledBorder("Weapons"));
        weaponsText.setText("weapon");
        weaponCardPanel.add(weaponsText);
        weaponsText.setEditable(false);
        cardPanel.add(weaponCardPanel, BorderLayout.NORTH);

        // People
        JTextField peopleText = new JTextField();
        peopleCardPanel.setBorder(new TitledBorder("People"));
        peopleText.setEditable(false);
        peopleText.setText("people");
        peopleCardPanel.add(peopleText);
        cardPanel.add(peopleCardPanel, BorderLayout.CENTER);

        // Rooms
        JTextField roomsText = new JTextField();
        roomCardPanel.setBorder(new TitledBorder("Rooms"));
        roomsText.setEditable(false);
        roomsText.setText("rooms");
        roomCardPanel.add(roomsText);
        cardPanel.add(roomCardPanel, BorderLayout.SOUTH);

        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(cardPanel, BorderLayout.EAST);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    public void setTurn(Player p, int roll) {
        whoseTurn = p.getName();
    }

    public void setGuess(String guess) {
        guessString = guess;
    }

    public void setGuessResult(String result) {
        resultString = result;
    }

    public static void main(String[] args) {
        board = Board.getInstance(); // Only creates one instance of the board
        board.setConfigFiles("bin/data/Clue Excel Diagram2.csv", "bin/data/ClueSetup.txt");
        board.initialize();

        player = board.getHumanPlayer();
        playerCards = player.getHand();

        GameControlPanel panel = new GameControlPanel(); // create the panel
        panel.setVisible(true);

        whoseTurn = board.getHumanPlayer().getName();
    }
}