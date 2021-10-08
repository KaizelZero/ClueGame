package game;

public class Room {
    private String room;
    private String name;
    private BoardCell centerCell;
    private BoardCell labelCell;
    
    
    
	public Room(char room) {
		super();
		this.room = Character.toString(room);
	}

    public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public BoardCell getCenterCell() {
		return centerCell;
	}
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	public BoardCell getLabelCell() {
		return labelCell;
	}
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

}
