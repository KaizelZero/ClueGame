package experiment;
import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	// Variables
	private int row, column;
	private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
	private boolean isRoom, isOccupied;

	// Constructor
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	// Adds adjacent cells to adjacencyList
	public void addAdjacency(Set<TestBoardCell> adjacencyList) {
		this.adjList = adjacencyList;
	}

	// Returns the adjacencyList
	public Set<TestBoardCell> getAdjList() {
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

	@Override
	public String toString() {
		return "TestBoardCell [row=" + row + ", column=" + column + "]";
	}

}
