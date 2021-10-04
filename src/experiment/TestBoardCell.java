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
	
	public void addAdjacency(TestBoardCell cell) {
		adjacencyList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList(){
		return this.adjacencyList;
	}
	
	public void setRoom(boolean isAssignRoom) {
		isRoom = isAssignRoom;
	}
	
	public boolean getRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean newOccupied) {
		isOccupied = newOccupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
	
}
