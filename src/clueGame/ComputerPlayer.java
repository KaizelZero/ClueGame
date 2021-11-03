package clueGame;

import java.util.ArrayList;

public class ComputerPlayer extends Player {
	private ArrayList<Card> seen;
    public ComputerPlayer(String name, String color, int row, int col){
        super(name, color, row, col);
        seen = new ArrayList<Card>();
    }
    
    public void generateSuggestion(){
    	this.currentSuggestion.clear();
    	while(this.currentSuggestion.size() < 3) {
    		int rand = (int) Math.floor(Math.random() * (Board.getInstance().getDeck().size()));
    		switch (Board.getInstance().getDeck().get(rand).getType()){
    			case PERSON:
    				if(this.currentSuggestion.size() == 0) {
    					this.currentSuggestion.add(Board.getInstance().getDeck().get(rand));
    					this.currentSuggestion.add(Board.getInstance().getCell(row, col).getCellRoom().getRoomCard());
    				}
    				break;
    			case WEAPON:
    				if(this.currentSuggestion.size() == 2) {
    					this.currentSuggestion.add(Board.getInstance().getDeck().get(rand));
    				}
    				break;
    			case ROOM:
    				break;
    		}
    	}
    }
    //TODO: Random selection, accusations, random dice roll?

	@Override
	public void updateHand(Card newCard){
        hand.add(newCard);
        seen.add(newCard);
    }
}
