/**
 * Game loop of the game
 * sets FPS and UPS
 * @author Tony Krystofiak
 * @Date 12/3/23
 */
package main;

public class Game implements Runnable{
	 
	 private GameWindow gameWindow;
	 private GamePanel gamePanel;
	 private Thread gameThread;
	 private static String playerName;
	 private final int FPS_SET = 120; //120
	 private final int UPS_SET = 200; //200
	 
	 public static final int TILES_DEFAULT_SIZE = 32;
	 public static final float SCALE = 1.5f;
	 public static final int TILES_IN_WIDTH = 26; //number of x-axis tiles
	 public static final int TILES_IN_HEIGHT = 14; //number of y-axis tiles
	 public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	 public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH + 12; //pixel width of screen
	 public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT + 38; //pixel height of screen
	 
	 public Game() {
	     System.out.println("starting game");
	     gamePanel = new GamePanel();
	     gameWindow = new GameWindow(gamePanel);
	     gamePanel.requestFocusInWindow();//gives gamePanel the focus of user input
	     startGameLoop();
	 }
	 /**
	  * GAME LOOP, startGameLoop, and update BY KAARIN GAMING 
	  */
	 /*********************************************************************************/
	 /**
	  * Starts the game loop
	  */
	 private void startGameLoop() {
	   gameThread = new Thread(this);
	   gameThread.start();
	 }
	 public GamePanel getGamePanel() {
		 return this.gamePanel;
	 }
	 /**
	  * Updates the game 
	  */
	 public void update() {
	   gamePanel.updateGame(); 
	 }
	 /**
	  * Sets the FPS and UPS of the game.
	  */
	 @Override
	 public void run() { 
	   double timePerFrame = 1_000_000_000.0 / FPS_SET; //stores duration of each frame in nanoseconds.
	   double timePerUpdate = 1_000_000_000.0 / UPS_SET; //stores duration of each frame in nanoseconds.
	   
	   long previousTime = System.nanoTime();
	   
	   int frames = 0;
	   int updates = 0;
	   long lastCheck = System.currentTimeMillis();
	   
	   double deltaU = 0;
	   double deltaF = 0;
	   
	   while(true) {
	     long currentTime = System.nanoTime();
	     
	     deltaU += (currentTime - previousTime) / timePerUpdate;
	     deltaF += (currentTime - previousTime) / timePerFrame;
	     previousTime = currentTime;
	     
	     if(deltaU >= 1) {
	       update();
	       updates++;
	       deltaU--;
	     }
	     if(deltaF >= 1) {
	       gamePanel.repaint();
	       deltaF--;
	       frames++;
	     }
	     if(System.currentTimeMillis() - lastCheck >= 1000) {
	       lastCheck = System.currentTimeMillis();
	       //System.out.println("FPS: " + frames + " | UPS: " + updates);
	       frames = 0;
	       updates = 0;
	     }
	   }
	 }
}
