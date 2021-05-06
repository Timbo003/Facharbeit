package de.tim.facharbeit.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


public class GraphLine extends GraphStructure {

	private List<GraphPoint> points = new ArrayList<GraphPoint>();
	public Color color;
	
	
	public GraphLine(List<GraphPoint> points,Color color) {
		this.color = color;
		this.points = points;
	}

	@Override
	public void draw(Graphics graphics) {
		double difference = 1500D / points.size();
		int iterator = 1;
//		if (difference < GraphManager.minX) {
//			iterator = (int) (GraphManager.minX / difference) + 1;
//		}
		
		graphics.setColor(color);
		for (int i = 1; i < points.size(); i += iterator) {
			GraphPoint a = points.get(i-1);
			GraphPoint b = points.get(i);
			
			Graphics2D g2 = (Graphics2D) graphics;
			g2.setStroke(new BasicStroke(4));
			g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
			
			//System.out.println("form : " + a + "  to : " + b);
			//graphics.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
//			graphics.drawLine(a.getX()-1, a.getY()-1, b.getX()-1, b.getY()-1);
//			graphics.drawLine(a.getX()+1, a.getY()+1, b.getX()+1, b.getY()+1);
		}
	}

}
