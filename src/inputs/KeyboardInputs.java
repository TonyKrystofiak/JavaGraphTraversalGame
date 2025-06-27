package inputs;

import main.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener{
  private GamePanel gamePanel;
  
  /**
   * Keyboard Inputs class
   * @param GamePanel gamePanel
   */
  public KeyboardInputs(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }
  /**
   * Registers when a certain key is pressed
   * @param KeyEvent e registers when a key is pressed
   */
  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
    if (e.getKeyChar() == 'a') {
      gamePanel.getPlayer().setPressedLeft(true);
      //System.out.println("left " + isPressedLeft);
    } 
    else if (e.getKeyChar() == 'd') {
    	gamePanel.getPlayer().setPressedRight(true);
      //System.out.println("right " + isPressedRight);
    } 
    else if (e.getKeyChar() == 'w') {
    	gamePanel.getPlayer().setPressedUp(true);
      //System.out.println("up " + isPressedUp);
    } 
    else if (e.getKeyChar() == 's') {
    	gamePanel.getPlayer().setPressedDown(true);
      //System.out.println("down " + isPressedDown);
    }
    else if (e.getKeyChar() == 'm') {
    	gamePanel.makeMenu();
    }
  }
  /**
   * Registers when a certain key is released
   * @param KeyEvent e registers when a key is released
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_A) {
    	gamePanel.getPlayer().setPressedLeft(false);
      //System.out.println("left " + gamePanel.isPressedLeft);
    }
    else if (e.getKeyCode() == KeyEvent.VK_D) {
    	gamePanel.getPlayer().setPressedRight(false);
      //System.out.println("right " + isPressedRight);
    } 
    else if (e.getKeyCode() == KeyEvent.VK_W) {
    	gamePanel.getPlayer().setPressedUp(false);
      //System.out.println("up " + isPressedUp);
    } 
    else if (e.getKeyCode() == KeyEvent.VK_S) {
    	gamePanel.getPlayer().setPressedDown(false);
      //System.out.println("down " + isPressedDown);
    } 
  }
  /**
   * Registers when a certain key is pressed
   * @param KeyEvent e registers when there is a keyInput
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      System.out.println("You are pressing the Space key");
    }
  }
}
