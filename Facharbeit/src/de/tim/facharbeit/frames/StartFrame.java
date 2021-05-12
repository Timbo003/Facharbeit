package de.tim.facharbeit.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	public  static JLabel streetSliderText;
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
	
	public static JSlider streetSlider;
	
	public static int SliderX = 5;
	public static int SliderY = 5;
	public static int SliderW = 300;
	public static int SliderH = 20;
	
	public StartFrame() {
		frame = new JFrame("StartScreen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setPreferredSize(new Dimension(750, 500));
		frame.setLayout(null);

		setupStartFrame();

		// ENDE nichts mehr danach
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void setupStartFrame() {
		startButtonSetup();
		presetSetup();
		sliderSetup();
	}

	private static void startButtonSetup() {
		// Start Button
		JButton startSimButton = new JButton("Start Sim");
		startSimButton.addActionListener((e) -> {
			Main.switchToSim();
			frame.dispose();
			System.out.println("press");
		});
		startSimButton.setBounds(25, frame.getHeight() - 150, frame.getWidth() - 65, 100);
		startSimButton.setVisible(true);
		frame.add(startSimButton);
	}

	private static void presetSetup() {
		// PresetLable
		JPanel PresetLable = new JPanel();
		PresetLable.setVisible(true);
		PresetLable.setBounds(610, 25, 125, 350);
//		PresetLable.setBackground(Color.white);
		PresetLable.setLayout(null);

		JButton p1 = new JButton("Preset 1");
		p1.addActionListener((e) -> {
			System.out.println("p1");
			Variables.streetCount = 12;
			streetSliderText.setText("Streets: " + Variables.streetCount);
//			streetSlider.setValue(Variables.streetCount);
			
			Variables.infectedCount = 10;
			infectedSliderText.setText("Infected: " + Variables.infectedCount);
			
			Variables.imuneCount = 5;
			imuneSliderText.setText("Imune: " + Variables.imuneCount);
			
			Variables.totalHumanCounter = 200;
			humanCountSliderText.setText("Humans: " + Variables.totalHumanCounter);	
			
			Variables.maxHumansInHome = 4;
			maxHumansInHomeSliderText.setText("Fam. gr��e: " + Variables.maxHumansInHome);	
			
			Variables.bedachtCount = 20;
			bedachtSliderText.setText("Bedachte: " + Variables.bedachtCount);
			
			Variables.verweigererCount = 20;
			verweigererSliderText.setText("Verweigerer: " + Variables.verweigererCount);
			
			Variables.maxTimeSick = 5;
			maxTimeSickSliderText.setText("maxTimeSick: " + Variables.maxTimeSick);
			
			Variables.infectionDistance = 10;
			infectionDistanceSliderText.setText("infectionDistance: " + Variables.infectionDistance);
			
			Variables.infectionRisk = 0.005;
			infectionRiskText.setText("infectionRisk: " + Variables.infectionRisk + "%");
			
			Variables.mortality = 2.57;
			mortalityText.setText("mortality: " + Variables.mortality + "%");
		});
		p1.setBounds(0, 25, 115, 83);
		p1.setVisible(true);
		PresetLable.add(p1);

		JButton p2 = new JButton("Preset 2");
		p2.addActionListener((e) -> {
			System.out.println("P2");

			imuneSliderText.setText("b");
			humanCountSliderText.setText("b");
			maxHumansInHomeSliderText.setText("b");
			bedachtSliderText.setText("b");
			verweigererSliderText.setText("b");
			maxTimeSickSliderText.setText("b");
			infectionDistanceSliderText.setText("b");
			infectionRiskText.setText("b");
			mortalityText.setText("b");
		});
		p2.setBounds(0, 133, 115, 83);
		p2.setVisible(true);
		PresetLable.add(p2);

		JButton p3 = new JButton("Preset 3");
		p3.addActionListener((e) -> {
			System.out.println("P3");
		});
		p3.setBounds(0, 241, 115, 83);
		p3.setVisible(true);
		PresetLable.add(p3);

		frame.add(PresetLable);
	}

	private static void sliderSetup() {
		// SliderLabele
		JPanel SliderLable = new JPanel();
		SliderLable.setVisible(true);
		SliderLable.setBounds(25, 25, 550, 350);
//		SliderLable.setBackground(Color.LIGHT_GRAY);
		SliderLable.setLayout(null);

		// slider & text for Streets
		JPanel streetPanel = new JPanel();
		streetPanel.setVisible(true);
		streetPanel.setBounds(50, 10, 450, 30);
//		streetPanel.setBackground(Color.red);
		streetPanel.setLayout(null);
		
		streetSliderText = new JLabel();
		streetSliderText.setBounds(310,0, 200, 30);
		streetSliderText.setVisible(true);
		streetSliderText.setText("Streets: " + Variables.streetCount);
		

		streetSlider = new JSlider(0, 50, Variables.streetCount);
		streetSlider.addChangeListener((e) -> {
			Variables.streetCount = streetSlider.getValue();
			streetSliderText.setText("Streets: " + streetSlider.getValue());
		});
		streetSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		streetSlider.setVisible(true);
		
		streetPanel.add(streetSlider);
		streetPanel.add(streetSliderText);
		
		
		SliderLable.add(streetPanel);

		// slider & text for Infected
		JPanel infectedPanel = new JPanel();
		infectedPanel.setVisible(true);
		infectedPanel.setBounds(50, 40, 450, 30);
//		infectedPanel.setBackground(Color.blue);
		infectedPanel.setLayout(null);
		
		infectedSliderText = new JLabel();
		infectedSliderText.setBounds(310,0, 200, 30);
		infectedSliderText.setVisible(true);
		infectedSliderText.setText("Infected: " + Variables.infectedCount);

		JSlider infectedSlider = new JSlider(1, 100, Variables.infectedCount);
		infectedSlider.addChangeListener((e) -> {
			Variables.infectedCount = infectedSlider.getValue();
			infectedSliderText.setText("Infected: " + infectedSlider.getValue());
		});
		infectedSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		infectedSlider.setVisible(true);
		
		infectedPanel.add(infectedSlider);
		infectedPanel.add(infectedSliderText);
		SliderLable.add(infectedPanel);


		// slider & text for Imune
		JPanel imunePanel = new JPanel();
		imunePanel.setVisible(true);
		imunePanel.setBounds(50, 70, 450, 30);
//		imunePanel.setBackground(Color.green);
		imunePanel.setLayout(null);
		
		imuneSliderText = new JLabel();
		imuneSliderText.setBounds(310,0, 200, 30);
		imuneSliderText.setVisible(true);
		imuneSliderText.setText("Imune: " + Variables.imuneCount);

		JSlider imuneSlider = new JSlider(0, 100, Variables.imuneCount);
		imuneSlider.addChangeListener((e) -> {
			Variables.imuneCount = imuneSlider.getValue();
			imuneSliderText.setText("Imune: " + imuneSlider.getValue());
		});
		imuneSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		imuneSlider.setVisible(true);
		imunePanel.add(imuneSlider);
		imunePanel.add(imuneSliderText);
		SliderLable.add(imunePanel);

		// slider & text for humanCount
		JPanel humanCountPanel = new JPanel();
		humanCountPanel.setVisible(true);
		humanCountPanel.setBounds(50, 100, 450, 30);
//		humanCountPanel.setBackground(Color.orange);
		humanCountPanel.setLayout(null);
		
		humanCountSliderText = new JLabel();
		humanCountSliderText.setBounds(310,0, 200, 30);
		humanCountSliderText.setVisible(true);
		humanCountSliderText.setText("Humans: " + Variables.totalHumanCounter);

		JSlider humanCountSlider = new JSlider(1, 400, Variables.totalHumanCounter);
		humanCountSlider.addChangeListener((e) -> {
			Variables.totalHumanCounter = humanCountSlider.getValue();
			humanCountSliderText.setText("Humans: " + humanCountSlider.getValue());
		});
		humanCountSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		humanCountSlider.setVisible(true);
		
		humanCountPanel.add(humanCountSlider);
		humanCountPanel.add(humanCountSliderText);
		SliderLable.add(humanCountPanel);


		// slider & text for maxHumansInHome
		JPanel maxHumansInHomePanel = new JPanel();
		maxHumansInHomePanel.setVisible(true);
		maxHumansInHomePanel.setBounds(50, 130, 450, 30);
//		maxHumansInHomePanel.setBackground(Color.pink);
		maxHumansInHomePanel.setLayout(null);
		
		maxHumansInHomeSliderText = new JLabel();
		maxHumansInHomeSliderText.setBounds(310,0, 200, 30);
		maxHumansInHomeSliderText.setVisible(true);
		maxHumansInHomeSliderText.setText("Fam. gr��e: " + Variables.maxHumansInHome);

		JSlider maxHumansInHomeSlider = new JSlider(1, 10, Variables.maxHumansInHome);
		maxHumansInHomeSlider.addChangeListener((e) -> {
			Variables.maxHumansInHome = maxHumansInHomeSlider.getValue();
			maxHumansInHomeSliderText.setText("Fam. gr��e: " + maxHumansInHomeSlider.getValue());
		});
		maxHumansInHomeSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		maxHumansInHomeSlider.setVisible(true);
		
		maxHumansInHomePanel.add(maxHumansInHomeSlider);
		maxHumansInHomePanel.add(maxHumansInHomeSliderText);
		SliderLable.add(maxHumansInHomePanel);


		// slider & text for bedacht
		JPanel bedachtPanel = new JPanel();
		bedachtPanel.setVisible(true);
		bedachtPanel.setBounds(50, 160, 450, 30);
//		bedachtPanel.setBackground(Color.cyan);
		bedachtPanel.setLayout(null);
		
		bedachtSliderText = new JLabel();
		bedachtSliderText.setBounds(310,0, 200, 30);
		bedachtSliderText.setVisible(true);
		bedachtSliderText.setText("bedacht: " + Variables.bedachtCount);

		JSlider bedachtSlider = new JSlider(0, 100, Variables.bedachtCount);
		bedachtSlider.addChangeListener((e) -> {
			Variables.bedachtCount = bedachtSlider.getValue();
			bedachtSliderText.setText("Bedachte: " + bedachtSlider.getValue());
		});
		bedachtSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		bedachtSlider.setVisible(true);
		
		bedachtPanel.add(bedachtSlider);
		bedachtPanel.add(bedachtSliderText);
		SliderLable.add(bedachtPanel);


		// slider & text for bedacht
		JPanel verweigererPanel = new JPanel();
		verweigererPanel.setVisible(true);
		verweigererPanel.setBounds(50, 190, 450, 30);
//		verweigererPanel.setBackground(Color.MAGENTA);
		verweigererPanel.setLayout(null);
		
		verweigererSliderText = new JLabel();
		verweigererSliderText.setBounds(310,0, 200, 30);
		verweigererSliderText.setVisible(true);
		verweigererSliderText.setText("verweigerer: " + Variables.verweigererCount);

		JSlider verweigererSlider = new JSlider(0, 100, Variables.verweigererCount);
		verweigererSlider.addChangeListener((e) -> {
			Variables.verweigererCount = verweigererSlider.getValue();
			verweigererSliderText.setText("Verweigerer: " + verweigererSlider.getValue());
		});
		verweigererSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		verweigererSlider.setVisible(true);
		
		verweigererPanel.add(verweigererSlider);
		verweigererPanel.add(verweigererSliderText);
		SliderLable.add(verweigererPanel);


		// slider & text for maxTimeSick
		JPanel maxTimeSickPanel = new JPanel();
		maxTimeSickPanel.setVisible(true);
		maxTimeSickPanel.setBounds(50, 220, 450, 30);
//		maxTimeSickPanel.setBackground(Color.gray);
		maxTimeSickPanel.setLayout(null);
		
		maxTimeSickSliderText = new JLabel();
		maxTimeSickSliderText.setBounds(310,0, 200, 30);
		maxTimeSickSliderText.setVisible(true);
		maxTimeSickSliderText.setText("maxTimeSick: " + Variables.maxTimeSick);

		JSlider maxTimeSickSlider = new JSlider(0, 30, Variables.maxTimeSick);
		maxTimeSickSlider.addChangeListener((e) -> {
			Variables.maxTimeSick = maxTimeSickSlider.getValue();
			maxTimeSickSliderText.setText("maxTimeSick: " + maxTimeSickSlider.getValue());
		});
		maxTimeSickSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		maxTimeSickSlider.setVisible(true);
		
		maxTimeSickPanel.add(maxTimeSickSlider);
		maxTimeSickPanel.add(maxTimeSickSliderText);
		SliderLable.add(maxTimeSickPanel);


		// slider & text for infectionDistance
		JPanel infectionDistancePanel = new JPanel();
		infectionDistancePanel.setVisible(true);
		infectionDistancePanel.setBounds(50, 250, 450, 30);
//		infectionDistancePanel.setBackground(Color.white);
		infectionDistancePanel.setLayout(null);
		
		infectionDistanceSliderText = new JLabel();
		infectionDistanceSliderText.setBounds(310,0, 200, 30);
		infectionDistanceSliderText.setVisible(true);
		infectionDistanceSliderText.setText("infectionDistance: " + Variables.infectionDistance);

		JSlider infectionDistanceSlider = new JSlider(0, 30, Variables.infectionDistance);
		infectionDistanceSlider.addChangeListener((e) -> {
			Variables.infectionDistance = infectionDistanceSlider.getValue();
			infectionDistanceSliderText.setText("infectionDistance: " + infectionDistanceSlider.getValue());
		});
		infectionDistanceSlider.setBounds(SliderX, SliderY, SliderW, SliderH);
		infectionDistanceSlider.setVisible(true);
		
		infectionDistancePanel.add(infectionDistanceSlider);
		infectionDistancePanel.add(infectionDistanceSliderText);
		SliderLable.add(infectionDistancePanel);


		// TextInput for infectionRisk
		JPanel infectionRiskPanel = new JPanel();
		infectionRiskPanel.setVisible(true);
		infectionRiskPanel.setBounds(50, 280, 450, 30);
//		infectionRiskPanel.setBackground(Color.green);
		infectionRiskPanel.setLayout(null);
		
		infectionRiskText = new JLabel();
		infectionRiskText.setBounds(310,0, 200, 30);
		infectionRiskText.setVisible(true);
		infectionRiskText.setText("infectionRisk: " + Variables.infectionRisk+ "%");
		

		JTextField infectionRiskInput = new JTextField(6);
		infectionRiskInput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("enter pressed");
				infectionRiskText.setText("infectionRisk: " + infectionRiskInput.getText() + "%");
				Variables.infectionRisk = Double.parseDouble(infectionRiskInput.getText());
			}
		});
		infectionRiskInput.setVisible(true);
		infectionRiskInput.setBounds(SliderX, SliderY, SliderW, SliderH);
		infectionRiskInput.setHorizontalAlignment(JTextField.CENTER);
		
		infectionRiskPanel.add(infectionRiskText);
		infectionRiskPanel.add(infectionRiskInput);
		SliderLable.add(infectionRiskPanel);


		// TextInput for mortality
		JPanel mortalityPanel = new JPanel();
		mortalityPanel.setVisible(true);
		mortalityPanel.setBounds(50, 310, 450, 30);
//		mortalityPanel.setBackground(Color.orange);
		mortalityPanel.setLayout(null);
		
		mortalityText = new JLabel();
		mortalityText.setBounds(310,0, 200, 30);
		mortalityText.setVisible(true);
		mortalityText.setText("mortality: " + Variables.mortality + "%");
		

		JTextField mortalityInput = new JTextField(6);
		mortalityInput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("enter pressed");
				mortalityText.setText("mortality: " + mortalityInput.getText()+ "%");
				Variables.mortality = Double.parseDouble(mortalityInput.getText());

			}
		});
		mortalityInput.setVisible(true);
		mortalityInput.setBounds(SliderX, SliderY, SliderW, SliderH);
		mortalityInput.setHorizontalAlignment(JTextField.CENTER);
		
		mortalityPanel.add(mortalityText);
		mortalityPanel.add(mortalityInput);
		SliderLable.add(mortalityPanel);


		frame.add(SliderLable);
	}
}
