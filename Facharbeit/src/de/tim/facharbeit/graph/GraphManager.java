package de.tim.facharbeit.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.structure.Health;

public class GraphManager {

	public static void setupNewGraph() {
		setupGraphLine();
	}

	private static void setupGraphLine() {
		List<GraphPoint> ImuneList = getRandPoints(Health.IMUNE);
		GraphLine ImuneLine = new GraphLine(ImuneList, Color.blue);
		Main.GraphStructures.add(ImuneLine);
		
		List<GraphPoint> InfectedList = getRandPoints(Health.INFECTED);
		GraphLine InfectedLine = new GraphLine(InfectedList, Color.red);
		Main.GraphStructures.add(InfectedLine);
		
		List<GraphPoint> DeadList = getRandPoints(Health.DEAD);
		GraphLine DeadLine = new GraphLine(DeadList, Color.gray);
		Main.GraphStructures.add(DeadLine);
		
		List<GraphPoint> healthyList = getRandPoints(Health.HEALTHY);
		GraphLine healthyLine = new GraphLine(healthyList, Color.green);
		Main.GraphStructures.add(healthyLine);
	}

	private static List<GraphPoint> getRandPoints(Health health) {
		List<GraphPoint> list = new ArrayList<GraphPoint>();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			GraphPoint p = new GraphPoint(i * 75 + 50, random.nextInt(600), health);
			list.add(p);
			Main.GraphStructures.add(p);
		}
		return list;
	}
}
