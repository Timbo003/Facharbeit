package de.tim.facharbeit.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;

public class StartFrame {
	private static JFrame frame;
	public static JLabel streetSliderText;
	public static JLabel infectedSliderText;
	public static JLabel imuneSliderText;
	public static JLabel humanCountSliderText;
	public static JLabel maxHumansInHomeSliderText;
	public static JLabel bedachtSliderText;
	public static JLabel verweigererSliderText;
	public static JLabel maxTimeSickSliderText;
	public static JLabel infectionDistanceSliderText;
	public static JLabel infectionRiskText;
	public static JLabel mortalityText;
	public static JLabel maskEffectivityText;

	public static Font textFont = new Font("Arial", Font.PLAIN, (int) (Variables.screenSize.getWidth() / 147));

	public static JSlider streetSlider;
	public static JSlider infectedSlider;
	public static JSlider imuneSlider;
	public static JSlider humanCountSlider;
	public static JSlider maxHumansInHomeSlider;
	public static JSlider bedachtSlider;
	public static JSlider verweigererSlider;
	public static JSlider maxTimeSickSlider;
	public static JSlider infectionDistanceSlider;

	public static JButton startSimButton;


	public static int SliderX = 5;							//genormte slider Ma?e
	public static int SliderY = 5;
	public static int SliderW = 300;
	public static int SliderH = 20;

	public static Color buttonColor = new Color(230, 239, 244);				//Hellblaue Farbe der Kn?pfe

	public StartFrame() {
		frame = new JFrame("StartScreen");//Name des Fensters									
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//wird beendet wenn es geschlossen wird
		frame.setResizable(false);//man kann es nicht gr??er oder kleiner ziehen
		frame.getContentPane().setPreferredSize(new Dimension(750, 500));//width & height 
		frame.setLayout(null);

		setupStartFrame();

		// ENDE nichts mehr danach
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void setupStartFrame() {
		startButtonSetup();
		sliderSetup();
		presetSetup();
	}

	private static void startButtonSetup() {													//funk die den gro?en Start Knopf unten erzeugt
		// Start Button
		startSimButton = new JButton("Start Sim");
		startSimButton.setFont(Variables.defaultFont);
		startSimButton.addActionListener((e) -> {
			if (Variables.infectionRiskInputOk == true && Variables.mortalityInputOk == true
					&& Variables.maskEffectivityInputOk == true) {
				Main.switchToSim();
				frame.dispose();
				System.out.println("press");
			} else {
				startSimButton.setBackground(Color.red);
			}

		});
		startSimButton.setBounds(25, frame.getHeight() - 150, frame.getWidth() - 65, 100);
		startSimButton.setVisible(true);
		startSimButton.setBackground(buttonColor);
		frame.add(startSimButton);
	}

	private static void presetSetup() {															//funktion die die Kn?pfe ganz rechts mit den presets erzeugt
		// PresetLable
		JPanel PresetLable = new JPanel();
		PresetLable.setVisible(true);
		PresetLable.setBounds(610, 25, 125, 350);
//		PresetLable.setBackground(Color.white);
		PresetLable.setLayout(null);

		JButton p1 = new JButton("Preset 1");													//neuer Knopf wird erzeugt
		p1.setFont(Variables.defaultFont);
		p1.addActionListener((e) -> {															//beim dr?cken werden:
			System.out.println("p1");
			Variables.streetCount = 15;															//die Variabeln ge?ndert
			streetSliderText.setText("Stra?en: " + Variables.streetCount);						//der Text upgedated
			streetSlider.setValue(Variables.streetCount);										//der Slider r?ckt zur richtigen Stelle
			
			Variables.infectedCount = 5;														//immer das gleiche
			infectedSliderText.setText("Infizierte: " + Variables.infectedCount);
			infectedSlider.setValue(Variables.infectedCount);

			Variables.imuneCount = 0;
			imuneSliderText.setText("Immune: " + Variables.imuneCount);
			imuneSlider.setValue(Variables.imuneCount);

			Variables.totalHumanCounter = 223;
			humanCountSliderText.setText("Menschen Anzahl: " + Variables.totalHumanCounter);
			humanCountSlider.setValue(Variables.totalHumanCounter);

			Variables.maxHumansInHome = 4;
			maxHumansInHomeSliderText.setText("Fam. gr??e: " + Variables.maxHumansInHome);
			maxHumansInHomeSlider.setValue(Variables.maxHumansInHome);

			Variables.bedachtCount = 45;
			bedachtSliderText.setText("Bedachte: " + Variables.bedachtCount);
			bedachtSlider.setValue(Variables.bedachtCount);

			Variables.verweigererCount = 45;
			verweigererSliderText.setText("Verweigerer: " + Variables.verweigererCount);
			verweigererSlider.setValue(Variables.verweigererCount);

			Variables.maxTimeSick = 14;
			maxTimeSickSliderText.setText("Krankheitsdauer: " + Variables.maxTimeSick);
			maxTimeSickSlider.setValue(Variables.maxTimeSick);

			Variables.infectionDistance = 3;
			infectionDistanceSliderText.setText("Infektionsdistanz: " + Variables.infectionDistance);
			infectionDistanceSlider.setValue(Variables.infectionDistance);

			Variables.infectionRisk = 0.044;
			infectionRiskText.setText("Infectionsrisiko: " + Variables.infectionRisk + "%");

			Variables.mortality = 2.57;
			mortalityText.setText("Mortalit?t: " + Variables.mortality + "%");
			
			Variables.maskEffectivity = 90;
			maskEffectivityText.setText("MaskEffectivity: " + Variables.maskEffectivity + "%");
		});
		p1.setBounds(0, 25, 115, 83);

		p1.setBackground(buttonColor);

		p1.setVisible(true);
		PresetLable.add(p1);

		JButton p2 = new JButton("Preset 2");														//siehe p1
		p2.setFont(Variables.defaultFont);
		p2.addActionListener((e) -> {
		});
		p2.setBounds(0, 133, 115, 83);
		p2.setVisible(true);
		p2.setBackground(buttonColor);
		PresetLable.add(p2);

		JButton p3 = new JButton("Preset 3");														//siehe p1
		p3.setFont(Variables.defaultFont);
		p3.addActionListener((e) -> {
			System.out.println("P3");
			Variables.streetCount = 15;
			streetSliderText.setText("Stra?en: " + Variables.streetCount);
			streetSlider.setValue(Variables.streetCount);
			

			Variables.infectedCount = 1;
			infectedSliderText.setText("Infizierte: " + Variables.infectedCount);
			infectedSlider.setValue(Variables.infectedCount);

			Variables.imuneCount = 0;
			imuneSliderText.setText("Immune: " + Variables.imuneCount);
			imuneSlider.setValue(Variables.imuneCount);

			Variables.totalHumanCounter = 1;
			humanCountSliderText.setText("Menschen Anzahl: " + Variables.totalHumanCounter);
			humanCountSlider.setValue(Variables.totalHumanCounter);

			Variables.maxHumansInHome = 1;
			maxHumansInHomeSliderText.setText("Fam. gr??e: " + Variables.maxHumansInHome);
			maxHumansInHomeSlider.setValue(Variables.maxHumansInHome);

			Variables.bedachtCount = 1;
			bedachtSliderText.setText("Bedachte: " + Variables.bedachtCount);
			bedachtSlider.setValue(Variables.bedachtCount);

			Variables.verweigererCount = 0;
			verweigererSliderText.setText("Verweigerer: " + Variables.verweigererCount);
			verweigererSlider.setValue(Variables.verweigererCount);

			Variables.maxTimeSick = 1;
			maxTimeSickSliderText.setText("Krankheitsdauer: " + Variables.maxTimeSick);
			maxTimeSickSlider.setValue(Variables.maxTimeSick);


			Variables.infectionRisk = 0.005;
			infectionRiskText.setText("Infectionsrisiko: " + Variables.infectionRisk + "%");

			Variables.mortality = 2.57;
			mortalityText.setText("Mortalit?t: " + Variables.mortality + "%");
			
			Variables.maskEffectivity = 90;
			maskEffectivityText.setText("MaskEffectivity: " + Variables.maskEffectivity + "%");
		});
		p3.setBounds(0, 241, 115, 83);
		p3.setVisible(true);
		p3.setBackground(buttonColor);
		PresetLable.add(p3);

		frame.add(PresetLable);																				//alle presets werden zu dem Frame hinzugef?gt
	}

	private static void sliderSetup() {
		// SliderLabele
		JPanel SliderLable = new JPanel();																	//panel f?r alle silider auf dem start frame
		SliderLable.setVisible(true);
		SliderLable.setBounds(25, 15, 550, 365);
//		SliderLable.setBackground(Color.LIGHT_GRAY);
		SliderLable.setLayout(null);

		// slider & text for Streets																		//das folgende ist f?r alle slider gleich
		JPanel streetPanel = new JPanel();																	//panel auf dem sich die slider befinden wird erschaffen											
		streetPanel.setVisible(true);	
		streetPanel.setBounds(50, 0, 500, 30);
//		streetPanel.setBackground(Color.red);
		streetPanel.setLayout(null);

		streetSliderText = new JLabel();																	//Text der neben dem Slider steht wird erstellt 
		streetSliderText.setFont(textFont);
		streetSliderText.setBounds(310, 0, 200, 30);
		streetSliderText.setVisible(true);
		streetSliderText.setText("Stra?en: " + Variables.streetCount);										//bekommt diese Aufschrift

		streetSlider = new JSlider(0, 50, Variables.streetCount);											//Slider um die Variabel anzupassen
		streetSlider.addChangeListener((e) -> {																//passt die Variabel seinem momentanen Value an
			int value = ((JSlider) e.getSource()).getValue();
			System.out.println(value);
			Variables.streetCount = value;
			streetSliderText.setText("Stra?en: " + value);
		});
		streetSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		streetSlider.setVisible(true);

		streetPanel.add(streetSlider);																		
		streetPanel.add(streetSliderText);

		SliderLable.add(streetPanel);																		//das panel mit dem slider und seinem Text wird dem sliderLable geadded

		// slider & text for Infected
		JPanel infectedPanel = new JPanel();
		infectedPanel.setVisible(true);
		infectedPanel.setBounds(50, 60, 450, 30);
//		infectedPanel.setBackground(Color.blue);
		infectedPanel.setLayout(null);

		infectedSliderText = new JLabel();
		infectedSliderText.setFont(textFont);
		infectedSliderText.setBounds(310, 0, 200, 30);
		infectedSliderText.setVisible(true);
		infectedSliderText.setText("Infizierte: " + Variables.infectedCount);

		infectedSlider = new JSlider(1, 100, Variables.infectedCount);
		infectedSlider.addChangeListener((e) -> {
			Variables.infectedCount = ((JSlider) e.getSource()).getValue();
			infectedSliderText.setText("Infizierte: " + Variables.infectedCount);
		});
		infectedSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		infectedSlider.setVisible(true);

		infectedPanel.add(infectedSlider);
		infectedPanel.add(infectedSliderText);
		SliderLable.add(infectedPanel);

		// slider & text for Imune
		JPanel imunePanel = new JPanel();
		imunePanel.setVisible(true);
		imunePanel.setBounds(50, 90, 450, 30);
//		imunePanel.setBackground(Color.green);
		imunePanel.setLayout(null);

		imuneSliderText = new JLabel();
		imuneSliderText.setFont(textFont);
		imuneSliderText.setBounds(310, 0, 200, 30);
		imuneSliderText.setVisible(true);
		imuneSliderText.setText("Immune: " + Variables.imuneCount);

		imuneSlider = new JSlider(0, 100, Variables.imuneCount);
		imuneSlider.addChangeListener((e1) -> {
			Variables.imuneCount = ((JSlider) e1.getSource()).getValue();
			imuneSliderText.setText("Immune: " + Variables.imuneCount);
		});
		imuneSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		imuneSlider.setVisible(true);
		imunePanel.add(imuneSlider);
		imunePanel.add(imuneSliderText);
		SliderLable.add(imunePanel);

		// slider & text for humanCount
		JPanel humanCountPanel = new JPanel();
		humanCountPanel.setVisible(true);
		humanCountPanel.setBounds(50, 30, 450, 30);
//		humanCountPanel.setBackground(Color.orange);
		humanCountPanel.setLayout(null);

		humanCountSliderText = new JLabel();
		humanCountSliderText.setFont(textFont);
		humanCountSliderText.setBounds(310, 0, 200, 30);
		humanCountSliderText.setVisible(true);
		humanCountSliderText.setText("Menschen Anzahl: " + Variables.totalHumanCounter);

		humanCountSlider = new JSlider(1, 400, Variables.totalHumanCounter);
		humanCountSlider.addChangeListener((e) -> {
			Variables.totalHumanCounter = ((JSlider) e.getSource()).getValue();
			humanCountSliderText.setText("Menschen Anzahl: " + Variables.totalHumanCounter);
		});
		humanCountSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		humanCountSlider.setVisible(true);

		humanCountPanel.add(humanCountSlider);
		humanCountPanel.add(humanCountSliderText);
		SliderLable.add(humanCountPanel);

		// slider & text for maxHumansInHome
		JPanel maxHumansInHomePanel = new JPanel();
		maxHumansInHomePanel.setVisible(true);
		maxHumansInHomePanel.setBounds(50, 120, 450, 30);
//		maxHumansInHomePanel.setBackground(Color.pink);
		maxHumansInHomePanel.setLayout(null);

		maxHumansInHomeSliderText = new JLabel();
		maxHumansInHomeSliderText.setFont(textFont);
		maxHumansInHomeSliderText.setBounds(310, 0, 200, 30);
		maxHumansInHomeSliderText.setVisible(true);
		maxHumansInHomeSliderText.setText("Fam. gr??e: " + Variables.maxHumansInHome);

		maxHumansInHomeSlider = new JSlider(1, 10, Variables.maxHumansInHome);
		maxHumansInHomeSlider.addChangeListener((e) -> {
			Variables.maxHumansInHome = ((JSlider) e.getSource()).getValue();
			maxHumansInHomeSliderText.setText("Fam. gr??e: " + Variables.maxHumansInHome);
		});
		maxHumansInHomeSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		maxHumansInHomeSlider.setVisible(true);

		maxHumansInHomePanel.add(maxHumansInHomeSlider);
		maxHumansInHomePanel.add(maxHumansInHomeSliderText);
		SliderLable.add(maxHumansInHomePanel);

		// slider & text for bedacht
		JPanel bedachtPanel = new JPanel();
		bedachtPanel.setVisible(true);
		bedachtPanel.setBounds(50, 150, 450, 30);
//		bedachtPanel.setBackground(Color.cyan);
		bedachtPanel.setLayout(null);

		bedachtSliderText = new JLabel();
		bedachtSliderText.setFont(textFont);
		bedachtSliderText.setBounds(310, 0, 200, 30);
		bedachtSliderText.setVisible(true);
		bedachtSliderText.setText("Bedachte: " + Variables.bedachtCount);

		bedachtSlider = new JSlider(0, 100, Variables.bedachtCount);
		bedachtSlider.addChangeListener((e) -> {
			Variables.bedachtCount = ((JSlider) e.getSource()).getValue();
			bedachtSliderText.setText("Bedachte: " + Variables.bedachtCount);
		});
		bedachtSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		bedachtSlider.setVisible(true);

		bedachtPanel.add(bedachtSlider);
		bedachtPanel.add(bedachtSliderText);
		SliderLable.add(bedachtPanel);

		// slider & text for bedacht
		JPanel verweigererPanel = new JPanel();
		verweigererPanel.setVisible(true);
		verweigererPanel.setBounds(50, 180, 450, 30);
//		verweigererPanel.setBackground(Color.MAGENTA);
		verweigererPanel.setLayout(null);

		verweigererSliderText = new JLabel();
		verweigererSliderText.setFont(textFont);
		verweigererSliderText.setBounds(310, 0, 200, 30);
		verweigererSliderText.setVisible(true);
		verweigererSliderText.setText("Verweigerer: " + Variables.verweigererCount);

		verweigererSlider = new JSlider(0, 100, Variables.verweigererCount);
		verweigererSlider.addChangeListener((e) -> {
			Variables.verweigererCount = ((JSlider) e.getSource()).getValue();
			verweigererSliderText.setText("Verweigerer: " + Variables.verweigererCount);
		});
		verweigererSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		verweigererSlider.setVisible(true);

		verweigererPanel.add(verweigererSlider);
		verweigererPanel.add(verweigererSliderText);
		SliderLable.add(verweigererPanel);

		// slider & text for maxTimeSick
		JPanel maxTimeSickPanel = new JPanel();
		maxTimeSickPanel.setVisible(true);
		maxTimeSickPanel.setBounds(50, 210, 450, 30);
//		maxTimeSickPanel.setBackground(Color.gray);
		maxTimeSickPanel.setLayout(null);

		maxTimeSickSliderText = new JLabel();
		maxTimeSickSliderText.setFont(textFont);
		maxTimeSickSliderText.setBounds(310, 0, 200, 30);
		maxTimeSickSliderText.setVisible(true);
		maxTimeSickSliderText.setText("Krankheitsdauer: " + Variables.maxTimeSick);

		maxTimeSickSlider = new JSlider(0, 30, Variables.maxTimeSick);
		maxTimeSickSlider.addChangeListener((e) -> {
			Variables.maxTimeSick = ((JSlider) e.getSource()).getValue();
			maxTimeSickSliderText.setText("Krankheitsdauer: " + Variables.maxTimeSick);
		});
		maxTimeSickSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		maxTimeSickSlider.setVisible(true);

		maxTimeSickPanel.add(maxTimeSickSlider);
		maxTimeSickPanel.add(maxTimeSickSliderText);
		SliderLable.add(maxTimeSickPanel);

		// slider & text for infectionDistance
		JPanel infectionDistancePanel = new JPanel();
		infectionDistancePanel.setVisible(true);
		infectionDistancePanel.setBounds(50, 240, 450, 30);
//		infectionDistancePanel.setBackground(Color.white);
		infectionDistancePanel.setLayout(null);

		infectionDistanceSliderText = new JLabel();
		infectionDistanceSliderText.setFont(textFont);
		infectionDistanceSliderText.setBounds(310, 0, 200, 30);
		infectionDistanceSliderText.setVisible(true);
		infectionDistanceSliderText.setText("Infektionsdistanz: " + Variables.infectionDistance);

		infectionDistanceSlider = new JSlider(0, 30, Variables.infectionDistance);
		infectionDistanceSlider.addChangeListener((e) -> {
			Variables.infectionDistance = ((JSlider) e.getSource()).getValue();
			infectionDistanceSliderText.setText("Infektionsdistanz: " + Variables.infectionDistance);
		});
		infectionDistanceSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		infectionDistanceSlider.setVisible(true);

		infectionDistancePanel.add(infectionDistanceSlider);
		infectionDistancePanel.add(infectionDistanceSliderText);
		SliderLable.add(infectionDistancePanel);

		// TextInput for infectionRisk																							//das folgende ist f?r alle 3 Text Eingaben das gleiche
		JPanel infectionRiskPanel = new JPanel();																				//panel f?r Eingabefeld und den Tetx nebendran
		infectionRiskPanel.setVisible(true);
		infectionRiskPanel.setBounds(50, 270, 450, 30);
//		infectionRiskPanel.setBackground(Color.green);
		infectionRiskPanel.setLayout(null);

		infectionRiskText = new JLabel();																						//Text wird erstellt
		infectionRiskText.setFont(textFont);
		infectionRiskText.setBounds(310, 0, 200, 30);
		infectionRiskText.setVisible(true);
		infectionRiskText.setText("Infectionsrisiko: " + Variables.infectionRisk + "%");											//Text wird zu "Infectionsrisiko: " + Variables.infectionRisk + "%"

		JTextField infectionRiskInput = new JTextField(6);
		infectionRiskInput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {																		//wird enter gepresst?
				try {																											//besteht es aus zahlen?
					System.out.println("enter pressed");
					Variables.infectionRisk = Double.parseDouble(infectionRiskInput.getText());	
					if (Variables.infectionRisk < 0  || Variables.infectionRisk > 100) {										//ist es eine zahl zwischen 0-100								
						infectionRiskText.setText("ung?ltiger Wert");
						infectionRiskText.setForeground(Color.red);
						Variables.infectionRiskInputOk = false;
					} else {																									//wenn nicht error message & button rot
						infectionRiskText.setText("Infectionsrisiko: " + infectionRiskInput.getText() + "%");
						Variables.infectionRiskInputOk = true;
						infectionRiskText.setForeground(Color.black);
						if (Variables.infectionRiskInputOk == true && Variables.mortalityInputOk == true
								&& Variables.maskEffectivityInputOk == true) {
							startSimButton.setBackground(buttonColor);
						}
					}
				} catch (Exception e2) {																						//wenn es keine zahl ist dann  error message & button rot
					infectionRiskText.setText("ung?ltiger Wert");	
					Variables.infectionRiskInputOk = false;
					infectionRiskText.setForeground(Color.red);

				}
			}
		});
		infectionRiskInput.setVisible(true);
		infectionRiskInput.setBounds(SliderX, SliderY, SliderW, SliderH);
		infectionRiskInput.setHorizontalAlignment(JTextField.CENTER);															//man schreibt in der mitte des Eingabefelds

		infectionRiskPanel.add(infectionRiskText);
		infectionRiskPanel.add(infectionRiskInput);
		SliderLable.add(infectionRiskPanel);

		// TextInput for mortality
		JPanel mortalityPanel = new JPanel();
		mortalityPanel.setVisible(true);
		mortalityPanel.setBounds(50, 300, 450, 30);
//		mortalityPanel.setBackground(Color.orange);
		mortalityPanel.setLayout(null);

		mortalityText = new JLabel();
		mortalityText.setFont(textFont);
		mortalityText.setBounds(310, 0, 200, 30);
		mortalityText.setVisible(true);
		mortalityText.setText("Mortalit?t: " + Variables.mortality + "%");

		JTextField mortalityInput = new JTextField(6);
		mortalityInput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("enter pressed");
					Variables.mortality = Double.parseDouble(mortalityInput.getText());
					if (Variables.mortality < 0 || Variables.mortality > 100) {
						mortalityText.setText("ung?ltiger Wert");
						mortalityText.setForeground(Color.red);
						Variables.mortalityInputOk = false;
					} else {
						mortalityText.setText("Mortalit?t: " + mortalityInput.getText() + "%");
						Variables.mortalityInputOk = true;
						mortalityText.setForeground(Color.black);
						if (Variables.infectionRiskInputOk == true && Variables.mortalityInputOk == true
								&& Variables.maskEffectivityInputOk == true) {
							startSimButton.setBackground(buttonColor);
						}
					}
				} catch (Exception e2) {
					mortalityText.setForeground(Color.red);
					mortalityText.setText("ung?ltiger Wert");
					Variables.mortalityInputOk = false;
				}
			}
		});
		mortalityInput.setVisible(true);
		mortalityInput.setBounds(SliderX, SliderY, SliderW, SliderH);
		mortalityInput.setHorizontalAlignment(JTextField.CENTER);

		mortalityPanel.add(mortalityText);
		mortalityPanel.add(mortalityInput);
		SliderLable.add(mortalityPanel);

		// TextInput for maskEffectivity
		JPanel maskEffectivityPanel = new JPanel();
		maskEffectivityPanel.setVisible(true);
		maskEffectivityPanel.setBounds(50, 330, 450, 30);
//		maskEffectivityPanel.setBackground(Color.orange);
		maskEffectivityPanel.setLayout(null);

		maskEffectivityText = new JLabel();
		maskEffectivityText.setFont(textFont);
		maskEffectivityText.setBounds(310, 0, 200, 30);
		maskEffectivityText.setVisible(true);
		maskEffectivityText.setText("MaskEffectivity: " + Variables.maskEffectivity + "%");

		JTextField maskEffectivityInput = new JTextField(6);
		maskEffectivityInput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("enter pressed");
				try {
					Variables.maskEffectivity = Double.parseDouble(maskEffectivityInput.getText());
					if (Variables.maskEffectivity < 0 || Variables.maskEffectivity > 100) {
						maskEffectivityText.setText("ung?ltiger Wert");
						maskEffectivityText.setForeground(Color.red);
						Variables.maskEffectivityInputOk = false;
					} else {
						maskEffectivityText.setText("MaskEffectivity: " + maskEffectivityInput.getText() + "%");
						Variables.maskEffectivityInputOk = true;
						maskEffectivityText.setForeground(Color.black);
						if (Variables.infectionRiskInputOk == true && Variables.mortalityInputOk == true
								&& Variables.maskEffectivityInputOk == true) {
							startSimButton.setBackground(buttonColor);
						}
					}

				} catch (Exception e2) {
					maskEffectivityText.setText("ung?ltiger Wert");
					maskEffectivityText.setForeground(Color.red);
					Variables.maskEffectivityInputOk = false;
				}
			}
		});
		maskEffectivityInput.setVisible(true);
		maskEffectivityInput.setBounds(SliderX, SliderY, SliderW, SliderH);
		maskEffectivityInput.setHorizontalAlignment(JTextField.CENTER);

		maskEffectivityPanel.add(maskEffectivityText);
		maskEffectivityPanel.add(maskEffectivityInput);
		SliderLable.add(maskEffectivityPanel);

		frame.add(SliderLable);
	}
}
