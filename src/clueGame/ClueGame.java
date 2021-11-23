package clueGame;

import java.awt.BorderLayout;

import javax.swing.*;

@SuppressWarnings("serial")
public class ClueGame extends JFrame {

	GameControlPanel controlPanel;
	GameCardPanel cardPanel;
	static ClueGame clueGame;
	private static HumanPlayer player;

	public ClueGame(Board board) {
		setTitle("Clue Game");
		setSize(750, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		controlPanel = new GameControlPanel();
		cardPanel = new GameCardPanel();

		add(controlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
	}

	public void setNewTurn(Player p, int roll) {
		controlPanel.setTurn(p, roll);
	}

	public void setNewGuess(Player p, String guess, String guessResult) {
		controlPanel.setGuess(guess);
		controlPanel.setGuessResult(guessResult);
	}
	
	public static ClueGame getClueGame() {
		return clueGame;
	}

	public GameControlPanel getControlPanel() {
		return controlPanel;
	}

	public static void main(String[] args) {
		Board board = Board.getInstance(); // Only creates one instance of the board
		board.setConfigFiles("bin/data/Clue Excel Diagram2.csv", "bin/data/ClueSetup.txt");
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
}
