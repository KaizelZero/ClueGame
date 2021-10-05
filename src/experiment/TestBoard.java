package experiment;

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
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}
	private void findAllTargets(TestBoardCell thisCell, int numSteps) {
		for(TestBoardCell adjCell : thisCell.getAdjList()) {
			if(visited.contains(adjCell)){
				continue;
			}
			visited.add(adjCell);
			if(numSteps == 1) {
				if(!adjCell.getOccupied() && !adjCell.getRoom()) {
					targets.add(adjCell);
				}
			}else {
				findAllTargets(adjCell, numSteps - 1);
			}
			visited.remove(adjCell);
		}
	}
	// Creates a TestBoard
	public TestBoard(int width, int height) {
		super();
		board = new TestBoardCell[ROWS][COLS];
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				board[i][j] = new TestBoardCell(i, j);
			}
		}
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				board[i][j].addAdjacency(calcAdj(i, j));
			}
		}
	}
	public Set<TestBoardCell> calcAdj(int row, int col) {
		Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
		if(row > 0) {
			adjList.add(board[row - 1][col]);
		}
		if(row < 3) {
			adjList.add(board[row + 1][col]);
		}
		if(col > 0) {
			adjList.add(board[row][col - 1]);
		}
		if(col < 3) {
			adjList.add(board[row][col + 1]);
		}
		return adjList;
	}
	// Returns targets ArrayList
	public Set<TestBoardCell> getTargets() {
		return targets;
	}

	// Returns the instance of a call at a certain position
	public TestBoardCell getCell(int row, int col) {
		return board[row][col];
	}
}
