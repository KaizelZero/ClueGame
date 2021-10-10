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
	private Set<Room> roomList = new HashSet<Room>();
	static int cols = 0;
	static int rows = 0;
	File layoutCSV;
	File layoutText;

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
    }
    public static Board getInstance() { // this method returns the only Board
        return theInstance;
    }
    public void initialize(){ // initialize the board (since we are using singleton pattern)
    	System.out.println('A');
    	loadLayoutConfig();
    	loadSetupConfig();
    }
    private void generateCells(int rows, int cols) {
    	board = new BoardCell[rows][cols];
	 	for(int i = 0; i < rows; i++) {
	 		for(int j = 0; j < cols; j++) {
	 			board[i][j] = new BoardCell(i, j);
	 		}
	 	}
	 	for(int i = 0; i < rows; i++) {
	 		for(int j = 0; j < cols; j++) {
	 			board[i][j].addAdjacency(calcAdj(i, j));
	 		}
	 	}
    }
    
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
		int cRow = 0, cCol = 0;
		Scanner csvIn = null;
		try {
			csvIn = new Scanner(layoutCSV);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(csvIn.hasNext()){
			String[] newLine = csvIn.nextLine().split(",");
			cCol = newLine.length;
			cRow++;
		}
		csvIn.close();
		rows = cRow;
		cols = cCol;
		System.out.println(cRow + " " + cCol);
		this.generateCells(cRow, cCol);
		cRow = 0; cCol = 0; csvIn = null;
		try {
			csvIn = new Scanner(layoutCSV);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (csvIn.hasNext()) {
			String[] newLine = csvIn.nextLine().split(",");
			for(String inCell : newLine) {
				this.getCell(cRow, cCol).getCellRoom().setRoom(inCell.substring(0, 1));
				for(Room r : roomList) {
					if(r.getRoom().equals(inCell.substring(0, 1))) {
						this.getCell(cRow, cCol).getCellRoom().setName(r.getName());
					}
				}
				if(inCell == "X"){
					continue;
				}else if(inCell == "W" ){
					
				}else if(inCell.length() == 1){
					this.getCell(cRow, cCol).setRoom(true);
					
				}else if(inCell.charAt(1) == '#') {
					this.getCell(cRow, cCol).setRoom(true);
					for(Room r : roomList) {
						if(r.getRoom().equals(inCell.substring(0, 1))) {
							r.setLabelCell(this.getCell(cRow, cCol));
							this.getCell(cRow, cCol).getCellRoom().setLabelCell(this.getCell(cRow, cCol));
						}
					}
				}else if(inCell.charAt(1) == '*') {
					this.getCell(cRow, cCol).setRoom(true);
					for(Room r : roomList) {
						if(r.getRoom().equals(inCell.substring(0, 1))) {
							r.setCenterCell(this.getCell(cRow, cCol));
							this.getCell(cRow, cCol).getCellRoom().setCenterCell(this.getCell(cRow, cCol));
						}
					}
				}else if(inCell.charAt(1) == '^' || inCell.charAt(1) == '<' || inCell.charAt(1) == '>' || inCell.charAt(1) == 'v') {
					this.getCell(cRow, cCol).setDoorDirection(inCell.charAt(1));
				}else {
					this.getCell(cRow, cCol).setSecretPassage(inCell.charAt(1));
				}
				cCol++;
			}
			cCol = 0;
			cRow++;
	    }
		csvIn.close();
	}

	public void loadLayoutConfig(){
		Scanner layoutIn = null;
		System.out.println('b');
		try {
			layoutIn = new Scanner(layoutText);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (layoutIn.hasNext()) {
			String[] newLine = layoutIn.nextLine().split(", ");
			if(newLine[0].charAt(0) == '/' ) {
				continue;
			}
			Room tempRoom = new Room(newLine[2]);
			tempRoom.setName(newLine[1]);
			roomList.add(tempRoom);
	    }
		layoutIn.close();
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
		return cellBoardCell.getCellRoom();
	}
	public Room getRoom(char roomChar){
		for(Room r : roomList) {
			if(r.getRoom().equals(Character.toString(roomChar))) {
				return r;
			}
		}
		return null;
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
}