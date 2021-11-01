package clueGame;

import java.util.ArrayList;

public abstract class Player {
    private String name;
    private String color;

    protected ArrayList<Card> hand;
    protected BoardCell location; 
    protected int diceRoll;


    public Player(String name, String color){
        super();
        this.name = name;
        this.color = color;
        hand = new ArrayList<Card>();
        diceRoll = 0;
    }
    
    public String getName(){
        return name;
    }
    public String getColor(){
        return color;
    }

    public void setLocation(BoardCell target) {
        this.location = target;
    }

    public BoardCell getLocation() {
        return this.location;
    }

    public ArrayList<Card> getHand(){
        return hand;
    }
    public void updateHand(Card newCard){
        hand.add(newCard);
    }

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