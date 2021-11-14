package clueGame;

import java.util.HashSet;
import java.util.Set;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class BoardCell {
	// Variables
	private int row, column;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	private Room thisRoom = new Room();
	private char secretPassage = ']';
	private DoorDirection doorDirection = DoorDirection.NONE;
	private int xPos, yPos, width, height;
	private Color cellColor = Color.white;

	// isser's
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isDoorway;
	private boolean isWalkway;
	private boolean isUnused;

	// Constructor
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		isUnused = false;
	}

	public Room getCellRoom() {
		return this.thisRoom;
	}

	// Adds adjacent cells to adjacencyList
	public void addAdjacency(Set<BoardCell> adjacencyList) {
		this.adjList = adjacencyList;
	}

	// Returns the adjacencyList
	public Set<BoardCell> getAdjList() {
		return this.adjList;
	}

	// Checks whether the cell is occupied
	public void setOccupied(boolean newOccupied) {
		isOccupied = newOccupied;
	}

	// Returns whether the cell is occupied
	public boolean getOccupied() {
		return isOccupied;
	}

	// Returns if the cell is a label cell
	public boolean isLabel() {
		if (this.getCellRoom().getLabelCell() != null) {
			return true;
		}
		return false;
	}

	// Returns if the cell is the center of a room
	public boolean isRoomCenter() {
		if (this.getCellRoom().getCenterCell() != null) {
			return true;
		}
		return false;
	}

	// Returns if the cell is a doorway
	public boolean isDoorway() {
		if (doorDirection != DoorDirection.NONE) {
			return true;
		}
		return false;
	}

	// Returns doorway direction
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	// Specifies door direction
	public void setDoorDirection(char direction) {
		switch (direction) {
		case '^':
			this.doorDirection = DoorDirection.UP;
			break;
		case 'v':
			this.doorDirection = DoorDirection.DOWN;
			break;
		case '<':
			this.doorDirection = DoorDirection.LEFT;
			break;
		case '>':
			this.doorDirection = DoorDirection.RIGHT;
			break;
		}
	}

	public boolean isSecretPassage() {
		if (secretPassage != ']') {
			return true;
		} else {
			return false;
		}
	}

	// Returns the secret passage char
	public char getSecretPassage() {
		return secretPassage;
	}

	// Sets the secret passage char
	public void setSecretPassage(char pass) {
		this.secretPassage = pass;
	}

	public void drawCell(int row, int col, int width, int height, Graphics g, Board board) { //Code for drawing in the cells
		this.xPos = row;
		this.yPos = col;
		this.width = width;
		this.height = height;
		if (this.isWalkway || this.isDoorway()) {
			g.setColor(cellColor);
			g.fillRect(row, col, width, height);
			g.setColor(Color.black);
			g.drawRect(row, col, width, height);
		}
		if (this.isRoom) {
			if(cellColor == Color.magenta) {
				g.setColor(Color.green);
			}else {
				g.setColor(Color.gray);
			}
			g.fillRect(row, col, width, height);
			g.setColor(Color.black);
			g.drawRect(row, col, width, height);
		}
		if (this.isDoorway()) { //Draws door in correct position
			g.setColor(Color.yellow);
			switch (this.doorDirection) {
			case UP:
				g.fillRect(row, col, width, 5);
				break;
			case DOWN:
				g.fillRect(row, col + height - 5, width, 5);
				break;
			case LEFT:
				g.fillRect(row, col, 5, height);
				break;
			case RIGHT:
				g.fillRect(row + width - 5, col, 5, height);
				break;
			case NONE:
				break;
			}
		}
		if (this.isUnused) {
			g.setColor(Color.black);
			g.fillRect(row, col, width, height);
		}
		if (this.isSecretPassage()) {
			g.setColor(Color.cyan);
			g.fillRect(row, col, width, height);
			g.setColor(Color.black);
			g.drawString("S", row + width / 3, col + height / 2);
		}
		if (this.isRoomCenter() && !this.isDoorway()) { //Draws in room name
			g.setColor(Color.red);
			int offset = this.getCellRoom().getName().length() * 3;
			g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, g.getFont().getSize())); 
			g.drawString(this.getCellRoom().getName(), row - offset, col);
			g.setColor(Color.orange);
			g.setFont(new Font(g.getFont().getFontName(), Font.BOLD - 50, g.getFont().getSize())); 
		    g.drawString(this.getCellRoom().getName(), row - offset, col);
		}
	}

	// Checks whether the cell is in room
	public void setRoom(boolean isAssignRoom) {
		isRoom = isAssignRoom;
	}

	public void setWalkway() {
		this.isWalkway = true;
	}

	public void setUnused() {
		this.isUnused = true;
	}

	public boolean isRoom() {
		return isRoom;
	}

	public boolean isWalkway() {
		return isWalkway;
	}

	public boolean isUnused() {
		return isUnused;
	}
	public int getXPos() {
		return this.xPos;
	}
	public int getYPos() {
		return this.yPos;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public void setColor(Color newColor) {
		this.cellColor = newColor;
	}
	public Color getColor() {
		return this.cellColor;
	}
	@Override // Simple toString for debugging cells
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	
}
