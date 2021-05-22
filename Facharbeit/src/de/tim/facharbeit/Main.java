package de.tim.facharbeit;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.plaf.basic.BasicSplitPaneUI.BasicVerticalLayoutManager;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import de.tim.facharbeit.dijkstra.DijkstraManager;
import de.tim.facharbeit.dijkstra.DijkstraPoint;
import de.tim.facharbeit.frames.SimulationFrame;
import de.tim.facharbeit.frames.GraphFrame;
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
	
	// it works now
	public static SimulationFrame SimulationFrame;
	private static StartFrame StartFrame;	
	
	static Random random = new Random();

	public static void main(String[] args) {
		setupSavePaths();
		System.out.println("start");
		StartFrame = new StartFrame();
		StartFrame.setupStartFrame();
	}

	public static void switchToSim() {		
		SimulationFrame = new SimulationFrame();

		createStreets();
		SimulationFrame.instance.update();
		System.out.println("created starting Street");

		for (int i = 0; i < Variables.streetCount; i++) {
			addStreet();
			SimulationFrame.instance.update();
		}

		sortStreets();
		SimulationFrame.instance.update();
		createBlocks();

		HumanManager.humanStartup();
		HumanManager.healthStartup();
		HumanManager.characterStartup();

		DijkstraManager.createDijkstraPoints();
		DijkstraManager.__init__();

		InfectionManager.start();
		
		DayManager.nextDay();
		}
	
	public static void setupSavePaths() {
		String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
		
		new File(desktopPath + "\\SimulationData").mkdirs();
		
		Variables.pathGraph = desktopPath + "\\SimulationData\\GraphExport.png";
		Variables.pathSim = desktopPath + "\\SimulationData\\SimExport.png";
		Variables.pathText = desktopPath + "\\SimulationData\\DataExport.txt";
		Variables.DataFile = new File(Variables.pathText);
	}
	
	public static void switchToGraph() {
		GraphManager.setupNewGraph();
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
	
	public static List<Human> getAllLifingHumans() {
		List<Human> humans = new ArrayList<>();
		for (Structure structure : Main.structures) {
			if (structure instanceof Human) {
				Human human = (Human) structure;
				if (!(human.health.equals(Health.DEAD))) {
					humans.add(human);
				}
				
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
		Street street0 = new Street(new Point(20, 20), StreetOrientation.HORIZONTAL, Variables.screenSize.width - 40); // 0 -
		Street street1 = new Street(new Point(20, 20), StreetOrientation.VERTICAL, Variables.screenSize.height - 270); // 1|
		Street street2 = new Street(new Point(20, Variables.screenSize.height - 250 ), StreetOrientation.HORIZONTAL, Variables.screenSize.width - 40); // 2 _
		Street street3 = new Street(new Point(Variables.screenSize.width - 20, 20), StreetOrientation.VERTICAL, Variables.screenSize.height - 270); // 3 |

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
			if (point2.pointDistance(point) < Variables.minimumDistance) {
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