package de.tim.facharbeit.dijkstra;

import java.util.ArrayList;

import java.util.List;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.frames.Frame;
import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.streets.Street;
import de.tim.facharbeit.structure.streets.StreetOrientation;

public class DijkstraManager {
	
	private static List<DijkstraPoint> crossings = new ArrayList<>();
	private static List<Street> checkedStreets = new ArrayList<>();
	
	
	public static List<DijkstraPoint> DijkstraAlgorythmus(DijkstraPoint start, DijkstraPoint target) {
		List<DijkstraPoint> path = new ArrayList<>();
		
		if (path.get(path.size()-1) == target) {
			return path;
		}
		start.marked = true;
		return path;
		
		
	}

	public static void createDijkstraPoints() {
		checkStreet(Street.streets.get(0));
		System.out.println("size: " + crossings.size());
		for (DijkstraPoint dijkstraPoint : crossings) {
			dijkstraPoint.setupDistances();
			System.out.println("distance to up: " + dijkstraPoint.distanceToUp + "\ndistance to down: " + dijkstraPoint.distanceToDown +"\ndistance to left: " + dijkstraPoint.distanceToLeft +"\ndistance to right: " + dijkstraPoint.distanceToRight +"\n\n");
		}
		Main.structures.addAll(crossings);
		Frame.instance.update();
		
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
			System.out.println("Build from " + street.orientation + " point: " + street.startPoint + " and: " + neighbor.startPoint + " -->" + point);
			DijkstraPoint current = new DijkstraPoint(point);
			DijkstraPoint check = getPointFromCrossings(point);
			if (check != null) {
				System.out.println("dublicate");
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
		Frame.instance.update();
		for (Street neighbor : street.neighbors) {
			if (!(isStreetChecked(neighbor))) {
				checkStreet(neighbor);
			}
		}
	}

	private static DijkstraPoint getPointFromCrossings(Point point) {
		for (DijkstraPoint dijkstraPoint : crossings) {
			if (dijkstraPoint.getPoint().equals(point)) return dijkstraPoint;
		}
		return null;
	}
	
	private static boolean isStreetChecked(Street street) {
		for (Street checked : checkedStreets) {
			if (checked.equals(street)) return true;
		}
		return false;
	}
	
	private static Point isPointEqual(Street street, Point point) {
		if (street.orientation == StreetOrientation.HORIZONTAL) {
			if (!(street.startPoint.getY() - point.getY() < 5 || street.startPoint.getY() - point.getY() > -5)) return null;
			return new Point(point.getX(), street.getY());
		} else {
			if (!(street.startPoint.getX() - point.getX() < 5 || street.startPoint.getX() - point.getX() > -5)) return null;
			return new Point(street.getX(), point.getY());
		}
//		if (!(point1.pointDistance(point2) < 10)) return null;
//		if (orientation1 == orientation2) throw new Error("RIP");
//		Point vertical = orientation1 == StreetOrientation.VERTICAL ? point1 : point2;
//		Point horizontal = orientation1 == StreetOrientation.HORIZONTAL ? point1 : point2;
//		int x = vertical.getX();
//		int y = horizontal.getY();
//		return new Point(x, y);
	}
}
