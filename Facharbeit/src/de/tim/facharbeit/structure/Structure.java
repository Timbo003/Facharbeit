package de.tim.facharbeit.structure;

import java.awt.Graphics;

public abstract class Structure {

	protected Point point;			//ist vererbbar	
	protected int height;
	protected int width;


    public Structure(Point point, int width, int height) {
        this.point = point;
        this.width = width;
        this.height = height;
    }
    

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
	public int getX() {
		return point.getX();
	}
	
	public int getY() {
		return point.getY();
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
    public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

    public abstract void draw(Graphics graphics);  
}
