package clueGame;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {

	// Variables
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private BoardCell[][] board;
	private Map<String, Room> roomMap = new HashMap<String, Room>();
	private ArrayList<String> roomTracker = new ArrayList<String>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private static Board theInstance = new Board();
	private Solution gameSolution;
	private HumanPlayer humanPlayer;
	static int cols = 0;
	static int rows = 0;
	private int currentPlayerIndex;
	File layoutCSV;
	File layoutText;

	private Board() {// constructor is private to ensure only one can be created
		super();
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
					this.getCell(cRow, cCol).setRoom(true); // Closet cells are set to rooms to simplify code
				} else if (inCell.equals("W")) {

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
				if (!adjCell.getOccupied() && !adjCell.isRoom() || adjCell.getCellRoom().getCenterCell() == adjCell) { // Checks
																														// if
																														// current
																														// cell
																														// is
																														// not
																														// occupied
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
		if (board[row][col].isRoom()) {
			if (board[row][col].isRoomCenter()) { // If the cell is a super-room (room center) then add all doors and
													// portals to the adj list for that cell
				adjList.addAll(roomMap.get(String.valueOf(this.getCell(row, col).getCellRoom().getRoom().charAt(0)))
						.getRoomDoors());
			}
		} else {
			if (row > 0) { // If the cell we are calculating adj for is not a room or doorway, then simply
							// add all adj non-room cells to it
				if (!board[row - 1][col].isRoom()) {
					adjList.add(board[row - 1][col]);
				}
			}
			if (row < rows - 1) {
				if (!board[row + 1][col].isRoom()) {
					adjList.add(board[row + 1][col]);
				}
			}
			if (col < cols - 1) {
				if (!board[row][col + 1].isRoom()) {
					adjList.add(board[row][col + 1]);
				}
			}
			if (col > 0) {
				if (!board[row][col - 1].isRoom()) {
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

	// REQUIREMENT: Check an accusation
	public boolean checkAccusation(Card person, Card room, Card weapon) {
		if (person == gameSolution.person && room == gameSolution.room && weapon == gameSolution.weapon) {
			return true;
		}
		return false;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	// Player disproves suggestion and handle a suggestion made
	public Card handleSuggestion(Player currentPlayer, Card person, Card room, Card weapon) {
		// This for loop round robins and starts at the player making a suggestion
		for (int i = playerList.indexOf(currentPlayer) + 1;; i = (i + 1) % playerList.size()) {
			currentPlayer = playerList.get(i - 1);
			ArrayList<Card> matchingCards = new ArrayList<Card>();
			for (Card c : playerList.get(i).getHand()) {
				if (c == person) {
					matchingCards.add(c);
				} else if (c == room) {
					matchingCards.add(c);
				} else if (c == weapon) {
					matchingCards.add(c);
				}
			}
			// Return card based on how many matching cards
			if (matchingCards.size() == 1) {
				return matchingCards.get(0);
			} else if (matchingCards.size() > 1) {
				return matchingCards.get((int) Math.floor(Math.random() * (matchingCards.size())));
			}
			if (i == playerList.indexOf(currentPlayer) - 1
					|| (i == playerList.size() - 1 && playerList.indexOf(currentPlayer) == 0)) {
				return null; // Returns null if no matching cards done
			}
		}
	}
}
