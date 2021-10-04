package experiment;

import java.util.ArrayList;
import java.util.Set;

public class TestBoard {
	private Set<TestBoardCell> targets;
	private ArrayList<ArrayList<TestBoardCell>> board;
	void calcTargets(TestBoardCell startCell, int pathlength) {
	
	}
	
	public TestBoard(int width, int height) {
		super();
		for(int i = 0; i < width; i++) {
			board.add(null);
			for(int j = 0; j < height; j++) {
				board.get(i).add(new TestBoardCell(j, i));
			}
		}
	}

	public TestBoard() {

	}

	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return board.get(col).get(row);
	}
}
