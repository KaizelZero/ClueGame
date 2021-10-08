package game;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	// Variables
	private int row, column;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	private boolean isRoom, isOccupied;

	// Constructor
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
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
	public boolean getRoom() {
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

	public boolean isLabel() {
		return false;
	}

	public boolean isRoomCenter() {
		return false;
	}

    public boolean isDoorway() {
        return false;
    }

	public DoorDirection getDoorDirection() {
		return null;
	}

	public char getSecretPassage() {
		return 0;
	}

}
