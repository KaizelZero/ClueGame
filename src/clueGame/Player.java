package clueGame;

import java.util.ArrayList;

public abstract class Player {
    private String name;
    private String color;

    protected ArrayList<Card> hand;
    protected ArrayList<Card> currentSuggestion;
    protected BoardCell location;
    protected int row, col;
    protected int diceRoll;


    public Player(String name, String color, int row, int col){
        super();
        this.name = name;
        this.color = color;
        this.row = row;
        this.col = col;
        hand = new ArrayList<Card>();
        currentSuggestion = new ArrayList<Card>();
        diceRoll = 0;
    }
    
    public Player(String name, String color, BoardCell location){
        super();
        this.name = name;
        this.color = color;
        this.location = location;
        hand = new ArrayList<Card>();
        currentSuggestion = new ArrayList<Card>();
        diceRoll = 0;
    }
    
    public void setLocation(int row, int col) {
    	this.location = Board.getInstance().getCell(row, col);
    }
    public void setLocation(BoardCell cell) {
    	this.location = cell;
    }
    public String getName(){
        return name;
    }
    public String getColor(){
        return color;
    }
    public BoardCell getLocation() {
    	return location;
    }
    public void initializePosition() {
    	this.location = Board.getInstance().getCell(row, col);
    }
    public ArrayList<Card> getHand(){
        return hand;
    }
    public abstract void updateHand(Card newCard);
    
    public abstract void generateSuggestion();

    public void movePlayer(BoardCell target) {
        this.location = target;
    }

	public static String getSymbol(String symbol) {
		return symbol;
	}

    public String toString() {
        return ("Name: " + getName() + " Color: " + this.color + "Location: " + getLocation());
    }

}