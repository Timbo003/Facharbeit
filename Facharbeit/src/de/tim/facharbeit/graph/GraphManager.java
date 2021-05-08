package de.tim.facharbeit.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.tim.facharbeit.Day;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.frames.GraphFrame;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.Structure;

public class GraphManager {
	static int minX = 100;
	static int minY = 50;
	
	static int xShift;

	private static List<Integer> infected = new ArrayList<>();
	private static List<Integer> dead = new ArrayList<>();
	private static List<Integer> imune = new ArrayList<>();
	private static List<Integer> healthy = new ArrayList<>();

	public static void setupNewGraph() {
		GraphFrame GraphFrame = new GraphFrame();
		resetForNewGraph();
		fillDataLists();
		double x = 1300D / (infected.size()-1);
		System.out.println("x: " + x + " size: " + infected.size());
		xShift = (int) x;
		setupGraphLine();
	}


	private static void fillDataLists() {
		for (Day day : Variables.days) {		//TODO hier muss nach den Tests Variables.days rein   //Main.testDays
			infected.add(day.getInfected());
			imune.add(day.getImune());
			dead.add(day.getDead());
			healthy.add(day.getHealthy());
		}
	}
	
	private static void resetForNewGraph() {
		Main.graphStructures.clear();
		infected.clear();
		dead.clear();
		imune.clear();
		healthy.clear();
	}

	private static void setupGraphLine() {
		List<GraphPoint> ImuneList = fillListWithGraphPoints(imune, Color.blue);
		GraphLine ImuneLine = new GraphLine(ImuneList, Color.blue);
		Main.graphStructures.add(ImuneLine);
		
		List<GraphPoint> HealthyList = fillListWithGraphPoints(healthy, Color.green);
		GraphLine HealthyLine = new GraphLine(HealthyList, Color.green);
		Main.graphStructures.add(HealthyLine);
		
		List<GraphPoint> DeadList = fillListWithGraphPoints(dead, Color.gray);
		GraphLine DeadLine = new GraphLine(DeadList, Color.gray);
		Main.graphStructures.add(DeadLine);
		
		List<GraphPoint> InfectedList = fillListWithGraphPoints(infected, Color.red);
		GraphLine InfectedLine = new GraphLine(InfectedList, Color.red);
		Main.graphStructures.add(InfectedLine);
	}

	public static List<GraphPoint> fillListWithGraphPoints(List<Integer> intList, Color color){
		List<GraphPoint> graphPointList = new ArrayList<GraphPoint>();
		
		
		
		for (int i = 0; i < intList.size(); i++) {
			int x = xShift * i + minX;
			int y = 700 - (intList.get(i) * 2 + minY);
			
			GraphPoint newPoint = new GraphPoint(x, y, color);
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
