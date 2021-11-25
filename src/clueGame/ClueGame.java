package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class ClueGame extends JFrame implements ActionListener {

	GameControlPanel controlPanel;
	GameCardPanel cardPanel;
	static ClueGame clueGame;
	private static HumanPlayer player;
	private static String layoutName = "bin/data/Clue Excel Diagram2.csv";
	private static String setupName = "bin/data/ClueSetup.txt";

	public ClueGame(Board board) {
		setTitle("Clue Game");
		setSize(875, 875);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		controlPanel = new GameControlPanel();
		cardPanel = new GameCardPanel();

		add(controlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
		Board.getInstance().setPanels(controlPanel, cardPanel);
		
		JMenuBar menuBar = new JMenuBar();
    	JMenu file = new JMenu("File");
    	JMenuItem reset = new JMenuItem("Reset");
    	JMenuItem exit = new JMenuItem("Exit");
    	
		// menu bar
        exit.addActionListener(this);
        reset.addActionListener(this);
        
        file.add(reset);
        file.add(exit);
        
        menuBar.add(file);
        
        add(menuBar, BorderLayout.NORTH);
		
	}
	

	public void setNewTurn(Player p, int roll) {
		controlPanel.setTurn(p, roll);
	}
	
	public static ClueGame getClueGame() {
		return clueGame;
	}

	public static void main(String[] args) {
		Board board = Board.getInstance(); // Only creates one instance of the board
		board.setConfigFiles(layoutName, setupName);
		board.initialize();
		player = board.getHumanPlayer();
		clueGame = new ClueGame(board);
		clueGame.add(board, BorderLayout.CENTER);
		clueGame.repaint();
		clueGame.setVisible(true);
		JLabel message = new JLabel("<html>You are " + player.getName() + " and your color is "
				+ player.getColorString() + ".<br>Can you find the solution<br>before the Computer Players?</html>");
		JOptionPane.showMessageDialog(clueGame, message);
		Board.getInstance().nextPlayer();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String menu = e.getActionCommand();
		switch(menu) {
			case "Exit" :
				System.exit(0);
				break;
			case "Reset":
				dispose();
				main(new String[0]);
				break;
		}
		
	}

}
