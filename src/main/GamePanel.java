/**
 * The GamePanel Class that dictates the behavior of the game
 * @Date 12/11/2023
 * @author Tony Krystofiak
 */


package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import interactives.Key;
import interactives.Platform;
import inputs.*;
import interactives.Player;
import interactives.Enemy;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static main.Game.TILES_IN_WIDTH;
import static main.Game.TILES_IN_HEIGHT;
import map.*;

public class GamePanel extends JPanel {
  private static final int IMAGEWIDTH = 32;
  private static final int SLIMEWIDTH = 12; //width of the slime at 32x32 size
  private static final int SLIMEHEIGHT = 10; //height of the slime at 32x32 size
  private static final int BLOCK_SIZE = 48;
  
  private static final int PANELWIDTH =1288;
  private static final int PANELHEIGHT = 672;
  private static final int TOPBARHEIGHT = 40;
  private static final int WIDTHOFFSET = 15;
  private static final int FLOORHEIGHT = PANELHEIGHT - 140;
  private static final int MAX_SPEED = 20;
  
  private BufferedImage[] idleAni;
  private BufferedImage[] levelSprites;
  private BufferedImage img;
  private int aniTick = 0, aniIndex = 0, aniRate = 5;
  private float playerSpeed = 5.0f;
  private Player player;
  private int newWidth = 128;
  private double scaleFactorSlime;
  private double scaleFactorBlock;
  private Map<Integer, Room> rooms; //hashmap of room number --> room values
  private Map<Integer, RoomEdge> edgesIterator; //hashmap of room number --> edge values
  
  
  private int currentRoomNum; //USE THIS TO DICTATE WHICH ROOM TO LOAD!!!
  private Room currentRoom;
  private RoomGraph rg;
  private Menu menu;
  private Enemy enemy;
  
  //ROOM STUFF
  
  
  ArrayList<Platform> platforms = new ArrayList<Platform>();
  private boolean gamePaused;
  
  /**
   * Initializes the GamePanel and gives it key and mouse listeners
   */
  public GamePanel() { 
	  
	  
    int startingRoom = -1;
    this.scaleFactorSlime = (double) newWidth/IMAGEWIDTH;
	this.scaleFactorBlock = (double) BLOCK_SIZE / IMAGEWIDTH;
	this.player = new Player(PANELWIDTH/2, GAME_HEIGHT - 200, (int) (SLIMEWIDTH*scaleFactorSlime), (int) (SLIMEHEIGHT*scaleFactorSlime), playerSpeed, startingRoom);
	this.enemy = new Enemy(PANELWIDTH/2, GAME_HEIGHT - 100, (int) (SLIMEWIDTH*scaleFactorSlime), (int) (SLIMEHEIGHT*scaleFactorSlime), playerSpeed, startingRoom);
    this.rg = new GameLayout("UnweightedData.txt", false, player);
    this.rooms = this.rg.getRoomMap();
    for (Integer r : this.rooms.keySet()) {
    	if (this.rooms.get(r).isStart()) {
    		startingRoom = r;
    		player.setCurRoom(startingRoom);
    		player.setxPos(GAME_WIDTH/2);
    		player.setyPos(GAME_HEIGHT-200);
    	}
    }
    

    
    this.displayGraphInfo(this.rg); 
    
	
    setPanelSize();
    addKeyListener(new KeyboardInputs(this));
    addMouseListener(new MouseInputs(this));


    
	importImgPlayer();
	loadAnimations();
	importImgLevel();
	loadLevelSprites();
  }
  /**
   * Initializes the menu for the game
   */
  public void makeMenu() {
	  this.menu = new Menu(this, rg, player);
	  this.gamePaused = true;
  }
  /**
   * Displays information about the graph
   * @param g is the RoomGraph data is being drawn from
   */
  public void displayGraphInfo(RoomGraph g) {
//	    System.out.println("Graph g has " + g.getNumV() + " vertices");
//	    System.out.println("Is graph directed?  " + g.isDirected());
//	    System.out.println("Show all edges:");
//	    for(int v = 0; v<g.getNumV();v++){
//	      Iterator<RoomEdge> edges = g.getEdgeIterator(v);
//	      if (edges!=null){
//	        while(edges.hasNext())
//	          System.out.println(edges.next());
//	      }
//	    }
		  System.out.println("Show all rooms:");
		  Map<Integer, Room> roomMap = g.getRoomMap();
		  for(int r = 0; r<g.getNumV();r++){
			  Room curRoom = roomMap.get(r);
			  System.out.println("Room: " + r);//+ " " + curRoom)
		  }
	  }
  /**
   * Sets the size of the gamePanel
   */
  private void setPanelSize() {
      Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
      System.out.println(GAME_WIDTH + " : " + GAME_HEIGHT);
      setMinimumSize(size);
      setPreferredSize(size);
      setMaximumSize(size);
    }
  /**
   * Imports image files for the player
   */
  private void importImgPlayer() {
		InputStream is = getClass().getResourceAsStream("/slime spritesheet calciumtrice.png");
		
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			System.out.println("not working");
		}
	}	
  /**
   * Imports the files for the level sprites
   */
	private void importImgLevel() {
		InputStream is = getClass().getResourceAsStream("/DungeonCrawl_ProjectUtumnoTileset.png");
		
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			System.out.println("not working");
		}
	}
	/**
	 * Loads the animations for the slime
	 */
	private void loadAnimations() {
		idleAni = new BufferedImage[10];
		BufferedImage scaledImg;
		BufferedImage subImg;
		for (int i = 0; i < idleAni.length; i++) {
			subImg = img.getSubimage(32*i, 0, 32, 32);
			scaledImg = resize(subImg, newWidth, scaleFactorSlime);
			idleAni[i] = scaledImg;
		}
	  }
/**
 * Loads Level Sprites for the map
 */
	private void loadLevelSprites() {
		levelSprites = new BufferedImage[1];
		BufferedImage scaledImg;
		BufferedImage subImg;
		for (int i = 0; i < levelSprites.length; i++) {
			subImg = img.getSubimage(40*32, 18*32, 32, 32);
			scaledImg = resize(subImg, BLOCK_SIZE, this.scaleFactorBlock);
			levelSprites[i] = scaledImg;
		}
	}
	/**
	 * //Method by Oggi AI, I did not write this myself
	 * Scales the image size
	 * @param image the image being scaled
	 * @param width the new width of the image
	 * @param scaleFactor the factor the image is being scaled by
	 * @return BufferedImage the scaled image
	 */
	private BufferedImage resize(BufferedImage image, int width, double scaleFactor) { 
		//System.out.println("scaling");
		//double scaleFactor = (double) width/image.getHeight();
		BufferedImage scaledImage = new BufferedImage((int) (scaleFactor*image.getWidth()), width, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(scaleFactor, scaleFactor);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return scaleOp.filter(image, scaledImage);
	}
	/**
	 * Sets speed for animations
	 */
	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniRate) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= idleAni.length) {
				aniIndex = 0;
			}
		}
	}
	/**
	 * @return Player the player object on this game panel
	 */
	public Player getPlayer() {
		  return this.player;
	}
	/**
	 * @return Enemy the enemy object on this game panel
	 */
	public Enemy getEnemy() {
		  return this.enemy;
	}

  /**
   * Where the magic happens, paints objects onto the game window.
   * @param Graphics g acts as the paintbrush that creates visuals for every object.
   */
  public void paintComponent(Graphics g) {
	platforms = new ArrayList<Platform>();
    super.paintComponent(g);
    

    updateAnimationTick();
    
    if (player.getAlive()) {
    	this.getPlayer().updatePos();
  	  	this.getEnemy().passPlayer(this.getPlayer());
        this.getEnemy().updatePos();
        //System.out.println(this.getEnemy().getxPos());
        //System.out.println(this.getEnemy().getyPos());
      
      currentRoomNum = player.getCurRoom(); //PLAYERS CURRENT ROOM POSITION
      currentRoom = rooms.get(currentRoomNum);
      
      //border();
      door(currentRoomNum);
      
      Graphics g2d = (Graphics2D) g;
      
      g.setColor(Color.black);
      g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

      
      String[][] currentRoomData = rooms.get(currentRoomNum).getLevelData();
      for (int i = 0; i < currentRoomData.length; i++) {
    	  for (int j = 0; j < currentRoomData[i].length; j++) {
    		  if (currentRoomData[i][j].equals("X")) {
    			  g.drawImage(levelSprites[0], j*BLOCK_SIZE, i*BLOCK_SIZE, null);
    			  platforms.add(new Platform(j * BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, false));
    		  } else if (currentRoomData[i][j].equals("E")) {
    			 // this.enemy.setxPos((int) j * BLOCK_SIZE);
    			  //this.enemy.setyPos((int) i * BLOCK_SIZE);
    			  //System.out.println(this.enemy.getxPos());
    			  //System.out.println(this.enemy.getyPos());
    			  //System.out.println("spawning enemy");
    			 // g.drawImage(idleAni[aniIndex], j*BLOCK_SIZE - (int) (10*scaleFactorSlime), i*BLOCK_SIZE - (int) (22*scaleFactorSlime),  null); 
    		  }
    	  }
      }

      spawnExit(g);
      spawnKey(g);
      g.setColor(Color.black);
      //g.fillRect(GAME_WIDTH/2-48, 0, 96, 48); //top door position
      //g.fillRect(GAME_WIDTH/2-48, GAME_HEIGHT-86, 96, 48); //bottom door position
      //g.fillRect(0, GAME_HEIGHT/2 - 48, 48, 96); //left door position
      //g.fillRect(GAME_WIDTH-60, GAME_HEIGHT/2 - 48, 48, 96);
      for (int i = 0; i < platforms.size(); i++) { 
          collision(platforms.get(i));
          collisionEnemy(platforms.get(i));
        }
      //System.out.println(player.hasKey());
      g.setColor(Color.red);
      ((Graphics2D) g2d).draw(player.getBoundsBottom());
      ((Graphics2D) g2d).draw(player.getBoundsRight());
      ((Graphics2D) g2d).draw(player.getBoundsLeft());
      ((Graphics2D) g2d).draw(player.getBoundsTop());  
      
      g.setColor(Color.white);
      g.drawRect((int)player.getxPos(), (int)player.getyPos(), (int)player.getWidth(), (int)player.getHeight());      
      g.drawImage(idleAni[aniIndex], (int)player.getxPos() - (int) (10*scaleFactorSlime), (int)player.getyPos()-(int) (22*scaleFactorSlime),  null);
      if(player.getCurRoom()==enemy.getCurRoom()){
    	  g.drawImage(idleAni[aniIndex], (int)enemy.getxPos() - (int) (10*scaleFactorSlime), (int)enemy.getyPos()-(int) (22*scaleFactorSlime),  null); 
  	  }
      updateGame();
    } else {
      System.exit(0);
    }
  }
/**
 * Sets the position of the key depending on the room position
 * @param g the graphics interface for game panel
 */
	private void spawnKey(Graphics g) {
		if (rooms.get(player.getCurRoom()).getHasKey() && !player.hasKey()) {
				g.setColor(Color.yellow);
				Key k;
				if (player.getCurRoom() == 0) {
					g.fillRect(1090, 540, 20, 20);
					k = new Key(1090, 540, 20, 20);
				} else if (player.getCurRoom() == 1) {
					g.fillRect(620, 350, 20, 20);
					k = new Key(620, 350, 20, 20);
				} else if (player.getCurRoom() == 2) {
					g.fillRect(1120, 520, 20, 20);
					k = new Key(1120, 520, 20, 20);
				} else if (player.getCurRoom() == 3) {
					g.fillRect(110, 90, 20, 20);
					k = new Key(110, 90, 20, 20);
				} else if (player.getCurRoom() == 4) {
					g.fillRect(1170, 330, 20, 20);
					k = new Key(1170, 330, 20, 20);
				} else if (player.getCurRoom() == 5) {
					g.fillRect(1160, 330, 20, 20);
					k = new Key(1060, 330, 20, 20);
				} else if (player.getCurRoom() == 6) {
					g.fillRect(930, 580, 20, 20);
					k = new Key(930, 580, 20, 20);
				} else if (player.getCurRoom() == 7) {
					g.fillRect(1000, 360, 20, 20);
					k = new Key(1000, 360, 20, 20);
				} else if (player.getCurRoom() == 8) {
					g.fillRect(860, 330, 20, 20);
					k = new Key(860, 330, 20, 20);
				} else {
					g.fillRect(620, 340, 20, 20);
					k = new Key(620, 340, 20, 20);
				}

				if ( (player.getBoundsBottom().intersects(k.getBounds(k))) || (player.getBoundsTop().intersects(k.getBounds(k))) ||
						(player.getBoundsLeft().intersects(k.getBounds(k))) || (player.getBoundsRight().intersects(k.getBounds(k))) ) {
				      player.setHasKey(true);
				}
		}
	}
	/**
	 * Spawns the exit within the game
	 * @param g the graphics control for game panel
	 */
	private void spawnExit(Graphics g) {
		if (rooms.get(player.getCurRoom()).isFinish()) {
			g.setColor(Color.red);
			g.fillRect(GAME_WIDTH - 200, GAME_HEIGHT-150, 50, 80);
			if (!player.hasKey()) {
				platforms.add(new Platform(GAME_WIDTH - 200, GAME_HEIGHT-150, 50, 80, false));
			} else {
				platforms.add(new Platform(GAME_WIDTH - 200, GAME_HEIGHT-150, 50, 80, true));
			}
		}
	
}
	/**
	 * Dictates the behavior of paths between rooms
	 * @param room the room the player is currently in
	 */
	private void door(int room) {
		Room curRoom;
		Room destRoom;
		Iterator<RoomEdge> edges = this.rg.getEdgeIterator(room);
		if (player.getxPos() <= 0) { //left door
		    if (edges!=null){
		    	while(edges.hasNext()) {
		          //System.out.println(edges.next());
		    		RoomEdge curEdge = edges.next();
		    		if (curEdge.getSourceSide().equals("left")) {
		    			curRoom = this.rooms.get(player.getCurRoom());
		    			curRoom.setPlayer(false);
		    			destRoom = this.rooms.get(curEdge.getDest());
		    			destRoom.setPlayer(true);
		    			player.setCurRoom(curEdge.getDest());
		    			player.setxPos(GAME_WIDTH-WIDTHOFFSET-player.getWidth());
		    			player.setyPos(GAME_HEIGHT/2);
		    		}
		        }
		    }
		} else if (player.getxPos() >= GAME_WIDTH - player.getWidth()) { //right door
			if (edges!=null){
		    	while(edges.hasNext()) {
		          //System.out.println(edges.next());
		    		RoomEdge curEdge = edges.next();
		    		if (curEdge.getSourceSide().equals("right")) {
		    			curRoom = this.rooms.get(player.getCurRoom());
		    			curRoom.setPlayer(false);
		    			System.out.println("curRoom --> " + curRoom.hasPlayer());
		    			destRoom = this.rooms.get(curEdge.getDest());
		    			destRoom.setPlayer(true);
		    			player.setCurRoom(curEdge.getDest());
		    			player.setxPos(0);
		    			player.setyPos(GAME_HEIGHT/2);
		    		}
		        }
			}
		} else if (player.getyPos() <= 0) { //top door
		if (edges!=null){
	    	while(edges.hasNext()) {
	    		RoomEdge curEdge = edges.next();
	    		if (curEdge.getSourceSide().equals("top")) {
	    			curRoom = this.rooms.get(player.getCurRoom());
	    			curRoom.setPlayer(false);
	    			destRoom = this.rooms.get(curEdge.getDest());
	    			destRoom.setPlayer(true);
	    			player.setCurRoom(curEdge.getDest());
	    			//player.setxPos(0);
	    			player.setyPos(GAME_HEIGHT - player.getHeight()-40);
	    		}
	        }
		}
	} else if (player.getyPos() >= GAME_HEIGHT - player.getHeight()-40) { //bottom door
		if (edges!=null){
	    	while(edges.hasNext()) {
	    		RoomEdge curEdge = edges.next();
	    		if (curEdge.getSourceSide().equals("bottom")) {
	    			curRoom = this.rooms.get(player.getCurRoom());
	    			curRoom.setPlayer(false);
	    			destRoom = this.rooms.get(curEdge.getDest());
	    			destRoom.setPlayer(true);
	    			player.setCurRoom(curEdge.getDest());
	    			//player.setxPos(0);
	    			player.setyPos(40);
	    		}
	        }
		}
	}
	}
/**
   * Updates the game by setting the position of the player
   */
  public void updateGame() {
    setRectPos(player.getxPos(), player.getyPos());
  }
  /**
   * Sets the position of the player and refreshes the GamePanel.
   */
  public void setRectPos(float xPos, float yPos) {
    player.setxPos(xPos);
    player.setyPos(yPos);
    repaint();
  } 
  /**
   * Sets the game border of the panel
   */
  public void border() {
    if (player.getxPos() <= 0) { //left border
      player.setxPos(0);
    } else if (player.getxPos() >= PANELWIDTH - player.getWidth() - WIDTHOFFSET) { //RIGHT BORDER ADD CLASS CONSTANT
//    	System.out.println(PANELWIDTH - player.getWidth());
//    	System.out.println("xPos: " + player.getxPos());
      player.setxPos(PANELWIDTH - player.getWidth() - WIDTHOFFSET);
    } 
    if (player.getyPos() < 0) { //top border
      player.setyPos(0);
    } else if (player.getyPos() >= PANELHEIGHT - player.getHeight()) { //BOTTOM BORDER ADD CLASS CONSTANT
      player.setyPos(PANELHEIGHT - player.getHeight());
      //System.out.println("on ground\tfall: " + player.getFalling() + "\tjump: " + player.getJumping());
    }
  }
  /**
   * Creates a new platform
   * @param int xPos
   * @param int yPos
   */
  public void createPlatform(int xPos, int yPos) {
    platforms.add(new Platform(xPos, yPos, 100, 100, false));
    System.out.println("adding platform");
  }
  /**
   * Checks for collision between platforms and player
   * @param Platform p is the current platform being checked for collision.
   */
  public void collision(Platform p) {
    if (player.getBoundsBottom().intersects(p.getBounds(p))) {
      player.setyPos(p.getyPos() - player.getHeight());
      if (p.getHarmful()) {
    	  player.setAlive(false);
      }
      //System.out.println("colliding with top");      
    } else if (player.getBoundsTop().intersects(p.getBounds(p))){
      player.setyPos(p.getyPos() + p.getHeight());
      if (p.getHarmful()) {
    	  player.setAlive(false);
      }
    }
    if (player.getBoundsRight().intersects(p.getBounds(p))) {
      player.setxPos(p.getxPos() - player.getWidth());
      if (p.getHarmful()) {
    	  player.setAlive(false);
      }
    }
    if (player.getBoundsLeft().intersects(p.getBounds(p))) {
      player.setxPos(p.getxPos() + p.getWidth());
      if (p.getHarmful()) {
    	  player.setAlive(false);
      }
    }
  }
  /**
   * @return the RoomGraph for this game panel
   */
  public RoomGraph getRG() {
	  return this.rg;
  }
  /**
   * Determines the collision of the enemy
   * @param p Platform are the platforms the enemies are colliding with
   */
  public void collisionEnemy(Platform p) {
	    if (enemy.getBoundsBottom().intersects(p.getBounds(p))) {
	      enemy.setyPos(p.getyPos() - enemy.getHeight());      
	    } else if (enemy.getBoundsTop().intersects(p.getBounds(p))){
	      enemy.setyPos(p.getyPos() + p.getHeight());
	    }
	    if (enemy.getBoundsRight().intersects(p.getBounds(p))) {
	      enemy.setxPos(p.getxPos() - p.getWidth());
	    }
	    if (enemy.getBoundsLeft().intersects(p.getBounds(p))) {
	      enemy.setxPos(p.getxPos() + p.getWidth());
	    }
		
//		if (enemy.getBoundsBottom().intersects(player.getBoundsEnemy())) {
//	    	  player.setAlive(false);  
//		}   
//	    if (enemy.getBoundsTop().intersects(player.getBoundsEnemy())){
//	    	  player.setAlive(false);
//	    }
//	    if (enemy.getBoundsRight().intersects(player.getBoundsEnemy())) {
//	    	  player.setAlive(false);
//	    }
//	    if (enemy.getBoundsLeft().intersects(player.getBoundsEnemy())) {
//	    	  player.setAlive(false);
//	    }
		
	  }
}
