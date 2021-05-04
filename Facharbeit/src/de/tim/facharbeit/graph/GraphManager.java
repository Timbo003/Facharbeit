package de.tim.facharbeit.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.tim.facharbeit.Day;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.Structure;

public class GraphManager {

	private static List<Integer> infected = new ArrayList<>();
	private static List<Integer> dead = new ArrayList<>();
	private static List<Integer> imune = new ArrayList<>();
	private static List<Integer> healthy = new ArrayList<>();

	public static void setupNewGraph() {
		fillDataLists();
//		System.out.println("infected: "+infected);
//		System.out.println("imune: " + imune);
//		System.out.println("healthy: " + healthy);
//		System.out.println("dead: " + dead);
		setupGraphLine();
	}

	private static void fillDataLists() {
		for (Day day : Main.testDays) {		//TODO hier muss nach den Tests Variables.days rein
			infected.add(day.getInfected());
			imune.add(day.getImune());
			dead.add(day.getDead());
			healthy.add(day.getHealthy());
		}
	}

	private static void setupGraphLine() {
		List<GraphPoint> ImuneList = fillListWithGraphPoints(imune, Color.gray);
		GraphLine ImuneLine = new GraphLine(ImuneList, Color.gray);
		Main.graphStructures.add(ImuneLine);
	}

	public static List<GraphPoint> fillListWithGraphPoints(List<Integer> intList, Color color){
		List<GraphPoint> graphPointList = new ArrayList<GraphPoint>();
		
		int xShift = 1400 / intList.size();
		
		for (int i = 0; i < intList.size(); i++) {
			GraphPoint newPoint = new GraphPoint(xShift * i + 75, intList.get(i) * 2 + 20, color);
			graphPointList.add(newPoint);
			Main.graphStructures.add(newPoint);
		}
		return graphPointList;
	}
	
	private static List<GraphPoint> getRandPoints(Color color) {
		List<GraphPoint> list = new ArrayList<GraphPoint>();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			GraphPoint p = new GraphPoint(i * 75 + 50, random.nextInt(600), color);
			list.add(p);
			Main.graphStructures.add(p);
		}
		return list;
	}
}
