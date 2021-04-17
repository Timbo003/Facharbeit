package de.tim.facharbeit.structure.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.dijkstra.DijkstraManager;
import de.tim.facharbeit.dijkstra.DijkstraPoint;
import de.tim.facharbeit.frames.Frame;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.House;
import de.tim.facharbeit.structure.Human;
import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.streets.Street;

public class AnimationManager {

	public static void start() {
		
		
		for (Structure structure : Main.structures) {
			if (structure instanceof Human) {
				Random random = new Random();
				int randIntStart = random.nextInt(DijkstraManager.crossings.size() - 1);
				int randIntEnd = random.nextInt(DijkstraManager.crossings.size() - 1);
				while (randIntEnd == randIntStart) {
					randIntEnd = random.nextInt(DijkstraManager.crossings.size() - 1);
				}
				
				DijkstraPoint start = DijkstraManager.crossings.get(randIntStart);
				DijkstraPoint end = DijkstraManager.crossings.get(randIntEnd); 
				System.out.println("start: " + start);
				System.out.println("end: " + end);
				
				Human human = (Human) structure;
				
				human.setPoint(start.getPoint());
				Frame.instance.update();
				human.path = DijkstraManager.startDijkstra(start, end);
				DijkstraManager.resetPoints();
			}
		}
		walkAnimation();
	}
	
	private static void walkAnimation() {
		Timer timer = new Timer();
		
//		List<Human> humas = Main.getAllHumans();
		
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				boolean finished = true;
				for (Structure structure : Main.structures) {
					if (structure instanceof Human) {
						Human human = (Human) structure;
						if (human.getPoint().equals(human.path.get(human.path.size() - 1).getPoint())) {
							continue;
						}
						finished = false;
						human.setPoint(human.nextPointOnTheWay(human.path.get(human.pathIndex).getPoint()));
					}	
				}
				if (finished) {
					timer.cancel();
					System.out.println("finished");
				}
				Frame.instance.update();
			}
		}, 100, 5);
	}
}
