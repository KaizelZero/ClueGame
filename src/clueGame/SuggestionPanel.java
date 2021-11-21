package clueGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuggestionPanel extends JDialog implements ActionListener {

    private String roomText;

    private JPanel mainPanel = new JPanel(new GridLayout(0, 2));
    private JPanel leftPanel = new JPanel(new GridLayout(4, 0));
    private JPanel rightPanel = new JPanel(new GridLayout(4, 0));

    private JPanel personDrop;
    private JPanel weaponDrop;
    private JPanel roomDrop;

    private JLabel roomLabel;
    private JLabel personLabel = new JLabel("Person");
    private JLabel weaponLabel = new JLabel("Weapon");

    private JComboBox<String> personBox;
    private JComboBox<String> weaponBox;
    private JComboBox<String> roomBox;

    private JLabel currentRoom;

    private static Board board = Board.getInstance();
    private static HumanPlayer player;

    private boolean isAccusation;

    public SuggestionPanel(Boolean bool) {
        this.isAccusation = bool;
        player = board.getHumanPlayer();
        setSize(400, 300);
        createLayout();
    }

    private void createLayout() {
        personDrop = new JPanel();
        weaponDrop = new JPanel();
        roomDrop = new JPanel();

        roomLabel = new JLabel();

        roomBox = new JComboBox<String>();
        personBox = new JComboBox<String>();
        weaponBox = new JComboBox<String>();

        if (isAccusation) {
            setTitle("Make an Accusation");
            for (Card c : board.getDeck()) {
                switch (c.getType()) {
                case ROOM:
                    roomBox.addItem(c.getCardName());
                    break;
                case PERSON:
                    personBox.addItem(c.getCardName());
                    break;
                case WEAPON:
                    weaponBox.addItem(c.getCardName());
                    break;
                }
            }
            personDrop.add(personBox);
            weaponDrop.add(weaponBox);
            roomDrop.add(roomBox);

            roomLabel.setText("Room");

        } else {
            setTitle("Make a Suggestion");
            roomLabel = new JLabel();
            roomText = board.getHumanPlayer().getLocation().getCellRoom().getName();
            currentRoom = new JLabel(roomText);
            for (Card c : board.getDeck()) {
                switch (c.getType()) {
                case PERSON:
                    personBox.addItem(c.getCardName());
                    break;
                case WEAPON:
                    weaponBox.addItem(c.getCardName());
                    break;
                case ROOM:
                    break;
                }
            }
            roomLabel.setText("Current Room");

            personDrop.add(personBox);
            weaponDrop.add(weaponBox);
            roomDrop.add(currentRoom);
        }

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);

        leftPanel.add(roomLabel);
        leftPanel.add(personLabel);
        leftPanel.add(weaponLabel);
        leftPanel.add(submitButton);

        rightPanel.add(roomDrop);
        rightPanel.add(personDrop);
        rightPanel.add(weaponDrop);
        rightPanel.add(cancelButton);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        setResizable(false);
        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
        case "Submit":
            Card accusePlayer, accuseRoom, accuseWeapon;

            if (isAccusation) {
                accuseRoom = new Card(roomBox.getSelectedItem().toString(), CardType.ROOM);
            } else {
                accuseRoom = new Card(roomText, CardType.ROOM);
            }
            accusePlayer = new Card(personBox.getSelectedItem().toString(), CardType.PERSON);
            accuseWeapon = new Card(weaponBox.getSelectedItem().toString(), CardType.WEAPON);
            if (isAccusation) {
                board.handleSuggestion(player, accusePlayer, accuseRoom, accuseWeapon);
                setVisible(false);
            } else {
                board.checkAccusation(accusePlayer, accuseRoom, accuseWeapon);
            }

            break;
        case "Cancel":
            setVisible(false);
            break;
        }

    }

}
