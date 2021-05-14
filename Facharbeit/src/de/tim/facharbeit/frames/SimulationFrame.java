package de.tim.facharbeit.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.structure.Block;
import de.tim.facharbeit.structure.Garden;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Human;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.manager.HumanManager;
import de.tim.facharbeit.structure.streets.Street;

public class SimulationFrame extends JPanel {

	private JFrame frame;
	private static JPanel controllPanel;
	private static JPanel varPanel;
	private static JPanel sliderPanel;
	private static JPanel buttonPanel;
	private static JPanel animationSpeedPanel;
	private static JPanel maskPanel;
	private static JPanel p1;
	private static JPanel p2;
	private static JPanel p3;

	private static Font varFont = new Font("Arial", Font.PLAIN, 20);

	private static JLabel infectedLable = new JLabel();
	private static JLabel imuneLable = new JLabel();
	private static JLabel healthyLable = new JLabel();
	private static JLabel deadLable = new JLabel();
	private static JLabel dateLable = new JLabel();
	private static JLabel notDead = new JLabel();
	

	private static JButton maskButton = new JButton();

	private static JSlider animationSpeedSlider = new JSlider(1, 10, Variables.animationSpeed);
	private static JLabel animationSpeedSliderText = new JLabel();

	private static JSlider maskSlider = new JSlider(0, 100, Variables.wearingMask);
	private static JLabel maskSliderText = new JLabel();
	
	private static JSlider allowedDistanceSlider = new JSlider(50, 1000, Variables.allowedDistance);
	private static JLabel allowedDistanceSliderText = new JLabel();

	public static SimulationFrame instance;
	public static Color buttonColor = new Color(230, 239, 244);

	public SimulationFrame() {
		instance = this;
		frame = new JFrame("Simulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setPreferredSize(Variables.screenSize);
		super.setBounds(0, 0, Variables.screenSize.width, Variables.screenSize.height - 230);
		super.setBackground(Color.WHITE);
		super.setLayout(null);
		frame.setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(this);

		setupSimulationFrame();

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void setupSimulationFrame() { // Panel for Buttens etc.
		controllPanel = new JPanel();
		controllPanel.setBounds(250, Variables.screenSize.height - 230, Variables.screenSize.width - 500, 190);
//		controllPanel.setBackground(new Color(114, 118, 125));
		controllPanel.setVisible(true);
		controllPanel.setLayout(null);

		setupButtonPanel();
		setupSliderPanel();
		setupVarPanel();
		frame.add(controllPanel);
	}

	private void setupButtonPanel() {
		// Stop Button
		buttonPanel = new JPanel();
		buttonPanel.setBounds(controllPanel.getWidth() / 2 - 150, 0, 300, controllPanel.getHeight());
//		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setVisible(true);
		buttonPanel.setLayout(null);

		JButton stopButton = new JButton("Halt Stop");
		stopButton.setFont(varFont);
		stopButton.addActionListener((e) -> {
			if (Variables.stop) {
				stopButton.setText("Stop");
				Variables.stop = false;
			} else {
				stopButton.setText("Resume");
				Variables.stop = true;
			}
		});
		stopButton.setBounds(0, 0, buttonPanel.getWidth(), buttonPanel.getHeight() / 2 - 10);
		stopButton.setVisible(true);
		stopButton.setBackground(buttonColor);

		// Graph Button
		JButton showGraphButton = new JButton("Show Graph");
		showGraphButton.setFont(varFont);
		showGraphButton.addActionListener((e) -> {
			Main.switchToGraph();
			System.out.println("Graph");
		});
		showGraphButton.setBounds(0, buttonPanel.getHeight() / 2, buttonPanel.getWidth(),
				buttonPanel.getHeight() / 2 - 10);
		showGraphButton.setVisible(true);
		showGraphButton.setBackground(buttonColor);

		buttonPanel.add(showGraphButton);
		buttonPanel.add(stopButton);
		controllPanel.add(buttonPanel);
		HumanManager.refrechHumanHealthVar();
	}

	private void setupSliderPanel() {
		sliderPanel = new JPanel();
		sliderPanel.setBounds(controllPanel.getWidth() - 520, 0, 500, controllPanel.getHeight() - 10);
//		sliderPanel.setBackground(Color.LIGHT_GRAY);
		sliderPanel.setVisible(true);
		sliderPanel.setLayout(null);

		// slider & text for animationSpeed
		animationSpeedPanel = new JPanel();
		animationSpeedPanel.setBounds(5, 10, sliderPanel.getWidth() - 10, 30);
//		animationSpeedPanel.setBackground(Color.red);
		animationSpeedPanel.setVisible(true);
		animationSpeedPanel.setLayout(null);

		animationSpeedSliderText.setBounds(animationSpeedPanel.getWidth() - 190,
				animationSpeedPanel.getHeight() / 2 - 10, 200, 20);
		animationSpeedSliderText.setVisible(true);
		animationSpeedSliderText.setText("Animation Speed: " + Variables.animationSpeed);
		animationSpeedSliderText.setFont(varFont);

		animationSpeedSlider.addChangeListener((e) -> {
			Variables.animationSpeed = animationSpeedSlider.getValue();
			animationSpeedSliderText.setText("Animation Speed: " + animationSpeedSlider.getValue());
		});
		animationSpeedSlider.setBounds(0, -5, animationSpeedPanel.getWidth() - 200, 40);
		animationSpeedSlider.setVisible(true);

		animationSpeedPanel.add(animationSpeedSliderText);
		animationSpeedPanel.add(animationSpeedSlider);
		sliderPanel.add(animationSpeedPanel);
		controllPanel.add(sliderPanel);

		// Slider & Text & Button for Masks
		maskPanel = new JPanel();
		maskPanel.setBounds(5, 50, sliderPanel.getWidth() - 10, 130);
//		maskPanel.setBackground(Color.orange);
		maskPanel.setLayout(null);
		maskPanel.setVisible(true);

		maskSliderText.setBounds(maskPanel.getWidth() - 190, 5, 200, 20);
		maskSliderText.setVisible(true);
		maskSliderText.setText(maskSlider.getValue() + "% wearing masks");
		maskSliderText.setFont(varFont);

		maskSlider.addChangeListener((e) -> {
			if (Variables.maskButtonPressed == false) {
				Variables.wearingMask = maskSlider.getValue();
				maskSliderText.setText(maskSlider.getValue() + "% wearing a mask");
				Variables.howManyAreWearingMasks = (int) ((Variables.wearingMask * 0.01) * Variables.alive);
				maskButton.setText(Variables.howManyAreWearingMasks + " will wear a mask");
			}
		});
		maskSlider.setBounds(0, -5, maskPanel.getWidth() - 200, 40);
		maskSlider.setVisible(true);

		maskPanel.add(maskSlider);
		maskPanel.add(maskSliderText);

		maskButton = new JButton(Variables.howManyAreWearingMasks + " will wear a mask");
		maskButton.setFont(varFont);
		maskButton.addActionListener((e) -> {
			if (Variables.maskButtonPressed) {
				Variables.maskButtonPressed = false;
				maskButton.setText(Variables.howManyAreWearingMasks + " will wear a mask");
				maskSliderText.setText(Variables.howManyAreWearingMasks + " will wear  a mask");
				for (Human human : Main.getAllLifingHumans()) {
					human.isWearingMask = false;
				}
				
			} else {
				maskSliderText.setText("locked");
				maskButton.setText("off");
				Variables.maskButtonPressed = true;
				maskButton.setText(Variables.howManyAreWearingMasks + " are wearing a mask");
				
				for (int i = 0; i < Variables.howManyAreWearingMasks; i++) {
					Main.getAllLifingHumans().get(i).isWearingMask = true;
				}
			}
		});
		maskButton.setBounds(5, maskPanel.getHeight() - 85, maskPanel.getWidth() - 10, 85);
		maskButton.setVisible(true);
		maskButton.setBackground(buttonColor);

		maskPanel.add(maskButton);

		sliderPanel.add(maskPanel);
		controllPanel.add(sliderPanel);

	}

	private void setupVarPanel() {
		varPanel = new JPanel();
		varPanel.setBounds(20, 0, 500, controllPanel.getHeight());
//		varPanel.setBackground(Color.LIGHT_GRAY);
		varPanel.setVisible(true);
		varPanel.setLayout(null);

		p1 = new JPanel();
		p1.setBounds(0, 0, varPanel.getWidth(), varPanel.getHeight() / 2 - 20);
//		p1.setBackground(Color.blue);
		p1.setVisible(true);
		p1.setLayout(null);

		// infected
		infectedLable.setBounds(10 + p1.getWidth() / 3 * 0, -20, 200, 100);
		infectedLable.setVisible(true);
		infectedLable.setText("infected: " + Variables.infected);
		infectedLable.setFont(varFont);
		p1.add(infectedLable);

		// imune
		imuneLable.setBounds(10 + p1.getWidth() / 3 * 1, -20, 200, 100);
		imuneLable.setVisible(true);
		imuneLable.setText("imune: " + Variables.imune);
		imuneLable.setFont(varFont);
		p1.add(imuneLable);

		// healthy
		healthyLable.setBounds(10 + p1.getWidth() / 3 * 2, -20, 200, 100);
		healthyLable.setVisible(true);
		healthyLable.setText("healthy: " + Variables.healthy);
		healthyLable.setFont(varFont);
		p1.add(healthyLable);

		p2 = new JPanel();
		p2.setBounds(0, varPanel.getHeight() / 2-20, varPanel.getWidth(), varPanel.getHeight() / 2 - 20);
//		p2.setBackground(Color.cyan);
		p2.setVisible(true);
		p2.setLayout(null);

		// dead
		deadLable.setBounds(10 + p2.getWidth() / 3 * 0, -20, 200, 100);
		deadLable.setVisible(true);
		deadLable.setText("dead: " + Variables.dead);
		deadLable.setFont(varFont);
		p2.add(deadLable);

		// gesHumans
		notDead.setBounds(10 + p2.getWidth() / 3 * 1, -20, 200, 100);
		notDead.setVisible(true);
		notDead.setText("alive: " + Main.getAllLifingHumans().size());
		notDead.setFont(varFont);
		p2.add(notDead);

		// date
		dateLable.setBounds(10 + p2.getWidth() / 3 * 2, -20, 200, 100);
		dateLable.setVisible(true);
		dateLable.setText("date: " + (Variables.days.size()));
		dateLable.setFont(varFont);
		p2.add(dateLable);
		
		p3 = new JPanel();
		p3.setBounds(0, varPanel.getHeight() / 2-20 + varPanel.getHeight() / 2 - 20, varPanel.getWidth(), 40);
//		p3.setBackground(Color.orange);
		p3.setVisible(true);
		p3.setLayout(null);
		
		allowedDistanceSliderText.setBounds(animationSpeedPanel.getWidth() - 200,
				animationSpeedPanel.getHeight() / 2 - 10, 210, 20);
		allowedDistanceSliderText.setVisible(true);
		allowedDistanceSliderText.setText("allowed Distance: " + Variables.allowedDistance);
		allowedDistanceSliderText.setFont(varFont);

		allowedDistanceSlider.addChangeListener((e) -> {
			Variables.allowedDistance = allowedDistanceSlider.getValue();
			allowedDistanceSliderText.setText("allowed Distance: " + Variables.allowedDistance);
		});
		allowedDistanceSlider.setBounds(0, -5, p3.getWidth() - 220, 40);
		allowedDistanceSlider.setVisible(true);
		
		p3.add(allowedDistanceSlider);
		p3.add(allowedDistanceSliderText);

		varPanel.add(p1);
		varPanel.add(p2);
		varPanel.add(p3);
		controllPanel.add(varPanel);
	}

	public static void updateDate() {
		dateLable.setText("date: " + (Variables.days.size()));
	}

	public static void updateText() {
		HumanManager.refrechHumanHealthVar();

		notDead.setText("alive: " + Variables.alive);
		infectedLable.setText("infected: " + Variables.infected);
		imuneLable.setText("imune: " + Variables.imune);
		healthyLable.setText("healthy: " + Variables.healthy);
		deadLable.setText("dead: " + Variables.dead);
	}

	public void update() {
		super.repaint();
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, Variables.screenSize.width, Variables.screenSize.height - 230);

		for (Structure structure : Main.structures) {
			if (structure instanceof Street) {
				structure.draw(graphics);
			}
		}
		for (Structure structure : Main.structures) {
			if (structure instanceof Block) {
				structure.draw(graphics);
			}
		}
		for (Structure structure : Main.structures) {
			if (structure instanceof House || structure instanceof Garden) {
				structure.draw(graphics);
			}
		}
		for (Structure structure : Main.structures) {
			if (!(structure instanceof Block) || !(structure instanceof House) || !(structure instanceof Garden)
					|| !(structure instanceof Street) || !(structure instanceof Human)) {
				structure.draw(graphics);
			}
		}
		for (Structure structure : Main.structures) {
			if (structure instanceof Human) {
				structure.draw(graphics);
			}
		}
	}
}
