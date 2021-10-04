package experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	// Variables
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	private ArrayList<ArrayList<TestBoardCell>> board = new ArrayList<ArrayList<TestBoardCell>>();
	public void calcTargets(TestBoardCell startCell, int pathlength) {}
	
	// Creates a TestBoard
	public TestBoard(int width, int height) {
		super();
		for(int i = 0; i < width; i++) {
			ArrayList<TestBoardCell> temp = new ArrayList<TestBoardCell>();
			for(int j = 0; j < height; j++) {
				temp.add(new TestBoardCell(j, i));
			}
			board.add(temp);
		}
	}
	
	// Returns targets ArrayList
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	// Returns the instance of a call at a certain position
	public TestBoardCell getCell(int row, int col) {
		return board.get(col).get(row);
	}
}
