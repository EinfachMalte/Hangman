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

public class BotCtrl extends JPanel{
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
	public BotCtrl() {
		// Größe und zuordnung der JButtons
		Main.frame.setPreferredSize(new Dimension(600, 600));
		setLayout(new GridLayout(3, 1, 5, 5));

		// Buttons
		JButton b1 = new JButton();
		JButton b2 = new JButton();
		JButton b3 = new JButton();

		b1.setSize(w, h / 3);
		b2.setSize(w, h / 3);
		b3.setSize(w, h / 3);

		// Buttontext
		b1.setText("Leicht");
		b2.setText("Normal");
		b3.setText("Schwer");

		// Hintergrund
		b1.setBackground(Color.black);
		b2.setBackground(Color.black);
		b3.setBackground(Color.black);

		// Vordergrund
		b1.setForeground(Color.white);
		b2.setForeground(Color.white);
		b3.setForeground(Color.white);

		// JButtons hinzufügen
		add(b1);
		add(b2);
		add(b3);

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				w = Main.frame.getWidth();
				h = Main.frame.getHeight();

				// Größe der Schrift
				Font font = new Font("SansSerif", Font.BOLD, w / 11);
				b1.setFont(font);
				b2.setFont(font);
				b3.setFont(font);

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
				b3.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						wahl = 3;
						loop = true;
					}
				});

				// Welcher JButton wurde gedrückt?
				while (loop == true) {
					if (wahl == 1) {
						Main.frame.add(Main.bot = new Bot(), BorderLayout.CENTER);
						loop = false;
						setVisible(false);
						break;
					} else if (wahl == 2) {
						Main.frame.add(Main.bot = new Bot(), BorderLayout.CENTER);
						loop = false;
						setVisible(false);
						break;
					} else if (wahl == 3) {
						Main.frame.add(Main.bot = new Bot(), BorderLayout.CENTER);
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
