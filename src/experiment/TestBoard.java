package experiment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {

	// Variables
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	private Set<TestBoardCell> visited = new HashSet<TestBoardCell>();
	private TestBoardCell[][] board;
	final static int COLS = 4;
	final static int ROWS = 4;

	public void calcTargets(TestBoardCell startCell, int pathlength) {
	}

	// Creates a TestBoard
	public TestBoard(int width, int height) {
		super();
		board = new TestBoardCell[ROWS][COLS];
		for(int i = 0; i < COLS; i++) {
			for(int j = 0; j < ROWS; j++) {
				board[i][j] = new TestBoardCell(j, i);
			}
		}
	}

	// Returns targets ArrayList
	public Set<TestBoardCell> getTargets() {
		return targets;
	}

	// Returns the instance of a call at a certain position
	public TestBoardCell getCell(int row, int col) {
		return board[col][row];
	}
}
