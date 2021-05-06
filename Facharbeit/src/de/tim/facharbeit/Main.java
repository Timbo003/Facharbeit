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

import de.tim.facharbeit.dijkstra.DijkstraManager;
import de.tim.facharbeit.dijkstra.DijkstraPoint;
import de.tim.facharbeit.frames.Frame;
import de.tim.facharbeit.frames.GraphFrame;
import de.tim.facharbeit.frames.ScoreFrame;
import de.tim.facharbeit.frames.StartFrame;
import de.tim.facharbeit.graph.GraphManager;
import de.tim.facharbeit.graph.GraphStructure;
import de.tim.facharbeit.structure.Block;
import de.tim.facharbeit.structure.Entrance;
import de.tim.facharbeit.structure.Garden;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Human;
import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.manager.AnimationManager;
import de.tim.facharbeit.structure.manager.DayManager;
import de.tim.facharbeit.structure.manager.HumanManager;
import de.tim.facharbeit.structure.manager.InfectionManager;
import de.tim.facharbeit.structure.manager.WalkManager;
import de.tim.facharbeit.structure.streets.Street;
import de.tim.facharbeit.structure.streets.StreetOrientation;

public class Main {

	public static List<Structure> structures = new ArrayList<>(); // alle sachen, welche auf der map gezeigt werden
	public static List<GraphStructure> graphStructures = new ArrayList<>(); // alle sachen, welche auf der map gezeigt werden

	public static List<Day> testDays = new ArrayList<>();
	
	// it works now
	private static Frame Frame;
	private static StartFrame StartFrame;
	public static ScoreFrame ScoreFrame;
	private static GraphFrame GraphFrame;
	

	public static int minimumDistance = 100;

	static Random random = new Random();

	public static void main(String[] args) {
		System.out.println("start");

		StartFrame = new StartFrame();
		StartFrame.setupStartFrame();
	}

	public static void switchToSim() {
		ScoreFrame = new ScoreFrame();
		ScoreFrame.setupScoreFrame();
		
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
		createBlocks();

		HumanManager.humanStartup();
		HumanManager.healthStartup();
		HumanManager.characterStartup();

		DijkstraManager.createDijkstraPoints();
		for (House house : totalHouses()) {
			house.getDijkstraPoint();
		}

		InfectionManager.start();
		fillTestDays(50);
		
		DayManager.nextDay();
		
		}
	
	public static void switchToGraph() {
		GraphFrame = new GraphFrame();
		GraphManager.setupNewGraph();
	}
	
	
	public static void fillTestDays(int nDays) {
		Random random = new Random();
		for (int i = 0; i < nDays; i++) {
			int max = 300;
			
			Day newDay = new Day(i);
			
			newDay.healthy = random.nextInt(max);
			max = max - newDay.getHealthy();
			
			newDay.infected = random.nextInt(max);
			max = max - newDay.getInfected();
			
			newDay.imune = random.nextInt(max);
			max = max - newDay.getImune();
			
			newDay.dead = max;
			
			testDays.add(newDay);
		}
		System.out.println(testDays);
		System.out.println(testDays.size());
	}
	
	public static List<Human> getAllHumans() {
		List<Human> humans = new ArrayList<>();
		for (Structure structure : Main.structures) {
			if (structure instanceof Human) {
				Human human = (Human) structure;
				humans.add(human);
			}
		}
		return humans;
	}

	public static List<House> totalHouses() {
		List<House> houses = new ArrayList<>();
		for (Structure structure : Main.structures) {
			if (structure instanceof House) {
				houses.add((House) structure);
			}
		}
		return houses;
	}

	public static int totalBlocks() { // return Street.blocks.size(); //<--- Reicht das nicht ????
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
		if (i == 2 || i == 3) { // nur von oben nach unten & von links nach rechts sollen straßen erzeugt werden
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
			if (old.isPointOnStreet(street.startPoint)) { // liste wird gefüllt mit straßen, welche ihren AP auf der
															// straße haben
				intersectionPoints.add(street.startPoint);
			}
		}
		intersectionPoints.add(old.endPoint);

		for (Point point2 : intersectionPoints) {
			if (point2.pointDistance(point) < minimumDistance) {
//				System.err.println("Distance is to short");
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

	public static void givaAllNumbs() {

		System.out.println("structures size: " + structures.size());

		int Blocks = 0;
		for (Structure structure : structures) {
			if (structure instanceof Block) {
//				System.out.println(structure);
				Blocks++;
			}
		}
		System.out.println("Blocks: " + Blocks);

		int Houses = 0;
		for (Structure structure : structures) {
			if (structure instanceof House) {
//				System.out.println(structure);
				Houses++;
			}
		}
		System.out.println("Houses: " + Houses);

		int Humans = 0;
		for (Structure structure : structures) {
			if (structure instanceof Human) {
//				System.out.println(structure);
				Humans++;

			}
		}
		System.out.println("Humans: " + Humans);

		int Gardens = 0;
		for (Structure structure : structures) {
			if (structure instanceof Garden) {
//				System.out.println(structure);
				Gardens++;

			}
		}
		System.out.println("Gardens: " + Gardens);

		int DijPoint = 0;
		for (Structure structure : structures) {
			if (structure instanceof DijkstraPoint) {
//				System.out.println(structure);
				DijPoint++;

			}
		}
		System.out.println("DijPoint: " + DijPoint);

		int Entrances = 0;
		for (Structure structure : structures) {
			if (structure instanceof Entrance) {
//				System.out.println(structure);
				Entrances++;

			}
		}
		System.out.println("Entrances: " + Entrances);

		int Streets = 0;
		for (Structure structure : structures) {
			if (structure instanceof Street) {
//				System.out.println(structure);
				Streets++;

			}
		}
		System.out.println("Streets: " + Streets);
	}

	private static void createBlocks() {
		for (Street street : Street.streets) {
			if (street.orientation == StreetOrientation.HORIZONTAL) {
				street.createBlocks();
			}
		}
	}
}