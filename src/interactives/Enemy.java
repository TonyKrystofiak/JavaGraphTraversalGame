/**
 * The enemy class inherits the abstract class entity and stores information about the player and its hitboxes.
 * @author Tony Krystofiak comments by Sam Edwards
 * 4/25/2023
 */
package interactives;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Enemy extends Entity{
	private static final float PLAYERSPEED = 0.5f;
  private static final float AGGRORANGE = 640.0f;
	private String name;
	private boolean alive;
	private float speed;
	private int curRoom;
  private float playerX;
  private float playerY;
  private int playerRoom;
  
  /**
   * Player constructor
   * @param float xPos
   * @param float yPos
   * @param float speed
   * @param String name
   */
  public Enemy(float xPos, float yPos, int width, int height, float speed, int curRoom) {
    super(xPos, yPos, speed, width, height);
    this.alive = true;
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

  public void passPlayer(Player player){
    this.playerX = player.getxPos();
    this.playerY = player.getyPos();
    this.playerRoom = player.getCurRoom();
  }

  public float getPlayerXDif(){
    return (this.playerX - super.getxPos());
  }
    public float getPlayerYDif(){
    return (this.playerY - super.getyPos());
  }
  public float getAbsPlayerXDif(){
    return Math.abs(this.playerX - super.getxPos());
  }
    public float getAbsPlayerYDif(){
    return Math.abs(this.playerY - super.getyPos());
  }

  public boolean giveChase(){
    return(curRoom==playerRoom&&(this.getAbsPlayerXDif() + this.getAbsPlayerYDif()<=AGGRORANGE));
  }
  public void updatePos() {
    if(this.giveChase()){
      super.setxPos(super.getxPos() + (getPlayerXDif()/1000));
      super.setyPos(super.getyPos() + (getPlayerYDif()/1000));
    }
  }

  public int getCurRoom() {
	  return curRoom;
  }
  public void setCurRoom(int curRoom) {
	  this.curRoom = curRoom;
  }
  public void setPlayerX(float x){
    this.playerX = x;
  }
  public void setPlayerY(float y){
    this.playerY = y;
  }
}