package clueGame;

import java.util.ArrayList;
import java.awt.Color;
import java.lang.reflect.Field;

public abstract class Player {
    private String name;
    private Color color;
    private String colorString;

    protected ArrayList<Card> hand;
    protected ArrayList<Card> currentSuggestion;
    protected BoardCell location;
    protected int row, col;
    protected int diceRoll;

    public Player(String name, String color, int row, int col) {
        super();
        this.name = name;
        this.colorString = color;
        this.color = convertColor(color);
        this.row = row;
        this.col = col;
        hand = new ArrayList<Card>();
        currentSuggestion = new ArrayList<Card>();
        diceRoll = 0;
    }

    public Player(String name, String color, BoardCell location) {
        super();
        this.name = name;
        this.colorString = color;
        this.color = convertColor(color);
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

    public String getName() {
        return name;
    }

    public Color convertColor(String strColor) {
    	// https://stackoverflow.com/questions/2854043/converting-a-string-to-color-in-java
	    Color color = null;
	    switch(strColor) {
	    	case "Blue":
	    		color = Color.BLUE;
	    		break;
	    	case "Red":
	    		color = Color.RED;
	    		break;
	    	case "Pink":
	    		color = Color.PINK;
	    		break;
	    	case "Green":
	    		color = Color.GREEN;
	    		break;
	    	case "Orange":
	    		color = Color.ORANGE;
	    		break;
	    	case "Violet":
	    		color = Color.MAGENTA;
	    		break;
	    }
	    System.out.println(color);
	    return color;
    } 
    
    public Color getColor() {
        return color;
    }
    
    public String getColorString() {
		return colorString;
	}

    public BoardCell getLocation() {
        return location;
    }

    public void initializePosition() {
        this.location = Board.getInstance().getCell(row, col);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public abstract void updateHand(Card newCard);

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