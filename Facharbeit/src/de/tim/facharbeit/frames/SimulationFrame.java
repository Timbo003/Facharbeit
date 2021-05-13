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
	private static JPanel p1;
	private static JPanel p2;

	private static Font varFont = new Font("Arial", Font.PLAIN, 20);

	private static JLabel infectedLable = new JLabel();
	private static JLabel imuneLable = new JLabel();
	private static JLabel healthyLable = new JLabel();
	private static JLabel deadLable = new JLabel();
	private static JLabel dateLable = new JLabel();
	private static JLabel notDead = new JLabel();

	private static JSlider animationSpeedSlider = new JSlider(1, 10, Variables.animationSpeed);
	private static JLabel animationSpeedSliderText = new JLabel();

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
		sliderPanel.setBounds(controllPanel.getWidth() - 520, 10, 500, controllPanel.getHeight() - 20);
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
		animationSpeedSlider.setBounds(0, -5,
				animationSpeedPanel.getWidth() - 200, 40);
		animationSpeedSlider.setVisible(true);

		animationSpeedPanel.add(animationSpeedSliderText);
		animationSpeedPanel.add(animationSpeedSlider);
		sliderPanel.add(animationSpeedPanel);
		controllPanel.add(sliderPanel);
	}

	private void setupVarPanel() {
		varPanel = new JPanel();
		varPanel.setBounds(20, 10, 500, controllPanel.getHeight() - 20);
//		varPanel.setBackground(Color.LIGHT_GRAY);
		varPanel.setVisible(true);
		varPanel.setLayout(null);

		p1 = new JPanel();
		p1.setBounds(0, 10, varPanel.getWidth(), varPanel.getHeight() / 2 - 20);
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
		p2.setBounds(0, varPanel.getHeight() / 2, varPanel.getWidth(), varPanel.getHeight() / 2 - 20);
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

		varPanel.add(p1);
		varPanel.add(p2);
		controllPanel.add(varPanel);
	}

	public static void updateDate() {
		dateLable.setText("date: " + (Variables.days.size()));
	}

	public static void updateText() {
		HumanManager.refrechHumanHealthVar();

		notDead.setText("alive: " + Main.getAllLifingHumans().size());
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
