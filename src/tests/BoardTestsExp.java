package tests;

import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestsExp {
	TestBoard board;

	@BeforeEach // Sets up 4x4 board for each test
	void setUp() throws Exception {
		board = new TestBoard(4, 4);
	}

	@Test
	public void topLeftCorner() { // Tests adjacency in top left cell
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
	}

	@Test
	public void bottomRightCorner() { // Tests adjacency in bottom right cell
		TestBoardCell cell = board.getCell(3, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertEquals(2, testList.size());
	}

	@Test
	public void rightEdge() { // Tests adjacency on a right edge cell
		TestBoardCell cell = board.getCell(1, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertEquals(3, testList.size());
	}

	@Test
	public void leftEdge() { // Tests adjacency on a left edge cell
		TestBoardCell cell = board.getCell(2, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertEquals(3, testList.size());

	}

	@Test
	public void middleOfGrid() { // Tests adjacency on a cell with no edges
		TestBoardCell cell = board.getCell(2, 2);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList.contains(board.getCell(3, 1)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(4, testList.size());

	}

	@Test
	public void emptyBoard() { // Tests targets on an empty board
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}

	@Test
	public void testOccupied() { // Tests targets on a board where there is an occupied cell
		board.getCell(0, 2).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}

	@Test
	public void testRoom() { // Tests targets on a board where there is a room cell
		board.getCell(0, 2).setRoom(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}

	@Test
	public void testMovement() { // Tests targets, making sure that walked on cells can not be targets again
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertFalse(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
	}

	@Test
	public void testTwoOccupied() { // Tests targets on a board where there is two occupied cells
		board.getCell(0, 2).setRoom(true);
		board.getCell(1, 2).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertFalse(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}

}
