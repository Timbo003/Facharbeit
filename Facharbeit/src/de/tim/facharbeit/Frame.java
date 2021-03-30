package de.tim.facharbeit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tim.facharbeit.structure.Structure;


public class Frame extends JPanel {

	private JFrame frame;

	public Frame() {
        frame = new JFrame("Simulation");									
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
		frame.getContentPane().setPreferredSize(new Dimension(600,400));	
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
        for (Structure structure : Main.structures) {
            structure.draw(graphics);
        }
	}
}
