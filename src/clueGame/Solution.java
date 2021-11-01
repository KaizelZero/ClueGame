package clueGame;

public class Solution {
    public static Card person;
    public static Card room;
    public static Card weapon;

    public Solution(Card person, Card room, Card weapon){
        super();
        Solution.person = person;
        Solution.room = room;
        Solution.weapon = weapon;
    }

    public void setWeapon(Card weapon) {
        Solution.weapon = weapon;
    }

    public void setPerson(Card person) {
        Solution.person = person;
    }

    public void setRoom(Card room) {
        Solution.room = room;
    }

    public Card getWeapon() {
        return Solution.weapon;
    }

    public Card getPerson() {
        return Solution.person;
    }

    public Card getRoom() {
        return Solution.room;
    }

    @Override
    public String toString() {
        return (person + " with the\n" + weapon + " in the\n" + room);

    }


}
