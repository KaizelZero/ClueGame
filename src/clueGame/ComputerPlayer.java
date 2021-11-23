package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.Color;

public class ComputerPlayer extends Player {
	private Set<Card> seen;

	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
		seen = new HashSet<Card>();
	}

	public ComputerPlayer(String name, String color, BoardCell location) {
		super(name, color, location);
		seen = new HashSet<Card>();
		diceRoll = 0;
	}

	// Computer creating a suggestion
	public void generateSuggestion() {

		this.currentSuggestion.clear();
		while (this.currentSuggestion.size() < 3) {
			int rand = (int) Math.floor(Math.random() * (Board.getInstance().getDeck().size()));
			// Switch statement ensures that each card type is chosen
			switch (Board.getInstance().getDeck().get(rand).getType()) {
			case PERSON: // Makes sure the card is not in the seen deck and that it is a person (to keep
							// alignment)
				if (this.currentSuggestion.size() == 0 && !seen.contains(Board.getInstance().getDeck().get(rand))) {
					this.currentSuggestion.add(Board.getInstance().getDeck().get(rand));
					this.currentSuggestion.add(location.getCellRoom().getRoomCard());
				}
				break;
			case WEAPON:
				if (this.currentSuggestion.size() == 2 && !seen.contains(Board.getInstance().getDeck().get(rand))) {
					this.currentSuggestion.add(Board.getInstance().getDeck().get(rand));
				}
				break;
			case ROOM: // Room will always been the room that the CPU is in
				break;
			}
		}
	}

	// Selects a target
	public void choosePosition(int roll) {
		Board.getInstance().calcTargets(location, roll);
		for (BoardCell cell : Board.getInstance().getTargets()) {
			if (!seen.contains(cell.getCellRoom().getRoomCard()) && cell.isRoom()) { // If in not seen list
				this.location = cell; // Selects the room
				return;
			}
		}
		// Otherwise select randomly
		int rand = (int) Math.floor(Math.random() * (Board.getInstance().getTargets().size()));
		this.location = (BoardCell) Board.getInstance().getTargets().toArray()[rand];

	}

	@Override
	public void updateHand(Card newCard) {
		hand.add(newCard);
		seen.add(newCard);
	}

	public Set<Card> getSeen() {
		return seen;
	}

	// Returns the suggestion as three strings
	public ArrayList<Card> getSuggestion() {
		return this.currentSuggestion;
	}

}
