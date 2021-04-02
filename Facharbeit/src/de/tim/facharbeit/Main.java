package de.tim.facharbeit;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.plaf.basic.BasicSplitPaneUI.BasicVerticalLayoutManager;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.streets.Street;
import de.tim.facharbeit.structure.streets.StreetOrientation;

public class Main {

	public static List<Structure> structures = new ArrayList<>(); // alle sachen, welche auf der map gezeigt werden
//	public static List<Street> street0 = new ArrayList<>(); 
//	public static List<Street> street1 = new ArrayList<>(); 
//	public static List<Street> street2 = new ArrayList<>(); 
//	public static List<Street> street3 = new ArrayList<>(); 
//	public static List<Street> newStreet = new ArrayList<>();

	private static Frame Frame;
	private static int streetInt = 10;

	public static void main(String[] args) {
		System.out.println("start");
		Frame = new Frame();

		createStreets();
		Frame.instance.update();
		System.out.println("created starting Street");
		
		for (int i = 0; i < streetInt; i++) {
			addStreet();
			Frame.instance.update();
			
		}
		sortStreets();
		createHouses();
//		dumpList(Street.streets);	
		System.out.println("end");
	}
	public static void sortStreets() {
		for (int i = 0; i < Street.streets.size(); i++) {
			Street street = Street.streets.get(i);
			street.sortsNeighbors();
//			System.out.println(street.neighbors);
		}
	}
	
	private static void createStreets() {
		Street street0 = new Street(new Point(20 , 20), StreetOrientation.HORIZONTAL, Frame.getWidth() - 40);  // 0 -
		Street street1 = new Street(new Point(20, 20), StreetOrientation.VERTICAL, Frame.getHeight() - 40);    // 1|
		Street street2 = new Street(new Point(20, 730), StreetOrientation.HORIZONTAL, Frame.getWidth() - 40); // 2 _
		Street street3 = new Street(new Point(1480, 20), StreetOrientation.VERTICAL, Frame.getHeight() - 40);   // 3   |
		
		street0.start = street1;
		street0.end = street3;
		
		street1.start = street0;
		street1.end = street2;
		
		street2.start = street1;
		street2.end = street3;
		
		street3.start = street0;
		street3.end = street2;
	}
	
	//TODO Javadoc
	private static void addStreet() {
		Random random = new Random();
		int i = random.nextInt(Street.streets.size());
		if (i == 2 || i == 3) {								//nur von oben nach unten & von links nach rechts sollen straßen erzeugt werden
			addStreet();
			return;
		}
//		System.out.println("i: " + i);									
		Street old = Street.streets.get(i);
		int b = old.getLength() - 10;
		if (b <= 0) {
			addStreet();
			return;
		}
		int a = random.nextInt(b) + 10;
//		System.out.println("a: " + a);
		StreetOrientation orientation = old.getOrientation() == StreetOrientation.VERTICAL
				? StreetOrientation.HORIZONTAL
				: StreetOrientation.VERTICAL;
		int x = old.getX() + (orientation == StreetOrientation.VERTICAL ? a : 0);
		int y = old.getY() + (orientation == StreetOrientation.VERTICAL ? 0 : a);
		Point point = new Point(x, y);
//		System.out.println(point);
//		System.out.println(orientation);
		try {
			Street newStreet = new Street(point, orientation);
			newStreet.start = old;
//			System.out.println("Name of the new street: " + Street.streets.get(Street.streets.size() - 1));
//			System.out.println("name of the source Street: " + old);
			newStreet.reconfigureNeighbors();			 
		} catch (Exception e) {
			addStreet();
		}
	}
	
	public static void createHouses() {
		for (Street street : Street.streets) {
			if (street.orientation == StreetOrientation.HORIZONTAL) {	
				street.createHouses();		
				break;
			}
		}
	}
	
	public static void dumpList(List<Street> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println("street number: " + i);
			System.out.println("start of the street: " + list.get(i).start);
			System.out.println("neighbors of the street: " + list.get(i).neighbors);
			System.out.println("end of the street: " + list.get(i).end);
			System.out.println("");
		}
	}
}