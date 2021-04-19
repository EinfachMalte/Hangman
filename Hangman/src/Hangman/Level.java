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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Level extends JPanel implements ChangeListener, KeyListener {
	private static final long serialVersionUID = 1L;
	static JSlider slider1 = new JSlider(0, 5, 5), slider2 = new JSlider(0, 5, 5), slider3 = new JSlider(0, 5, 5);
	static JLabel anzeige1 = new JLabel(), anzeige2 = new JLabel(), anzeige3 = new JLabel();
	static int einfach = 0, mittel = 0, schwer = 0;
	static int counter = 0;
	static int spieleGewonnen = 0;

	static JTextField eingabe = new JTextField();
	static JLabel label = new JLabel();
	static String ratewort = "";
	static String verschl�sselt = "";
	static String buchstabe = "";
	static String �berpr�fung = "";
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
	static int maxLenght = 0;
	static String labelText = "Dr�cke enter um ein zuf�lliges Wort zu generieren";
	static ArrayList<String> benutzteBuchstaben = new ArrayList<String>();
	static ArrayList<String> benutzteW�rter = new ArrayList<String>();
	static int bildCounter = 0;
	static boolean bilderLaden = false;
	static boolean wortGenerieren = true;
	static boolean speichern = false;

	static File file;
	static String fileName = "";
	static String[] values = null;
	static ArrayList<String> einfacheW�rter = new ArrayList<String>(), mittlereW�rter = new ArrayList<String>(),
			schwereW�rter = new ArrayList<String>();
	static Scanner inputStream;
	static int random = 0;

	static PrintWriter writer;

	static BufferedImage verloren, boden, stab, galgen, seil, kopf, hals, linkerArm, beideArme, bauch, linkesBein,
			anzeigebild;

	/**
	 * Start der Level Class
	 */
	public Level() {
		// H�he und Breite des Frames
		int w = Main.frame.getWidth();
		int h = Main.frame.getHeight();

		// Frame Optionen
		setLayout(null);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		setBackground(Color.black);
		Main.frame.setResizable(false);
		Main.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// F�gt Slider hinzu
		slider1.setBackground(Color.black);
		slider1.setForeground(Color.white);
		slider1.addChangeListener(this);
		slider1.setPaintTicks(true);
		slider1.setMajorTickSpacing(1);
		slider1.setPaintTrack(true);
		slider1.setPaintLabels(true);
		slider1.setMinimum(1);
		slider1.setLabelTable(slider1.createStandardLabels(1, 1));
		slider1.setBounds(0, 0, w - 15, h / 3);

		slider2.setBackground(Color.black);
		slider2.setForeground(Color.white);
		slider2.addChangeListener(this);
		slider2.setPaintTicks(true);
		slider2.setMajorTickSpacing(1);
		slider2.setPaintTrack(true);
		slider2.setPaintLabels(true);
		slider2.setMinimum(1);
		slider2.setLabelTable(slider1.createStandardLabels(1, 1));
		slider2.setBounds(0, h / 3, w - 15, ((2 * h) / 6));

		slider3.setBackground(Color.black);
		slider3.setForeground(Color.white);
		slider3.addChangeListener(this);
		slider3.setPaintTicks(true);
		slider3.setMajorTickSpacing(1);
		slider3.setPaintTrack(true);
		slider3.setPaintLabels(true);
		slider3.setMinimum(1);
		slider3.setLabelTable(slider1.createStandardLabels(1, 1));
		slider3.setBounds(0, ((2 * h) / 6), w - 15, h);

		// Die werte der Slider werden den Variablen zugewiesen
		einfach = ((int) (slider1.getValue()));
		mittel = ((int) (slider2.getValue()));
		schwer = ((int) (slider3.getValue()));

		// Anzeige f�r bessere �bersicht
		anzeige1.setText("Anzahl der einfachen Level: " + einfach + " Dr�cke ENTER um zu best�tigen");
		anzeige1.setBounds((w - anzeige1.getWidth() / 2) / 2, 20, w, 10);
		anzeige1.setForeground(Color.white);

		anzeige2.setText("Anzahl der mittleren Level: " + mittel + " Dr�cke ENTER um zu best�tigen");
		anzeige2.setBounds((w - anzeige1.getWidth() / 2) / 2, (h / 3) + 20, w, 10);
		anzeige2.setForeground(Color.white);

		anzeige3.setText("Anzahl der schweren Level: " + schwer + " Dr�cke ENTER um zu best�tigen");
		anzeige3.setBounds((w - anzeige1.getWidth() / 2) / 2, ((2 * h) / 3) + 20, w, 10);
		anzeige3.setForeground(Color.white);

		add(anzeige1);

		// Laden des ersten Bildes
		try {
			verloren = ImageIO.read(new File("Bilder/verloren.jpg"));
			anzeigebild = verloren;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Bilder nicht gefunden");
		}

		// Lade alle W�rter aus Date HangmanLeich.txt
		fileName = "HangmanLeich.txt";
		try {
			file = new File(fileName);
			inputStream = new Scanner(file);

			while (inputStream.hasNext()) {
				String data = inputStream.next();
				values = data.split("\n");

				einfacheW�rter.add(values[0]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Datei nicht gefunden");
		}
		// F�gt KeyListener zu eingabe Hinzu
		eingabe.addKeyListener(this);

		// Generiert das Ratewort
		wortGenerieren();
	}

	/**
	 * @param g
	 * @param g2d Zeichnen der Bilder und Texte
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Bild-H�he und Breite
		int w = getWidth();
		int h = getHeight();

		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);

		// Um den String labelText zu zentrieren
		int stringLenght = (int) g2d.getFontMetrics().getStringBounds(labelText, g2d).getWidth();
		int width = w / 2 - stringLenght / 2;

		// Grenzen f�r Anzeigen
		anzeige1.setBounds((w - anzeige1.getWidth() / 2) / 2, 20, w, 10);
		anzeige2.setBounds((w - anzeige1.getWidth() / 2) / 2, (h / 3) + 20, w, 10);
		anzeige3.setBounds((w - anzeige1.getWidth() / 2) / 2, ((2 * h) / 3) + 30, w, 10);

		add(slider1);

		if (counter == 1) {
			add(slider2);
		} else if (counter == 2) {
			add(slider3);
		} else if (counter == 3) {
			Main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// Slider und Anzeigen entfernen
			remove(slider1);
			remove(slider2);
			remove(slider3);
			remove(anzeige1);
			remove(anzeige2);
			remove(anzeige3);

			// Startbildschirm
			anzeigebild = verloren;
			g2d.drawImage(anzeigebild, 0, 0, w, h, null);
			labelText = "Dr�cke enter um ein zuf�lliges Wort zu generieren";
			g.setColor(Color.black);
			g.drawString(labelText, width, h / 30);
		} else if (counter > 3) {
			// Ab hier kann man das Spiel speichern
			speichern = true;

			remove(slider1);
			remove(slider2);
			remove(slider3);
			remove(anzeige1);
			remove(anzeige2);
			remove(anzeige3);

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

			do {
				// Zeichnen des Strings und des Hangmans
				g2d.drawImage(anzeigebild, 0, 0, w, h, null);
				g.setColor(Color.black);
				g.drawString(labelText, width, h / 30);
				label.setText(labelText);
				eingabe.setBounds(w / 20, h - 20, w - w / 10, 20);
				eingabe.setFont(font);
				eingabe.setHorizontalAlignment(SwingConstants.CENTER);
				if (spieleGewonnen == (einfach + mittel + schwer)) {
					labelText = "Herzlichen Gl�ckwunsch. Du hast alle level gemeistert: Dr�cke ESC um zu beenden";
				}
			} while (labelText.equals(ratewort));
		}

		repaint();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// Wenn ein Slider bewegt wurde werden Anzeige und Variablen angepasst
		einfach = ((int) (slider1.getValue()));
		mittel = ((int) (slider2.getValue()));
		schwer = ((int) (slider3.getValue()));
		anzeige1.setText("Anzahl der einfachen Level: " + einfach + " Dr�cke ENTER um zu best�tigen");
		anzeige2.setText("Anzahl der mittleren Level: " + mittel + " Dr�cke ENTER um zu best�tigen");
		anzeige3.setText("Anzahl der schweren Level: " + schwer + " Dr�cke ENTER um zu best�tigen");
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
			if (spieleGewonnen != (einfach + mittel + schwer)) {
				counter++;

				if (counter == 1) {
					add(anzeige2);
				} else if (counter == 2) {
					add(anzeige3);
				} else if (counter > 2) {
					Main.frame.setResizable(true);

					// Anzeigetext
					labelText = "Ratewort: " + verschl�sselt;

					// Bilder laden
					bilderLaden = true;
					bildCounter++;
					bilderLaden();

					if (!(counter > 3)) {
						add(eingabe);
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
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (spieleGewonnen == (einfach + mittel + schwer)) {
				System.exit(0);
			}
		}
		// Abfrage f�r STRG+S um zu speichern
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
			if (speichern == true) {
				// Writer greift auf Datei Spielstand.txt zu
				try {
					writer = new PrintWriter(new BufferedWriter(new FileWriter("Spielstand.txt")));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// Beschreiben der Datei mit allen wichtigen Variablen
				writer.println("Level");
				writer.println(einfach);
				writer.println(mittel);
				writer.println(schwer);
				writer.println(counter);
				writer.println(spieleGewonnen);
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
				writer.println(benutzteW�rter);
				writer.println(bildCounter);
				writer.println(bilderLaden);
				writer.print(wortGenerieren);
				writer.flush();

				System.exit(0);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

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
		// Richtiger Buchstabe enthalt?
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
				falscherBuchstabe = falscherBuchstabe + "; " + buchstabe;
			} else {
				falscherBuchstabe = falscherBuchstabe + buchstabe;
			}
			fehlVersuche++;
		}
		nichtEnthalten = true;

		// �berpr�fen ob das Wort erraten wurde
		if (verschl�sselt.equals(ratewort)) {
			labelText = "Du hast das Wort " + ratewort + " erraten: Dr�cke ENTER um weiter zu spielen";
			spieleGewonnen++;
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

	public void wortGenerieren() {
		// Wenn spieleGewonnen den Wert der Slider �berschreitet
		// Legt den Dateinamen fest
		if (spieleGewonnen == einfach) {
			fileName = "HangmanMittel.txt";
		} else if (spieleGewonnen == (einfach + mittel)) {
			fileName = "HangmanSchwer.txt";
		}

		// L�dt die W�rter in ArrayLists
		try {
			file = new File(fileName);
			inputStream = new Scanner(file);

			while (inputStream.hasNext()) {
				String data = inputStream.next();
				values = data.split("\n");
				if (spieleGewonnen == einfach) {
					mittlereW�rter.add(values[0]);
				} else if (spieleGewonnen == (einfach + mittel)) {
					schwereW�rter.add(values[0]);
				}
			}
		} catch (FileNotFoundException a) {
			a.printStackTrace();
			System.out.println("Datei nicht gefunden");
		}

		// Generiert ein ratewort
		if (spieleGewonnen < einfach) {
			random = (int) (Math.random() * einfacheW�rter.size());
			ratewort = einfacheW�rter.get(random);
		} else if (spieleGewonnen >= einfach && spieleGewonnen < (einfach + mittel)) {
			random = (int) (Math.random() * mittlereW�rter.size());
			ratewort = mittlereW�rter.get(random);
		} else if (spieleGewonnen >= (einfach + mittel)) {
			random = (int) (Math.random() * schwereW�rter.size());
			ratewort = schwereW�rter.get(random);
		}

		// Verschl�sselt das Ratewort
		verschl�sseln();
		�berpr�fung = ratewort.toLowerCase();
	}
}
