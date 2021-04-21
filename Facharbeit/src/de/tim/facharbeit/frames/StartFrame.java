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
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;

public class StartFrame {
	private static JFrame frame;

	public StartFrame() {
		frame = new JFrame("StartScreen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setPreferredSize(new Dimension(500, 500));
		frame.setLayout(null);

		setupStartFrame();

		// ENDE nichts mehr danach
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void setupStartFrame() {

		// Start Button
		JButton startSimButton = new JButton("Start Sim");
		startSimButton.addActionListener((e) -> {
			Main.switchToSim();
			frame.dispose();
			System.out.println("press");
		});
		startSimButton.setBounds(50, frame.getHeight() - 150, frame.getWidth() - 100, 100);
		startSimButton.setVisible(true);
		frame.add(startSimButton);

		// SliderLabele
		JPanel SliderLable = new JPanel();
		SliderLable.setVisible(true);
		SliderLable.setBounds(50, 25, frame.getWidth() - 100, 300);
		SliderLable.setBackground(Color.LIGHT_GRAY);

		// slider & text for Streets
		JLabel streetSliderText = new JLabel();
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
		SliderLable.add(streetSlider);
		SliderLable.add(streetSliderText);

		// slider & text for Infected
		JLabel infectedSliderText = new JLabel();
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
		SliderLable.add(infectedSlider);
		SliderLable.add(infectedSliderText);

		// slider & text for Imune
		JLabel imuneSliderText = new JLabel();
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
		SliderLable.add(imuneSlider);
		SliderLable.add(imuneSliderText);
		
		
		// slider & text for humanCount
		JLabel humanCountSliderText = new JLabel();
		humanCountSliderText.setBounds(0, 0, 100, 100);
		humanCountSliderText.setVisible(true);
		humanCountSliderText.setText("Humans: " + Variables.totalHumanCounter);

		JSlider humanCountSlider = new JSlider(1, 300, Variables.totalHumanCounter);
		humanCountSlider.addChangeListener((e) -> {
			Variables.totalHumanCounter = humanCountSlider.getValue();
			humanCountSliderText.setText("Humans: " + humanCountSlider.getValue());
		});
		humanCountSlider.setBounds(0, 20, 100, 100);
		humanCountSlider.setVisible(true);
		SliderLable.add(humanCountSlider);
		SliderLable.add(humanCountSliderText);
		
		
		// slider & text for maxHumansInHome
		JLabel maxHumansInHomeSliderText = new JLabel();
		maxHumansInHomeSliderText.setBounds(0, 0, 100, 100);
		maxHumansInHomeSliderText.setVisible(true);
		maxHumansInHomeSliderText.setText("maxHumansInHome: " + Variables.maxHumansInHome);

		JSlider maxHumansInHomeSlider = new JSlider(1, 10, Variables.maxHumansInHome);
		maxHumansInHomeSlider.addChangeListener((e) -> {
			Variables.maxHumansInHome = maxHumansInHomeSlider.getValue();
			maxHumansInHomeSliderText.setText("maxHumansInHome: " + maxHumansInHomeSlider.getValue());
		});
		maxHumansInHomeSlider.setBounds(0, 20, 100, 100);
		maxHumansInHomeSlider.setVisible(true);
		SliderLable.add(maxHumansInHomeSlider);
		SliderLable.add(maxHumansInHomeSliderText);

		frame.add(SliderLable);
	}
}
