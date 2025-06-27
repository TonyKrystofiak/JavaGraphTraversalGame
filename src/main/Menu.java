/**
 * The Menu of the game
 * @author Tony krystofiak
 * @date 12/10/23
 */
package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import map.*;
import interactives.*;

public class Menu implements ActionListener {

	private boolean start;
	private boolean hasName = false;
	private String playerName;

	private int width = 240;
	private int height = 510;
	
	private JButton buttonRoomData;
	private JButton buttonEdgeData;
	private JButton buttonHint;
	private JButton buttonSave;
	private JButton buttonLoad;
	private JButton buttonClose;
	
	private JTextField textField;
	
	private JFrame frame;
	
	private JPanel panel;
	
	private GameLayout g;
	
	private GamePanel gamePanel;	
	
	private RoomGraph rg;
	
	private Player player;
	/**
	 * Constructs the menu
	 * @param gamePanel
	 * @param rg
	 * @param player
	 */
		public Menu(GamePanel gamePanel, RoomGraph rg, Player player) {
			this.rg = rg;
			this.player = player;

			
			panel = new JPanel();
			panel.setBounds(0, 0, width, height);
			panel.setLayout(new FlowLayout());
			
			JLabel label = new JLabel();
			label.setText("Player Name: ");
			label.setBounds(130, 10, 100, 30);
			
			buttonRoomData = new JButton("Room Data");
			buttonRoomData.addActionListener(this);
			buttonRoomData.setBounds(10, 10, 200, 60);
			
			buttonEdgeData = new JButton("Edge Data");
			buttonEdgeData.addActionListener(this);
			buttonEdgeData.setBounds(10, 80, 200, 60);
			
			buttonHint = new JButton("Hint");
			buttonHint.addActionListener(this);
			buttonHint.setBounds(10, 150, 200, 60);
			
			buttonSave = new JButton("Save Game");
			buttonSave.addActionListener(this);
			buttonSave.setBounds(10, 220, 200, 60);
			
			buttonLoad = new JButton("Load Game");
			buttonLoad.addActionListener(this);
			buttonLoad.setBounds(10, 290, 200, 60);
			
			buttonClose = new JButton("Close Menu");
			buttonClose.addActionListener(this);
			buttonClose.setBounds(10, 360, 200, 60);
		
	//		textField = new JTextField();
	//		textField.setPreferredSize(new Dimension(50, 20));
	//		textField.setBounds(130, 50, 100, 30);
		

			panel.add(buttonRoomData);
			panel.add(buttonEdgeData);
			panel.add(buttonHint);
			panel.add(buttonSave);
			panel.add(buttonLoad);
			panel.add(buttonClose);
		
	//		panel.add(label);
	//		panel.add(textField);
			panel.setLayout(null);
			
			frame = new JFrame();
			frame.setTitle("Start");
			frame.setSize(width, height);
			frame.setBackground(new Color(100, 150, 100));
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//frame.getContentPane().setBackground(new Color(120, 100, 150));
			frame.add(panel, BorderLayout.CENTER);
			//frame.pack();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == buttonRoomData) {
				//System.out.println("Room Data Goes Here");
				System.out.println(this.rg.getRoomData(player.getCurRoom()));
			}
			
			if (e.getSource() == buttonEdgeData) {
				//System.out.println("Edge Data Goes Here");
				this.rg.getEdgeData(player.getCurRoom());
			}
			
			if (e.getSource() == buttonHint) {
				System.out.println("Hint...");
			}
			
			if (e.getSource() == buttonSave) {
				System.out.println("Saving game...");
				this.rg.save();
			}
			
			if (e.getSource() == buttonLoad) {
				System.out.println("Loading save...");
				this.rg.load();
			}
			if (e.getSource() == buttonClose) {
				System.out.println("Closing Menu");
				frame.dispose();
			}			
		}
}

