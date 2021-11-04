package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Card;
import clueGame.Solution;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.CardType;

public class GameSolutionTest {
    private static Board board;
    @BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("bin/data/Clue Excel Diagram2.csv", "bin/data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();


	}

   @Test
    public void testAccusation() {
        Card a = new Card("cardA", CardType.PERSON, "a");
        Card b = new Card("cardB", CardType.ROOM, "b");
        Card c = new Card("cardC", CardType.WEAPON, "c");
        Card d = new Card("cardD", CardType.PERSON, "a");
        Card e = new Card("cardE", CardType.ROOM, "b");
        Card f = new Card("cardF", CardType.WEAPON, "c");
        board.setSolution(a, b, c);
        assertTrue(board.checkAccusation(a, b, c));
        assertFalse(board.checkAccusation(d, b, c));
        assertFalse(board.checkAccusation(a, e, c));
        assertFalse(board.checkAccusation(a, b, f));
    }

}
