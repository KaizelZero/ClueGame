package clueGame;

public class HumanPlayer extends Player{

    private boolean hasMoved;
    private boolean isTurn;

    public HumanPlayer(String name, String color, int row, int col){
        super(name, color, row, col);
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

    public Boolean isTurn() {
        return this.isTurn;
    }

	@Override
	public void updateHand(Card newCard){
        hand.add(newCard);
	}

	@Override
	public void generateSuggestion() {
		// TODO Auto-generated method stub
		
	}




}
