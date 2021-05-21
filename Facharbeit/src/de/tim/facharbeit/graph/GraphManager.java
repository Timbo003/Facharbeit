package de.tim.facharbeit.graph;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.tim.facharbeit.Day;
import de.tim.facharbeit.Main;
import de.tim.facharbeit.Variables;
import de.tim.facharbeit.frames.GraphFrame;

public class GraphManager {
	public static int minX = 100;
	public static int minY = 50;

	public static int xShift;
	
	public static double humanPixelSize;

	private static List<Integer> infected = new ArrayList<>();
	private static List<Integer> dead = new ArrayList<>();
	private static List<Integer> imune = new ArrayList<>();
	private static List<Integer> healthy = new ArrayList<>();
	
	public static GraphFrame graphFrame;

	public static void setupNewGraph() {
		graphFrame = new GraphFrame();
		resetForNewGraph();
		fillDataLists();
		
		setupVariables();
		
		setupGraphLine();
	}
	
	private static void setupVariables() {
		double x = 1300D / (infected.size() - 1);
		System.out.println("x: " + x + " size: " + infected.size());
		xShift = (int) x;
		
		humanPixelSize = 570D / Variables.totalHumanCounter;				//länge der y Achse / Menschen
		System.out.println("hps:" + humanPixelSize);
	}

	private static void fillDataLists() {
		for (Day day : Variables.days) { 
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

	public static void setUpNamingOnX(Graphics2D graphics) {
		graphics.setColor(Color.black);
		graphics.setFont(Variables.defaultFont); 
		
		String day = "x in Tagen";
		graphics.drawString(day,1405, 625);	
		
		String human = "y in Menschen";
		graphics.drawString(human,60,  15);	
		
		String distance = "Radius: ";
		graphics.drawString(distance,2,  660);
		
		String mask = "Träger: ";
		graphics.drawString(mask,2,  685);
		
		String visitors = "Besucher: ";
		graphics.drawString(visitors,2,  710);
		
		
		
		for (int i = 0; i < infected.size(); i++) {
			String text = ""+(i+1);
			int xOffset = new Canvas().getFontMetrics(Variables.defaultFont).stringWidth(text) / 2;
			int x = xShift * i + minX - xOffset;
			graphics.drawString(text, x, 640);	
			//moveDistance
			text = "" + Variables.days.get(i).getMaxAllowedDistance();
			xOffset = new Canvas().getFontMetrics(Variables.defaultFont).stringWidth(text) / 2;
			x = xShift * i + minX - xOffset;
			graphics.drawString(text, x, 660);
			//wearing Masks
			text = "" + Variables.days.get(i).getWearingMasks();
			xOffset = new Canvas().getFontMetrics(Variables.defaultFont).stringWidth(text) / 2;
			x = xShift * i + minX - xOffset;
			graphics.drawString(text, x, 685);
			//wearing Masks
			text = "" + Variables.days.get(i).getvisitors();
			xOffset = new Canvas().getFontMetrics(Variables.defaultFont).stringWidth(text) / 2;
			x = xShift * i + minX - xOffset;
			graphics.drawString(text, x, 710);
		}
		
		
	}
	
	public static void setUpNamingOnY(Graphics2D graphics) {
		graphics.setColor(Color.black);
		graphics.setFont(Variables.defaultFont); 
		
		for (int i = 0; i <= Variables.totalHumanCounter / 25; i++) {
			String text = ""+(i*25);
			int xOffset = new Canvas().getFontMetrics(Variables.defaultFont).stringWidth(text) / 2;
			int x = 30 - xOffset;
			int y = (int) ( 620 - humanPixelSize * 25D * i) + 5;
			
			graphics.drawString(text, x, y);	
		}
	}

	public static List<GraphPoint> fillListWithGraphPoints(List<Integer> intList, Color color) {
		List<GraphPoint> graphPointList = new ArrayList<GraphPoint>();
		
		for (int i = 0; i < intList.size(); i++) {
			int x = xShift * i + minX;
			int y =  (int) (620 - (intList.get(i) * humanPixelSize));

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
