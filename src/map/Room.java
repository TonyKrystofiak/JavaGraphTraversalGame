/**
 * Defines the properties of the Room object
 * @author Tony Krystofiak
 * @Date 12/12/2023
 */
package map;
import java.util.*;
import java.io.*;
public class Room implements Serializable {	
	public static final int ROOM_WIDTH = 26;
	public static final int ROOM_HEIGHT = 14;
	private boolean isStart;
	private boolean isFinish;
	private boolean hasKey;
	private boolean hasTopDoor;
	private boolean hasBottomDoor;
	private boolean hasLeftDoor;
	private boolean hasRightDoor;
	private boolean hasPlayer;
	private String[][] levelData = new String[ROOM_HEIGHT][ROOM_WIDTH];
	private int roomNumber;
	private boolean path;
	private boolean deadEnd;

	/**
	 * Constructs a new room object
	 * @param top if it has a top door
	 * @param left if it has a left door
	 * @param bottom if it has a bottom door
	 * @param right if it has a right door
	 * @param roomNumber the room number
	 */
	public Room(boolean top, boolean left, boolean bottom, boolean right, int roomNumber) {
		this.hasPlayer = false;
		this.isStart = false;
		this.isFinish = false;
		this.hasKey = false;
		this.hasTopDoor = top;
		this.hasBottomDoor = bottom;
		this.hasLeftDoor = left;
		this.hasRightDoor = right;
		this.roomNumber = roomNumber;
		this.path = false;
		this.deadEnd = false;
		obtainLevelData();
		
	}
	/**
	 * Reads the level txt file to generate level structure
	 */
	private void obtainLevelData() {
		try {//C:\Users\apkry\eclipse-workspacev2\CS200FinalProject\src\map\LevelData0.txt
			Scanner scan = new Scanner(new File("res\\LevelData" + this.getRoomNumber() + ".txt"));
			int row = 0;
			int column = 0;
			while (scan.hasNextLine()) {  	   			    	
		        String line = scan.nextLine();
		        Scanner lineScan = new Scanner(line);
		        while (lineScan.hasNext()) {
		        	levelData[row][column] = lineScan.next();
		        	//System.out.print(levelData[row][column] + " ");
		        	column++;
		        }
		        column = 0;
		        row++;
			}
			scan.close();  
//			for (int i = 0; i < ROOM_HEIGHT; i++) {
//				System.out.println(Arrays.toString(levelData[i]));
//			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Determines if the room has a player in it
	 * @return
	 */
	public boolean hasPlayer() {
		return hasPlayer;
	}
/**
 * sets the hasPlayer value
 * @param hasPlayer sets if a player is in the room
 */
	public void setPlayer(boolean hasPlayer) {
		this.hasPlayer = hasPlayer;
	}
	/**
	 * Determines the room is the start
	 * @return if the room is the start
	 */
	public boolean isStart() {
		return isStart;
	}
	/**
	 * sets the start value
	 * @param isStart is if the room is the starting room
	 */
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	/**
	 * determines if the room is the finish
	 * @return boolean if the room is the finish
	 */
	public boolean isFinish() {
		return isFinish;
	}
	/**
	 * Sets if the room is the finish
	 * @param isFinish
	 */
	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}
	/**
	 * sees if the room has the key
	 * @return
	 */
	public boolean getHasKey() {
		return hasKey;
	}
	/**
	 * sets if the room has the key
	 * @param hasKey
	 */
	public void setHasKey(boolean hasKey) {
		this.hasKey = hasKey;
	}
	/**
	 * sees if the room has a top door
	 * @return
	 */
	public boolean hasTopDoor() { //UP DOOR GETTER/SETTER
		return hasTopDoor;
	}
	/**
	 * sets if the room has a top door
	 * @param hasTopDoor
	 */
	public void setHasTopDoor(boolean hasTopDoor) {
		this.hasTopDoor = hasTopDoor;
	}
	/**
	 * sees if the room has a bottom door
	 * @return
	 */
	public boolean hasBottomDoor() { //BOTTON DOOR GETTER/SETTER
		return hasBottomDoor;
	}
	/**
	 * sets if the room has a bottom door
	 * @param hasBottomDoor
	 */
	public void setHasBottomDoor(boolean hasBottomDoor) {
		this.hasBottomDoor = hasBottomDoor;
	}
	/**
	 * sees if the room has a left door
	 * @return
	 */
	public boolean hasLeftDoor() { //LEFT DOOR GETTER/SETTER
		return hasLeftDoor;
	}
	/**
	 * sets if the room has a left door
	 * @param hasLeftDoor
	 */
	public void setHasLeftDoor(boolean hasLeftDoor) {
		this.hasLeftDoor = hasLeftDoor;
	}
	/**
	 * sees if the room has a right door
	 * @return
	 */
	public boolean hasRightDoor() { //RIGHT DOOR GETTER/SETTER
		return hasRightDoor;
	}
	/**
	 * sets if the room has a right door
	 * @param hasRightDoor
	 */
	public void setHasRightDoor(boolean hasRightDoor) {
		this.hasRightDoor = hasRightDoor;
	}
	/**
	 * Retrieves the level data of the room
	 * @return
	 */
	public String[][] getLevelData() {
		return levelData;
	}
	/** Return a String representation of the room
    * @return A String representation of the room
    */
    public String toString() {
      return "[(Player: " + hasPlayer() + "),(Start: " + isStart() + "), (Finish: " + isFinish() + "), (Key: " + getHasKey() + "), (Top Door: " + hasTopDoor() + 
    		   "), (Left Door: " + hasLeftDoor() + "), (Bottom Door: " + hasBottomDoor() + "), (Right Door: " + hasRightDoor() + ")] ";
    }
    /**
     * Determines if there is a connection between two rooms
     * @param otherRoom
     * @param direction
     * @return true if there is a connection
     */
	public boolean hasConnection(Room otherRoom, String direction) {
	
		if (direction.toLowerCase().equals("up")) {	
			return (this.hasTopDoor() && otherRoom.hasBottomDoor());	
		} else if (direction.toLowerCase().equals("down")) {	
			return (this.hasBottomDoor() && otherRoom.hasTopDoor());	
		} else if (direction.toLowerCase().equals("right")) {
			return (this.hasRightDoor() && otherRoom.hasLeftDoor());	
		} else if (direction.toLowerCase().equals("left")) {
			return (this.hasLeftDoor() && otherRoom.hasRightDoor());	
		} else {
			System.out.println("invalid room given");
			return false;
		}	
	}
	/**
	 * sees the room number of the room
	 * @return
	 */
	public int getRoomNumber() {
		return roomNumber;
	}
	/**
	 * sets the room number of the room
	 * @param roomNumber
	 */
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	/**
	 * Gets the path value of the room
	 * @return
	 */
	public boolean getPath() {
		return this.path;
	}
	/**
	 * sets the path value of the room
	 * @param marked
	 */
	public void setPath(boolean marked) {
		this.path = path;
	}
	/**
	 * sees if the room is a deadend
	 * @return
	 */
	public boolean getDeadEnd() {
		return this.getDeadEnd();
	}
	/**
	 * sets if the room is a deadend
	 * @param deadEnd
	 */
	public void setDeadEnd(boolean deadEnd) {
		this.deadEnd = deadEnd;
	}

	
}
