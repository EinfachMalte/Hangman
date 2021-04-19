package Hangman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Ctrl extends JPanel {
	private static final long serialVersionUID = 1L;
	static Timer timer;
	static int wahl = 0;
	static boolean loop = false;
	static boolean leereDatei = true;
	static int w;
	static int h;
	static String[] values = null;
	static ArrayList<String> dataList = new ArrayList<String>();

	/**
	 * Erstellt JButtons und ButtonListener um zu überprüfen welcher gedrückt wurde
	 * Den Wert des JButtons wird wahl zugewiesen
	 */
	public Ctrl() {
		Main.frame.setPreferredSize(new Dimension(600, 600));
		setLayout(new GridLayout(4, 1, 5, 5));

		// Buttons
		JButton b1 = new JButton();
		JButton b2 = new JButton();
		JButton b3 = new JButton();
		JButton b4 = new JButton();

		b1.setSize(w, h / 4);
		b2.setSize(w, h / 4);
		b3.setSize(w, h / 4);
		b4.setSize(w, h / 4);

		// Buttontext
		b1.setText("Patnerspiel");
		b2.setText("Computer Gegner");
		b3.setText("Level");
		b4.setText("Speicherpunkt laden");

		// Hintergrund
		b1.setBackground(Color.black);
		b2.setBackground(Color.black);
		b3.setBackground(Color.black);
		b4.setBackground(Color.black);

		// Vordergrund
		b1.setForeground(Color.white);
		b2.setForeground(Color.white);
		b3.setForeground(Color.white);
		b4.setForeground(Color.white);

		// JButtons hinzufügen
		add(b1);
		add(b2);
		add(b3);
		add(b4);

		try {
			// Spielstand einlesen
			try {
				FileReader dataFile = new FileReader("Spielstand.txt");
				BufferedReader inputStream = new BufferedReader(dataFile);
				String line;

				try {
					while ((line = inputStream.readLine()) != null) {
						values = line.split("\n");

						dataList.add(values[0]);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!dataList.get(0).equalsIgnoreCase("leer")) {
				// Wenn Spielstand nicht leer
				leereDatei = false;
			} else {
				// Wenn Spielstand leer
				b4.setText("Strg+S zum Speichern");
				b4.setForeground(Color.red);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ein Fehler ist aufgetreten!\nGeben sie in der Datei Spielstand.txt LEER ein!");
			b4.setText("Strg+S zum Speichern");
			b4.setForeground(Color.red);
		}

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
				b3.setFont(font);
				b4.setFont(font);

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
				if (leereDatei == false) {
					b4.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							wahl = 4;
							loop = true;
						}
					});
				}

				// Welcher JButton wurde gedrückt?
				while (loop == true) {
					if (wahl == 1) {
						Main.frame.add(Main.playerCtrl = new PlayerCtrl(), BorderLayout.CENTER);
						loop = false;
						setVisible(false);
						break;
					} else if (wahl == 2) {
						Main.frame.add(Main.botCtrl = new BotCtrl(), BorderLayout.CENTER);
						loop = false;
						setVisible(false);
						break;
					} else if (wahl == 3) {
						Main.frame.add(Main.lvl = new Level(), BorderLayout.CENTER);
						loop = false;
						setVisible(false);
						break;
					} else if (wahl == 4 && leereDatei == false) {
						Main.frame.add(Main.spielstand = new Spielstand(), BorderLayout.CENTER);
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
