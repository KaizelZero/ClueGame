package tests;

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import game.Board;
import game.BoardCell;
import game.DoorDirection;
import game.Room;

public class FileInitTests {
    // Constants that I will use to test whether the file was loaded correctly
    public static final int LEGEND_SIZE = 11;
    public static final int NUM_ROWS = 25;
    public static final int NUM_COLUMNS = 24;

    // NOTE: I made Board static because I only want to set it up one
    // time (using @BeforeAll), no need to do setup before each test.
    private static Board board;

    @BeforeAll
    public static void setUp() {
        // Board is singleton, get the only instance
        board = Board.getInstance();
        // set the file names to use my config files
        board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
        // Initialize will load BOTH config files
        board.initialize();
    }

    @Test
    public void testRoomLabels() {//Test all rooms are correctly loaded
        assertEquals("Helm", board.getRoom('H').getName());
        assertEquals("Medical", board.getRoom('M').getName());
        assertEquals("Weapons", board.getRoom('D').getName());
        assertEquals("Sleeping", board.getRoom('S').getName());
        assertEquals("Right Thruster", board.getRoom('R').getName());
        assertEquals("Left Thruster", board.getRoom('L').getName());
        assertEquals("Engine", board.getRoom('E').getName());
        assertEquals("Canteen", board.getRoom('C').getName());
        assertEquals("Communications", board.getRoom('A').getName());
    }

    @Test
    public void testBoardDimensions() {
        // Ensure we have the proper number of rows and columns
        assertEquals(NUM_ROWS, board.getNumRows());
        assertEquals(NUM_COLUMNS, board.getNumRows());
    }

    // Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
    // two cells that are not a doorway.
    // These cells are white on the planning spreadsheet
    @Test
    public void FourDoorDirections() {
        BoardCell cell = board.getCell(10, 4);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
        cell = board.getCell(5, 6);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.UP, cell.getDoorDirection());
        cell = board.getCell(3, 15);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
        cell = board.getCell(17, 10);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
        // Test that walkways are not doors
        cell = board.getCell(7, 7);
        assertFalse(cell.isDoorway());
    }

    // Test that we have the correct number of doors
    @Test
    public void testNumberOfDoorways() {
        int numDoors = 0;
        for (int row = 0; row < board.getNumRows(); row++)
            for (int col = 0; col < board.getNumRows(); col++) {
                BoardCell cell = board.getCell(row, col);
                if (cell.isDoorway())
                    numDoors++;
            }
        Assert.assertEquals(10, numDoors);
    }

    // Test a few room cells to ensure the room initial is correct.
    @Test
    public void testRooms() {
        // just test a standard room location
        BoardCell cell = board.getCell(13, 2);
        Room room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Hull");
        assertFalse(cell.isLabel());
        assertFalse(cell.isRoomCenter());
        assertFalse(cell.isDoorway());

        // this is a label cell to test
        cell = board.getCell(19, 18);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Left Thruster");
        assertTrue(cell.isLabel());
        assertTrue(room.getLabelCell() == cell);

        // this is a room center cell to test
        cell = board.getCell(10, 18);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Engine");
        assertTrue(cell.isRoomCenter());
        assertTrue(room.getCenterCell() == cell);

        // this is a secret passage test
        cell = board.getCell(9, 2);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Hull");
        assertTrue(cell.getSecretPassage() == 'K');

        // test a walkway
        cell = board.getCell(17, 7);
        room = board.getRoom(cell);
        // Note for our purposes, walkways and closets are rooms
        assertTrue(room != null);
        assertEquals(room.getName(), "Walkway");
        assertFalse(cell.isRoomCenter());
        assertFalse(cell.isLabel());

        // test a closet
        cell = board.getCell(10, 10);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Unused");
        assertFalse(cell.isRoomCenter());
        assertFalse(cell.isLabel());

    }

}