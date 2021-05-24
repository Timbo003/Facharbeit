package de.tim.facharbeit.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


public class GraphLine extends GraphStructure {

	private List<GraphPoint> points = new ArrayList<GraphPoint>();				//punkte die zu einer linie verbunden werden müssen
	public Color color;															//Farbe
	
	
	public GraphLine(List<GraphPoint> points,Color color) {	
		this.color = color;
		this.points = points;
	}

	@Override
	public void draw(Graphics graphics) {
		double difference = 1500D / points.size();
		int iterator = 1;
		
		graphics.setColor(color);
		for (int i = 1; i < points.size(); i += iterator) {						//widerholen für jeden Punkt
			GraphPoint a = points.get(i-1);										//vorheriger Punkt
			GraphPoint b = points.get(i);										//momentaner Punkt
			
			Graphics2D g2 = (Graphics2D) graphics;
			g2.setStroke(new BasicStroke(4));									//dicke der Linie
			g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());				//male eine linie von a nach b
		}
	}

}
