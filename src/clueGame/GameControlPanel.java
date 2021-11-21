package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
    Board board = Board.getInstance();
    private static HumanPlayer player;

    private int roll;
    private static String whoseTurn = "No player yet";
    private String resultString = "So you have nothing";
    private String guessString = "I have no guess";
    private Color color;

    private JLabel whoseTurnField = new JLabel();
    private JLabel diceRoll = new JLabel();

    public GameControlPanel() {
        createLayout();
    }

    public void createLayout() {

        player = board.getHumanPlayer();
        whoseTurn = board.getPlayerList().get(board.getCurrentPlayerIndex()).getName();

        JPanel controlPanel = new JPanel(new GridLayout(2, 0)); // Control panel at the bottom

        // --- Buttons and Whose Turn ---
        JPanel topControl = new JPanel(new GridLayout(1, 3)); // Top row with 3 columns

        // Whose Turn
        JPanel whoseTurnPanel = new JPanel();
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

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board.getInstance().nextPlayer();
            }
        });
        topControl.add(nextButton);
        JButton accuseButton = new JButton("Make Accusation");
        accuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuggestionPanel panel = new SuggestionPanel(true);
                panel.setVisible(true);
            }
        });
        topControl.add(accuseButton);

        // --- Dice Roll and Guess ---
        JPanel bottomControl = new JPanel(new GridLayout(1, 3)); // Bottom row with 3 columns
        // Dice rolls
        JPanel dicePanel = new JPanel(new GridLayout(1, 2));
        dicePanel.setBorder(new TitledBorder("Dice"));
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

        add(controlPanel, BorderLayout.SOUTH);
    }

    public void setTurn(Player p, int roll) {
        whoseTurn = p.getName();
        this.roll = roll;
        whoseTurnField.setText(whoseTurn);
        whoseTurnField.setBackground(p.getColor());
        whoseTurnField.setOpaque(true);

        diceRoll.setText(Integer.toString(roll));

    }

    public void setGuess(String guess) {
        guessString = guess;
    }

    public void setGuessResult(String result) {
        resultString = result;
    }
}