package clueGame;

public class Solution {
    public Card person;
    public Card room;
    public Card weapon;

    public Solution(Card person, Card room, Card weapon){
        super();
        this.person = person;
        this.room = room;
        this.weapon = weapon;
    }

    public void setWeapon(Card weapon) {
        this.weapon = weapon;
    }

    public void setPerson(Card person) {
        this.person = person;
    }

    public void setRoom(Card room) {
        this.room = room;
    }

    public Card getWeapon() {
        return this.weapon;
    }

    public Card getPerson() {
        return this.person;
    }

    public Card getRoom() {
        return this.room;
    }

    @Override
    public String toString() {
        return (person + " with the\n" + weapon + " in the\n" + room);

    }


}
