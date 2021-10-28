package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("bin/data/Clue Excel Diagram2.csv", "bin/data/ClueSetup.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the helm has two doors and a secret room
		Set<BoardCell> testList = board.getAdjList(10, 2);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(10, 18)));
		assertTrue(testList.contains(board.getCell(10, 4)));
		assertTrue(testList.contains(board.getCell(11, 4)));
		
		// now test the right thruster
		testList = board.getAdjList(3, 18);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(3, 15)));
		
		// one more room, the canteen
		testList = board.getAdjList(19, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(17, 10)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(3, 15);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(3, 18)));

		testList = board.getAdjList(17, 5);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(17, 6)));
		assertTrue(testList.contains(board.getCell(16, 5)));
		
		testList = board.getAdjList(0, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(1, 11)));
		
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(21, 8);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(20, 8)));
		
		// Test near closet cells
		testList = board.getAdjList(8, 12);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(8, 11)));
		assertTrue(testList.contains(board.getCell(7, 12)));
		assertTrue(testList.contains(board.getCell(8, 13)));

		// Test near doors, but not adjacent
		testList = board.getAdjList(8, 15);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(7, 15)));
		assertTrue(testList.contains(board.getCell(8, 14)));
		assertTrue(testList.contains(board.getCell(9, 15)));

		// Test next to room (canteen)
		testList = board.getAdjList(16,12);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(16, 11)));
		assertTrue(testList.contains(board.getCell(15, 12)));
		assertTrue(testList.contains(board.getCell(16, 13)));
	
	}
	
	
	// Tests out of room center, 1, 2 and 3
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInWeapondsRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(2, 10), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(5, 9)));
		
		// test a roll of 2
		board.calcTargets(board.getCell(2, 10), 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(5, 8)));
		assertTrue(targets.contains(board.getCell(6, 9)));	
		assertTrue(targets.contains(board.getCell(5, 10)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(2, 10), 3);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(5, 7)));
		assertTrue(targets.contains(board.getCell(7, 9)));	
		assertTrue(targets.contains(board.getCell(5, 11)));
		assertTrue(targets.contains(board.getCell(6, 10)));	
	}
	
	@Test
	public void testTargetsInHelm() {
		// test a roll of 1
		board.calcTargets(board.getCell(10, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(10, 4)));
		assertTrue(targets.contains(board.getCell(11, 4)));	
		assertTrue(targets.contains(board.getCell(10, 18)));	
		
		// test a roll of 2
		board.calcTargets(board.getCell(10, 2), 2);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(9, 4)));
		assertTrue(targets.contains(board.getCell(10, 5)));	
		assertTrue(targets.contains(board.getCell(11, 5)));
		assertTrue(targets.contains(board.getCell(12, 4)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(10, 2), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(8, 4)));
		assertTrue(targets.contains(board.getCell(9, 5)));	
		assertTrue(targets.contains(board.getCell(12, 5)));
		assertTrue(targets.contains(board.getCell(13, 4)));	
	}

	// Tests out of room center, 1, 2 and 3
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(18, 15), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(17, 18)));
		assertTrue(targets.contains(board.getCell(17, 15)));	
		assertTrue(targets.contains(board.getCell(18, 14)));	
		assertTrue(targets.contains(board.getCell(19, 15)));	
		
		// test a roll of 2
		board.calcTargets(board.getCell(18, 15), 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(17, 18)));
		assertTrue(targets.contains(board.getCell(16, 15)));
		assertTrue(targets.contains(board.getCell(17, 14)));	
		assertTrue(targets.contains(board.getCell(19, 14)));
		assertTrue(targets.contains(board.getCell(20, 15)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(18, 15), 3);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(17, 18)));
		assertTrue(targets.contains(board.getCell(15, 15)));
		assertTrue(targets.contains(board.getCell(16, 14)));	
		assertTrue(targets.contains(board.getCell(20, 14)));
		assertTrue(targets.contains(board.getCell(21, 15)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 15), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(12, 15)));
		assertTrue(targets.contains(board.getCell(13, 14)));	
		assertTrue(targets.contains(board.getCell(14, 15)));	

		// test a roll of 2
		board.calcTargets(board.getCell(13, 15), 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(11, 15)));
		assertTrue(targets.contains(board.getCell(12, 14)));
		assertTrue(targets.contains(board.getCell(13, 13)));
		assertTrue(targets.contains(board.getCell(14, 14)));	
		assertTrue(targets.contains(board.getCell(15, 15)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(13, 15), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(10, 18)));
		assertTrue(targets.contains(board.getCell(10, 15)));
		assertTrue(targets.contains(board.getCell(11, 14)));	
		assertTrue(targets.contains(board.getCell(13, 12)));	
		assertTrue(targets.contains(board.getCell(14, 13)));	
		assertTrue(targets.contains(board.getCell(15, 14)));	
		assertTrue(targets.contains(board.getCell(16, 15)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(17, 9), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(17, 8)));
		assertTrue(targets.contains(board.getCell(16, 9)));	
		assertTrue(targets.contains(board.getCell(17, 10)));	
		
		// test a roll of 2
		board.calcTargets(board.getCell(17, 9), 2);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(19, 11)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(17, 7)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(17, 9), 3);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(19, 11)));
		assertTrue(targets.contains(board.getCell(19, 8)));
		assertTrue(targets.contains(board.getCell(16, 7)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 3 blocked 2 down
		board.getCell(2, 14).setOccupied(true);
		board.calcTargets(board.getCell(0, 14), 3);
		board.getCell(2, 14).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(1, 14)));
		assertTrue(targets.contains(board.getCell(0, 15)));
		assertTrue(targets.contains(board.getCell(2, 15)));	
		assertFalse( targets.contains( board.getCell(14, 2))) ;
		assertFalse( targets.contains( board.getCell(14, 3))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(17, 18).setOccupied(true);
		board.calcTargets(board.getCell(18, 15), 1);
		board.getCell(17, 18).setOccupied(false);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(17, 18)));	
		assertTrue(targets.contains(board.getCell(17, 15)));	
		assertTrue(targets.contains(board.getCell(18, 14)));	
		assertTrue(targets.contains(board.getCell(19, 15)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(11, 15).setOccupied(true);
		board.calcTargets(board.getCell(10, 18), 2);
		board.getCell(11, 15).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(9, 15)));
		assertTrue(targets.contains(board.getCell(10, 14)));	
		assertTrue(targets.contains(board.getCell(10, 2)));

	}
}
