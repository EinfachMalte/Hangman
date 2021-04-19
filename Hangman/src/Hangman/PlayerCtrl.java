package Hangman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayerCtrl extends JPanel {
	private static final long serialVersionUID = 1L;
	static Timer timer;
	static int wahl = 0;
	static boolean loop = false;
	static int w;
	static int h;
	
	/**
	 * Erstellt JButtons und ButtonListener um zu überprüfen welcher gedrückt wurde 
	 * Den Wert des JButtons wird wahl zugewiesen
	 */
	public PlayerCtrl() {
		Main.frame.setPreferredSize(new Dimension(600, 600));
		setLayout(new GridLayout(2, 1, 5, 5));

		// Buttons
		JButton b1 = new JButton();
		JButton b2 = new JButton();

		b1.setSize(w, h / 3);
		b2.setSize(w, h / 3);
		
		// Buttontext
		b1.setText("Eins gegen Eins");
		b2.setText("Einer gegen alle");

		// Hintergrund
		b1.setBackground(Color.black);
		b2.setBackground(Color.black);

		// Vordergrund
		b1.setForeground(Color.white);
		b2.setForeground(Color.white);
	
		// JButtons hinzufügen
		add(b1);
		add(b2);

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				w = Main.frame.getWidth();
				h = Main.frame.getHeight();

				// Größe der Schrift
				Font font = new Font("SansSerif", Font.BOLD, w / 12);
				b1.setFont(font);
				b2.setFont(font);

				// ActionListener hinzufügen
				b1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						wahl = 1;
						loop = true;
					}
				});
				b2.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						wahl = 2;
						loop = true;
					}
				});

				// Welcher JButton wurde geklickt?
				while (loop == true) {
					if (wahl == 1) {
						Main.frame.add(Main.pvp = new PlayerVsPlayer(), BorderLayout.CENTER);
						loop = false;
						setVisible(false);
						break;
					} else if (wahl == 2) {
						Main.frame.add(Main.playerVsGroup = new PlayerVsGroup(), BorderLayout.CENTER);
						loop = false;
						setVisible(false);
						break;
					}
				}
			}
		}, 0, 15);
		Main.frame.pack();
	}
}
