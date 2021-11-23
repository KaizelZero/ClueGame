package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HumanPlayer extends Player {

    private boolean hasMoved;
    private boolean isTurn;
    private Map<Player, HashSet<Card>> seen;

    public HumanPlayer(String name, String color, int row, int col) {
        super(name, color, row, col);
        seen = new HashMap<Player, HashSet<Card>>();
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

    public Map<Player, HashSet<Card>> getSeen() {
        return seen;
    }

    public void updateSeen(Player owner, Card newCard) {
        if (!hand.contains(newCard)) {
            if (this.seen.containsKey(owner)) {
                this.seen.get(owner).add(newCard);
            } else {
                HashSet<Card> temp = new HashSet<Card>();
                temp.add(newCard);
                this.seen.put(owner, temp);
            }
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
