package de.tim.facharbeit.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

		frame.add(varPanel);
	}

	public void update() {
		HumanManager.refrechHumanHealthVar();

		infectedLable.setText("infected: " + Variables.infected);
		imuneLable.setText("imune: " + Variables.imune);
		healthyLable.setText("healthy: " + Variables.healthy);
		deadLable.setText("dead: " + Variables.dead);
	}
}
