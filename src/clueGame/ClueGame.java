package clueGame;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

public class ClueGame extends JFrame{
    
	
    GameControlPanel controlPanel;
	GameCardPanel cardPanel;
	static ClueGame clueGame;
	

	public ClueGame(Board board) {
		setTitle("Clue Game");
		setSize(750, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		controlPanel = new GameControlPanel();
		cardPanel = new GameCardPanel();
		
		add(controlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
	}
	
	
	
	public static void main(String[] args) {
		Board board = Board.getInstance(); // Only creates one instance of the board
		board.setConfigFiles("bin/data/Clue Excel Diagram2.csv", "bin/data/ClueSetup.txt");
		board.initialize();
		
		clueGame = new ClueGame(board);
		clueGame.add(board, BorderLayout.CENTER);
		clueGame.repaint();
		clueGame.setVisible(true);
	}
}
