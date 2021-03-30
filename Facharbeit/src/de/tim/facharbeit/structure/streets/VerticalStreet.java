package de.tim.facharbeit.structure.streets;


import de.tim.facharbeit.Frame;
import de.tim.facharbeit.structure.Point;

public class VerticalStreet extends Street {

	private Point point;
	private int length;

	public VerticalStreet(Point point) throws Exception {
		this(point, -1);
	}
	
	public VerticalStreet(Point point, int length) throws Exception {
		super(point.getX() - Street.size/2, point.getY() - Street.size/2, Street.size, length);
		this.point = point;
		if (length < 0) {
			this.length = getLength();
			super.setHeight(this.length);
		} else {
			this.length = length;
		}
		for (Street street : allStreets) {
			//|| (!street.equals(this) && point.equals(street.getPoint()) && street instanceof HorizontalStreet)
			if (!street.equals(this) && street.checkPoint(point) && street instanceof VerticalStreet )  {
				throw new Exception("v: point already existing");
			}
		}
	}

	@Override
	public boolean checkPoint(Point point) { // checkt ob ein Punkt auf der Straße liegt
		if (point.getX() == this.point.getX()) {
			if (point.getY() > this.point.getY() && point.getY() < this.point.getY() + length) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Point getDistance(int distance) { // gibt uns einen Punkt, welcher auf der Mittelline der Straße liegt
		return new Point(this.point.getX(), this.point.getY() + distance);
	}

	@Override
	public int getLength() throws Exception {
		System.out.println("x: " + x + " y: " + y);
		for (int i = 1; i < 1000; i++) {
			for (Street street : Street.allStreets) {
				if (street instanceof HorizontalStreet) {
					int x = this.point.getX();
					int y = this.point.getY() + i;
					if (y >= Frame.instance.getHeight()) throw new Exception("OutOfBounds");
					if (street.checkPoint(new Point(x, y))) {
						System.out.println("got length: " + i);
						return i;
					}
				}
			}
		}
		throw new Exception("V: Cant be this long !");
	}

	@Override
	protected Point getPoint() {
		return point;
	}

}
