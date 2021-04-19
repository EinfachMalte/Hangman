package Hangman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Spielstand extends JPanel implements KeyListener, ChangeListener {
	private static final long serialVersionUID = 1L;
	static JTextField eingabe = new JTextField();
	static JTextField eingabeSpieler2 = new JTextField();
	static JTextField wörterEingeben = new JTextField();
	static JLabel label = new JLabel();
	static String ratewort = "", ratewortSpieler2 = "";
	static String verschlüsselt = "", verschlüsseltSpieler2 = "";
	static String buchstabe = "", buchstabeSpieler2 = "";
	static String überprüfung, überprüfungSpieler2;
	static Font font = new Font("Arial", Font.BOLD, 20);
	static boolean enter = false;
	static boolean wortEingeben = true, wortEingebenSpieler2 = true;
	static boolean nichtErlaubterBuchstabe = false, nichtErlaubterBuchstabeSpier2 = false;
	static String falscherBuchstabe = "", falscherBuchstabeSpieler2 = "";
	static boolean nichtEnthalten = true, nichtEnthaltenSpieler2 = true;
	static boolean erraten = false, erratenSpieler2 = false;
	static boolean ratewortEingegeben = false, ratewortEingebenSpieler2 = false;
	static boolean gehängt = false, gehängtSpieler2 = false;
	static boolean gewonnen = false;
	int w = getWidth();
	int h = getHeight();
	static int fehlVersuche = 0, fehlVersucheSpieler2 = 0;
	static int versuche = 0;
	static int bildCounter = 0, bildCounterSpieler2 = 0;
	static boolean bilderLaden = false;
	static boolean wortGenerieren = true;
	static int maxLenght, minLenght;
	static String labelText = "", labelTextSpieler2 = "", neustart = "Gib das Wort für Spieler 1 ein";
	static ArrayList<String> benutzteBuchstaben = new ArrayList<String>();
	static ArrayList<String> benutzteBuchstabenSpieler2 = new ArrayList<String>();
	static ArrayList<String> benutzteWörter = new ArrayList<String>();
	static ArrayList<String> einfacheWörter = new ArrayList<String>(), mittlereWörter = new ArrayList<String>(),
			schwereWörter = new ArrayList<String>();

	static StringBuilder sb;

	static File file;
	static String fileName = "";
	static String[] values = null;
	static ArrayList<String> list = new ArrayList<String>();
	static Scanner inputStream;
	static int random = 0;
	static int einfacheLevel = 0, mittlereLevel = 0, schwereLevel = 0;

	static BufferedImage verloren, boden, stab, galgen, seil, kopf, hals, linkerArm, beideArme, bauch, linkesBein,
			anzeigebild, anzeigebildSpieler2, hintergrund;

	static JSlider slider1 = new JSlider(0, 5, 5), slider2 = new JSlider(0, 5, 5), slider3 = new JSlider(0, 5, 5);
	static JLabel anzeige1 = new JLabel(), anzeige2 = new JLabel(), anzeige3 = new JLabel();
	static int einfach = 0, mittel = 0, schwer = 0;
	static int counter = 0;
	static int spieleGewonnen = 0;
	static int botWahl = 0;
	static boolean exit = false;
	static boolean eingabeEinfügen = true;

	static PrintWriter writer;

	static ArrayList<String> dataList = new ArrayList<String>();

	/**
	 * Start der Spielstad Class
	 */
	@SuppressWarnings("unused")
	public Spielstand() {
		// Lädt die Datei in die ArrayList dataList
		String fileName = "Spielstand.txt";
		String[] values = null;
		ArrayList<String> list = new ArrayList<String>();
		File file = new File(fileName);
		try {
			FileReader dataFile = new FileReader(fileName);
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

		// Wenn Level gespielt wurden
		if (dataList.get(0).equals("Level")) {
			// Das erste Bild wird geladen
			try {
				verloren = ImageIO.read(new File("Bilder/verloren.jpg"));
				anzeigebild = verloren;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Höhe und Breite des Frames
			int w = Main.frame.getWidth();
			int h = Main.frame.getHeight();

			// Hinzufügen eines KeyListeners
			eingabe.addKeyListener(this);
			addKeyListener(this);
			setFocusable(true);
			requestFocus();
			Main.frame.setResizable(false);

			// Alle Variablen werden mit den Daten aus der dataList beschrieben
			einfach = Integer.parseInt(dataList.get(1));
			mittel = Integer.parseInt(dataList.get(2));
			schwer = Integer.parseInt(dataList.get(3));
			counter = Integer.parseInt(dataList.get(4));
			spieleGewonnen = Integer.parseInt(dataList.get(5));
			ratewort = dataList.get(6);
			verschlüsselt = dataList.get(7);
			buchstabe = dataList.get(8);
			überprüfung = dataList.get(9);
			enter = Boolean.getBoolean(dataList.get(10));
			wortEingeben = Boolean.getBoolean(dataList.get(11));
			nichtErlaubterBuchstabe = Boolean.getBoolean(dataList.get(12));
			falscherBuchstabe = dataList.get(13);
			nichtEnthalten = Boolean.getBoolean(dataList.get(14));
			erraten = Boolean.getBoolean(dataList.get(15));
			ratewortEingegeben = Boolean.getBoolean(dataList.get(16));
			gehängt = Boolean.getBoolean(dataList.get(17));
			fehlVersuche = Integer.parseInt(dataList.get(18));
			versuche = Integer.parseInt(dataList.get(19));
			maxLenght = Integer.parseInt(dataList.get(20));
			benutzteBuchstaben.add(dataList.get(21));
			benutzteWörter.add(dataList.get(22));
			bildCounter = Integer.parseInt(dataList.get(23));
			bilderLaden = Boolean.getBoolean(dataList.get(24));
			wortGenerieren = Boolean.getBoolean(dataList.get(25));

			counter = 3;

			// Bilder laden
			bildCounter = fehlVersuche;
			bilderLaden();

			if (falscherBuchstabe == "leer") {
				falscherBuchstabe = "";
			}

			// Lädt die leichten Wörter in die ArrayList list
			fileName = "HangmanLeich.txt";
			try {
				file = new File(fileName);
				inputStream = new Scanner(file);

				while (inputStream.hasNext()) {
					String data = inputStream.next();
					values = data.split("\n");

					list.add(values[0]);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Datei nicht gefunden");
			}
		} else if (dataList.get(0).equals("SpielerVsGruppe")) {
			// Wenn man Spieler gegen Gruppe gespielt hat

			// Hinzufügen eines KeyListeners
			eingabe.addKeyListener(this);
			addKeyListener(this);
			setFocusable(true);
			requestFocus();
			add(eingabe);

			// Erstes Bild laden
			try {
				verloren = ImageIO.read(new File("Bilder/verloren.jpg"));
				anzeigebild = verloren;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Alle Variablen werden mit den Daten aus der dataList beschrieben
			ratewort = dataList.get(1);
			verschlüsselt = dataList.get(2);
			buchstabe = dataList.get(3);
			überprüfung = dataList.get(4);
			enter = Boolean.getBoolean(dataList.get(5));
			wortEingeben = Boolean.getBoolean(dataList.get(6));
			nichtErlaubterBuchstabe = Boolean.getBoolean(dataList.get(7));
			falscherBuchstabe = dataList.get(8);
			nichtEnthalten = Boolean.getBoolean(dataList.get(9));
			erraten = Boolean.getBoolean(dataList.get(10));
			ratewortEingegeben = Boolean.getBoolean(dataList.get(11));
			gehängt = Boolean.getBoolean(dataList.get(12));
			fehlVersuche = Integer.parseInt(dataList.get(13));
			versuche = Integer.parseInt(dataList.get(14));
			maxLenght = Integer.parseInt(dataList.get(15));
			benutzteBuchstaben.add(dataList.get(16));
			bildCounter = Integer.parseInt(dataList.get(17));
			bilderLaden = Boolean.getBoolean(dataList.get(18));

			// Bilder Laden
			bildCounter = fehlVersuche + 1;
			bilderLaden();

			if (falscherBuchstabe == "leer") {
				falscherBuchstabe = "";
			}

			labelText = "Ratewort: " + verschlüsselt;
		} else if (dataList.get(0).equals("SpielerVsSpieler")) {
			// Wenn man Spieler gegen Spieler gespielt hat
			Main.frame.setSize(652 * 2, 800);

			// Hinzufügen eines KeyListeners
			eingabe.addKeyListener(this);
			eingabeSpieler2.addKeyListener(this);
			wörterEingeben.addKeyListener(this);
			addKeyListener(this);
			setFocusable(true);
			requestFocus();
			add(wörterEingeben);

			// Erstes Bild laden
			try {
				verloren = ImageIO.read(new File("Bilder/verloren.jpg"));
				anzeigebild = verloren;
				anzeigebildSpieler2 = verloren;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Bilder nicht gefunden");
			}

			// Alle Variablen werden mit den Daten aus der dataList beschrieben
			ratewort = dataList.get(1);
			ratewortSpieler2 = dataList.get(2);
			verschlüsselt = dataList.get(3);
			verschlüsseltSpieler2 = dataList.get(4);
			buchstabe = dataList.get(5);
			buchstabeSpieler2 = dataList.get(6);
			überprüfung = dataList.get(7);
			überprüfungSpieler2 = dataList.get(8);
			enter = Boolean.getBoolean(dataList.get(9));
			wortEingeben = Boolean.getBoolean(dataList.get(10));
			wortEingebenSpieler2 = Boolean.getBoolean(dataList.get(11));
			nichtErlaubterBuchstabe = Boolean.getBoolean(dataList.get(12));
			nichtErlaubterBuchstabeSpier2 = Boolean.getBoolean(dataList.get(13));
			falscherBuchstabe = dataList.get(14);
			falscherBuchstabeSpieler2 = dataList.get(15);
			nichtEnthalten = Boolean.getBoolean(dataList.get(16));
			nichtEnthaltenSpieler2 = Boolean.getBoolean(dataList.get(17));
			erraten = Boolean.getBoolean(dataList.get(18));
			erratenSpieler2 = Boolean.getBoolean(dataList.get(19));
			ratewortEingegeben = Boolean.getBoolean(dataList.get(20));
			ratewortEingebenSpieler2 = Boolean.getBoolean(dataList.get(21));
			gehängt = Boolean.getBoolean(dataList.get(22));
			gehängtSpieler2 = Boolean.getBoolean(dataList.get(23));
			gewonnen = Boolean.getBoolean(dataList.get(24));
			fehlVersuche = Integer.parseInt(dataList.get(25));
			fehlVersucheSpieler2 = Integer.parseInt(dataList.get(26));
			versuche = Integer.parseInt(dataList.get(27));
			maxLenght = Integer.parseInt(dataList.get(28));
			benutzteBuchstaben.add(dataList.get(29));
			benutzteBuchstabenSpieler2.add(dataList.get(30));
			bildCounter = Integer.parseInt(dataList.get(31));

			// Bilder laden
			bildCounter = fehlVersuche + 1;
			bilderLaden();
			bildCounterSpieler2 = fehlVersucheSpieler2 + 1;
			bilderLadenSpieler2();

			if (falscherBuchstabe == "leer") {
				falscherBuchstabe = "";
			}
			if (falscherBuchstabeSpieler2 == "leer") {
				falscherBuchstabeSpieler2 = "";
			}

			labelText = "Ratewort: " + verschlüsselt;
			labelTextSpieler2 = "Ratewort: " + verschlüsseltSpieler2;

			if (wortEingeben == false && wortEingebenSpieler2 == false) {
				remove(wörterEingeben);
				add(eingabe);
				add(eingabeSpieler2);
			}
		} else if (dataList.get(0).equals("Bot")) {
			// Wenn man gegen den Computer gespielt hat
			eingabe.addKeyListener(this);
			addKeyListener(this);
			setFocusable(true);
			requestFocus();

			// Erstes Bild laden
			try {
				verloren = ImageIO.read(new File("Bilder/verloren.jpg"));
				anzeigebild = verloren;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Bilder nicht gefunden");
			}

			// Alle Variablen werden mit den Daten aus der dataList beschrieben
			ratewort = dataList.get(1);
			verschlüsselt = dataList.get(2);
			buchstabe = dataList.get(3);
			überprüfung = dataList.get(4);
			enter = Boolean.getBoolean(dataList.get(5));
			wortEingeben = Boolean.getBoolean(dataList.get(6));
			nichtErlaubterBuchstabe = Boolean.getBoolean(dataList.get(7));
			falscherBuchstabe = dataList.get(8);
			nichtEnthalten = Boolean.getBoolean(dataList.get(9));
			erraten = Boolean.getBoolean(dataList.get(10));
			ratewortEingegeben = Boolean.getBoolean(dataList.get(11));
			gehängt = Boolean.getBoolean(dataList.get(12));
			fehlVersuche = Integer.parseInt(dataList.get(13));
			versuche = Integer.parseInt(dataList.get(14));
			maxLenght = Integer.parseInt(dataList.get(15));
			benutzteBuchstaben.add(dataList.get(16));
			bildCounter = Integer.parseInt(dataList.get(17));
			bilderLaden = Boolean.getBoolean(dataList.get(18));
			botWahl = Integer.parseInt(dataList.get(19));

			// Bilder laden
			bildCounter = fehlVersuche + 1;
			bilderLaden();

			if (falscherBuchstabe == "leer") {
				falscherBuchstabe = "";
			}

			labelText = "Drücke ENTER um weiter zu spielen";

			try {
				writer = new PrintWriter(new BufferedWriter(new FileWriter("Spielstand.txt")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Main.frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// Wenn das Spiel geschlossen wird
				Main.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				exit = true;
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * @param g
	 * @param g2d Zeichnen der Bilder und Texte
	 */
	public void paintComponent(Graphics g) {
		if (exit == true) {
			exit = false;
			exit();
		}

		// Wenn man Level gespielt hat
		if (dataList.get(0).equals("Level")) {
			super.paintComponent(g);

			// Bildhöhe und -Breite
			int w = getWidth();
			int h = getHeight();

			g.setColor(Color.white);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.white);

			// Um den String labelText zu zentrieren
			int stringLenght = (int) g2d.getFontMetrics().getStringBounds(labelText, g2d).getWidth();
			int width = w / 2 - stringLenght / 2;

			if (counter == 3) {
				anzeigebild = verloren;
				g2d.drawImage(anzeigebild, 0, 0, w, h, null);
				labelText = "Drücke ENTER um weiter zu Spielen";
				g.setColor(Color.black);
				g.drawString(labelText, width, h / 30);
			} else if (counter > 3) {
				// Welcher Teil vom Hangman soll gezeichnet werden
				if (fehlVersuche == 0 && bilderLaden == true) {
					anzeigebild = boden;
				} else if (fehlVersuche == 1) {
					anzeigebild = stab;
				} else if (fehlVersuche == 2) {
					anzeigebild = galgen;
				} else if (fehlVersuche == 3) {
					anzeigebild = seil;
				} else if (fehlVersuche == 4) {
					anzeigebild = kopf;
				} else if (fehlVersuche == 5) {
					anzeigebild = hals;
				} else if (fehlVersuche == 6) {
					anzeigebild = linkerArm;
				} else if (fehlVersuche == 7) {
					anzeigebild = beideArme;
				} else if (fehlVersuche == 8) {
					anzeigebild = bauch;
				} else if (fehlVersuche == 9) {
					anzeigebild = linkesBein;
				} else if (fehlVersuche == 10) {
					labelText = "Du wurdest gehängt: Drücke ENTER um neu zu starten und ESC um zu verlassen";
					anzeigebild = verloren;
					gehängt = true;
				}

				repaint();

				// Wenn gewonnen oder verloren
				if (gehängt == true || erraten == true) {
					remove(eingabe);
				}

				// Wenn das wort erraten wurde
				if (ratewort.equals(verschlüsselt)) {
					labelText = "Du hast das Wort erraten: Drücke ENTER um weiter zu spielen";
				}

				// Wenn alle level durchgespiel wurden
				if (spieleGewonnen == (einfach + mittel + schwer)) {
					labelText = "Herzlichen Glückwunsch. Du hast alle level gemeistert: Drücke ESC um zu beenden";
				}

				do {
					// Zeichnen des Strings und des Hangmans
					stringLenght = (int) g2d.getFontMetrics().getStringBounds(labelText, g2d).getWidth();
					width = w / 2 - stringLenght / 2;
					g2d.drawImage(anzeigebild, 0, 0, w, h, null);
					g.setColor(Color.black);
					g.drawString(labelText, width, h / 30);
					label.setText(labelText);
					eingabe.setBounds(w / 20, h - 20, w - w / 10, 20);
					eingabe.setFont(font);
					eingabe.setHorizontalAlignment(SwingConstants.CENTER);
				} while (labelText.equals(ratewort));
			}
			repaint();
		} else if (dataList.get(0).equals("SpielerVsGruppe")) {
			// Welcher Teil vom Hangman soll gezeichnet werden
			if (fehlVersuche == 0 && bilderLaden == true) {
				anzeigebild = boden;
			} else if (fehlVersuche == 1) {
				anzeigebild = stab;
			} else if (fehlVersuche == 2) {
				anzeigebild = galgen;
			} else if (fehlVersuche == 3) {
				anzeigebild = seil;
			} else if (fehlVersuche == 4) {
				anzeigebild = kopf;
			} else if (fehlVersuche == 5) {
				anzeigebild = hals;
			} else if (fehlVersuche == 6) {
				anzeigebild = linkerArm;
			} else if (fehlVersuche == 7) {
				anzeigebild = beideArme;
			} else if (fehlVersuche == 8) {
				anzeigebild = bauch;
			} else if (fehlVersuche == 9) {
				anzeigebild = linkesBein;
			} else if (fehlVersuche == 10) {
				anzeigebild = verloren;
				labelText = "Du hast verloren";
				gehängt = true;
			}

			repaint();

			// Wenn gewonnen oder verloren
			if (gehängt == true || erraten == true) {
				remove(eingabe);
			}

			if (erraten == true) {
				labelText = "Du hast das Wort erraten: Drücke ENTER um neu zu starten oder ESC um zu verlassen";
			}

			w = getWidth();
			h = getHeight();
			super.paintComponent(g);
			g.setColor(Color.black);
			Graphics g2d = (Graphics2D) g;

			// Um den String labelText zu zentrieren
			int stringLenght = (int) g2d.getFontMetrics().getStringBounds(labelText, g2d).getWidth();
			int width = w / 2 - stringLenght / 2;

			// Zeichnen des Strings und des Hangmans
			g2d.drawImage(anzeigebild, 0, 0, w, h, null);
			g.drawString(labelText, width, h / 30);
			label.setText(labelText);
			eingabe.setBounds(w / 20, h - 20, w - w / 10, 20);
			eingabe.setFont(font);
			eingabe.setHorizontalAlignment(SwingConstants.CENTER);
		} else if (dataList.get(0).equals("SpielerVsSpieler")) {
			// Wenn man Spieler gegen Spieler gespielt hat

			// Welcher Teil vom Hangman soll für Spieler 1 gezeichnet werden
			if (fehlVersuche == 0 && wortEingeben == false) {
				anzeigebild = boden;
			}
			if (fehlVersuche == 1) {
				anzeigebild = stab;
			} else if (fehlVersuche == 2) {
				anzeigebild = galgen;
			} else if (fehlVersuche == 3) {
				anzeigebild = seil;
			} else if (fehlVersuche == 4) {
				anzeigebild = kopf;
			} else if (fehlVersuche == 5) {
				anzeigebild = hals;
			} else if (fehlVersuche == 6) {
				anzeigebild = linkerArm;
			} else if (fehlVersuche == 7) {
				anzeigebild = beideArme;
			} else if (fehlVersuche == 8) {
				anzeigebild = bauch;
			} else if (fehlVersuche == 9) {
				anzeigebild = linkesBein;
			} else if (fehlVersuche == 10) {
				anzeigebild = verloren;
				labelText = "Du hast verloren";
				labelTextSpieler2 = "Du hast gewonnen";
				neustart = "Drücke ENTER um neu zu starten und ESC um zu verlassen";
				gehängt = true;
			}

			// Welcher Teil vom Hangman soll für Spieler 2 gezeichnet werden
			if (fehlVersucheSpieler2 == 0 && wortEingebenSpieler2 == false) {
				anzeigebildSpieler2 = boden;
			}
			if (fehlVersucheSpieler2 == 1) {
				anzeigebildSpieler2 = stab;
			} else if (fehlVersucheSpieler2 == 2) {
				anzeigebildSpieler2 = galgen;
			} else if (fehlVersucheSpieler2 == 3) {
				anzeigebildSpieler2 = seil;
			} else if (fehlVersucheSpieler2 == 4) {
				anzeigebildSpieler2 = kopf;
			} else if (fehlVersucheSpieler2 == 5) {
				anzeigebildSpieler2 = hals;
			} else if (fehlVersucheSpieler2 == 6) {
				anzeigebildSpieler2 = linkerArm;
			} else if (fehlVersucheSpieler2 == 7) {
				anzeigebildSpieler2 = beideArme;
			} else if (fehlVersucheSpieler2 == 8) {
				anzeigebildSpieler2 = bauch;
			} else if (fehlVersucheSpieler2 == 9) {
				anzeigebildSpieler2 = linkesBein;
			} else if (fehlVersucheSpieler2 == 10) {
				anzeigebildSpieler2 = verloren;
				labelTextSpieler2 = "Du hast verloren";
				labelText = "Du hast gewonnen";
				neustart = "Drücke ENTER um neu zu starten und ESC um zu verlassen";
				gehängtSpieler2 = true;
			}

			if (fehlVersuche == 10 && fehlVersucheSpieler2 == 10) {
				labelText = "Unentschieden";
				labelTextSpieler2 = "Unentschiden";
				neustart = "Drücke ENTER um neu zu starten und ESC um zu verlassen";
			}

			if (gehängt == true || gehängtSpieler2 == true || gewonnen == true) {
				remove(eingabe);
				remove(eingabeSpieler2);
			}

			repaint();

			w = getWidth();
			h = getHeight();
			super.paintComponent(g);
			g.setColor(Color.black);
			Graphics g2d = (Graphics2D) g;

			// Position der Strings labelText, labelTextSpieler2 und neustart
			int stringLenght = (int) g2d.getFontMetrics().getStringBounds(labelText, g2d).getWidth();
			int stringLenghtSpieler2 = (int) g2d.getFontMetrics().getStringBounds(labelTextSpieler2, g2d).getWidth();
			int stringWidth = w / 4 - stringLenght / 2;
			int stringWidthSpieler2 = w / 2 + (w / 4) - stringLenghtSpieler2 / 2;
			int stringLenghtNeustart = (int) g2d.getFontMetrics().getStringBounds(neustart, g2d).getWidth();
			int stringWidthNeustart = w / 2 - stringLenghtNeustart / 2;

			// Zeichnen der Strings und Hangmans
			g2d.drawImage(anzeigebild, 0, 0, w / 2, h, null);
			g2d.drawImage(anzeigebildSpieler2, w / 2, 0, w / 2, h, null);
			g.drawString(labelText, stringWidth, h / 30);
			g.drawString(labelTextSpieler2, stringWidthSpieler2, h / 30);
			g.drawString(neustart, stringWidthNeustart, h / 30);
			label.setText(labelText);
			eingabe.setBounds(w / 20, h - 20, (w / 2) - (w / 10), 20);
			eingabeSpieler2.setBounds(w / 2 + (w / 20), h - 20, (w / 2) - (w / 10), 20);
			wörterEingeben.setBounds(w / 20, h - 20, w - w / 10, 20);
			eingabe.setFont(font);
			eingabeSpieler2.setFont(font);
			wörterEingeben.setFont(font);
			eingabe.setHorizontalAlignment(SwingConstants.CENTER);
			eingabeSpieler2.setHorizontalAlignment(SwingConstants.CENTER);
			wörterEingeben.setHorizontalAlignment(SwingConstants.CENTER);
		} else if (dataList.get(0).equals("Bot")) {
			// Wenn man gegen den Computer gespielt hat

			// Welcher Teil vom Hangman soll gezeichnet werden
			if (fehlVersuche == 0 && bilderLaden == true) {
				anzeigebild = boden;
			} else if (fehlVersuche == 1) {
				anzeigebild = stab;
			} else if (fehlVersuche == 2) {
				anzeigebild = galgen;
			} else if (fehlVersuche == 3) {
				anzeigebild = seil;
			} else if (fehlVersuche == 4) {
				anzeigebild = kopf;
			} else if (fehlVersuche == 5) {
				anzeigebild = hals;
			} else if (fehlVersuche == 6) {
				anzeigebild = linkerArm;
			} else if (fehlVersuche == 7) {
				anzeigebild = beideArme;
			} else if (fehlVersuche == 8) {
				anzeigebild = bauch;
			} else if (fehlVersuche == 9) {
				anzeigebild = linkesBein;
			} else if (fehlVersuche == 10) {
				labelText = "Du wurdest gehängt: Drücke ENTER um neu zu starten und ESC um zu verlassen";
				anzeigebild = verloren;
				gehängt = true;
			}

			repaint();

			if (gehängt == true || erraten == true) {
				remove(eingabe);
			}

			if (ratewort.equals(verschlüsselt)) {
				labelText = "Du hast das Wort erraten: Drücke ENTER um neu zu starten oder ESC um zu verlassen";
			}

			w = getWidth();
			h = getHeight();
			super.paintComponent(g);
			g.setColor(Color.black);
			Graphics g2d = (Graphics2D) g;

			// Um den String labelText zu zentrieren
			int stringLenght = (int) g2d.getFontMetrics().getStringBounds(labelText, g2d).getWidth();
			int width = w / 2 - stringLenght / 2;

			// Zeichnen des Strings und des Hangmans
			do {
				g2d.drawImage(anzeigebild, 0, 0, w, h, null);
				g.drawString(labelText, width, h / 30);
				label.setText(labelText);
				eingabe.setBounds(w / 20, h - 20, w - w / 10, 20);
				eingabe.setFont(font);
				eingabe.setHorizontalAlignment(SwingConstants.CENTER);
			} while (labelText.equals(ratewort));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Überprüfung für ENTER
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// Wenn man Level gespielt hat
			if (dataList.get(0).equals("Level")) {
				if (!(spieleGewonnen == (einfach + mittel + schwer))) {
					counter++;

					if (counter == 1) {
						add(anzeige2);
					} else if (counter == 2) {
						add(anzeige3);
					} else if (counter > 2) {
						Main.frame.setResizable(true);

						labelText = "Ratewort: " + verschlüsselt;

						// Bilder laden
						bilderLaden = true;
						bildCounter++;
						bilderLaden();

						if (counter == 4) {
							add(eingabe);
						}

						// Neustart
						if (gehängt == true || erraten == true) {
							gehängt = false;
							erraten = false;
							add(eingabe);
							labelText = "";
							benutzteBuchstaben = new ArrayList<String>();
							wortEingeben = true;
							verschlüsselt = "";
							fehlVersuche = 0;
							wortGenerieren = true;
							bildCounter = 1;
							bilderLaden();
							wortGenerierenLevel();
							verschlüsseln();
							labelText = "Ratewort: " + verschlüsselt;
							bildCounter = 0;
						}

						if (gehängt == false && erraten == false) {
							// Abfrage des Buchstabens in Kleinbuchstaben
							buchstabe = eingabe.getText();
							buchstabe = buchstabe.toLowerCase();
							buchstabe = buchstabe.replaceAll(" ", "");
							buchstabe = buchstabe.replaceAll("[^a-z]", "");

							// Zurücksetzen
							eingabe.setText("");

							if (buchstabe.length() == 1) {
								nichtErlaubterBuchstabe = false;
							} else {
								nichtErlaubterBuchstabe = true;
							}

							// Buchstabe in benutzteBuchstaben (ArrayList)?
							for (int j = 0; j < benutzteBuchstaben.size(); j++) {
								if (benutzteBuchstaben.get(j).equalsIgnoreCase(buchstabe)) {
									JOptionPane.showMessageDialog(null, "Den Buchstaben hast du schon benutzt");
									nichtErlaubterBuchstabe = true;
								}
							}

							if (nichtErlaubterBuchstabe == false) {
								// Buchstabenlänge = 1 und nicht in benutzteBuchstaben
								benutzteBuchstaben.add(buchstabe);
								if (erraten == false) {
									überprüfung();
								}
							}
						}
					}
				}
			} else if (dataList.get(0).equals("SpielerVsGruppe")) {
				// Wenn man Spieler gege Gruppe gespielt hat
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// Bilder Laden
					bilderLaden = true;
					bildCounter++;
					bilderLaden();

					// Neustart
					if (gehängt == true || erraten == true) {
						gehängt = false;
						erraten = false;
						add(eingabe);
						labelText = "";
						benutzteBuchstaben = new ArrayList<String>();
						wortEingeben = true;
						verschlüsselt = "";
						fehlVersuche = 0;
						wortGenerieren = true;
						bildCounter = 1;
						try {
							boden = ImageIO.read(new File("Bilder/boden.jpg"));
							anzeigebild = boden;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						bilderLaden();
						labelText = "Ratewort: " + verschlüsselt;
					}

					// Abfrage des Ratewortes
					if (wortEingeben == true && gehängt == false) {
						labelText = "Gib das zu erratende Wort ein";
						ratewortEingegeben = true;
						ratewort = eingabe.getText();
						if (ratewort.length() != 0) {
							Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
							Matcher m = p.matcher(ratewort);
							boolean b = m.find();
							if (b) {
								labelText = "Das Wort darf keine Sonderzeichen enthalten";
							} else {
								verschlüsseln();
								überprüfung = ratewort.toLowerCase();
								eingabe.setText("");
								wortEingeben = false;
							}
						}
					} else {
						// Abfrage des Buchstabens in Kleinbuchstaben
						buchstabe = eingabe.getText();
						buchstabe = buchstabe.toLowerCase();
						buchstabe = buchstabe.replaceAll(" ", "");
						buchstabe = buchstabe.replaceAll("[^a-z]", "");

						// Zurücksetzen der Eingabe
						eingabe.setText("");

						if (buchstabe.length() == 1) {
							nichtErlaubterBuchstabe = false;
						} else {
							nichtErlaubterBuchstabe = true;
						}

						// Buchstabe in benutzteBuchstaben (ArrayList)?
						for (int j = 0; j < benutzteBuchstaben.size(); j++) {
							if (benutzteBuchstaben.get(j).equalsIgnoreCase(buchstabe)) {
								JOptionPane.showMessageDialog(null, "Den Buchstaben hast du schon benutzt");
								nichtErlaubterBuchstabe = true;
							}
						}

						if (nichtErlaubterBuchstabe == false) {
							// Buchstabenlänge = 1 und nicht in benuitzteBuchstaben
							benutzteBuchstaben.add(buchstabe);
							if (erraten == false) {
								überprüfung();
							}
						}
					}
				}
			} else if (dataList.get(0).equals("SpielerVsSpieler")) {
				// Wenn man Spieler gegen Spieler gespielt hat
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// Bilder laden
					bildCounter++;
					bilderLaden();

					// Neustart
					if (gehängt == true || gehängtSpieler2 == true || gewonnen == true) {
						gehängt = false;
						gehängtSpieler2 = false;
						erraten = false;
						erratenSpieler2 = false;
						gewonnen = false;
						ratewort = "";
						ratewortSpieler2 = "";
						labelText = "";
						labelTextSpieler2 = "";
						neustart = "Gib das Ratewort für Spieler 1 ein";
						benutzteBuchstaben = new ArrayList<String>();
						benutzteBuchstabenSpieler2 = new ArrayList<String>();
						remove(eingabe);
						remove(eingabeSpieler2);
						add(wörterEingeben);
						wortEingeben = true;
						wortEingebenSpieler2 = true;
						verschlüsselt = "";
						verschlüsseltSpieler2 = "";
						fehlVersuche = 0;
						fehlVersucheSpieler2 = 0;
						bildCounter = 0;
						bildCounterSpieler2 = 0;
					}

					// Abfrage eines Rateworts für Spieler 1
					if (wortEingeben == true) {
						ratewort = wörterEingeben.getText();
						if (ratewort.length() != 0 && wortEingeben == true) {
							Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
							Matcher m = p.matcher(ratewort);
							boolean b = m.find();
							if (b == true) {
								labelText = "Das Wort darf keine Sonderzeichen enthalten";
							} else {
								verschlüsseln();
								überprüfung = ratewort.toLowerCase();
								wörterEingeben.setText("");
								neustart = "Gib das Ratewort für Spieler 2 ein";
								wortEingeben = false;
							}
						} else {
							ratewortEingegeben = true;
						}
					}

					// Abfrage eines Rateworts für Spieler 2
					if (wortEingebenSpieler2 == true) {
						minLenght = (ratewort.length() - 2);
						maxLenght = (ratewort.length() + 2);
						ratewortSpieler2 = wörterEingeben.getText();
						if (ratewortSpieler2.length() != 0 && wortEingebenSpieler2 == true) {
							Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
							Matcher m = p.matcher(ratewortSpieler2);
							boolean b = m.find();
							if (b == true) {
								labelTextSpieler2 = "Das Wort darf keine Sonderzeichen enthalten";
							} else if (b == false && ratewortSpieler2.length() >= minLenght
									&& ratewortSpieler2.length() <= maxLenght) {
								verschlüsselnSpieler2();
								überprüfungSpieler2 = ratewortSpieler2.toLowerCase();
								wörterEingeben.setText("");
								wortEingebenSpieler2 = false;
							} else if (b == false && ratewortSpieler2.length() < minLenght) {
								labelTextSpieler2 = "Das wort muss mindestens " + (ratewort.length() - 2)
										+ " Zeichen haben";
							} else if (b == false && ratewortSpieler2.length() > maxLenght) {
								labelTextSpieler2 = "Das Wort darf maximal " + (ratewort.length() + 2)
										+ " Zeichen haben";
							}
						} else {
							wortEingebenSpieler2 = true;
						}
					}

					if (wortEingeben == false && wortEingebenSpieler2 == false) {
						remove(wörterEingeben);
						neustart = "";
						add(eingabe);
						add(eingabeSpieler2);

						// Abfrage des Buchstabens für Spieler 1 in Kleinbuchstaben
						buchstabe = eingabe.getText();
						buchstabe = buchstabe.toLowerCase();
						buchstabe = buchstabe.replaceAll(" ", "");
						buchstabe = buchstabe.replaceAll("[^a-z]", "");

						if (buchstabe.length() == 1) {
							nichtErlaubterBuchstabe = false;
						} else {
							nichtErlaubterBuchstabe = true;
						}

						for (int j = 0; j < benutzteBuchstaben.size(); j++) {
							if (benutzteBuchstaben.get(j).equalsIgnoreCase(buchstabe)) {
								JOptionPane.showMessageDialog(null, "Den Buchstaben hast du schon benutzt");
								nichtErlaubterBuchstabe = true;
							}
						}

						// Abfrage des Buchstabens für Spieler 2 in Kleinbuchstaben
						buchstabeSpieler2 = eingabeSpieler2.getText();
						buchstabeSpieler2 = buchstabeSpieler2.toLowerCase();
						buchstabeSpieler2 = buchstabeSpieler2.replaceAll(" ", "");
						buchstabeSpieler2 = buchstabeSpieler2.replaceAll("[^a-z]", "");

						if (buchstabeSpieler2.length() == 1) {
							nichtErlaubterBuchstabeSpier2 = false;
						} else {
							nichtErlaubterBuchstabeSpier2 = true;
						}

						// Buchstabe in benutzteBuchstabenSpieler2 (ArrayList)?
						for (int j = 0; j < benutzteBuchstabenSpieler2.size(); j++) {
							if (benutzteBuchstabenSpieler2.get(j).equalsIgnoreCase(buchstabeSpieler2)) {
								JOptionPane.showMessageDialog(null, "Den Buchstaben hast du schon benutzt");
								nichtErlaubterBuchstabeSpier2 = true;
							}
						}

						if (nichtErlaubterBuchstabe == false && nichtErlaubterBuchstabeSpier2 == true) {
							labelText = "Warte auf Spieler 2";
							eingabe.setText(buchstabe);
						} else if (nichtErlaubterBuchstabe == true && nichtErlaubterBuchstabeSpier2 == false) {
							labelTextSpieler2 = "Warte auf Spieler 1";
							eingabeSpieler2.setText(buchstabeSpieler2);
						} else if (nichtErlaubterBuchstabe == false && nichtErlaubterBuchstabeSpier2 == false) {
							// Wenn beide Spieler einen erlaubten Buchstaben haben
							eingabe.setText("");
							eingabeSpieler2.setText("");
							benutzteBuchstaben.add(buchstabe);
							benutzteBuchstabenSpieler2.add(buchstabeSpieler2);
							// Überprüfung
							überprüfung();
							überprüfungSpieler2();
							// Welcher Text soll angezeit werden?
							labelTest();
						}
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			} else if (dataList.get(0).equals("Bot")) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					labelText = "Ratewort: " + verschlüsselt;

					// Bilder laden
					bilderLaden = true;
					bildCounter++;
					bilderLaden();

					if (eingabeEinfügen == true) {
						add(eingabe);
						eingabeEinfügen = false;
					}

					// Neustart
					if (gehängt == true || erraten == true) {
						gehängt = false;
						erraten = false;
						add(eingabe);
						labelText = "";
						benutzteBuchstaben = new ArrayList<String>();
						wortEingeben = true;
						verschlüsselt = "";
						fehlVersuche = 0;
						wortGenerieren = true;
						wortGenerieren();
						labelText = "Ratewort: " + verschlüsselt;
						bildCounter = 1;
						try {
							boden = ImageIO.read(new File("Bilder/boden.jpg"));
							anzeigebild = boden;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					if (gehängt == false && erraten == false) {
						// Abfrage des Buchstabens in Kleinbuchstaben
						buchstabe = eingabe.getText();
						buchstabe = buchstabe.toLowerCase();
						buchstabe = buchstabe.replaceAll(" ", "");
						buchstabe = buchstabe.replaceAll("[^a-z]", "");

						// Zurücksetzen der Eingabe
						eingabe.setText("");

						if (buchstabe.length() == 1) {
							nichtErlaubterBuchstabe = false;
						} else {
							nichtErlaubterBuchstabe = true;
						}

						// Buchstabe in beutzteBuchstaben (ArrayList)?
						for (int j = 0; j < benutzteBuchstaben.size(); j++) {
							if (benutzteBuchstaben.get(j).equalsIgnoreCase(buchstabe)) {
								JOptionPane.showMessageDialog(null, "Den Buchstaben hast du schon benutzt");
								nichtErlaubterBuchstabe = true;
							}
						}

						if (nichtErlaubterBuchstabe == false) {
							// Buchstabenlänge = 1 und nicht in benutzteBuchstaben
							benutzteBuchstaben.add(buchstabe);
							if (erraten == false) {
								überprüfung();
							}
						}
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			try {
				writer = new PrintWriter("Spielstand.txt");
				writer.print("leer");
				writer.flush();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(0);
		}
	}

	/**
	 * Erzeugen des rateworts in ?
	 */
	public void verschlüsseln() {
		for (int i = 0; i < ratewort.length(); i++) {
			if (ratewort.charAt(i) != ' ') {
				verschlüsselt = verschlüsselt + "?";
			} else {
				verschlüsselt = verschlüsselt + " ";
			}
		}
		labelText = "Ratewort: " + verschlüsselt;
	}

	/**
	 * Erzeugen des rateworts für Spieler 2 in ?
	 */
	public void verschlüsselnSpieler2() {
		for (int i = 0; i < ratewortSpieler2.length(); i++) {
			if (ratewortSpieler2.charAt(i) != ' ') {
				verschlüsseltSpieler2 = verschlüsseltSpieler2 + "?";
			} else {
				verschlüsseltSpieler2 = verschlüsseltSpieler2 + " ";
			}
		}
		labelTextSpieler2 = "Ratewort: " + verschlüsseltSpieler2;
	}

	/**
	 * Überprüft ob Buchstaben im Ratewort enthalten sind 
	 * Diese werden aufgedeckt
	 */
	public void überprüfung() {
		überprüfung = ratewort.toLowerCase();

		versuche++;
		// Richtiger Buchstabe enthalten?
		for (int i = 0; i < verschlüsselt.length(); i++) {
			if (überprüfung.charAt(i) == buchstabe.charAt(0)) {
				sb = new StringBuilder(verschlüsselt);
				sb.setCharAt(i, ratewort.charAt(i));
				verschlüsselt = sb.toString();
				nichtEnthalten = false;
			}
		}
		// Wenn nicht enthalten fehlversuche erhöhen
		if (nichtEnthalten == true) {
			if (fehlVersuche != 0) {
				falscherBuchstabe = falscherBuchstabe + ", " + buchstabe;
			} else {
				falscherBuchstabe = falscherBuchstabe + buchstabe;
			}
			fehlVersuche++;
		}

		nichtEnthalten = true;

		labelText = "Ratewort: " + verschlüsselt;

		// Überprüfung ob das Wort erraten wurde
		if (verschlüsselt.equals(ratewort)) {
			spieleGewonnen++;
			erraten = true;
		}
	}

	/**
	 * Überprüft ob Buchstaben im Ratewort entahlten sind 
	 * Diese werden aufgedeckt
	 */
	public void überprüfungSpieler2() {
		// Richtiger Buchstabe?
		überprüfungSpieler2 = ratewortSpieler2.toLowerCase();
		for (int i = 0; i < verschlüsseltSpieler2.length(); i++) {
			if (überprüfungSpieler2.charAt(i) == buchstabeSpieler2.charAt(0)) {
				sb = new StringBuilder(verschlüsseltSpieler2);
				sb.setCharAt(i, ratewortSpieler2.charAt(i));
				verschlüsseltSpieler2 = sb.toString();
				nichtEnthaltenSpieler2 = false;
			}
		}
		// Wenn nicht enthalten fehlversuche erhöhen
		if (nichtEnthaltenSpieler2 == true) {
			if (fehlVersucheSpieler2 != 1) {
				falscherBuchstabeSpieler2 = falscherBuchstabeSpieler2 + ", " + buchstabeSpieler2;
			} else {
				falscherBuchstabeSpieler2 = falscherBuchstabeSpieler2 + buchstabeSpieler2;
			}
			fehlVersucheSpieler2++;
		}
		nichtEnthaltenSpieler2 = true;

		labelTextSpieler2 = "Ratewort: " + verschlüsseltSpieler2;

		// Überprüfung ob das Wort erraten wurde
		if (verschlüsseltSpieler2.equalsIgnoreCase(ratewortSpieler2)) {
			erratenSpieler2 = true;
		}
	}

	/**
	 * Bilder werden nacheinander geladen um das Programm nicht zu überlasten
	 * bildCounter wird bei jeder betätigung der ENTER Taste um 1 erhöht
	 */
	static void bilderLaden() {
		try {
			if (bildCounter == 1) {
				boden = ImageIO.read(new File("Bilder/boden.jpg"));
			} else if (bildCounter == 2) {
				stab = ImageIO.read(new File("Bilder/stab.jpg"));
			} else if (bildCounter == 3) {
				galgen = ImageIO.read(new File("Bilder/galgen.jpg"));
			} else if (bildCounter == 4) {
				seil = ImageIO.read(new File("Bilder/seil.jpg"));
			} else if (bildCounter == 5) {
				kopf = ImageIO.read(new File("Bilder/kopf.jpg"));
			} else if (bildCounter == 6) {
				hals = ImageIO.read(new File("Bilder/hals.jpg"));
			} else if (bildCounter == 7) {
				linkerArm = ImageIO.read(new File("Bilder/linkerArm.jpg"));
			} else if (bildCounter == 8) {
				beideArme = ImageIO.read(new File("Bilder/beideArme.jpg"));
			} else if (bildCounter == 9) {
				bauch = ImageIO.read(new File("Bilder/bauch.jpg"));
			} else if (bildCounter == 10) {
				linkesBein = ImageIO.read(new File("Bilder/linkesBein.jpg"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Bilder werden nacheinander geladen um das Programm nicht zu überlasten
	 * bildCounter wird bei jeder betätigung der ENTER Taste um 1 erhöht
	 */
	static void bilderLadenSpieler2() {
		try {
			if (bildCounterSpieler2 == 1) {
				boden = ImageIO.read(new File("Bilder/boden.jpg"));
			} else if (bildCounterSpieler2 == 2) {
				stab = ImageIO.read(new File("Bilder/stab.jpg"));
			} else if (bildCounterSpieler2 == 3) {
				galgen = ImageIO.read(new File("Bilder/galgen.jpg"));
			} else if (bildCounterSpieler2 == 4) {
				seil = ImageIO.read(new File("Bilder/seil.jpg"));
			} else if (bildCounterSpieler2 == 5) {
				kopf = ImageIO.read(new File("Bilder/kopf.jpg"));
			} else if (bildCounterSpieler2 == 6) {
				hals = ImageIO.read(new File("Bilder/hals.jpg"));
			} else if (bildCounterSpieler2 == 7) {
				linkerArm = ImageIO.read(new File("Bilder/linkerArm.jpg"));
			} else if (bildCounterSpieler2 == 8) {
				beideArme = ImageIO.read(new File("Bilder/beideArme.jpg"));
			} else if (bildCounterSpieler2 == 9) {
				bauch = ImageIO.read(new File("Bilder/bauch.jpg"));
			} else if (bildCounterSpieler2 == 10) {
				linkesBein = ImageIO.read(new File("Bilder/linkesBein.jpg"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * Legt den Text für beide Spieler fest 
	  */
	public void labelTest() {
		if (erratenSpieler2 == true && erraten == false) {
			neustart = "Drücke ENTER um neu zu starten und ESC um zu verlassen";
			labelTextSpieler2 = "Du hast gewonnen";
			labelText = "Du hast verloren";
			gewonnen = true;
		} else if (erratenSpieler2 == false && erraten == true) {
			neustart = "Drücke ENTER um neu zu starten und ESC um zu verlassen";
			labelText = "Du hast gewonnen";
			labelTextSpieler2 = "Du hast verloren";
			gewonnen = true;
		} else if (erratenSpieler2 == true && erraten == true) {
			neustart = "Drücke ENTER um neu zu starten und ESC um zu verlassen";
			labelTextSpieler2 = "Unentschieden";
			labelText = "Unentschieden";
			gewonnen = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateChanged(ChangeEvent e) {
	}
	
	/**
	 * Generieren des Ratewortes und anschließende Verschlüsselung
	 */
	public void wortGenerieren() {
		list = new ArrayList<String>();
		if (botWahl == 1) {
			fileName = "HangmanLeich.txt";
		} else if (botWahl == 2) {
			fileName = "HangmanMittel.txt";
		} else if (botWahl == 3) {
			fileName = "HangmanSchwer.txt";
		}

		try {
			file = new File(fileName);
			inputStream = new Scanner(file);

			while (inputStream.hasNext()) {
				String data = inputStream.next();
				values = data.split("\n");

				list.add(values[0]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Datei nicht gefunden");
		}
		random = (int) (Math.random() * list.size());
		ratewort = list.get(random);
		for (int i = 0; i < ratewort.length(); i++) {
			if (ratewort.charAt(i) != ' ') {
				verschlüsselt = verschlüsselt + "?";
			} else {
				verschlüsselt = verschlüsselt + " ";
			}
		}
		überprüfung = ratewort.toLowerCase();
	}
	
	/**
	 * Generieren des Wortes für die Level und anschließende Verschlüsselung
	 */
	public void wortGenerierenLevel() {
		if (spieleGewonnen == einfach) {
			fileName = "HangmanMittel.txt";
		} else if (spieleGewonnen >= (einfach + mittel)) {
			fileName = "HangmanSchwer.txt";
		} else {
			fileName = "HangmanLeich.txt";
		}

		try {
			file = new File(fileName);
			inputStream = new Scanner(file);

			while (inputStream.hasNext()) {
				String data = inputStream.next();
				values = data.split("\n");
				if (spieleGewonnen == einfach) {
					mittlereWörter.add(values[0]);
				} else if (spieleGewonnen >= (einfach + mittel)) {
					schwereWörter.add(values[0]);
				} else {
					einfacheWörter.add(values[0]);
				}
			}
		} catch (FileNotFoundException a) {
			a.printStackTrace();
			System.out.println("Datei nicht gefunden");
		}

		if (spieleGewonnen < einfach) {
			random = (int) (Math.random() * einfacheWörter.size());
			ratewort = einfacheWörter.get(random);
		} else if (spieleGewonnen >= einfach && spieleGewonnen < (einfach + mittel)) {
			random = (int) (Math.random() * mittlereWörter.size());
			ratewort = mittlereWörter.get(random);
		} else if (spieleGewonnen >= (einfach + mittel)) {
			random = (int) (Math.random() * schwereWörter.size());
		}

		überprüfung = ratewort.toLowerCase();
	}
	
	/**
	 * Weist den Nutzer darauf hin, dass der Spielstand beim Verlassen gelöscht wird 
	 * Nutzer wird zur bestätigung aufgefordert
	 */
	public static void exit() {
		int result = JOptionPane.showConfirmDialog(null,
				"Spielstand wird beim Verlassen Gelöscht, JA drücken um zu bestätigen", "Programm beenden bestätigen",
				JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			try {
				writer = new PrintWriter("Spielstand.txt");
				writer.print("leer");
				writer.flush();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			System.exit(0);
		}
	}
}
