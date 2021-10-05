package experiment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {

	// Variables
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	private TestBoardCell[][] board;
	final static int COLS = 4;
	final static int ROWS = 4;

	public void calcTargets(TestBoardCell startCell, int pathlength) {
	}

	// Creates a TestBoard
	public TestBoard(int width, int height) {
		super();
		TestBoardCell[][] board = new TestBoardCell[ROWS][COLS];
	}

	// Returns targets ArrayList
	public Set<TestBoardCell> getTargets() {
		return targets;
	}

	// Returns the instance of a call at a certain position
	public TestBoardCell getCell(int row, int col) {
		return board.get(col).get(row);
	}
}
