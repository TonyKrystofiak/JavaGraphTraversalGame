/**
 * The Platform class creates the platforms and hitboxes generated on the screen.
 * @author Tony Krystofiak comments by Sam Edwards
 * @date 4/23/2023
 */
package interactives;

import javax.swing.*;
import java.awt.*;

public class Platform extends Entity{
  private boolean harmful;
  /**
   * Platform Constructor
   * @param float xPos
   * @param float yPos
   * @param float xVel
   * @param float yVel
   * @param int width
   * @param int height
   * @param boolean harmful determines if the platform damages the player
   */
  public Platform(float xPos, float yPos, int width, int height, boolean harmful) {
    super(xPos, yPos, 0, width, height);
    
    this.setHarmful(harmful);
  }
  /**
   * getBounds creates the hitbox for the platform
   * @param Platform p is the instance of the platform class
   * @return Rectangle is the hitbox of the platform
   */
  public Rectangle getBounds(Platform p) {
//  System.out.println(p.getxPos());
//  System.out.println(p.getyPos());
//  System.out.println(p.getWidth());
//  System.out.println(p.getHeight());
    return new Rectangle((int) super.getxPos(), (int) super.getyPos(), (int) super.getWidth(), (int) super.getHeight());
  }
  /**
   * moves platform to the left
   * @param float xVel
   */
  public void moveLeft(int xVel) {
    super.setxPos(super.getxPos() + xVel);
  }
  /**
   * checks if platform is harmful
   * @return boolean harmful
   */
  public boolean getHarmful() {
    return harmful;
  }
  /**
   * Setter for boolean harmful
   * @param boolean harmful
   */
  public void setHarmful(boolean harmful) {
    this.harmful = harmful;
  }
}
