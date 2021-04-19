package Hangman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Bot extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	static JTextField eingabe = new JTextField();
	static JLabel label = new JLabel();
	static String ratewort = "";
	static String verschl�sselt = "";
	static String buchstabe = "";
	static String �berpr�fung;
	static Font font = new Font("Arial", Font.BOLD, 20);
	static boolean enter = false;
	static boolean wortEingeben = true;
	static boolean nichtErlaubterBuchstabe = false;
	static String falscherBuchstabe = "";
	static boolean nichtEnthalten = true;
	static boolean erraten = false;
	static boolean ratewortEingegeben = false;
	static boolean geh�ngt = false;
	int w = getWidth();
	int h = getHeight();
	static int fehlVersuche = 0;
	static int versuche = 0;
	static int maxLenght;
	static String labelText = "Dr�cke enter um ein zuf�lliges Wort zu generieren";
	static ArrayList<String> benutzteBuchstaben = new ArrayList<String>();
	static int bildCounter = 0;
	static boolean bilderLaden = false;
	static boolean wortGenerieren = true;
	static boolean eingabeEinf�gen = true;

	static File file;
	static String fileName = "";
	static String[] values = null;
	static ArrayList<String> list = new ArrayList<String>();
	static Scanner inputStream;
	static int random = 0;

	static BufferedImage verloren, boden, stab, galgen, seil, kopf, hals, linkerArm, beideArme, bauch, linkesBein,
			anzeigebild;

	static PrintWriter writer;

	/**
	 * Start der Bot Class
	 */
	public Bot() {
		// F�gt einen KeyListener zu Frame und eingabe hinzu
		eingabe.addKeyListener(this);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();

		// L�dt das erste Bild
		try {
			verloren = ImageIO.read(new File("Bilder/verloren.jpg"));
			anzeigebild = verloren;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Bilder nicht gefunden");
		}

		// Je nach Entscheidung wird der Dateiname festgelegt und ausgelesen
		if (BotCtrl.wahl == 1) {
			fileName = "HangmanLeich.txt";
		} else if (BotCtrl.wahl == 2) {
			fileName = "HangmanMittel.txt";
		} else if (BotCtrl.wahl == 3) {
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

		wortGenerieren();
	}

	/**
	 * @param g
	 * @param g2d Zeichnen der Bilder und Texte
	 */
	public void paintComponent(Graphics g) {
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
			labelText = "Du wurdest geh�ngt: Dr�cke ENTER um neu zu starten und ESC um zu verlassen";
			anzeigebild = verloren;
			geh�ngt = true;
		}

		repaint();

		// Wenn gewonnen oder verloren
		if (geh�ngt == true || erraten == true) {
			remove(eingabe);
		}

		w = getWidth();
		h = getHeight();
		super.paintComponent(g);
		g.setColor(Color.black);
		Graphics g2d = (Graphics2D) g;

		// Um den String labelText zu zentrieren
		int stringLenght = (int) g2d.getFontMetrics().getStringBounds(labelText, g2d).getWidth();
		int width = w / 2 - stringLenght / 2;

		do {
			// Zeichnen des Strings und des Hangmans
			g2d.drawImage(anzeigebild, 0, 0, w, h, null);
			g.drawString(labelText, width, h / 30);
			label.setText(labelText);
			eingabe.setBounds(w / 20, h - 20, w - w / 10, 20);
			eingabe.setFont(font);
			eingabe.setHorizontalAlignment(SwingConstants.CENTER);
		} while (labelText.equals(ratewort));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param e KeyListender
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Abfrage f�r ENTER
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// Anzeigetext
			labelText = "Ratewort: " + verschl�sselt;

			// Bilder werden nacheinander geladen
			bilderLaden = true;
			bildCounter++;
			bilderLaden();

			if (eingabeEinf�gen == true) {
				add(eingabe);
				eingabeEinf�gen = false;
			}

			// Neustart
			if (geh�ngt == true || erraten == true) {
				geh�ngt = false;
				erraten = false;
				add(eingabe);
				labelText = "";
				benutzteBuchstaben = new ArrayList<String>();
				wortEingeben = true;
				verschl�sselt = "";
				fehlVersuche = 0;
				wortGenerieren = true;
				wortGenerieren();
				labelText = "Ratewort: " + verschl�sselt;
			}

			if (geh�ngt == false && erraten == false) {
				// Abfrage des Buchstabens in Kleinbuchstaben
				buchstabe = eingabe.getText();
				buchstabe = buchstabe.toLowerCase();
				buchstabe = buchstabe.replaceAll(" ", "");
				buchstabe = buchstabe.replaceAll("[^a-z]", "");
				
				// Zur�cksetzen der Eingabe
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
					// Buchstabenl�nge = 1 und nicht in benutzteBuchstaben
					benutzteBuchstaben.add(buchstabe);
					if (erraten == false) {
						�berpr�fung();
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// Spiel verlassen wenn gewonnen oder verloren
			if (erraten == true || geh�ngt == true) {
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
			// Beschreiben der Datei mit allen wichtigen Variablen
			writer.println("Bot");
			writer.println(ratewort);
			writer.println(verschl�sselt);
			writer.println(buchstabe);
			writer.println(�berpr�fung);
			writer.println(enter);
			writer.println(wortEingeben);
			writer.println(nichtErlaubterBuchstabe);
			if (falscherBuchstabe.length() > 0) {
				writer.println(falscherBuchstabe);
			} else {
				falscherBuchstabe = "leer";
				writer.println(falscherBuchstabe);
			}
			writer.println(nichtEnthalten);
			writer.println(erraten);
			writer.println(ratewortEingegeben);
			writer.println(geh�ngt);
			writer.println(fehlVersuche);
			writer.println(versuche);
			writer.println(maxLenght);
			writer.println(benutzteBuchstaben);
			writer.println(bildCounter);
			writer.println(bilderLaden);
			writer.println(BotCtrl.wahl);
			writer.flush();

			System.exit(0);
		}
	}

	/**
	 * Erzeugen des ratewortes in ?
	 */
	public void verschl�sseln() {
		for (int i = 0; i < ratewort.length(); i++) {
			if (ratewort.charAt(i) != ' ') {
				verschl�sselt = verschl�sselt + "?";
			} else {
				verschl�sselt = verschl�sselt + " ";
			}
		}
	}

	/**
	 * �berpr�ft ob Buchstaben im ratewort enthalten sind
	 * Diese werden aufgedeckt
	 */
	public void �berpr�fung() {
		versuche++;
		// Richtiger Buchstabe enthalten?
		for (int i = 0; i < verschl�sselt.length(); i++) {
			if (�berpr�fung.charAt(i) == buchstabe.charAt(0)) {
				StringBuilder sb = new StringBuilder(verschl�sselt);
				sb.setCharAt(i, ratewort.charAt(i));
				verschl�sselt = sb.toString();
				labelText = "Ratewort: " + verschl�sselt;
				nichtEnthalten = false;
			}
		}

		// Wenn nicht enthalten fehlversuche erh�hen
		if (nichtEnthalten == true) {
			if (fehlVersuche != 0) {
				falscherBuchstabe = falscherBuchstabe + ", " + buchstabe;
			} else {
				falscherBuchstabe = falscherBuchstabe + buchstabe;
			}
			fehlVersuche++;
		}
		nichtEnthalten = true;

		// �berpr�fung ob Wort erraten wurde
		if (verschl�sselt.equals(ratewort)) {
			labelText = "Du hast das Wort " + ratewort
					+ " erraten: Dr�cke ENTER um neu zu starten und ESC um zu verlassen";
			erraten = true;
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
	 * Generieren des Ratewortes und anschlie�ende Verschl�sselung
	 */
	public void wortGenerieren() {
		random = (int) (Math.random() * list.size());
		ratewort = list.get(random);
		�berpr�fung = ratewort.toLowerCase();
		verschl�sseln();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
