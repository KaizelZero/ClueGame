package experiment;
import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	// Variables
	private int row, column;
	Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
	private boolean isRoom, isOccupied;

	// Constructor
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	// Adds adjacent cells to adjacencyList
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}

	// Returns the adjacencyList
	public Set<TestBoardCell> getAdjList() {
		this.adjList.add(new TestBoardCell(0,0));
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

}
