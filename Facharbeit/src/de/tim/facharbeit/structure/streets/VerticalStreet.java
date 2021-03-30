package de.tim.facharbeit.structure.streets;

import de.tim.facharbeit.structure.Point;

public class VerticalStreet extends Street {
	
	private Point point;
	private int length;
	
	public VerticalStreet(Point point, int length) {													//erstellt die Straße
		super(point.getX() - Street.size/2, point.getY() - Street.size/2, Street.size, length);
		this.point = point;
		this.length = length;
	}

	@Override
	public boolean checkPoint(Point point) {															//checkt ob ein Punkt auf der Straße liegt
		if (point.getX() == this.point.getX()) {
			if (point.getY() > this.point.getY() && point.getY() < this.point.getY() + length) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Point getDistance(int distance) {															//gibt uns einen Punkt, welcher auf der Mittelline der Straße liegt
		return new Point(this.point.getX(), this.point.getY() + distance);								
	}

	
}
