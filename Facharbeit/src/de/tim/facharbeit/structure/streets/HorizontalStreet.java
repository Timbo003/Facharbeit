package de.tim.facharbeit.structure.streets;

import de.tim.facharbeit.structure.Point;

public class HorizontalStreet extends Street{
	
	private Point point;
	private int length;

	protected HorizontalStreet(Point point, int length) {
		super(point.getX() + Street.size/2, point.getY() - Street.size/2, length, Street.size);
		this.point = point;
		this.length = length;
	}

	@Override
	public boolean checkPoint(Point point) {															//checkt ob ein Punkt auf der Straße liegt
		if (point.getY() == this.point.getY()) {
			if (point.getX() > this.point.getX() && point.getX() < this.point.getX() + length) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Point getDistance(int distance) {
		return new Point(this.point.getX() + distance, this.point.getY() );
	}

}
