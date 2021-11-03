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

    @Test
    public void testSuggestion(){ //Our handle suggestion function handles both "Player disproves a suggestion" and "Handle a suggestion made"
    							  //By checking all players hands (in order) and returning the first matching card or if there are no matching
    							  //cards in any of the other player's hands (not the one making the suggestion), then null is returned. 
        Card a = new Card("cardA", CardType.PERSON, "a"); //Test handleSolution when there is a solution
        Card b = new Card("cardB", CardType.ROOM, "b");
        Card c = new Card("cardC", CardType.WEAPON, "c");
        board.getPlayerList().clear();
        Player pa = new ComputerPlayer("playerA", "r", 0, 0);
        Player pb = new ComputerPlayer("playerB", "g", 0, 0);
        Player pc = new ComputerPlayer("playerC", "b", 0, 0);
        pc.updateHand(a);
        pb.updateHand(b);
        board.getPlayerList().add(pa);
        board.getPlayerList().add(pb);
        board.getPlayerList().add(pc);
        Card answer = board.handleSuggestion(pa, a, b, c);
        assertEquals(answer, b);
        
        pb.updateHand(c); //If there are two cards that match the query, check that the one returned is randomly selected
        int bCount = 0, cCount = 0;
        for(int i = 0; i < 100; i++) {
        answer = board.handleSuggestion(pa, a, b, c);
        if(answer == b) {
        	bCount++;
        }else if(answer == c) {
        	cCount++;
        }else {
        	assertTrue(false);
        }
        }
        assertTrue(bCount > 10);
        assertTrue(cCount > 10);
        
        pc.getHand().clear(); //Test if no suggestion works (should return null)
        pb.getHand().clear();
        answer = board.handleSuggestion(pa, a, b, c);
        assertEquals(answer, null);
        
        pa.updateHand(a); //Checks that if the only match is in the suggesting player's hand, null is returned
        answer = board.handleSuggestion(pa, a, b, c);
        assertEquals(answer, null);
        
    }
    
    @Test
    public void testComputersSuggestions() {
        Card a = new Card("cardA", CardType.PERSON, "a"); 
        Card b = new Card("cardB", CardType.ROOM, "b");
        Card c = new Card("cardC", CardType.WEAPON, "c");
        board.getPlayerList().clear();
        Player p = new ComputerPlayer("computerPlayer", "r", 0, 0);
        p.generateSuggestion();
        
        
    }

}
