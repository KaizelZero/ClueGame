package game;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	// Variables
	private int row, column;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	private boolean isRoom, isOccupied;
	private Room thisRoom = new Room();
	private char secretPassage = ']';
	private DoorDirection doorDirection = DoorDirection.NONE;
	

	// Constructor
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	public Room getCellRoom() {
		return this.thisRoom;
	}
	// Adds adjacent cells to adjacencyList
	public void addAdjacency(Set<BoardCell> adjacencyList) {
		this.adjList = adjacencyList;
	}

	// Returns the adjacencyList
	public Set<BoardCell> getAdjList() {
		return this.adjList;
	}

	// Checks whether the cell is in room
	public void setRoom(boolean isAssignRoom) {
		isRoom = isAssignRoom;
	}

	// Returns the whether the cell is in the room
	public boolean isRoom() {
		return isRoom;
	}

	// Checks whether the cell is occupied
	public void setOccupied(boolean newOccupied) {
		isOccupied = newOccupied;
	}

	// Returns whether the cell is occupied
	public boolean getOccupied() {
		return isOccupied;
	}
	// Returns if the cell is a label cell
	public boolean isLabel() {
		if(this.getCellRoom().getLabelCell() != null) {
			return true;
		}
		return false;
	}
	// Returns if the cell is the center of a room
	public boolean isRoomCenter() {
		if(this.getCellRoom().getCenterCell() != null) {
			return true;
		}
		return false;
	}
	// Returns if the cell is a doorway
    public boolean isDoorway() {
        if(doorDirection != DoorDirection.NONE) {
        	return true;
        }
        return false;
    }

    // Returns doorway direction
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	//Specifies door direction
	public void setDoorDirection(char direction) { 
		if(direction == '^') {
			this.doorDirection = DoorDirection.UP;
		}else if(direction == '<') {
			this.doorDirection = DoorDirection.LEFT;
		}else if(direction == 'v') {
			this.doorDirection = DoorDirection.DOWN;
		}else {
			this.doorDirection = DoorDirection.RIGHT;
		}
	}
	public boolean isSecretPassage(){
		if(secretPassage != ']'){
			return true;
		} else {
			return false;
		}
	}

	//Returns the secret passage char
	public char getSecretPassage() {
		return secretPassage;
	}
	//Sets the secret passage char
	public void setSecretPassage(char pass) {
		this.secretPassage = pass;
	}

	public boolean isWalkway(){
		if(thisRoom.getName() == "W"){
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	
}
