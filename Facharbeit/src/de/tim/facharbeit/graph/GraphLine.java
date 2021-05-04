package de.tim.facharbeit.graph;

import java.awt.Color;
import java.awt.Graphics;
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
		graphics.setColor(color);
		for (int i = 1; i < points.size(); i++) {
			GraphPoint a = points.get(i-1);
			GraphPoint b = points.get(i);
			//System.out.println("form : " + a + "  to : " + b);
			graphics.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
			graphics.drawLine(a.getX()-1, a.getY()-1, b.getX()-1, b.getY()-1);
			graphics.drawLine(a.getX()+1, a.getY()+1, b.getX()+1, b.getY()+1);
		}
	}

}
