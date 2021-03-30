package de.tim.facharbeit.structure.streets;

import de.tim.facharbeit.Frame;
import de.tim.facharbeit.structure.Point;

public class HorizontalStreet extends Street{
	

	private Point point;
	private int length;
	
	
	public HorizontalStreet(Point point) throws Exception {
		this(point, -1);
	}
	
	public HorizontalStreet(Point point, int length) throws Exception {
		super(point.getX() - Street.size/2, point.getY() - Street.size/2, length, Street.size);
		this.point = point;
		if (length < 0) {
			this.length = getLength();
			super.setWidth(this.length);
		} else {
			this.length = length;
		}
		for (Street street : allStreets) {
			if (!street.equals(this) && street.checkPoint(point) && street instanceof HorizontalStreet )  {
				throw new Exception("h: point already existing");
			}
		}
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

	@Override
	public int getLength() throws Exception {
		System.out.println("x: " + x + " y: " + y);
		for (int i = 1; i < 1000; i++) {
			for (Street street : Street.allStreets) {
				if (street instanceof VerticalStreet) {
					int x = this.point.getX() + i;
					int y = this.point.getY();
					if (x >= Frame.instance.getWidth()) throw new Exception("OutOfBounds");
					if (street.checkPoint(new Point(x, y))) {
						System.out.println("got length: " + i);
						return i;
					}
				}
			}
		}
		throw new Exception("H: Cant be this long !");
	}

	@Override
	protected Point getPoint() {
		return point;
	}

}
