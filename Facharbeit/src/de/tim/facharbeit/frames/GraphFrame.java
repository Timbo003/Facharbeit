package de.tim.facharbeit.frames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.graph.GraphManager;
import de.tim.facharbeit.graph.GraphStructure;

public class GraphFrame extends JPanel {

	private static JFrame frame;
	public static GraphFrame instance;
	public static JButton saveButton;
	
	
	
	public GraphFrame() {
		instance = this;
        frame = new JFrame("Graph");									
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		
		g2.drawLine(100,  50, 100, 620);			//Y Achse
		g2.drawLine(100,  620, 1400, 620);			//X Achse
		
		for (GraphStructure graphStructure : Main.graphStructures) {
			graphStructure.draw(graphics);
		}
		
		GraphManager.setUpNamingOnX((Graphics2D) graphics);
		GraphManager.setUpNamingOnY((Graphics2D) graphics);
		
	}
}
