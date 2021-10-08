package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Board {

	// Variables
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private BoardCell[][] board;
	private ArrayList<Room> roomList;
	final static int COLS = 25;
	final static int ROWS = 25;
	File layoutCSV;
	File layoutText;
	Room testRoom = new Room('a');

	public void calcTargets(BoardCell startCell, int pathlength) { //Caller function for findAllTargets
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}
	private void findAllTargets(BoardCell thisCell, int numSteps) { //Recursively searches through adj cells for targets given a roll
		for(BoardCell adjCell : thisCell.getAdjList()) {
			if(visited.contains(adjCell)){ //If visited, skip that cell
				continue;
			}
			visited.add(adjCell);
			if(numSteps == 1) { //If at limit of dice roll, then the current cell must be a target
				if(!adjCell.getOccupied() && !adjCell.getRoom()) { //Checks if current cell is not occupied
					targets.add(adjCell);
				}
			}else { //Recursively checks next targets
				findAllTargets(adjCell, numSteps - 1);
			}
			visited.remove(adjCell); //Removes adjCell from visited for remaining recursive calls
		}
	}
    
	//  public Board(int width, int height) { // Creates a Board using width and height, 
	//  	super();
	//  	board = new BoardCell[ROWS][COLS];
	//  	for(int i = 0; i < ROWS; i++) {
	//  		for(int j = 0; j < COLS; j++) {
	//  			board[i][j] = new BoardCell(i, j);
	//  		}
	//  	}
	//  	for(int i = 0; i < ROWS; i++) {
	//  		for(int j = 0; j < COLS; j++) {
	//  			board[i][j].addAdjacency(calcAdj(i, j));
	//  		}
	//  	}
	//  }

    private static Board theInstance = new Board();
    private Board()  {// constructor is private to ensure only one can be created
        super() ;
		board = new BoardCell[ROWS][COLS];
	 	for(int i = 0; i < ROWS; i++) {
	 		for(int j = 0; j < COLS; j++) {
	 			board[i][j] = new BoardCell(i, j);
	 		}
	 	}
	 	for(int i = 0; i < ROWS; i++) {
	 		for(int j = 0; j < COLS; j++) {
	 			board[i][j].addAdjacency(calcAdj(i, j));
	 		}
	 	}
    }
    public static Board getInstance() { // this method returns the only Board
        return theInstance;
    }
    public void initialize(){} // initialize the board (since we are using singleton pattern)

    
	public Set<BoardCell> calcAdj(int row, int col) { //Calculates adjacent cells from a given cell
		Set<BoardCell> adjList = new HashSet<BoardCell>();
		if(row > 0) {
			adjList.add(board[row - 1][col]);
		}
		if(row < 3) {
			adjList.add(board[row + 1][col]);
		}
		if(col > 0) {
			adjList.add(board[row][col - 1]);
		}
		if(col < 3) {
			adjList.add(board[row][col + 1]);
		}
		return adjList;
	}

	public void setConfigFiles(String csv, String text){
		layoutCSV = new File(csv);
		layoutText = new File(text);
		
		try {
			Scanner Text = new Scanner(layoutText);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public void loadSetupConfig(){
		Scanner csvIn = null;
		try {
			csvIn = new Scanner(layoutCSV);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (csvIn.hasNext()) {
			char current = csvIn.findInLine(".").charAt(0);
			if(current == 'X'){
				continue;
			}
			if(current != 'W' ){
				roomList = new ArrayList<Room>();
				roomList.add(new Room(current));
			}
	    }
	}

	public void loadLayoutConfig(){
		
	}

	// Returns targets ArrayList
	public Set<BoardCell> getTargets() {
		return targets;
	}

	// Returns the instance of a call at a certain position
	public BoardCell getCell(int row, int col) {
		return board[row][col];
	}
	public Room getRoom(BoardCell cellBoardCell){
		return testRoom;
	}
	public Room getRoom(char roomChar){
		return testRoom;
	}
	public int getNumRows() {
		return ROWS;
	}
	public int getNumColumns() {
		return COLS;
	}
}