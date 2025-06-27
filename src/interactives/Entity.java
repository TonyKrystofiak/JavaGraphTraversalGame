/**
 * The Entity abstract class creates the framework for all on-screen objects such as the player and platforms.
 * @author Tony Krystofiak comments by Sam Edwards
 * 4/23/2023
 */

package interactives;

public abstract class Entity {
  private float xPos;
  private float yPos;
  private float speed;
  private int width;
  private int height;
  
  /**
   * Entity Constructor
   * @param float xPos
   * @param float yPos
   * @param float speed
   * @param int width
   * @param int height
   */
  public Entity(float xPos, float yPos, float speed, int width, int height) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.width = width;
    this.height = height;
    this.speed = speed;
  }
  /**
   * Gets entity x position
   * @return float xPos
   */
  public float getxPos() {
    return xPos;
  }
  /**
   * Sets entity x position
   * @param float xPos
   */
  public void setxPos(float xPos) {
    this.xPos = xPos;
  }
  /**
   * Gets entity y position
   * @return float yPos
   */
  public float getyPos() {
    return yPos;
  }
  /**
   * Sets entity y position
   * @param float yPos
   */
  public void setyPos(float yPos) {
    this.yPos = yPos;
  }
  /**
   * Gets entity x velocity
   * @return float xVel
   */
  public float getSpeed() {
    return speed;
  }
  /**
   * Sets entity x velocity
   * @param float xVel
   */
  public void setSpeed(float d) {
    this.speed = d;
  }
  /**
   * Gets entity width
   * @return int width
   */
  public int getWidth() {
    return width;
  }
  /**
   * Sets entity width
   * @param int width
   */
  public void setWidth(int width) {
    this.width = width;
  }
  /**
   * Gets entity height
   * @return int height
   */
  public int getHeight() {
    return height;
  }
  /**
   * Sets entity height
   * @param int height
   */
  public void setHeight(int height) {
    this.height = height;
  }
}
