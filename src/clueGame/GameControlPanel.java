package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel {

    // private String playerName = "First Player", guess = "First guess",
    // guessResult = "First guess result";
    private int roll;
    private JLabel labelTurn;
    private JLabel labelRoll;
    private JLabel labelGuess;
    private JLabel labelGuessResult;
    private Color color;

    public GameControlPanel() {
        super();

        JPanel newPanel = new JPanel(new GridBagLayout());
        JLabel label;
        JButton button;
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        label = new JLabel("Whose turn is it?");
        newPanel.add(label, constraints);

        constraints.gridy = 1;
        labelTurn = new JLabel("firstPlayer");
        // TODO: THIS SHIT
        labelTurn.setOpaque(true);
        labelTurn.setBackground(color);
        System.out.println("yuhh" + color);
        newPanel.add(labelTurn, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        label = new JLabel("Roll");
        newPanel.add(label, constraints);

        constraints.gridy = 1;
        labelRoll = new JLabel(String.valueOf(roll));
        newPanel.add(labelRoll, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        button = new JButton("Make Accusation");
        newPanel.add(button, constraints);

        constraints.gridx = 3;
        button = new JButton("NEXT!");
        newPanel.add(button, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 2;
        labelGuess = new JLabel("firstGuess");
        newPanel.add(labelGuess, constraints);

        labelGuess.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Guess"));

        constraints.gridx = 2;
        constraints.gridy = 2;
        labelGuessResult = new JLabel("firstGuessResult");
        newPanel.add(labelGuessResult, constraints);

        labelGuessResult
                .setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Guess Result"));

        add(newPanel);
    }

    public void setTurn(Player p, int roll) {
        this.labelTurn.setText(p.getName());
        this.labelRoll.setText(String.valueOf(roll));
        color = p.getColor();
        System.out.println(p.getColor());
    }

    public void setGuess(String guess) {
        this.labelGuess.setText(guess);
    }

    public void setGuessResult(String guessResult) {
        this.labelGuessResult.setText(guessResult);
    }

    public static void main(String[] args) {
        GameControlPanel panel = new GameControlPanel(); // create the panel
        JFrame frame = new JFrame(); // create the frame
        frame.setContentPane(panel); // put the panel in the frame
        frame.setSize(750, 180); // size the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
        frame.setVisible(true); // make it visible

        // test filling in the data
        panel.setTurn(new ComputerPlayer("Col. Mustard", "Orange", 1, 1), 5);
        panel.setGuess("I have no guess!");
        panel.setGuessResult("So you have nothing?");
    }
}