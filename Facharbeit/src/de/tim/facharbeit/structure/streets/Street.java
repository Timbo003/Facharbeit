package de.tim.facharbeit.structure.streets;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.tim.facharbeit.Frame;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;

public class Street extends Structure {

	protected static final int size = 4;
	public static List<Street> streets = new ArrayList<>();

	public List<Street> neighbors = new LinkedList<>();
	public Street start;
	public Street end;
	public Point startPoint;
	public Point endPoint;

	private int length;
	public StreetOrientation orientation;
	
	public int getLength() {
		return length;
	}
	
	public StreetOrientation getOrientation() {
		return orientation;
	}

	public Street(Point point, StreetOrientation orientation) throws Exception {
		this(point, orientation, getEndStreet(point, orientation).getDistance(point));
		this.end = getEndStreet(point, orientation);

	}

	public Street(Point point, StreetOrientation orientation, int length) {
		super(point, orientation == StreetOrientation.HORIZONTAL ? length : size,
				orientation == StreetOrientation.VERTICAL ? length : size);
		Main.structures.add(this);
		streets.add(this);
		this.length = length;
		this.orientation = orientation;
		this.startPoint = point;
		int x = orientation == StreetOrientation.HORIZONTAL ? this.startPoint.getX() : this.startPoint.getX() + length;
		int y = orientation == StreetOrientation.HORIZONTAL ? this.startPoint.getY() + length : this.startPoint.getY();
		endPoint = new Point(x, y);
	}





	private boolean isPointOnStreet(Point point) {
		for (int i = 0; i <= length + size; i++) {
			int x = orientation == StreetOrientation.HORIZONTAL ? this.getX() + i : this.getX();
			int y = orientation == StreetOrientation.VERTICAL ? this.getY() + i : this.getY();
			if (point.getX() == x && point.getY() == y) {
				return true;
			}
		}
		return false;
	}
	

	public static Street getEndStreet(Point point, StreetOrientation orientation) {
		for (int i = 1; i <= 10000; i++) {
//			System.out.println("------ " + i + " ------");
			int x = orientation == StreetOrientation.HORIZONTAL ? point.getX() + i : point.getX();
			int y = orientation == StreetOrientation.VERTICAL ? point.getY() + i : point.getY();
			Point p = new Point(x, y);
			for (Street street : streets) {
				int index = streets.indexOf(street);
//				System.out.print("index: " + index);
				if (street.isPointOnStreet(p) && street.orientation != orientation) {
//					System.out.println(" ok");
					return street;
				}
//				System.out.println(" wrong");
			}
		}
//		System.err.println("no neighours");
		return null;

	}

	private int getDistance(Point point) {
		if (this.orientation == StreetOrientation.VERTICAL) {
			return this.getX() - point.getX();
		} else {
			return this.getY() - point.getY();
		}
	}
	
	public void sortX() {
		Street temp = null;
		for (int i = 0; i < this.neighbors.size(); i++) {
			for (int j = 1; j < this.neighbors.size(); j++) {
				if (this.neighbors.get(j - 1).getX() > this.neighbors.get(j).getX()) {
					temp = this.neighbors.get(j - 1);
					this.neighbors.set(j - 1, this.neighbors.get(j));
					this.neighbors.set(j, temp);
				}
			}
		}
	}

	public void sortY() {
		Street temp = null;
		for (int i = 0; i < this.neighbors.size(); i++) {
			for (int j = 1; j < this.neighbors.size() ; j++) {
				if (this.neighbors.get(j - 1).getY() > this.neighbors.get(j).getY()) {
					temp = this.neighbors.get(j - 1);
					this.neighbors.set(j - 1, this.neighbors.get(j));
					this.neighbors.set(j, temp);
				}
			}
		}
	}
	
	public void sortsNeighbors() {
		this.neighbors.add(this.start);
		this.neighbors.add(this.end);
		System.out.println("size beguin: " + neighbors.size());
		for (Street n : neighbors) {
			if (n.orientation.equals(StreetOrientation.HORIZONTAL)) {

				n.sortX();
			} else {
				n.sortY();
				
			}
		}
		System.out.println("size after: " + neighbors.size());
	}

//	public  void createHouse() {
//		//System.out.println(this.orientation);
//		
//		List<Street> children = new LinkedList<>();
//		for (Street street : neighbors) {
//			if (this.isPointOnStreet(street.startPoint) || street.isPointOnStreet(this.startPoint)) {
//				children.add(street);
//				System.out.println("added street: " + street);
//				System.out.println("added");
//			}
//		}
//		System.out.println(this);
//		System.out.println("children.size(): " + children.size());
//		System.out.println("neighbors.size(): " + neighbors.size());
//		
//		for (int i = 0; i < children.size(); i++) {
//			if (this.startPoint != children.get(i).startPoint) {
//				System.out.println("fit");
//				if (i != 0) {
//					int width = children.get(i).startPoint.getX() - children.get(i - 1).startPoint.getX();
//					System.out.println("width: " + width);
//				}
//
//			}
//			//System.out.println("children.size(): " + children.size());
//			System.out.println("child identical");
//		}	
//		
//	}
	
	private Street getNextNeighbor(Street street) {
		System.out.println("requsted: " + neighbors.indexOf(street));
		boolean found = false;
		
		for (int i = 0; i < neighbors.size(); i++) {
			Street s = neighbors.get(i);
			System.out.println("check index: " + streets.indexOf(s));
			if (s.equals(street)) {
				System.out.println("found: " + i);
				found = true;
			} else if (found && (this.isPointOnStreet(street.startPoint) || street.isPointOnStreet(this.startPoint))) {
				System.out.println("returned: " + i);
				System.out.println("index: " + neighbors.indexOf(s));
				return s;
			} 
		}
		System.out.println("null");
		return null;
	}
	
	public void createHouses() {
		System.out.println("###########################################################");
		System.out.println(this);
		System.out.println("###########################################################");
		List<Street> children = neighbors;
				/*new ArrayList<>();
		for (Street street : neighbors) {
			if (this.isPointOnStreet(street.startPoint) || street.isPointOnStreet(this.startPoint)) {
				children.add(street);
			}
		}*/
		System.out.println("----------------------- neighbors: " + neighbors.size() + " childs: " + children.size());
		for (int i = 0; i < children.size() - 1; i++) {
			Street s1 = children.get(i);
			Street s2 = children.get(i + 1);
			System.out.println("s1: " + s1.startPoint.getX());
			System.out.println("s2: " + s2.startPoint.getX());
			createHouse(s1, s2);
		}
	}

	private void createHouse(Street s1, Street s2) {
		System.out.println(".........................................");
		Street horizontal1 = this;
		Street vertical1 = s1;
		Street vertical2 = s2;
		Street horizonal2 = vertical1.getNextNeighbor(horizontal1);
		System.out.println(s1);
		System.out.println(s2);

		if (horizonal2 == null) {
			return;
		}
		System.out.println("vertical1: " + vertical1);
		System.out.println("vertical2: " + vertical2);
		System.out.println("horizontal1: " + horizontal1);
		System.out.println("horizontal2: " + horizonal2);
		
		int width = vertical2.getDistance(vertical1.startPoint);
		int height = horizonal2.getDistance(horizontal1.startPoint);
		System.out.println("width: " + width);
		System.out.println("height: " + height);
		Point point;
		if (vertical1.isPointOnStreet(horizontal1.startPoint))  {
			point = new Point(horizontal1.startPoint.getX() + 5, horizontal1.startPoint.getY() + 5);
		} else {
			point = new Point(vertical1.startPoint.getX() + 5, vertical1.startPoint.getY() + 5);
		}
		House house = new House(point, width - 10, height - 10);
		System.out.println("Point of the House: " + point);
		Main.structures.add(house);
		Frame.instance.update();
		System.out.println("house created");
	}
	
	
	private Color color;
	
	@Override
	public void draw(Graphics graphics) {
		if (color == null) {
			Random random = new Random();
			color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		}
		graphics.setColor(color);
		graphics.fillRect(this.point.getX() - (size / 2), this.point.getY() - (size / 2), width, height);
	}

	public void reconfigureNeighbors() {
		this.start.neighbors.add(this);
		this.end.neighbors.add(this);
	}
	
	@Override
	public String toString() {
		return "street index: " + streets.indexOf(this) + " Orientation: " + orientation + " x: " + this.getX() + " y: " + this.getY() + " l: " + this.length + " color: " + color + " neighbors: " + neighbors.size();
	}
}
