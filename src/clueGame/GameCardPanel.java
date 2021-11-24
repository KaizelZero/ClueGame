package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GameCardPanel extends JPanel {
	Board board = Board.getInstance();
    private static HumanPlayer player;
    private static ArrayList<Card> playerCards;
    private static Map<Player, HashSet<Card>> seenCards;
    
    public GameCardPanel() {
    	createLayout();
    }
    
    public void createLayout() {
    	Dimension textbox = new Dimension(170, 20);
    	// Cards Section
        JPanel peopleCardPanel = new JPanel();
        JPanel weaponCardPanel = new JPanel();
        JPanel roomCardPanel = new JPanel();
        JPanel cardPanel = new JPanel(new GridLayout(3, 0)); // Shows cards
        cardPanel.setBorder(new TitledBorder("Known Cards"));
        cardPanel.setMinimumSize(new Dimension(190, 600));
        cardPanel.setPreferredSize(new Dimension(190, 680));
        
        player = board.getHumanPlayer();
        playerCards = player.getHand();
	    seenCards = player.getSeen();


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
        for (Entry<Player, HashSet<Card>> entry : seenCards.entrySet()) {
            for (Card c: entry.getValue()) {
            	switch (c.getType()) {
                case PERSON:
                    JLabel peopleText = new JLabel();
                    peopleText.setText(c.getCardName().toString());
                    peopleText.setBackground(entry.getKey().getColor());
                    peopleText.setOpaque(true);
                    peopleText.setPreferredSize(textbox);
                    peopleText.setHorizontalAlignment(JLabel.CENTER);
                    peopleCardPanel.add(peopleText);
                    break;
                case WEAPON:
                    JLabel weaponText = new JLabel();
                    weaponText.setText(c.getCardName().toString());
                    weaponText.setBackground(entry.getKey().getColor());
                    weaponText.setOpaque(true);
                    weaponText.setPreferredSize(textbox);
                    weaponText.setHorizontalAlignment(JLabel.CENTER);
                    weaponCardPanel.add(weaponText);
                    break;
                case ROOM:
                    JLabel roomText = new JLabel();
                    roomText.setText(c.getCardName().toString());
                    roomText.setBackground(entry.getKey().getColor());
                    roomText.setOpaque(true);
                    roomText.setPreferredSize(textbox);
                    roomText.setHorizontalAlignment(JLabel.CENTER);
                    roomCardPanel.add(roomText);
                    break;
                }
            }
        	
        }
        add(cardPanel, BorderLayout.EAST);
    }
    
    public void updateDisplay() {
    	removeAll();
    	createLayout();
    }
}
