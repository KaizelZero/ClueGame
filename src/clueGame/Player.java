package clueGame;

import java.util.ArrayList;
import java.awt.Color;

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
        int alpha = 180;
        // https://stackoverflow.com/questions/2854043/converting-a-string-to-color-in-java
        switch (strColor) {
        case "Blue":
            color = newColorWithAlpha(Color.BLUE, alpha);
            break;
        case "Red":
            color = newColorWithAlpha(Color.RED, alpha);
            break;
        case "Pink":
            color = newColorWithAlpha(Color.PINK, alpha);
            break;
        case "Green":
            color = newColorWithAlpha(Color.GREEN, alpha);
            break;
        case "Orange":
            color = newColorWithAlpha(Color.ORANGE, alpha);
            break;
        case "Violet":
            color = newColorWithAlpha(Color.MAGENTA, alpha);
            break;
        }
        return color;
    }

    public static Color newColorWithAlpha(Color original, int alpha) {
        return new Color(original.getRed(), original.getGreen(), original.getBlue(), alpha);
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