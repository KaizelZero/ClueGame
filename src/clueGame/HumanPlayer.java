package clueGame;

public class HumanPlayer extends Player{

    private boolean hasMoved;
    private boolean isTurn;

    public HumanPlayer(String name, String color){
        super(name, color);
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



}
