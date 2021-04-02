package de.tim.facharbeit.structure.streets;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;

public class Street extends Structure {

	protected static final int size = 3;
	public static List<Street> streets = new ArrayList<>(); 
	public static List<List<Street>> neighbors = new ArrayList<>();
	
	
	private int length;
	private StreetOrientation orientation;

	public Street(Point point, StreetOrientation orientation) throws Exception {
		this(point, orientation , getNeighbour(point, orientation).getDistance(point));
	}
	
	public Street(Point point, StreetOrientation orientation, int length) {
		super(point.getX(), point.getY(),
		orientation == StreetOrientation.HORIZONTAL ? length : size, 
		orientation == StreetOrientation.VERTICAL ? length : size);
		Main.structures.add(this);
		streets.add(this);
		this.length = length;
		this.orientation = orientation;
	}
	
	public int getLength() {
		return length;
	}

	public StreetOrientation getOrientation() {
		return orientation;
	}

	private boolean checkPoint(Point point) {
		for (int i = 0; i < length; i++) {
			int x = orientation == StreetOrientation.HORIZONTAL ? super.getX() + i : super.getX();
			int y = orientation == StreetOrientation.VERTICAL ? super.getY() + i: super.getY();
			if (point.getX() == x && point.getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	protected static Street getNeighbour(Point point, StreetOrientation orientation) {
		for (int i = 1; i <= 1000; i++) {
			System.out.println("------ " + i + " ------");
			int x = orientation == StreetOrientation.HORIZONTAL ? point.getX() + i : point.getX();
			int y = orientation == StreetOrientation.VERTICAL ? point.getY() + i: point.getY();
			Point p = new Point(x, y);
			System.out.println(p);
			for (Street street : streets) {
				int index = streets.indexOf(street);
				System.out.print("index: " + index);
				if (street.checkPoint(p)) {
					System.out.println(" ok");
					return street;
				}
				System.out.println(" wrong");
			}
		}
		System.err.println("no neighours");
		return null;
		
	}
	
	private int getDistance(Point point) {
		if (this.orientation == StreetOrientation.VERTICAL) {
			return this.getX() - point.getX();
		} else {
			return this.getY() - point.getY();
		}
	}
	
	@Override
	public void draw(Graphics graphics) {
		Random random = new Random();
		Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		graphics.setColor(color);
		graphics.fillRect(x, y, width, height);
	}
}
