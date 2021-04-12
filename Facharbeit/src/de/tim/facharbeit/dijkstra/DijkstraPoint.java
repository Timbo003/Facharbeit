package de.tim.facharbeit.dijkstra;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;

public class DijkstraPoint extends Structure {

	private static List<DijkstraPoint> points = new ArrayList<>();

	public Point point;
	
	protected DijkstraPoint up;
	protected DijkstraPoint left;
	protected DijkstraPoint right;
	protected DijkstraPoint down;

	protected boolean marked = false;
	protected int distanceFromStart;
	
	protected int distanceToUp;
	protected int distanceToLeft;
	protected int distanceToRight;
	protected int distanceToDown;

	public DijkstraPoint(Point point) {
		super(point, 10, 10);
		this.point = point;
		points.add(this);
		//Main.structures.add(this);
	}

	public void setupDistances() {
		if (up != null) {
			distanceToUp = point.getY() - up.point.getY();
		}else {
			distanceToUp = -1;
		}
		if (down != null) {
			distanceToDown = down.point.getY() - point.getY();
		}else {
			distanceToDown = -1;
		}
		if (left != null) {
			distanceToLeft = point.getX() - left.point.getX();
		}else {
			distanceToLeft = -1;
		}
		if (right != null) {
			distanceToRight = right.point.getX() - point.getX();
		}else {
			distanceToRight = -1;
		}
	}

	public DijkstraPoint getUp() {
		return up;
	}

	public void setUp(DijkstraPoint up) {
		this.up = up;
	}

	public DijkstraPoint getLeft() {
		return left;
	}

	public void setLeft(DijkstraPoint left) {
		this.left = left;
	}

	public DijkstraPoint getRight() {
		return right;
	}

	public void setRight(DijkstraPoint right) {
		this.right = right;
	}

	public DijkstraPoint getDown() {
		return down;
	}

	public void setDown(DijkstraPoint down) {
		this.down = down;
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.RED);
		graphics.fillRect(getX() - 10, getY() - 10, 20, 20);
		graphics.setColor(Color.ORANGE);
		if (up != null) {
			graphics.fillRect(getX() - 5, getY() - 20, 10, 20);
		}
		if (down != null) {
			graphics.fillRect(getX() - 5, getY(), 10, 20);
		}
		if (left != null) {
			graphics.fillRect(getX() - 20, getY() - 5, 20, 10);
		}
		if (right != null) {
			graphics.fillRect(getX(), getY() - 5, 20, 10);
		}
	}
	
	@Override
	public String toString() {
		return "x: " + point.getX() + " y: " + point.getY();
	}

}
