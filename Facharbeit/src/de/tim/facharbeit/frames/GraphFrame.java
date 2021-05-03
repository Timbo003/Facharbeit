package de.tim.facharbeit.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.graph.graphStructure;

public class GraphFrame extends JPanel {

	private JFrame frame;
	public static GraphFrame instance;
	
	public GraphFrame() {
		instance = this;
        frame = new JFrame("Graph");									
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
		for (graphStructure graphStructures : Main.graphStructures) {
			graphStructures.draw(graphics);
		}
		
	}
}
