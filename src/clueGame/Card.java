package clueGame;

public class Card {
    private String cardName, cardID;
    private CardType type;

    public Card(String cardName, CardType type, String cardID) {
        super();
        this.cardName = cardName;
        this.type = type;
        this.cardID = cardID;
    }

    public Card(String cardName, CardType type) {
        super();
        this.cardName = cardName;
        this.type = type;
    }

    public String getCardName() {
        return this.cardName;
    }

    public CardType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Card [cardName=" + cardName + ", type=" + type + "]";
    }

}
