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

		JButton p1 = new JButton("P1");
		p1.addActionListener((e) -> {
			System.out.println("p1");
			streetSliderText.setText("a");
			streetSliderText.setText("a");
			infectedSliderText.setText("a");
			imuneSliderText.setText("a");
			humanCountSliderText.setText("a");
			maxHumansInHomeSliderText.setText("a");
			bedachtSliderText.setText("a");
			verweigererSliderText.setText("a");
			maxTimeSickSliderText.setText("a");
			infectionDistanceSliderText.setText("a");
			infectionRiskText.setText("a");
			mortalityText.setText("a");
		});
		p1.setBounds(0, 25, 115, 83);
		p1.setVisible(true);
		PresetLable.add(p1);

		JButton p2 = new JButton("P2");
		p2.addActionListener((e) -> {
			System.out.println("P2");
			streetSliderText.setText("b");
			streetSliderText.setText("b");
			infectedSliderText.setText("b");
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

		JButton p3 = new JButton("P3");
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
		SliderLable.setBackground(Color.LIGHT_GRAY);

		// slider & text for Streets
		JPanel streetPanel = new JPanel();
		streetPanel.setVisible(true);
		streetPanel.setBounds(0, 0, 0, 0);
		streetPanel.setBackground(Color.red);
		
		streetSliderText = new JLabel();
		streetSliderText.setBounds(0, 0, 100, 100);
		streetSliderText.setVisible(true);
		streetSliderText.setText("Streets: " + Variables.streetCount);

		JSlider streetSlider = new JSlider(0, 50, Variables.streetCount);
		streetSlider.addChangeListener((e) -> {
			Variables.streetCount = streetSlider.getValue();
			streetSliderText.setText("Streets: " + streetSlider.getValue());
		});
		streetSlider.setBounds(0, 20, SliderLable.getWidth(), 200);
		streetSlider.setVisible(true);
		
		streetPanel.add(streetSlider);
		streetPanel.add(streetSliderText);
		
		SliderLable.add(streetPanel);

		// slider & text for Infected
		JPanel infectedPanel = new JPanel();
		infectedPanel.setVisible(true);
		infectedPanel.setBounds(0, 0, 0, 0);
		infectedPanel.setBackground(Color.blue);
		
		infectedSliderText = new JLabel();
		infectedSliderText.setBounds(0, 0, 100, 100);
		infectedSliderText.setVisible(true);
		infectedSliderText.setText("Infected: " + Variables.infectedCount);

		JSlider infectedSlider = new JSlider(1, 100, Variables.infectedCount);
		infectedSlider.addChangeListener((e) -> {
			Variables.infectedCount = infectedSlider.getValue();
			infectedSliderText.setText("Infected: " + infectedSlider.getValue());
		});
		infectedSlider.setBounds(0, 20, 100, 100);
		infectedSlider.setVisible(true);
		
		infectedPanel.add(infectedSlider);
		infectedPanel.add(infectedSliderText);
		SliderLable.add(infectedPanel);


		// slider & text for Imune
		JPanel imunePanel = new JPanel();
		imunePanel.setVisible(true);
		imunePanel.setBounds(0, 0, 0, 0);
		imunePanel.setBackground(Color.green);
		
		imuneSliderText = new JLabel();
		imuneSliderText.setBounds(0, 0, 100, 100);
		imuneSliderText.setVisible(true);
		imuneSliderText.setText("Imune: " + Variables.imuneCount);

		JSlider imuneSlider = new JSlider(0, 100, Variables.imuneCount);
		imuneSlider.addChangeListener((e) -> {
			Variables.imuneCount = imuneSlider.getValue();
			imuneSliderText.setText("Imune: " + imuneSlider.getValue());
		});
		imuneSlider.setBounds(0, 20, 100, 100);
		imuneSlider.setVisible(true);
		imunePanel.add(imuneSlider);
		imunePanel.add(imuneSliderText);
		SliderLable.add(imunePanel);

		// slider & text for humanCount
		JPanel humanCountPanel = new JPanel();
		humanCountPanel.setVisible(true);
		humanCountPanel.setBounds(0, 0, 0, 0);
		humanCountPanel.setBackground(Color.orange);
		
		humanCountSliderText = new JLabel();
		humanCountSliderText.setBounds(0, 0, 100, 100);
		humanCountSliderText.setVisible(true);
		humanCountSliderText.setText("Humans: " + Variables.totalHumanCounter);

		JSlider humanCountSlider = new JSlider(1, 400, Variables.totalHumanCounter);
		humanCountSlider.addChangeListener((e) -> {
			Variables.totalHumanCounter = humanCountSlider.getValue();
			humanCountSliderText.setText("Humans: " + humanCountSlider.getValue());
		});
		humanCountSlider.setBounds(0, 20, 100, 100);
		humanCountSlider.setVisible(true);
		
		humanCountPanel.add(humanCountSlider);
		humanCountPanel.add(humanCountSliderText);
		SliderLable.add(humanCountPanel);


		// slider & text for maxHumansInHome
		JPanel maxHumansInHomePanel = new JPanel();
		maxHumansInHomePanel.setVisible(true);
		maxHumansInHomePanel.setBounds(0, 0, 0, 0);
		maxHumansInHomePanel.setBackground(Color.pink);
		
		maxHumansInHomeSliderText = new JLabel();
		maxHumansInHomeSliderText.setBounds(0, 0, 100, 100);
		maxHumansInHomeSliderText.setVisible(true);
		maxHumansInHomeSliderText.setText("Fam. größe: " + Variables.maxHumansInHome);

		JSlider maxHumansInHomeSlider = new JSlider(1, 10, Variables.maxHumansInHome);
		maxHumansInHomeSlider.addChangeListener((e) -> {
			Variables.maxHumansInHome = maxHumansInHomeSlider.getValue();
			maxHumansInHomeSliderText.setText("Fam. größe: " + maxHumansInHomeSlider.getValue());
		});
		maxHumansInHomeSlider.setBounds(0, 20, 100, 100);
		maxHumansInHomeSlider.setVisible(true);
		
		maxHumansInHomePanel.add(maxHumansInHomeSlider);
		maxHumansInHomePanel.add(maxHumansInHomeSliderText);
		SliderLable.add(maxHumansInHomePanel);


		// slider & text for bedacht
		JPanel bedachtPanel = new JPanel();
		bedachtPanel.setVisible(true);
		bedachtPanel.setBounds(0, 0, 0, 0);
		bedachtPanel.setBackground(Color.cyan);
		
		bedachtSliderText = new JLabel();
		bedachtSliderText.setBounds(0, 0, 100, 100);
		bedachtSliderText.setVisible(true);
		bedachtSliderText.setText("bedacht: " + Variables.bedachtCount);

		JSlider bedachtSlider = new JSlider(0, 100, Variables.bedachtCount);
		bedachtSlider.addChangeListener((e) -> {
			Variables.bedachtCount = bedachtSlider.getValue();
			bedachtSliderText.setText("bedacht: " + bedachtSlider.getValue());
		});
		bedachtSlider.setBounds(0, 20, 100, 100);
		bedachtSlider.setVisible(true);
		
		bedachtPanel.add(bedachtSlider);
		bedachtPanel.add(bedachtSliderText);
		SliderLable.add(bedachtPanel);


		// slider & text for bedacht
		JPanel verweigererPanel = new JPanel();
		verweigererPanel.setVisible(true);
		verweigererPanel.setBounds(0, 0, 0, 0);
		verweigererPanel.setBackground(Color.MAGENTA);
		
		verweigererSliderText = new JLabel();
		verweigererSliderText.setBounds(1, 0, 100, 100);
		verweigererSliderText.setVisible(true);
		verweigererSliderText.setText("verweigerer: " + Variables.verweigererCount);

		JSlider verweigererSlider = new JSlider(0, 100, Variables.verweigererCount);
		verweigererSlider.addChangeListener((e) -> {
			Variables.verweigererCount = verweigererSlider.getValue();
			verweigererSliderText.setText("verweigerer: " + verweigererSlider.getValue());
		});
		verweigererSlider.setBounds(0, 20, 100, 100);
		verweigererSlider.setVisible(true);
		
		verweigererPanel.add(verweigererSlider);
		verweigererPanel.add(verweigererSliderText);
		SliderLable.add(verweigererPanel);


		// slider & text for maxTimeSick
		JPanel maxTimeSickPanel = new JPanel();
		maxTimeSickPanel.setVisible(true);
		maxTimeSickPanel.setBounds(0, 0, 0, 0);
		maxTimeSickPanel.setBackground(Color.gray);
		
		maxTimeSickSliderText = new JLabel();
		maxTimeSickSliderText.setBounds(1, 0, 100, 100);
		maxTimeSickSliderText.setVisible(true);
		maxTimeSickSliderText.setText("maxTimeSick: " + Variables.maxTimeSick);

		JSlider maxTimeSickSlider = new JSlider(0, 30, Variables.maxTimeSick);
		maxTimeSickSlider.addChangeListener((e) -> {
			Variables.maxTimeSick = maxTimeSickSlider.getValue();
			maxTimeSickSliderText.setText("maxTimeSick: " + maxTimeSickSlider.getValue());
		});
		maxTimeSickSlider.setBounds(0, 20, 100, 100);
		maxTimeSickSlider.setVisible(true);
		
		maxTimeSickPanel.add(maxTimeSickSlider);
		maxTimeSickPanel.add(maxTimeSickSliderText);
		SliderLable.add(maxTimeSickPanel);


		// slider & text for infectionDistance
		JPanel infectionDistancePanel = new JPanel();
		infectionDistancePanel.setVisible(true);
		infectionDistancePanel.setBounds(0, 0, 0, 0);
		infectionDistancePanel.setBackground(Color.white);
		
		infectionDistanceSliderText = new JLabel();
		infectionDistanceSliderText.setBounds(1, 0, 100, 100);
		infectionDistanceSliderText.setVisible(true);
		infectionDistanceSliderText.setText("infectionDistance: " + Variables.infectionDistance);

		JSlider infectionDistanceSlider = new JSlider(0, 30, Variables.infectionDistance);
		infectionDistanceSlider.addChangeListener((e) -> {
			Variables.infectionDistance = infectionDistanceSlider.getValue();
			infectionDistanceSliderText.setText("infectionDistance: " + infectionDistanceSlider.getValue());
		});
		infectionDistanceSlider.setBounds(0, 20, 100, 100);
		infectionDistanceSlider.setVisible(true);
		
		infectionDistancePanel.add(infectionDistanceSlider);
		infectionDistancePanel.add(infectionDistanceSliderText);
		SliderLable.add(infectionDistancePanel);


		// TextInput for infectionRisk
		JPanel infectionRiskPanel = new JPanel();
		infectionRiskPanel.setVisible(true);
		infectionRiskPanel.setBounds(0, 0, 0, 0);
		infectionRiskPanel.setBackground(Color.green);
		
		infectionRiskText = new JLabel();
		infectionRiskText.setBounds(1, 0, 100, 100);
		infectionRiskText.setVisible(true);
		infectionRiskText.setText("infectionRisk in %: " + Variables.infectionRisk);
		

		JTextField infectionRiskInput = new JTextField(6);
		infectionRiskInput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("enter pressed");
				infectionRiskText.setText("infectionRisk: " + infectionRiskInput.getText() + "%");
				Variables.infectionRisk = Double.parseDouble(infectionRiskInput.getText());
			}
		});
		infectionRiskInput.setVisible(true);
		
		infectionRiskPanel.add(infectionRiskText);
		infectionRiskPanel.add(infectionRiskInput);
		SliderLable.add(infectionRiskPanel);


		// TextInput for mortality
		JPanel mortalityPanel = new JPanel();
		mortalityPanel.setVisible(true);
		mortalityPanel.setBounds(0, 0, 0, 0);
		mortalityPanel.setBackground(Color.orange);
		
		mortalityText = new JLabel();
		mortalityText.setBounds(1, 0, 100, 100);
		mortalityText.setVisible(true);
		mortalityText.setText("mortality in %: " + Variables.mortality);
		

		JTextField mortalityInput = new JTextField(6);
		mortalityInput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("enter pressed");
				mortalityText.setText("mortality: " + mortalityInput.getText()+ "%");
				Variables.mortality = Double.parseDouble(mortalityInput.getText());

			}
		});
		mortalityInput.setVisible(true);
		mortalityPanel.add(mortalityText);
		mortalityPanel.add(mortalityInput);
		SliderLable.add(mortalityPanel);


		frame.add(SliderLable);
	}
}
