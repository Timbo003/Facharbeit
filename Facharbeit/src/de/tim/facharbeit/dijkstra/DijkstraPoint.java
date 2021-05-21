package de.tim.facharbeit.dijkstra;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

	private boolean marked = false;
	protected int distanceFromStart = Integer.MAX_VALUE;
	protected DijkstraPoint last;

	protected int distanceToUp;
	protected int distanceToLeft;
	protected int distanceToRight;
	protected int distanceToDown;

	public boolean active;
	
	public DijkstraPoint(Point point) {
		super(point, 10, 10);
		this.point = point;
		points.add(this);
	}

	public void setupDistances() {
		if (up != null) {
			distanceToUp = point.getY() - up.point.getY();
		} else {
			distanceToUp = Integer.MAX_VALUE;
		}
		if (down != null) {
			distanceToDown = down.point.getY() - point.getY();
		} else {
			distanceToDown = Integer.MAX_VALUE;
		}
		if (left != null) {
			distanceToLeft = point.getX() - left.point.getX();
		} else {
			distanceToLeft = Integer.MAX_VALUE;
		}
		if (right != null) {
			distanceToRight = right.point.getX() - point.getX();
		} else {
			distanceToRight = Integer.MAX_VALUE;
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

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}
	
	
	public static void setColor(Color color) {
		
	}

	@Override
	public void draw(Graphics graphics) {
		if (!active) return;
		graphics.setColor(Color.RED);
		graphics.fillRect(getX() - 10, getY() - 10, 20, 20);
		graphics.setColor(Color.ORANGE);
		if (up != null) {
			if (up.equals(last)) graphics.setColor(Color.GREEN);
			graphics.fillRect(getX() - 5, getY() - 20, 10, 20);
		}
		graphics.setColor(Color.ORANGE);
		if (down != null) {
			if (down.equals(last)) graphics.setColor(Color.GREEN);
			graphics.fillRect(getX() - 5, getY(), 10, 20);
		}
		graphics.setColor(Color.ORANGE);
		if (left != null) {
			if (left.equals(last)) graphics.setColor(Color.GREEN);
			graphics.fillRect(getX() - 20, getY() - 5, 20, 10);
		}
		graphics.setColor(Color.ORANGE);
		if (right != null) {
			if (right.equals(last)) graphics.setColor(Color.GREEN);
			graphics.fillRect(getX(), getY() - 5, 20, 10);
		}
		Random random = new Random();
		graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		int offset = random.nextInt(5) - 2;

		if (right != null && right.active)
			graphics.drawLine(getX() + offset, getY() + offset, right.getX() + offset, right.getY() + offset);	
		if (down != null && down.active)
			graphics.drawLine(getX() + offset, getY() + offset, down.getX() + offset, down.getY() + offset);		
	}

	@Override
	public String toString() {
		return "x: " + point.getX() + " y: " + point.getY();
	}

	public boolean checkForDublicate(DijkstraPoint point) {
		return this.getX() == point.getX() && this.getY() == point.getY();
	}
}