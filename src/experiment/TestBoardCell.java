package experiment;

import java.util.Set;

public class TestBoardCell {
	private int row, column;
	private Set<TestBoardCell> adjacencyList;
	private boolean isRoom = false, isOccupied = false;
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	void addAdjacency(TestBoardCell cell) {
		adjacencyList.add(cell);
	}
	public Set<TestBoardCell> getAdjList(){
		return this.adjacencyList;
	}
	void setRoom(boolean isAssignRoom) {
		isRoom = isAssignRoom;
	}
	boolean getRoom() {
		return isRoom;
	}
	void setOccupied(boolean newOccupied) {
		isOccupied = newOccupied;
	}
	boolean getOccupied() {
		return isOccupied;
	}
}
