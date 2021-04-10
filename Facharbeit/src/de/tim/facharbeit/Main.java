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

import de.tim.facharbeit.structure.Block;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Human;
import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.manager.AnimationManager;
import de.tim.facharbeit.structure.manager.HumanManager;
import de.tim.facharbeit.structure.streets.Street;
import de.tim.facharbeit.structure.streets.StreetOrientation;

public class Main {

	public static List<Structure> structures = new ArrayList<>(); // alle sachen, welche auf der map gezeigt werden
	

	private static Frame Frame;
	private static StartFrame StartFrame;
	public static int minimumDistance = 100;

	static Random random = new Random();

	public static void main(String[] args) {
		
		
		
		System.out.println("start");
		
		StartFrame = new StartFrame();
		StartFrame.setupStartFrame();
	}
	
	public static void switchToSim() {

		Frame = new Frame();

		createStreets();
		Frame.instance.update();
		System.out.println("created starting Street");

		for (int i = 0; i < Variables.streetCount; i++) {
			addStreet();
			Frame.instance.update();
		}

		sortStreets();
		Frame.instance.update();
		//dumpAllStreets();
		createBlocks();
		
		HumanManager.humanStartup();
		HumanManager.healthStartup();
		
		System.out.println(HumanManager.totalHumans());

		
		
		
		AnimationManager.start();
		System.out.println("struc" + Main.structures);

		System.out.println("end");
//		calculateDistance();
	}

	
	public static void calculateDistance() {
		long time = System.currentTimeMillis();
		List<Human> humans = getAllHumans();
		int[] distances = new int[humans.size()*humans.size()];
		int counter = 0;
		for (int i = 0; i < humans.size(); i++) {
			Human human1 = humans.get(i);
			for (int j = i + 1; j < humans.size(); j++) {
				Human human2 = humans.get(j);
				if (human1.equals(human2)) continue;
				distances[counter++] = human1.distanceTo(human2.getPoint());
				if (human1.distanceTo(human2.getPoint()) < 1) {
					human1.setHealth(Health.DEAD);
					human2.setHealth(Health.DEAD);
				}
			}
		}
		System.out.println("DONE! Took " + (System.currentTimeMillis() - time) + "ms");
	}
	
	public static List<Human> getAllHumans(){
		List<Human> humans = new ArrayList<>();
		for (Structure structure : Main.structures) {
			if (structure instanceof Human) {
				Human human = (Human) structure;
				humans.add(human);
			}
		}
		return humans;
	}
	

	public static int totalHouses() {
		int totalHouses = 0;
		for (Block block : Street.blocks) {
			totalHouses += block.houses.size();
		}
		return totalHouses;
	}

	public static int totalBlocks() {
		int totalBlocks = 0;
		for (Block block : Street.blocks) {
			totalBlocks += 1;
		}
		return totalBlocks;
	}

	

	public static void dumpAllStreets() {
		System.out.println("DUMPING STREETS :");
		for (Street street : Street.streets) {
			System.out.println(street);
		}
		System.out.println("");
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
		if (i == 2 || i == 3) { // nur von oben nach unten & von links nach rechts sollen stra�en erzeugt werden
			addStreet();
			return;
		}
		Street old = Street.streets.get(i);
		int b = old.getLength() - 15;
		if (b <= 0) {
			addStreet();
			return;
		}
		int a = random.nextInt(b) + 15;
		StreetOrientation orientation = old.getOrientation() == StreetOrientation.VERTICAL
				? StreetOrientation.HORIZONTAL
				: StreetOrientation.VERTICAL;
		int x = old.getX() + (orientation == StreetOrientation.VERTICAL ? a : 0);
		int y = old.getY() + (orientation == StreetOrientation.VERTICAL ? 0 : a);
		Point point = new Point(x, y);

		List<Point> intersectionPoints = new LinkedList<>(); // includes intersections of old
		for (Street street : Street.streets) {
			if (old.isPointOnStreet(street.startPoint)) { // liste wird gef�llt mit stra�en, welche ihren AP auf der
															// stra�e haben
				intersectionPoints.add(street.startPoint);
			}
		}
		intersectionPoints.add(old.endPoint);

		for (Point point2 : intersectionPoints) {
			if (point2.pointDistance(point) < minimumDistance) {
				System.err.println("Distance is to short");
				addStreet();
				return;
			}
		}

		try {
			Street newStreet = new Street(point, orientation);
			newStreet.start = old;
			newStreet.reconfigureNeighbors();
		} catch (Exception e) {
			addStreet();
		}
	}

	private static void createBlocks() {
		for (Street street : Street.streets) {
			if (street.orientation == StreetOrientation.HORIZONTAL) {
				street.createBlocks();
			}
		}
	}
}