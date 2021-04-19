package Hangman;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	static Startbildschirm startBild;
	static Ctrl ctrl;
	static PlayerCtrl playerCtrl;
	static PlayerVsGroup playerVsGroup;
	static PlayerVsPlayer pvp;
	static BotCtrl botCtrl;
	static Bot bot;
	static Level lvl;
	static Spielstand spielstand;
	static JFrame frame; // Draws the frame
	static JPanel topPanel = new JPanel();
	static JLabel label = new JLabel();
	
	public Main() {
		frame = new JFrame();
		frame.setTitle("Hangman");
		topPanel.setLayout(new FlowLayout());
		frame.add(startBild = new Startbildschirm(), BorderLayout.CENTER); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1600, 800);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * @author EinfachMalte
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}
}
