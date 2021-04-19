package Hangman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Startbildschirm extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	static BufferedImage startBild;
	static JTextField tf = new JTextField();
	static String text;
	public int w = getWidth();
	public int h = getHeight();
	static Font font = new Font("Arial", Font.BOLD, 50);
	static Timer timer;
	static boolean bestÃ¤tigen = false;
	static int richtigerBuchstabe;
	
	/**
	 * Startet die Startbildschirm Class
	 */
	public Startbildschirm() {
		// Hinzufügen eines Keylisteners für tf
		tf.addKeyListener(this);
		setFocusable(true);
		requestFocus();
		
		// Startbild laden
		try {
			startBild = ImageIO.read(new File("Bilder/start.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Bilder nicht gefunden");
		}
		
		// JTextfield hinzufügen
		add(tf);
	}
	
	/**
	 * Zeichnet den Startbildschirm und das JTextField
	 */
	public void paintComponent(Graphics g) {
		w = getWidth();
		h = getHeight();
		super.paintComponent(g);
		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(startBild, 0, 0, w, h, null);
		tf.setBounds((int) (w / 1.311), (int) (h / 1.48), (int) ((int) w / 20.5), (int) (h / 7));
		tf.setFont(font);
		tf.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// KeyListener
		text = tf.getText();
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (text.equalsIgnoreCase("m")) {
				// Wenn man m oder M eingegeben hat kommt man zur Ctrl
				setVisible(false);
				Main.frame.add(Main.ctrl = new Ctrl(), BorderLayout.CENTER);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
