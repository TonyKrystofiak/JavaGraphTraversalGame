/**
 * The Platform class creates the platforms and hitboxes generated on the screen.
 * @author Tony Krystofiak comments by Sam Edwards
 * @date 4/23/2023
 */
package interactives;

import javax.swing.*;
import java.awt.*;

public class Key extends Entity{
  /**
   * Platform Constructor
   * @param float xPos
   * @param float yPos
   * @param int width
   * @param int height
   */
  public Key(float xPos, float yPos, int width, int height) {
    super(xPos, yPos, 0, width, height);
  }
  /**
   * getBounds creates the hitbox for the platform
   * @param Platform p is the instance of the platform class
   * @return Rectangle is the hitbox of the platform
   */
  public Rectangle getBounds(Key k) {
    return new Rectangle((int) super.getxPos(), (int) super.getyPos(), (int) super.getWidth(), (int) super.getHeight());
  }
}