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
	
	@Override
	public String toString() {
		return "x: " + x + " y: " + y; 
	}
	
	public int distance(Point p) {
		if (p.x == this.x) {
			return Math.abs(p.y - this.y);
		} else  if (p.y == this.y) {
			return Math.abs(p.x - this.x);
		}
		System.err.println("Invalid disance");
		return 0;
	}
}
