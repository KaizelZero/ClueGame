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

public class PlayerTests {
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
	public void deckTypeTest(){ //Checks the right number of each card is stored in the deck
        int numPlayers = 0, numRooms = 0, numWeapons = 0;
        for(Card c : board.getDeck()){
            switch (c.getType()){
                case ROOM:
                    numRooms++;
                    break;
                case PERSON:
                    numPlayers++;
                    break;
                case WEAPON:
                    numWeapons++;
                    break;
                default:
                    assertTrue(false);
                    break;
            }
        }
        assertEquals(numRooms, 9);
        assertEquals(numPlayers, 6);
        assertEquals(numWeapons, 6);
    }

    @Test
    public void testSolution(){
        board.deal();
        assertEquals(board.getSolution().person.getType(), CardType.PERSON);
        assertEquals(board.getSolution().room.getType(), CardType.ROOM);
        assertEquals(board.getSolution().weapon.getType(), CardType.WEAPON);
    }

    @Test
    public void testAllDealt(){//Test if all of the cards are dealt to a player or solution
        board.deal();
        for(Player player : board.getPlayerList()){
        	assertEquals(player.getHand().size(), 3, 1);
        }
    }

    @Test
    public void testDuplicates(){ //Make sure there are no duplicate cards among those playing or in the solution
    	board.deal();
        Set<Card> duplicateCards = new HashSet<Card>();
        for(Player p : board.getPlayerList()){
            assertTrue(duplicateCards.addAll(p.getHand()));
        }
        assertTrue(duplicateCards.add(board.getSolution().person));
        assertTrue(duplicateCards.add(board.getSolution().weapon));
        assertTrue(duplicateCards.add(board.getSolution().room));

    }

    // Tests that there were 6 people loaded
    @Test
    public void testNumPeopleLoaded() {
        assertEquals(board.getPlayerList().size(), 6);
    }


}