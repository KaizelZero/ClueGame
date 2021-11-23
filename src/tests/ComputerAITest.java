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
import clueGame.Room;
import clueGame.Card;
import clueGame.Solution;
import clueGame.ComputerPlayer;
import clueGame.GameCardPanel;
import clueGame.GameControlPanel;
import clueGame.HumanPlayer;
import clueGame.CardType;
import clueGame.ClueGame;

public class ComputerAITest {
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
    
    
    //@Test
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
        pa.setLocation(2, 7);
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
        board.getDeck().clear();
        Card pa = new Card("playerA", CardType.PERSON, "pa");
        Card pb = new Card("playerB", CardType.PERSON, "pb");

        Card a = new Card("cardA", CardType.PERSON, "a"); 
        Card b = new Card("cardB", CardType.ROOM, "b");
        Card c = new Card("cardC", CardType.WEAPON, "c");
        Card d = new Card("cardD", CardType.WEAPON, "d");

        board.getDeck().add(pa);
        board.getDeck().add(pb);
        board.getDeck().add(a);
        board.getDeck().add(b);
        board.getDeck().add(c);
        board.getPlayerList().clear();
        ComputerPlayer p = new ComputerPlayer("computerPlayer", "r", Board.getInstance().getCell(2, 7));
        p.getSeen().add(a);
        p.getSeen().add(pb);
        p.updateHand(b);
        p.generateSuggestion();
        assertEquals(p.getSuggestion().get(0), pa); //Tests if there is just one of each item to choose from
        assertEquals(p.getSuggestion().get(1), p.getLocation().getCellRoom().getRoomCard());
        assertEquals(p.getSuggestion().get(2), c);
        
        board.getDeck().add(d);

        p.getSeen().clear();
        int countPa = 0, countPb = 0, countC = 0, countD = 0, countA = 0; 
        //Tests the random function based on having multiple people and weapons
        for(int i = 0; i < 100; i++) {
            p.generateSuggestion();
            if(p.getSuggestion().get(0) == pa){
                countPa++;
            }else if(p.getSuggestion().get(0) == pb){
                countPb++;
            }else if(p.getSuggestion().get(0) == a){
                countA++;
            }            
            if(p.getSuggestion().get(2) == c){ 
                countC++;
            }else if(p.getSuggestion().get(2) == d) {
            	countD++;
            }

        }
        if(countPa > 5 && countPb > 5 && countA > 0 && countC > 5 && countD > 5) {
        	assertTrue(true);
        }else {
        	assertTrue(false);
        }
    }
    
    @Test
    public void testTargets() {
        ComputerPlayer p = new ComputerPlayer("computerPlayer", "r", Board.getInstance().getCell(2, 7));
        p.choosePosition(4); 
    	Map<BoardCell, Integer> cellMap = new HashMap<BoardCell, Integer>();
        for(BoardCell possibleCells : Board.getInstance().getTargets()) {
        	cellMap.put(possibleCells, 0);
        }
        for(int i = 0; i < 1000; i++) {
        	p.setLocation(Board.getInstance().getCell(2, 7));
            p.choosePosition(4);
            cellMap.put(p.getLocation(), cellMap.get(p.getLocation()) + 1);
        }
        for(Map.Entry<BoardCell, Integer> tableValue : cellMap.entrySet()) {
        	System.out.println(tableValue);
        	if (tableValue.getValue() < 30) {
        		assertTrue(false);
        	}
        }
        assertTrue(true);
    }
}
