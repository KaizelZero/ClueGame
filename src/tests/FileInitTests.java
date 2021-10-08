package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import game.Board;
import game.BoardCell;
import game.DoorDirection;
import game.Room;

public class FileInitTests {
    public static final int LEGEND_SIZE = 11;
    public static final int NUM_ROWS = 21;
    public static final int NUM_COLUMNS = 21;

    private static Board board;

    @BeforeAll
    public static void setUp() { //Setup test board
        board = Board.getInstance();
        board.setConfigFiles("bin/data/Clue Excel Diagram.csv", "bin/data/ClueSetup.txt");
        board.initialize();
    }

    @Test
    public void testRoomLabels() {//Test that all rooms are correctly loaded
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
    public void testBoardDimensions() { // Ensure we have the proper number of rows and columns
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
        cell = board.getCell(7, 7); // Test that walkways are not doors
        assertFalse(cell.isDoorway());
        cell = board.getCell(14, 11);
        assertFalse(cell.isDoorway());
    }

    @Test
    public void testNumberOfDoorways() { // Test that we have the correct number of doors
        int numDoors = 0;
        for (int row = 0; row < board.getNumRows(); row++)
            for (int col = 0; col < board.getNumRows(); col++) {
                BoardCell cell = board.getCell(row, col);
                if (cell.isDoorway())
                    numDoors++;
            }
        Assert.assertEquals(10, numDoors); //10 doors total
    }

    // Test a few room cells to ensure the room initial is correct.
    @Test
    public void testRooms() {
        BoardCell cell = board.getCell(13, 2); //Standard room test
        Room room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Hull");
        assertFalse(cell.isLabel());
        assertFalse(cell.isRoomCenter());
        assertFalse(cell.isDoorway());

        cell = board.getCell(19, 18); //Label cell test
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Left Thruster");
        assertTrue(cell.isLabel());
        assertTrue(room.getLabelCell() == cell);

        cell = board.getCell(10, 18); //Room center test
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Engine");
        assertTrue(cell.isRoomCenter());
        assertTrue(room.getCenterCell() == cell);

        cell = board.getCell(9, 2); //Secret passage test
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Hull");
        assertTrue(cell.getSecretPassage() == 'K');

        cell = board.getCell(17, 7); //Walkway test
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Walkway");
        assertFalse(cell.isRoomCenter());
        assertFalse(cell.isLabel());

        cell = board.getCell(10, 10); //Test unused space
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Unused");
        assertFalse(cell.isRoomCenter());
        assertFalse(cell.isLabel());

    }

}
