package game;

public class Room {
    private String room;
    private String name;
    private BoardCell centerCell = null;
    private BoardCell labelCell = null;
    
	public Room(String roomLetter) {
		super();
		this.room = roomLetter;
	}
	public Room() {
		super();
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
		return this.labelCell;
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
