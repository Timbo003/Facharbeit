package de.tim.facharbeit.graph;

import java.awt.Color;
import java.awt.Graphics;

import de.tim.facharbeit.structure.Health;

public class GraphPoint extends GraphStructure {

	Color color;
	private int x;
	private int y;

	public GraphPoint(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "x: " + x + " y: " + y;
	}

	public void draw(Graphics graphics) {
		graphics.setColor(color);
		graphics.fillOval(x - 5, y - 5, 10, 10);
	}

}
