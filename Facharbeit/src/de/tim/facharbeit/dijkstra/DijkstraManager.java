package de.tim.facharbeit.dijkstra;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.frames.SimulationFrame;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.streets.Street;
import de.tim.facharbeit.structure.streets.StreetOrientation;

public class DijkstraManager {

	public static List<DijkstraPoint> crossings = new ArrayList<>();
	public static List<DijkstraPoint> markedPoints = new ArrayList<>();
	private static List<Street> checkedStreets = new ArrayList<>();
	public static List<DijkstraPoint> path = new ArrayList<>();
	public static List<DijkstraPoint> toDelete = new ArrayList<>();

	public static void __init__() {
		for (Street street : Street.streets) {
			street.prepairPoints();
		}
		for (House house : Main.totalHouses()) {
			DijkstraPoint point = prepairAlgorithm(house);
			if (point != null) {
				crossings.add(point);
			} else {
				house.deactivate();
			}
		}
	}

	private static DijkstraPoint target;

	
	public static List<DijkstraPoint> startDijkstra(House start, House ziel) {
		ziel.color = Color.BLACK;
		resetPoints();

		DijkstraPoint startPoint = getByPoint(start.pointOnStreet);
		DijkstraPoint endPoint = getByPoint(ziel.pointOnStreet);
		
		startPoint.distanceFromStart = 0;
		target = endPoint;
		
		dijkstraAlgorythmus(startPoint);
		
		buildPath();
		return path;
	}

	private static DijkstraPoint prepairAlgorithm(House house) {
		DijkstraPoint dijkstraPoint = new DijkstraPoint(house.pointOnStreet);
		Street street = house.street;

		DijkstraPoint next;
		DijkstraPoint previous;
		try {
			street.addPoint(dijkstraPoint);
			next = street.getNextCrossing(dijkstraPoint);
			previous = street.getPreviousCrossing(dijkstraPoint);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showInputDialog(null, "This is the message", "This is the default text");
			return null;
		}
		crossings.add(dijkstraPoint);
		Main.structures.add(dijkstraPoint);
		toDelete.add(previous);
		toDelete.add(next);
		
		if (street.orientation == StreetOrientation.HORIZONTAL) {
			next.left = dijkstraPoint;
			dijkstraPoint.right = next;
			
			previous.right = dijkstraPoint;
			dijkstraPoint.left = previous;	
		} else {
			next.up = dijkstraPoint;
			dijkstraPoint.down = next;
			
			previous.down = dijkstraPoint;
			dijkstraPoint.up = previous;	
		}

		dijkstraPoint.setupDistances();
		next.setupDistances();
		previous.setupDistances();
		
		return dijkstraPoint;
	}
	
	public static DijkstraPoint getByPoint(Point point) {
		for (DijkstraPoint dijkstraPoint : crossings) {
			if (dijkstraPoint.getPoint().equals(point)) {
				return dijkstraPoint;
			}
		}
		System.out.println("byPoint");
		throw new Error("oops. I'm a kek.");
	}
	
	private static void resetPoints() {
		for (DijkstraPoint point : crossings) {
			point.setMarked(false);
			point.distanceFromStart = Integer.MAX_VALUE;
			point.last = null;
			point.active = false;
		}
		path = new ArrayList<>();
		checkedStreets = new ArrayList<>();
	}

	public static boolean dijkstraAlgorythmus(DijkstraPoint aktuell) {
		if (aktuell == null) 
			return false;
		if (!aktuell.isMarked()) {
			aktuell.setMarked(true);
			markedPoints.add(aktuell);
		}
		if (aktuell.equals(target))
			return true;
		Map<DijkstraPoint, Integer> dijkstraMap = new HashMap<>();
		dijkstraMap.put(aktuell.up, aktuell.distanceToUp);
		dijkstraMap.put(aktuell.down, aktuell.distanceToDown);
		dijkstraMap.put(aktuell.left, aktuell.distanceToLeft);
		dijkstraMap.put(aktuell.right, aktuell.distanceToRight);
		
		for (DijkstraPoint next : dijkstraMap.keySet()) {
			if (next == null) continue;			
			if (next.distanceFromStart > aktuell.distanceFromStart + dijkstraMap.get(next)) {
				next.distanceFromStart = aktuell.distanceFromStart + dijkstraMap.get(next);
				next.last = aktuell;
			}
		}
		DijkstraPoint next = getNext();
		return dijkstraAlgorythmus(next);
	}
	
	public static DijkstraPoint getNext() {
		int lowest = Integer.MAX_VALUE;
		DijkstraPoint returner = null;
		for (DijkstraPoint point : crossings) {
			if (!point.isMarked() && point.distanceFromStart < lowest) {
				lowest = point.distanceFromStart;
				returner = point;
			}
		}
		//System.out.println("returner: " + returner);
		return returner;
	}

	public static void buildPath() {
		DijkstraPoint last = target;
		path.add(last);
		while (last != null) { //TODO endless
			//System.out.println("last");
			path.add(last);
			last.active = true;
			last = last.last;
		}

//		System.out.println(path);
		for (int i = 0; i < path.size() / 2; i++) {
			DijkstraPoint tmp = path.get(i);
			path.set(i, path.get(path.size() - i - 1));
			path.set(path.size() - i - 1, tmp);
		}
//		System.out.println("swap: " + path);

	}

	public static void createDijkstraPoints() {
		checkStreet(Street.streets.get(0));
//		System.out.println("size: " + crossings.size());
		for (DijkstraPoint dijkstraPoint : crossings) {
			dijkstraPoint.setupDistances();
//			System.out.println("distance to up: " + dijkstraPoint.distanceToUp + "\ndistance to down: "
//					+ dijkstraPoint.distanceToDown + "\ndistance to left: " + dijkstraPoint.distanceToLeft
//					+ "\ndistance to right: " + dijkstraPoint.distanceToRight + "\n\n");
		}
		Main.structures.addAll(crossings);
		SimulationFrame.instance.update();

	}

	private static void checkStreet(Street street) {
		DijkstraPoint last = null;
		for (Street neighbor : street.neighbors) {
			Point point = isPointEqual(street, neighbor.startPoint);
			if (point == null) {
				point = isPointEqual(street, neighbor.endPoint);
			}
			if (point == null) {
				throw new Error("RIP");
			}
//			System.out.println("Build from " + street.orientation + " point: " + street.startPoint + " and: "
//					+ neighbor.startPoint + " -->" + point);
			DijkstraPoint current = new DijkstraPoint(point);
			DijkstraPoint check = getPointFromCrossings(point);
			if (check != null) {
				current = check;
			} else {
				crossings.add(current);
			}
			if (last != null) {
				if (street.orientation == StreetOrientation.HORIZONTAL) {
					last.right = current;
					current.left = last;
				} else {
					last.down = current;
					current.up = last;
				}
			}
			last = current;
		}
		checkedStreets.add(street);
		SimulationFrame.instance.update();
		for (Street neighbor : street.neighbors) {
			if (!(isStreetChecked(neighbor))) {
				checkStreet(neighbor);
			}
		}
	}

	private static DijkstraPoint getPointFromCrossings(Point point) {
		for (DijkstraPoint dijkstraPoint : crossings) {
			if (dijkstraPoint.getPoint().equals(point))
				return dijkstraPoint;
		}
		return null;
	}

	private static boolean isStreetChecked(Street street) {
		for (Street checked : checkedStreets) {
			if (checked.equals(street))
				return true;
		}
		return false;
	}

	private static Point isPointEqual(Street street, Point point) {
		if (street.orientation == StreetOrientation.HORIZONTAL) {
			if (!(street.startPoint.getY() - point.getY() < 5 || street.startPoint.getY() - point.getY() > -5))
				return null;
			return new Point(point.getX(), street.getY());
		} else {
			if (!(street.startPoint.getX() - point.getX() < 5 || street.startPoint.getX() - point.getX() > -5))
				return null;
			return new Point(street.getX(), point.getY());
		}
	}
}