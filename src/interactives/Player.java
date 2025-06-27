/**
 * The player class inherits the abstract class entity and stores information about the player and its hitboxes.
 * @author Tony Krystofiak
 * 4/25/2023
 */
package interactives;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Player extends Entity implements Serializable {
	private static final float PLAYERSPEED = 5.0f;
	private String name;
	private boolean alive;
	private float speed;
	private boolean isPressedDown;
	private boolean isPressedUp;
	private boolean isPressedLeft;
	private boolean isPressedRight;
	private boolean hasKey;
	private int curRoom;
  
  /**
   * Player constructor
   * @param float xPos
   * @param float yPos
   * @param float speed
   * @param String name
   */
	  public Player(float xPos, float yPos, int width, int height, float speed, int curRoom) {
	    super(xPos, yPos, speed, width, height);
	    this.alive = true;
	    this.setHasKey(false);
	    this.setCurRoom(curRoom);
	  }
	  /**
	   * Returns the name of the player
	   * @return String name
	   */ 
	  public String getName() {
	    return name;
	  }
	  /**
	   * Sets the name of the player
	   * @param String name
	   */
	  public void setName(String name) {
	    this.name = name;
	  }
	  public boolean getPressedRight() {
		    return isPressedRight;
	  }
	  /**
	   * Sets isPressedRight
	   * @param boolean isPressedRight
	   */
	  public void setPressedRight(boolean isPressedRight) {
	    this.isPressedRight = isPressedRight;
	  }
	  /**
	   * Returns boolean isPressedLeft
	   * @return boolean isPressedLeft
	   */
	  public boolean getPressedLeft() {
	    return isPressedLeft;
	  }
	  /**
	   * Sets isPressedLeft
	   * @param boolean isPressedLeft
	   */
	  public void setPressedLeft(boolean isPressedLeft) {
	    this.isPressedLeft = isPressedLeft;
	  }
	  /**
	   * Returns boolean isPressedUp
	   * @return boolean isPressedUp
	   */
	  public boolean getPressedUp() {
	    return isPressedUp;
	  }
	  /**
	   * Sets isPressedUp
	   * @param boolean isPressedUp
	   */
	  public void setPressedUp(boolean isPressedUp) {
	    this.isPressedUp = isPressedUp;
	  }
	  /**
	   * Returns boolean isPressedDown
	   * @return boolean isPressedDown
	   */
	  public boolean getPressedDown() {
	    return isPressedDown;
	  }
	  /**
	   * Sets isPressedDown
	   * @param boolean isPressedDown
	   */
	  public void setPressedDown(boolean isPressedDown) {
	    this.isPressedDown = isPressedDown;
	  }
	  /**
	   * Creates top hitbox
	   * @return rectangle hitbox
	   */
	  public Rectangle getBoundsTop() {
	    return new Rectangle((int) super.getxPos() + super.getWidth()/6, (int) super.getyPos(), 2 * super.getWidth()/3, super.getHeight()/2);
	  }
	  /**
	   * Creates left hitbox
	   * @return rectangle hitbox
	   */
	  public Rectangle getBoundsLeft() {
	    return new Rectangle((int) super.getxPos(), (int) super.getyPos() + 4, super.getWidth()/6, super.getHeight() - 8);
	  }
	  /**
	   * Creates right hitbox
	   * @return rectange hitbox
	   */
	  public Rectangle getBoundsRight() {
	    return new Rectangle((int) super.getxPos() + super.getWidth() - super.getWidth()/6, (int) super.getyPos() + 4,  super.getWidth()/6, super.getHeight() - 8);
	  }
	  /**
	   * Creates bottom hitbox
	   * @return rectangle hitbox
	   */
	  public Rectangle getBoundsBottom() {
	    return new Rectangle((int) super.getxPos() + super.getWidth()/6, (int) super.getyPos() + super.getHeight()/2, 2 * super.getWidth()/3,  super.getHeight()/2);
	  }
	  /**
	   * Returns boolean alive
	   * @return boolean alive
	   */
	  public boolean getAlive() {
	    return alive;
	  }
	  /**
	   * Setter for boolean alive
	   * @param boolean alive
	   */ 
	  public void setAlive(boolean alive) {
	    this.alive = alive;
	  }
	  /**
	   * Updates position of the player
	   */
	  public void updatePos() {
		  this.diagonals(); //REDUCE SPEED IF MOVING DIAGONALLY
		  //System.out.println(this.getSpeed());
		  if (!(this.getPressedRight() && this.getPressedLeft())) { //IF NOT RIGHT AND LEFT
			  if (this.getPressedRight()) {
				  super.setxPos(super.getxPos() + super.getSpeed()); //IF ONLY RIGHT
			  } else if (this.getPressedLeft()) {
				  super.setxPos(super.getxPos() - super.getSpeed()); //IF ONLY LEFT
			  }
		  } 
		  if (!(this.getPressedUp() && this.getPressedDown())) { //IF NOT UP AND DOWN
			  
			  if (this.getPressedUp()) {
				  super.setyPos(super.getyPos() - super.getSpeed()); //IF ONLY UP
			  } else if (this.getPressedDown()) {
				  super.setyPos(super.getyPos() + super.getSpeed()); //IF ONLY DOWN
			  }
		  }
		  super.setSpeed(PLAYERSPEED); //RESET SPEED TO ORIGINAL
	  }
	  /**
	   * caps diagonal speed of player
	   */
	  private void diagonals() {
		  if ((this.getPressedUp() && this.getPressedRight()) || (this.getPressedRight() && this.getPressedDown()) || 
				  (this.getPressedDown() && this.getPressedLeft()) || (this.getPressedLeft() && this.getPressedUp())) {
			  super.setSpeed(super.getSpeed() / 1.414f);
		  }
	  }
	  /**
	   * Sees if player has key
	   * @return
	   */
	public boolean hasKey() {
		return hasKey;
	}
	/**
	 * sets if the player has key
	 * @param hasKey
	 */
	public void setHasKey(boolean hasKey) {
		this.hasKey = hasKey;
	}
	/**
	 * checks the current room of the player
	 * @return
	 */
	public int getCurRoom() {
		return curRoom;
	}
	/**
	 * sets the current room of the player
	 * @param curRoom
	 */
	public void setCurRoom(int curRoom) {
		this.curRoom = curRoom;
	}
}