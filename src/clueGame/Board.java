package clueGame;

import java.util.*;
import java.util.Map.Entry;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel {

	// Variables
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private BoardCell[][] board;
	private Map<String, Room> roomMap = new HashMap<String, Room>();
	private ArrayList<String> roomTracker = new ArrayList<String>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private int currentPlayer = -1;
	private static Board theInstance = new Board();
	private Solution gameSolution;
	private HumanPlayer humanPlayer;
	static int cols = 0;
	static int rows = 0;
	private Player resultPlayer;
	private Card result;

	File layoutCSV;
	File layoutText;
	
	GameControlPanel controlPanel;
	GameCardPanel cardPanel;
	public void setPanels(GameControlPanel gc, GameCardPanel gcp) {
		this.controlPanel = gc;
		this.cardPanel = gcp;
	}

	private Board() {// constructor is private to ensure only one can be created
		super();
		addMouseListener(new BoardListener());

	}

	public static Board getInstance() { // this method returns the only Board
		return theInstance;
	}

	public void initialize() { // initialize the board (since we are using singleton pattern)
		roomMap.clear();
		roomTracker.clear();
		deck.clear();
		playerList.clear();
		try {
			loadSetupConfig();
		} catch (BadConfigFormatException e1) {
			System.out.println("Check your setup.txt file for formatting issues");
			e1.printStackTrace();
		}
		try {
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			System.out.println("Ensure your .csv file is correctly formatted and try again.");
		}
	}

	public void setConfigFiles(String csv, String text) { // Sets config files to load in
		layoutCSV = new File(csv);
		layoutText = new File(text);
	}

	public void loadSetupConfig() throws BadConfigFormatException { // Loads the setup.txt file, creating the base rooms
																	// and naming them
		deck.clear();
		Scanner layoutIn = null;
		try {
			layoutIn = new Scanner(layoutText);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (layoutIn.hasNext()) {
			String[] newLine = layoutIn.nextLine().split(", ");
			if (newLine[0].charAt(0) == '/') {
				continue;
			}

			Card inCard;
			switch (newLine[0]) { // Add new card to the deck
			case "Weapon":
				inCard = new Card(newLine[1], CardType.WEAPON, newLine[2]);
				deck.add(inCard);
				break;
			case "Room":
				inCard = new Card(newLine[1], CardType.ROOM, newLine[2]);
				deck.add(inCard);
				break;
			case "Person":
				inCard = new Card(newLine[1], CardType.PERSON, newLine[2]);
				Player player;

				if (playerList.size() == 0) {
					humanPlayer = new HumanPlayer(newLine[1], newLine[2], Integer.parseInt(newLine[3]),
							Integer.parseInt(newLine[4]));
					player = humanPlayer;
				} else {
					player = new ComputerPlayer(newLine[1], newLine[2], Integer.parseInt(newLine[3]),
							Integer.parseInt(newLine[4]));
				}
				playerList.add(player);
				deck.add(inCard);
				break;
			case "Space":
				break;
			default:
				throw new BadConfigFormatException("Check your setup.txt, one of the fields is not correct.");
			}
			if (newLine[0].equals("Space") || newLine[0].equals("Room")) {
				Room tempRoom = new Room(newLine[2]);
				tempRoom.setName(newLine[1]);
				tempRoom.setRoomCard(deck.get(deck.size() - 1));
				roomTracker.add(newLine[2]);
				roomMap.put(newLine[2], tempRoom);
			}
		}
		layoutIn.close();
		deal();
	}

	private void generateCells(int rows, int cols) { // Generates the actual BoardCells and places them in the array
														// based on data found in the .csv file
		board = new BoardCell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				board[i][j] = new BoardCell(i, j);
			}
		}
	}

	public void loadLayoutConfig() throws BadConfigFormatException { // Loads the .csv file
		int cRow = 0, cCol = -1;
		Scanner csvIn = null;
		try {
			csvIn = new Scanner(layoutCSV);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (csvIn.hasNext()) { // Initial read through allows for number of columns and rows to be calculated
			String[] newLine = csvIn.nextLine().split(",");
			if (cCol != -1 && cCol != newLine.length) {
				throw new BadConfigFormatException("The number of columns in this .csv file is inconsistant");
			}
			cCol = newLine.length;
			cRow++;
		}
		csvIn.close();
		rows = cRow;
		cols = cCol;
		this.generateCells(cRow, cCol);
		cRow = 0;
		cCol = 0;
		csvIn = null;
		try {
			csvIn = new Scanner(layoutCSV);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (csvIn.hasNext()) { // Second run through creates rooms and assigns them to corisponding cells based
									// on read in data
			String[] newLine = csvIn.nextLine().split(",");
			for (String inCell : newLine) {
				if (!roomTracker.contains(inCell.substring(0, 1))) {
					throw new BadConfigFormatException("The room loaded in your " + layoutCSV.getName()
							+ " file does not exist within " + layoutText.getName());
				}
				this.getCell(cRow, cCol).getCellRoom().setRoom(inCell.substring(0, 1));
				this.getCell(cRow, cCol).getCellRoom().setName(roomMap.get(inCell.substring(0, 1)).getName());
				if (inCell.equals("X")) { // Base cases (to be expanded)
					this.getCell(cRow, cCol).setUnused(); // Unused Cells
				} else if (inCell.equals("W")) {
					this.getCell(cRow, cCol).setWalkway();
				} else if (inCell.length() == 1) {
					this.getCell(cRow, cCol).setRoom(true);
				} else if (inCell.charAt(1) == '#') { // If label
					this.getCell(cRow, cCol).setRoom(true);
					roomMap.get(inCell.substring(0, 1)).setLabelCell(this.getCell(cRow, cCol));
					this.getCell(cRow, cCol).getCellRoom().setLabelCell(this.getCell(cRow, cCol));
				} else if (inCell.charAt(1) == '*') { // If centerCell
					this.getCell(cRow, cCol).setRoom(true);

					roomMap.get(inCell.substring(0, 1)).setCenterCell(this.getCell(cRow, cCol));
					this.getCell(cRow, cCol).getCellRoom().setCenterCell(this.getCell(cRow, cCol));
				} else if (inCell.charAt(1) == '^' || inCell.charAt(1) == '<' || inCell.charAt(1) == '>'
						|| inCell.charAt(1) == 'v') {
					this.getCell(cRow, cCol).setDoorDirection(inCell.charAt(1));
				} else {
					this.getCell(cRow, cCol).setSecretPassage(inCell.charAt(1));
				}
				cCol++;
			}
			cCol = 0;
			cRow++;
		}
		csvIn.close();

		for (Player p : playerList) {
			p.initializePosition();
		}

		addRoomDoorways();
		for (int i = 0; i < rows; i++) { // Calculates all cells adj lists
			for (int j = 0; j < cols; j++) {
				board[i][j].addAdjacency(calcAdj(i, j));
			}
		}
	}

	public void addRoomDoorways() { // Adds all of the doorways to the super-rooms they lead to (This inclues
									// portals)
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				BoardCell cell = this.getCell(i, j);
				if (cell.isDoorway()) {
					switch (cell.getDoorDirection()) {
					case UP:
						// Get cell at the top with i-1
						roomMap.get(this.getCell(i - 1, j).getCellRoom().getRoom()).addDoor(cell);
						cell.getCellRoom().setCenterCell(
								roomMap.get(this.getCell(i - 1, j).getCellRoom().getRoom()).getCenterCell());
						break;
					case DOWN:
						// Get cell below with i+1
						roomMap.get(this.getCell(i + 1, j).getCellRoom().getRoom()).addDoor(cell);
						cell.getCellRoom().setCenterCell(
								roomMap.get(this.getCell(i + 1, j).getCellRoom().getRoom()).getCenterCell());
						break;
					case LEFT:
						// Get the cell to the left if j-1
						roomMap.get(this.getCell(i, j - 1).getCellRoom().getRoom()).addDoor(cell);
						cell.getCellRoom().setCenterCell(
								roomMap.get(this.getCell(i, j - 1).getCellRoom().getRoom()).getCenterCell());
						break;
					case RIGHT:
						// Get cell at the right with j+1
						roomMap.get(this.getCell(i, j + 1).getCellRoom().getRoom()).addDoor(cell);
						cell.getCellRoom().setCenterCell(
								roomMap.get(this.getCell(i, j + 1).getCellRoom().getRoom()).getCenterCell());
						break;
					case NONE:
						break;
					}
				} else if (cell.isSecretPassage()) {
					Room temp = roomMap.get(String.valueOf(cell.getSecretPassage()));
					Room temp2 = roomMap.get(cell.getCellRoom().getRoom());
					temp.addDoor(temp2.getCenterCell());
				}
			}
		}
	}

	public void calcTargets(BoardCell startCell, int pathlength) { // Caller function for findAllTargets
		targets.clear();// Clears out old data to ensure the sets only contain data from this call
		visited.clear();
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}

	private void findAllTargets(BoardCell thisCell, int numSteps) { // Recursively searches through adj cells for
																	// targets given a roll
		for (BoardCell adjCell : thisCell.getAdjList()) {
			if (visited.contains(adjCell) || (adjCell.getOccupied() && adjCell.getCellRoom().getCenterCell() != adjCell)
					|| (adjCell.getOccupied() && thisCell.getCellRoom().getCenterCell() == thisCell)) { // If visited,
																										// skip that
																										// cell
				continue;
			}
			visited.add(adjCell);
			if (numSteps == 1) { // If at limit of dice roll, then the current cell must be a target
				if ((!adjCell.getOccupied() && !adjCell.isRoom() && !adjCell.isUnused())
						|| adjCell.getCellRoom().getCenterCell() == adjCell) { // Checks if current cell is not occupied
					targets.add(adjCell);
				}
			} else if (adjCell.getCellRoom().getCenterCell() == adjCell) { // If the adj cell is a room center, then
																			// stop the recursive call and add that room
																			// to the targets list
				targets.add(adjCell);
			} else { // Recursively checks next targets
				findAllTargets(adjCell, numSteps - 1);
			}
			visited.remove(adjCell); // Removes adjCell from visited for remaining recursive calls
		}
	}

	public Set<BoardCell> calcAdj(int row, int col) { // Calculates adjacent cells from a given cell
		Set<BoardCell> adjList = new HashSet<BoardCell>();
		if (board[row][col].isRoom() || board[row][col].isUnused()) {
			if (board[row][col].isRoomCenter()) { // If the cell is a super-room (room center) then add all doors and
													// portals to the adj list for that cell
				adjList.addAll(roomMap.get(String.valueOf(this.getCell(row, col).getCellRoom().getRoom().charAt(0)))
						.getRoomDoors());
			}
		} else {
			if (row > 0) { // If the cell we are calculating adj for is not a room or doorway, then simply
							// add all adj non-room cells to it
				if (!board[row - 1][col].isRoom() && !board[row - 1][col].isUnused()) {
					adjList.add(board[row - 1][col]);
				}
			}
			if (row < rows - 1) {
				if (!board[row + 1][col].isRoom() && !board[row + 1][col].isUnused()) {
					adjList.add(board[row + 1][col]);
				}
			}
			if (col < cols - 1) {
				if (!board[row][col + 1].isRoom() && !board[row][col + 1].isUnused()) {
					adjList.add(board[row][col + 1]);
				}
			}
			if (col > 0) {
				if (!board[row][col - 1].isRoom() && !board[row][col - 1].isUnused()) {
					adjList.add(board[row][col - 1]);
				}
			}
			if (board[row][col].isDoorway()) { // Add room centers to the adj list of that room's doors
				adjList.add(board[row][col].getCellRoom().getCenterCell());
			}
		}
		return adjList;
	}

	// Returns targets ArrayList
	public Set<BoardCell> getTargets() {
		return targets;
	}

	// Returns the instance of a call at a certain position
	public BoardCell getCell(int row, int col) {
		return board[row][col];
	}

	public Room getRoom(BoardCell cellBoardCell) {
		return cellBoardCell.getCellRoom();
	}

	public Room getRoom(char roomChar) {
		return roomMap.get(String.valueOf(roomChar));
	}

	public Room getRoom(String roomString) {
		return roomMap.get(String.valueOf(roomString));
	}

	public int getNumRows() {
		return rows;
	}

	public int getNumColumns() {
		return cols;
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return getCell(row, col).getAdjList();
	}

	public void deal() {
		ArrayList<Card> tempDeck = new ArrayList<Card>();
		tempDeck.addAll(deck);
		for (Player p : playerList) { // Clear all players hands before dealing
			p.getHand().clear();
		}
		Card[] cardSet = new Card[] { tempDeck.get(0), tempDeck.get(tempDeck.size() - 1), tempDeck.get(0) }; // Solution
																												// set
																												// definition
		while (true) { // Randomized solution set
			int rand = (int) Math.floor(Math.random() * (tempDeck.size()));
			if (tempDeck.get(rand).getType() == CardType.PERSON) {
				cardSet[0] = deck.get(rand);
			} else if (tempDeck.get(rand).getType() == CardType.ROOM) {
				cardSet[1] = tempDeck.get(rand);
			} else {
				cardSet[2] = tempDeck.get(rand);
			}
			if (cardSet[0].getType() == CardType.PERSON && cardSet[1].getType() == CardType.ROOM
					&& cardSet[2].getType() == CardType.WEAPON) {
				gameSolution = new Solution(cardSet[0], cardSet[1], cardSet[2]);
				tempDeck.remove(cardSet[0]);
				tempDeck.remove(cardSet[1]);
				tempDeck.remove(cardSet[2]);
				break;
			}
		}
		while (tempDeck.size() > 0) {
			for (Player p : playerList) { // Randomly looks for a card and gives it to the next player
				if (tempDeck.size() > 0) { // If there are more cards, then continue dealing
					int rand = (int) Math.floor(Math.random() * (tempDeck.size()));
					p.updateHand(tempDeck.get(rand));
					tempDeck.remove(rand); // Remove the delt card from the pool
				}
			}
		}
	}

	// REQUIREMENT: Check an accusation
	public boolean checkAccusation(Card person, Card room, Card weapon) {
		if (person.getCardName().equals(gameSolution.person.getCardName()) && room.getCardName().equals(gameSolution.room.getCardName()) && weapon.getCardName().equals(gameSolution.weapon.getCardName())) {
			return true;
		}
		return false;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	// Player disproves suggestion and handle a suggestion made
	@SuppressWarnings("unused")
	public Card handleSuggestion(Player currentPlayer, Card person, Card room, Card weapon) {
		if (currentPlayer.getLocation().isRoomCenter()) {
			for (Player p : playerList) {
				if (p.getName() == person.getCardName()) {
					p.setLocation(currentPlayer.getLocation());
				}
			}
		}
		int current = playerList.indexOf(currentPlayer);
		int count = (playerList.indexOf(currentPlayer) + 1) % playerList.size();
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		while(current != count) {		// This for loop round robins and starts at the player making a suggestion
			
			if (false) {
				currentPlayer = playerList.get(playerList.size() - 1);
			} else {
				currentPlayer = playerList.get(count);
			}
			matchingCards.clear();
			for (Card c : playerList.get(count).getHand()) {
				if (c.getCardName().equals(person.getCardName())) {
					matchingCards.add(c);
				} else if (c.getCardName().equals(room.getCardName())) {
					matchingCards.add(c);
				} else if (c.getCardName().equals(weapon.getCardName())) {
					matchingCards.add(c);
				}
			}
			// Return card based on how many matching cards
			if (matchingCards.size() == 1) {
				resultPlayer = currentPlayer;
				result = matchingCards.get(0);
				break;
			} else if (matchingCards.size() > 1) { //Choose a random card if there are multiple matches
				resultPlayer = currentPlayer;
				result = matchingCards.get((int) Math.floor(Math.random() * (matchingCards.size())));
				break;
			}
			count = (count + 1) % playerList.size();
		}
		if(Board.getInstance().currentPlayer == 0) {
			if(matchingCards.size() == 0) { //No matching cards
				controlPanel.setGuess(person.getCardName() + ", " + room.getCardName() + ", " + weapon.getCardName()); //Suggestion disproved
				controlPanel.setGuessResult("Suggestion Disproved");
				controlPanel.updateDisplay(this.getHumanPlayer());
				result = this.getHumanPlayer().getHand().get(0);
			}else { //Update display pannel if there was a matching card
				controlPanel.setGuess(person.getCardName() + ", " + room.getCardName() + ", " + weapon.getCardName()); //Suggestion proven
				controlPanel.setGuessResult(result.getCardName());
				controlPanel.updateDisplay(resultPlayer);
				cardPanel.updateDisplay();
			}
		} else {
			if(matchingCards.size() == 0) { //No matching cards
				controlPanel.setGuess(person.getCardName() + ", " + room.getCardName() + ", " + weapon.getCardName()); //Suggestion disproved
				controlPanel.updateDisplay(this.getHumanPlayer());
				result = this.getHumanPlayer().getHand().get(0);
			}else { //Update display pannel if there was a matching card
				controlPanel.setGuess(person.getCardName() + ", " + room.getCardName() + ", " + weapon.getCardName()); //Suggestion proven
				controlPanel.setGuessResult("Suggestion Disproved");
				controlPanel.updateDisplay(resultPlayer);
				cardPanel.updateDisplay();
			}
		}
		this.repaint();
	
		return result; //Return the matching card (if there is one)
		}

	public void paintComponent(Graphics g) { // Paint in all cells and players of the board
		super.paintComponent(g);
		int xOffset, yOffset;
		int width = this.getWidth() / cols;
		int height = this.getHeight() / rows;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				xOffset = j * width;
				yOffset = i * height;
				getCell(i, j).drawCell(xOffset, yOffset, width, height, g, this);
				int offsetLine = roomMap.get(this.getCell(i, j).getCellRoom().getRoom()).getOccupants() - 1;
				for (Player p : playerList) {
					if (p.location == this.getCell(i, j)) {

						if (roomMap.get(this.getCell(i, j).getCellRoom().getRoom()).getOccupants() > 0) {
							p.drawPlayer(xOffset - 10 * offsetLine, yOffset, width, height, g, this);
							offsetLine--;
						} else {
							p.drawPlayer(xOffset, yOffset, width, height, g, this);
						}
						Board.getInstance().repaint();
					}
				}
			}
		}
	}

	public void calculateOccupants() {
		for (Entry<String, Room> room : roomMap.entrySet()) {
			room.getValue().setOccupants(-room.getValue().getOccupants()); // Set occupants to 0
			for (Player player : playerList) {
				if (room.getValue().getCenterCell() == player.getLocation()) { // Add an occupant for each person seen
																				// in that cell
					room.getValue().setOccupants(1);
				}
			}
		}
	}

	public void nextPlayer() { // Called by next player button, allows the game to properly process all it
								// needs to do
		boolean currentTurn = false;
		if (currentPlayer == 0) {
			for (BoardCell[] row : board) {
				for (BoardCell colorChecker : row) {
					if (colorChecker.getColor() == Color.magenta) {
						currentTurn = true;
					}
				}
			}
			playerList.get(currentPlayer).setMoved(false);
		}
		if (currentTurn) {
			JOptionPane.showMessageDialog(this, "You must first move your piece!");
		} else {
			if(currentPlayer > 0) { 
				if(((ComputerPlayer) playerList.get(currentPlayer)).getSeen().size() + 3 == this.getDeck().size()) { //If the computer has found the solution, make the accusation!
					Card suggestedPerson = null; Card suggestedWeapon = null; Card suggestedRoom = null;
					for(Card c : this.getDeck()) {
						if(!((ComputerPlayer) playerList.get(currentPlayer)).getSeen().contains(c)) {
							if(c.getType() == CardType.PERSON) {
								suggestedPerson = c;
							}else if(c.getType() == CardType.WEAPON) {
								suggestedWeapon = c;
							}else {
								suggestedRoom = c;
							}
						}
					}
					JOptionPane.showMessageDialog(this, playerList.get(currentPlayer).getName() + " won the game! The solution was: " + suggestedPerson + " with the " + suggestedWeapon + " in the " + suggestedRoom);
            		System.exit(0);
				}
			}
			int roll = (int) Math.floor(Math.random() * (6)) + 1;
			currentPlayer++;
			if (currentPlayer >= playerList.size()) {
				currentPlayer = 0;
			}
			ClueGame.getClueGame().setNewTurn(playerList.get(currentPlayer), roll);
			playerList.get(currentPlayer).diceRoll = roll;
			this.calcTargets(playerList.get(currentPlayer).getLocation(), roll);
			if (currentPlayer != 0) { //Run the computer player's turn

				playerList.get(currentPlayer).getLocation().setOccupied(false);
				int rand = (int) Math.floor(Math.random() * (getTargets().size()));
				playerList.get(currentPlayer).setLocation((BoardCell) this.getTargets().toArray()[rand]);
				playerList.get(currentPlayer).getLocation().setOccupied(true);
				if(playerList.get(currentPlayer).getLocation().isRoomCenter() && !playerList.get(currentPlayer).getLocation().isDoorway() ) {
					handleComputerSuggestion(currentPlayer);
				}
			} else {
				for (BoardCell cell : this.getTargets()) {
					cell.setColor(Color.magenta);
				}
			}
			this.repaint();
		}

	}
	
	private void handleComputerSuggestion(int player) { //Suggestion prep function for computers (random results)
		Card suggestedPerson = null; Card suggestedWeapon = null; 
		Card suggestedRoom = roomMap.get(playerList.get(player).getLocation().getCellRoom().getRoom()).getRoomCard();
		
		while(suggestedPerson == null || suggestedWeapon == null) {
			int rand = (int) Math.floor(Math.random() * (this.getDeck().size()));
			if(this.getDeck().get(rand).getType() == CardType.PERSON && !playerList.get(player).getHand().contains(this.getDeck().get(rand))) {
				suggestedPerson = this.getDeck().get(rand);
			}else if(this.getDeck().get(rand).getType() == CardType.WEAPON && !playerList.get(player).getHand().contains(this.getDeck().get(rand))) {
				suggestedWeapon = this.getDeck().get(rand);
			}
		}
		((ComputerPlayer) this.playerList.get(player)).getSeen().add(handleSuggestion(playerList.get(player), suggestedPerson, suggestedRoom, suggestedWeapon));
	}

	private class BoardListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {// Checks if the player is clicking on a correct cell, then moves them
												// there
			if (currentPlayer == 0 && playerList.get(currentPlayer).getMoved() == false) {
				for (BoardCell[] row : board) {
					for (BoardCell cell : row) {
						if ((cell.getXPos() < e.getX() && cell.getXPos() + cell.getWidth() > e.getX())
								&& (cell.getYPos() < e.getY() && cell.getYPos() + cell.getHeight() > e.getY())) {
							if (targets.contains(cell)) {
								playerList.get(currentPlayer).getLocation().setOccupied(false);
								playerList.get(currentPlayer).setLocation(cell);
								playerList.get(currentPlayer).getLocation().setOccupied(true);
								if (playerList.get(currentPlayer).getLocation().isRoomCenter() && !playerList.get(currentPlayer).getLocation().isDoorway()) {
									SuggestionPanel panel = new SuggestionPanel(false);
									panel.setVisible(true);
								}

								for (BoardCell colorCell : Board.getInstance().getTargets()) {
									colorCell.setColor(Color.white);
								}
								Board.getInstance().repaint();
								playerList.get(currentPlayer).setMoved(true);
							} else {
								JOptionPane.showMessageDialog(Board.getInstance(), "You cannot move there!", "Message",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
				}
			} else {
				// JOptionPane.showMessageDialog(Board.getInstance(), "You must wait until your
				// turn!", "Message", currentPlayer);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
	
	public GameCardPanel getCardPanel() {
		return cardPanel;
	}
	
	public Card getResult() {
    	return result;
    }
	public ArrayList<Card> getDeck() {
		return deck;
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}

	public Solution getSolution() {
		return gameSolution;
	}

	public void setSolution(Card person, Card room, Card weapon) {
		gameSolution = new Solution(person, room, weapon);
	}

}