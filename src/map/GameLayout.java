/**
 * GameLayout.java
 * The graph data of the game
 * @Date 12/11/2023
 * @author Tony Krystofiak
 */
package map;

import java.util.*;
import java.io.*;
import interactives.*;

public class GameLayout implements RoomGraph, Serializable {
  // Data Field
  
  /** The number of vertices */
  private int numV;
  
  /** Flag to indicate whether this is a directed graph */
  private boolean directed;
  
  /** A map to contain the edges that originate with each vertex. */
  private Map<Integer, ArrayList<RoomEdge>> edges;
  
  private Map<Integer, Room> rooms;
  
  private Room startRoom;
  private Room finishRoom;
  private Room keyRoom;
  private Player player;
  
  /** Construct a graph with the specified number of vertices.
    * @param numV The number of vertices
    * @param directed The directionality flag
    * @param is the current player object
    */
  public GameLayout(int numV, boolean directed, Player player) {
	this.player = player;
    this.numV = numV;
    this.directed = directed;
    this.edges = new HashMap<Integer,ArrayList<RoomEdge>>();
    this.rooms = new HashMap<Integer, Room>();
  }
  
  /** 
   *  {@inheritdoc}
   */
  public int getNumV() {
    return this.numV;
  }
  
  /** 
   * {@inheritdoc}
   */
  public boolean isDirected() {
    return this.directed;
  }  
  
  /** 
   * Construct a graph and load the data from an input file. The first line  
   * of the input file should contain the number of vertices, followed by
   * a series of lines, each line with two or three data values. The first 
   * is the source, the second is the destination, and the optional third is 
   * the weight.
   * @param fileName The file to read data from
   * @param directed true if this is a directed graph, false otherwise
   * @param Player is the current player object
   */
  public GameLayout(String fileName, boolean directed, Player player) {
	this.player = player;
	boolean hasTop;
	boolean hasLeft;
	boolean hasBottom;
	boolean hasRight;
    try{ //C:\\Users\\apkry\\eclipse-workspacev2\\CS200FinalProject\\src\\map\\"
      File file = new File("res\\" + fileName);
      Scanner fileSC = new Scanner(file);
      String line = fileSC.nextLine(); 
      this.numV = Integer.parseInt(line); 
      // set graph type
      this.directed = directed;   
      // create map to store graph info
      this.edges = new HashMap<Integer,ArrayList<RoomEdge>>(); 
      
      //this.allEdges = new HashMap<Integer,ArrayList<RoomEdge>>(); 
      // create map to store link between room number and room data
      this.rooms = new HashMap<Integer, Room>();
      // loop over the file one line at a time
      while (fileSC.hasNextLine()){  	   	
    	
        //read next edge information
        line = fileSC.nextLine();
        //Use a line scanner to split edge information into source, dest and weight (if present)
        Scanner lineSC = new Scanner(line);
        //first is the source vertex
        int source = lineSC.nextInt();
        //Top connection, -1 if no connection
        int top = lineSC.nextInt();
        //Left connection, -1 if no connection
        int left = lineSC.nextInt();
        //Bottom connection, -1 if no connection
        int bottom = lineSC.nextInt();
        //Right connection, -1 if no connection
        int right = lineSC.nextInt();
        
        hasTop = (top != -1);
        hasLeft = (left != -1);
        hasBottom = (bottom != -1);
        hasRight = (right != -1);
        
      //CREATE ROOM AND INSERT IT INTO GRAPH/MAP
        Room curRoom = new Room(hasTop, hasLeft, hasBottom, hasRight, source);
        rooms.put(source, curRoom);
        //CREATE EDGE(S) AND INSERT IT INTO GRAPH/MAP
    	makeEdge(source, top, "top", "bottom");
    	makeEdge(source, left, "left", "right");
    	makeEdge(source, bottom, "bottom", "top");
    	makeEdge(source, right, "right", "left");
    	
//    	makeEdge(source, top, "top", "bottom");
//    	makeEdge(source, left, "left", "right");
//    	makeEdge(source, bottom, "bottom", "top");
//    	makeEdge(source, right, "right", "left");
    	 //close line scanner
        lineSC.close();
      }
      //close file scanner
        fileSC.close();   
  	} catch(IOException ioe){
    	System.out.println("Unknown file");
      return;
    }
    int startingNode = setStart();
    System.out.println(startingNode);
    setFinish(startingNode);
    setKey();
  }
  //Returns the room number --> Room mapping associated with this game
  public Map<Integer, Room> getRoomMap() {
		return rooms;
	}

/**
   * Randomly sets the starting room of the game
   * Dictates where the player begins the game
   * @return int - the starting room number
   */
  private int setStart() {
	Random rand = new Random();
	int randRoom = rand.nextInt(0, 9);
	Room curRoom = rooms.get(randRoom);
	curRoom.setStart(true);	
	curRoom.setPlayer(true);
	this.startRoom = curRoom;
	return randRoom;
  }
  
  /**
   * defines the finish room of the graph
   * @param start is the location of the starting room
   */
  public void setFinish(int start) {
	  int[] curTraverse = BreadthFirstTraversal(start);
	  System.out.println(Arrays.toString(curTraverse));
	  int end = curTraverse[curTraverse.length - 1];
	  Room endRoom = rooms.get(end);
	  endRoom.setFinish(true);
	  this.finishRoom = endRoom;
  }
/**
 * Traverses the graph in order to determine the position of the exit
 * @param start the starting position on the graph of the traversal
 * @return int[] the traversal order of the graph
 */
	private int[] BreadthFirstTraversal(int start) {
		//1-Use a queue initialized to start ... note that Java's LinkedList implements Queue Interface
		  Queue<Integer> theQ = new LinkedList<Integer> ();
		  // Array to store the visit status for each vertex; a true indicates that 
		  // a vertex has been added to queue or visited before
		  boolean[] seen = new boolean[this.getNumV()];
		  
		  // append visited vertices to this String
		  int[] visitSequence = new int[this.getNumV()];
		  int index = 0;
		  theQ.offer(start);
		  //2-Mark start as seen
		  seen[start] = true;
		  while(!theQ.isEmpty()){
		    int current = theQ.remove();
		    visitSequence[index] = current;
		    index++;
		    Iterator<RoomEdge> iterator = this.getEdgeIterator(current);
		    if (iterator != null) {
		      while (iterator.hasNext()) {
		        RoomEdge thisEdge = iterator.next();
		        if (!seen[thisEdge.getDest()] && thisEdge.getDest() != -1) {
		          theQ.offer(thisEdge.getDest());
		          seen[thisEdge.getDest()] = true;
		        }
		      }
		    }
		  }
		  System.out.println(visitSequence);
		  return visitSequence;
	  }
	  
/**
 * Sets the room position of the key
 */
	private void setKey() {
		Random rand = new Random();
		int curPos = rand.nextInt(0, this.getNumV());
		Room curRoom = rooms.get(curPos);
		
		while (curRoom.isFinish() || curRoom.isStart()) {
			curPos = rand.nextInt(0, this.getNumV());
			curRoom = rooms.get(curPos);
		}
		curRoom.setHasKey(true);
		this.keyRoom = curRoom;
	}
	/**
	 * Creates a new edge between the source and destination, as well as what side of the source and edge the path connects to
	 * @param source is the source of the path
	 * @param dest is the destination of the path
	 * @param sourceSide is the side of the source room the path is on
	 * @param destSide is the side of the destination room the path is on
	 */
	private void makeEdge(int source, int dest, String sourceSide, String destSide) {
		  if (dest != -1) { //if the connection value isn't -1		  
			  RoomEdge curEdge = new RoomEdge(source, dest, sourceSide, destSide); //makes new room edge with current source
		      //System.out.println(curEdge.toString());
		      ArrayList<RoomEdge> edgeCol = this.edges.get(source);
		      if (edgeCol == null) {
		        ArrayList<RoomEdge> newList = new ArrayList<>();
		        newList.add(curEdge);
		        this.edges.put(source, newList);
		      } else {
		        edgeCol.add(curEdge);
		        this.edges.put(source, edgeCol);
		      } 
		  }
	  }
	  /** Return the edges connected to a given vertex.
	    * @param source The source vertex
	    * @return An Iterator<RoomEdge> to the vertices connected to source or null if source doesn't connect to any
	    *    vertices 
	    */
	  public Iterator<RoomEdge> getEdgeIterator(int source) {
	    /* return an iterator over set of Edges from "source" or null if "source" doesn't connect to any
	     *    vertices */
	    ArrayList<RoomEdge> collection = edges.get(source);
	    if (collection.isEmpty()) {
	      return null;
	    } else {      
	      
	      return collection.iterator();
	    }
	  }
  /**
   * Retrieves room data on the current room
   * @param int is the current room
   */
  public String getRoomData(int room) {
	  Room curRoom = rooms.get(room);
	  return ("Room Number : " + room + " | " + curRoom);
  }
  /**
   * Retrieves edge data on the current room
   * @param int is the current room
   */
  public void getEdgeData(int room) {
	  Iterator<RoomEdge> edges = this.getEdgeIterator(room);
      if (edges!=null){
        while(edges.hasNext())
          System.out.println(edges.next());
      }
  }
  /**
   * Save the current state of the game
   */
  public void save() {
	    String fileName = "saveRooms";
	    try {
	    ObjectOutputStream ow1 = new ObjectOutputStream(new FileOutputStream(fileName));
	    ow1.writeObject(rooms);
	    ow1.close();
	    } catch (IOException io) {
	      System.out.println("Room file could not be created.");
	    }
	    
	    fileName = "savePlayer";
	    try {
	    PrintStream playerData = new PrintStream(new File(fileName));
	    playerData.println(player.getxPos());
	    playerData.println(player.getyPos());
	    playerData.println(player.hasKey());
	    } catch (IOException io) {
	      System.out.println("Player file could not be created.");
	    }
	  }
  /**
   * Loads an old state of the game
   */
  public void load() {
	    String fileName = "saveRooms";
	    //player.setHasKey(true);
	    try {
	    ObjectInputStream ow1 = new ObjectInputStream(new FileInputStream(fileName));
	    this.rooms = (Map<Integer, Room>) ow1.readObject();
	    ow1.close();
	    for(int v = 0; v<this.getNumV();v++){
	      System.out.println(this.getRoomData(v));
	      if (this.rooms.get(v).hasPlayer()) {
	    	  player.setCurRoom(v);
	      }
	    }
	    
	    } catch (IOException io) {
	      System.out.println("File could not be found.");
	    } catch (ClassNotFoundException cge) {
	      System.out.println("Class could not be found.");
	    }
	
	  fileName = "savePlayer";
	  try {
	  Scanner playerScan = new Scanner(new File(fileName));
	  float xPos = playerScan.nextFloat();
	  float yPos = playerScan.nextFloat();
	  boolean hasKey = Boolean.parseBoolean(playerScan.next());
	  this.player.setxPos(xPos);
	  this.player.setyPos(yPos);
	  this.player.setHasKey(hasKey);
	  
	  } catch (IOException io) {
	    System.out.println("Player File could not be found.");
	  }
	}

@Override
public int keySearch(int i, int j) {
	// TODO Auto-generated method stub
	return 0;
}
  
//  public int keySearch(int current, int end) {
//	  if (current == -1) {
//		  return -1;
//	  }
//	  if (current == end) {
//		  return 0;
//	  } else {
//		  ArrayList<RoomEdge> curEdges = edges.get(current); //find all possible connections
//		  Room curRoom = rooms.get(current); //find current room
//		  curRoom.setPath(true); // set room as marked
//		  System.out.println("room number: " + curRoom.getRoomNumber() + "\tedges size: " + curEdges.size());
//		  
//			  for (int i = 0; i < curEdges.size(); i++) {
//				  
//				  System.out.println("current destination: " + curEdges.get(i).getDest() + "\tis marked: " + rooms.get(curEdges.get(i).getDest()).getPath() + "\ti: " + i);
//				  
//				  if (!rooms.get(curEdges.get(i).getDest()).getPath() || rooms.get(curEdges.get(i).getDest()).getDeadEnd()) {
//					  return 1 + keySearch(curEdges.get(i).getDest(), end);
//				  }
//			  }
//		  curRoom.setDeadEnd(true);
//		  curRoom.setPath(false);
//		  System.out.println("here");
//		  return keySearch(-1, end);
//	  }
//  public boolean keySearch(int current, int end) {
//	    if (current == -1){  
//	      return false; // Cell is out of bounds.
//	    }
//	    else if (this.rooms.get(current).getDeadEnd() || this.rooms.get(current).getPath()){  // Cell is not a Dead End, on the path, or out of bounds
//	      return false; 
//	    }
//	    else if (current == end) {
//	      this.rooms.get(current).setPath(true); //destination is on path
//	      return true; // and is maze exit.
//	    }
//	    else { // Recursive case.
//	      this.rooms.get(current).setPath(true);
//	      if (findMazePath(row,col-1) || findMazePath(row-1,col) || findMazePath(row+1,col) || findMazePath(row,col+1)) { 
//	        return true;
//	      }
//	      //otherwise, backtrack
//	      this.rooms.get(current).setDeadEnd(true); // Deadend.
//	      return false;
//	    }
//  }
}

