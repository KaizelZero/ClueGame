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
        board.setSolution(a, b, c);
        assertTrue(board.checkAccusation(a, b, c));
        assertFalse(board.checkAccusation(b, a, c));
        assertFalse(board.checkAccusation(c, b, a));
        assertFalse(board.checkAccusation(a, c, b));
    }

    @Test
    public void testSuggestion(){
        Card a = new Card("cardA", CardType.PERSON, "a");
        Card b = new Card("cardB", CardType.ROOM, "b");
        Card c = new Card("cardC", CardType.WEAPON, "c");
        board.getPlayerList().clear();
        Player pa = new ComputerPlayer("playerA", "r", 0, 0);
        Player pb = new ComputerPlayer("playerB", "g", 0, 0);
        Player pc = new ComputerPlayer("playerC", "b", 0, 0);
        pc.updateHand(a);
        board.getPlayerList().add(pa);
        board.getPlayerList().add(pb);
        board.getPlayerList().add(pc);
        Card answer = board.handleSuggestion(pa, a, b, c);
        assertEquals(answer, a);
    }

}
