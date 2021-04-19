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
	static int bildCounter = 0;
	static int maxLenght, minLenght;
	static String labelText = "", labelTextSpieler2 = "", neustart = "Gib das Wort für Spieler 1 ein";
	static ArrayList<String> benutzteBuchstaben = new ArrayList<String>();
	static ArrayList<String> benutzteBuchstabenSpieler2 = new ArrayList<String>();

	static StringBuilder sb;

	static BufferedImage verloren, boden, stab, galgen, seil, kopf, hals, linkerArm, beideArme, bauch, linkesBein,
			anzeigebild, anzeigebildSpieler2;

	static PrintWriter writer;

	static ArrayList<String> alleWörter = new ArrayList<String>();
	static File file;
	static String fileName = "";
	static String[] values = null;
	static ArrayList<String> einfacheWörter = new ArrayList<String>(), mittlereWörter = new ArrayList<String>(),
			schwereWörter = new ArrayList<String>();
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
		wörterEingeben.addKeyListener(this);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		add(wörterEingeben);

		// Lädt das erste Bild
		try {
			verloren = ImageIO.read(new File("Bilder/verloren.jpg"));
			anzeigebild = verloren;
			anzeigebildSpieler2 = verloren;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Bilder nicht gefunden");
		}

		// Greift auf Datei AlleWörter.txt zu
		String fileName = "AlleWörter.txt";
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
					alleWörter.add(values[0]);
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
		// Abfrage für ENTER
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
			}
			// Abfrage des Rateworts für Spieler 1
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

			// Abfrage des Rateworts für Spieler 2
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
						labelTextSpieler2 = "Das wort muss mindestens " + (ratewort.length() - 2) + " Zeichen haben";
					} else if (b == false && ratewortSpieler2.length() > maxLenght) {
						labelTextSpieler2 = "Das Wort darf maximal " + (ratewort.length() + 2) + " Zeichen haben";
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

				// Buchstabe in benutzteBuchstaben (ArrayList)?
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
					// Wenn beide einen Erlaubten buchstaben haben
					eingabe.setText("");
					eingabeSpieler2.setText("");
					benutzteBuchstaben.add(buchstabe);
					benutzteBuchstabenSpieler2.add(buchstabeSpieler2);
					// Überprüfung
					überprüfung();
					überprüfungSpieler2();
					// Welcher Text soll angezeigt werden?
					labelTest();
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (erraten == true || erratenSpieler2 == true || gehängt == true || gehängtSpieler2 == true) {
				System.exit(0);
			}
		}
		// Abfrage für STRG+S um zu speichern
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
			writer.println(verschlüsselt);
			writer.println(verschlüsseltSpieler2);
			writer.println(buchstabe);
			writer.println(buchstabeSpieler2);
			writer.println(überprüfung);
			writer.println(überprüfungSpieler2);
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
			writer.println(gehängt);
			writer.println(gehängtSpieler2);
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
	 * Erzeugen eines ratewortes in ? für Spieler 1
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

	/*
	 * Erzeugen eines ratewortes in ? für Spieler 2
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
	 * Überprüft ob Buchstaben im ratewort enthalten sind
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
		// Wenn nicht fehlversuche erhöhen
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

		// Überprüfung ob Wort erraten wurde
		if (verschlüsselt.equals(ratewort)) {
			erraten = true;
			// Fügt das ratewort zu einer Datei hinzu
			// Deaktiviert wegen Ladedauer
//			hinzufügen();
		}
	}

	/**
	 * Überprüft ob Buchstaben im ratewort enthalten sind
	 * Diese werden aufgedeckt
	 */
	public void überprüfungSpieler2() {
		überprüfungSpieler2 = ratewortSpieler2.toLowerCase();

		// Richtiger Buchstabe enthalten?
		for (int i = 0; i < verschlüsseltSpieler2.length(); i++) {
			if (überprüfungSpieler2.charAt(i) == buchstabeSpieler2.charAt(0)) {
				sb = new StringBuilder(verschlüsseltSpieler2);
				sb.setCharAt(i, ratewortSpieler2.charAt(i));
				verschlüsseltSpieler2 = sb.toString();
				nichtEnthaltenSpieler2 = false;
			}
		}
		// Wenn nicht enthalten fehlversucheSpieler2 erhöhen
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

		// Überfrüfung ob Wort erraten wurde
		if (verschlüsseltSpieler2.equalsIgnoreCase(ratewortSpieler2)) {
			erratenSpieler2 = true;
			// Fügt das ratewort zu einer Datei hinzu
			// Deaktiviert wegen Ladedauer
//			hinzufügen();
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

	/**
	 * Ratewörter zu der Richtigen Liste hinzufügen
	 */
	public void hinzufügen() {
		// Schleife wird 3 mal duchlaufen
		// Bei jedem Durchlauf wird eine Andere Datei aufgerufen
		// Die Wörter werden der entsprechenden ArrayList hinzugefügt
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
						einfacheWörter.add(values[0]);
					} else if (i == 1) {
						mittlereWörter.add(values[0]);
					} else if (i == 2) {
						schwereWörter.add(values[0]);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Datei nicht gefunden");
			}
		}
		// Schreibt den ersten Buchstaben des rateworts groß
		ratewort = ratewort.substring(0, 1).toUpperCase() + ratewort.substring(1);

		// Wenn das wort nicht in der liste ist wird es der entsprechenden ArrayList
		// hinzugefügt
		if (!alleWörter.contains(ratewort)) {
			if (ratewort.length() <= 5) {
				einfacheWörter.add(ratewort);
			} else if (ratewort.length() > 5 && ratewort.length() <= 7) {
				mittlereWörter.add(ratewort);
			} else if (ratewort.length() > 7) {
				schwereWörter.add(ratewort);
			}
			alleWörter.add(ratewort);
		}

		// Schreibt den ersten Buchstaben des rateworts groß
		ratewortSpieler2 = ratewortSpieler2.substring(0, 1).toUpperCase() + ratewortSpieler2.substring(1);

		// Wenn das wort nicht in der liste ist wird es der entsprechenden ArrayList
		// hinzugefügt
		if (!alleWörter.contains(ratewortSpieler2)) {
			if (ratewortSpieler2.length() <= 5) {
				einfacheWörter.add(ratewortSpieler2);
			} else if (ratewortSpieler2.length() > 5 && ratewortSpieler2.length() <= 7) {
				mittlereWörter.add(ratewortSpieler2);
			} else if (ratewortSpieler2.length() > 7) {
				schwereWörter.add(ratewortSpieler2);
			}
			alleWörter.add(ratewortSpieler2);
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
				datei = "AlleWörter.txt";
			}

			try {
				writer = new PrintWriter(datei);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i == 0) {
				// Beschreibt die Datei HangmanLeich.txt
				for (int j = 0; j < einfacheWörter.size(); j++) {
					writer.println(einfacheWörter.get(j));
					writer.flush();
				}
			} else if (i == 1) {
				// Beschreibt die Datei HangmanMittel.txt
				for (int j = 0; j < mittlereWörter.size(); j++) {
					writer.println(mittlereWörter.get(j));
					writer.flush();
				}
			} else if (i == 2) {
				// Beschreibt die Datei HangmanSchwer.txt
				for (int j = 0; j < schwereWörter.size(); j++) {
					writer.println(schwereWörter.get(j));
					writer.flush();
				}
			} else if (i == 3) {
				// Beschreibt die Date AlleWörter.txt
				for (int j = 0; j < alleWörter.size(); j++) {
					writer.println(alleWörter.get(j));
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
