package de.tim.facharbeit.frames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.graph.GraphStructure;

public class GraphFrame extends JPanel {

	private JFrame frame;
	public static GraphFrame instance;
	
	public GraphFrame() {
		instance = this;
        frame = new JFrame("Graph");									
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
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
		//System.out.println("drawing");
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 1500, 750);
		graphics.setColor(Color.BLACK);
		
		Graphics2D g2 = (Graphics2D) graphics;
		g2.setStroke(new BasicStroke(4));
		
		g2.drawLine(100,  50, 100, 650);
		g2.drawLine(100,  650, 1400, 650);
		
		for (GraphStructure graphStructure : Main.graphStructures) {
			graphStructure.draw(graphics);
		}
		
	}
}