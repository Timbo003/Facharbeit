package de.tim.facharbeit.structure;

public class Point {

	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
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

	public int pointDistance(Point point) {
		if (this.x == point.x) {
			return Math.abs(point.y - this.y);
		} else if (this.y == point.y) {
			return Math.abs(point.x - this.x);
		}
		System.err.println("pointDistance not working");
		return 0;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point point = (Point) obj;
			return this.x == point.x && this.y == point.y;
		}
		return false;
	}

	@Override
	public String toString() {
		return "x: " + x + " y: " + y;
	}
}
