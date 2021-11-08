package clueGame;

import java.util.ArrayList;

public class HumanPlayer extends Player {

    private boolean hasMoved;
    private boolean isTurn;
    private ArrayList<Card> seen;

    public HumanPlayer(String name, String color, int row, int col) {
        super(name, color, row, col);
        seen = new ArrayList<Card>();
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

    public ArrayList<Card> getSeen() {
        return seen;
    }

    public Boolean isTurn() {
        return this.isTurn;
    }

    @Override
    public void updateHand(Card newCard) {
        hand.add(newCard);
    }

}
