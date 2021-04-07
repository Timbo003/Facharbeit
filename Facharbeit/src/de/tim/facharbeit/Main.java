package de.tim.facharbeit;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.plaf.basic.BasicSplitPaneUI.BasicVerticalLayoutManager;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.streets.Street;
import de.tim.facharbeit.structure.streets.StreetException;
import de.tim.facharbeit.structure.streets.StreetOrientation;

public class Main {

	public static List<Structure> structures = new ArrayList<>(); // alle sachen, welche auf der map gezeigt werden

	private static Frame Frame;
	private static int streetInt = 10;

	public static void main(String[] args) {
		System.out.println("start");
		Frame = new Frame();

		createStreets();
		Frame.instance.update();
		System.out.println("created starting Street");

		for (int i = 0; i < streetInt; i++) {
			addStreet2();
			Frame.instance.update();
		}

		sortStreets();
		Frame.instance.update();
		System.out.println("DUMPING STREETS : ");
		for (Street street : Street.streets) {
			System.out.println(street);
		}
		System.out.println("\n\n\n");

		createHouse();

		System.out.println("end");
	}

	private static void createStreets() {
		Street street0 = new Street(new Point(20, 20), StreetOrientation.HORIZONTAL, Frame.getWidth() - 40); // 0 -
		Street street1 = new Street(new Point(20, 20), StreetOrientation.VERTICAL, Frame.getHeight() - 40); // 1|
		Street street2 = new Street(new Point(20, 730), StreetOrientation.HORIZONTAL, Frame.getWidth() - 36); // 2 _
		Street street3 = new Street(new Point(1480, 20), StreetOrientation.VERTICAL, Frame.getHeight() - 36); // 3 |

		street0.start = street1;
		street0.end = street3;

		street1.start = street0;
		street1.end = street2;

		street2.start = street1;
		street2.end = street3;

		street3.start = street0;
		street3.end = street2;

//		street0.reconfigureNeighbors();
//		street1.reconfigureNeighbors();
//		street2.reconfigureNeighbors();
//		street3.reconfigureNeighbors();
//		System.out.println("n0 : " + street0.neighbors);
//		System.out.println("n1 : " + street1.neighbors);
//		System.out.println("n2 : " + street2.neighbors);
//		System.out.println("n3 : " + street3.neighbors);
	}

	private static void createHouse() {
		for (Street street : Street.streets) {
			if (street.orientation == StreetOrientation.HORIZONTAL) {
				street.createHouses();
			}
		}
	}

	public static void sortStreets() {
		for (int i = 0; i < Street.streets.size(); i++) {
			Street street = Street.streets.get(i);
			street.sortsNeighbors();
//			System.out.println(street.neighbors);
		}
	}

	// TODO Javadoc
	private static void addStreet() {
		Random random = new Random();
		int i = random.nextInt(Street.streets.size());
		if (i == 2 || i == 3) { // nur von oben nach unten & von links nach rechts sollen straßen erzeugt werden
			addStreet();
			return;
		}
		Street old = Street.streets.get(i); // zufällige straße an der wir bauen auser 2 & 3
		int b = old.getLength() - 15; // mindest abstand zum startpunkt von old
		if (b <= 0) { // wir verwenden nur straßen, deren Länge größer 15 ist
			addStreet();
			return;
		}
		int a = random.nextInt(b) + 15; // random int, welche irgendwo auf der straße liegt
		StreetOrientation orientation = old.getOrientation() == StreetOrientation.VERTICAL
				? StreetOrientation.HORIZONTAL
				: StreetOrientation.VERTICAL;
		int x = old.getX() + (orientation == StreetOrientation.VERTICAL ? a : 0);
		int y = old.getY() + (orientation == StreetOrientation.VERTICAL ? 0 : a);
		Point point = new Point(x, y);

		System.out.println("s0" + Street.streets.get(0).neighbors);

		try {
			Street newStreet = new Street(point, orientation);
			newStreet.start = old;
			newStreet.reconfigureNeighbors();
		} catch (Exception e) {
			addStreet();
		}
	}

	private static void addStreet2() {
		Random random = new Random();
		int i = random.nextInt(Street.streets.size());
		if (i == 2 || i == 3) { // nur von oben nach unten & von links nach rechts sollen straßen erzeugt werden
			addStreet2();
			return;
		}
		Street old = Street.streets.get(i);
//		int b = old.getLength() - 15;						//mindest abstand zum startpunkt von old
//		if (b <= 0) {										//wir verwenden nur straßen, deren Länge größer 15 ist
//			addStreet();
//			return;
//		}		
		List<Street> childs = new ArrayList<>();
		for (Street street : Street.streets) {
			if (old.isPointOnStreet(street.startPoint)) {
				childs.add(street);
			}
		}
		
		
		StreetOrientation orientation = old.getOrientation() == StreetOrientation.VERTICAL // wenn die oriantation von
																							// old VERTICAL ist, dann
																							// ist die der neuen
																							// HORIZONTAL und
																							// andersherum
				? StreetOrientation.HORIZONTAL
				: StreetOrientation.VERTICAL;
		
		Point point = null;
		
		
		boolean finished = false;
		for (int j = 0; j < 100; j++) {
			int a = random.nextInt(old.getLength());
			int x = old.getX() + (orientation == StreetOrientation.VERTICAL ? a : 0);
			int y = old.getY() + (orientation == StreetOrientation.VERTICAL ? 0 : a);
			point = new Point(x, y);
			
			for (Street street : childs) {
				if (point.distance(street.startPoint) < 100) {
					finished = true;
					break;
				}
			}
			if (finished) break;
		}
		if (!finished) {
			addStreet2();
			return;
		}
//		for (Street street : Street.streets) {
//			if (old.isPointOnStreet(street.startPoint)) {
//				if (orientation == StreetOrientation.HORIZONTAL) {
//					if ((Math.abs(y - street.startPoint.getY())) > 100) {
//						System.out.println("y break");
//						continue;
//					}else {
//						System.out.println("else 2");
//						addStreet2();
//						return;
//					}
//				}else {
//					if ((Math.abs(x - street.startPoint.getX())) > 100) {
//						System.out.println("x break");
//						continue;
//					}else {
//						System.out.println("else 2");
//						addStreet2();
//						return;
//					}
//				}
//			}
//		}

		try {
			Street newStreet = new Street(point, orientation);
			newStreet.start = old;
			newStreet.reconfigureNeighbors();
		} catch (StreetException e) {
			e.printStackTrace();
			addStreet2();
		}
	}

	// betrag var = var < 0 ? var * -1 : var; oder Math.abs(x);
}