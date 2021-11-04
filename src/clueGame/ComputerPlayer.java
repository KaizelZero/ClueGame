package clueGame;

import java.util.ArrayList;

public class ComputerPlayer extends Player {
	private ArrayList<Card> seen;
    public ComputerPlayer(String name, String color, int row, int col){
        super(name, color, row, col);
        seen = new ArrayList<Card>();
    }
    public ComputerPlayer(String name, String color, BoardCell location){
        super(name, color, location);
        seen = new ArrayList<Card>();
        diceRoll = 0;
    }
    
    public void generateSuggestion(){
    	this.currentSuggestion.clear();
    	while(this.currentSuggestion.size() < 3) {
    		int rand = (int) Math.floor(Math.random() * (Board.getInstance().getDeck().size()));
    		switch (Board.getInstance().getDeck().get(rand).getType()){
    			case PERSON: //Makes sure the card is not in the seen deck and that it is a person (to keep alignment)
    				if(this.currentSuggestion.size() == 0 && !seen.contains(Board.getInstance().getDeck().get(rand))) {
    					this.currentSuggestion.add(Board.getInstance().getDeck().get(rand));
    					this.currentSuggestion.add(location.getCellRoom().getRoomCard());
    				}
    				break;
    			case WEAPON:
    				if(this.currentSuggestion.size() == 2 && !seen.contains(Board.getInstance().getDeck().get(rand))) {
    					this.currentSuggestion.add(Board.getInstance().getDeck().get(rand));
    				}
    				break;
    			case ROOM:
    				break;
    		}
    	}
    }
    
    public void choosePosition(int roll) {
    	Board.getInstance().calcTargets(location, roll);
    	for(BoardCell cell : Board.getInstance().getTargets()) {
    		if(!seen.contains(cell.getCellRoom().getRoomCard()) && cell.isRoom()) {
    			this.location = cell;
    			return;
    		}
    	}
		int rand = (int) Math.floor(Math.random() * (Board.getInstance().getTargets().size()));
    	this.location = (BoardCell) Board.getInstance().getTargets().toArray()[rand];
    	
    }
    //TODO: Random selection, accusations, random dice roll?

	@Override
	public void updateHand(Card newCard){
        hand.add(newCard);
        seen.add(newCard);
    }
	public ArrayList<Card> getSeen() {
		return seen;
	}
	public ArrayList<Card> getSuggestion(){
		return this.currentSuggestion;
	}
}
