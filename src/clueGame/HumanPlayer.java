package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HumanPlayer extends Player {

    private boolean hasMoved;
    private boolean isTurn;
    private Set<Card> seen;

    public HumanPlayer(String name, String color, int row, int col) {
        super(name, color, row, col);
        seen = new HashSet<Card>();
        hasMoved = false;
    }

    public void setLocation(BoardCell cell) {
        this.location = cell;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public Boolean hasMoved() {
        return this.hasMoved;
    }

    public Set<Card> getSeen() {
        return seen;
    }
    
    public void updateSeen(Card newCard) {
    	if(!hand.contains(newCard)) {
    		this.seen.add(newCard);
    	}
    }

    public Boolean isTurn() {
        return this.isTurn;
    }

    @Override
    public void updateHand(Card newCard) {
        hand.add(newCard);
    }

}
