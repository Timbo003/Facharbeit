package de.tim.facharbeit.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Iterator;

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
import de.tim.facharbeit.structure.streets.Street;

public class SimulationFrame extends JPanel {

	private JFrame frame;
	private static JPanel controllPanel;

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
		controllPanel.setBackground(Color.RED);
		controllPanel.setVisible(true);
		frame.add(controllPanel);
		setupControllPanel();
	}

	private void setupControllPanel() {
		
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
