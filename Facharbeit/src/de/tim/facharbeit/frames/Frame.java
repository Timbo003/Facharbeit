package de.tim.facharbeit.frames;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.Block;
import de.tim.facharbeit.structure.Garden;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Human;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.streets.Street;


public class Frame extends JPanel {

	private JFrame frame;
	public static Frame instance;

	public Frame() {
		instance = this;
        frame = new JFrame("Simulation");									
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
		frame.getContentPane().setPreferredSize(new Dimension(1500,750));	
		super.setBackground(Color.WHITE);
		super.setLayout(null);
		frame.add(this);
		frame.pack();						
		frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

	public void update() {
		super.repaint();
		
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 1500, 750);	
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
			if (structure instanceof House || structure instanceof Garden ) {
				structure.draw(graphics);
			}
		}
		for (Structure structure : Main.structures) {
			if (!(structure instanceof Block) || !(structure instanceof House) || !(structure instanceof Garden) || !(structure instanceof Street) || !(structure instanceof Human)) {
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
