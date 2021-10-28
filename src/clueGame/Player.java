package clueGame;

import java.util.ArrayList;

public abstract class Player {
    private String name;
    private String color;
    ArrayList<Card> hand = new ArrayList<Card>();
    protected int row, col; 
    abstract void movePlayer();
    public Player(String name, String color, int row, int col){
        super();
        this.name = name;
        this.color = color;
        this.row = row;
        this.col = col;
    }
    public String getName(){
        return name;
    }
    public String getColor(){
        return color;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public ArrayList<Card> getHand(){
        return hand;
    }
    public void updateHand(Card newCard){
        hand.add(newCard);
    }
	public static String getSymbol(String symbol) {
		return symbol;
	}
}