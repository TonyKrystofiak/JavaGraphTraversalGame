/**
 * The MouseInputs class handles instances in which the mouse inputs are registered.
 * @author Tony Krystofiak comments by Sam Edwards
 * 4/20/2023
 */
package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import main.*;

public class MouseInputs implements MouseListener, MouseMotionListener{
private GamePanel gamePanel;

//MouseListener
 
 public MouseInputs(GamePanel gamePanel) {
  this.gamePanel = gamePanel;
 }
 /**
  * Generates a platform at the mouse position
  * used for code testing (mainly for collisions), necessary for code to compile
  * @param MouseEvent e registers when the mouse is clicked
  */
 @Override
 public void mouseClicked(MouseEvent e) {
  System.out.println("mouse clicked");
  System.out.println("x: " + e.getX() + " | y: " +  e.getY());
 }
 /**
  * unused, necessary for code to compile
  */
 @Override
 public void mousePressed(MouseEvent e) {
 }
/**
  * unused, necessary for code to compile
  */
 @Override
 public void mouseReleased(MouseEvent e) {
 }
 /**
  * unused, necessary for code to compile
  */
 @Override
 public void mouseEntered(MouseEvent e) {
 }
 /**
  * unused, necessary for code to compile
  */
 @Override
 public void mouseExited(MouseEvent e) {
 }
//MouseMotionListener
/**
  * unused, necessary for code to compile
  */
 @Override
 public void mouseDragged(MouseEvent e) {
  //System.out.println("mouse dragged");  
 }
/**
  * used for code testing, necessary for code to compile
  */
 @Override
 public void mouseMoved(MouseEvent e) {
  //System.out.println("mouse moved");
  //gamePanel.setRectPos(e.getX(),  e.getY());
 }
}
