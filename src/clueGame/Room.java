package clueGame;

import java.util.ArrayList;

public class Room {
	private String room;
	private String name;
	private Card roomCard;
	private BoardCell centerCell = null;
	private BoardCell labelCell = null;
	private ArrayList<BoardCell> roomDoors = new ArrayList<BoardCell>();
	private int occupants = 0;

	public Room(String roomLetter) {
		super();
		this.room = roomLetter;
	}

	public Room() {
		super();
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public BoardCell getLabelCell() {
		return this.labelCell;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addDoor(BoardCell doorCell) {
		roomDoors.add(doorCell);
	}

	public ArrayList<BoardCell> getRoomDoors() {
		return roomDoors;
	}
	
	public void setRoomCard(Card c) {
		this.roomCard = c;
	}
	
	public Card getRoomCard() {
		return this.roomCard;
	}
	public void setOccupants(int change) {
		this.occupants += change;
	}
	public int getOccupants() {
		return this.occupants;
	}
}
