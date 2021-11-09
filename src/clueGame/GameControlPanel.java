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
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JFrame {

    private static Board board;
    private static HumanPlayer player;
    private static ArrayList<Card> playerCards;
    private static ArrayList<Card> seenCards;

    private int roll;
    private static String whoseTurn = "No player yet";
    private String resultString = "So you have nothing";
    private String guessString = "I have no guess";
    private Color color;

    public GameControlPanel() {
        setTitle("Clue Game");
        setSize(750, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createLayout();
    }

    public void createLayout() {

        Dimension textbox = new Dimension(170, 20);

        JPanel mainPanel = new JPanel(new BorderLayout()); // Whole GUI
        JPanel controlPanel = new JPanel(new GridLayout(2, 0)); // Control panel at the bottom
        JPanel cardPanel = new JPanel(new GridLayout(3, 0)); // Shows cards
        JPanel boardPanel = new JPanel(new BorderLayout()); // Shows Game Board
        // set panel sizes
        boardPanel.setMaximumSize(new Dimension(600, 600));
        boardPanel.setMinimumSize(new Dimension(600, 600));
        boardPanel.setPreferredSize(new Dimension(600, 600));
        cardPanel.setPreferredSize(new Dimension(190, 680));

        // --- Buttons and Whose Turn ---
        JPanel topControl = new JPanel(new GridLayout(1, 3)); // Top row with 3 columns

        // Whose Turn
        JPanel whoseTurnPanel = new JPanel();
        JLabel whoseTurnField = new JLabel();
        whoseTurnPanel.setBorder(new TitledBorder("Whose Turn"));
        whoseTurnField.setText(whoseTurn);
        whoseTurnField.setBackground(player.getColor());
        whoseTurnField.setOpaque(true);
        whoseTurnField.setPreferredSize(new Dimension(170, 35));
        whoseTurnField.setHorizontalAlignment(JLabel.CENTER);
        whoseTurnPanel.add(whoseTurnField);
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
        JLabel diceRoll = new JLabel();
        diceRoll.setText(String.valueOf(roll));
        dicePanel.add(diceRoll);
        bottomControl.add(dicePanel);

        // Guess
        JPanel guessPanel = new JPanel(new GridLayout(1, 2));
        guessPanel.setBorder(new TitledBorder("Guess"));
        JLabel guess = new JLabel(guessString);
        guessPanel.add(guess);

        JPanel guessResultPanel = new JPanel(new GridLayout(1, 2));
        guessResultPanel.setBorder(new TitledBorder("Guess Result"));
        JLabel guessResult = new JLabel(resultString);
        guessResultPanel.add(guessResult);
        bottomControl.add(guessPanel);
        bottomControl.add(guessResultPanel);

        controlPanel.add(topControl);
        controlPanel.add(bottomControl);

        // Cards Section
        cardPanel.setBorder(new TitledBorder("Known Cards"));
        JPanel peopleCardPanel = new JPanel();
        JPanel weaponCardPanel = new JPanel();
        JPanel roomCardPanel = new JPanel();

        // Prints In Hand Label, will not print if no cards in hand
        // Definately could get implemented better
        int personCount = 0, roomCount = 0, weaponCount = 0;
        for (Card type : playerCards) {
            if (type.getType() == CardType.PERSON && personCount == 0) {
                // People
                JLabel inHandPeople = new JLabel();
                peopleCardPanel.setBorder(new TitledBorder("People"));
                inHandPeople.setText("In Hand:");
                inHandPeople.setPreferredSize(textbox);
                peopleCardPanel.add(inHandPeople);
                cardPanel.add(peopleCardPanel, BorderLayout.CENTER);
                personCount++;
            }
            // Rooms
            if (type.getType() == CardType.ROOM && roomCount == 0) {
                JLabel inHandRoom = new JLabel();
                roomCardPanel.setBorder(new TitledBorder("Rooms"));
                inHandRoom.setText("In Hand:");
                inHandRoom.setPreferredSize(textbox);
                roomCardPanel.add(inHandRoom);
                cardPanel.add(roomCardPanel, BorderLayout.SOUTH);
                roomCount++;
            }
            // Weapons
            if (type.getType() == CardType.WEAPON && weaponCount == 0) {
                JLabel inHandWeapon = new JLabel();
                weaponCardPanel.setBorder(new TitledBorder("Weapons"));
                inHandWeapon.setText("In Hand:");
                inHandWeapon.setPreferredSize(textbox);
                weaponCardPanel.add(inHandWeapon);
                cardPanel.add(weaponCardPanel, BorderLayout.NORTH);
                weaponCount++;
            }
        }

        // Adds Cards in hand
        for (Card c : playerCards) {
            switch (c.getType()) {
            case PERSON:
                JLabel peopleText = new JLabel();
                peopleText.setText(c.getCardName().toString());
                peopleText.setBackground(player.getColor());
                peopleText.setOpaque(true);
                peopleText.setPreferredSize(textbox);
                peopleText.setHorizontalAlignment(JLabel.CENTER);
                peopleCardPanel.add(peopleText);
                break;
            case WEAPON:
                JLabel weaponText = new JLabel();
                weaponText.setText(c.getCardName().toString());
                weaponText.setBackground(player.getColor());
                weaponText.setOpaque(true);
                weaponText.setPreferredSize(textbox);
                weaponText.setHorizontalAlignment(JLabel.CENTER);
                weaponCardPanel.add(weaponText);
                break;
            case ROOM:
                JLabel roomText = new JLabel();
                roomText.setText(c.getCardName().toString());
                roomText.setBackground(player.getColor());
                roomText.setOpaque(true);
                roomText.setPreferredSize(textbox);
                roomText.setHorizontalAlignment(JLabel.CENTER);
                roomCardPanel.add(roomText);
                break;
            }
        }

        // Weapons
        JLabel seenWeapon = new JLabel();
        weaponCardPanel.setBorder(new TitledBorder("Weapons"));
        seenWeapon.setText("Seen:");
        seenWeapon.setPreferredSize(textbox);
        weaponCardPanel.add(seenWeapon);
        cardPanel.add(weaponCardPanel, BorderLayout.NORTH);

        // People
        JLabel seenPeople = new JLabel();
        peopleCardPanel.setBorder(new TitledBorder("People"));
        seenPeople.setText("Seen:");
        seenPeople.setPreferredSize(textbox);
        peopleCardPanel.add(seenPeople);
        cardPanel.add(peopleCardPanel, BorderLayout.CENTER);

        // Rooms
        JLabel seenRoom = new JLabel();
        roomCardPanel.setBorder(new TitledBorder("Rooms"));
        seenRoom.setText("Seen:");
        seenRoom.setPreferredSize(textbox);
        roomCardPanel.add(seenRoom);
        cardPanel.add(roomCardPanel, BorderLayout.SOUTH);

        // Adds Cards in hand
        for (Card x : seenCards) {
            switch (x.getType()) {
            case PERSON:
                JLabel peopleText = new JLabel();
                peopleText.setText(x.getCardName().toString());
                peopleText.setBackground(player.getColor());
                peopleText.setOpaque(true);
                peopleText.setPreferredSize(textbox);
                peopleText.setHorizontalAlignment(JLabel.CENTER);
                peopleCardPanel.add(peopleText);
                break;
            case WEAPON:
                JLabel weaponText = new JLabel();
                weaponText.setText(x.getCardName().toString());
                weaponText.setBackground(player.getColor());
                weaponText.setOpaque(true);
                weaponText.setPreferredSize(textbox);
                weaponText.setHorizontalAlignment(JLabel.CENTER);
                weaponCardPanel.add(weaponText);
                break;
            case ROOM:
                JLabel roomText = new JLabel();
                roomText.setText(x.getCardName().toString());
                roomText.setBackground(player.getColor());
                roomText.setOpaque(true);
                roomText.setPreferredSize(textbox);
                roomText.setHorizontalAlignment(JLabel.CENTER);
                roomCardPanel.add(roomText);
                break;
            }
        }

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
        seenCards = player.getSeen();
        whoseTurn = board.getPlayerList().get(board.getCurrentPlayerIndex()).getName();

        GameControlPanel panel = new GameControlPanel(); // create the panel
        panel.setVisible(true);
    }
}