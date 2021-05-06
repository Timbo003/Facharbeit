package de.tim.facharbeit.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.structure.manager.HumanManager;

public class ScoreFrame {

	private static JFrame frame;

	private static JPanel varPanel = new JPanel();

	private static JLabel infectedLable = new JLabel();
	private static JLabel imuneLable = new JLabel();
	private static JLabel healthyLable = new JLabel();
	private static JLabel deadLable = new JLabel();
	private static JLabel dateLable = new JLabel();
	private static JSlider animationSpeedSlider = new JSlider(1, 10, Variables.animationSpeed);
	private static JLabel animationSpeedSliderText = new JLabel();

	public ScoreFrame() {
		frame = new JFrame("ScoreScreen");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setPreferredSize(new Dimension(500, 500));
		frame.setLayout(null);

		setupScoreFrame();

		// ENDE nichts mehr danach
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void setupScoreFrame() {
		// Start Button
				JButton startSimButton = new JButton("Show Graph");
				startSimButton.addActionListener((e) -> {
					Main.switchToGraph();
					System.out.println("Graph");
				});
				startSimButton.setBounds(50, frame.getHeight() - 150, frame.getWidth() - 100, 100);
				startSimButton.setVisible(true);
				frame.add(startSimButton);
		
		// varPanel
		varPanel.setVisible(true);
		varPanel.setBounds(50, 25, frame.getWidth() - 100, 300);
		varPanel.setBackground(Color.LIGHT_GRAY);

		HumanManager.refrechHumanHealthVar();

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
			animationSpeedSliderText.setText("Animation Speed:: " + animationSpeedSlider.getValue());
		});
		animationSpeedSlider.setBounds(0, 20, varPanel.getWidth(), 200);
		animationSpeedSlider.setVisible(true);
		varPanel.add(animationSpeedSlider);
		varPanel.add(animationSpeedSliderText);

		frame.add(varPanel);
	}

	public static void updateDate() {
		dateLable.setText("date: " + (Variables.days.size()));
	}

	public static void update() {
		HumanManager.refrechHumanHealthVar();

		infectedLable.setText("infected: " + Variables.infected);
		imuneLable.setText("imune: " + Variables.imune);
		healthyLable.setText("healthy: " + Variables.healthy);
		deadLable.setText("dead: " + Variables.dead);
	}
}
