package Hangman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PlayerVsPlayer extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	static JTextField eingabe = new JTextField();
	static JTextField eingabeSpieler2 = new JTextField();
	static JTextField w�rterEingeben = new JTextField();
	static JLabel label = new JLabel();
	static String ratewort = "", ratewortSpieler2 = "";
	static String verschl�sselt = "", verschl�sseltSpieler2 = "";
	static String buchstabe = "", buchstabeSpieler2 = "";
	static String �berpr�fung, �berpr�fungSpieler2;
	static Font font = new Font("Arial", Font.BOLD, 20);
	static boolean enter = false;
	static boolean wortEingeben = true, wortEingebenSpieler2 = true;
	static boolean nichtErlaubterBuchstabe = false, nichtErlaubterBuchstabeSpier2 = false;
	static String falscherBuchstabe = "", falscherBuchstabeSpieler2 = "";
	static boolean nichtEnthalten = true, nichtEnthaltenSpieler2 = true;
	static boolean erraten = false, erratenSpieler2 = false;
	static boolean ratewortEingegeben = false, ratewortEingebenSpieler2 = false;
	static boolean geh�ngt = false, geh�ngtSpieler2 = false;
	static boolean gewonnen = false;
	int w = getWidth();
	int h = getHeight();
	static int fehlVersuche = 0, fehlVersucheSpieler2 = 0;
	static int versuche = 0;
	static int bildCounter = 0;
	static int maxLenght, minLenght;
	static String labelText = "", labelTextSpieler2 = "", neustart = "Gib das Wort f�r Spieler 1 ein";
	static ArrayList<String> benutzteBuchstaben = new ArrayList<String>();
	static ArrayList<String> benutzteBuchstabenSpieler2 = new ArrayList<String>();

	static StringBuilder sb;

	static BufferedImage verloren, boden, stab, galgen, seil, kopf, hals, linkerArm, beideArme, bauch, linkesBein,
			anzeigebild, anzeigebildSpieler2;

	static PrintWriter writer;

	static ArrayList<String> alleW�rter = new ArrayList<String>();
	static File file;
	static String fileName = "";
	static String[] values = null;
	static ArrayList<String> einfacheW�rter = new ArrayList<String>(), mittlereW�rter = new ArrayList<String>(),
			schwereW�rter = new ArrayList<String>();
	static Scanner inputStream;
	static int random = 0;

	/**
	 * Start der PlayerVsPlayer Class
	 */
	@SuppressWarnings("unused")
	public PlayerVsPlayer() {
		// Frame einstellungen
		Main.frame.setSize(652 * 2, 800);
		eingabe.addKeyListener(this);
		eingabeSpieler2.addKeyListener(this);
		w�rterEingeben.addKeyListener(this);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		add(w�rterEingeben);

		// L�dt das erste Bild
		try {
			verloren = ImageIO.read(new File("Bilder/verloren.jpg"));
			anzeigebild = verloren;
			anzeigebildSpieler2 = verloren;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Bilder nicht gefunden");
		}

		// Greift auf Datei AlleW�rter.txt zu
		String fileName = "AlleW�rter.txt";
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
					// Beschreiben der ArrayList
					alleW�rter.add(values[0]);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param g
	 * @param g2d Zeichnen der Bilder und Texte
	 */
	public void paintComponent(Graphics g) {
		// Welcher Teil vom Hangman soll f�r Spieler 1 gezeichnet werden
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
			neustart = "Dr�cke ENTER um neu zu starten und ESC um zu verlassen";
			geh�ngt = true;
		}
		// Welcher Teil vom Hangman soll f�r Spieler 2 gezeichnet werden
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
			neustart = "Dr�cke ENTER um neu zu starten und ESC um zu verlassen";
			geh�ngtSpieler2 = true;
		}

		if (fehlVersuche == 10 && fehlVersucheSpieler2 == 10) {
			labelText = "Unentschieden";
			labelTextSpieler2 = "Unentschiden";
			neustart = "Dr�cke ENTER um neu zu starten und ESC um zu verlassen";
		}

		if (geh�ngt == true || geh�ngtSpieler2 == true || gewonnen == true) {
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
		w�rterEingeben.setBounds(w / 20, h - 20, w - w / 10, 20);
		eingabe.setFont(font);
		eingabeSpieler2.setFont(font);
		w�rterEingeben.setFont(font);
		eingabe.setHorizontalAlignment(SwingConstants.CENTER);
		eingabeSpieler2.setHorizontalAlignment(SwingConstants.CENTER);
		w�rterEingeben.setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param e KeyListener
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Abfrage f�r ENTER
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// Bilder laden
			bildCounter++;
			bilderLaden();

			// Neustart
			if (geh�ngt == true || geh�ngtSpieler2 == true || gewonnen == true) {
				geh�ngt = false;
				geh�ngtSpieler2 = false;
				erraten = false;
				erratenSpieler2 = false;
				gewonnen = false;
				ratewort = "";
				ratewortSpieler2 = "";
				labelText = "";
				labelTextSpieler2 = "";
				neustart = "Gib das Ratewort f�r Spieler 1 ein";
				benutzteBuchstaben = new ArrayList<String>();
				benutzteBuchstabenSpieler2 = new ArrayList<String>();
				remove(eingabe);
				remove(eingabeSpieler2);
				add(w�rterEingeben);
				wortEingeben = true;
				wortEingebenSpieler2 = true;
				verschl�sselt = "";
				verschl�sseltSpieler2 = "";
				fehlVersuche = 0;
				fehlVersucheSpieler2 = 0;
			}
			// Abfrage des Rateworts f�r Spieler 1
			if (wortEingeben == true) {
				ratewort = w�rterEingeben.getText();
				if (ratewort.length() != 0 && wortEingeben == true) {
					Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
					Matcher m = p.matcher(ratewort);
					boolean b = m.find();
					if (b == true) {
						labelText = "Das Wort darf keine Sonderzeichen enthalten";
					} else {
						verschl�sseln();
						�berpr�fung = ratewort.toLowerCase();
						w�rterEingeben.setText("");
						neustart = "Gib das Ratewort f�r Spieler 2 ein";
						wortEingeben = false;
					}
				} else {
					ratewortEingegeben = true;
				}
			}

			// Abfrage des Rateworts f�r Spieler 2
			if (wortEingebenSpieler2 == true) {
				minLenght = (ratewort.length() - 2);
				maxLenght = (ratewort.length() + 2);
				ratewortSpieler2 = w�rterEingeben.getText();
				if (ratewortSpieler2.length() != 0 && wortEingebenSpieler2 == true) {
					Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
					Matcher m = p.matcher(ratewortSpieler2);
					boolean b = m.find();
					if (b == true) {
						labelTextSpieler2 = "Das Wort darf keine Sonderzeichen enthalten";
					} else if (b == false && ratewortSpieler2.length() >= minLenght
							&& ratewortSpieler2.length() <= maxLenght) {
						verschl�sselnSpieler2();
						�berpr�fungSpieler2 = ratewortSpieler2.toLowerCase();
						w�rterEingeben.setText("");
						wortEingebenSpieler2 = false;
					} else if (b == false && ratewortSpieler2.length() < minLenght) {
						labelTextSpieler2 = "Das wort muss mindestens " + (ratewort.length() - 2) + " Zeichen haben";
					} else if (b == false && ratewortSpieler2.length() > maxLenght) {
						labelTextSpieler2 = "Das Wort darf maximal " + (ratewort.length() + 2) + " Zeichen haben";
					}
				} else {
					wortEingebenSpieler2 = true;
				}
			}

			if (wortEingeben == false && wortEingebenSpieler2 == false) {
				remove(w�rterEingeben);
				neustart = "";
				add(eingabe);
				add(eingabeSpieler2);

				// Abfrage des Buchstabens f�r Spieler 1 in Kleinbuchstaben
				buchstabe = eingabe.getText();
				buchstabe = buchstabe.toLowerCase();
				buchstabe = buchstabe.replaceAll(" ", "");
				buchstabe = buchstabe.replaceAll("[^a-z]", "");

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

				// Abfrage des Buchstabens f�r Spieler 2 in Kleinbuchstaben
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
					// Wenn beide einen Erlaubten buchstaben haben
					eingabe.setText("");
					eingabeSpieler2.setText("");
					benutzteBuchstaben.add(buchstabe);
					benutzteBuchstabenSpieler2.add(buchstabeSpieler2);
					// �berpr�fung
					�berpr�fung();
					�berpr�fungSpieler2();
					// Welcher Text soll angezeigt werden?
					labelTest();
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (erraten == true || erratenSpieler2 == true || geh�ngt == true || geh�ngtSpieler2 == true) {
				System.exit(0);
			}
		}
		// Abfrage f�r STRG+S um zu speichern
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
			// Writer greift auf Datei Spielstand.txt zu
			try {
				writer = new PrintWriter(new BufferedWriter(new FileWriter("Spielstand.txt")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Beschreibt die Datei mit allen wichtigen Variablen
			writer.println("SpielerVsSpieler");
			writer.println(ratewort);
			writer.println(ratewortSpieler2);
			writer.println(verschl�sselt);
			writer.println(verschl�sseltSpieler2);
			writer.println(buchstabe);
			writer.println(buchstabeSpieler2);
			writer.println(�berpr�fung);
			writer.println(�berpr�fungSpieler2);
			writer.println(enter);
			writer.println(wortEingeben);
			writer.println(wortEingebenSpieler2);
			writer.println(nichtErlaubterBuchstabe);
			writer.println(nichtErlaubterBuchstabeSpier2);
			if (falscherBuchstabe.length() > 0) {
				writer.println(falscherBuchstabe);
			} else {
				falscherBuchstabe = "leer";
				writer.println(falscherBuchstabe);
			}
			if (falscherBuchstabeSpieler2.length() > 0) {
				writer.println(falscherBuchstabeSpieler2);
			} else {
				falscherBuchstabeSpieler2 = "leer";
				writer.println(falscherBuchstabeSpieler2);
			}
			writer.println(nichtEnthalten);
			writer.println(nichtEnthaltenSpieler2);
			writer.println(erraten);
			writer.println(erratenSpieler2);
			writer.println(ratewortEingegeben);
			writer.println(ratewortEingebenSpieler2);
			writer.println(geh�ngt);
			writer.println(geh�ngtSpieler2);
			writer.println(gewonnen);
			writer.println(fehlVersuche);
			writer.println(fehlVersucheSpieler2);
			writer.println(versuche);
			writer.println(maxLenght);
			writer.println(benutzteBuchstaben);
			writer.println(benutzteBuchstabenSpieler2);
			writer.println(bildCounter);
			writer.flush();

			System.exit(0);
		}
	}

	/*
	 * Erzeugen eines ratewortes in ? f�r Spieler 1
	 */
	public void verschl�sseln() {
		for (int i = 0; i < ratewort.length(); i++) {
			if (ratewort.charAt(i) != ' ') {
				verschl�sselt = verschl�sselt + "?";
			} else {
				verschl�sselt = verschl�sselt + " ";
			}
		}
		labelText = "Ratewort: " + verschl�sselt;
	}

	/*
	 * Erzeugen eines ratewortes in ? f�r Spieler 2
	 */
	public void verschl�sselnSpieler2() {
		for (int i = 0; i < ratewortSpieler2.length(); i++) {
			if (ratewortSpieler2.charAt(i) != ' ') {
				verschl�sseltSpieler2 = verschl�sseltSpieler2 + "?";
			} else {
				verschl�sseltSpieler2 = verschl�sseltSpieler2 + " ";
			}
		}
		labelTextSpieler2 = "Ratewort: " + verschl�sseltSpieler2;
	}

	/**
	 * �berpr�ft ob Buchstaben im ratewort enthalten sind
	 * Diese werden aufgedeckt
	 */
	public void �berpr�fung() {
		�berpr�fung = ratewort.toLowerCase();
		versuche++;

		// Richtiger Buchstabe enthalten?
		for (int i = 0; i < verschl�sselt.length(); i++) {
			if (�berpr�fung.charAt(i) == buchstabe.charAt(0)) {
				sb = new StringBuilder(verschl�sselt);
				sb.setCharAt(i, ratewort.charAt(i));
				verschl�sselt = sb.toString();
				nichtEnthalten = false;
			}
		}
		// Wenn nicht fehlversuche erh�hen
		if (nichtEnthalten == true) {
			if (fehlVersuche != 0) {
				falscherBuchstabe = falscherBuchstabe + ", " + buchstabe;
			} else {
				falscherBuchstabe = falscherBuchstabe + buchstabe;
			}
			fehlVersuche++;
		}

		nichtEnthalten = true;

		labelText = "Ratewort: " + verschl�sselt;

		// �berpr�fung ob Wort erraten wurde
		if (verschl�sselt.equals(ratewort)) {
			erraten = true;
			// F�gt das ratewort zu einer Datei hinzu
			// Deaktiviert wegen Ladedauer
//			hinzuf�gen();
		}
	}

	/**
	 * �berpr�ft ob Buchstaben im ratewort enthalten sind
	 * Diese werden aufgedeckt
	 */
	public void �berpr�fungSpieler2() {
		�berpr�fungSpieler2 = ratewortSpieler2.toLowerCase();

		// Richtiger Buchstabe enthalten?
		for (int i = 0; i < verschl�sseltSpieler2.length(); i++) {
			if (�berpr�fungSpieler2.charAt(i) == buchstabeSpieler2.charAt(0)) {
				sb = new StringBuilder(verschl�sseltSpieler2);
				sb.setCharAt(i, ratewortSpieler2.charAt(i));
				verschl�sseltSpieler2 = sb.toString();
				nichtEnthaltenSpieler2 = false;
			}
		}
		// Wenn nicht enthalten fehlversucheSpieler2 erh�hen
		if (nichtEnthaltenSpieler2 == true) {
			if (fehlVersucheSpieler2 != 1) {
				falscherBuchstabeSpieler2 = falscherBuchstabeSpieler2 + ", " + buchstabeSpieler2;
			} else {
				falscherBuchstabeSpieler2 = falscherBuchstabeSpieler2 + buchstabeSpieler2;
			}
			fehlVersucheSpieler2++;
		}

		nichtEnthaltenSpieler2 = true;

		labelTextSpieler2 = "Ratewort: " + verschl�sseltSpieler2;

		// �berfr�fung ob Wort erraten wurde
		if (verschl�sseltSpieler2.equalsIgnoreCase(ratewortSpieler2)) {
			erratenSpieler2 = true;
			// F�gt das ratewort zu einer Datei hinzu
			// Deaktiviert wegen Ladedauer
//			hinzuf�gen();
		}
	}

	/**
	 * Bilder werden nacheinander geladen um das Programm nicht zu �berlasten
	 * bildCounter wird bei jeder bet�tigung der ENTER Taste um 1 erh�ht
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
	 * Legt den Text f�r beide Spieler fest
	 */
	public void labelTest() {
		if (erratenSpieler2 == true && erraten == false) {
			neustart = "Dr�cke ENTER um neu zu starten und ESC um zu verlassen";
			labelTextSpieler2 = "Du hast gewonnen";
			labelText = "Du hast verloren";
			gewonnen = true;
		} else if (erratenSpieler2 == false && erraten == true) {
			neustart = "Dr�cke ENTER um neu zu starten und ESC um zu verlassen";
			labelText = "Du hast gewonnen";
			labelTextSpieler2 = "Du hast verloren";
			gewonnen = true;
		} else if (erratenSpieler2 == true && erraten == true) {
			neustart = "Dr�cke ENTER um neu zu starten und ESC um zu verlassen";
			labelTextSpieler2 = "Unentschieden";
			labelText = "Unentschieden";
			gewonnen = true;
		}
	}

	/**
	 * Ratew�rter zu der Richtigen Liste hinzuf�gen
	 */
	public void hinzuf�gen() {
		// Schleife wird 3 mal duchlaufen
		// Bei jedem Durchlauf wird eine Andere Datei aufgerufen
		// Die W�rter werden der entsprechenden ArrayList hinzugef�gt
		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				fileName = "HangmanLeich.txt";
			} else if (i == 1) {
				fileName = "HangmanMittel.txt";
			} else if (i == 2) {
				fileName = "HangmanSchwer.txt";
			}

			try {
				file = new File(fileName);
				inputStream = new Scanner(file);

				while (inputStream.hasNext()) {
					String data = inputStream.next();
					values = data.split("\n");

					if (i == 0) {
						einfacheW�rter.add(values[0]);
					} else if (i == 1) {
						mittlereW�rter.add(values[0]);
					} else if (i == 2) {
						schwereW�rter.add(values[0]);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Datei nicht gefunden");
			}
		}
		// Schreibt den ersten Buchstaben des rateworts gro�
		ratewort = ratewort.substring(0, 1).toUpperCase() + ratewort.substring(1);

		// Wenn das wort nicht in der liste ist wird es der entsprechenden ArrayList
		// hinzugef�gt
		if (!alleW�rter.contains(ratewort)) {
			if (ratewort.length() <= 5) {
				einfacheW�rter.add(ratewort);
			} else if (ratewort.length() > 5 && ratewort.length() <= 7) {
				mittlereW�rter.add(ratewort);
			} else if (ratewort.length() > 7) {
				schwereW�rter.add(ratewort);
			}
			alleW�rter.add(ratewort);
		}

		// Schreibt den ersten Buchstaben des rateworts gro�
		ratewortSpieler2 = ratewortSpieler2.substring(0, 1).toUpperCase() + ratewortSpieler2.substring(1);

		// Wenn das wort nicht in der liste ist wird es der entsprechenden ArrayList
		// hinzugef�gt
		if (!alleW�rter.contains(ratewortSpieler2)) {
			if (ratewortSpieler2.length() <= 5) {
				einfacheW�rter.add(ratewortSpieler2);
			} else if (ratewortSpieler2.length() > 5 && ratewortSpieler2.length() <= 7) {
				mittlereW�rter.add(ratewortSpieler2);
			} else if (ratewortSpieler2.length() > 7) {
				schwereW�rter.add(ratewortSpieler2);
			}
			alleW�rter.add(ratewortSpieler2);
		}

		// Die Schleife wird 4 mal durchlaufen
		// Bei jedem Durchlauf wird eine andere Datei beschrieben
		for (int i = 0; i < 4; i++) {
			String datei = "";
			if (i == 0) {
				datei = "HangmanLeich.txt";
			} else if (i == 1) {
				datei = "HangmanMittel.txt";
			} else if (i == 2) {
				datei = "HangmanSchwer.txt";
			} else if (i == 3) {
				datei = "AlleW�rter.txt";
			}

			try {
				writer = new PrintWriter(datei);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i == 0) {
				// Beschreibt die Datei HangmanLeich.txt
				for (int j = 0; j < einfacheW�rter.size(); j++) {
					writer.println(einfacheW�rter.get(j));
					writer.flush();
				}
			} else if (i == 1) {
				// Beschreibt die Datei HangmanMittel.txt
				for (int j = 0; j < mittlereW�rter.size(); j++) {
					writer.println(mittlereW�rter.get(j));
					writer.flush();
				}
			} else if (i == 2) {
				// Beschreibt die Datei HangmanSchwer.txt
				for (int j = 0; j < schwereW�rter.size(); j++) {
					writer.println(schwereW�rter.get(j));
					writer.flush();
				}
			} else if (i == 3) {
				// Beschreibt die Date AlleW�rter.txt
				for (int j = 0; j < alleW�rter.size(); j++) {
					writer.println(alleW�rter.get(j));
					writer.flush();
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
