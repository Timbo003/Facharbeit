package de.tim.facharbeit.graph;

import java.awt.Color;
import java.awt.Graphics;

import de.tim.facharbeit.structure.Health;

public class GraphPoint extends GraphStructure {

	Health health;
	private int x;
	private int y;

	public GraphPoint(int x, int y, Health health) {
		this.x = x;
		this.y = y;
		this.health = health;
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
		switch (health) {
		case HEALTHY:
			graphics.setColor(Color.green);
			break;
		case INFECTED:
			graphics.setColor(Color.red);
			break;
		case IMUNE:
			graphics.setColor(Color.blue);
			break;
		case DEAD:
			graphics.setColor(Color.gray);
			break;
		default:
			break;
		}
		graphics.fillOval(x - 4, y - 4, 8, 8);
	}

}
