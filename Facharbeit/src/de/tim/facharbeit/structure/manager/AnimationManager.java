package de.tim.facharbeit.structure.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.tim.facharbeit.Main;
import de.tim.facharbeit.dijkstra.DijkstraManager;
import de.tim.facharbeit.dijkstra.DijkstraPoint;
import de.tim.facharbeit.frames.Frame;
import de.tim.facharbeit.structure.Health;
import de.tim.facharbeit.structure.Human;
import de.tim.facharbeit.structure.Point;
import de.tim.facharbeit.structure.Structure;
import de.tim.facharbeit.structure.streets.Street;

public class AnimationManager {

	public static void start() {
		
		
		Human testHuman = Street.blocks.get(0).houses.get(0).humans.get(0);
		testHuman.setHealth(Health.DEAD);
		
		

		testHuman.setPoint(DijkstraManager.crossings.get(0).getPoint());
		System.out.println("test" + testHuman.getPoint());
		Frame.instance.update();

		testHuman.path = DijkstraManager.startDijkstra(DijkstraManager.crossings.get(0), DijkstraManager.crossings.get(DijkstraManager.crossings.size() -1));

		
		walkAnimation(testHuman);
	}
	
	private static void walkAnimation(Human human) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Point point = human.getPoint();
				System.out.println(point);

				if (human.walkStep()) {
					timer.cancel();
				}
//				human.setPoint(human.nextPointOnTheWay(target));
//				Frame.instance.update();
//				if (point.equals(target)) {
//					System.out.println("destination reached");
//					walkAnimation(human, );
//					Frame.instance.update();
//					timer.cancel();
//
//				}
				Frame.instance.update();
			}
		}, 100, 5);
	}
}
