package de.tim.facharbeit.frames;

import java.awt.Color;
import java.awt.Dimension;
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

	private static JLabel infectedLable = new JLabel();
	private static JLabel imuneLable = new JLabel();
	private static JLabel healthyLable = new JLabel();
	private static JLabel deadLable = new JLabel();
	private static JLabel dateLable = new JLabel();
	private static JSlider animationSpeedSlider = new JSlider(1, 10, Variables.animationSpeed);
	private static JLabel animationSpeedSliderText = new JLabel();

	public static SimulationFrame instance;

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
		controllPanel.setBounds(20, Variables.screenSize.height - 230, Variables.screenSize.width - 40, 190);
		controllPanel.setBackground(new Color(114, 118, 125));
		controllPanel.setVisible(true);
		setupControllPanel();
	}

	private void setupControllPanel() {
		// Stop Button
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
		stopButton.setBounds(50, frame.getHeight() - 150, 175, 100);
		stopButton.setVisible(true);
		controllPanel.add(stopButton);

		// Graph Button
		JButton showGraphButton = new JButton("Show Graph");
		showGraphButton.addActionListener((e) -> {
			Main.switchToGraph();
			System.out.println("Graph");
		});
		showGraphButton.setBounds(275, frame.getHeight() - 150, 175, 100);
		showGraphButton.setVisible(true);
		controllPanel.add(showGraphButton);

		HumanManager.refrechHumanHealthVar();
		
		setupVarPanel();
		
	}

	private void setupVarPanel() {
		varPanel = new JPanel();
		varPanel.setBounds(20,20, 200, 200);
		varPanel.setBackground(Color.LIGHT_GRAY);
		varPanel.setVisible(true);

		// infected
		infectedLable.setBounds(0, 0, 100, 100);
		infectedLable.setVisible(true);
		infectedLable.setText("infected: " + Variables.infected);
		varPanel.add(infectedLable);

		// imune
		imuneLable.setBounds(0, 0, 100, 100);
		imuneLable.setVisible(true);
		imuneLable.setText("imune: " + Variables.imune);
		varPanel.add(imuneLable);

		// imune
		healthyLable.setBounds(0, 0, 100, 100);
		healthyLable.setVisible(true);
		healthyLable.setText("healthy: " + Variables.healthy);
		varPanel.add(healthyLable);

		// imune
		deadLable.setBounds(0, 0, 100, 100);
		deadLable.setVisible(true);
		deadLable.setText("dead: " + Variables.dead);
		varPanel.add(deadLable);

		// imune
		dateLable.setBounds(0, 0, 100, 100);
		dateLable.setVisible(true);
		dateLable.setText("date: " + (Variables.days.size()));
		varPanel.add(dateLable);

		// slider & text for animationSpeed
		animationSpeedSliderText.setBounds(0, 0, 100, 100);
		animationSpeedSliderText.setVisible(true);
		animationSpeedSliderText.setText("Animation Speed: " + Variables.animationSpeed);

		animationSpeedSlider.addChangeListener((e) -> {
			Variables.animationSpeed = animationSpeedSlider.getValue();
			animationSpeedSliderText.setText("Animation Speed: " + animationSpeedSlider.getValue());
		});
		animationSpeedSlider.setBounds(0, 20, controllPanel.getWidth(), 200);
		animationSpeedSlider.setVisible(true);
		varPanel.add(animationSpeedSlider);
		varPanel.add(animationSpeedSliderText);

		controllPanel.add(varPanel);
		frame.add(controllPanel);
	}

	public static void updateDate() {
		dateLable.setText("date: " + (Variables.days.size()));
	}

	public static void updateText() {
		HumanManager.refrechHumanHealthVar();

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
