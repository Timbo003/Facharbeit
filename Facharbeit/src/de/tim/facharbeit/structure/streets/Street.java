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

import de.tim.facharbeit.Main;
import de.tim.facharbeit.dijkstra.DijkstraManager;
import de.tim.facharbeit.dijkstra.DijkstraPoint;
import de.tim.facharbeit.frames.SimulationFrame;
import de.tim.facharbeit.structure.Block;
import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;

public class Street extends Structure {

	public static final int size = 4;
	public static List<Street> streets = new ArrayList<>();
	public List<Street> neighbors = new LinkedList<>();
	public static List<Block> blocks = new LinkedList<>();

	public Street start;
	public Street end;
	public Point startPoint;
	public Point endPoint;

	private int length;
	public StreetOrientation orientation;

	private List<DijkstraPoint> points = new ArrayList<>();
	
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
		int x = orientation == StreetOrientation.HORIZONTAL ? this.startPoint.getX() + length : this.startPoint.getX();
		int y = orientation == StreetOrientation.HORIZONTAL ? this.startPoint.getY() : this.startPoint.getY() + length;
		endPoint = new Point(x, y);
	}
	

	public boolean isPointOnStreet(Point point) {
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
			for (int j = 1; j < this.neighbors.size(); j++) {
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
		for (Street n : neighbors) {
			if (n.orientation.equals(StreetOrientation.HORIZONTAL)) {

				n.sortX();
			} else {
				n.sortY();

			}
		}
	}

	private Street getNextNeighbor(Street street) {
		boolean found = false;

		for (int i = 0; i < neighbors.size(); i++) {
			Street s = neighbors.get(i);
			if (s.equals(street)) {
				// System.out.println("found: " + i);
				found = true;
			} else if (found && (this.isPointOnStreet(street.startPoint) || street.isPointOnStreet(this.startPoint))) {
				return s;
			}
		}
		System.err.println("getNextNeighbor returned null");
		return null;
	}

	public void createBlocks() {
		List<Street> children = new LinkedList<>();
		;

		for (Street street : neighbors) {
			children.add(street);
		}

		for (Street street : neighbors) {
			if (this.isPointOnStreet(street.endPoint)) {
				children.remove(street);
			}
		}

		for (int i = 0; i < children.size() - 1; i++) {
			Street s1 = children.get(i);
			Street s2 = children.get(i + 1);
			createHouse(s1, s2);
		}
	}

	private void createHouse(Street s1, Street s2) {
		Street horizontal1 = this;
		Street vertical1 = s1;
		Street vertical2 = s2;
		
		List<Street> childrenv1 = new LinkedList<>();
		for(Street street : vertical1.neighbors) {
			childrenv1.add(street);
		}
		for(Street street : vertical1.neighbors) {
			if (vertical1.isPointOnStreet(street.endPoint)) {
				childrenv1.remove(street);
			}
		}
		
		Street horizonal2 = childrenv1.get(childrenv1.indexOf(this)+ 1);

		if (horizonal2 == null) {
			return;
		}
		int width = vertical2.getDistance(vertical1.startPoint);
		int height = horizonal2.getDistance(horizontal1.startPoint);
		Point point;
		if (vertical1.isPointOnStreet(horizontal1.startPoint))  {
			point = new Point(horizontal1.startPoint.getX() + 5, horizontal1.startPoint.getY() + 5);
		} else {
			point = new Point(vertical1.startPoint.getX() + 5, vertical1.startPoint.getY() + 5);
		}
		
		List<Street> blockList = new ArrayList<>();
		
		blockList.add(horizontal1);
		blockList.add(vertical1);
		blockList.add(horizonal2);
		blockList.add(vertical2);
		
		Block block = new Block(point, width - 10, height - 10, blockList);
		Main.structures.add(block);
		blocks.add(block);
		SimulationFrame.instance.update();
	}

	private Color color;

	@Override
	public void draw(Graphics graphics) {
		if (color == null) {
			Random random = new Random();
			color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
//			color = new Color(89,89,89);
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
		return "street index: " + streets.indexOf(this) + " Orientation: " + orientation + " x: " + this.getX() + " y: "
				+ this.getY() + " l: " + this.length + " color: " + color + " neighbors: " + neighbors.size();
	}
	
	public void prepairPoints() {
		try {
			points.add(DijkstraManager.getByPoint(startPoint));
			for (int i = 1; i < neighbors.size() - 1; i++) {
				Street neigbor = neighbors.get(i);
				if (isPointOnStreet(neigbor.startPoint)) { //hier ein kleines if und schon läuft alles...
					points.add(DijkstraManager.getByPoint(neigbor.startPoint));
				} else if (isPointOnStreet(neigbor.endPoint)) {
					points.add(DijkstraManager.getByPoint(neigbor.endPoint));
				} else {
					System.out.println("   :#");
				}
			}
			points.add(DijkstraManager.getByPoint(endPoint));
			
			
			
			for (int i = 1; i < points.size(); i++ ) {
				Point a = points.get(i-1).getPoint();
				Point b = points.get(i).getPoint();
				if (this.orientation == StreetOrientation.HORIZONTAL) {
					System.out.println(a.getX() < b.getX() ? ";D" : "   ;/1");
					if (a.getY() != b.getY()) {
						System.out.println("  :*1");
						return;
					}
				} else {
					System.out.println(a.getY() < b.getY() ? ";D" : "   ;/2");
					if (a.getX() != b.getX()) {
						System.out.println("  :*2");
						return;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Please restart your application. This should never happen");
		}
	}
	
	public void addPoint(DijkstraPoint point) throws Exception { // 100 200
		if (!this.isPointOnStreet(point.getPoint())) {
			System.out.println("   :X");
			return;
		}
		for (DijkstraPoint p : points) {
			if (p.checkForDublicate(point)) {
				throw new Exception("Dijstra point dublicate.");
			}
		}
		
		if (this.orientation == StreetOrientation.HORIZONTAL) {
			for (int i = 0; i < points.size(); i++) {
				DijkstraPoint p = points.get(i); // 50 200 | 120 200
				if (point.getY() != p.getY()) {
					System.out.println("  :o");
					return;
				}
				if (p.getX() > point.getX()) {
					points.add(i, point);
					System.out.println(points.indexOf(p) + " - " + points.indexOf(point));
					checkPoints();
					return;
				}
			}
		} else {
			for (int i = 0; i < points.size(); i++) {
				DijkstraPoint p = points.get(i);
				if (point.getX() != p.getX()) {
					System.out.println("  :O");
					return;
				}
				if (p.getY() > point.getY()) {
					points.add(i, point);
					System.out.println(points.indexOf(p) + " - " + points.indexOf(point));
					checkPoints();
					return;
				}
			}
		}
	}
	
	private void checkPoints() {
		System.out.println("checking...");
		for (int i = 1; i < points.size(); i++) {
			Point a = points.get(i-1).getPoint();
			Point b = points.get(i).getPoint();
			if (a.distanceToPoint(b) != a.pointDistance(b)) {
				System.out.println("ouch ;\\");
			}
		}
	}

	public DijkstraPoint getNextCrossing(DijkstraPoint point) throws Exception {
		if (!points.contains(point)) {
			System.err.println(":ooo");
			System.out.println(this);
			throw new Exception("That feature isn't supported yet.");
		}
		return points.get(points.indexOf(point) + 1); 
	}
	
	public DijkstraPoint getPreviousCrossing(DijkstraPoint point) throws Exception {
		if (!points.contains(point)) {
			System.err.println(":ooo");
			System.out.println(this);
			throw new Exception("That feature isn't supported yet.");
		}
		return points.get(points.indexOf(point) - 1); 
	}
}
