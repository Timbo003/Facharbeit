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
	public static List<Street> street0 = new ArrayList<>(); 
	public static List<Street> street1 = new ArrayList<>(); 
	public static List<Street> street2 = new ArrayList<>(); 
	public static List<Street> street3 = new ArrayList<>(); 
	public static List<Street> newStreet = new ArrayList<>();

	private static Frame Frame;
	private static int streetInt = 1;

	public static void main(String[] args) {
		System.out.println("start");
		Frame = new Frame();

		createStreets();
		Frame.instance.update();
		System.out.println("created Street");
		
		for (int i = 0; i < streetInt; i++) {
			addStreet();
			Frame.instance.update();
			
		}
		System.out.println(Street.neighbors);
		
//		new Timer().scheduleAtFixedRate(new TimerTask() {
//
//			@Override
//			public void run() {
//				addStreet();
//
//				Frame.instance.update();
//			}
//		}, 0, 1000);
	}

	private static void createStreets() {
		new Street(new Point(20, 20), StreetOrientation.HORIZONTAL, 300); // 0 -
		new Street(new Point(20, 20), StreetOrientation.VERTICAL, 300); // 1 |
		new Street(new Point(20, 320), StreetOrientation.HORIZONTAL, 300); // 2 _
		new Street(new Point(320, 20), StreetOrientation.VERTICAL, 300); // 3     |
		
		street0.add(Street.streets.get(1));
		street0.add(Street.streets.get(3));
		Street.neighbors.add(0, street0);
		
		street1.add(Street.streets.get(0));
		street1.add(Street.streets.get(2));
		Street.neighbors.add(1, street1);
		
		street2.add(Street.streets.get(1));
		street2.add(Street.streets.get(3));
		Street.neighbors.add(2, street2);
		
		street3.add(Street.streets.get(0));
		street3.add(Street.streets.get(2));
		Street.neighbors.add(3, street3);
	}

	private static void addStreet() {
		Random random = new Random();
		int i = random.nextInt(Street.streets.size());
		if (i == 2 || i == 3) {								//nur von oben nach unten & von links nach rechts sollen straßen erzeugt werden
			addStreet();
			return;
		}
		System.out.println("i: " + i);									
		Street old = Street.streets.get(i);
		int b = old.getLength() - 10;
		if (b <= 0) {
			addStreet();
			return;
		}
		int a = random.nextInt(b) + 10;
		System.out.println("a: " + a);
		StreetOrientation orientation = old.getOrientation() == StreetOrientation.VERTICAL
				? StreetOrientation.HORIZONTAL
				: StreetOrientation.VERTICAL;
		int x = old.getX() + (orientation == StreetOrientation.VERTICAL ? a : 0);
		int y = old.getY() + (orientation == StreetOrientation.VERTICAL ? 0 : a);
		Point point = new Point(x, y);
		System.out.println(point);
		System.out.println(orientation);
		try {
			new Street(point, orientation);
			System.out.println("Name of the new street: " + Street.streets.get(Street.streets.size() - 1));
			System.out.println("name of the source Street: " + old);
			
			setupStreetNeighbors(Street.streets.get(Street.streets.size() - 1), old, null);								//übergeben die nueste straße und ihre Nachbarn
			 
		} catch (Exception e) {
			addStreet();
		}
	}
	
	private static void setupStreetNeighbors(Street street, Street Neighbor1, Street Neighbor2) {
		newStreet.add(Neighbor1);
		newStreet.add(Neighbor2);
		Street.neighbors.add(Street.neighbors.size(), newStreet);
		
		updateNeighbors(street);
	}
	
	private static void updateNeighbors(Street street) {
		Street.neighbors.get(Street.neighbors.size()-1).get(0);					//TODO die stelle dieser Straße in der Liste suchen und street zu ihren nachbern hinzufügen
	}

}